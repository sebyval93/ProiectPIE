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
		
		setSize(734, 119);
		
		JPanel panel = new JPanel();
		panel.setBounds(228, 49, 277, 20);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNumeStudent = new JLabel("Nume profesor:");
		lblNumeStudent.setBounds(0, 3, 75, 14);
		panel.add(lblNumeStudent);
		
		txtNumeProfesor = new JTextField();
		txtNumeProfesor.setBounds(85, 0, 192, 20);
		panel.add(txtNumeProfesor);
		txtNumeProfesor.setColumns(10);

	}
	
	public void setFields(String numeProfesor) {
		txtNumeProfesor.setText(numeProfesor);
		
	}
	
	
	
	public String getNumeProfesor() {
		return txtNumeProfesor.getText();
	}

	public Object[] getFields(boolean allowNull) {
		if(!allowNull) {
			if (txtNumeProfesor.getText().length() == 0)
				return null;
		}
		
		Object result[] = { txtNumeProfesor.getText() };
		
		return result;
	}
	
	public void resetFields() {
		txtNumeProfesor.setText("");
	}
}
