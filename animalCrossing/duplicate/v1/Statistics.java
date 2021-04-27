package animalCrossing.duplicate.v1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import animalCrossing.duplicate.v1.Animal.Color;
import animalCrossing.duplicate.v1.Animal.Species;

public class Statistics {
	public static int duplicates = 0;
	public static ArrayList<ArrayList<LinkedList<Animal>>> boards;
	public static final Color[] colors = Color.values();
	public static final Species[] species = Species.values();
	public static LinkedList<Animal> animals = new LinkedList<>();

	public static void main(String[] args) {
		boards = new ArrayList<ArrayList<LinkedList<Animal>>>();
		createAnimals(3);
		for (int i = 0; i < 1000; i++) {
			createRandomGame(3);
		}
		analyze(3, 1000);
		
		boards.clear();
		duplicates = 0;
		createAnimals(4);
		for (int i = 0; i < 1000; i++) {
			createRandomGame(4);
		}
		analyze(4, 1000);

		duplicates = 0;
		boards.clear();
		createAnimals(5);
		for (int i = 0; i < 1000; i++) {
			createRandomGame(5);
		}
		analyze(5, 1000);

		duplicates = 0;
		boards.clear();
		createAnimals(6);
		for (int i = 0; i < 1000; i++) {
			createRandomGame(6);
		}
		analyze(6, 1000);
		
		duplicates = 0;
		boards.clear();
		createAnimals(2);
		for (int i = 0; i < 24; i++) {
			createRandomGame(2);
		}
		analyze(2, 24);
	}

	private static void analyze(int s, int many) {
		for (int i = 0; i<boards.size(); i++) {
			for (int j = 0; j<boards.size(); j++) {
				// check this
				if (DuplicateBoards.isDuplicate(boards.get(i), boards.get(j), s)) {
					duplicates++;
					boards.remove(boards.get(j));
					j--;
				}
			}
		}
		System.out.println(s + " is size, d: "+(duplicates-many)/2);
		System.out.println("boards removed were: " + (many-boards.size()));
	}

	public static void createRandomGame(int boardSize) {
		ArrayList<LinkedList<Animal>> board = new ArrayList<>();
		for (int i = 0; i < boardSize; i++) {
			board.add(new LinkedList<Animal>());
		}
		populateBoard(board, animals, boardSize);
		boards.add(board);
	}

	public static void populateBoard(ArrayList<LinkedList<Animal>> board,
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
