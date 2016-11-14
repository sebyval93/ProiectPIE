package gui_system;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import main.MainFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JList;

public class AdminPanel extends JPanel {
	
	main.MainFrame parentFrame;
	private JTable selTable;
	private DefaultTableModel tableSelectionModel;
	
	
	private String selectionColumns[] = { "Tabele" };
	
	private DefaultTableCellRenderer centerCellRenderer;

	public AdminPanel() {
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 156, 428);
		add(scrollPane);
		
		
		
		selTable = new JTable();
		scrollPane.setViewportView(selTable);
		
		setupTableModels();
		loadSelectionModel();
		
		selTable.setModel(tableSelectionModel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(176, 11, 564, 320);
		add(scrollPane_1);
		
	}
	
	public void setParentFrame(MainFrame frame) {
		parentFrame = frame;
	}
	
	public void resetState() {
	}
	
	private void setupTableModels() {
		
		centerCellRenderer = new DefaultTableCellRenderer();
		centerCellRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		tableSelectionModel = new DefaultTableModel();
		
		for (int i = 0; i < selectionColumns.length; ++i) {
			tableSelectionModel.addColumn(selectionColumns[i]);
		}
	}
	
	private void loadSelectionModel() {
		selTable.setModel(tableSelectionModel);
		
		for (int i = 0; i < selTable.getColumnCount(); ++i) {
			selTable.getColumnModel().getColumn(i).setCellRenderer(centerCellRenderer);
		}
		//selTable.getColumnModel().getColumn(0).setPreferredWidth(120);
		((JLabel) selTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}
}
