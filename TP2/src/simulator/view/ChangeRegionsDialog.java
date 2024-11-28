package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import simulator.control.Controller;
import simulator.launcher.Main;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

class ChangeRegionsDialog extends JDialog implements EcoSysObserver {
	private DefaultComboBoxModel<String> _regionsModel;
	private DefaultComboBoxModel<String> _fromRowModel;
	private DefaultComboBoxModel<String> _toRowModel;
	private DefaultComboBoxModel<String> _fromColModel;
	private DefaultComboBoxModel<String> _toColModel;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private List<JSONObject> _regionsInfo;
	private String[] _headers = { "Key", "Value", "Description" };
	private JComboBox<String> regionsCombo;
	private JComboBox<String> fromRowCombo;
	private JComboBox<String> toRowCombo;
	private JComboBox<String> fromColCombo;
	private JComboBox<String> toColCombo;
	private int _status = 0;

	ChangeRegionsDialog(Controller ctrl) {
		super((Frame) null, true);
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}

	private void initGUI() {
		setTitle("Change Regions");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		JPanel helpPanel = new JPanel(new BorderLayout());
		helpPanel.add(new JLabel(
				"<html><p>Select a region type, the rows/cols interval, and provide values for the parameters in the Value column (default values are used for parameters with no value)</p></html>"));
		mainPanel.add(helpPanel);

		_dataTableModel = new DefaultTableModel(_headers, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1;
			}
		};

		//We initialize the combo Boxes
		initializeComboBox();

		JTable dataTable = new JTable(_dataTableModel);
		JScrollPane tableScrollPane = new JScrollPane(dataTable);
		mainPanel.add(tableScrollPane);

		loadRegionsInfo();

		regionsCombo = new JComboBox<>(_regionsModel);

		regionsCombo = new JComboBox<>(_regionsModel);
		fromRowCombo = new JComboBox<>(_fromRowModel);
		toRowCombo = new JComboBox<>(_toRowModel);
		fromColCombo = new JComboBox<>(_fromColModel);
		toColCombo = new JComboBox<>(_toColModel);
		
		//we create the panel for our coordinates 
		JPanel coordsPanel = new JPanel();
		coordsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		coordsPanel.add(new JLabel("Region Type:"));
		coordsPanel.add(regionsCombo);
		coordsPanel.add(new JLabel("From Row:"));
		coordsPanel.add(fromRowCombo);
		coordsPanel.add(new JLabel("To Row:"));
		coordsPanel.add(toRowCombo);
		coordsPanel.add(new JLabel("From Col:"));
		coordsPanel.add(fromColCombo);
		coordsPanel.add(new JLabel("To Col:"));
		coordsPanel.add(toColCombo);
		mainPanel.add(coordsPanel);

