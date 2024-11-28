package simulator.view;

import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;
import java.util.List;

import simulator.control.Controller;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

public class BabyTable extends AbstractTableModel implements EcoSysObserver {
	
	class Info{
		int _num_baby;
		double _time;
		
		Info(double time, int n){
			_time = time;
			_num_baby = n;
		}
	}
	
	private List<Info> _data;
	private Controller _ctrl;
	private  String columnName[] =  { "time", "NumBabies" };
	public BabyTable(Controller ctrl) {
		_ctrl = ctrl;
		_data = new ArrayList<>();
		_ctrl.addObserver(this);
		
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
    	 return columnName[column];
    }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Info info = _data.get(rowIndex);
		switch(columnIndex) {
		case 0:
			return info._time;
		case 1:
			return info._num_baby;
		}
		return null;
	}
	
	public void update(double time,  List<AnimalInfo> animals) {
		int numBaby = (int) animals.stream().filter(a -> a.get_age() == 0.0 && time != 0.0).count();
		_data.add(new Info(time, numBaby));
		if(_data.size() > 100) {
			_data.remove(0);
		}
		 fireTableDataChanged();
		
	}

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		_data.clear();
		update(time, animals);
		
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
	update(time, animals);
		
	}

	
}
