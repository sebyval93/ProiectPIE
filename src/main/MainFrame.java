package main;
import java.awt.EventQueue;
import javax.swing.JFrame;
import gui_system.AdminPanel;
import gui_system.LoginPanel;
import gui_system.MainPanel;
import gui_system.ModalFrame;
import gui_system.ResetWeeksPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import Singleton.*;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {

	private gui_system.LoginPanel loginPanel;
	private gui_system.MainPanel mainPanel;
	private gui_system.AdminPanel adminPanel;

	JMenuBar menuBar;
	JMenu mnFile, mnUnelte, mnGenerareRaport;
	JMenuItem mntmDelogare, mntmExit, 
	mntmAdministrare, mntmGestionareModule,
	mntmRaportStudenti, mntmRaportGrupe, mntmTest;
	private JMenu renewDateMnt;
	private JMenuItem mntmImport_1;
	private JMenuItem mntmDataInceperii;

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
		initFrame();		
		showLoginPanel();
	}
	
	public void setMenuVisible(boolean bool){
		this.menuBar.setVisible(bool);
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
		mnGenerareRaport.setVisible(false);
		setMenuVisible(false);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void showMainPanel() {
		setMenuVisible(true);
		setTitle("Gestiune module pentru profesorul: "+Singleton.getInstance().currentUser.getUsername());
		loginPanel.setVisible(false);
		adminPanel.setVisible(false);
		renewDateMnt.setVisible(false);
		mainPanel.loadFromDB();
		setContentPane(mainPanel);
		mainPanel.setVisible(true);
		mnUnelte.setVisible(true);
		mntmDelogare.setVisible(true);
		mntmGestionareModule.setVisible(false);
		mntmAdministrare.setVisible(true);
		mnGenerareRaport.setVisible(false);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void showAdminPanel() {
		setTitle("Administrare baza de date");
		mntmAdministrare.setVisible(false);
		mntmGestionareModule.setVisible(true);
		mnGenerareRaport.setVisible(true);
		renewDateMnt.setVisible(true);
		mainPanel.setVisible(false);
		setContentPane(adminPanel);
		adminPanel.setVisible(true);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void initFrame(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		getContentPane().setLayout(null);
		
		setTitle("Logare sistem gestiune prezente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 750, 500);
		
		setResizable(false);
		setLocationRelativeTo(null);
		
		loginPanel = new LoginPanel();
		loginPanel.setParentFrame(this);
		mainPanel = new MainPanel();
		mainPanel.setParentFrame(this);
		adminPanel = new AdminPanel();
		adminPanel.setParentFrame(this);
		
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		mnFile.setMnemonic('F');
		menuBar.add(mnFile);
		
		mntmDelogare = new JMenuItem("Delogare");
		mntmDelogare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showLoginPanel();
				Singleton.getInstance().currentUser = null;
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
		
		mntmGestionareModule = new JMenuItem("Gestionare Module");
		mntmGestionareModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//FIX ME!!!
				showMainPanel();
			}
		});
		
		mnGenerareRaport = new JMenu("Generare Raport");
		mnGenerareRaport.setMnemonic('R');
		mnUnelte.add(mnGenerareRaport);
		
		mntmRaportStudenti = new JMenuItem("Studenti");
		mnGenerareRaport.add(mntmRaportStudenti);
		
		mntmRaportGrupe = new JMenuItem("Grupe");
		mnGenerareRaport.add(mntmRaportGrupe);
		
		mntmTest = new JMenuItem("Test");
		mnGenerareRaport.add(mntmTest);
		mnUnelte.add(mntmGestionareModule);
		
		renewDateMnt = new JMenu("Re\u00EEnnoire date");
		mnUnelte.add(renewDateMnt);
		
		mntmImport_1 = new JMenuItem("Import");
		mntmImport_1.setHorizontalAlignment(SwingConstants.LEFT);
		renewDateMnt.add(mntmImport_1);
		
		mntmDataInceperii = new JMenuItem("Data \u00EEnceperii anului");
		mntmDataInceperii.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setNewYearStart();
			}
		});
		mntmDataInceperii.setHorizontalAlignment(SwingConstants.CENTER);
		renewDateMnt.add(mntmDataInceperii);
		
		mnUnelte.setVisible(false);
		mntmDelogare.setVisible(false);
		mntmGestionareModule.setVisible(false);
		
	}
	
	public void setNewYearStart(){
		new ModalFrame(this,new ResetWeeksPanel());
	}
	
	public void hideUnelteMenu() {
		mnUnelte.setEnabled(false);
	}
	
	public void showUnelteMenu() {
		mnUnelte.setEnabled(true);
	}
	
	public void updateWeekBrowser() {
		mainPanel.updateWeekBrowser();
	}

}
