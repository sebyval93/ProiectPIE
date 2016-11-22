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
		
		setSize(584, 119);
		
		JLabel lblNumeStudent = new JLabel("Disciplina:");
		lblNumeStudent.setBounds(30, 34, 47, 14);
		add(lblNumeStudent);
		
		cbDisciplina = new JComboBox<String>();
		cbDisciplina.setSelectedIndex(-1);
		cbDisciplina.setBounds(115, 31, 94, 20);
		add(cbDisciplina);
		
		JLabel lblActivitate = new JLabel("Activitate:");
		lblActivitate.setBounds(219, 34, 50, 14);
		add(lblActivitate);
		
		cbActivitate = new JComboBox<String>();
		cbActivitate.setSelectedIndex(-1);
		cbActivitate.setBounds(279, 31, 94, 20);
		add(cbActivitate);
		
		JLabel lblNumeProfesor = new JLabel("Nume profesor:");
		lblNumeProfesor.setBounds(30, 70, 75, 14);
		add(lblNumeProfesor);
		
		cbProfesor = new JComboBox<String>();
		cbProfesor.setBounds(115, 67, 94, 20);
		add(cbProfesor);
		
		JLabel lblInterval = new JLabel("Interval:");
		lblInterval.setBounds(219, 70, 42, 14);
		add(lblInterval);
		
		cbInterval = new JComboBox<String>();
		cbInterval.setBounds(279, 67, 94, 20);
		add(cbInterval);
		
		JLabel lblParticipanti = new JLabel("Participanti:");
		lblParticipanti.setBounds(385, 34, 57, 14);
		add(lblParticipanti);
		
		cbParticipanti = new JComboBox<String>();
		cbParticipanti.setBounds(452, 31, 94, 20);
		add(cbParticipanti);
		
		JRadioButton radioGrupa = new JRadioButton("Grupa");
		radioGrupa.setSelected(true);
		buttonGroup.add(radioGrupa);
		radioGrupa.setBounds(391, 65, 71, 23);
		add(radioGrupa);
		
		JRadioButton radioSubgrupa = new JRadioButton("Subgrupa");
		buttonGroup.add(radioSubgrupa);
		radioSubgrupa.setBounds(462, 65, 71, 23);
		add(radioSubgrupa);

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
}
