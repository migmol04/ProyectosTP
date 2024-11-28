package simulator.factories;

import org.json.JSONObject;

import simulator.model.SelectClosest;
import simulator.model.SelectionStrategy;

public class SelectClosestBuilder extends Builder<SelectionStrategy> {
    public SelectClosestBuilder() {
        super("closest", "Select the closest animal.");
    }

    @Override
    protected SelectionStrategy create_instance(JSONObject data) {
        return new SelectClosest();
    }
    @Override
    protected void fill_in_data(JSONObject data) {
        //No specific data is added here since SelectClosest does not require it.
    }
}