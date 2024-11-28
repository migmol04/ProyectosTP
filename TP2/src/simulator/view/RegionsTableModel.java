package simulator.view;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;
import java.util.List;

import simulator.control.Controller;
import simulator.model.Animal;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

class RegionsTableModel extends AbstractTableModel implements EcoSysObserver {
	
	class Info {
	    int row;
	    int col;
	    String desc;
	    int[] diet;

	    Info(MapInfo.RegionData regionData) {
	        this.row = regionData.row();
	        this.col = regionData.col();
	        RegionInfo region = regionData.r();
	        this.desc = region.toString();
	        this.diet = new int[Animal.Diet.values().length];

	        for (Animal.Diet dietType : Animal.Diet.values()) {
	            this.diet[dietType.ordinal()] = (int) region.getAnimalsInfo().stream()
	                .filter(animal -> animal.get_diet() == dietType)
	                .count();
	        }
	    }
	}
	
	private List<Info> regionsData2;
    private Controller controller;
    private List<String> columnNames = new ArrayList<>();


    RegionsTableModel(Controller ctrl) {
        this.controller = ctrl;
        this.regionsData2 = new ArrayList<>();
        this.controller.addObserver(this);

        columnNames.add("Row");
        columnNames.add("Column");
        columnNames.add("Description");

        for (Animal.Diet diet : Animal.Diet.values()) {
            columnNames.add(diet.toString());
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    @Override
    public int getRowCount() {
        return regionsData2.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Info info = regionsData2.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return info.row;
            case 1:
                return info.col;
            case 2:
                return info.desc;
            default:
                int dietIndex = columnIndex - 3;
                if (dietIndex >= 0 && dietIndex < info.diet.length) {
                    return info.diet[dietIndex];
                } else {
                    return "N/A";
                }
        }
    }

    private void updateRegionData(MapInfo map) {
        SwingUtilities.invokeLater(() -> {
            regionsData2.clear();

            map.forEach(regionData -> regionsData2.add(new Info(regionData)));

            fireTableDataChanged();
        });
    }

    @Override
    public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
        updateRegionData(map);
    }

    @Override
    public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
        updateRegionData(map);
    }

    @Override
    public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
        updateRegionData(map);
    }

    @Override
    public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
        updateRegionData(map);
    }

    @Override
    public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
        // No action needed
    }
}
