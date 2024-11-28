package simulator.model;

import java.util.List;

public class SelectFirst implements SelectionStrategy { //The primary functionality of this class is to provide a method to select the first animal from a list

	@Override
	public Animal select(Animal a, List<Animal> as) {
		Animal FirstAnimal;
		if(as.isEmpty()) {
			FirstAnimal = null;
		}
		else {
			if(a != as.get(0)) {
				FirstAnimal = as.get(0);
				
			}
			else {
				if(as.size() > 1) {
					FirstAnimal = as.get(1);
					
				}
				else {
					FirstAnimal = null;
				}
				
			}
		
		}
		return FirstAnimal;
	}

}
