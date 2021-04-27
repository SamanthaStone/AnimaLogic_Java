package animalCrossing.statistics.v2;

import java.util.ArrayList;

import animalCrossing.statistics.v2.Animal;

public class BoardState {
	ArrayList<ArrayList<Animal>> board;
	ArrayList<Animal> animalsRemoved;

	public BoardState(ArrayList<ArrayList<Animal>> board,
			ArrayList<Animal> removed) {
		this.board = board;
		if (removed == null) {
			animalsRemoved = new ArrayList<>();
		} else {
			animalsRemoved = removed;
		}
	}
	
	boolean canPerformMove(Animal animal) {
		if (animalsRemoved == null || animalsRemoved.size()==0) {
			return true;
		}

		Animal lastAnimal = animalsRemoved.get(animalsRemoved.size() - 1);
		return lastAnimal.isMatch(animal);
	}

	boolean findASolution() {
		if (animalsRemoved != null
				&& (animalsRemoved.size() == board.size()
						* board.size())) {
			return true;
		}
		
		for (ArrayList<Animal> row : board) {
			Animal removeable = row.get(0);
			if (canPerformMove(removeable)) {
				animalsRemoved.add(removeable);
				row.remove(0);
				if (findASolution()) {
					return true;
				}
				animalsRemoved.remove(animalsRemoved.size()-1);
				row.add(0, removeable);
			}
		}
		
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("board: ");
		for (ArrayList<Animal> arr : board) {
			for (Animal a : arr) {
				sb.append(a + ", ");
			}
			sb.append(" || ");
		}
		sb.append("\n");
		sb.append("animals off: ");
		if (animalsRemoved != null)
			for (Animal a : animalsRemoved) {
				sb.append(a + ", ");
			}
		return sb.toString();
	}
}