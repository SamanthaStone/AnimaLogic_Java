package animalCrossing.v3b;

import java.util.ArrayList;
import java.util.Scanner;

import animalCrossing.v3b.Animal.Color;
import animalCrossing.v3b.Animal.Species;

public class AnimalCrossing {
	// ArrayList<Animal> a list of animals
	// ArrayList<ArrayList<Animal>> is a representation of the board

	static int board_size;
	static Animal doneRow = new Animal(Color.DONE, Species.DONE);
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.print("Enter the size of the board: ");
		board_size = scan.nextInt();
		scan.nextLine();
		ArrayList<ArrayList<Animal>> board = createBoard(board_size);
		printBoard(board);
		
		BoardState bs = new BoardState(board, null);		
		ArrayList<ArrayList<Animal>> arr = bs.findAllSolutions(bs);
		System.out.println("\nSolutions: " + arr.size());
		for (ArrayList<Animal> ar2 : arr) {
			System.out.println(ar2);
		}
	}

	public static void printBoard(ArrayList<ArrayList<Animal>> board) {
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
}