package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

import java.util.List;

import simulator.control.Controller;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

class StatusBar extends JPanel implements EcoSysObserver {
	private JLabel timeLabel;
	private JLabel animalCountLabel;
	private JLabel dimensionsLabel;
	private Controller _ctrl;

	StatusBar(Controller ctrl) {
		_ctrl = ctrl;
		_ctrl.addObserver(this);
		initGUI();
	}
	
	// Method to set up the GUI components of the status bar

	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT)); // Set layout
		this.setBorder(BorderFactory.createBevelBorder(1)); // Set border

		// Initialize and add the time label
		timeLabel = new JLabel("Time: 0.0");
		this.add(timeLabel);
		
		// Add a separator between time and animal count
		JSeparator timeSeparator = new JSeparator(JSeparator.VERTICAL);
		timeSeparator.setPreferredSize(new Dimension(10, 20));
		this.add(timeSeparator);

		// Initialize and add the animal count label
		animalCountLabel = new JLabel("Animals: 0");
		this.add(animalCountLabel);

		JSeparator animalSeparator = new JSeparator(JSeparator.VERTICAL);
		animalSeparator.setPreferredSize(new Dimension(10, 20));
		this.add(animalSeparator);

		// Add a separator between animal count and dimensions
		dimensionsLabel = new JLabel("Dimensions: N/A");
		this.add(dimensionsLabel);
	}
	
	
    //Updates the information displayed on the status bar.
	private void updateStatusBar(double time, MapInfo map, int animalCount) {
		SwingUtilities.invokeLater(() -> {
			timeLabel.setText(String.format("Time: %.2f", time));
			animalCountLabel.setText("Animals: " + animalCount);
			dimensionsLabel.setText(String.format("Dimensions: %dx%d, %dx%d", map.get_width(), map.get_height(),
					map.get_rows(), map.get_cols()));
		});
	}

	
	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		updateStatusBar(time, map, animals.size());
	}

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		updateStatusBar(time, map, animals.size());
	}

	@Override
	public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		updateStatusBar(time, map, animals.size());
	}

	@Override
	public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
	}

	@Override
	public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		updateStatusBar(time, map, animals.size());
	}
}
