package gui_system;

import javax.swing.JPanel;
import java.awt.Dimension;

import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Services.ModulService;
import entity.Disciplina;
import entity.Profesor;
import main.MainFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	private JButton btnCautare, btnAdaugare,
		btnModificare, btnStergere;

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
		

		
		mainTable = new JTable(){
			private Border outside = new MatteBorder(1, 0, 1, 0, Color.RED);
			private Border inside = new EmptyBorder(0, 1, 0, 1);
			private Border highlight = new CompoundBorder(outside, inside);

			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				Component c = super.prepareRenderer(renderer, row, column);
				JComponent jc = (JComponent)c;

				// Add a border to the selected row

				if (isRowSelected(row))
					jc.setBorder( highlight );

				return c;
			}
		};
		mainTable.getTableHeader().setReorderingAllowed(false);
		mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		mainTable.setRowHeight(30);
		mainTable.setCellSelectionEnabled(false);
		
		
		context = new AdminContext(selTable, mainTable);
		
		cardLayout = new CardLayout();
		
		coverPanel = new JPanel();
		coverPanel.setBounds(156, 320, 734, 119);
		//add(coverPanel);
		
		ctrlPanel = new JPanel();
		ctrlPanel.setBounds(156, 320, 734, 119);
		add(ctrlPanel);
		ctrlPanel.setLayout(cardLayout);
		
		addCtrlPanels();
		
		//private String selectionData[][] = { { "Student" }, { "Profesor" }, { "Disciplina" }, { "An" }, { "Grupa" }, 
		//		{ "Subgrupa" }, { "Modul" }, { "Prezenta" }, { "Saptamana" }, { "Semestru" } };
		
		btnCautare = new JButton("Cautare");
		btnCautare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					actiuneCautare();
			}
		});
		btnCautare.setBounds(32, 326, 89, 23);
		add(btnCautare);
		
		btnAdaugare = new JButton("Adaugare");
		btnAdaugare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actiuneAdaugare();
			}
		});
		btnAdaugare.setBounds(32, 353, 89, 23);
		add(btnAdaugare);
		this.setPreferredSize(new Dimension(900, 450));
		
		btnModificare = new JButton("Modificare");
		btnModificare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actiuneModificare();
			}
		});
		btnModificare.setEnabled(false);
		btnModificare.setBounds(32, 380, 89, 23);
		add(btnModificare);
		
		btnStergere = new JButton("Stergere");
		btnStergere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actiuneStergere();
			}
		});
		btnStergere.setEnabled(false);
		btnStergere.setBounds(32, 407, 89, 23);
		add(btnStergere);
		
		mainTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					JTable t =(JTable) me.getSource();
					DefaultTableModel model = (DefaultTableModel) t.getModel();
					Point p = me.getPoint();
					int row = t.rowAtPoint(p);
					if (context.getCurrentModelName().equals("studentModel")) {
						studentiCtrlPanel.setFields((String)model.getValueAt(row, 0), (String)model.getValueAt(row, 1), 
								(String)model.getValueAt(row, 2));
						enableEditButtons(true);
					}
					else if (context.getCurrentModelName().equals("profesorModel")) {
						profesorCtrlPanel.setFields((String)model.getValueAt(row, 0));
						enableEditButtons(true);
					}
					else if (context.getCurrentModelName().equals("disciplinaModel")) {
						disciplinaCtrlPanel.setFields((String)model.getValueAt(row, 0), model.getValueAt(row, 1).toString(), 
								model.getValueAt(row, 2).toString(), model.getValueAt(row, 3).toString(), model.getValueAt(row, 4).toString(), 
								model.getValueAt(row, 5).toString(), (String)model.getValueAt(row, 6));
						enableEditButtons(true);
					}
					else if (context.getCurrentModelName().equals("modulModel")) {
						sitDidacticaCtrlPanel.setFields((String)model.getValueAt(row, 0), (String)model.getValueAt(row, 1), 
								(String)model.getValueAt(row, 2), (String)model.getValueAt(row, 3), (String)model.getValueAt(row, 4));
						
						enableEditButtons(true);
					}
				}
			}
		});
		
		selTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					JTable t =(JTable) me.getSource();
					DefaultTableModel model = (DefaultTableModel) t.getModel();
					Point p = me.getPoint();
					int row = t.rowAtPoint(p);
					enableEditButtons(false);
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
							
							setDisciplinaTableWidth();
							
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
		scrollPane.setBounds(10, 11, 136, 295);
		add(scrollPane);
		
		scrollPane.setViewportView(selTable);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(156, 11, 734, 295);
		add(scrollPane_1);
		
		scrollPane_1.setViewportView(mainTable);
		
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
	
	private void setDisciplinaTableWidth() {
		mainTable.getColumnModel().getColumn(1).setMinWidth(40);
		mainTable.getColumnModel().getColumn(1).setMaxWidth(40);
		
		for (int i = 2; i < 6; ++i) {
			mainTable.getColumnModel().getColumn(i).setMinWidth(75);
			mainTable.getColumnModel().getColumn(i).setMaxWidth(75);
		}
		mainTable.getColumnModel().getColumn(6).setMinWidth(65);
		mainTable.getColumnModel().getColumn(6).setMaxWidth(65);
	}
	
	private void enableEditButtons(boolean enable) {
		btnModificare.setEnabled(enable);
		btnStergere.setEnabled(enable);
	}
	
	private void actiuneAdaugare() {
		switch(context.getCurrentModelName()) {
		case "studentModel":
			break;
		case "profesorModel":
			break;
		case "disciplinaModel":
			break;
		case "modulModel":
			Object fields[] = sitDidacticaCtrlPanel.getFields(false);
			if (fields == null){
				System.out.println("Could not add modul to database. Found invalid field.");
				break;
			}
			else {
				Disciplina disciplina = sitDidacticaCtrlPanel.getSelectedDisciplina();
				Profesor profesor = sitDidacticaCtrlPanel.getSelectedProfesor();
				String activitate = sitDidacticaCtrlPanel.getSelectedActivitate();
				String participanti = sitDidacticaCtrlPanel.getSelectedParticipanti();
				int interval = sitDidacticaCtrlPanel.getSelectedInterval();
				
				if (disciplina != null || profesor != null || activitate != null || participanti != null || interval != -1)
					ModulService.addModul(disciplina, profesor, activitate, participanti, interval);
				else
					System.out.println("Could not add modul to database. Found invalid field.");
			}
			break;
		}
	}
	
	private void actiuneCautare() {
		switch(context.getCurrentModelName()) {
		case "studentModel":
			break;
		case "profesorModel":
			break;
		case "disciplinaModel":
			break;
		case "modulModel":
			break;
		}
	}
	
	private void actiuneModificare() {
		switch(context.getCurrentModelName()) {
		case "studentModel":
			break;
		case "profesorModel":
			break;
		case "disciplinaModel":
			break;
		case "modulModel":
			break;
		}
	}
	
	private void actiuneStergere() {
		switch(context.getCurrentModelName()) {
		case "studentModel":
			break;
		case "profesorModel":
			break;
		case "disciplinaModel":
			break;
		case "modulModel":
			break;
		}
	}
	
}
