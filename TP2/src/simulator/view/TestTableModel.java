package simulator.view;

import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;
import java.util.List;

import simulator.control.Controller;
import simulator.model.Animal.State;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

public class TestTableModel extends AbstractTableModel implements EcoSysObserver {

	class Info {
		double _time;
		int num_animals;
		List<AnimalInfo> animals;

		Info(double time, int numAnimals) {
			this._time = time;
			this.num_animals = numAnimals;
		}
		
	}

	private List<Info> _data;
	private Controller _ctrl;
	private final String[] columnNames = { "Time", "Number of Dead Animals" };

	public TestTableModel(Controller ctrl) {
		this._ctrl = ctrl;
		this._data = new ArrayList<>();
		this._ctrl.addObserver(this);

	}

	@Override
	public int getRowCount() {
		return _data.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Info info = _data.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return info._time;
		case 1:
			return info.num_animals;

		}

		return null;
	}
	
	 private void updateData(double time, List<AnimalInfo> animals) {
	      int num_animals = (int)animals.stream().filter(a -> a.get_state() == State.NORMAL).count();
	      _data.add(new Info(time,num_animals));
	      if(_data.size()> 100) {
	    	  _data.remove(0);
	      }
	      fireTableDataChanged();
	    }

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		updateData(time, animals);

	}

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		_data.clear();
		updateData(time, animals);
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
		updateData(time, animals);
		

	}

}
