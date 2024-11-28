package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Animal;
import simulator.model.SelectionStrategy;
import simulator.model.Sheep;

public class SheepBuilder extends Builder<Animal> {
	private Factory<SelectionStrategy> strategyFactory;

	public SheepBuilder(Factory<SelectionStrategy> strategyFactory) {
		super("sheep", "Creates a sheep with specific mate and danger strategies.");
		this.strategyFactory = strategyFactory;
	}

	@Override
	protected Animal create_instance(JSONObject data) {
		SelectionStrategy mateStrategy = null;
		SelectionStrategy dangerStrategy = null;
		Vector2D position = null;

		// Get matching and danger strategies from JSON, if available
		if (data.has("mate_strategy")) {
			mateStrategy = strategyFactory.create_instance(data.getJSONObject("mate_strategy"));
		} else {
			mateStrategy = strategyFactory.create_instance(new JSONObject("{ 'type': 'first' }"));
		}
		if (data.has("danger_strategy")) {
			dangerStrategy = strategyFactory.create_instance(data.getJSONObject("danger_strategy"));
		} else {
			dangerStrategy = strategyFactory.create_instance(new JSONObject("{ 'type': 'first' }"));
		}

		if (data.has("pos")) {
			JSONObject pos = data.getJSONObject("pos");
			double x = (pos.getJSONArray("x_range").getDouble(0) + pos.getJSONArray("x_range").getDouble(1)) / 2;
			double y = (pos.getJSONArray("y_range").getDouble(0) + pos.getJSONArray("y_range").getDouble(1)) / 2;
			position = new Vector2D(x, y);
		}

		return new Sheep(mateStrategy, dangerStrategy, position);
	}

	@Override
	protected void fill_in_data(JSONObject data) {

	}
}
