package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class RegionManager implements AnimalMapView {

	private int cols;
	private int rows;
	private int width;
	private int height;
	private int regionWidth;
	private int regionHeight;
	private Region[][] _regions;
	private Map<Animal, Region> _animal_region;

	public RegionManager(int cols, int rows, int width, int height) {
		if (width % cols != 0 || height % rows != 0) {
	        throw new IllegalArgumentException("Width must be divisible by number of columns and height must be divisible by number of rows");
	    }
		
		this.cols = cols;
		this.rows = rows;
		this.width = width;
		this.height = height;
		this.regionWidth = width / cols;
		this.regionHeight = height / rows;

		_regions = new Region[rows][cols];
		_animal_region = new HashMap<>();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				_regions[i][j] = new DefaultRegion();
			}
		}

	}
	
	@Override
    public Iterator<MapInfo.RegionData> iterator() {
        return new Iterator<MapInfo.RegionData>() {
            private int currentRow = 0;
            private int currentCol = 0;

            @Override
            public boolean hasNext() {
                return currentRow < rows && currentCol < cols;
            }

            @Override
            public MapInfo.RegionData next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                RegionInfo regionInfo = _regions[currentRow][currentCol];
                MapInfo.RegionData data = new MapInfo.RegionData(currentRow, currentCol, regionInfo);

              
                currentCol++;
                if (currentCol >= cols) {
                    currentCol = 0;
                    currentRow++;
                }

                return data;
            }
        };
    }
	


	 void set_region(int row, int col, Region r) {                         //Assigns a new region to a specific grid position, transferring all animals from the old region to the new one
	    if (row >= 0 && row < this.rows && col >= 0 && col < this.cols) {
	        Region oldRegion = _regions[row][col];
	        _regions[row][col] = r;
	        
	        List<Animal> a = new ArrayList<>();
	        a = oldRegion.getAnimals();
	        for(int i = 0; i < a.size();i++) {
	        	 _animal_region.put(a.get(i), r);
		            r.add_animal(a.get(i));
		            oldRegion.remove_animal(a.get(i)); 
	        	
	        }
	    } else {
	        throw new IllegalArgumentException("Row or column out of bounds");
	    }
	}


	void register_animal(Animal a) {                        // Assigns an animal to the appropriate region based on its position.
       a.init(this); 
	    Region r = get_region_for_animal(a);
	    r.add_animal(a);
	    _animal_region.put(a, r);
	}
	
	
	void unregister_animal(Animal a) {                  //Removes an animal from its current region
		Region r = _animal_region.get(a);
		r.remove_animal(a);

	}
	
	//Returns the region based on animal position
	private Region get_region_for_animal(Animal a) {
	        int col = (int) (a.get_position().getX() / this.regionWidth);
	        int row = (int) (a.get_position().getY() / this.regionHeight);
	        return _regions[row][col];
	    }
	
	

	void update_animal_region(Animal a) { //Updates an animal's region if it has moved to a different area within the map.

	    // Find the current region of the animal
	    Region currentRegion = _animal_region.get(a);
	    //Find the new region based on the calculated row and column
	    Region newRegion = get_region_for_animal(a);
	    
	    //Compare and update only if necessary
	    if (currentRegion != newRegion) {
	        currentRegion.remove_animal(a); // Removes the animal from its current region
	        newRegion.add_animal(a); // Add the animal to the new region
	        _animal_region.put(a, newRegion); //Update the animal's mapping to its new region
	    }
	}



	public double get_food(Animal a, double dt) {         //provides food to an animal based on the region it's currently in and the elapsed time dt.
		Region r = _animal_region.get(a);
		return r.get_food(a, dt);
	}

	void update_all_regions(double dt) {     //Updates all regions
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				_regions[i][j].update(dt);
			}
		}
	}

	@Override
	public List<Animal> get_animals_in_range(Animal animal, Predicate<Animal> filter) {     //Retrieves a list of animals within the sight range of a given animal that also meet certain criteria 
		//First, it calculates the starting and ending column (startCol and endCol) and row (startRow and endRow) indices that define the rectangular area around the animal within its sight range
		int startCol = (int) (( animal.get_position().getX() - animal.get_sight_range()) / this.regionWidth);
	    if (startCol < 0) startCol = 0; 

	    int endCol = (int) (( animal.get_position().getX() + animal.get_sight_range()) / this.regionWidth);
	    if (endCol > this.cols - 1) endCol = this.cols - 1; 

	    int startRow = (int) (( animal.get_position().getY() - animal.get_sight_range()) / this.regionHeight);
	    if (startRow < 0) startRow = 0; 

	    int endRow = (int) (( animal.get_position().getY() + animal.get_sight_range()) / this.regionHeight);
	    if (endRow > this.rows - 1) endRow = this.rows - 1;

	    List<Animal> animalsInRange = new ArrayList<>();

	    //The method then iterates through each region within the calculated bounds
	    for (int row = startRow; row <= endRow; row++) {
	        for (int col = startCol; col <= endCol; col++) {
	            Region region = _regions[row][col];
	            for (Animal otherAnimal : region.getAnimals()) {
	                // Check if the other animal is within vision range and meets the filter
	                if (!otherAnimal.equals(animal) && animal.get_position().distanceTo(otherAnimal.get_position()) <= animal.get_sight_range() && filter.test(otherAnimal)) {
	                	//ADD THE ANIMAL 
	                    animalsInRange.add(otherAnimal);
	                }
	            }
	        }
	    }

	    return animalsInRange;
	}
	
	 public JSONObject as_JSON() {       // Provides a JSON representation of the regions and their statuses
	        JSONArray regionsArray = new JSONArray();
	        for (int i = 0; i < this.rows; i++) {
	            for (int j = 0; j < this.cols; j++) {
	                JSONObject regionObj = new JSONObject();
	                regionObj.put("row", i);
	                regionObj.put("col", j);
	                regionObj.put("data", _regions[i][j].as_JSON()); 
	                regionsArray.put(regionObj);
	            }
	        }

	        JSONObject result = new JSONObject();
	        result.put("regiones", regionsArray);
	        return result;
	    }

//////////GETTERS///////////////
	@Override
	public int get_cols() {
		return this.cols;
	}

	@Override
	public int get_rows() {
		return this.rows;
	}

	@Override
	public int get_width() {
		return this.width;
	}

	@Override
	public int get_height() {
		return this.height;
	}

	@Override
	public int get_region_width() {
		return this.regionWidth;
	}

	@Override
	public int get_region_height() {
		return this.regionHeight;
	}
/////////////////////////////////


	
}
