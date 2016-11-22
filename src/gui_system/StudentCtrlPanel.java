package gui_system;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Component;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.ListDataListener;

import Services.GrupaService;
import Services.SubgrupaService;
import entity.Grupa;
import entity.Subgrupa;

public class StudentCtrlPanel extends JPanel {
	private JTextField txtNumeStudent;
	private JComboBox cbGrupa;
	private JComboBox cbSubgrupa;
	List<Grupa> allFromGrupa;
	List<Subgrupa> allFromSubgrupa;

	/**
	 * Create the panel.
	 */
	public StudentCtrlPanel() {
		setLayout(null);
		
		setSize(584, 119);
		
		JLabel lblNumeStudent = new JLabel("Nume student:");
		lblNumeStudent.setBounds(26, 52, 71, 14);
		add(lblNumeStudent);
		
		txtNumeStudent = new JTextField();
		txtNumeStudent.setBounds(107, 49, 102, 20);
		add(txtNumeStudent);
		txtNumeStudent.setColumns(10);
		allFromGrupa = GrupaService.getAllFromGrupa();
		allFromSubgrupa = SubgrupaService.getAllFromSubgrupa();
		JLabel lblGrupa = new JLabel("Grupa:");
		lblGrupa.setBounds(219, 52, 33, 14);
		add(lblGrupa);
		
		
		cbGrupa = new JComboBox<>(allFromGrupa.toArray());
		cbGrupa.setBounds(262, 49, 102, 20);
		add(cbGrupa);
		
		JLabel lblSubgrupa = new JLabel("Subgrupa:");
		lblSubgrupa.setBounds(374, 52, 55, 14);
		add(lblSubgrupa);
		
		cbSubgrupa = new JComboBox<>(allFromSubgrupa.toArray());
		cbSubgrupa.setBounds(439, 49, 102, 20);
		add(cbSubgrupa);
		
		loadCombos();

	}
	
	public void loadCombos() {
		//loadAllFromGrupa();
		//init combo boxes with info from db.
		//all of them.
		
		/*
		for (String grupa : grupe)
			cbGrupa.addItem(grupa);
		
		for (String subgrupa : subgrupe)
			cbGrupa.addItem(subgrupa);
		*/
	}
	
	public void setFields(String numeStudent, String grupa, String subgrupa) {
		txtNumeStudent.setText(numeStudent);
		
		cbGrupa.setSelectedItem(grupa);
		cbSubgrupa.setSelectedItem(subgrupa);
		
	}
	

	public Object[] getFields(boolean allowNull) {
		if(!allowNull) {
			if (txtNumeStudent.getText().length() == 0 || cbGrupa.getSelectedIndex() == -1 || cbSubgrupa.getSelectedIndex() == -1)
				return null;
		}
		
		Object result[] = { txtNumeStudent.getText(), cbGrupa.getSelectedItem(), cbSubgrupa.getSelectedItem() };
		
		return result;
	}
	
	public void resetFields() {
		txtNumeStudent.setText("");
		cbGrupa.setSelectedIndex(-1);
		cbSubgrupa.setSelectedIndex(-1);
	}
}
