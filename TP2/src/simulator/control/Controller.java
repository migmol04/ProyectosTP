package simulator.control;

import java.io.IOException;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.Simulator;
import simulator.view.SimpleObjectViewer;
import simulator.view.SimpleObjectViewer.ObjInfo;

public class Controller {

	private Simulator _sim;

	public Controller(Simulator sim) {
		this._sim = sim;

	}

	public void reset(int cols, int rows, int width, int height) {
		_sim.reset(cols, rows, width, height);
	}

	private void set_regions_from_json(JSONObject data) {
		// Process the regions if present.
		if (data.has("regions")) {
			JSONArray regions = data.getJSONArray("regions");
			for (int i = 0; i < regions.length(); i++) {
				JSONObject region = regions.getJSONObject(i);

				// Get the ranges of rows and columns.
				JSONArray rowRange = region.getJSONArray("row");
				int startRow = rowRange.getInt(0);
				int endRow = rowRange.getInt(1);

				JSONArray colRange = region.getJSONArray("col");
				int startCol = colRange.getInt(0);
				int endCol = colRange.getInt(1);

				// Get the region specification.
				JSONObject spec = region.getJSONObject("spec");

				// Set each region within the specified ranges.
				for (int row = startRow; row <= endRow; row++) {
					for (int col = startCol; col <= endCol; col++) {
						_sim.set_region(row, col, spec);

					}
				}
			}
		}

	}

	public void set_regions(JSONObject rs) {
		set_regions_from_json(rs);
	}

	public void advance(double dt) {
		_sim.advance(dt);
	}

	public void addObserver(EcoSysObserver o) {
		_sim.addObserver(o);
	}

	public void removeObserver(EcoSysObserver o) {
		_sim.removeObserver(o);
	}

	public void load_data(JSONObject data) {
		int cols = data.optInt("cols", 15);
		int rows = data.optInt("rows", 20);
		int width = data.optInt("width", 800);
		int height = data.optInt("height", 600);
		
		reset(cols, rows, width, height);

		// Process regions if present.
		set_regions_from_json(data);

		// Process animals if present.
		if (data.has("animals")) {
			JSONArray animals = data.getJSONArray("animals");
			for (int i = 0; i < animals.length(); i++) {
				JSONObject animalEntry = animals.getJSONObject(i);
				int amount = animalEntry.getInt("amount");
				JSONObject spec = animalEntry.getJSONObject("spec");
				for (int j = 0; j < amount; j++) {
					_sim.add_animal(spec);
				}
			}
		}
	}
	
	public void run(double t, double dt, boolean sv, OutputStream out) throws IOException {
	    // Runs the simulation for a specified time t, advancing it in steps of dt seconds.
	    JSONObject init_state = _sim.as_JSON();
	    SimpleObjectViewer view = null;
	    // It captures the initial state of the simulation and then enters a loop, repeatedly advancing the simulation and optionally updating a visual view if sv (show view) is true
	    if (sv) {
	        MapInfo m = _sim.get_map_info();
	        view = new SimpleObjectViewer("[ECOSYSTEM]", m.get_width(), m.get_height(), m.get_cols(), m.get_rows());
	        view.update(to_animals_info(_sim.get_animals()), _sim.get_time(), dt);
	    }

	    while (_sim.get_time() <= t) {
	        _sim.advance(dt);
	        if (sv) {
	            view.update(to_animals_info(_sim.get_animals()), _sim.get_time(), dt);
	        }
	    }

	    if (sv) {
	        view.close();
	    }
	    // Once the simulation time reaches or exceeds t, it captures the final state
	    
	    JSONObject final_state = _sim.as_JSON(); // Capture the final state of the simulation

	    // Construct the JSON object for output
	    JSONObject output = new JSONObject();
	    output.put("in", init_state);
	    output.put("out", final_state);

	    // It then constructs a JSON object containing the initial and final states and writes this to the provided OutputStream
	    String formattedJson = output.toString(2);
	    out.write(formattedJson.getBytes());
	    out.flush();
	    out.close();
	}

	private static List<ObjInfo> to_animals_info(List<? extends AnimalInfo> animals) {
		List<ObjInfo> ol = new ArrayList<>(animals.size());
		for (AnimalInfo a : animals)
			ol.add(new ObjInfo(a.get_genetic_code(), (int) a.get_position().getX(), (int) a.get_position().getY(),
					(int) Math.round(a.get_age()) + 2));
		return ol;
	}

}
