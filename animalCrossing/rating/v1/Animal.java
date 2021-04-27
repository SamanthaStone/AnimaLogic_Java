package animalCrossing.rating.v1;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Animal implements Serializable {
	public enum Color {
		RED, GREEN, BLUE, YELLOW, WHITE, BLACK, SEVENTH_COLOR
	};

	public enum Species {
		HIPPO, CAMEL, LION, GIRAFFE, PEACOCK, OSTRICH, SEVENTH_SPECIES
	};

	private Color color;
	private Species species;

	public Animal(Color color, Species species) {
		this.color = color;
		this.species = species;
	}

	public Color getColor() {
		return color;
	}

	public Species getSpecies() {
		return species;
	}

	public boolean isMatch(Animal animal) {
		if (animal == null) {
			return true;
		}
		if ((animal.color == this.color) || (animal.species == this.species)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return color.toString().toLowerCase() + " "
				+ species.toString().toLowerCase();
	}
	
	
}