		//We set up the button panel
		JPanel ButtonPanel = setupButtonPanel();
		mainPanel.add(ButtonPanel);
		regionsCombo.addActionListener(e -> updateDataTableModel());
		updateDataTableModel();

		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(false);
	}

	/////// auxiliary methods////////////////////

	private void initializeComboBox() {         //we create our combo boxes
		_regionsModel = new DefaultComboBoxModel<>();
		_fromRowModel = new DefaultComboBoxModel<>();
		_toRowModel = new DefaultComboBoxModel<>();
		_fromColModel = new DefaultComboBoxModel<>();
		_toColModel = new DefaultComboBoxModel<>();

	}

	private void loadRegionsInfo() {     //we load our region info
		_regionsInfo = Main._region_factory.get_info();
		_regionsModel.removeAllElements();
		for (JSONObject region : _regionsInfo) {
			_regionsModel.addElement(region.getString("type"));
		}
	}

	private void updateDataTableModel() {    //We update the selected region
		int selectedIndex = regionsCombo.getSelectedIndex();
		updateDataTableModel(selectedIndex);
	}

	private JPanel setupButtonPanel() {            //our button panel
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		okButton.addActionListener(e -> onOk());
		cancelButton.addActionListener(e -> onCancel());
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		return buttonPanel;
	}

	private void updateDataTableModel(int selectedIndex) {  //we update the selected region
		_dataTableModel.setRowCount(0);
		if (selectedIndex >= 0 && selectedIndex < _regionsInfo.size()) {
			JSONObject selectedRegion = _regionsInfo.get(selectedIndex);
			JSONObject data = selectedRegion.optJSONObject("data");
			for (String key : data.keySet()) {
				String description = data.optString(key);
				_dataTableModel.addRow(new Object[] { key, "", description });
			}
		}
	}
	
	private String getJSONString() {
	    StringBuilder jsonBuilder = new StringBuilder();
	    jsonBuilder.append("{");

	    for (int i = 0; i < _dataTableModel.getRowCount(); i++) {
	        String key = (String) _dataTableModel.getValueAt(i, 0);
	        String value = (String) _dataTableModel.getValueAt(i, 1);

	        if (!value.isEmpty()) {
	            jsonBuilder.append("\"").append(key).append("\"").append(":").append(value).append(",");
	        }
	    }

	    if (jsonBuilder.length() > 1) {
	        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);  // Remove trailing comma
	    }

	    jsonBuilder.append("}");
	    return jsonBuilder.toString();
	}

	private void onOk() {
	    try {
	        JSONArray regions = new JSONArray();
	        JSONObject region = new JSONObject();

	        Object fromRow = fromRowCombo.getSelectedItem();
	        Object toRow = toRowCombo.getSelectedItem();
	        Object fromCol = fromColCombo.getSelectedItem();
	        Object toCol = toColCombo.getSelectedItem();

	        if (fromRow != null && toRow != null && fromCol != null && toCol != null) {
	            region.put("row", new JSONArray().put(fromRow.toString()).put(toRow.toString()));
	            region.put("col", new JSONArray().put(fromCol.toString()).put(toCol.toString()));
	        }

	        String selectedType = (String) regionsCombo.getSelectedItem();
	        JSONObject spec = new JSONObject();
	        spec.put("type", selectedType);

	        JSONObject regionData = new JSONObject(getJSONString());
	        spec.put("data", regionData);

	        region.put("spec", spec);
	        regions.put(region);

	        JSONObject finalJson = new JSONObject().put("regions", regions);
	        _ctrl.set_regions(finalJson);
	        _status = 1;
	        setVisible(false);
	    } catch (Exception ex) {
	        ViewUtils.showErrorMsg(this, "Error processing request: " + ex.getMessage());
	    }
	}


	public void open(Frame parent) {
		setLocation(//
				parent.getLocation().x + parent.getWidth() / 2 - getWidth() / 2, //
				parent.getLocation().y + parent.getHeight() / 2 - getHeight() / 2);
		pack();
		setVisible(true);
	}

	private void updateComboBoxModels(MapInfo map) {  //We update the combo boxes
		_fromRowModel.removeAllElements();
		_toRowModel.removeAllElements();
		_fromColModel.removeAllElements();
		_toColModel.removeAllElements();

		int rows = map.get_rows();
		int cols = map.get_cols();

		for (int i = 0; i < rows; i++) {
			_fromRowModel.addElement(String.valueOf(i));
			_toRowModel.addElement(String.valueOf(i));
		}

		for (int j = 0; j < cols; j++) {
			_fromColModel.addElement(String.valueOf(j));
			_toColModel.addElement(String.valueOf(j));
		}
	}

	private void onCancel() { // cancel button
		_status = 0;
		setVisible(false);
	}

///////auxiliary methods////////////////////

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		updateComboBoxModels(map);
	}

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		updateComboBoxModels(map);
	}

	@Override
	public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {

	}

	@Override
	public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {

	}

	@Override
	public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {

	}

}
