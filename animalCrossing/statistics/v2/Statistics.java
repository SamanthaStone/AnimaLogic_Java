package animalCrossing.statistics.v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import animalCrossing.statistics.v2.Animal.Color;
import animalCrossing.statistics.v2.Animal.Species;

public class Statistics {
	public static int gamesWon = 0;
	public static int gamesLost = 0;

	public static final Color[] colors = Color.values();
	public static final Species[] species = Species.values();
	public static LinkedList<Animal> animals = new LinkedList<>();

	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		// ~90% chance of winning
		createAnimals(3);
		for (int i = 0; i < 5000000; i++) {
			createAndSolveRandomGame(3);
		}
		printStatistics(3, start);

		gamesLost = 0;
		gamesWon = 0;
		start = System.currentTimeMillis();
		// ~80% winning
		createAnimals(4);
		for (int i = 0; i < 500000; i++) {
			createAndSolveRandomGame(4);
		}
		printStatistics(4, start);

		gamesLost = 0;
		gamesWon = 0;
		start = System.currentTimeMillis();

		// a couple percent less (~77%)
		// 77.094, 77.058, 77.436, 77.132, 77.212, 77.296, 77.024
		createAnimals(5);
		for (int i = 0; i < 10000; i++) {
			createAndSolveRandomGame(5);
		}
		printStatistics(5, start);

		gamesLost = 0;
		gamesWon = 0;
		start = System.currentTimeMillis();

		// hovering around at ~77.5, oddly enough
		// 75.8, 78.6, 76.5, 78.3, 79.4, 77.2, 76.3, 78.9, 78.6, 75.6,
		// 76.0, 77.8, 79.7, 78.6, 75.9, 77.0, 77.4, 77.0, 77.7, 78.7
		createAnimals(6);
		for (int i = 0; i < 100; i++) {
			createAndSolveRandomGame(6);
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

	public static void createAndSolveRandomGame(int boardSize) {
		ArrayList<ArrayList<Animal>> board = new ArrayList<>();
		for (int i = 0; i < boardSize; i++) {
			board.add(new ArrayList<Animal>());
		}
		populateBoard(board, animals, boardSize);

		BoardState des = new BoardState(board, null);
		boolean solve = des.findASolution();
		if (solve) {
			gamesWon++;
		} else {
			gamesLost++;
		}
	}

	public static void populateBoard(ArrayList<ArrayList<Animal>> board,
			LinkedList<Animal> animals, int board_size) {
		Collections.shuffle(animals);
		int index = 0;
		for (int row = 0; row < board_size; row++) {
			for (int column = 0; column < board_size; column++) {
				board.get(row).add(animals.get(index));
				index++;
			}
		}
	}

	public static void createAnimals(int boardSize) {
		animals.clear();
		for (int sIndex = 0; sIndex < boardSize; sIndex++)
			for (int cIndex = 0; cIndex < boardSize; cIndex++)
				animals.add(new Animal(colors[cIndex], species[sIndex]));
	}
}
