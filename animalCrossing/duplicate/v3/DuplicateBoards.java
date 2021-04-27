package animalCrossing.duplicate.v3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import animalCrossing.duplicate.v3.BoardState;
import animalCrossing.duplicate.v3.Animal;

public class DuplicateBoards {
	static final Animal.Color[] colors = Animal.Color.values();
	static final Animal.Species[] species = Animal.Species.values();

	public static void main(String[] args) {
		ArrayList<LinkedList<Animal>> board1 = new ArrayList<>(3);
		ArrayList<LinkedList<Animal>> board2 = new ArrayList<>(3);
		ArrayList<LinkedList<Animal>> board3 = new ArrayList<>(3);
		for (int i = 0; i < 3; i++) {
			board1.add(new LinkedList<Animal>());
			board2.add(new LinkedList<Animal>());
			board3.add(new LinkedList<Animal>());
		}
		board1.get(0).add(new Animal(Animal.Color.RED, Animal.Species.LION));
		board1.get(0).add(new Animal(Animal.Color.YELLOW, Animal.Species.HIPPO));
		board1.get(0).add(new Animal(Animal.Color.BLUE, Animal.Species.CAMEL));
		board1.get(1).add(new Animal(Animal.Color.RED, Animal.Species.HIPPO));
		board1.get(1).add(new Animal(Animal.Color.YELLOW, Animal.Species.CAMEL));
		board1.get(1).add(new Animal(Animal.Color.BLUE, Animal.Species.LION));
		board1.get(2).add(new Animal(Animal.Color.RED, Animal.Species.CAMEL));
		board1.get(2).add(new Animal(Animal.Color.YELLOW, Animal.Species.LION));
		board1.get(2).add(new Animal(Animal.Color.BLUE, Animal.Species.HIPPO));

		board2.get(0).add(new Animal(Animal.Color.YELLOW, Animal.Species.LION));
		board2.get(0).add(new Animal(Animal.Color.RED, Animal.Species.CAMEL));
		board2.get(0).add(new Animal(Animal.Color.BLUE, Animal.Species.HIPPO));
		board2.get(1).add(new Animal(Animal.Color.YELLOW, Animal.Species.CAMEL));
		board2.get(1).add(new Animal(Animal.Color.RED, Animal.Species.HIPPO));
		board2.get(1).add(new Animal(Animal.Color.BLUE, Animal.Species.LION));
		board2.get(2).add(new Animal(Animal.Color.YELLOW, Animal.Species.HIPPO));
		board2.get(2).add(new Animal(Animal.Color.RED, Animal.Species.LION));
		board2.get(2).add(new Animal(Animal.Color.BLUE, Animal.Species.CAMEL));
		
		// RED,   YELLOW, BLUE, 
		// HIPPO, CAMEL,  LION, 
		
		board3.get(0).add(new Animal(Animal.Color.BLUE, Animal.Species.CAMEL));
		board3.get(0).add(new Animal(Animal.Color.YELLOW, Animal.Species.HIPPO));
		board3.get(0).add(new Animal(Animal.Color.RED, Animal.Species.LION));
		board3.get(1).add(new Animal(Animal.Color.YELLOW, Animal.Species.CAMEL));
		board3.get(1).add(new Animal(Animal.Color.RED, Animal.Species.HIPPO));
		board3.get(1).add(new Animal(Animal.Color.BLUE, Animal.Species.LION));
		board3.get(2).add(new Animal(Animal.Color.RED, Animal.Species.CAMEL));
		board3.get(2).add(new Animal(Animal.Color.BLUE, Animal.Species.HIPPO));
		board3.get(2).add(new Animal(Animal.Color.YELLOW, Animal.Species.LION));
		
		System.out.println(isDuplicate2(board1, board3, 3));
		System.out.println(isDuplicate2b(board1, board3, 3));
		System.out.println(isDuplicate2b(board2, board3, 3));
		System.out.println(isDuplicate3(board1, board3));
		System.out.println(isDuplicate4(board1, board3));
	}

