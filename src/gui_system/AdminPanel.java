package gui_system;

import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.Point;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import main.MainFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;

public class AdminPanel extends JPanel {
	
	main.MainFrame parentFrame;
	private JTable selTable, mainTable;
	private AdminContext context;
	private JTextField txtNumeStudent;

	public AdminPanel() {
		setLayout(null);
		
		
		selTable = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		selTable.getTableHeader().setReorderingAllowed(false);
		selTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		selTable.setRowHeight(40);
		selTable.setCellSelectionEnabled(false);
		

		
		mainTable = new JTable();
		mainTable.getTableHeader().setReorderingAllowed(false);
		mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		mainTable.setRowHeight(30);
		mainTable.setCellSelectionEnabled(false);
		
		
		context = new AdminContext(selTable, mainTable);
		
		//private String selectionData[][] = { { "Student" }, { "Profesor" }, { "Disciplina" }, { "An" }, { "Grupa" }, 
		//		{ "Subgrupa" }, { "Modul" }, { "Prezenta" }, { "Saptamana" }, { "Semestru" } };
		
		selTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					JTable t =(JTable) me.getSource();
					DefaultTableModel model = (DefaultTableModel) t.getModel();
					Point p = me.getPoint();
					int row = t.rowAtPoint(p);
					String selectedValue = (String) model.getValueAt(row, 0);
					if (selectedValue == "Student")
						context.switchToStudent();
					else if (selectedValue == "Profesor")
						context.switchToProfesorModel();
					else if (selectedValue == "Disciplina")
						context.switchToDisciplinaModel();
					else if (selectedValue == "An")
						context.switchToAnModel();
					else if (selectedValue == "Grupa")
						context.switchToGrupaModel();
					else if (selectedValue == "Subgrupa")
						context.switchToSubgrupaModel();
					else if (selectedValue == "Modul")
						context.switchToModulModel();
					else if (selectedValue == "Prezenta")
						context.switchToPrezentaModel();
					else if (selectedValue == "Saptamana")
						context.switchToSaptamanaModel();
					else if (selectedValue == "Semestru")
						context.switchToSemestruModel();
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 156, 427);
		add(scrollPane);
		
		scrollPane.setViewportView(selTable);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(176, 11, 564, 320);
		add(scrollPane_1);
		
		scrollPane_1.setViewportView(mainTable);
		
		JPanel panelStudent = new JPanel();
		panelStudent.setBounds(263, 366, 314, 52);
		add(panelStudent);
		panelStudent.setLayout(null);
		
		JLabel lblNumeStudent = new JLabel("Nume student:");
		lblNumeStudent.setBounds(64, 3, 71, 14);
		panelStudent.add(lblNumeStudent);
		
		txtNumeStudent = new JTextField();
		txtNumeStudent.setBounds(145, 0, 102, 20);
		panelStudent.add(txtNumeStudent);
		txtNumeStudent.setColumns(10);
		
		JLabel lblGrupa = new JLabel("Grupa:");
		lblGrupa.setBounds(0, 35, 38, 14);
		panelStudent.add(lblGrupa);
		
		JComboBox cbGrupa = new JComboBox();
		cbGrupa.setBounds(40, 32, 102, 20);
		panelStudent.add(cbGrupa);
		
		JLabel lblSubgrupa = new JLabel("Subgrupa:");
		lblSubgrupa.setBounds(152, 35, 50, 14);
		panelStudent.add(lblSubgrupa);
		
		JComboBox cbSubgrupa = new JComboBox();
		cbSubgrupa.setBounds(212, 32, 102, 20);
		panelStudent.add(cbSubgrupa);
		
		JButton btnCautare = new JButton("Cautare");
		btnCautare.setBounds(651, 363, 89, 23);
		add(btnCautare);
		
		JButton btnAdaugare = new JButton("Adaugare");
		btnAdaugare.setBounds(651, 395, 89, 23);
		add(btnAdaugare);
		this.setPreferredSize(new Dimension(750,450));
	}
	
	public void setParentFrame(MainFrame frame) {
		parentFrame = frame;
	}
}
