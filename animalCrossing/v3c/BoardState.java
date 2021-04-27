package animalCrossing.v3c;

import java.util.ArrayList;

import animalCrossing.v3c.Animal;
import animalCrossing.v3c.BoardState;

public class BoardState {
	ArrayList<ArrayList<Animal>> board;
	ArrayList<Animal> animalsRemoved;

	public BoardState(ArrayList<ArrayList<Animal>> board,
			ArrayList<Animal> removed) {
		this.board = board;
		animalsRemoved = removed;
	}

	BoardState performMove(int i) {
		ArrayList<ArrayList<Animal>> boardCopy = new ArrayList<>();

		for (ArrayList<Animal> row : board) {
			ArrayList<Animal> copyRow = new ArrayList<Animal>();
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
		if (b.animalsRemoved == null) {
			return true;
		}
		Animal lastAnimal = b.animalsRemoved.get(b.animalsRemoved.size() - 1);
		if (b.board.get(i).size() == 0) {
			return false;
		}

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

	void findAllSolutions(BoardState board,
			ArrayList<ArrayList<Animal>> allSolutions) {

		if (board.animalsRemoved != null
				&& (board.animalsRemoved.size() >= board.board.size()
						* board.board.size())) {
			allSolutions.add(board.animalsRemoved);
		}

		ArrayList<BoardState> nextBoardStates = board.nextBoardStates(board);
		for (BoardState b : nextBoardStates) {
			findAllSolutions(b, allSolutions);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("board: ");
		for (ArrayList<Animal> arr : board) {
			for (Animal a : arr) {
				sb.append(a + ", ");
			}
			sb.append(" || ");
		}
		sb.append("\n");
		sb.append("animals off: ");
		if (animalsRemoved != null)
			for (Animal a : animalsRemoved) {
				sb.append(a + " ");
			}
		return sb.toString();
	}
}