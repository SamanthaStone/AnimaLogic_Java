package animalCrossing.rating.v2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
 import animalCrossing.rating.v2.Animal;

public class Statistics {
	public static int gamesWon = 0;
	public static int gamesLost = 0;

	public static final Animal.Color[] colors = Animal.Color.values();
	public static final Animal.Species[] species = Animal.Species.values();
	public static LinkedList<Animal> animals = new LinkedList<>();

	public static void main(String[] args) {

		long start = System.currentTimeMillis();

		createAnimals(4);

//		createAndWrite(16); // puts down ~20
		ArrayList<ArrayList<ArrayList<Animal>>> boardStates = readBoards(13);
		for (ArrayList<ArrayList<Animal>> board : boardStates) {
			for (ArrayList<Animal> row : board) {
				System.out.println(row);
			}
			printBoardStats(board);
			System.out.println();
		}
		printStatistics(4, start);
	}

	static void printBoardStats(ArrayList<ArrayList<Animal>> board) {
		BoardState bs = new BoardState(board, null);
		ArrayList<ArrayList<Animal>> allSolutions = new ArrayList<ArrayList<Animal>>();
		bs.findAllSolutions(allSolutions);
		BoardState.printStatistics();
		BoardState.resetStats();
	}

	@SuppressWarnings("unused")
	private static void createAndWrite(int numberToWrite) {
		try {
			FileOutputStream fos = new FileOutputStream("boardsv2");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			for (int i = 0; i < numberToWrite; i++) {
				ArrayList<ArrayList<Animal>> board = new ArrayList<>();
				for (int j = 0; j < 4; j++) {
					board.add(new ArrayList<Animal>());
				}
				populateBoard(board, animals, 4);
				BoardState bs = new BoardState(board, null);
				ArrayList<ArrayList<Animal>> allSolutions = new ArrayList<ArrayList<Animal>>();
				bs.findAllSolutions(allSolutions);
				if (allSolutions.size() != 0) {
					oos.writeObject(board);
				}
				board.clear();
			}
			oos.close();
			fos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static ArrayList<ArrayList<ArrayList<Animal>>> readBoards(int numberToRead) {
		ArrayList<ArrayList<ArrayList<Animal>>> boards = new ArrayList<>();
		int i = 0;
		try {
			FileInputStream fos = new FileInputStream("boardsv2");
			ObjectInputStream oos = new ObjectInputStream(fos);
			while (fos.available() > 0 && i < numberToRead) {
				@SuppressWarnings("unchecked")
				ArrayList<ArrayList<Animal>> bs = (ArrayList<ArrayList<Animal>>) oos.readObject();
				boards.add(bs);
				i++;
			}
			oos.close();
			fos.close();
		} catch (IOException | ClassNotFoundException ioe) {
			ioe.printStackTrace();
		}
		System.out.println(i);
		return boards;
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

	public static void createAndSolveRandomGame(int boardSize, ArrayList<ArrayList<Animal>> board) {
		for (int i = 0; i < boardSize; i++) {
			board.add(new ArrayList<Animal>());
		}
		populateBoard(board, animals, boardSize);

		BoardState des = new BoardState(board, null);
		ArrayList<ArrayList<Animal>> allSolutions = new ArrayList<ArrayList<Animal>>();
		des.findAllSolutions(allSolutions);
		if (allSolutions.size() != 0) {
			gamesWon++;
		} else {
			gamesLost++;
		}
	}

	public static void populateBoard(ArrayList<ArrayList<Animal>> board, LinkedList<Animal> animals, int board_size) {
		Collections.shuffle(animals);
		int index = 0;
		for (int row = 0; row < board_size; row++) {
			for (int column = 0; column < board_size; column++) {
				board.get(row).add(column, animals.get(index));
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
