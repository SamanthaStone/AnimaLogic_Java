package animalCrossing.duplicate.v2;

import animalCrossing.duplicate.v2.Animal;

public class Animal {
	public enum Color {
		RED, YELLOW, BLUE, GREEN, WHITE, BLACK, SEVENTH_COLOR
	};

	public enum Species {
		HIPPO, CAMEL, LION, GIRAFFE, PEACOCK, OSTRICH, SEVENTH_SPECIES
	};

	public Color color;
	public Species species;

	public Animal(Color color, Species species) {
		this.color = color;
		this.species = species;
	}

	@Override
	public String toString() {
		if (color == null & species == null)
			return "null null";
		if (color == null)
			return "null " + species.toString().toLowerCase();
		if (species == null)
			return color.toString().toLowerCase() + " null";
		
		return color.toString().toLowerCase() + " " + species.toString().toLowerCase();
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Animal other = (Animal) obj;
		if (color != other.color)
			return false;
		if (species != other.species)
			return false;
		return true;
	}

}