package gui_system;

import javax.swing.JPanel;
import java.awt.Dimension;

import java.awt.Point;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import main.MainFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.CardLayout;

public class AdminPanel extends JPanel {
	
	main.MainFrame parentFrame;
	private JTable selTable, mainTable;
	private AdminContext context;
	private StudentCtrlPanel studentiCtrlPanel;
	private DisciplinaCtrlPanel disciplinaCtrlPanel;
	private ProfesorCtrlPanel profesorCtrlPanel;
	private SitDidacticaCtrlPanel sitDidacticaCtrlPanel;
	private JPanel ctrlPanel;
	private CardLayout cardLayout;
	private JPanel coverPanel;

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
		
		cardLayout = new CardLayout();
		
		coverPanel = new JPanel();
		coverPanel.setBounds(156, 320, 584, 119);
		add(coverPanel);
		
		ctrlPanel = new JPanel();
		ctrlPanel.setBounds(156, 320, 584, 119);
		add(ctrlPanel);
		ctrlPanel.setLayout(cardLayout);
		
		addCtrlPanels();
		
		//private String selectionData[][] = { { "Student" }, { "Profesor" }, { "Disciplina" }, { "An" }, { "Grupa" }, 
		//		{ "Subgrupa" }, { "Modul" }, { "Prezenta" }, { "Saptamana" }, { "Semestru" } };
		
		selTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					JTable t =(JTable) me.getSource();
					DefaultTableModel model = (DefaultTableModel) t.getModel();
					Point p = me.getPoint();
					int row = t.rowAtPoint(p);
					try {
						String selectedValue = (String) model.getValueAt(row, 0);
						if (selectedValue == "Student") {
							context.switchToStudent();
							showStudentCtrlPanel();
						}
						else if (selectedValue == "Profesor") {
							context.switchToProfesorModel();
							showProfesorCtrlPanel();
						}
						else if (selectedValue == "Disciplina") {
							context.switchToDisciplinaModel();
							showDisciplinaCtrlPanel();
						}
						else if (selectedValue == "Situatie didactica") {
							context.switchToModulModel();
							showSitDidacticaCtrlPanel();
						}
					} 
					catch(Exception e) {
						//mysterious out of bounds error
					}
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 136, 298);
		add(scrollPane);
		
		scrollPane.setViewportView(selTable);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(156, 11, 584, 298);
		add(scrollPane_1);
		
		scrollPane_1.setViewportView(mainTable);
		
		JButton btnCautare = new JButton("Cautare");
		btnCautare.setBounds(32, 320, 89, 23);
		add(btnCautare);
		
		JButton btnAdaugare = new JButton("Adaugare");
		btnAdaugare.setBounds(32, 347, 89, 23);
		add(btnAdaugare);
		this.setPreferredSize(new Dimension(750,450));
		
		JButton btnModificare = new JButton("Modificare");
		btnModificare.setEnabled(false);
		btnModificare.setBounds(32, 374, 89, 23);
		add(btnModificare);
		
		JButton btnStergere = new JButton("Stergere");
		btnStergere.setEnabled(false);
		btnStergere.setBounds(32, 401, 89, 23);
		add(btnStergere);
		
	}
	
	public void addCtrlPanels() {
		ctrlPanel.add(coverPanel, "");
		
		studentiCtrlPanel = new StudentCtrlPanel();
		studentiCtrlPanel.setBounds(156, 320, 584, 119);
		ctrlPanel.add(studentiCtrlPanel, "studenti");
		
		disciplinaCtrlPanel = new DisciplinaCtrlPanel();
		disciplinaCtrlPanel.setBounds(156, 320, 584, 119);
		ctrlPanel.add(disciplinaCtrlPanel, "disciplina");
		
		profesorCtrlPanel = new ProfesorCtrlPanel();
		profesorCtrlPanel.setBounds(156, 320, 584, 119);
		ctrlPanel.add(profesorCtrlPanel, "profesor");
		
		sitDidacticaCtrlPanel = new SitDidacticaCtrlPanel();
		sitDidacticaCtrlPanel.setBounds(156, 320, 584, 119);
		ctrlPanel.add(sitDidacticaCtrlPanel, "situatie");
	}
	
	public void setParentFrame(MainFrame frame) {
		parentFrame = frame;
	}
	
	public void showStudentCtrlPanel() {
		resetAllFields();
		cardLayout.show(ctrlPanel, "studenti");
	}
	
	public void showDisciplinaCtrlPanel() {
		resetAllFields();
		cardLayout.show(ctrlPanel, "disciplina");
	}
	
	public void showProfesorCtrlPanel() {
		resetAllFields();
		cardLayout.show(ctrlPanel, "profesor");
	}
	
	public void showSitDidacticaCtrlPanel() {
		resetAllFields();
		cardLayout.show(ctrlPanel, "situatie");
	}
	
	public void resetAllFields() {
		studentiCtrlPanel.resetFields();
		disciplinaCtrlPanel.resetFields();
		profesorCtrlPanel.resetFields();
		sitDidacticaCtrlPanel.resetFields();
	}
	
}
