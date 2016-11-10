import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import gui_system.ModelContext;

import javax.swing.JButton;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	//private JPanel cardsPane;
	//private gui.LoginPanel loginPanel;
	//private gui.ManagePanel managePanel;
	private gui_system.LoginFrame loginFrame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		setTitle("DB");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750,450);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic('F');
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setMnemonic('x');
		mnFile.add(mntmExit);
		
		JMenu mnAdministrare = new JMenu("Unelte");
		mnAdministrare.setMnemonic('U');
		menuBar.add(mnAdministrare);
		
		JMenuItem mntmAdministrare = new JMenuItem("Administrare");
		mntmAdministrare.setMnemonic('A');
		mnAdministrare.add(mntmAdministrare);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 724, 345);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(30);
		table.setCellSelectionEnabled(false);
		
		ModelContext context = new ModelContext(table);
		
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
		
		JButton btnNewButton = new JButton("Inapoi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (context.isStudentiModelLoaded()) {
					context.switchToModule();
				}
			}
		});
		btnNewButton.setBounds(10, 367, 724, 23);
		contentPane.add(btnNewButton);
	}
}
