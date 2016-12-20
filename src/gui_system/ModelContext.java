package gui_system;

import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Services.ModulService;
import Singleton.Singleton;
import Utils.Week;
import entity.Modul;
import entity.Profesor;
import entity.Saptamana;
import entity.Student;

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
		
		//loadModuleForUser();
		loadModuleForProfesorInSaptamana(Singleton.getInstance().currentProfesor, Singleton.getInstance().currentWeek);
	}
	
	public void loadModuleInWeek(Week week) {
		loadModuleForProfesorInSaptamana(Singleton.getInstance().currentProfesor, week);
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
		
		List<Student> studenti = MainPanel.getStudentiFromParticipant((String) selectedRowData[4]);
        studenti.stream().forEach((aux) -> {
        	studentiModel.addRow(new Object[]{aux.getNume(), false});
        });
		
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
	
	public void loadModuleForProfesorInSaptamana(Profesor prof, Week week) {
		List<Modul> module = ModulService.getAllModulByProfesor(prof);
		
		Iterator<Modul> iter = module.iterator();
		
		while (iter.hasNext()) {
			Modul modul = iter.next();
			switch(modul.getInterval().intValue()) {
			case 0:
				//Impar
				if (week.getSaptamanaNumber() % 2 != 1)
					iter.remove();
				break;
			case 1:
				//Par
				if (week.getSaptamanaNumber() % 2 != 0)
					iter.remove();
				break;
			}
		}
		
		moduleModel.setRowCount(0);
		if (module == null || module.size() == 0) {
			return;
		}
		
		module.stream().forEach((aux) -> {
            moduleModel.addRow(new Object[]{aux.getDisciplina().getDenumire(),aux.getActivitate(),aux.getDisciplina().getAn(),
            		week.getSaptamanaNumber(),aux.getParticipanti()});
        });
	}
	
}
