package gui_system;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import entity.*;
import Services.DisciplinaService;
import Services.GrupaService;
import Services.ModulService;
import Services.ProfesorService;
import Services.StudentService;

public class AdminContext {
	
	private JTable selTable, mainTable;
	private DefaultTableCellRenderer centerCellRenderer;
	private DefaultTableModel tableSelectionModel, studentModel, profesorModel,
		disciplinaModel, anModel, grupaModel, subgrupaModel, modulModel, prezentaModel, 
		saptamanaModel, semestruModel, currentModel;
	
	private String selectionColumns[] = { "Tabele" };
	private String selectionData[][] = { { "Student" }, { "Profesor" }, { "Disciplina" }, { "Situatie didactica" } };
	
	private String studentColumns[] = { "Nume", "Grupa", "Subgrupa" };
	private String profesorColumns[] = { "Nume" };
	private String disciplinaColumns[] = { "Denumire", "An", "Ore curs", "Ore laborator", "Ore seminar", "Ore proiect", "Nume scurt" };
	private String anColumns[] = { "An" };
	private String grupaColumns[] = { "Nume", "An" };
	private String subgrupaColumns[] = { "Nume", "Grupa" };
	private String modulColumns[] = { "Disciplina", "Activitate", "Nume profesor", "Interval", "Participanti" };
	private String prezentaColumns[] = { "Modul", "Nume student", "Prezent", "Nr. saptamana", "Data saptamana" };
	private String saptamanaColumns[] = { "Denumire", "Data de inceput", "Data de sfarsit", "Semestru" };
	private String semestruColumns[] = { "Nume" };
	
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
		
		selTable.setRowHeight(271 / 4);
	}
	
	public void switchToStudent() {
		studentModel.setRowCount(0);
		if (!mainTable.getModel().equals(studentModel))
			mainTable.setModel(studentModel);
		currentModel = studentModel;
		centerMainTableCells();
		loadAllFromStudent();
		
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
		
	}
	
	public void loadAllFromStudent(){
		List<Student> list = StudentService.getAllFromStudent();
		DefaultTableModel model = getCurrentModel();
        list.stream().forEach((aux) -> {
            model.addRow(new Object[]{aux.getNume(),aux.getSubgrupa().getGrupa().getNume(),aux.getSubgrupa().getNume()});
        });
		
	}
	
	public void loadAllFromDisciplina(){
		List<Disciplina> list = DisciplinaService.getAllFromDisciciplina();
		DefaultTableModel model = getCurrentModel();
        list.stream().forEach((aux) -> {
            model.addRow(new Object[]{aux.getDenumire(),aux.getAn(),aux.getOrecurs(),aux.getOrelab(),
            		aux.getOreseminar(),aux.getOreproiect(),aux.getNumeScurt()});
        });
	}
	
	public void loadAllFromProfesor(){
		List<Profesor> list = ProfesorService.getAllFromProfesor();
		DefaultTableModel model = getCurrentModel();
        list.stream().forEach((aux) -> {
        	if (!aux.getNume().equals("admin"))
        		model.addRow(new Object[]{aux.getNume()});
        });
	}
	
	public void loadAllFromModul(){
		List<Modul> list = ModulService.getAllFromModul();
		DefaultTableModel model = getCurrentModel();
        list.stream().forEach((aux) -> {
        	BigDecimal value = aux.getInterval();
        	String interval = null;
        	if (value.intValue() == 0)
        		interval = "Impar";
        	else if (value.intValue() == 1)
        		interval = "Par";
        	else if (value.intValue() == 2)
        		interval = "Saptamanal";
        	else 
        		interval = "Interval invalid";
        	
            model.addRow(new Object[]{aux.getDisciplina().getDenumire(), aux.getActivitate(), aux.getProfesor().getNume(),
            		interval, aux.getParticipanti()});
        });
	}

}
