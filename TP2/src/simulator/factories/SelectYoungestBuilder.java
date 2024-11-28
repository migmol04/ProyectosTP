package simulator.factories;

import org.json.JSONObject;

import simulator.model.SelectYoungest;
import simulator.model.SelectionStrategy;

public class SelectYoungestBuilder extends Builder<SelectionStrategy> {
    public SelectYoungestBuilder() {
        super("youngest", "Select the youngest animal.");
    }

    @Override
    protected SelectionStrategy create_instance(JSONObject data) {
        return new SelectYoungest();
    }
    @Override
    protected void fill_in_data(JSONObject data) {
        //No specific data is added here since SelectClosest does not require it.
    }
}