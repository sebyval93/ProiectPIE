package gui_system;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import Services.DisciplinaService;
import Services.GrupaService;
import Services.ProfesorService;
import Services.SubgrupaService;
import entity.Disciplina;
import entity.Grupa;
import entity.Profesor;
import entity.Subgrupa;

import javax.swing.JRadioButton;

import java.util.Arrays;
import java.util.List;

import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class SitDidacticaCtrlPanel extends JPanel {
	
	private JComboBox<String> cbDisciplina, cbActivitate,
		cbProfesor, cbInterval, cbParticipanti;
	
	private JRadioButton radioGrupa, radioSubgrupa;
	
	/*
	 * 0 curs        0 impar
	 * 1 seminar     1 par
	 * 2 laborator   2 saptamanal
	 * 3 proiect
	 */
	
	private enum Activitate {
		CURS,
		SEMINAR,
		LABORATOR,
		PROIECT
	}
	
	private enum Interval {
		IMPAR,
		PAR,
		SAPTAMANAL
	}
	
	private List<Grupa> allFromGrupa;
	private List<Subgrupa> allFromSubgrupa;
	private List<Disciplina> allFromDisciplina;
	private List<Profesor> allFromProfesor;
	private List<String> activitati;
	private List<String> interval;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the panel.
	 */
	public SitDidacticaCtrlPanel() {
		setLayout(null);
		
		setSize(734, 119);
		
		JPanel panel = new JPanel();
		panel.setBounds(17, 31, 700, 57);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNumeStudent = new JLabel("Disciplina:");
		lblNumeStudent.setBounds(0, 3, 47, 14);
		panel.add(lblNumeStudent);
		
		cbDisciplina = new JComboBox<String>();
		cbDisciplina.setBounds(85, 0, 280, 20);
		panel.add(cbDisciplina);
		cbDisciplina.setSelectedIndex(-1);
		
		JLabel lblActivitate = new JLabel("Activitate:");
		lblActivitate.setBounds(375, 3, 50, 14);
		panel.add(lblActivitate);
		
		cbActivitate = new JComboBox<String>();
		cbActivitate.setBounds(435, 0, 94, 20);
		panel.add(cbActivitate);
		cbActivitate.setSelectedIndex(-1);
		
		JLabel lblNumeProfesor = new JLabel("Nume profesor:");
		lblNumeProfesor.setBounds(0, 39, 75, 14);
		panel.add(lblNumeProfesor);
		
		cbProfesor = new JComboBox<String>();
		cbProfesor.setBounds(85, 36, 280, 20);
		panel.add(cbProfesor);
		
		JLabel lblInterval = new JLabel("Interval:");
		lblInterval.setBounds(383, 39, 42, 14);
		panel.add(lblInterval);
		
		cbInterval = new JComboBox<String>();
		cbInterval.setBounds(435, 36, 94, 20);
		panel.add(cbInterval);
		
		JLabel lblParticipanti = new JLabel("Participanti:");
		lblParticipanti.setBounds(539, 3, 57, 14);
		panel.add(lblParticipanti);
		
		cbParticipanti = new JComboBox<String>();
		cbParticipanti.setBounds(606, 0, 94, 20);
		panel.add(cbParticipanti);
		
		radioGrupa = new JRadioButton("Grupa");
		radioGrupa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadGrupa();
			}
		});
		radioGrupa.setBounds(556, 35, 71, 23);
		panel.add(radioGrupa);
		radioGrupa.setSelected(true);
		buttonGroup.add(radioGrupa);
		
		radioSubgrupa = new JRadioButton("Subgrupa");
		radioSubgrupa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadSubgrupa();
			}
		});
		radioSubgrupa.setBounds(629, 35, 71, 23);
		panel.add(radioSubgrupa);
		buttonGroup.add(radioSubgrupa);
		
		activitati = Arrays.asList("Curs", "Seminar", "Laborator", "Proiect");
		interval = Arrays.asList("Impar", "Par", "Saptamanal");

		loadCombos();
		
	}
	
	public void loadCombos() {
		//init combo boxes with info from db.
		//all of them.
		
		allFromGrupa = GrupaService.getAllFromGrupa();
		allFromSubgrupa = SubgrupaService.getAllFromSubgrupa();
		allFromDisciplina = DisciplinaService.getAllFromDisciciplina();
		allFromProfesor = ProfesorService.getAllFromProfesor();
		
		allFromProfesor.remove("admin");
		
		cbDisciplina.removeAllItems();
		for (Object disciplina : allFromDisciplina.toArray())
			cbDisciplina.addItem(((Disciplina) disciplina).getDenumire().toString());
		
		cbProfesor.removeAllItems();
		for (Object profesor : allFromProfesor.toArray())
			cbProfesor.addItem(((Profesor) profesor).getNume().toString());
		
		cbActivitate.removeAllItems();
		for (Object activitate : activitati.toArray())
			cbActivitate.addItem(activitate.toString());
		
		cbInterval.removeAllItems();
		for (Object interval : interval.toArray())
			cbInterval.addItem(interval.toString());
		
		if (radioGrupa.isSelected()) {
			cbParticipanti.removeAllItems();
			for (Object grupa : allFromGrupa.toArray())
				cbParticipanti.addItem(grupa.toString());
		}
		else if (radioSubgrupa.isSelected()) {
			cbParticipanti.removeAllItems();
			for (Object subgrupa : allFromSubgrupa.toArray())
				cbParticipanti.addItem(subgrupa.toString());
		}
		
		cbParticipanti.setSelectedIndex(-1);
	}
	
	public void loadGrupa() {
		if (allFromGrupa != null) {
			cbParticipanti.removeAllItems();
			for (Object grupa : allFromGrupa.toArray())
				cbParticipanti.addItem(grupa.toString());
			cbParticipanti.setSelectedIndex(-1);
		}
	}
	
	public void loadSubgrupa() {
		if (allFromSubgrupa != null) {
			cbParticipanti.removeAllItems();
			for (Object subgrupa : allFromSubgrupa.toArray())
				cbParticipanti.addItem(subgrupa.toString());
			cbParticipanti.setSelectedIndex(-1);
		}
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
	
	public Disciplina getSelectedDisciplina() {
		for (Disciplina disciplina : allFromDisciplina) {
			if (disciplina.getDenumire().toString().equals(cbDisciplina.getSelectedItem().toString())) {
				return disciplina;
			}
		}
		
		return null;
	}
	
	public Profesor getSelectedProfesor() {
		for (Profesor profesor : allFromProfesor) {
			if (profesor.getNume().toString().equals(cbProfesor.getSelectedItem().toString())) {
				return profesor;
			}
		}
		
		return null;
	}
	
	public String getSelectedActivitate() {
		if (cbActivitate.getSelectedIndex() == -1)
			return null;
		else 
			return (String) cbActivitate.getSelectedItem();
	}
	
	public String getSelectedParticipanti() {
		if (cbParticipanti.getSelectedIndex() == -1)
			return null;
		else 
			return (String) cbParticipanti.getSelectedItem();
	}
	
	public int getSelectedInterval() {
		if (cbInterval.getSelectedIndex() == -1)
			return -1;
		else {
			switch ((String)cbInterval.getSelectedItem()) {
			case "Impar":
			case "IMPAR":
				return Interval.IMPAR.ordinal();
			case "Par":
			case "PAR":
				return Interval.PAR.ordinal();
			case "Saptamanal":
			case "SAPTAMANAL":
				return Interval.SAPTAMANAL.ordinal();
			}
			
			//invalid value
			return -1;
		}
	}
	
	public boolean allFieldsSelected() {
		if (cbDisciplina.getSelectedIndex() == -1 || cbActivitate.getSelectedIndex() == -1
				|| cbProfesor.getSelectedIndex() == -1 || cbInterval.getSelectedIndex() == -1
				|| cbParticipanti.getSelectedIndex() == -1)
			return false;
		else
			return true;
	}
}
