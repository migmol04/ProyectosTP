package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import simulator.model.Animal;
import simulator.model.AnimalInfo;
import simulator.model.MapInfo;

@SuppressWarnings("serial")
public class MapViewer extends AbstractMapViewer {

	// Anchura/altura/ de la simulación -- se supone que siempre van a ser iguales
	// al tamaño del componente
	private int _width;
	private int _height;

	// Número de filas/columnas de la simulación
	private int _rows;
	private int _cols;

	// Anchura/altura de una región
	int _rwidth;
	int _rheight;

	// Mostramos sólo animales con este estado. Los posibles valores de _currState
	// son null, y los valores deAnimal.State.values(). Si es null mostramos todo.
	Animal.State _currState;

	// En estos atributos guardamos la lista de animales y el tiempo que hemos
	// recibido la última vez para dibujarlos.
	volatile private Collection<AnimalInfo> _objs;
	volatile private Double _time;

	// Una clase auxilar para almacenar información sobre una especie
	private static class SpeciesInfo {
		private Integer _count;
		private Color _color;

		SpeciesInfo(Color color) {
			_count = 0;
			_color = color;
		}
	}

	// Un mapa para la información sobre las especies
	Map<String, SpeciesInfo> _kindsInfo = new HashMap<>();

	// El font que usamos para dibujar texto
	private Font _font = new Font("Arial", Font.BOLD, 12);

	// Indica si mostramos el texto la ayuda o no
	private boolean _showHelp;

	public MapViewer() {
		initGUI();
	}

	private void initGUI() {

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
				case 'h':
					_showHelp = !_showHelp;
					repaint();
					break;
				case 's':
					if (_currState == null) {
						_currState = Animal.State.values()[0];
					} else {
						int nextStateIndex = (_currState.ordinal() + 1) % Animal.State.values().length;
						_currState = (nextStateIndex == 0) ? null : Animal.State.values()[nextStateIndex];
					}
					repaint();
					break;
				default:
				}
			}

		});

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				requestFocus(); // Esto es necesario para capturar las teclas cuando el ratón está sobre este
								// componente.
			}
		});

		// Por defecto mostramos todos los animales
		_currState = null;

		// Por defecto mostramos el texto de ayuda
		_showHelp = true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g.setFont(_font);

		gr.setBackground(Color.WHITE);
		gr.clearRect(0, 0, _width, _height);

		if (_objs != null)
			drawObjects(gr, _objs, _time);

		if (_showHelp) {
			String helpText1 = "h: toggle help";
			String helpText2 = "s: show animals of a specific state";
			int x = 10;
			int y = 20;
			int lineSpacing = 20;

			gr.setColor(Color.RED);
			Font helpFont = new Font("Arial", Font.BOLD, 18); // Ajusta el tamaño como prefieras
			gr.setFont(helpFont);
			gr.drawString(helpText1, x, y);
			gr.drawString(helpText2, x, y + lineSpacing);

		}
	}

	private boolean visible(AnimalInfo a) {

		return _currState == null || a.get_state() == _currState;
	}

	private void drawObjects(Graphics2D g, Collection<AnimalInfo> animals, Double time) {
		 for (SpeciesInfo info : _kindsInfo.values()) {
		        info._count = 0;
		    }

		g.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i <= _rows; i++) {
			g.drawLine(0, i * _rheight, _width, i * _rheight);
		}
		for (int j = 0; j <= _cols; j++) {
			g.drawLine(j * _rwidth, 0, j * _rwidth, _height);
		}

		for (AnimalInfo a : animals) {

			if (!visible(a))
				continue;

			SpeciesInfo esp_info = _kindsInfo.get(a.get_genetic_code());
			if (esp_info == null) {
				// Add the species to the map and set the count to 1
				esp_info = new SpeciesInfo(ViewUtils.get_color(a.get_genetic_code()));
				_kindsInfo.put(a.get_genetic_code(), esp_info);
			} else {
				// Increment the count only if the species is already added
				esp_info._count++;
			}
			// Dibujar el animal
			int size = (int) (a.get_age() / 2 + 2); // Ajusta esta fórmula según necesites
			g.setColor(esp_info._color);
			g.fillOval((int) a.get_position().getX() - size / 2, (int) a.get_position().getY() - size / 2, size, size);

		}

		// TODO Dibujar la etiqueta del estado visible, sin no es null.

		// TODO Dibujar la etiqueta del tiempo. Para escribir solo 3 decimales puede
		// usar String.format("%.3f", time)
		int x = 10; // Margen izquierdo para empezar a dibujar
		int y = _height - 150; // Ajustar según la ubicación deseada para comenzar desde abajo hacia arriba
		for (Entry<String, SpeciesInfo> entry : _kindsInfo.entrySet()) {
			String species = entry.getKey();
			SpeciesInfo info = entry.getValue();
			  g.setColor(info._color);

			// Ajustamos la llamada para pasar los argumentos correctos
			drawStringWithRect(g, x, y, species + ": " + info._count);

			// Aumentamos la posición en 'y' para el próximo rectángulo
			y += g.getFontMetrics().getHeight() + 10;
		}

		Color magenta = new Color(255, 0, 255);

		// Dibuja la etiqueta del tiempo
		g.setColor(magenta);
		String tiempoStr = "Time: " + String.format("%.3f", _time);
		drawStringWithRect(g, x, y, tiempoStr);
		y += g.getFontMetrics().getHeight() + 10; // Ajusta el 'y' para el siguiente texto

		// Dibuja la etiqueta del estado actual, si no es null
		if (_currState != null) {
		    g.setColor(Color.BLUE);
		    String estadoStr = "State: " + _currState;
		    drawStringWithRect(g, x, y, estadoStr);
		    // No necesitas ajustar 'y' aquí a menos que vayas a dibujar más cosas debajo
		}
		
		

	}

	void drawStringWithRect(Graphics2D g, int x, int y, String s) {
		Rectangle2D rect = g.getFontMetrics().getStringBounds(s, g);
		g.drawString(s, x, y);
		g.drawRect(x - 1, y - (int) rect.getHeight(), (int) rect.getWidth() + 1, (int) rect.getHeight() + 5);
	}

	@Override
	public void update(List<AnimalInfo> objs, Double time) {
		this._objs = objs;
		this._time = time;
		repaint();
	}

	@Override
	public void reset(double time, MapInfo map, List<AnimalInfo> animals) {
		this._width = map.get_width();
		this._height = map.get_height();
		this._rows = map.get_rows();
		this._cols = map.get_cols();

		this._rwidth = this._width / this._cols;
		this._rheight = this._height / this._rows;
		setPreferredSize(new Dimension(map.get_width(), map.get_height()));
		update(animals, time);
	}

}
