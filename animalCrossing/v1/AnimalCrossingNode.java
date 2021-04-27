package animalCrossing.v1;

import java.util.ArrayList;
import java.util.Arrays;

public class AnimalCrossingNode {	
	// I made this for Mark Engelberg's AnimaLogic game
	
	// ArrayList<String> is an animal
	// ArrayList<ArrayList<String>> is a list of moves (combo)
	// ArrayList<ArrayList<ArrayList<String>>> is a list of combos

	// making all the arrays, red hippo, yellow hippo...
	// The weird format just creates the ArrayList with the color and type
	static final ArrayList<String> rh = new ArrayList<>(Arrays.asList("red", "hippo"));
	static final ArrayList<String> yh = new ArrayList<>(Arrays.asList("yellow", "hippo"));
	static final ArrayList<String> gh = new ArrayList<>(Arrays.asList("green", "hippo"));
	static final ArrayList<String> bh = new ArrayList<>(Arrays.asList("blue", "hippo"));
	static final ArrayList<String> rc = new ArrayList<>(Arrays.asList("red", "camel"));
	static final ArrayList<String> yc = new ArrayList<>(Arrays.asList("yellow", "camel"));
	static final ArrayList<String> gc = new ArrayList<>(Arrays.asList("green", "camel"));
	static final ArrayList<String> bc = new ArrayList<>(Arrays.asList("blue", "camel"));
	static final ArrayList<String> rl = new ArrayList<>(Arrays.asList("red", "lion"));
	static final ArrayList<String> yl = new ArrayList<>(Arrays.asList("yellow", "lion"));
	static final ArrayList<String> gl = new ArrayList<>(Arrays.asList("green", "lion"));
	static final ArrayList<String> bl = new ArrayList<>(Arrays.asList("blue", "lion"));
	static final ArrayList<String> rg = new ArrayList<>(Arrays.asList("red", "giraffe"));
	static final ArrayList<String> yg = new ArrayList<>(Arrays.asList("yellow", "giraffe"));
	static final ArrayList<String> gg = new ArrayList<>(Arrays.asList("green", "giraffe"));
	static final ArrayList<String> bg = new ArrayList<>(Arrays.asList("blue", "giraffe"));

	static ArrayList<ArrayList<String>> row1 = new ArrayList<>(4);
	static ArrayList<ArrayList<String>> row2 = new ArrayList<>(4);
	static ArrayList<ArrayList<String>> row3 = new ArrayList<>(4);
	static ArrayList<ArrayList<String>> row4 = new ArrayList<>(4);

	static ArrayList<ArrayList<String>> nextAnimalsInRows = new ArrayList<>(4);

	static ArrayList<ArrayList<String>> pickedAnimals = new ArrayList<>(16);
	static ArrayList<String> chosenAnimal = new ArrayList<String>(2);
	static int chosenNumber = 0;
	static ArrayList<String> lastAnimal;

	static ArrayList<String> doneRow = new ArrayList<>(Arrays.asList("row", "done"));

	static ArrayList<ArrayList<ArrayList<String>>> queue = new ArrayList<>(20);

	public static void main(String[] args) {
		inputMethod();
		resetRows();
		pickedAnimals.clear();

		chosenNumber = 0;
		setNextAnimalsInRow();

		chosenAnimal = row4.get(0);
		addComboToQueue();

		chosenAnimal = row3.get(0);
		addComboToQueue();

		chosenAnimal = row2.get(0);
		addComboToQueue();

		chosenAnimal = row1.get(0);
		addComboToQueue();

		chooseNextInQueue();
		checkForLogicalMoves();

		// TODO add user input for the number of iterations
		for (int i = 0; i < 100; i++) {
			resetRows();
			chooseNextInQueue();
			checkForLogicalMoves();
		}
		
	}

	// TODO add user input
	private static void inputMethod() {
		row1.add(yh); row1.add(rh); row1.add(gc); row1.add(yl);
		row2.add(rg); row2.add(yc); row2.add(yg); row2.add(bc);
		row3.add(gg); row3.add(rc); row3.add(rl); row3.add(gh);
		row4.add(bg); row4.add(bl); row4.add(gl); row4.add(bh);
	}

	private static void checkForLogicalMoves() {
		for (chosenNumber = 3; chosenNumber >= 0; chosenNumber--) {
			chosenAnimal = nextAnimalsInRows.get(chosenNumber);
			if (checkValidMove()) {
				addComboToQueue();
			}
		}
	}

	private static void resetRows() {
		row1.clear(); row2.clear(); row3.clear(); row4.clear();
		inputMethod();
		setNextAnimalsInRow();
	}

	private static void chooseNextInQueue() {
		queue.remove(pickedAnimals);
		resetRows();
		pickedAnimals = queue.get(queue.size() - 1);

		if (pickedAnimals.size() == 16) {
			System.out.println();
			System.out.println("Found a solution! Combo is: " + pickedAnimals);
			queue.remove(pickedAnimals);
			resetRows();
			pickedAnimals = queue.get(queue.size() - 1);
		}

		for (ArrayList<String> animal : pickedAnimals) {
			chosenAnimal = animal;
			setChosenAnimal();
			setNextAnimalsInRow();
		}
	}

	private static void addComboToQueue() {
		ArrayList<ArrayList<String>> temp = new ArrayList<>(16);
		for (ArrayList<String> animal : pickedAnimals) {
			temp.add(animal);
		}
		temp.add(chosenAnimal);
		queue.add(temp);
	}

	private static boolean checkValidMove() {
		boolean valid;
		if (lastAnimal.get(0) == chosenAnimal.get(0)) {
			valid = true;
		} else {
			if (lastAnimal.get(1) == chosenAnimal.get(1)) {
				valid = true;
			} else {
				valid = false;
			}
		}
		return valid;
	}

	private static void setChosenAnimal() {
		lastAnimal = chosenAnimal;

		nextAnimalsInRows.remove(chosenAnimal);
		if (row1.contains(chosenAnimal)) {
			row1.remove(chosenAnimal);
		}
		if (row2.contains(chosenAnimal)) {
			row2.remove(chosenAnimal);
		}
		if (row3.contains(chosenAnimal)) {
			row3.remove(chosenAnimal);
		}
		if (row4.contains(chosenAnimal)) {
			row4.remove(chosenAnimal);
		}
	}

	private static void setNextAnimalsInRow() { 
		nextAnimalsInRows.clear();				

		if (row1.size() != 0) {
			nextAnimalsInRows.add(0, row1.get(0));
		} else {
			nextAnimalsInRows.add(0, doneRow);
		}
		if (row2.size() != 0) {
			nextAnimalsInRows.add(1, row2.get(0));
		} else {
			nextAnimalsInRows.add(1, doneRow);
		}
		if (row3.size() != 0) {
			nextAnimalsInRows.add(2, row3.get(0));
		} else {
			nextAnimalsInRows.add(2, doneRow);
		}
		if (row4.size() != 0) {
			nextAnimalsInRows.add(3, row4.get(0));
		} else {
			nextAnimalsInRows.add(3, doneRow);
		}
	}
}
