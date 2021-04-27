package animalCrossing.v3b;

public class Animal {
	public enum Color { RED, GREEN, BLUE, YELLOW, DONE};

	public enum Species {
		HIPPO, CAMEL, LION, GIRAFFE, DONE
	}
	
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

	public static Animal returnAnimal(String color, String species) {
		Color c = Color.DONE;
		Species s = Species.DONE;

		switch (color) {
		case ("r"):
			c = Color.RED;
			break;
		case ("g"):
			c = Color.GREEN;
			break;
		case ("b"):
			c = Color.BLUE;
			break;
		case ("y"):
			c = Color.YELLOW;
			break;
		}

		switch (species) {
		case ("l"):
			s = Species.LION;
			break;
		case ("c"):
			s = Species.CAMEL;
			break;
		case ("h"):
			s = Species.HIPPO;
			break;
		case ("g"):
			s = Species.GIRAFFE;
			break;
		}

		return new Animal(c, s);
	}
}