package gui_system;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import entity.*;

public class AdminContext {
	
	private JTable selTable, mainTable;
	private DefaultTableCellRenderer centerCellRenderer;
	private DefaultTableModel tableSelectionModel, studentModel, profesorModel,
		disciplinaModel, modulModel, contModel, currentModel;
	
	
	private String selectionColumns[] = { "Tabele" };
	private String selectionData[][] = { { "Student" }, { "Profesor" }, { "Disciplina" }, { "Situatie didactica" }, { "Cont" } };
	
	private String studentColumns[] = { "Nume", "Grupa", "Subgrupa" };
	private String profesorColumns[] = { "Nume" };
	private String disciplinaColumns[] = { "Denumire", "An", "Semestru", "Ore curs", "Ore lab.", "Ore sem.", "Ore pro.", "Nume scurt" };
	private String modulColumns[] = { "Disciplina", "Activitate", "Nume profesor", "Interval", "Participanti" };
	private String contColumns[] = { "Nume profesor", "Nume cont" };
	
	public AdminContext(JTable selTable, JTable mainTable) {
		this.selTable = selTable;
		this.mainTable = mainTable;		
		setupTableModels();
		loadSelectionModel();
	}

	private void loadSelectionModel() {
		selTable.setModel(tableSelectionModel);
		
		for (int i = 0; i < selTable.getColumnCount(); ++i) {
			selTable.getColumnModel().getColumn(i).setCellRenderer(centerCellRenderer);
		}
		((JLabel) selTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
		for (int i = 0; i < selectionData.length; ++i) {
			tableSelectionModel.addRow(selectionData[i]);
		}
		
		selTable.setRowHeight(270 / 5);
	}
	
	public void switchToContModel() {
		contModel.setRowCount(0);
		if (!mainTable.getModel().equals(contModel))
			mainTable.setModel(contModel);
		currentModel = contModel;
		centerMainTableCells();
		loadAllFromCont();

	}
	
	
	public void switchToProfesorModel() {
		profesorModel.setRowCount(0);
		if (!mainTable.getModel().equals(profesorModel))
			mainTable.setModel(profesorModel);
		currentModel = profesorModel;
		centerMainTableCells();
		loadAllFromProfesor();
	}
	
	
	
	public void switchToDisciplinaModel() {
		disciplinaModel.setRowCount(0);
		if (!mainTable.getModel().equals(disciplinaModel))
			mainTable.setModel(disciplinaModel);
		currentModel = disciplinaModel;
		centerMainTableCells();
		loadAllFromDisciplina();
	}
	
	public void switchToModulModel() {
		modulModel.setRowCount(0);
		if (!mainTable.getModel().equals(modulModel))
			mainTable.setModel(modulModel);
		currentModel = modulModel;
		centerMainTableCells();
		loadAllFromModul();
	}
	
	public void switchToStudent() {
		contModel.setRowCount(0);
		if (!mainTable.getModel().equals(studentModel))
			mainTable.setModel(studentModel);
		currentModel = studentModel;
		centerMainTableCells();
		loadAllFromStudent();

	}
	
	public DefaultTableModel getCurrentModel() {
		return currentModel;
	}
	
	public String getCurrentModelName() {
		if (currentModel.equals(studentModel))
			return "studentModel";
		else if (currentModel.equals(profesorModel))
			return "profesorModel";
		else if (currentModel.equals(disciplinaModel))
			return "disciplinaModel";
		else if (currentModel.equals(modulModel))
			return "modulModel";
		else if (currentModel.equals(contModel))
			return "contModel";
		else
			return null;
	}
	
	private void centerMainTableCells() {
		for (int i = 0; i < mainTable.getColumnCount(); i++) {
			mainTable.getColumnModel().getColumn(i).setCellRenderer(centerCellRenderer);
		}
		((JLabel) mainTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);		
	}
	
	private void setupTableModels() {
		
		centerCellRenderer = new DefaultTableCellRenderer();
		centerCellRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		tableSelectionModel = new DefaultTableModel(){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		for (int i = 0; i < selectionColumns.length; ++i) {
			tableSelectionModel.addColumn(selectionColumns[i]);
		}
		
		studentModel = new DefaultTableModel()
		{

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		for (int i = 0; i < studentColumns.length; ++i) {
			studentModel.addColumn(studentColumns[i]);
		}
		
		profesorModel = new DefaultTableModel(){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		for (int i = 0; i < profesorColumns.length; ++i) {
			profesorModel.addColumn(profesorColumns[i]);
		}
		
		disciplinaModel = new DefaultTableModel(){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		for (int i = 0; i < disciplinaColumns.length; ++i) {
			disciplinaModel.addColumn(disciplinaColumns[i]);
		}
		
		modulModel = new DefaultTableModel(){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		for (int i = 0; i < modulColumns.length; ++i) {
			modulModel.addColumn(modulColumns[i]);
		}
		
		contModel = new DefaultTableModel() {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		for (int i = 0; i < contColumns.length; ++i) {
			contModel.addColumn(contColumns[i]);
		}
		
	}
	
	public void loadAllFromStudent(){
		DefaultTableModel model = getCurrentModel();
		Singleton.Singleton.getInstance().ListOfStudents.stream().forEach((aux) -> {
            model.addRow(new Object[]{aux.getNume(),aux.getSubgrupa().getGrupa().getNume(),aux.getSubgrupa().getNume()});
        });
		
	}
	
	public void loadAllFromDisciplina(){		
		DefaultTableModel model = getCurrentModel();
		Singleton.Singleton.getInstance().ListOfDisciplines.stream().forEach((aux) -> {
            model.addRow(new Object[]{aux.getDenumire(),aux.getAn(), aux.getSemestru().getId(),aux.getOrecurs(),aux.getOrelab(),
            		aux.getOreseminar(),aux.getOreproiect(),aux.getNumeScurt()});
        });
	}
	
	public void loadAllFromProfesor(){
		DefaultTableModel model = getCurrentModel();
		Singleton.Singleton.getInstance().ListOfTeachers.stream().forEach((aux) -> {
        	if (!aux.getNume().equals("admin"))
        		model.addRow(new Object[]{aux.getNume()});
        });
	}
	
	public void loadAllFromModul(){
		DefaultTableModel model = getCurrentModel();
		Singleton.Singleton.getInstance().ListOfModules.stream().forEach((aux) -> {
        	BigDecimal value = aux.getInterval();
        	String interval = null;
        	if (value.intValue() == 0)
        		interval = "Saptamanal";
        	else if (value.intValue() == 1)
        		interval = "Saptamana impara";
        	else if (value.intValue() == 2)
        		interval = "Saptamana para";
        	else 
        		interval = "Interval invalid";
        	
            model.addRow(new Object[]{aux.getDisciplina().getDenumire(), aux.getActivitate(), aux.getProfesor().getNume(),
            		interval, aux.getParticipanti()});
        });
	}
	
	public void loadAllFromCont(){
		DefaultTableModel model = getCurrentModel();
		Singleton.Singleton.getInstance().ListOfAccounts.stream().forEach((aux) -> {
            model.addRow(new Object[]{aux.getProfesor().getNume(), aux.getUsername()});
        });
	}
	
	public void loadStudentListInTable(List<Student> list) {
		DefaultTableModel model = getCurrentModel();
		model.setRowCount(0);
        list.stream().forEach((aux) -> {
            model.addRow(new Object[]{aux.getNume(),aux.getSubgrupa().getGrupa().getNume(),aux.getSubgrupa().getNume()});
        });
	}
	
	public void loadProfesorListInTable(List<Profesor> list) {
		DefaultTableModel model = getCurrentModel();
		model.setRowCount(0);
        list.stream().forEach((aux) -> {
            model.addRow(new Object[]{aux.getNume()});
        });
	}
	
	public void loadDisciplinaListInTable(List<Disciplina> list){
		DefaultTableModel model = getCurrentModel();
		model.setRowCount(0);
        list.stream().forEach((aux) -> {
            model.addRow(new Object[]{aux.getDenumire(),aux.getAn(), aux.getSemestru().getId(),aux.getOrecurs(),aux.getOrelab(),
            		aux.getOreseminar(),aux.getOreproiect(),aux.getNumeScurt()});
        });
	}
	
	public void loadModulListInTable(List<Modul> list){
		DefaultTableModel model = getCurrentModel();
		model.setRowCount(0);
        list.stream().forEach((aux) -> {
        	BigDecimal value = aux.getInterval();
        	String interval = null;
        	if (value.intValue() == 0)
        		interval = "Saptamanal";
        	else if (value.intValue() == 1)
        		interval = "Saptamana impara";
        	else if (value.intValue() == 2)
        		interval = "Saptamana para";
        	else 
        		interval = "Interval invalid";
        	
            model.addRow(new Object[]{aux.getDisciplina().getDenumire(), aux.getActivitate(), aux.getProfesor().getNume(),
            		interval, aux.getParticipanti()});
        });
	}
	
	public void resetStudentModel() {
		DefaultTableModel model = getCurrentModel();
		model.setRowCount(0);
		loadAllFromStudent();
	}
	
	public void resetDisciplinaModel() {
		DefaultTableModel model = getCurrentModel();
		model.setRowCount(0);
		loadAllFromDisciplina();
	}
	
	public void resetProfesorModel() {
		DefaultTableModel model = getCurrentModel();
		model.setRowCount(0);
		loadAllFromProfesor();
	}
	
	public void resetModulModel() {
		DefaultTableModel model = getCurrentModel();
		model.setRowCount(0);
		loadAllFromModul();
	}

}
