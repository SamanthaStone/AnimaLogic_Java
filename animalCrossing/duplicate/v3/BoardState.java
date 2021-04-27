package animalCrossing.duplicate.v3;

import java.util.ArrayList;
import java.util.LinkedList;

public class BoardState {
	ArrayList<LinkedList<Animal>> board;
	ArrayList<Animal> animalsRemoved;

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

		if (b.animalsRemoved.size() == 0) {
			return true;
		}
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

	public ArrayList<ArrayList<Integer>> findSolutionRows(BoardState boardState) {
		return findSolutionRows(boardState, new ArrayList<Integer>());
	}

	private ArrayList<ArrayList<Integer>> findSolutionRows(BoardState boardState, ArrayList<Integer> accumulator) {
		ArrayList<ArrayList<Integer>> solutions = new ArrayList<ArrayList<Integer>>();

		if (boardState.animalsRemoved != null
				&& (boardState.animalsRemoved.size() >= boardState.board.size() * boardState.board.size())) {
			solutions.add(copy(accumulator));
			return solutions;
		}

		ArrayList<BoardState> nextBoardStates = boardState.nextBoardStates(boardState);
		for (int i = 0; i < nextBoardStates.size(); i++) {
			accumulator.add(i);
			ArrayList<ArrayList<Integer>> sols = findSolutionRows(nextBoardStates.get(i), accumulator);
			for (ArrayList<Integer> solution : sols)
				solutions.add(copy(solution));
			accumulator.remove(accumulator.size() - 1);
		}
		return solutions;
	}

	private ArrayList<ArrayList<Integer>> findPathwayRows(BoardState boardState, ArrayList<Integer> accumulator) {
		ArrayList<ArrayList<Integer>> solutions = new ArrayList<ArrayList<Integer>>();

		if (boardState.animalsRemoved != null
				&& (boardState.animalsRemoved.size() >= boardState.board.size() * boardState.board.size())) {
			solutions.add(copy(accumulator));
			return solutions;
		}

		for (int i = 0; i < boardState.board.size(); i++) {
			if (canPerformMove(i, boardState)) {
				accumulator.add(i);
				Animal a = boardState.board.get(i).get(0);
				if (boardState.animalsRemoved == null)
					boardState.animalsRemoved = new ArrayList<>();
				boardState.animalsRemoved.add(a);
				boardState.board.get(i).remove(a);

				ArrayList<ArrayList<Integer>> sols = findPathwayRows(boardState, accumulator);
				for (ArrayList<Integer> solution : sols)
					solutions.add(copy(solution));

				accumulator.remove(accumulator.size() - 1);
				boardState.board.get(i).add(a);
				boardState.animalsRemoved.remove(animalsRemoved.size() - 1);
			} else {
				solutions.add(copy(accumulator));
			}
		}
		return solutions;
	}

	public ArrayList<ArrayList<Integer>> findPathwayRows(BoardState boardState) {
		return findPathwayRows(boardState, new ArrayList<Integer>());
	}

	private ArrayList<Integer> copy(ArrayList<Integer> accumulator) {
		ArrayList<Integer> copy = new ArrayList<>();
		for (Integer integer : accumulator)
			copy.add(integer);
		return copy;
	}

}
