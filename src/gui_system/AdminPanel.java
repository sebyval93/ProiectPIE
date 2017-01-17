package gui_system;

import javax.swing.JPanel;
import java.awt.Dimension;

import java.awt.Point;
import Singleton.*;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Services.DisciplinaService;
import Services.GrupaService;
import Services.ModulService;
import Services.ProfesorService;
import Services.StudentService;
import Services.SubgrupaService;
import entity.Disciplina;
import entity.Grupa;
import entity.Modul;
import entity.Profesor;
import entity.Semestru;
import entity.Student;
import entity.Subgrupa;
import main.MainFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
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
	
	private int selectedID = -1;
	private String selectedModel = "";

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
		mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mainTable.setAutoCreateRowSorter(true);
		
		
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
		btnCautare.setBounds(32, 353, 89, 23);
		add(btnCautare);
		
		btnAdaugare = new JButton("Adaugare");
		btnAdaugare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actiuneAdaugare();
			}
		});
		btnAdaugare.setBounds(32, 326, 89, 23);
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
		
		showAddAndRemoveButtons(false);
		
		mainTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					JTable t =(JTable) me.getSource();
					
					DefaultTableModel model = (DefaultTableModel) t.getModel();
					
					Point p = me.getPoint();
					int row = t.rowAtPoint(p);
					if (context.getCurrentModelName().equals("studentModel")) {
						selectedID = StudentService.getStudentByNume((String)model.getValueAt(t.convertRowIndexToModel(row), 0)).getId().intValue();
						System.out.println(selectedID);
						selectedModel = context.getCurrentModelName();
						studentiCtrlPanel.setFields((String)model.getValueAt(t.convertRowIndexToModel(row), 0),
								(String)model.getValueAt(t.convertRowIndexToModel(row), 1), 
								(String)model.getValueAt(t.convertRowIndexToModel(row), 2));
						enableEditButtons(true);
					}
					else if (context.getCurrentModelName().equals("profesorModel")) {
						selectedID = ProfesorService.getProfesorByNume((String)model.getValueAt(t.convertRowIndexToModel(row), 0)).getId().intValue();
						selectedModel = context.getCurrentModelName();
						profesorCtrlPanel.setFields((String)model.getValueAt(t.convertRowIndexToModel(row), 0));
						enableEditButtons(true);
					}
					else if (context.getCurrentModelName().equals("disciplinaModel")) {
						selectedID = DisciplinaService.getDisciplinaByDenumire((String)model.getValueAt(t.convertRowIndexToModel(row), 0)).getId().intValue();
						selectedModel = context.getCurrentModelName();
						disciplinaCtrlPanel.setFields((String)model.getValueAt(t.convertRowIndexToModel(row), 0), 
								model.getValueAt(t.convertRowIndexToModel(row), 1).toString(), 
								model.getValueAt(t.convertRowIndexToModel(row), 2).toString(), 
								model.getValueAt(t.convertRowIndexToModel(row), 3).toString(), 
								model.getValueAt(t.convertRowIndexToModel(row), 4).toString(), 
								model.getValueAt(t.convertRowIndexToModel(row), 5).toString(), 
								model.getValueAt(t.convertRowIndexToModel(row), 6).toString(), 
								(String)model.getValueAt(t.convertRowIndexToModel(row), 7));
						enableEditButtons(true);
					}
					else if (context.getCurrentModelName().equals("modulModel")) {
						Disciplina disciplina = DisciplinaService.getDisciplinaByDenumire(((String)model.getValueAt(t.convertRowIndexToModel(row), 0)));
						Profesor profesor = ProfesorService.getProfesorByNume(((String)model.getValueAt(t.convertRowIndexToModel(row), 2)));
						String activitate = ((String)model.getValueAt(t.convertRowIndexToModel(row), 1));
						String participanti = ((String)model.getValueAt(t.convertRowIndexToModel(row), 4));
						String interval = (String)model.getValueAt(t.convertRowIndexToModel(row), 3);
						int intervalInt = -1;
						switch(interval){
							case "Saptamanal":
								intervalInt = 0;
								break;
							case "Saptamana impara":
								intervalInt = 1;
								break;
							case "Saptamana para":
								intervalInt = 2;
								break;
						}
						selectedID = ModulService.getModul(disciplina, profesor, activitate, participanti, intervalInt).getId().intValue();
						selectedModel = context.getCurrentModelName();
						sitDidacticaCtrlPanel.setFields((String)model.getValueAt(t.convertRowIndexToModel(row), 0), 
								(String)model.getValueAt(t.convertRowIndexToModel(row), 1), 
								(String)model.getValueAt(t.convertRowIndexToModel(row), 2), 
								(String)model.getValueAt(t.convertRowIndexToModel(row), 3), 
								(String)model.getValueAt(t.convertRowIndexToModel(row), 4));
						
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
					//enableEditButtons(false);
					showAddAndRemoveButtons(false);
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
							showAddAndRemoveButtons(true);
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
		
		for (int i = 1; i < 7; ++i) {
			mainTable.getColumnModel().getColumn(i).setMinWidth(55);
			mainTable.getColumnModel().getColumn(i).setMaxWidth(55);
		}
		mainTable.getColumnModel().getColumn(7).setMinWidth(65);
		mainTable.getColumnModel().getColumn(7).setMaxWidth(65);
	}
	
	private void enableEditButtons(boolean enable) {
		btnModificare.setEnabled(enable);
		btnStergere.setEnabled(enable);
	}
	
	private void showAddAndRemoveButtons(boolean show) {
		btnAdaugare.setVisible(show);
		btnStergere.setVisible(show);
	}
	
	private void actiuneAdaugare() {
		if (context.getCurrentModelName().equals("modulModel")) {
			Disciplina disciplina = sitDidacticaCtrlPanel.getSelectedDisciplina();
			Profesor profesor = sitDidacticaCtrlPanel.getSelectedProfesor();
			String activitate = sitDidacticaCtrlPanel.getSelectedActivitate();
			String participanti = sitDidacticaCtrlPanel.getSelectedParticipanti();
			int interval = sitDidacticaCtrlPanel.getSelectedInterval();

			if (disciplina != null || profesor != null || activitate != null || participanti != null || interval != -1) {
				boolean done = ModulService.addModul(disciplina, profesor, activitate, participanti, interval,0);
				Singleton.getInstance().loadAllModules();
				if(done){
					JOptionPane.showMessageDialog(null, "Adaugat cu succes!");	
				}else{
					JOptionPane.showMessageDialog(null, "Nu s-a efectuat adaugarea!");
				}
				context.resetModulModel();
			}
			else
				System.out.println("Could not add modul to database. Found invalid field.");
		}

	}
	
	private void actiuneCautare() {
		String searchString = "";
		boolean search = false;
		
		switch(context.getCurrentModelName()) {
		case "studentModel":
			String numeStudent = studentiCtrlPanel.getNumeStudent();
			String numeSubgrupa = studentiCtrlPanel.getSelectedSubgrupa();
			String numeGrupa = studentiCtrlPanel.getSelectedGrupa();
			Grupa grupa;
			Subgrupa subgrupa;
			
			searchString = "from Student where ";
			
			if (!numeStudent.isEmpty()) {
				searchString += ("upper(nume) like upper('%" + numeStudent + "%')");
				search = true;
			}
			if (numeGrupa != null && numeSubgrupa == null && numeGrupa.length() != 0) {
				grupa = GrupaService.getGrupaByNume(numeGrupa);
				List<Subgrupa> list = SubgrupaService.getAllSubGrupeByGrupa(grupa);
				if (searchString.length() > 19)
					searchString += " AND ";
				
				searchString += "(";
				for (int i = 0; i < list.size(); ++i) {
					searchString += ("subgrupa.id = " + list.get(i).getId());
					if (i < list.size() - 1)
						searchString += " OR ";
				}
				searchString += ")";

				search = true;
			}
			if (numeSubgrupa != null && numeSubgrupa.length() != 0) {
				subgrupa = SubgrupaService.getSubgrupaByNume(numeSubgrupa);
				if (searchString.length() > 19)
					searchString += " AND ";
				searchString += ("subgrupa.id = " + subgrupa.getId());
				search = true;
			}
			
			System.out.println(searchString);
			
			if (search)
				context.loadStudentListInTable(StudentService.runSearchQuery(searchString));
			
			break;
		case "profesorModel":
			String numeProfesor = profesorCtrlPanel.getNumeProfesor();
			
			searchString = "from Profesor where ";
			
			if (!numeProfesor.isEmpty()) {
				searchString += ("upper(nume) like upper('%" + numeProfesor + "%') AND upper(nume) not like 'ADMIN'");
				search = true;
			}
			
			if (search)
				context.loadProfesorListInTable(ProfesorService.runSearchQuery(searchString));
			
			break;
		case "disciplinaModel":
			String denumire = disciplinaCtrlPanel.getDenumire();
			int an = disciplinaCtrlPanel.getAn();
			int semestru = disciplinaCtrlPanel.getSemestru();
			int oreCurs = disciplinaCtrlPanel.getOreCurs();
			int oreLab = disciplinaCtrlPanel.getOreLaborator();
			int oreSeminar = disciplinaCtrlPanel.getOreSeminar();
			int oreProiect = disciplinaCtrlPanel.getOreProiect();
			String numeScurt = disciplinaCtrlPanel.getNumeScurt();
			
			searchString = "from Disciplina where ";
			
			if (!denumire.isEmpty()) {
				searchString += ("upper(denumire) like upper('%" + denumire + "%')");
				search = true;
			}
			if (an != -1) {
				if (searchString.length() > 22)
					searchString += " AND ";
				searchString += ("an = " + an);
				search = true;
			}
			if (semestru != -1) {
				if (searchString.length() > 22)
					searchString += " AND ";
				searchString += ("semestru.id = " + semestru);
				search = true;
			}
			if (oreCurs != -1) {
				if (searchString.length() > 22)
					searchString += " AND ";
				searchString += ("orecurs = " + oreCurs);
				search = true;
			}
			if (oreLab != -1) {
				if (searchString.length() > 22)
					searchString += " AND ";
				searchString += ("orelab = " + oreLab);
				search = true;
			}
			if (oreSeminar != -1) {
				if (searchString.length() > 22)
					searchString += " AND ";
				searchString += ("oreseminar = " + oreSeminar);
				search = true;
			}
			if (oreProiect != -1) {
				if (searchString.length() > 22)
					searchString += " AND ";
				searchString += ("oreproiect = " + oreProiect);
				search = true;
			}
			if (!numeScurt.isEmpty()) {
				if (searchString.length() > 22)
					searchString += " AND ";
				searchString += ("upper(numeScurt) like upper('%" + numeScurt + "%')");
				search = true;
			}
			
			
			if (search)
				context.loadDisciplinaListInTable(DisciplinaService.runSearchQuery(searchString));

			break;
		case "modulModel":
			Disciplina disciplina = sitDidacticaCtrlPanel.getSelectedDisciplina();
			Profesor profesor = sitDidacticaCtrlPanel.getSelectedProfesor();
			String activitate = sitDidacticaCtrlPanel.getSelectedActivitate();
			String participanti = sitDidacticaCtrlPanel.getSelectedParticipanti();
			int interval = sitDidacticaCtrlPanel.getSelectedInterval();
			
			searchString = "from Modul where ";
			
			if (disciplina != null) {
				searchString += ("disciplina.id = " + disciplina.getId());
				search = true;
			}
			if (profesor != null) {
				if (searchString.length() > 17)
					searchString += " AND ";
				searchString += ("profesor.id = " + profesor.getId());
				search = true;
			}
			if (activitate != null) {
				if (searchString.length() > 17)
					searchString += " AND ";
				searchString += ("upper(activitate) like upper('%" + activitate + "%')");
				search = true;
			}
			if (participanti != null && !participanti.isEmpty()) {
				if (searchString.length() > 17)
					searchString += " AND ";
				if (Character.isAlphabetic(participanti.charAt(participanti.length() - 1)))
					searchString += ("upper(participanti) like upper('%" + participanti + "%')");
				else
					searchString += ("upper(participanti) like upper('%" + participanti + "')");
					
				search = true;
			}
			if (interval != -1) {
				if (searchString.length() > 17)
					searchString += " AND ";
				searchString += ("interval = " + interval);
				search = true;
			}
			
			if (search)
				context.loadModulListInTable(ModulService.runSearchQuery(searchString));
			
			break;
		}
	}
	
	private void actiuneModificare() {
		switch(context.getCurrentModelName()) {
		case "studentModel":
			if (selectedModel == "studentModel" && selectedID > -1) {
				String numeStudent = studentiCtrlPanel.getNumeStudent();
				String numeSubgrupa = studentiCtrlPanel.getSelectedSubgrupa();
				Subgrupa subgrupa = SubgrupaService.getSubgrupaByNume(numeSubgrupa);
				
				if (numeStudent != "" || numeSubgrupa != "" || subgrupa != null) {
					boolean done = StudentService.updateStudentByID(selectedID, subgrupa, numeStudent);
					if(done){
						for(Student s : Singleton.getInstance().ListOfStudents){
							if(s.getId().intValue() == selectedID){
								s.setNume(numeStudent);
								s.setSubgrupa(subgrupa);
								break;
							}
						}
						context.resetStudentModel();
						JOptionPane.showMessageDialog(null, "Modificat cu succes!");
					}	
					else{
						JOptionPane.showMessageDialog(null, "Nu s-a efectuat modificarea!");
					}
					
				}
				else
					System.out.println("Could not update student. Found invalid field.");
			}
				
			break;
		case "profesorModel":
			if (selectedModel == "profesorModel" && selectedID > -1) {
				String numeProfesor = profesorCtrlPanel.getNumeProfesor();
				
				if (numeProfesor != null) {
					boolean done = ProfesorService.updateProfesorByID(selectedID, numeProfesor);
					if(done){
						for(Profesor p : Singleton.getInstance().ListOfTeachers){
							if(p.getId().intValue() == selectedID){
								p.setNume(numeProfesor);
								break;
							}
							
						}
						context.resetProfesorModel();
						JOptionPane.showMessageDialog(null, "Modificat cu succes!");	
					}else{
						JOptionPane.showMessageDialog(null, "Nu s-a efectuat modificarea!");
					}
					
				}
				else
					System.out.println("Could not update profesor. Found invalid field.");
			}
			
			break;
		case "disciplinaModel":
			if (selectedModel == "disciplinaModel" && selectedID > -1) {
				String denumire = disciplinaCtrlPanel.getDenumire();
				Semestru semestru = Singleton.getInstance().ListOfSemesters.get(disciplinaCtrlPanel.getSemestru()-1);
				int an = disciplinaCtrlPanel.getAn();
				int oreCurs = disciplinaCtrlPanel.getOreCurs();
				int oreLab = disciplinaCtrlPanel.getOreLaborator();
				int oreSeminar = disciplinaCtrlPanel.getOreSeminar();
				int oreProiect = disciplinaCtrlPanel.getOreProiect();
				String numeScurt = disciplinaCtrlPanel.getNumeScurt();

				if (denumire != "" || numeScurt != "" || an > 1 || oreCurs >= 0 || oreLab >= 0 || oreSeminar >= 0 || oreProiect >= 0) {
					boolean done = DisciplinaService.updateDisciplinaByID(selectedID,semestru,denumire, an, oreCurs, oreLab, oreSeminar, oreProiect, numeScurt);
					if(done){
						for(Disciplina d : Singleton.getInstance().ListOfDisciplines){
							if(d.getId().intValue() == selectedID){
								d.setDenumire(denumire);
								d.setNumeScurt(numeScurt);
								d.setAn(new BigDecimal(an));
								d.setOrecurs(new BigDecimal(oreCurs));
								d.setOrelab(new BigDecimal(oreLab));
								d.setOreproiect(new BigDecimal(oreProiect));
								d.setOreseminar(new BigDecimal(oreSeminar));
								d.setSemestru(semestru);
								break;
							}
							
						}
						context.resetDisciplinaModel();
						JOptionPane.showMessageDialog(null, "Modificat cu succes!");	
					}else{
						JOptionPane.showMessageDialog(null, "Nu s-a efectuat modificarea!");
					}

				}
				else
					System.out.println("Could not update disciplina. Found invalid field.");
				
			}
			
			break;
		case "modulModel":
			if (selectedModel == "modulModel" && selectedID > -1) {
				Disciplina disciplina = sitDidacticaCtrlPanel.getSelectedDisciplina();
				Profesor profesor = sitDidacticaCtrlPanel.getSelectedProfesor();
				String activitate = sitDidacticaCtrlPanel.getSelectedActivitate();
				String participanti = sitDidacticaCtrlPanel.getSelectedParticipanti();
				int interval = sitDidacticaCtrlPanel.getSelectedInterval();

				if (disciplina != null || profesor != null || activitate != null || participanti != null || interval != -1) {
					Modul temp = ModulService.getModulByID(selectedID);
					boolean done = ModulService.updateModulByID(selectedID, disciplina, profesor, activitate, participanti, interval,temp.getOperat().intValue());
					if(done){
						for(Modul m : Singleton.getInstance().ListOfModules){
							if(m.getId().intValue() == selectedID){
								m.setActivitate(activitate);
								m.setDisciplina(disciplina);
								m.setInterval(new BigDecimal(interval));
								m.setParticipanti(participanti);
								m.setProfesor(profesor);
								break;
							}
							
						}
						context.resetModulModel();
						JOptionPane.showMessageDialog(null, "Modificat cu succes!");	
					}else{
						JOptionPane.showMessageDialog(null, "Nu s-a efectuat modificarea!");
					}
				}
				else
					System.out.println("Could not update modul. Found invalid field.");
			}
			
			break;
		}
		
		selectedID = -1;
		mainTable.clearSelection();
	}
	
	private void actiuneStergere() {
		if(context.getCurrentModelName().equals("modulModel")) {
			if (selectedModel == "modulModel" && selectedID > -1) {
				boolean done = ModulService.deleteModulByID(selectedID);
				Singleton.getInstance().loadAllModules();
				context.resetModulModel();
				if(done){
					JOptionPane.showMessageDialog(null, "Actualizat cu succes!");	
				}else{
					JOptionPane.showMessageDialog(null, "Nu s-a efectuat stergerea!");
				}
			}
		}
		
		selectedID = -1;
		mainTable.clearSelection();
	}
	
}
