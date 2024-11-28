package simulator.model;

import java.util.ArrayList;
import java.util.List;

import simulator.control.Controller;
import simulator.model.Animal.Diet;;

public class Supera implements EcoSysObserver {
	
	
	class Info {
		int _n;
		double _time;
		public Info(int n, double time) {
			_n = n;
			_time = time;
		}
	}
	
	private Controller _ctrl;
	private int N;
	private List<Info> info;
	private int numVeces;
	public Supera(Controller ctrl) {
		_ctrl = ctrl;
		info = new ArrayList<Info>();
		numVeces = 0;
		_ctrl.addObserver(this);
		
	}
	
	private void cuenta(List<AnimalInfo> animals, double time) {
		int num = (int)animals.stream().filter(a -> a.get_diet() == Diet.HERBIVORE).count();
		if(num > N) {
			numVeces++;
			info.add(new Info(num, time));
		}
		
		
	}
	
	public void print() {
		System.out.print("Se ha superado " + N + "un total de " + numVeces + " : ");
		for(int i = 0; i < info.size(); i++) {
			System.out.println(info.get(i)._n + " " +  info.get(i)._time);
		}
	}

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		N = (int)animals.stream().filter(a->a.get_diet() == Diet.HERBIVORE).count();

		
	}

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
cuenta(animals, time);
		
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
		cuenta(animals, time);
		
	}

}
