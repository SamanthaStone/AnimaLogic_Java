package animalCrossing.statistics.v1;

public class Animal {
	public enum Color {
		RED, GREEN, BLUE, YELLOW, WHITE, BLACK
	};

	public enum Species {
		HIPPO, CAMEL, LION, GIRAFFE, PEACOCK, OSTRICH
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
		if (animal == null)
			return true;
		if ((animal.color == this.color) && (animal.species == this.species))
			System.out.println("copied animals");
		if ((animal.color == this.color) || (animal.species == this.species))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return color.toString().toLowerCase() + " "
				+ species.toString().toLowerCase();
	}
}