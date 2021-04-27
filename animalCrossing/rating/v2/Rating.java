package animalCrossing.rating.v2;

import java.util.ArrayList;

import animalCrossing.rating.v2.Animal.Color;
import animalCrossing.rating.v2.Animal.Species;

public class Rating {

	public static final double EASY = 5; // 2.7
	public static final double MEDIUM = 20;
	public static final double HARD = 30;
	public static final double EXPERT = 60;
	public static final double DEATH = 120;

	public static void main(String[] args) {
		ArrayList<ArrayList<ArrayList<Animal>>> boardstoTest = Statistics.readBoards(20);
		for (ArrayList<ArrayList<Animal>> boardToTest : boardstoTest) {
			BoardState bs = new BoardState(boardToTest, null);
			System.out.print(bs.board + "\nRating: ");
			printRating(bs);
			System.out.println();
		}

		// test some of Mark's puzzles
		ArrayList<ArrayList<Animal>> board1 = new ArrayList<ArrayList<Animal>>();
		for (int i = 0; i < 4; i++) {
			board1.add(new ArrayList<Animal>());
		}

		board1.get(0).add(new Animal(Color.BLUE, Species.CAMEL));
		board1.get(0).add(new Animal(Color.RED, Species.GIRAFFE));
		board1.get(0).add(new Animal(Color.BLUE, Species.GIRAFFE));
		board1.get(0).add(new Animal(Color.YELLOW, Species.GIRAFFE));
		board1.get(1).add(new Animal(Color.GREEN, Species.LION));
		board1.get(1).add(new Animal(Color.YELLOW, Species.LION));
		board1.get(1).add(new Animal(Color.GREEN, Species.GIRAFFE));
		board1.get(1).add(new Animal(Color.RED, Species.LION));
		board1.get(2).add(new Animal(Color.BLUE, Species.LION));
		board1.get(2).add(new Animal(Color.BLUE, Species.HIPPO));
		board1.get(2).add(new Animal(Color.YELLOW, Species.HIPPO));
		board1.get(2).add(new Animal(Color.YELLOW, Species.CAMEL));
		board1.get(3).add(new Animal(Color.GREEN, Species.HIPPO));
		board1.get(3).add(new Animal(Color.RED, Species.HIPPO));
		board1.get(3).add(new Animal(Color.RED, Species.CAMEL));
		board1.get(3).add(new Animal(Color.GREEN, Species.CAMEL));

		System.out.println();
		BoardState bs = new BoardState(board1, null);
		Statistics.printBoardStats(bs.board);
		printRating(bs);
		System.out.println();

		ArrayList<ArrayList<Animal>> board = new ArrayList<ArrayList<Animal>>();
		for (int i = 0; i < 4; i++) {
			board.add(new ArrayList<Animal>());
		}

		board.get(0).add(new Animal(Color.YELLOW, Species.CAMEL));
		board.get(0).add(new Animal(Color.YELLOW, Species.GIRAFFE));
		board.get(0).add(new Animal(Color.RED, Species.GIRAFFE));
		board.get(0).add(new Animal(Color.RED, Species.LION));
		board.get(1).add(new Animal(Color.GREEN, Species.GIRAFFE));
		board.get(1).add(new Animal(Color.YELLOW, Species.LION));
		board.get(1).add(new Animal(Color.GREEN, Species.CAMEL));
		board.get(1).add(new Animal(Color.YELLOW, Species.HIPPO));
		board.get(2).add(new Animal(Color.RED, Species.CAMEL));
		board.get(2).add(new Animal(Color.BLUE, Species.GIRAFFE));
		board.get(2).add(new Animal(Color.BLUE, Species.CAMEL));
		board.get(2).add(new Animal(Color.BLUE, Species.HIPPO));
		board.get(3).add(new Animal(Color.RED, Species.HIPPO));
		board.get(3).add(new Animal(Color.GREEN, Species.HIPPO));
		board.get(3).add(new Animal(Color.GREEN, Species.LION));
		board.get(3).add(new Animal(Color.BLUE, Species.LION));

		System.out.println();
		BoardState bs1 = new BoardState(board, null);
		Statistics.printBoardStats(bs1.board);
		printRating(bs1);
		System.out.println();

	}

	private static boolean solvable(BoardState bs) {
		bs.findAllSolutions(new ArrayList<ArrayList<Animal>>());
		if (BoardState.solutions != 0)
			return true;
		return false;
	}

	public static void printRating(BoardState bs) {
		if (solvable(bs)) {
//			double dsratio = BoardState.getdsratio();
			double treeRatio = (double) BoardState.treeFails / BoardState.treeSols;
			printRating(treeRatio);
			BoardState.resetStats();
		} else {
			System.out.println("No solutions in this board!");
		}
	}

	public static void printRating(double treeRatio) {
		double expectedNumberOfTries = expectedTries(treeRatio);
		System.out.println(expectedNumberOfTries);
		if (expectedNumberOfTries < EASY) {
			System.out.println("This problem is Easy");
		} else if (expectedNumberOfTries < MEDIUM) {
			System.out.println("This problem is Medium");
		} else if (expectedNumberOfTries < HARD) {
			System.out.println("This problem is Hard");
		} else if (expectedNumberOfTries < EXPERT) {
			System.out.println("This problem is Expert");
		} else if (expectedNumberOfTries < DEATH) {
			System.out.println("This problem is BEYOND EXPERT!");
		} else {
			System.out.println("This problem should not be attempted...");
		}
	}

	public static double expectedTries(double ratio) {
		//return 0.018756981036094 * ratio - 0.37667264960243;  if using dsratio
		return ratio * 0.48101854517586 + 0.9067095121779;
	}

}
