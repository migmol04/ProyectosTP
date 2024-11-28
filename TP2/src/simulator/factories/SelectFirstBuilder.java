package simulator.factories;

import org.json.JSONObject;

import simulator.model.SelectFirst;
import simulator.model.SelectionStrategy;


public class SelectFirstBuilder extends Builder<SelectionStrategy> {
    public SelectFirstBuilder() {
        super("first", "Select the first animal encountered.");
    }

    @Override
    protected SelectionStrategy create_instance(JSONObject data) {
        return new SelectFirst();
    }
    @Override
    protected void fill_in_data(JSONObject data) {
        //No specific data is added here since SelectClosest does not require it.
    }
}