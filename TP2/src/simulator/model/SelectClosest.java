package simulator.model;

import java.util.List;

public class SelectClosest implements SelectionStrategy { //The primary role of this class is to select the animal that is closest to a given animal from a list of animals
	
	public Animal select(Animal a, List<Animal> as) {
		Animal ClosestAnimal;
		if (as.isEmpty()) {
			ClosestAnimal = null;
		} else {
			ClosestAnimal = as.get(0);
			Double ClosestDistance = a.get_position().distanceTo(ClosestAnimal.get_position());
			for (Animal other : as) {
				double distance = a.get_position().distanceTo(other.get_position());
				if (((distance < ClosestDistance) && (a != other))) {
					ClosestAnimal = other;
					ClosestDistance = distance;
				}
			}

		}
		return ClosestAnimal;
	}

}