	static boolean isDuplicate2(ArrayList<LinkedList<Animal>> board1, ArrayList<LinkedList<Animal>> board2,
			int boardSize) {
		board1 = standardizeBoard(board1, boardSize);
		ArrayList<ArrayList<LinkedList<Animal>>> board2RowPermutations = permuteRows(board2);

		for (ArrayList<LinkedList<Animal>> board : board2RowPermutations) {
			ArrayList<LinkedList<Animal>> standardBoard = standardizeBoard(board, boardSize);
			if (isExactDuplicate(board1, standardBoard, boardSize)) {
				return true;
			} else if (hasTypesSwitched(board1, standardBoard, boardSize)) {
				return true;
			}
		}

		return false;
	}

	private static boolean hasTypesSwitched(ArrayList<LinkedList<Animal>> board1,
			ArrayList<LinkedList<Animal>> standardBoard, int boardSize) {
		for (int i = 0; i < boardSize; i++)
			for (int j = 0; j < boardSize; j++)
				if (!((board1.get(i).get(j).color.ordinal() == standardBoard.get(i).get(j).species.ordinal()) && board1
						.get(i).get(j).species.ordinal() == standardBoard.get(i).get(j).color.ordinal()))
					return false;
		return true;
	}

	public static boolean isExactDuplicate(ArrayList<LinkedList<Animal>> board1,
			ArrayList<LinkedList<Animal>> board2, int boardSize) {
		for (int i = 0; i < boardSize; i++)
			for (int j = 0; j < boardSize; j++)
				if (!board1.get(i).get(j).equals(board2.get(i).get(j)))
					return false;
		return true;
	}

