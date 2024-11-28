package simulator.model;

import simulator.model.Animal.Diet;

public class DefaultRegion extends Region {
	private static final double zero = 0;
	private static final double first_multiplier = 60.0;
	private static final double second_multiplier = 2.0;
	private static final double minus_expression = 5.0;
	private static final String region_type = "Default region";




	public DefaultRegion() {

	}

	@Override
	public void update(double dt) {       //This method is overridden from the Region interface but does nothing in the context of DefaultRegion

	}

	@Override
    public double get_food(Animal a, double dt) {            //If the animal is a carnivore, it returns 0, meaning no food is supplied to carnivores by this region type.
        if (a.get_diet() == Diet.CARNIVORE) {
            return DefaultRegion.zero;
        } else {
            long herbivoreCount = _animals.stream().filter(animal -> animal.get_diet() == Diet.HERBIVORE).count();   //If the animal is a herbivore, it calculates the food supplied based on the number of herbivores in the region.We count the number of herbivorous animals in the animals list using Java Streams. It filters out only those animals whose diet is HERBIVORE and then counts them, storing the total count in the 
            return DefaultRegion.first_multiplier * Math.exp(-Math.max(DefaultRegion.zero, herbivoreCount - DefaultRegion.minus_expression) * DefaultRegion.second_multiplier) * dt;
        }
    }
	
	
	public String toString() {
    	return DefaultRegion.region_type;
    }

}
