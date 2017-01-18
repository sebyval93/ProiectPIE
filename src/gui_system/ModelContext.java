package gui_system;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Services.ModulService;
import Services.PrezentaService;
import Services.StareModulService;
import Singleton.Singleton;
import Utils.RecordTableModel;
import Utils.Week;
import entity.Modul;
import entity.Prezenta;
import entity.Saptamana;
import entity.StareModul;
import entity.Student;

public class ModelContext {
	private AbstractTableModel currentModel;
	private DefaultTableModel moduleModel;
	private RecordTableModel studentiModel;
	private JTable table;
	private DefaultTableCellRenderer centerCellRenderer;
	
	String moduleColumns[] = { "Nume Disciplina", "Activitate", "An Disciplina", "Numar Saptamana", "Participanti" };
	private Saptamana week;
	private Modul modul;
	List<Prezenta> list;
	public Modul getModul() {
		return modul;
	}

	public void setModul(Modul modul) {
		this.modul = modul;
	}

	//                          0        1
	//private enum TipGrupa { SUBGRUPA, GRUPA };
	//                          0        1
	//private enum Prezenta { ABSENT, PREZENT };
	//                          0        1       
	//private enum Saptamana { INTEGRAL, IMPAR, PAR };
	//                          0        1       2         
	
	private enum Activitate { CURS, SEMINAR, LABORATOR, PROIECT };	
	
	public ModelContext(JTable table) {
		this.table = table;
		setupTableModels();

	}
	
	public void switchToStudenti(Object selectedRowData[]) {
		currentModel = studentiModel;
		loadStudentiFromDB(selectedRowData);
		loadStudentiModel();
	}
	
	public void switchToModule(Week week) {
		currentModel = moduleModel;
		loadModuleModel();
		loadModulesForWeek(week);
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
		loadModulesForWeek(Singleton.getInstance().currentWeek);
	}
	
	private void loadStudentiFromDB(Object selectedRowData[]) {
		studentiModel = new RecordTableModel();
		currentModel = studentiModel;
		list = PrezentaService.getRecordsForModuleAndWeek(modul, week);
		studentiModel.addRows(list);
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
		
		studentiModel = new RecordTableModel();
		
	}
	
	public boolean addRecordsForStudents(){		
		boolean done = PrezentaService.updateListOfRecords(list);
		if(week != Singleton.getInstance().currentWeekStatic){
			StareModul m = StareModulService.getStareModulByModulAndWeek(modul, week);
			m.setOperat(new BigDecimal(1));
			StareModulService.updateStareModul(m);
		}else{
			for(StareModul m : Singleton.getInstance().ListOfModuleStats){
				if(m.getModul().getId().intValue() == modul.getId().intValue()){
					m.setOperat(new BigDecimal(1));
					StareModulService.updateStareModul(m);
					break;
				}
			}
		}
		return done;
	}
	
	public AbstractTableModel getCurrentModel() {
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
	
	public void loadModulesForWeek(Week week) {
		this.week = week.getSaptamana();
		moduleModel.setRowCount(0);
		
		if(week.getSaptamana().getId().intValue() != Singleton.getInstance().currentWeekStatic.getId().intValue()){
			if(week.getSaptamana().getId().intValue() % 2 == 0){	//sapt para
				
				Singleton.getInstance().ListOfTeacherModules.stream()
						.filter(e -> (e.getInterval().intValue()  == 0 || e.getInterval().intValue() == 2) 
								&& e.getDisciplina().getSemestru().getId().intValue() == week.getSaptamana().getSemestru().getId().intValue())
						.forEach((aux) -> {
							moduleModel.addRow(new Object[]{aux.getDisciplina().getDenumire(),aux.getActivitate(),aux.getDisciplina().getAn(),
		            		week.getSaptamanaNumber(),aux.getParticipanti()});
		        });
				
			}else{													//sapt impara
				Singleton.getInstance().ListOfTeacherModules.stream()
						.filter(e -> (e.getInterval().intValue()  == 0 ||  e.getInterval().intValue() == 1) 
								&& e.getDisciplina().getSemestru().getId().intValue() == week.getSaptamana().getSemestru().getId().intValue() )
						.forEach((aux) -> {
							moduleModel.addRow(new Object[]{aux.getDisciplina().getDenumire(),aux.getActivitate(),aux.getDisciplina().getAn(),
		            		week.getSaptamanaNumber(),aux.getParticipanti()});
		        });
			}
			return;
		}else{	
			System.out.println("currweek");
			if(week.getSaptamana().getId().intValue() % 2 == 0){	//sapt para
							
							Singleton.getInstance().ListOfModuleStats.stream()
									.filter(e -> (e.getModul().getInterval().intValue()  == 0 || e.getModul().getInterval().intValue() == 2) 
											&& e.getModul().getDisciplina().getSemestru().getId().intValue() == week.getSaptamana().getSemestru().getId().intValue()
											&& e.getOperat().intValue() != 1)
									.forEach((aux) -> {
										moduleModel.addRow(new Object[]{aux.getModul().getDisciplina().getDenumire(),aux.getModul().getActivitate(),aux.getModul().getDisciplina().getAn(),
					            		week.getSaptamanaNumber(),aux.getModul().getParticipanti()});
					        });
							
						}else{													//sapt impara
							Singleton.getInstance().ListOfModuleStats.stream()
									.filter(e -> (e.getModul().getInterval().intValue()  == 0 ||  e.getModul().getInterval().intValue() == 1) 
											&& e.getModul().getDisciplina().getSemestru().getId().intValue() == week.getSaptamana().getSemestru().getId().intValue() 
											&& e.getOperat().intValue() != 1)
									.forEach((aux) -> {
										moduleModel.addRow(new Object[]{aux.getModul().getDisciplina().getDenumire(),aux.getModul().getActivitate(),aux.getModul().getDisciplina().getAn(),
					            		week.getSaptamanaNumber(),aux.getModul().getParticipanti()});
					        });
			
						}

			}
	
	}
	
	public void loadAllModulesForActualWeek(Week week){
		
		if(week.getSaptamana().getId().intValue() == Singleton.getInstance().currentWeekStatic.getId().intValue()){
			moduleModel.setRowCount(0);
			if(week.getSaptamana().getId().intValue() % 2 == 0){	//sapt para
				
				Singleton.getInstance().ListOfTeacherModules.stream()
						.filter(e -> (e.getInterval().intValue()  == 0 || e.getInterval().intValue() == 2) 
								&& e.getDisciplina().getSemestru().getId().intValue() == week.getSaptamana().getSemestru().getId().intValue())
						.forEach((aux) -> {
							moduleModel.addRow(new Object[]{aux.getDisciplina().getDenumire(),aux.getActivitate(),aux.getDisciplina().getAn(),
		            		week.getSaptamanaNumber(),aux.getParticipanti()});
		        });
				
			}else{													//sapt impara
				Singleton.getInstance().ListOfTeacherModules.stream()
						.filter(e -> (e.getInterval().intValue()  == 0 ||  e.getInterval().intValue() == 1) 
								&& e.getDisciplina().getSemestru().getId().intValue() == week.getSaptamana().getSemestru().getId().intValue())
						.forEach((aux) -> {
							moduleModel.addRow(new Object[]{aux.getDisciplina().getDenumire(),aux.getActivitate(),aux.getDisciplina().getAn(),
		            		week.getSaptamanaNumber(),aux.getParticipanti()});
		        });

			}

		}
			
	}
}

