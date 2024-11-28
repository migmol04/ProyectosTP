package simulator.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import simulator.factories.Factory;

public class Simulator implements JSONable, Observable<EcoSysObserver> {

	private int _cols;
	private int _rows;
	private int _width;
	private int _height;
	private Factory<Animal> _animals_factory;
	private Factory<Region> _regions_factory;
	private RegionManager _region_manager;
	private List<Animal> _animals;
	private double _tmp;
	private Set<EcoSysObserver> observers = new HashSet<>();

	public Simulator(int cols, int rows, int width, int height, Factory<Animal> animals_factory,
			Factory<Region> regions_factory) {
		this._cols = cols;
		this._rows = rows;
		this._width = width;
		this._height = height;
		this._animals_factory = animals_factory;
		this._regions_factory = regions_factory;
		this._region_manager = new RegionManager(cols, rows, width, height);
		this._animals = new ArrayList<>();
		this._tmp = 0.0;
	}

	public void reset(int cols, int rows, int width, int height) { // This method resets the simulation
		// Empty the list of animals or create a new one
		this._animals = new ArrayList<>();
		// Create a new RegionManager with the specified dimensions
		this._region_manager = new RegionManager(cols, rows, width, height);
		// Set the simulation time to 0.0
		this._tmp = 0.0;

		notify_on_reset();
	}
	//////////Sending notifications///////////

	private void notify_on_register(EcoSysObserver o) {
		List<AnimalInfo> animals = new ArrayList<>(_animals);
		o.onRegister(_tmp, _region_manager, animals);
	}

	private void notify_on_reset() {
		List<AnimalInfo> animals = new ArrayList<>(_animals);
		for (EcoSysObserver observer : observers) {
			observer.onReset(this._tmp, this._region_manager, animals);
		}
	}

	private void notify_on_animal_added(Animal animal) {
		List<AnimalInfo> animals = new ArrayList<>(_animals);
		AnimalInfo animalInfo = animal;
		for (EcoSysObserver observer : observers) {
			observer.onAnimalAdded(this._tmp, this._region_manager, animals, animalInfo);
		}
	}

	private void notify_on_regionSet(int row, int col, Region region) {
		RegionInfo regionInfo = region; // Supone que tienes esta función implementada
		for (EcoSysObserver observer : observers) {
			observer.onRegionSet(row, col, this._region_manager, regionInfo);
		}
	}

	private void notify_on_advanced(double dt) {
		List<AnimalInfo> animals = new ArrayList<>(_animals);
		for (EcoSysObserver observer : observers) {
			observer.onAvanced(_tmp, _region_manager, animals, dt);
		}
	}
//////////////////////////////////
	@Override
	public void addObserver(EcoSysObserver o) {
		if (observers.add(o)) {
			notify_on_register(o);

		}
	}

	@Override
	public void removeObserver(EcoSysObserver o) {
		observers.remove(o);
	}

	private void set_region(int row, int col, Region r) {// Adds the region r to the RegionManager at the specified
															// position
		_region_manager.set_region(row, col, r);
		notify_on_regionSet(row, col, r);
	}

	public void set_region(int row, int col, JSONObject r_json) { // Creates a region from r_json using the regions
																	// factory and adds it to the RegionManager.
		Region region = _regions_factory.create_instance(r_json);
		set_region(row, col, region);
	}

	public void add_animal(JSONObject a_json) {
		Animal animal = _animals_factory.create_instance(a_json); // Creates an animal from a_json using the animals
																	// factory and adds it to the simulation
		add_animal(animal);
	}

	private void add_animal(Animal a) { // Adds the animal a to the list of animals and registers it with the
										// RegionManager
		this._animals.add(a);
		this._region_manager.register_animal(a);
		notify_on_animal_added(a);
	}

	public void delete_animal(Animal a) { // Removes the animal a from the list of animals and unregisters it from the
											// RegionManager
		this._animals.remove(a);
		this._region_manager.unregister_animal(a);
	}

	public List<Animal> get_animals() { // Returns an unmodifiable list of the current animals in the simulation.
		return Collections.unmodifiableList(this._animals);
	}

	public JSONObject as_JSON() { // eturns a JSON object representing the current state of the simulation,
									// including time and the state of the RegionManager
		JSONObject result = new JSONObject();
		result.put("time", this._tmp);

		result.put("state", this._region_manager.as_JSON());
		return result;
	}

	public void advance(double dt) {
	    // Advances the simulation one time step dt.
	    this._tmp += dt;
	    List<Animal> newBabies = new ArrayList<>();
	    List<Animal> deadAnimals = new ArrayList<>();

	 // First: Identify dead animals
	    for (Animal animal : _animals) {
	        if (animal.get_state() == Animal.State.DEAD) {
	            deadAnimals.add(animal);
	        }
	    }

	    // Eliminate dead animals
	    for (Animal deadAnimal : deadAnimals) {
	        delete_animal(deadAnimal);
	    }

	    // Second: Update all animals and their regions
	    for (Animal animal : _animals) {
	        animal.update(dt);
	        _region_manager.update_animal_region(animal);
	    }
	    _region_manager.update_all_regions(dt);

	    // Third: Manage pregnancies and births of new animals
	    for (Animal animal : _animals) {
	        if (animal.is_pregnent()) {
	            Animal baby = animal.deliver_baby();
	            newBabies.add(baby);
	            
	        }
	    }

	    // Add the new babies to the simulation later
	    for (Animal baby : newBabies) {
	        add_animal(baby);
	    }
	    
	    notify_on_advanced(dt);
		
	}
//
	// GETTERS//
	public double get_time() {
		return _tmp;
	}

	public MapInfo get_map_info() {
		return _region_manager;
	}
	///////////
}
