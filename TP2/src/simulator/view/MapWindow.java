package simulator.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.List;

import simulator.control.Controller;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

class MapWindow extends JFrame implements EcoSysObserver {
	private Controller _ctrl;
	private AbstractMapViewer _viewer;
	private Frame _parent;

	MapWindow(Frame parent, Controller ctrl) {
		super("[MAP VIEWER]");
		_ctrl = ctrl;
		_parent = parent;
		_ctrl.addObserver(this); 
		intiGUI();
// TODO registrar this como observador
	}

	private void intiGUI() {
		 // Configura el panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // Crea y añade el viewer al panel principal
        _viewer = new MapViewer();  // Asume que MapViewer es tu implementación concreta de AbstractMapViewer
        mainPanel.add(_viewer, BorderLayout.CENTER);
// TODO poner contentPane como mainPanel
// TODO crear el viewer y añadirlo a mainPanel (en el centro)
// TODO en el método windowClosing, eliminar ‘MapWindow.this’ de los
// observadores
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                _ctrl.removeObserver(MapWindow.this); // Esto asegura que el observador sea eliminado correctamente
                MapWindow.this.dispose(); // Cierra la ventana
            }
        });
		pack();
		if (_parent != null)
			setLocation(_parent.getLocation().x + _parent.getWidth() / 2 - getWidth() / 2,
					_parent.getLocation().y + _parent.getHeight() / 2 - getHeight() / 2);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
	    SwingUtilities.invokeLater(() -> {
	        _viewer.reset(time, map, animals);
	        pack(); // Esto asegura que la ventana cambie de tamaño para ajustarse al contenido.
	    });
	}

	@Override
	public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
	    SwingUtilities.invokeLater(() -> {
	        _viewer.reset(time, map, animals);
	        pack(); // Igual que arriba, asegura que la ventana se ajuste al nuevo contenido.
	    });
	}

	@Override
	public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
	    SwingUtilities.invokeLater(() -> {
	        _viewer.update(animals, time);
	    });
	}

	@Override
	public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
	 
	}

	@Override
	public void onAvanced(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
	    SwingUtilities.invokeLater(() -> {
	        _viewer.update(animals, time);
	    });
	}

// TODO otros métodos van aquí….
}
