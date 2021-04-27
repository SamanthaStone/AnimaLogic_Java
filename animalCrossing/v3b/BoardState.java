package animalCrossing.v3b;

import java.util.ArrayList;

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
		Animal nextAnimal = b.board.get(i).get(0);
		boolean valid = (lastAnimal.isMatch(nextAnimal)) ? true : false;		
		return valid;
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

	ArrayList<ArrayList<Animal>> findAllSolutions(BoardState board) {
		ArrayList<ArrayList<Animal>> solutions = new ArrayList<ArrayList<Animal>>();

		int solSize = (board.board.size()*board.board.size());
		
		if ((board.animalsRemoved != null) && (board.animalsRemoved.size() >= solSize )) {
			solutions.add(board.animalsRemoved);
			return solutions;
		}
		
		ArrayList<BoardState> nextBoardStates = board.nextBoardStates(board);
		for (BoardState b : nextBoardStates) {
			ArrayList<ArrayList<Animal>> sols = findAllSolutions(b);
			solutions.addAll(sols);
		}

		return solutions;
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