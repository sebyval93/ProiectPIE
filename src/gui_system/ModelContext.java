package gui_system;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Services.ModulService;
import Singleton.Singleton;
import entity.Modul;
import entity.Profesor;

public class ModelContext {
	
	private DefaultTableModel moduleModel, studentiModel, currentModel;
	private enum ContextType { MODULE, STUDENTI };
	private JTable table;
	private DefaultTableCellRenderer centerCellRenderer;
	
	String moduleColumns[] = { "Nume Disciplina", "Activitate", "An Disciplina", "Numar Saptamana", "Participanti" };
	String studentiColumns[] = { "Nume Student", "Prezenta" };
	
	//                        0        1
	//private enum TipGrupa { SUBGRUPA, GRUPA };
	//                        0        1
	//private enum Prezenta { ABSENT, PREZENT };
	//                         0      1       2
	//private enum Saptamana { IMPARA, PARA, INTEGRAL };
	//                         0       1         2         3
	private enum Activitate { CURS, SEMINAR, LABORATOR, PROIECT };
	
	//--test data -BEGIN
	private Object moduleData[][] = { 
			{ "Baze de Date", Activitate.CURS, 2, 3, "22c21" },
			{ "Baze de Date", Activitate.CURS, 2, 3, "22c22" },
			{ "Baze de Date", Activitate.CURS, 2, 3, "22c23" },
			{ "Baze de Date", Activitate.LABORATOR, 2, 3, "22c22a" },
			{ "Baze de Date", Activitate.LABORATOR, 2, 3, "22c21b" },
			{ "Proiectarea Bazelor de Date", Activitate.SEMINAR, 3, 3, "22c31"},
			{ "Proiect Informatic in Echipa", Activitate.PROIECT, 4,3, "22c41"},
			{ "Proiect Informatic in Echipa", Activitate.PROIECT, 4,3, "22c42"}
	};
	//--test data -END
	
	
	public ModelContext(JTable table) {
		this.table = table;
		setupTableModels();
		//currentModel = moduleModel;
		//loadModuleFromDB();
		//loadModuleModel();
	}
	
	public void switchToStudenti(Object selectedRowData[]) {
		currentModel = studentiModel;
		loadStudentiFromDB(selectedRowData);
		loadStudentiModel();
	}
	
	public void switchToModule() {
		currentModel = moduleModel;
		loadModuleFromDB();
		loadModuleModel();
	}
	
	private void loadModuleModel() {

		table.setModel(moduleModel);
		
		for (int i = 0; i < table.getColumnCount(); ++i) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerCellRenderer);
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}
	
	private void loadStudentiModel() {

		table.setModel(studentiModel);
		
		for (int i = 0; i < table.getColumnCount() - 1; ++i) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerCellRenderer);
		}
		((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}
	
	private void loadModuleFromDB() {
		// toate modulele din saptamana curenta ale profesorului logat.
		// cautare in baza de date pentru toate modulele la care preda userul
		//   logat, din saptamana curenta.
		
		loadModuleForUser();
		
		/*
		if (moduleModel.getRowCount() > 0) {
			moduleModel.setRowCount(0);
		}
		
		//test data
		for (int i = 0; i < moduleData.length; ++i) {
			moduleModel.addRow(moduleData[i]);
		}
		*/
	}
	
	private void loadStudentiFromDB(Object selectedRowData[]) {
		// toti studentii care fac parte din (sub)grupa care participa la un modul.
		// din selectedRowData luam informatia despre (sub)grupe, si in functie de ea afisam studentii
		
		for (int i = 0; i < selectedRowData.length; ++i) {
			System.out.print(selectedRowData[i] + " ");
		}
		System.out.println();
		
		if (studentiModel.getRowCount() > 0) {
			studentiModel.setRowCount(0);
		}
		
		//test data
		for (int i = 0; i < 20; ++i) {
			studentiModel.addRow(new Object[] { "Student " + i, false });
		}
		
	}
	
	private void setupTableModels() {
		
		centerCellRenderer = new DefaultTableCellRenderer();
		centerCellRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		moduleModel = new DefaultTableModel() {
		      public boolean isCellEditable(int row, int column){  
		          return false;
		      }
		};
		
		for (int i = 0; i < moduleColumns.length; ++i) {
			moduleModel.addColumn(moduleColumns[i]);
		}
		
		studentiModel = new DefaultTableModel() {
		      public boolean isCellEditable(int row, int column){  
		    	  if (column == 1)
		    		  return true;
		    	  
		          return false;
		      }
		      
	            public Class getColumnClass(int column) {
	            	switch (column) {
	                   case 0:
	                       return String.class;
	                   default:
	                       return Boolean.class;
	               }
	           };
		};
		
		for (int i = 0; i < studentiColumns.length; ++i) {
			studentiModel.addColumn(studentiColumns[i]);
		}
	}
	
	public DefaultTableModel getCurrentModel() {
		return currentModel;
	}
	
	public boolean isModuleModelLoaded() {
		if (currentModel == moduleModel)
			return true;
		else
			return false;
	}
	
	public boolean isStudentiModelLoaded() {
		if (currentModel == studentiModel)
			return true;
		else
			return false;
	}
	
	public void loadModuleForUser() {
		Profesor currentProfesor = Singleton.getInstance().currentUser.getProfesor();
		String sapt = Singleton.getInstance().currentWeek.getDenumire();
		int nrSapt = Integer.parseInt(sapt.substring(10));
		List<Modul> allModuleForProfesor = ModulService.getAllModulByProfesor(currentProfesor);
		
		moduleModel.setRowCount(0);
		
		allModuleForProfesor.stream().forEach((aux) -> {
            moduleModel.addRow(new Object[]{aux.getDisciplina().getDenumire(),aux.getActivitate(),aux.getDisciplina().getAn(),
            		nrSapt,aux.getParticipanti()});
        });
	}
	
}
