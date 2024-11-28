package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Region implements Entity, FoodSupplier, RegionInfo {
	protected List<Animal> _animals;

	public Region() {
		this._animals = new ArrayList<>();
	}

	 void add_animal(Animal a) {           //Adds an animal to the region. This method is marked as final to prevent overriding, ensuring that the addition process remains consistent across all region types.
		_animals.add(a);
	}

	 void remove_animal(Animal a) {       //Removes an animal from the region. Similar to add_animal, this method is final to ensure consistent removal behavior
		_animals.remove(a);
	}

	final List<Animal> getAnimals() {           //Returns an unmodifiable view of the animals list. This ensures that external classes cannot modify the list directly
		return Collections.unmodifiableList(_animals);
	}

	public JSONObject as_JSON() {               //Converts the region and its animals into a JSON object format, facilitating data export and visualization. This method iterates over the animals list, converting each animal to JSON and adding it to a JSONArray, which is then incorporated into the region's JSON representation.
		JSONObject json = new JSONObject();
		JSONArray animalArray = new JSONArray();

		for (Animal animal : _animals) {
			animalArray.put(animal.as_JSON());
		}

		json.put("animals", animalArray);
		return json;

	}
	
	public List<AnimalInfo> getAnimalsInfo() {
		return new ArrayList<>(_animals); 
		}

}
