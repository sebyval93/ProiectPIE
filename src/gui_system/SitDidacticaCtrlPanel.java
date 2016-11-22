package gui_system;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;


public class SitDidacticaCtrlPanel extends JPanel {
	
	private JComboBox<String> cbDisciplina, cbActivitate,
		cbProfesor, cbInterval, cbParticipanti;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the panel.
	 */
	public SitDidacticaCtrlPanel() {
		setLayout(null);
		
		setSize(734, 119);
		
		JPanel panel = new JPanel();
		panel.setBounds(109, 31, 516, 57);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNumeStudent = new JLabel("Disciplina:");
		lblNumeStudent.setBounds(0, 3, 47, 14);
		panel.add(lblNumeStudent);
		
		cbDisciplina = new JComboBox<String>();
		cbDisciplina.setBounds(85, 0, 94, 20);
		panel.add(cbDisciplina);
		cbDisciplina.setSelectedIndex(-1);
		
		JLabel lblActivitate = new JLabel("Activitate:");
		lblActivitate.setBounds(189, 3, 50, 14);
		panel.add(lblActivitate);
		
		cbActivitate = new JComboBox<String>();
		cbActivitate.setBounds(249, 0, 94, 20);
		panel.add(cbActivitate);
		cbActivitate.setSelectedIndex(-1);
		
		JLabel lblNumeProfesor = new JLabel("Nume profesor:");
		lblNumeProfesor.setBounds(0, 39, 75, 14);
		panel.add(lblNumeProfesor);
		
		cbProfesor = new JComboBox<String>();
		cbProfesor.setBounds(85, 36, 94, 20);
		panel.add(cbProfesor);
		
		JLabel lblInterval = new JLabel("Interval:");
		lblInterval.setBounds(189, 39, 42, 14);
		panel.add(lblInterval);
		
		cbInterval = new JComboBox<String>();
		cbInterval.setBounds(249, 36, 94, 20);
		panel.add(cbInterval);
		
		JLabel lblParticipanti = new JLabel("Participanti:");
		lblParticipanti.setBounds(355, 3, 57, 14);
		panel.add(lblParticipanti);
		
		cbParticipanti = new JComboBox<String>();
		cbParticipanti.setBounds(422, 0, 94, 20);
		panel.add(cbParticipanti);
		
		JRadioButton radioGrupa = new JRadioButton("Grupa");
		radioGrupa.setBounds(361, 34, 71, 23);
		panel.add(radioGrupa);
		radioGrupa.setSelected(true);
		buttonGroup.add(radioGrupa);
		
		JRadioButton radioSubgrupa = new JRadioButton("Subgrupa");
		radioSubgrupa.setBounds(432, 34, 71, 23);
		panel.add(radioSubgrupa);
		buttonGroup.add(radioSubgrupa);

		loadCombos();
		
	}
	
	public void loadCombos() {
		//init combo boxes with info from db.
		//all of them.
	}
	
	public void setFields(String disciplina, String activitate, String numeProfesor,
			String interval, String participanti) {
		cbDisciplina.setSelectedItem(disciplina);
		cbActivitate.setSelectedItem(activitate);
		cbProfesor.setSelectedItem(numeProfesor);
		cbInterval.setSelectedItem(interval);
		cbParticipanti.setSelectedItem(participanti);
		
		
	}
	
	public Object[] getFields(boolean allowNull) {
		if(!allowNull) {
			if (cbDisciplina.getSelectedIndex() == -1 || cbActivitate.getSelectedIndex() == -1
					|| cbProfesor.getSelectedIndex() == -1 || cbInterval.getSelectedIndex() == -1
					|| cbParticipanti.getSelectedIndex() == -1)
				return null;
		}
		
		Object result[] = { cbDisciplina.getSelectedItem(), cbActivitate.getSelectedItem(),
				cbProfesor.getSelectedItem(), cbProfesor.getSelectedItem(), cbParticipanti.getSelectedItem()
		};
		
		return result;
	}
	
	public void resetFields() {
		cbDisciplina.setSelectedIndex(-1);
		cbActivitate.setSelectedIndex(-1);
		cbProfesor.setSelectedIndex(-1);
		cbInterval.setSelectedIndex(-1);
		cbParticipanti.setSelectedIndex(-1);
	}
}
