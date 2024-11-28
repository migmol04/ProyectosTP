package simulator.view;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import simulator.control.Controller;

public class TestDialog extends JDialog  {

    private Controller _ctrl;
    private TestTableModel _tableModel;
    private JTable _table;
    private JButton _cancelButton;

    public TestDialog(Controller ctrl) {
        this._ctrl = ctrl;
        _tableModel = new TestTableModel(_ctrl);
        _table = new JTable(_tableModel);
        initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        
      
        add(new JScrollPane(_table), BorderLayout.CENTER);
        
        
        JPanel buttonPanel = new JPanel();
        _cancelButton = new JButton("Cancel");
        _cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(_cancelButton);
        
        add(buttonPanel, BorderLayout.PAGE_END);

        setSize(600, 400);
    }


	public void open(Frame parent) {
        setLocation(
                parent.getLocation().x + parent.getWidth() / 2 - getWidth() / 2,
                parent.getLocation().y + parent.getHeight() / 2 - getHeight() / 2);
        setVisible(true);
    }

   
}
