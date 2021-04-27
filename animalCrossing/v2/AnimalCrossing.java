package animalCrossing.v2;

import java.util.ArrayList;
import java.util.Scanner;

import animalCrossing.v2.Animal.Color;
import animalCrossing.v2.Animal.Species;

public class AnimalCrossing {
	// ArrayList<Animal> a list of animals
	// ArrayList<ArrayList<Animal>> is a representation of the board

	// rc rl bh bc
	// gc rg gl yl 
	// rh yc bg gg 
	// yg bl yh gh is an example problem

	static int board_size;
	static ArrayList<ArrayList<Animal>> board = new ArrayList<>(board_size);
	static ArrayList<Animal> nextAnimalsInRows = new ArrayList<>(board_size);
	static ArrayList<Animal> pickedAnimals = new ArrayList<>(board_size
			* board_size);
	static Animal doneRow = new Animal(Color.DONE, Species.DONE);
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.print("Enter the size of the board: ");
		board_size = scan.nextInt();
		scan.nextLine();
		board = createBoard(board_size);
		printBoard();
		solve(board);
	}

	public static void solve(ArrayList<ArrayList<Animal>> board) {
		if (isSolution(board, null, 0))
			printSolution();
		else
			System.out.println("No solution found!");
	}

	private static void printSolution() {
		System.out.println("\nSolution:");
		for (Animal a : pickedAnimals) {
			System.out.print(a + "->");
		}
		System.out.println("end");
	}

	private static boolean isSolution(ArrayList<ArrayList<Animal>> board,
			Animal animalToRemove, int row) {
		
		if (animalToRemove != null) {
			pickedAnimals.add(animalToRemove);
		}
		board.get(row).remove(animalToRemove);
		System.out.println(board);
		nextAnimalsInRows = setNextAnimalsInRow(board);

		if (pickedAnimals.size() == board_size * board_size) {
			return true;
		}

		for (int i = 0; i < board_size; i++) {
			if (nextAnimalsInRows.get(i).isMatch(animalToRemove)
					&& isSolution(board, nextAnimalsInRows.get(i), i)) {
				return true;
			}
		}
		
		pickedAnimals.remove(animalToRemove);
		System.out.println(board + " failed");
		return false;
	}

	public static void printBoard() {
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				System.out.printf("%-15s| ", board.get(i).get(j));
			}
			System.out.println();
		}
	}

	private static ArrayList<ArrayList<Animal>> createBoard(int size) {
		ArrayList<ArrayList<Animal>> b = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			ArrayList<Animal> row = new ArrayList<Animal>(size);
			// note: all input as single letters
			for (int j = 0; j < size; j++) {
				System.out.println("Row " + (i + 1) + ", Col " + (j + 1));
				System.out.print("Enter color: ");
				String color = scan.nextLine();
				System.out.print("Enter species: ");
				String species = scan.nextLine();
				row.add(j, Animal.returnAnimal(color, species));
			}
			row.add(doneRow);
			b.add(row);
		}
		return b;
	}

	private static ArrayList<Animal> setNextAnimalsInRow(
			ArrayList<ArrayList<Animal>> board) {
		ArrayList<Animal> nextAnimals = new ArrayList<>(board_size);
		for (int i = 0; i < board.size(); i++) {
			nextAnimals.add(i, board.get(i).get(0));
		}
		return nextAnimals;
	}
}