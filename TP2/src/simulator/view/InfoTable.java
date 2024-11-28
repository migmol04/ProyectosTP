package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

public class InfoTable extends JPanel {
	String _title;
	TableModel _tableModel;

	InfoTable(String title, TableModel tableModel) {
		_title = title;
		_tableModel = tableModel;
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());

		// Create a line border with black color
		Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

		// Create a titled border with the line border
		TitledBorder titledBorder = BorderFactory.createTitledBorder(lineBorder, _title, TitledBorder.LEFT,
				TitledBorder.TOP, null, Color.BLACK);

		Border margin = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border compound = new CompoundBorder(titledBorder, margin);

		this.setBorder(compound);

		JTable table = new JTable(_tableModel);
		// Disable the display of grid lines
		table.setShowGrid(false);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		 // Create a JScrollPane, add the table to it
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 200));
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		// Add the scroll pane to the center of this panel.
		this.add(scrollPane, BorderLayout.CENTER);
	}
}
