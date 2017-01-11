package gui_system;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import Services.GrupaService;
import Services.ModulService;
import Services.StudentService;
import Services.SubgrupaService;
import Singleton.Singleton;
import Utils.Week;
import entity.Grupa;
import entity.Modul;
import entity.Profesor;
import entity.Saptamana;
import entity.Student;
import entity.Subgrupa;
import main.MainFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.SystemColor;

public class MainPanel extends JPanel {
	
	private JTable table;
	
	JScrollPane scrollPane;
	JButton btnBack, btnSave,
		btnWeekPrev, btnWeekNext;
	JButton lblWeek;
	JButton buttonSwitch;
	
	ModelContext context;
	MainFrame parentFrame;
	
	private Week currWeek;
		
	public MainPanel() {
		currWeek = Singleton.getInstance().currentWeek;		

		setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 730, 368);
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
						AbstractTableModel model = context.getCurrentModel();
						Object selectedRowData[] = new Object[model.getColumnCount()];
						
						for (int i = 0; i < model.getColumnCount(); ++i) {
							selectedRowData[i] = model.getValueAt(t.convertRowIndexToModel(row), i);
						}
						context.setModul(Singleton.getInstance().ListOfTeacherModules.stream()
								.filter(e -> (e.getDisciplina().getDenumire().equals(selectedRowData[0]))
											&& e.getActivitate().equals(selectedRowData[1]) 
											&& e.getParticipanti().equals(selectedRowData[4])
										).findFirst().get()
								
								);
						
						context.switchToStudenti(selectedRowData);
						hideWeekBrowser();
						btnSave.setVisible(true);
						btnBack.setVisible(true);
						buttonSwitch.setVisible(false);
					}
				}
			}
		});
		
		scrollPane.setViewportView(table);
		
		
		
		btnBack = new JButton("Inapoi");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (context.isStudentiModelLoaded()) {
					context.switchToModule(currWeek);
					btnSave.setVisible(false);
					btnBack.setVisible(false);
					showWeekBrowser();
				}
			}
		});
		btnBack.setBounds(10, 416, 730, 23);
		add(btnBack);
		this.setPreferredSize(new Dimension(750, 450));
		
		btnWeekPrev = new JButton("");
		btnWeekPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currWeek.prevWeek();
				context.loadModulesForWeek(currWeek);
				updateWeekBrowser();
			}
		});
		
		btnBack.setVisible(false);
		btnWeekPrev.setIcon(new ImageIcon(MainPanel.class.getResource("/res/l_arrow.png")));
		btnWeekPrev.setBounds(10, 384, 50, 29);
		add(btnWeekPrev);
		
		btnWeekNext = new JButton("");
		btnWeekNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currWeek.getSaptamana().getId().intValue() == Singleton.getInstance().currentWeekStatic.getId().intValue())
					return;
				currWeek.nextWeek();
				context.loadModulesForWeek(currWeek);
				updateWeekBrowser();
			}
		});
		btnWeekNext.setIcon(new ImageIcon(MainPanel.class.getResource("/res/r_arrow.png")));
		btnWeekNext.setBounds(690, 384, 50, 29);
		add(btnWeekNext);
		
		lblWeek = new JButton("");
		lblWeek.setOpaque(true);
		lblWeek.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeek.setBounds(70, 384, 610, 29);
		add(lblWeek);
		
		btnSave = new JButton("Salveaza");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean done = context.addRecordsForStudents();
				if(done){
					JOptionPane.showMessageDialog(null, "Actualizat cu succes!");	
				}
			}
		});
		btnSave.setBounds(10, 390, 730, 23);
		add(btnSave);
		
		buttonSwitch = new JButton("Vezi toate modulele din saptamana curenta!");
		buttonSwitch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				context.loadAllModulesForActualWeek(currWeek);
			}
		});
		buttonSwitch.setBounds(10, 416, 730, 23);
		add(buttonSwitch);
		btnSave.setVisible(false);

	}
	
	public void updateWeekBrowser() {
		lblWeek.setText("Vizualizare Saptamana " + currWeek.getSaptamanaNumber() + ", Semestrul " + currWeek.getSemestruNumber());
	}
	
	public void hideWeekBrowser() {
		btnWeekPrev.setVisible(false);
		lblWeek.setVisible(false);
		btnWeekNext.setVisible(false);
	}
	
	public void showWeekBrowser() {
		btnWeekPrev.setVisible(true);
		lblWeek.setVisible(true);
		btnWeekNext.setVisible(true);
		btnBack.setVisible(false);
		buttonSwitch.setVisible(true);
	}
	
	public void setParentFrame(MainFrame frame) {
		parentFrame = frame;
	}
	
	public void loadFromDB() {
		context.switchToModule(currWeek);
	}
	
	public Week getCurrWeek() {
		return currWeek;
	}

	public void setCurrWeek(Week currWeek) {
		this.currWeek = currWeek;
	}
	
}
