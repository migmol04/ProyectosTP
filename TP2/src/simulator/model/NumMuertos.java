package simulator.model;

import java.util.List;

import simulator.control.Controller;
import simulator.model.Animal.State;

public class NumMuertos implements EcoSysObserver {
	
	private Controller _ctrl;
	private int num_animals;

	
	public NumMuertos(Controller ctrl) {
		this._ctrl = ctrl;
		this.num_animals = 0;
		this._ctrl.addObserver(this);
	}
	
	
	public void count_dead_animals(List<AnimalInfo> animals) {
		num_animals += (int) animals.stream().filter(AnimalInfo  -> AnimalInfo.get_state() == State.DEAD).count();
		
	}
	
	 public void show_message() {
	        System.out.print("El numero de animales muertos es " + num_animals); 
	    }

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		count_dead_animals(animals);
		
		
	}

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		count_dead_animals(animals);
		
		
	}

	@Override
	public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		count_dead_animals(animals);
		
		
	}

	@Override
	public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		count_dead_animals(animals);
		
	}

}
