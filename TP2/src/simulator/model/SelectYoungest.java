package simulator.model;

import java.util.List;

public class SelectYoungest implements SelectionStrategy {  //The purpose of the SelectYoungest class is to provide a way to select the youngest animal from a list of animals

	@Override
	public Animal select(Animal a, List<Animal> as) {
		Animal YoungestAnimal;
		if (as.isEmpty()) {
			YoungestAnimal = null;
		} else {
			YoungestAnimal = as.get(0);
			for (Animal other : as) {
				if ((other.get_age() < YoungestAnimal.get_age()) && (a != other)) {
					YoungestAnimal = other;
				}
			}
		}
		return YoungestAnimal;
	}

}
