package animalCrossing.duplicate.v2;

import java.util.ArrayList;
import java.util.LinkedList;

public class BoardState {
	ArrayList<LinkedList<Animal>> board;
	ArrayList<Animal> animalsRemoved;
	ArrayList<Integer> solutionAccumulator;

	public BoardState(ArrayList<LinkedList<Animal>> board, ArrayList<Animal> removed) {
		this.board = board;
		animalsRemoved = removed;
	}

	BoardState performMove(int i) {
		ArrayList<LinkedList<Animal>> boardCopy = new ArrayList<>();
		for (LinkedList<Animal> row : board) {
			LinkedList<Animal> copyRow = new LinkedList<Animal>();
			for (Animal a : row) {
				copyRow.add(a);
			}
			boardCopy.add(copyRow);
		}

		ArrayList<Animal> animalsRemovedCopy = new ArrayList<>();
		if (animalsRemoved != null) {
			for (Animal a : animalsRemoved) {
				animalsRemovedCopy.add(a);
			}
		}

		animalsRemovedCopy.add(board.get(i).get(0));
		boardCopy.get(i).remove(0);
		return new BoardState(boardCopy, animalsRemovedCopy);
	}

	boolean canPerformMove(int i, BoardState b) {
		if (b.animalsRemoved == null) 
			return true;
		if (b.board.get(i).size() == 0) 
			return false;

		Animal lastAnimal = b.animalsRemoved.get(b.animalsRemoved.size() - 1);
		Animal nextAnimal = b.board.get(i).get(0);
		return lastAnimal.isMatch(nextAnimal);
	}

	ArrayList<BoardState> nextBoardStates(BoardState board) {
		ArrayList<BoardState> array = new ArrayList<BoardState>();
		for (int i = 0; i < board.board.size(); i++) {
			if (canPerformMove(i, board)) {
				array.add(performMove(i));
			}
		}
		return array;
	}

	boolean findSolutionRows(BoardState board, ArrayList<Integer> accumulator) {
		if (board.animalsRemoved != null
				&& (board.animalsRemoved.size() >= board.board.size() * board.board.size())) {
			solutionAccumulator = accumulator;
			return true;
		}
		
		ArrayList<BoardState> nextBoardStates = board.nextBoardStates(board);
		for (int i = 0; i < nextBoardStates.size(); i++) {
			accumulator.add(i);
			if (findSolutionRows(nextBoardStates.get(i), accumulator))
				return true;
			accumulator.remove(i);
		}
		solutionAccumulator = accumulator;
		return false;
	}
}
