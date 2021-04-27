package animalCrossing.statistics.v1;
// after v3c
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import animalCrossing.statistics.v1.Animal.Color;
import animalCrossing.statistics.v1.Animal.Species;

public class Statistics {
	public static int gamesWon;
	public static int gamesLost;
	public static final Color[] colors = Color.values();
	public static final Species[] species = Species.values();
	public static LinkedList<Animal> animals = new LinkedList<>();

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		// 100% chance of winning
		for (int i = 0; i < 24; i++) {
			createRandomGame(2);
		}
		printStatistics(2, start);

		gamesLost = 0;
		gamesWon = 0;
		start = System.currentTimeMillis();
		// ~90% chance of winning
		for (int i = 0; i < 1; i++) {
			createRandomGame(3);
		}
		printStatistics(3, start);

		gamesLost = 0;
		gamesWon = 0;
		start = System.currentTimeMillis();
		// does 100000 in 9 seconds
		// about 80% winning
		for (int i = 0; i < 100000; i++) {
			createRandomGame(4);
		}
		printStatistics(4, start);

		gamesLost = 0;
		gamesWon = 0;
		start = System.currentTimeMillis();

		// does 10000 in 37 sec
		// a couple percent less winning (about 78%)
		for (int i = 0; i < 1; i++) {
			createRandomGame(5);
		}
		printStatistics(5, start);

		gamesLost = 0;
		gamesWon = 0;
		start = System.currentTimeMillis();

		// even less chance of winning, 
		// ~30 sec for 100 solutions
		for (int i = 0; i < 1; i++) {
			createRandomGame(6);
		}
		printStatistics(6, start);

	}

	private static void printStatistics(int boardSize, long start) {
		System.out.println("Board size: " + boardSize);
		System.out.println("Time is: " + (System.currentTimeMillis() - start));
		System.out.println("Games won: " + gamesWon);
		System.out.println("Games lost: " + gamesLost);
		float divisor = (gamesLost + gamesWon);
		float percent = (gamesWon * 100) / divisor;
		System.out.println("Percentage of wins: " + percent + "%\n");
	}

	private static void createRandomGame(int boardSize) {
		animals = createAnimals(colors, species, boardSize);
		ArrayList<ArrayList<Animal>> board = new ArrayList<>();
		for (int i = 0; i < boardSize; i++) {
			board.add(new ArrayList<Animal>());
		}
		addAnimals(board, animals, boardSize);
		BoardState bs = new BoardState(board, null);

		if (bs.findASolution(bs)) {
			gamesWon++;
		} else {
			gamesLost++;
		}
	}

	private static void addAnimals(ArrayList<ArrayList<Animal>> board,
			LinkedList<Animal> animals, int board_size) {
		Random random = new Random();
		for (int row = 0; row < board_size; row++) {
			for (int column = 0; column < board_size; column++) {
				int animalIndex = random.nextInt(animals.size());
				board.get(row).add(animals.get(animalIndex));
				animals.remove(animals.get(animalIndex));
			}
		}
	}

	private static LinkedList<Animal> createAnimals(Color[] colors,
			Species[] species, int boardSize) {
		LinkedList<Animal> animals = new LinkedList<>();
		for (int sIndex = 0; sIndex < boardSize; sIndex++) {
			for (int cIndex = 0; cIndex < boardSize; cIndex++) {
				animals.add(new Animal(colors[cIndex], species[sIndex]));
			}
		}
		return animals;
	}
}
