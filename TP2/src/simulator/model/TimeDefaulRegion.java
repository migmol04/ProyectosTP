package simulator.model;

import java.util.HashMap;
import java.util.Map;

public class TimeDefaulRegion extends DefaultRegion {

    private Map<Animal, Double> _map = new HashMap<Animal, Double>();

    public TimeDefaulRegion() {
        
    }

    @Override
    public void update(double dt) {
     
    }

    @Override
    public double get_food(Animal a, double dt) {
        double time = _map.get(a);

        if (time < 0.1) {
            _map.put(a, time + dt);
            return 0.0;
        } else {
            _map.put(a, 0.0);
            return 2000.0;
        }
    }

   @Override
    public void add_animal(Animal a) {
        super.add_animal(a);
        _map.put(a, 0.0);
    }

   @Override
    public void remove_animal(Animal a) {
        super.remove_animal(a);
        _map.remove(a);
    }
   
   public String toString() {
   	return "Time_region";
   }
}
