package simulator.view;

import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simulator.control.Controller;
import simulator.model.Animal;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

class SpeciesTableModel extends AbstractTableModel implements EcoSysObserver {
	private final Map<String, Integer[]> speciesSummary = new HashMap<>();
	 private List<String> speciesCodes = new ArrayList<>();
    private final String[] columnNames;
	private final Controller _ctrl;

	SpeciesTableModel(Controller ctrl) {
		this._ctrl = ctrl;
		this._ctrl.addObserver(this);
		Animal.State[] states = Animal.State.values();
        columnNames = new String[states.length + 1];
        columnNames[0] = "Species";
        for (int i = 0; i < states.length; i++) {
            columnNames[i + 1] = states[i].toString();
        }
		
	}

	@Override
	public int getRowCount() {
		  return speciesSummary.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
	 @Override
	    public Object getValueAt(int rowIndex, int columnIndex) {
	        String code = speciesCodes.get(rowIndex);
	        Integer[] counts = speciesSummary.get(code);

	        if (columnIndex == 0) {
	            return code; // The species name
	        } else {
	            return counts[columnIndex - 1] != null ? counts[columnIndex - 1] : 0;
	        }
	    }

	 private void updateSummary(List<AnimalInfo> animals) {
		    speciesSummary.clear();
		    speciesCodes.clear();

		    for (AnimalInfo animal : animals) {
		        String code = animal.get_genetic_code();
		        Animal.State state = animal.get_state();

		        if (!speciesSummary.containsKey(code)) {
		            speciesSummary.put(code, new Integer[Animal.State.values().length]);
		            speciesCodes.add(code); 
		        }

		        Integer[] stateCounts = speciesSummary.get(code);

		        int stateIndex = state.ordinal();
		        if (stateCounts[stateIndex] == null) {
		            stateCounts[stateIndex] = 1;
		        } else {
		            stateCounts[stateIndex]++;
		        }
		    }

		    fireTableDataChanged();
		}


	



	@Override
    public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
        updateSummary(animals);
    }

    @Override
    public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
        speciesSummary.clear();
        fireTableDataChanged();
    }

    @Override
    public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
        updateSummary(animals);
    }

    @Override
    public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
       
    }

    @Override
    public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
        updateSummary(animals);
    }
}