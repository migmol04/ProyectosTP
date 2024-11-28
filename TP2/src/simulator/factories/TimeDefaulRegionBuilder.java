package simulator.factories;

import org.json.JSONObject;

import simulator.model.Region;
import simulator.model.TimeDefaulRegion;

public class TimeDefaulRegionBuilder extends Builder<Region> {

    public TimeDefaulRegionBuilder() {
        super("time_default_region", "Region that supplies food every 2 seconds");
    }

    @Override
    protected void fill_in_data(JSONObject data) {
    
    }

    @Override
    protected Region create_instance(JSONObject data) {
        return new TimeDefaulRegion();
    }
}
