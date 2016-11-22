package gui_system;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

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
		mainTable.setModel(studentModel);
		currentModel = studentModel;
		centerMainTableCells();
		
	}
	
	public void switchToProfesorModel() {
		mainTable.setModel(profesorModel);
		currentModel = profesorModel;
		centerMainTableCells();
		
	}
	
	public void switchToDisciplinaModel() {
		mainTable.setModel(disciplinaModel);
		currentModel = disciplinaModel;
		centerMainTableCells();
		
	}
	
	public void switchToModulModel() {
		mainTable.setModel(modulModel);
		currentModel = modulModel;
		centerMainTableCells();
		
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
		for (int i = 0; i < mainTable.getColumnCount() - 1; ++i) {
			mainTable.getColumnModel().getColumn(i).setCellRenderer(centerCellRenderer);
		}
		((JLabel) mainTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
	}
	
	private void setupTableModels() {
		
		centerCellRenderer = new DefaultTableCellRenderer();
		centerCellRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		tableSelectionModel = new DefaultTableModel();
		for (int i = 0; i < selectionColumns.length; ++i) {
			tableSelectionModel.addColumn(selectionColumns[i]);
		}
		
		studentModel = new DefaultTableModel();
		for (int i = 0; i < studentColumns.length; ++i) {
			studentModel.addColumn(studentColumns[i]);
		}
		
		profesorModel = new DefaultTableModel();
		for (int i = 0; i < profesorColumns.length; ++i) {
			profesorModel.addColumn(profesorColumns[i]);
		}
		
		disciplinaModel = new DefaultTableModel();
		for (int i = 0; i < disciplinaColumns.length; ++i) {
			disciplinaModel.addColumn(disciplinaColumns[i]);
		}
		
		anModel = new DefaultTableModel();
		for (int i = 0; i < anColumns.length; ++i) {
			anModel.addColumn(anColumns[i]);
		}
		
		grupaModel = new DefaultTableModel();
		for (int i = 0; i < grupaColumns.length; ++i) {
			grupaModel.addColumn(grupaColumns[i]);
		}
		
		subgrupaModel = new DefaultTableModel();
		for (int i = 0; i < subgrupaColumns.length; ++i) {
			subgrupaModel.addColumn(subgrupaColumns[i]);
		}
		
		modulModel = new DefaultTableModel();
		for (int i = 0; i < modulColumns.length; ++i) {
			modulModel.addColumn(modulColumns[i]);
		}
		
		prezentaModel = new DefaultTableModel();
		for (int i = 0; i < prezentaColumns.length; ++i) {
			prezentaModel.addColumn(prezentaColumns[i]);
		}
		
		saptamanaModel = new DefaultTableModel();
		for (int i = 0; i < saptamanaColumns.length; ++i) {
			saptamanaModel.addColumn(saptamanaColumns[i]);
		}
		
		semestruModel = new DefaultTableModel();
		for (int i = 0; i < semestruColumns.length; ++i) {
			semestruModel.addColumn(semestruColumns[i]);
		}
	}

}
