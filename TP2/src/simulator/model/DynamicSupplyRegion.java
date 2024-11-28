package simulator.model;

import simulator.misc.Utils;
import simulator.model.Animal.Diet;

public class DynamicSupplyRegion extends Region {

    private double _food;
    private double _growthFactor;
	private static final double probability = 0.5;
	private static final double zero = 0;
	private static final double first_multiplier = 60.0;
	private static final double second_multiplier = 2.0;
	private static final double minus_expression = 5.0;
	private static final String region_type = "Dynamic region";


    public DynamicSupplyRegion(double initialFood, double growthFactor) {
        super();
        this._food = initialFood;
        this._growthFactor = growthFactor;
    }

    @Override
    public void update(double dt) {                           //Updates the state of the region over time
        if (Utils._rand.nextDouble() < DynamicSupplyRegion.probability) {
            _food += dt * _growthFactor;
        }
    }

    @Override
    public double get_food(Animal a, double dt) {           //Determines the amount of food provided to an animal during a simulation step. Carnivores always receive zero food from this region. Herbivores may receive food based on the current supply and the number of herbivores present. The actual amount provided is the lesser of the available food and a calculated maximum based on the herbivore population. After food is provided to an animal, the region's food supply decreases accordingly
        if (a.get_diet() == Diet.CARNIVORE) {
            return DynamicSupplyRegion.zero;
        } else {
            int herbivoreCount = (int) _animals.stream().filter(animal -> animal.get_diet() == Diet.HERBIVORE).count();
            double foodToProvide = Math.min(_food, DynamicSupplyRegion.first_multiplier * Math.exp(-Math.max(DynamicSupplyRegion.zero, herbivoreCount - DynamicSupplyRegion.minus_expression) * DynamicSupplyRegion.second_multiplier) * dt);
            _food -= foodToProvide;
            return foodToProvide;
        }
    }
    
    public String toString() {
    	return DynamicSupplyRegion.region_type;
    }
}
