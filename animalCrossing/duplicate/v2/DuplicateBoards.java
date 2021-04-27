package animalCrossing.duplicate.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import animalCrossing.duplicate.v2.Animal.Color;
import animalCrossing.duplicate.v2.Animal.Species;
import animalCrossing.duplicate.v2.BoardState;
import animalCrossing.duplicate.v2.Animal;

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

		System.out.println(isDuplicate2(board1, board2, 3));
		System.out.println(isDuplicate3(board1, board2, 3));
	}

	static boolean isDuplicate2(ArrayList<LinkedList<Animal>> board1, ArrayList<LinkedList<Animal>> board2,
			int boardSize) {
		board1 = standardizeBoard(board1, boardSize);
		ArrayList<ArrayList<LinkedList<Animal>>> board2RowPermutations = new ArrayList<>(); 
		permuteRows(board2, 0, board2RowPermutations);
		
		for (ArrayList<LinkedList<Animal>> board : board2RowPermutations) {
			ArrayList<LinkedList<Animal>> standardBoard = standardizeBoard(board, boardSize);
			if (isExactDuplicate(board1, standardBoard, boardSize)) {
				return true;
			}
		}
		
		return false;
	}

	public static boolean isExactDuplicate(ArrayList<LinkedList<Animal>> board1,
			ArrayList<LinkedList<Animal>> board2, int boardSize) {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (!board1.get(i).get(j).equals(board2.get(i).get(j))) {
					return false;
				}
			}
		}

		return true;
	}

	static ArrayList<LinkedList<Animal>> standardizeBoard(ArrayList<LinkedList<Animal>> board, int boardSize) {
		LinkedList<Color> tempC = new LinkedList<>();
		LinkedList<Species> tempS = new LinkedList<>();
		for (int i = 0; i < boardSize; i++) {
			tempC.add(i, colors[i]);
			tempS.add(i, species[i]);
		}

		HashMap<Color, Color> colorMap = new HashMap<>();
		HashMap<Species, Species> speciesMap = new HashMap<>();

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
		board = switchAllColorsandSpecies(board, colorMap, speciesMap, boardSize);
		return board;
	}

	static ArrayList<LinkedList<Animal>> switchAllColorsandSpecies(ArrayList<LinkedList<Animal>> oldBoard,
			HashMap<Color, Color> colorMap, HashMap<Species, Species> speciesMap, int boardSize) {
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

	static boolean isDuplicate3(ArrayList<LinkedList<Animal>> board1, ArrayList<LinkedList<Animal>> board2,
			int boardSize) {
		BoardState bs = new BoardState(board1, null);
		bs.findSolutionRows(bs, new ArrayList<Integer>());
		ArrayList<Integer> solutionRows1 = bs.solutionAccumulator;

		ArrayList<ArrayList<LinkedList<Animal>>> board2RowPermutations = new ArrayList<>(); 
		permuteRows(board2, 0, board2RowPermutations);
		for (ArrayList<LinkedList<Animal>> board : board2RowPermutations) {
			bs = new BoardState(board, null);
			bs.findSolutionRows(bs, new ArrayList<Integer>());
			if (bs.solutionAccumulator.equals(solutionRows1)) {
				return true;
			}
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

	private static ArrayList<LinkedList<Animal>> copy(ArrayList<LinkedList<Animal>> board2) {
		ArrayList<LinkedList<Animal>> boardCopy = new ArrayList<>();
		for (LinkedList<Animal> row : board2) {
			LinkedList<Animal> copyRow = new LinkedList<Animal>();
			for (Animal a : row)
				copyRow.add(a);
			boardCopy.add(copyRow);
		}
		return boardCopy;
	}
}