package simulator.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simulator.control.Controller;
import simulator.model.Animal.State;

public class NumDead implements EcoSysObserver {
	
	 class Info {
	        private String _type;
	        private double _time;

	        public Info(String type, double time) {
	            _type = type;
	            _time = time;
	        }

	    }
	
	
	
	private Controller _ctrl;
	private Map<AnimalInfo, Info> _data;
	
	
	public NumDead(Controller ctrl) {
		_ctrl = ctrl;
		_data= new HashMap<AnimalInfo, Info>();
		_ctrl.addObserver(this);
		
		
	}
	
	private void count(double time, List<AnimalInfo> animals) {
		for(AnimalInfo a: animals) {
			if(a.get_state() == State.DEAD) {
				_data.put(a, new Info(a.get_genetic_code(), time));
			}
		}
	}
	
	public void print() {
		for(Info info : _data.values()) {
			System.out.println("Muerto en el segundo: " + info._time + " siendo de tipo: " + info._type );
		}
	}

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		count(time, animals);
		
	}

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		count(time, animals);
		
	}

	@Override
	public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		count(time, animals);
		
	}

}
