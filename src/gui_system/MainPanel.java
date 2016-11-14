package gui_system;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.MainFrame;

public class MainPanel extends JPanel {
	
	private JTable table;
	
	JScrollPane scrollPane;
	JButton btnBack;
	
	ModelContext context;
	MainFrame parentFrame;
	
	public MainPanel() {
		
		setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 730, 393);
		add(scrollPane);
		
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(30);
		table.setCellSelectionEnabled(false);
		
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
							selectedRowData[i] = model.getValueAt(row, i);
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

	}
	
	public void setParentFrame(MainFrame frame) {
		parentFrame = frame;
	}
	
	public void loadFromDB(String user) {
		context.setLoggedUser(user);
		context.switchToModule();
	}

}
