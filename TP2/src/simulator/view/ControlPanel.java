package simulator.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.control.Controller;
import simulator.launcher.Main;

class ControlPanel extends JPanel {
	private Controller _ctrl;
	private ChangeRegionsDialog _changeRegionsDialog;
	private TestDialog _dialog;
	
	private JToolBar _toolaBar;
	private JFileChooser _fc;
	private boolean _stopped = true;
	private JButton _quitButton;
	private JButton _loadButton;
	private JButton _runButton;
	private JButton _stopButton;
	private JButton _viewMapButton;
	private JButton adittionalButton;
	private JButton adittionalButton2;
	private JButton _changeRegionsButton;
	private JSpinner _stepsSpinner;
	private JTextField _deltaTimeField;
	
	private static final int default_value = 10000;
	private static final int min_value = 1;
	private static final int max_value = 10000;
	private static final int step_value = 1;
	

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();

	}

	private void initGUI() {

		setLayout(new BorderLayout());
		_toolaBar = new JToolBar();
		add(_toolaBar, BorderLayout.PAGE_START);

		_fc = new JFileChooser(); //here we implement the logic to load our file
		_fc.setCurrentDirectory(new File(System.getProperty("user.dir") + "/resources/examples"));

		initButtons();     //we initialise our buttons

		SpinnerNumberModel stepsModel = new SpinnerNumberModel(ControlPanel.default_value, ControlPanel.min_value, ControlPanel.max_value, ControlPanel.step_value); // we define our JSpinner
		_stepsSpinner = new JSpinner(stepsModel);
		_toolaBar.add(new JLabel(" Steps:"));

		_toolaBar.add(_stepsSpinner);
		_deltaTimeField = new JTextField(Main._delta_time.toString(), 5);
		_toolaBar.add(new JLabel(" Delta-time:"));
		_toolaBar.add(_deltaTimeField);

		_toolaBar.add(Box.createGlue());
		_toolaBar.addSeparator();
		_quitButton = new JButton();
		_quitButton.setToolTipText("Quit");
		_quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		_quitButton.addActionListener((e) -> ViewUtils.quit(this));
		_toolaBar.add(_quitButton);
	}

	////////////// auxiliary methods//////////////////////////

	private void initButtons() {     //here we initialise the buttons
		_loadButton = new JButton();        //Load button
		_loadButton.setToolTipText("Load file");              
		_loadButton.setIcon(new ImageIcon("resources/icons/open.png"));
		_loadButton.addActionListener(e -> loadFile());
		_toolaBar.add(_loadButton);

		_viewMapButton = new JButton();              //viewMapButton
		_viewMapButton.setToolTipText("View Map");
		_viewMapButton.setIcon(new ImageIcon("resources/icons/viewer.png"));
		_viewMapButton.addActionListener(e -> openMapWindow());
		_toolaBar.add(_viewMapButton);

		_changeRegionsDialog = new ChangeRegionsDialog(_ctrl);    //changeRegionDialogButton
		_changeRegionsButton = new JButton();
		_changeRegionsButton.setToolTipText("Change Regions");
		_changeRegionsButton.setIcon(new ImageIcon("resources/icons/regions.png"));
		_changeRegionsButton.addActionListener(e -> openChangeRegionsDialog());
		_toolaBar.add(_changeRegionsButton);

		_runButton = new JButton();           //run button
		_runButton.setToolTipText("runs the simulation");
		_runButton.setIcon(new ImageIcon("resources/icons/run.png"));
		_runButton.addActionListener(e -> {
			_stopped = false;
			startSimulation();

		});
		_toolaBar.add(_runButton);

		_stopButton = new JButton();    //stop button
		_stopButton.setToolTipText("stops the ismulatiin");
		_stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		_stopButton.addActionListener(e -> {
			stopSimulation();
		});
		_toolaBar.add(_stopButton);
		_stopButton.setEnabled(false);
		
		///////////////////////////////////////////////////////////////////////////////////////
		_dialog = new TestDialog(_ctrl);
		adittionalButton = new JButton();        
		adittionalButton.setToolTipText("blah blah");              
		adittionalButton.setIcon(new ImageIcon("resources/icons/run.png"));
		adittionalButton.addActionListener(e -> examen_method());
		_toolaBar.add(adittionalButton);
		
		
	   

	}
	

	private void examen_method() {
		if (_dialog != null) {
			_dialog.open(ViewUtils.getWindow(this));
		}
		
	}
	
	

	private void loadFile() {         //Here we load our file
		int result = _fc.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = _fc.getSelectedFile();
			try (FileInputStream fis = new FileInputStream(selectedFile)) {
				JSONTokener tokener = new JSONTokener(fis);
				JSONObject json = new JSONObject(tokener);
				_ctrl.load_data(json);
			} catch (Exception ex) {
				ViewUtils.showErrorMsg(this, "Error loading file: " + ex.getMessage());
			}
		}
	}

	private void run_sim(int n, double dt) {  //Here we run our simulation
		if (n > 0 && !_stopped) {
			try {
				long startTime = System.currentTimeMillis();
				_ctrl.advance(dt);
				long stepTimeMs = System.currentTimeMillis() - startTime;
				long delay = (long) (dt * 1000 - stepTimeMs);
				Thread.sleep(delay > 0 ? delay : 0);
				SwingUtilities.invokeLater(() -> run_sim(n - 1, dt));

			} catch (Exception e) {
				ViewUtils.showErrorMsg(this, "Error during simulation: " + e.getMessage());
				enableControlButtons(true);
				_stopped = true;
			}
		} else {
			enableControlButtons(true);
			_stopped = true;
		}
	}

	private void openMapWindow() {      //here we open our simulation map
		Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
		MapWindow mapWindow = new MapWindow(frame, _ctrl);
		mapWindow.setVisible(true);
	}

	private void startSimulation() {
	    enableControlButtons(false);
	    _stopped = false;
	    _stopButton.setEnabled(true);

	    int steps = (Integer) _stepsSpinner.getValue();
	    double deltaTime;

	    try {
	        deltaTime = Double.parseDouble(_deltaTimeField.getText());
	        if (deltaTime <= 0) {
	            throw new IllegalArgumentException("Delta time must be positive.");
	        }
	    } catch (NumberFormatException e) {
	        ViewUtils.showErrorMsg(this, "Delta time must be a valid number.");
	        enableControlButtons(true);
	        return;
	    } catch (IllegalArgumentException e) {
	        ViewUtils.showErrorMsg(this, e.getMessage());
	        enableControlButtons(true);
	        return;
	    }

	    run_sim(steps, deltaTime);
	}

	private void enableControlButtons(boolean enable) {    //here we implement the logic to enable/disable our buttons

		_loadButton.setEnabled(enable);
		_viewMapButton.setEnabled(enable);
		_changeRegionsButton.setEnabled(enable);
		_runButton.setEnabled(enable);
	}

	private void stopSimulation() {      //here we stop our simulation
		_stopped = true;
		enableControlButtons(true);
		_stopButton.setEnabled(false);
	}

	private void openChangeRegionsDialog() { //here we open our change region Dialog
		if (_changeRegionsDialog != null) {
			_changeRegionsDialog.open(ViewUtils.getWindow(this));
		}
	}
/////////////////////////////////////////////////////

}
