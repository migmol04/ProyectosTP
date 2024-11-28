package simulator.model;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import simulator.control.Controller;
import simulator.model.Animal.State;

public class OBSMAYOR implements EcoSysObserver{

	class DATOS {
		Double time;
		int cantidad;
		
		public DATOS(Double time, int cant) {
			this.time = time;
			this.cantidad = cant;
		}
	}
	
	
	
	Controller ctrl;
	private Map<State, DATOS> mapa;
	
	public OBSMAYOR(Controller ctrl) {
		this.ctrl = ctrl;
		this.mapa = new HashMap<>();
		for(State s: State.values()) {
			DATOS dat = new DATOS(0.0, 0);
			this.mapa.put(s, dat);
		}
		this.ctrl.addObserver(this);
	}
	
	
	public void actualizarDatos(double time, List<AnimalInfo> animals) {
		
		this.mapa.forEach((key, value) ->{
			int num = (int) animals.stream().filter(AnimalInfo -> AnimalInfo.get_state() == key).count();
			if(num > value.cantidad) {
				DATOS nuevo = new DATOS(time, num);
				this.mapa.put(key, nuevo);
			}
		});	
	}
	
	
	public void print() {
		this.mapa.forEach((key, value) -> {
			System.out.println("El momento en el que el estado " + key.toString() + " ha sido mayor es: Time -> " + value.time + " Cantidad -> " + value.cantidad );
		});
	}
	
	
	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		// TODO Auto-generated method stub
		actualizarDatos(time, animals);
	}

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		// TODO Auto-generated method stub
		actualizarDatos(time, animals);
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
		// TODO Auto-generated method stub
		actualizarDatos(time, animals);
	}

}
