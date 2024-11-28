package simulator.factories;

import org.json.JSONObject;

import simulator.model.DefaultRegion;
import simulator.model.Region;

public class DefaultRegionBuilder extends Builder<Region> {

    public DefaultRegionBuilder() {
        super("default", "Infinite food supply");
    }
    @Override
    protected void fill_in_data(JSONObject data) {
        // No specific data is added here since DefaultRegion does not require it.
      
    }

    @Override
    protected Region create_instance(JSONObject data) {
        return new DefaultRegion();
    }
}
