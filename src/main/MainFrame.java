package main;
import java.awt.EventQueue;
import javax.swing.JFrame;
import gui_system.AdminPanel;
import gui_system.LoginPanel;
import gui_system.MainPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class MainFrame extends JFrame {

	private gui_system.LoginPanel loginPanel;
	private gui_system.MainPanel mainPanel;
	private gui_system.AdminPanel adminPanel;

	JMenuBar menuBar;
	JMenu mnFile;
	JMenuItem mntmDelogare;
	JMenuItem mntmExit;
	JMenu mnUnelte;
	JMenuItem mntmAdministrare;

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
		
		setTitle("Logare sistem gestiune prezente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 753, 498);
		
		setResizable(false);
		setLocationRelativeTo(null);
		
		loginPanel = new LoginPanel();
		loginPanel.setParentFrame(this);
		mainPanel = new MainPanel();
		mainPanel.setParentFrame(this);
		adminPanel = new AdminPanel();
		
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		mnFile.setMnemonic('F');
		menuBar.add(mnFile);
		
		mntmDelogare = new JMenuItem("Delogare");
		mntmDelogare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showLoginPanel();
			}
		});
		mntmDelogare.setMnemonic('D');
		mnFile.add(mntmDelogare);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mntmExit.setMnemonic('x');
		mnFile.add(mntmExit);
		
		mnUnelte = new JMenu("Unelte");
		mnUnelte.setMnemonic('U');
		menuBar.add(mnUnelte);
		
		mntmAdministrare = new JMenuItem("Administrare");
		mntmAdministrare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAdminPanel();
			}
		});
		mntmAdministrare.setMnemonic('A');
		mnUnelte.add(mntmAdministrare);
		
		mnUnelte.setVisible(false);
		mntmDelogare.setVisible(false);
		
		
		setContentPane(loginPanel);
	}
	
	public void showLoginPanel() {
		setTitle("Logare sistem gestiune prezente");
		mainPanel.setVisible(false);
		adminPanel.setVisible(false);
		loginPanel.resetState();
		setContentPane(loginPanel);
		loginPanel.setVisible(true);
		mnUnelte.setVisible(false);
		mntmDelogare.setVisible(false);
	}
	
	public void showMainPanel(String user) {
		setTitle("Gestiune module pentru user-ul: %USER_HERE%");
		loginPanel.setVisible(false);
		adminPanel.setVisible(false);
		mainPanel.loadFromDB(user);
		setContentPane(mainPanel);
		mainPanel.setVisible(true);
		mnUnelte.setVisible(true);
		mntmDelogare.setVisible(true);
	}
	
	public void showAdminPanel() {
		setTitle("Administrare baza de date");
		mainPanel.setVisible(false);
		setContentPane(adminPanel);
		adminPanel.setVisible(true);
		//mnUnel
	}
}
