package gui_system;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ProfesorCtrlPanel extends JPanel {
	private JTextField txtNumeProfesor;

	/**
	 * Create the panel.
	 */
	public ProfesorCtrlPanel() {
		setLayout(null);
		
		setSize(584, 119);
		
		JLabel lblNumeStudent = new JLabel("Nume profesor:");
		lblNumeStudent.setBounds(194, 52, 75, 14);
		add(lblNumeStudent);
		
		txtNumeProfesor = new JTextField();
		txtNumeProfesor.setBounds(279, 49, 102, 20);
		add(txtNumeProfesor);
		txtNumeProfesor.setColumns(10);

	}
	
	public void setFields(String numeProfesor) {
		txtNumeProfesor.setText(numeProfesor);
		
	}
	
	public Object[] getFields(boolean allowNull) {
		if(!allowNull) {
			if (txtNumeProfesor.getText().length() == 0)
				return null;
		}
		
		Object result[] = { txtNumeProfesor.getText() };
		
		return result;
	}
}
