package animalCrossing.rating.v1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import animalCrossing.rating.v1.Animal;

public class BoardState {
	ArrayList<ArrayList<Animal>> board;
	ArrayList<Animal> animalsRemoved;

	static int deadEnds = 0;
	static int solutions = 0;
	static double averageLengthOfDeadEnd = 0;
	static LinkedList<Integer> deadEndArray = new LinkedList<>();

	public BoardState(ArrayList<ArrayList<Animal>> board, ArrayList<Animal> removed) {
		this.board = board;
		animalsRemoved = removed;
	}

	public static void main(String[] args) {
		ArrayList<ArrayList<Animal>> board = new ArrayList<>();
		board.add(new ArrayList<Animal>());
		board.add(new ArrayList<Animal>());
		board.get(0).add(new Animal(Animal.Color.BLUE, Animal.Species.GIRAFFE));
		board.get(0).add(new Animal(Animal.Color.BLUE, Animal.Species.CAMEL));
		board.get(1).add(new Animal(Animal.Color.RED, Animal.Species.LION));
		board.get(1).add(new Animal(Animal.Color.RED, Animal.Species.GIRAFFE));

		BoardState bs = new BoardState(board, null);
		ArrayList<ArrayList<Animal>> allSolutions = new ArrayList<ArrayList<Animal>>();
		bs.findAllSolutions(allSolutions);
		BoardState.printStatistics();
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

	ArrayList<BoardState> nextBoardStates() {
		ArrayList<BoardState> array = new ArrayList<BoardState>();
		for (int i = 0; i < board.size(); i++) {
			if (canPerformMove(i, this)) {
				array.add(performMove(i));
			}
		}
		return array;
	}

	void findAllSolutions(ArrayList<ArrayList<Animal>> allSolutions) {
		if (animalsRemoved != null && (animalsRemoved.size() >= board.size() * board.size())) {
			allSolutions.add(animalsRemoved);
			solutions++;
			return;
		}

		ArrayList<BoardState> nextBoardStates = nextBoardStates();
		int newDeadEnds = board.size() - nextBoardStates.size();
		deadEnds = deadEnds + newDeadEnds;
		for (int i = 0; i < newDeadEnds; i++) {
			deadEndArray.add(animalsRemoved.size());
		}
		for (BoardState b : nextBoardStates) {
			b.findAllSolutions(allSolutions);
		}
	}

	private static double calculateAverageDeadEnd(LinkedList<Integer> deadEndList) {
		if (deadEndList.size() != 0) {
			int sum = 0;
			for (Integer deadEndLength : deadEndList)
				sum += deadEndLength;
			return (double) sum / deadEndList.size();
		}
		return 0;
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

	public static void printStatistics() {
		averageLengthOfDeadEnd = calculateAverageDeadEnd(deadEndArray);
		System.out.println("Ave. Length of Dead End: " + averageLengthOfDeadEnd);
		Collections.sort(deadEndArray);
		int tempLength = 0;
		@SuppressWarnings("unused")
		int number = 0;
		for (Integer i : deadEndArray) {
			if (i == tempLength) {
				number++;
			} else if (i == tempLength + 1) {
				// System.out.println(i + ": " + number);
				tempLength++;
				number = 0;
			}
		}
		System.out.println("Dead Ends: " + deadEnds);
		System.out.println("Solutions: " + solutions);
		System.out.println("Dead End to Solution Ratio: " + ((double) deadEnds / solutions));
	}

	public static void resetStats() {
		deadEnds = 0;
		solutions = 0;
		averageLengthOfDeadEnd = 0;
		deadEndArray = new LinkedList<>();
	}
}