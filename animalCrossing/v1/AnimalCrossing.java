package animalCrossing.v1;
// fix blacklist by adding element to array
//System.out.println(System.getProperty("java.runtime.version"));
import java.util.ArrayList;

public class AnimalCrossing {
	// making all the arrays, red hippo, yellow hippo...
	static ArrayList<String> rh = new ArrayList<>(2);
	static ArrayList<String> yh = new ArrayList<>(2);
	static ArrayList<String> gh = new ArrayList<>(2); 
	static ArrayList<String> bh = new ArrayList<>(2); 
	static ArrayList<String> rc = new ArrayList<>(2); 
	static ArrayList<String> yc = new ArrayList<>(2); 
	static ArrayList<String> gc = new ArrayList<>(2); 
	static ArrayList<String> bc = new ArrayList<>(2); 
	static ArrayList<String> rl = new ArrayList<>(2); 
	static ArrayList<String> yl = new ArrayList<>(2); 
	static ArrayList<String> gl = new ArrayList<>(2);
	static ArrayList<String> bl = new ArrayList<>(2); 
	static ArrayList<String> rg = new ArrayList<>(2); 
	static ArrayList<String> yg = new ArrayList<>(2); 
	static ArrayList<String> gg = new ArrayList<>(2); 
	static ArrayList<String> bg = new ArrayList<>(2); 
	
	static  ArrayList<ArrayList<String>> row1 = new ArrayList<ArrayList<String>>(4);
	static 	ArrayList<ArrayList<String>> row2 = new ArrayList<ArrayList<String>>(4);
	static 	ArrayList<ArrayList<String>> row3 = new ArrayList<ArrayList<String>>(4);
	static 	ArrayList<ArrayList<String>> row4 = new ArrayList<ArrayList<String>>(4);
	
	static ArrayList<ArrayList<String>> nextAnimalsInRows = new ArrayList<ArrayList<String>>(4);
	static ArrayList<ArrayList<String>> pickedAnimals = new ArrayList<ArrayList<String>>(16);
	static ArrayList<Integer> pickedAnimalsRow = new ArrayList<Integer>(16);
	static ArrayList<String> chosenAnimal = new ArrayList<String>(2);
	static int chosenNumber = 0;
	static ArrayList<String> lastAnimal;
	
	static ArrayList<String> finishedRow = new ArrayList<String>(2);
	
	static ArrayList<ArrayList<ArrayList<String>>> blackList = new ArrayList<>(500);
	static ArrayList<ArrayList<String>> checkIfBlackList = new ArrayList<ArrayList<String>>(16);
	
	public static void main(String[] args) {
		// setting the vars
		rh.add("red"); rh.add("hippo");	yh.add("yellow"); yh.add("hippo"); gh.add("green"); gh.add("hippo");
		bh.add("blue"); bh.add("hippo"); rc.add("red"); rc.add("camel"); yc.add("yellow"); yc.add("camel");
		gc.add("green"); gc.add("camel"); bc.add("blue"); bc.add("camel"); rl.add("red"); rl.add("lion");
		yl.add("yellow"); yl.add("lion"); gl.add("green"); gl.add("lion"); bl.add("blue"); bl.add("lion");
		rg.add("red"); rg.add("giraffe"); yg.add("yellow"); yg.add("giraffe"); gg.add("green"); gg.add("giraffe");
		bg.add("blue"); bg.add("giraffe");
	
		// add user input later
		row1.add(rl); row1.add(rh); row1.add(gc); row1.add(bg);
		row2.add(rg); row2.add(yg); row2.add(yc); row2.add(bc);
		row3.add(rc); row3.add(gg); row3.add(bh); row3.add(yl);
		row4.add(yh); row4.add(bl); row4.add(gl); row4.add(gh);

		chosenNumber = 0;
		finishedRow.add("finished"); finishedRow.add("row");
		
		setNextAnimalsInRow();
		chosenAnimal = nextAnimalsInRows.get(chosenNumber);
		System.out.println(nextAnimalsInRows);
		pickUpAnimal();
		
		for (int i=0;i<108;i++) {
			decideToPickOrChoose(); 
		}

//		System.out.println();
//		System.out.println("picked " + pickedAnimals);
//		for (ArrayList<ArrayList<String>> combo : blackList) {
//			System.out.println(combo);
//		}
		
	}

