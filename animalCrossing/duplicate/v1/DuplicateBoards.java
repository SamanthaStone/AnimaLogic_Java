package animalCrossing.duplicate.v1;

import java.util.ArrayList;
import java.util.LinkedList;

import animalCrossing.duplicate.v1.Animal.Color;
import animalCrossing.duplicate.v1.Animal.Species;

public class DuplicateBoards {
	static final Color[] colors = Color.values();
	static final Species[] species = Species.values();

	public static void main(String[] args) {
		ArrayList<LinkedList<Animal>> board1 = new ArrayList<>(3);
		ArrayList<LinkedList<Animal>> board2 = new ArrayList<>(3);
		for (int i = 0; i < 3; i++) {
			board1.add(new LinkedList<Animal>());
			board2.add(new LinkedList<Animal>());
		}
		board1.get(0).add(new Animal(Color.RED, Species.LION));
		board1.get(0).add(new Animal(Color.YELLOW, Species.HIPPO));
		board1.get(0).add(new Animal(Color.BLUE, Species.CAMEL));
		board1.get(1).add(new Animal(Color.RED, Species.HIPPO));
		board1.get(1).add(new Animal(Color.YELLOW, Species.CAMEL));
		board1.get(1).add(new Animal(Color.BLUE, Species.LION));
		board1.get(2).add(new Animal(Color.RED, Species.CAMEL));
		board1.get(2).add(new Animal(Color.YELLOW, Species.LION));
		board1.get(2).add(new Animal(Color.BLUE, Species.HIPPO));
		board2.get(0).add(new Animal(Color.YELLOW, Species.LION));
		board2.get(0).add(new Animal(Color.RED, Species.CAMEL));
		board2.get(0).add(new Animal(Color.BLUE, Species.HIPPO));
		board2.get(1).add(new Animal(Color.YELLOW, Species.CAMEL));
		board2.get(1).add(new Animal(Color.RED, Species.HIPPO));
		board2.get(1).add(new Animal(Color.BLUE, Species.LION));
		board2.get(2).add(new Animal(Color.YELLOW, Species.HIPPO));
		board2.get(2).add(new Animal(Color.RED, Species.LION));
		board2.get(2).add(new Animal(Color.BLUE, Species.CAMEL));

		System.out.println(isDuplicate(board1, board2, 3));
		System.out.println(isDuplicate2(board1, board2, 3));
	}

	static boolean isDuplicate(ArrayList<LinkedList<Animal>> board1, ArrayList<LinkedList<Animal>> board2,
			int boardSize) {
		boolean[][][] color1 = new boolean[boardSize][boardSize][boardSize];
		boolean[][][] color2 = new boolean[boardSize][boardSize][boardSize];
		boolean[][][] species1 = new boolean[boardSize][boardSize][boardSize];
		boolean[][][] species2 = new boolean[boardSize][boardSize][boardSize];

		// first layer is a specific color or species
		// next layers are rows and columns of the board
		// true if spot contains specific color/species
		for (int layerIndex = 0; layerIndex < boardSize; layerIndex++) {
			for (int i = 0; i < boardSize; i++) {
				for (int j = 0; j < boardSize; j++) {
					color1[layerIndex][i][j] = board1.get(i).get(j).color == colors[layerIndex];
					color2[layerIndex][i][j] = board2.get(i).get(j).color == colors[layerIndex];
					species1[layerIndex][i][j] = board1.get(i).get(j).species == species[layerIndex];
					species2[layerIndex][i][j] = board2.get(i).get(j).species == species[layerIndex];
				}
			}
		}

		int matches = 0;
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (isEqual(color1[i], color2[j]))
					matches++;
				if (isEqual(species1[i], species2[j]))
					matches++;
			}
		}

		return (matches == (boardSize * 2));
	}

	private static boolean isEqual(boolean[][] bs, boolean[][] bs2) {
		for (int i = 0; i < bs.length; i++)
			for (int j = 0; j < bs2.length; j++)
				if (bs[i][j] != bs2[i][j])
					return false;
		return true;
	}

	static boolean isDuplicate2(ArrayList<LinkedList<Animal>> board1, ArrayList<LinkedList<Animal>> board2,
			int boardSize) {

		board1 = standardizeBoard(board1, boardSize);
		board2 = standardizeBoard(board2, boardSize);

		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (!board1.get(i).get(j).equals(board2.get(i).get(j))) {
					return false;
				}
			}
		}

		return true;
	}

	private static ArrayList<LinkedList<Animal>> standardizeBoard(ArrayList<LinkedList<Animal>> board, int boardSize) {
		LinkedList<Color> tempC = new LinkedList<>();
		LinkedList<Species> tempS = new LinkedList<>();
		for (int i = 0; i < boardSize; i++) {
			tempC.add(i, colors[i]);
			tempS.add(i, species[i]);
		}
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (tempC.isEmpty() && tempS.isEmpty())
					continue;
				if (tempC.contains(board.get(i).get(j).color)) {
					board = switchColor(board, tempC.getFirst(), board.get(i).get(j).color);
					tempC.removeFirst();
				}
				if (tempS.contains(board.get(i).get(j).species)) {
					board = switchSpecies(board, tempS.getFirst(), board.get(i).get(j).species);
					tempS.removeFirst();
				}
			}
		}

		return board;
	}

	static ArrayList<LinkedList<Animal>> switchColor(ArrayList<LinkedList<Animal>> board, Color c1, Color c2) {
		if (c1 == c2)
			return board;
		for (LinkedList<Animal> linkList : board) {
			for (Animal animal : linkList) {
				if (animal.color == c1)
					animal.color = c2;
				else if (animal.color == c2)
					animal.color = c1;
			}
		}
		return board;
	}

	static ArrayList<LinkedList<Animal>> switchSpecies(ArrayList<LinkedList<Animal>> board, Species c1, Species c2) {
		if (c1 == c2)
			return board;
		for (LinkedList<Animal> linkList : board) {
			for (Animal animal : linkList) {
				if (animal.species == c1)
					animal.species = c2;
				else if (animal.species == c2)
					animal.species = c1;
			}
		}
		return board;
	}

}