	static ArrayList<LinkedList<Animal>> standardizeBoard(ArrayList<LinkedList<Animal>> board, int boardSize) {
		LinkedList<Animal.Color> tempC = new LinkedList<>();
		LinkedList<Animal.Species> tempS = new LinkedList<>();
		for (int i = 0; i < boardSize; i++) {
			tempC.add(i, colors[i]);
			tempS.add(i, species[i]);
		}

		HashMap<Animal.Color, Animal.Color> colorMap = new HashMap<>();
		HashMap<Animal.Species, Animal.Species> speciesMap = new HashMap<>();

		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (tempC.isEmpty() && tempS.isEmpty())
					continue;

				if (!colorMap.containsKey(board.get(i).get(j).color))
					colorMap.put(board.get(i).get(j).color, tempC.getFirst());
				if (!speciesMap.containsKey(board.get(i).get(j).species))
					speciesMap.put(board.get(i).get(j).species, tempS.getFirst());

				if (!tempC.isEmpty())
					tempC.removeFirst();
				if (!tempS.isEmpty())
					tempS.removeFirst();
			}
		}
		ArrayList<LinkedList<Animal>> sBoard = switchAllColorsandSpecies(board, colorMap, speciesMap, boardSize);
		return sBoard;
	}

	static ArrayList<LinkedList<Animal>> switchAllColorsandSpecies(ArrayList<LinkedList<Animal>> oldBoard,
			HashMap<Animal.Color, Animal.Color> colorMap, HashMap<Animal.Species, Animal.Species> speciesMap,
			int boardSize) {
		ArrayList<LinkedList<Animal>> newBoard = new ArrayList<LinkedList<Animal>>(boardSize);
		for (int i = 0; i < boardSize; i++) {
			newBoard.add(new LinkedList<Animal>());
			for (int j = 0; j < boardSize; j++) {
				Animal old = oldBoard.get(i).get(j);
				newBoard.get(i).add(new Animal(colorMap.get(old.color), speciesMap.get(old.species)));
			}
		}
		return newBoard;
	}

	static boolean isDuplicate2b(ArrayList<LinkedList<Animal>> board1, ArrayList<LinkedList<Animal>> board2,
			int boardSize) {
		board1 = standardizeBoard(board1, boardSize);
		ArrayList<ArrayList<LinkedList<Animal>>> board2RowPermutations = permuteRows(board2);

		for (ArrayList<LinkedList<Animal>> board : board2RowPermutations) {
			ArrayList<LinkedList<Animal>> standardBoard = standardizeBoard(board, boardSize);
			if (isExactDuplicate(board1, standardBoard, boardSize))
				return true;
			ArrayList<LinkedList<Animal>> switchBoard = switchTypes(board, boardSize);
			switchBoard = standardizeBoard(switchBoard, boardSize);
			if (isExactDuplicate(board1, switchBoard, boardSize))
				return true;
		}

		return false;
	}

	private static ArrayList<LinkedList<Animal>> switchTypes(ArrayList<LinkedList<Animal>> board, int boardSize) {
		ArrayList<LinkedList<Animal>> newBoard = new ArrayList<LinkedList<Animal>>();
		for (int i = 0; i < boardSize; i++) {
			newBoard.add(new LinkedList<Animal>());
			for (int j = 0; j < boardSize; j++) {
				Animal.Species species = board.get(i).get(j).species;
				Animal.Color color = board.get(i).get(j).color;
				species = Animal.Species.values()[color.ordinal()];
				color = Animal.Color.values()[species.ordinal()];
				newBoard.get(i).add(j, new Animal(color, species));
			}
		}
		return newBoard;
	}

	static boolean isDuplicate3(ArrayList<LinkedList<Animal>> board1, ArrayList<LinkedList<Animal>> board2) {
		BoardState bs = new BoardState(board1, null);
		ArrayList<ArrayList<Integer>> pathways = bs.findSolutionRows(bs);
		ArrayList<ArrayList<LinkedList<Animal>>> board2RowPermutations = permuteRows(board2);
		for (ArrayList<LinkedList<Animal>> board : board2RowPermutations) {
			bs = new BoardState(board, null);
			if (pathways.equals(bs.findSolutionRows(bs)))
				return true;
		}
		return false;
	}

	static boolean isDuplicate4(ArrayList<LinkedList<Animal>> board1, ArrayList<LinkedList<Animal>> board2) {
		BoardState bs = new BoardState(board1, null);
		ArrayList<ArrayList<Integer>> pathways = bs.findPathwayRows(bs);
		ArrayList<ArrayList<LinkedList<Animal>>> board2RowPermutations = permuteRows(board2);
		for (ArrayList<LinkedList<Animal>> board : board2RowPermutations) {
			bs = new BoardState(board, null);
			if (pathways.equals(bs.findPathwayRows(bs)))
				return true;
		}

		return false;
	}

	// https://stackoverflow.com/questions/4240080/generating-all-permutations-of-a-given-string
	static void permuteRows(ArrayList<LinkedList<Animal>> board2, int k,
			ArrayList<ArrayList<LinkedList<Animal>>> permutationOfRows) {
		if (k == board2.size()) {
			permutationOfRows.add(copy(board2));
		} else {
			for (int i = k; i < board2.size(); i++) {
				LinkedList<Animal> temp = board2.get(k);
				board2.set(k, board2.get(i));
				board2.set(i, temp);

				permuteRows(board2, k + 1, permutationOfRows);

				temp = board2.get(k);
				board2.set(k, board2.get(i));
				board2.set(i, temp);
			}
		}
	}

	static ArrayList<ArrayList<LinkedList<Animal>>> permuteRows(ArrayList<LinkedList<Animal>> board2) {
		ArrayList<ArrayList<LinkedList<Animal>>> permutationOfRows = new ArrayList<ArrayList<LinkedList<Animal>>>();
		permuteRows(board2, 0, permutationOfRows);
		return permutationOfRows;
	}

	private static ArrayList<LinkedList<Animal>> copy(ArrayList<LinkedList<Animal>> board) {
		ArrayList<LinkedList<Animal>> boardCopy = new ArrayList<>();
		for (LinkedList<Animal> row : board) {
			LinkedList<Animal> copyRow = new LinkedList<Animal>();
			for (Animal a : row)
				copyRow.add(a);
			boardCopy.add(copyRow);
		}
		return boardCopy;
	}
}