	private static void decideToPickOrChoose() {
		boolean valid;
		valid = checkValidMove();
		if (valid) { 
			pickUpAnimal();
		} else {
			// System.out.print("invalid, next is: ");
			chosenNumber ++;
			if (chosenNumber > 3) {
				rewind();
			} else {
				chosenAnimal = nextAnimalsInRows.get(chosenNumber);
			}
		}
	}

	private static void rewind() {
		if (pickedAnimals.size() < 16) {	
			System.out.println("rewinding");
			rewindering();
		} else {
			System.out.println("Found Solution Combo!");
			System.out.println(pickedAnimals);
			rewindering();
		}
	}

	private static void rewindering() {
		ArrayList<String> lastTurn = pickedAnimals.get(pickedAnimals.size()-1);
		Integer rowNum = pickedAnimalsRow.get(pickedAnimalsRow.size()-1);
		if (rowNum == 1) {
			row1.add(0, lastTurn);
		} else if (rowNum == 2) {
			row2.add(0, lastTurn);
		} else if (rowNum == 3) {
			row3.add(0, lastTurn);
		} else if (rowNum == 4) {
			row4.add(0, lastTurn);
		}

		ArrayList<ArrayList<String>> tempList = new ArrayList<ArrayList<String>>(15);
		for (ArrayList<String> animal : pickedAnimals) {
			tempList.add(animal);
		}
		blackList.add(tempList);
		
		pickedAnimalsRow.remove(pickedAnimalsRow.size()-1);
		pickedAnimals.remove(pickedAnimals.get(pickedAnimals.size()-1));
		lastAnimal = pickedAnimals.get(pickedAnimals.size() - 1);
		setNextAnimalsInRow();
		chosenNumber = 0;
	}

	private static void pickUpAnimal() {
		setChosenAnimal();
		setNextAnimalsInRow();
		chosenNumber = 0;
		chosenAnimal = nextAnimalsInRows.get(chosenNumber);
		lastAnimal = pickedAnimals.get(pickedAnimals.size() - 1);
		System.out.println(nextAnimalsInRows);
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
		
		if (valid) {
			for (ArrayList<String> animal : pickedAnimals) {
				checkIfBlackList.add(animal);
			}
			checkIfBlackList.add(checkIfBlackList.size(), chosenAnimal);
			for (ArrayList<ArrayList<String>> combo : blackList) {
				if (checkIfBlackList.equals(combo)) {
					valid = false;
				}
			}
			pickedAnimals.remove(chosenAnimal); 
		}
		checkIfBlackList.clear();
		return valid;
	}

	private static void setChosenAnimal() {
		pickedAnimals.add(chosenAnimal);
		nextAnimalsInRows.remove(chosenAnimal);
		if (row1.contains(chosenAnimal)) {
			row1.remove(chosenAnimal);
			pickedAnimalsRow.add(1);
		}
		if (row2.contains(chosenAnimal))  {
			row2.remove(chosenAnimal);
			pickedAnimalsRow.add(2);
		}
		if (row3.contains(chosenAnimal)) {
			row3.remove(chosenAnimal); 
			pickedAnimalsRow.add(3);
		}
		if (row4.contains(chosenAnimal)) {
			row4.remove(chosenAnimal); 	
			pickedAnimalsRow.add(4);
		}
	}

	private static void setNextAnimalsInRow() {
		nextAnimalsInRows.clear();
		
		if (row1.size() != 0) {
			nextAnimalsInRows.add(0, row1.get(0)); 
		} else { nextAnimalsInRows.add(0, finishedRow); }
		if (row2.size() != 0) {
			nextAnimalsInRows.add(1, row2.get(0));
		} else { nextAnimalsInRows.add(1, finishedRow); }
		if (row3.size() != 0) {
			nextAnimalsInRows.add(2, row3.get(0)); 
		} else { nextAnimalsInRows.add(2, finishedRow); }
		if (row4.size() != 0) {
			nextAnimalsInRows.add(3, row4.get(0));
		} else { nextAnimalsInRows.add(3, finishedRow); }
	}

}

