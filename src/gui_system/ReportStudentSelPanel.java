package gui_system;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Services.DisciplinaService;
import Services.GrupaService;
import Services.ProfesorService;
import Services.StudentService;
import Services.SubgrupaService;
import entity.Disciplina;
import entity.Grupa;
import entity.Student;
import entity.Subgrupa;
import rapoarte.RaportStudent;
import rapoarte.RaportStudentDisciplina;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReportStudentSelPanel extends JPanel {
	private JTextField txtNume;
	private JLabel lblTitle;
	JComboBox<String> cbGrupa, cbSubgrupa;
	DefaultTableModel studentModel, disciplinaModel,
		currentModel;
	private JTable table;
	private JButton btnGenerare;
	private CardLayout cardLayout;
	private RapStudentSearchCtrlPanel studentCtrl;
	private RapDiscSearchCtrlPanel disciplinaCtrl;
	JPanel ctrlPanel;
	private DefaultTableCellRenderer centerCellRenderer;
	private Student selectedStudent = null;
	private Disciplina selectedDisciplina = null;
	
	String studentCols[] = { "Nume", "Grupa", "Subgrupa" };
	String disciplinaCols[] = { "Denumire", "An", "Semestru" };


	public ReportStudentSelPanel(boolean includeDisciplina) {
		setLayout(null);
		setPreferredSize(new Dimension(450, 423));
		centerCellRenderer = new DefaultTableCellRenderer();
		centerCellRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		lblTitle = new JLabel("Selectati studentul pentru care doriti sa generati raportul");
		lblTitle.setBounds(88, 21, 273, 14);
		add(lblTitle);
		
		studentCtrl = new RapStudentSearchCtrlPanel();
		studentCtrl.setBounds(26, 278, 397, 81);
		disciplinaCtrl = new RapDiscSearchCtrlPanel();
		
		studentModel = new DefaultTableModel();
		disciplinaModel = new DefaultTableModel();
		for (int i = 0; i < studentCols.length; ++i) {
			studentModel.addColumn(studentCols[i]);
		}
		for (int i = 0; i < disciplinaCols.length; ++i) {
			disciplinaModel.addColumn(disciplinaCols[i]);
		}
		
		currentModel = studentModel;
		
		//loadStudentListInTable(Singleton.Singleton.getInstance().ListOfStudents);
		
		table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {

				if (me.getButton() == MouseEvent.BUTTON1 && currentModel.equals(studentModel) && btnGenerare.getText().equals("Urmator")) {
					JTable t = (JTable) me.getSource();

					DefaultTableModel model = (DefaultTableModel) t.getModel();

					Point p = me.getPoint();
					int row = t.rowAtPoint(p);
					selectedStudent = StudentService.getStudentByNume((String)model.getValueAt(t.convertRowIndexToModel(row), 0));
					
					if (selectedStudent != null && btnGenerare.getText().equals("Urmator")) {
						btnGenerare.setEnabled(true);
					}
				}
				
				else if (me.getButton() == MouseEvent.BUTTON1 && currentModel.equals(disciplinaModel)) {
					JTable t = (JTable) me.getSource();

					DefaultTableModel model = (DefaultTableModel) t.getModel();

					Point p = me.getPoint();
					int row = t.rowAtPoint(p);
					selectedDisciplina = DisciplinaService.getDisciplinaByDenumire((String)model.getValueAt(t.convertRowIndexToModel(row), 0));
					
					if (selectedDisciplina != null) {
						btnGenerare.setEnabled(true);
					}
				}
				
				else if (me.getButton() == MouseEvent.BUTTON1 && !includeDisciplina) {
					JTable t = (JTable) me.getSource();

					DefaultTableModel model = (DefaultTableModel) t.getModel();

					Point p = me.getPoint();
					int row = t.rowAtPoint(p);
					selectedStudent = StudentService.getStudentByNume((String)model.getValueAt(t.convertRowIndexToModel(row), 0));
					
					if (selectedStudent != null) {
						btnGenerare.setEnabled(true);
					}
				}
			}
		});
		
		table.setRowHeight(25);
		
		table.setModel(studentModel);
		
		table.getColumnModel().getColumn(0).setMinWidth(250);
		table.getColumnModel().getColumn(0).setMaxWidth(250);
		
		centerTableCells();
				
		ctrlPanel = new JPanel();
		ctrlPanel.setBorder(new TitledBorder(null, "Cautare", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		ctrlPanel.setBounds(26, 278, 397, 81);
		add(ctrlPanel);
		cardLayout = new CardLayout();
		ctrlPanel.setLayout(cardLayout);
		
		ctrlPanel.add(studentCtrl, "student");
		ctrlPanel.add(disciplinaCtrl, "disciplina");
		
		cardLayout.show(ctrlPanel, "student");
		
		btnGenerare = new JButton("Generare");
		btnGenerare.setEnabled(false);
		btnGenerare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnGenerare.getText().equals("Generare")) {
					if (currentModel.equals(studentModel)) {
						//TODO: studentReport
						RaportStudent.MakeSingleStudentReport(selectedStudent);
					}
					else if (currentModel.equals(disciplinaModel)) {
						//TODO: disciplinaReport
						RaportStudentDisciplina.MakeSingleStudentReport(selectedStudent, selectedDisciplina);
					}
				}
				else {
					List<Disciplina> list = DisciplinaService.getDisciplinaOfStudent(selectedStudent);
					btnGenerare.setText("Generare");
					lblTitle.setText("Selectati disciplina pentru care doriti sa generati raportul");
					cardLayout.show(ctrlPanel, "disciplina");
					table.setModel(disciplinaModel);
					loadDisciplinaListInTable(list);
					centerTableCells();
					table.getColumnModel().getColumn(0).setMinWidth(250);
					table.getColumnModel().getColumn(0).setMaxWidth(250);
					btnGenerare.setEnabled(false);
					currentModel = disciplinaModel;
				}
			}
		});
		btnGenerare.setBounds(45, 378, 89, 23);
		add(btnGenerare);
		
		if (includeDisciplina)
			btnGenerare.setText("Urmator");
		
		JButton btnAnulare = new JButton("Anulare");
		btnAnulare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeWindowWithQuestion();
			}
		});
		btnAnulare.setBounds(313, 378, 89, 23);
		add(btnAnulare);
		
		JButton btnCautare = new JButton("Cautare");
		btnCautare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentModel.equals(studentModel)) {
					doStudentSearch();
				}
				else if (currentModel.equals(disciplinaModel)) {
					doDisciplinaSearch();
				}
			}
		});
		btnCautare.setBounds(179, 378, 89, 23);
		add(btnCautare);
		
		JScrollPane scrollPane = new JScrollPane(table);
		//scrollPane.setLayout(new BorderLayout());
		scrollPane.setBounds(26, 57, 397, 211);
		add(scrollPane);
		scrollPane.setViewportView(table);
		
		studentCtrl.initLoadGrupa();
		studentCtrl.initLoadSubgrupa();
		
		loadStudentListInTable(Singleton.Singleton.getInstance().ListOfStudents);

	}
	
	private void closeWindowWithQuestion() {
		ModalFrame topFrame = (ModalFrame) SwingUtilities.getWindowAncestor(this);
		topFrame.closeThisWithQuestion();
	}
	
	private void centerTableCells() {
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerCellRenderer);
		}
		((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);		
	}
	
	private void doStudentSearch() {
		boolean search = false;
		String numeStudent = studentCtrl.getNume();
		String numeSubgrupa = studentCtrl.getSubgrupaText();
		String numeGrupa = studentCtrl.getGrupaText();
		Grupa grupa;
		Subgrupa subgrupa;
		
		String searchString = "from Student where ";
		
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
		
		if (search)
			loadStudentListInTable(StudentService.runSearchQuery(searchString));
		
	}
	
	private void doDisciplinaSearch() {
		boolean search = false;
		String denumire = disciplinaCtrl.getDenumire();
		int an = disciplinaCtrl.getAn();
		int semestru = disciplinaCtrl.getSemestru();
		
		String searchString = "from Disciplina where ";
		
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
		
		if (search)
			loadDisciplinaListInTable(DisciplinaService.runSearchQuery(searchString));

	}
	
	private void loadStudentListInTable(List<Student> list) {
		studentModel.setRowCount(0);
		list.stream().forEach((aux) -> {
            studentModel.addRow(new Object[]{aux.getNume(),aux.getSubgrupa().getGrupa().getNume(),aux.getSubgrupa().getNume()});
        });
	}
	
	private void loadDisciplinaListInTable(List<Disciplina> list) {
		disciplinaModel.setRowCount(0);
		list.stream().forEach((aux) -> {
			disciplinaModel.addRow(new Object[]{aux.getDenumire(),aux.getAn(), aux.getSemestru().getId()});
        });
	}
}
