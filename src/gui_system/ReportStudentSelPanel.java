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

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

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
	
	String studentCols[] = { "Nume", "Grupa", "Subgrupa" };
	String disciplinaCols[] = { "Denumire", "An", "Semestru" };

	/**
	 * Create the panel.
	 */
	public ReportStudentSelPanel(boolean includeDisciplina) {
		setLayout(null);
		setPreferredSize(new Dimension(450, 423));
		
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
		
		table.setModel(studentModel);
		
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
		btnGenerare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnGenerare.getText().equals("Generare")) {
					if (currentModel.equals(studentModel)) {
						//TODO: studentReport
						System.out.println("Student report all disc");
					}
					else if (currentModel.equals(disciplinaModel)) {
						//TODO: disciplinaReport
						System.out.println("Student report one disc");
					}
				}
				else {
					table.setModel(disciplinaModel);
					btnGenerare.setText("Generare");
					lblTitle.setText("Selectati disciplina pentru care doriti sa generati raportul");
					cardLayout.show(ctrlPanel, "disciplina");
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
		disciplinaModel.setColumnCount(0);
		list.stream().forEach((aux) -> {
			disciplinaModel.addRow(new Object[]{aux.getDenumire(),aux.getAn(), aux.getSemestru().getId()});
        });
	}
}
