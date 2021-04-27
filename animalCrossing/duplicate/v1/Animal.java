package animalCrossing.duplicate.v1;

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
		return color.toString().toLowerCase() + " "
				+ species.toString().toLowerCase();
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