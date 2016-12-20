package gui_system;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Services.GrupaService;
import Services.StudentService;
import Services.SubgrupaService;
import entity.Grupa;
import entity.Student;
import entity.Subgrupa;
import main.MainFrame;

public class MainPanel extends JPanel {
	
	private JTable table;
	
	JScrollPane scrollPane;
	JButton btnBack;
	
	ModelContext context;
	MainFrame parentFrame;
	
	public static List<Grupa> allFromGrupa;
	public static List<Subgrupa> allFromSubgrupa;
	
	public MainPanel() {
		
		allFromGrupa = GrupaService.getAllFromGrupa();
		allFromSubgrupa = SubgrupaService.getAllFromSubgrupa();
		
		setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 730, 393);
		add(scrollPane);
		
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(30);
		table.setCellSelectionEnabled(false);
		table.setAutoCreateRowSorter(true);
		
		context = new ModelContext(table);
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable t =(JTable) me.getSource();
				Point p = me.getPoint();
				int row = t.rowAtPoint(p);
				if (me.getClickCount() == 2) {
					if (context.isModuleModelLoaded()) {
						DefaultTableModel model = context.getCurrentModel();
						Object selectedRowData[] = new Object[model.getColumnCount()];
						
						for (int i = 0; i < model.getColumnCount(); ++i) {
							selectedRowData[i] = model.getValueAt(t.convertRowIndexToModel(row), i);
						}
						
						context.switchToStudenti(selectedRowData);
					}
				}
			}
		});
		
		scrollPane.setViewportView(table);
		
		btnBack = new JButton("Inapoi");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (context.isStudentiModelLoaded()) {
					context.switchToModule();
				}
			}
		});
		btnBack.setBounds(10, 416, 730, 23);
		add(btnBack);
		this.setPreferredSize(new Dimension(750, 450));

	}
	
	public void setParentFrame(MainFrame frame) {
		parentFrame = frame;
	}
	
	public void loadFromDB() {
		context.switchToModule();
	}
	
	public static List<Student> getStudentiFromParticipant(String participant) {
		List<Student> studenti = null;
		
		if (Character.isLetter(participant.charAt(participant.length() - 1))) {
			//subgrupa
			Subgrupa subgrupa = SubgrupaService.getSubgrupaByNume(participant);
			studenti = StudentService.getAllStudentsBySubgrupa(subgrupa);
			
			return studenti;
		}
		else {
			//grupa
			Grupa grupa = GrupaService.getGrupaByNume(participant);
			studenti = StudentService.getAllStudentsByGrupa(grupa);
			
			return studenti;
		}
		
	}

}
