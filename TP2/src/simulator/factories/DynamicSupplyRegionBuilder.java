package simulator.factories;

import org.json.JSONObject;

import simulator.model.DynamicSupplyRegion;
import simulator.model.Region;

public class DynamicSupplyRegionBuilder extends Builder<Region> {

    private static final double DEFAULT_FACTOR = 2.0;
    private static final double DEFAULT_FOOD = 100.0;

    public DynamicSupplyRegionBuilder() {
        super("dynamic", "Dynamic food supply");
    }
    
    @Override
    protected void fill_in_data(JSONObject data) {
    	data.put("factor", "food increase factor(optional, default 2.0)");
    	data.put("food", "initial amount of food(optional, default 100.0)");
    }

    @Override
    protected Region create_instance(JSONObject data) {
        // Use default values if not specified in JSON.
        double factor = data.optDouble("factor", DEFAULT_FACTOR);
        double food = data.optDouble("food", DEFAULT_FOOD);
        return new DynamicSupplyRegion(food, factor);
    }
}