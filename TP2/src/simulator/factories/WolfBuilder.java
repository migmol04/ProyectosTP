package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Animal;
import simulator.model.SelectionStrategy;
import simulator.model.Sheep;
import simulator.model.Wolf;

public class WolfBuilder extends Builder<Animal> {
	private Factory<SelectionStrategy> strategyFactory;

	public WolfBuilder(Factory<SelectionStrategy> strategyFactory) {
		super("wolf", "Creates a wolf with specific mate and hunting strategies.");
		this.strategyFactory = strategyFactory;
	}

	@Override
	protected Animal create_instance(JSONObject data) {
		SelectionStrategy mateStrategy = null;
		SelectionStrategy huntingStrategy = null;
		Vector2D position = null;

		//Get matching and hunting strategies from JSON, if available
		if (data.has("mate_strategy")) {
			mateStrategy = strategyFactory.create_instance(data.getJSONObject("mate_strategy"));
		}
		else{
			mateStrategy = strategyFactory.create_instance(new JSONObject("{ 'type': 'first' }"));
			
		}
		if (data.has("hunt_strategy")) {
			huntingStrategy = strategyFactory.create_instance(data.getJSONObject("hunt_strategy"));
		}
		else {
			huntingStrategy = strategyFactory.create_instance(new JSONObject("{ 'type': 'first' }"));
			
		}

		// Get starting position from JSON, if available
		if (data.has("pos")) {
			JSONObject pos = data.getJSONObject("pos");
			double x = (pos.getJSONArray("x_range").getDouble(0) + pos.getJSONArray("x_range").getDouble(1)) / 2;
			double y = (pos.getJSONArray("y_range").getDouble(0) + pos.getJSONArray("y_range").getDouble(1)) / 2;
			position = new Vector2D(x, y);
		}


		return new Wolf(mateStrategy, huntingStrategy, position);
	}
	
	 @Override
	    protected void fill_in_data(JSONObject data) {
	     
	    }
}