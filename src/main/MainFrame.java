package main;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import gui_system.AdminPanel;
import gui_system.LoginPanel;
import gui_system.MainPanel;
import gui_system.ModalFrame;
import gui_system.MyProgressBar;
import gui_system.ReportStudentSelPanel;
import gui_system.ResetWeeksPanel;
import gui_system.YearReportPanel;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import Services.PrezentaService;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import Singleton.*;
import Utils.ProgressBarListener;
import Utils.Workers.GenerateRecordsWorker;

import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

public class MainFrame extends JFrame {

	private LoginPanel loginPanel;
	private MainPanel mainPanel;
	

	private AdminPanel adminPanel;
	
	JMenuBar menuBar;
	JMenu mnFile, mnUnelte, mnGenerareRaport,
	mnRaportStudenti;
	JMenuItem mntmDelogare, mntmExit, 
	mntmAdministrare, mntmAn;
	private JMenu renewDateMnt;
	private JMenuItem mntmImport_1;
	private JMenuItem mntmDataInceperii;
	private JMenuItem mntmGenerarePrezen;
	private JMenuItem mntmRaportDisciplina;
	private JMenuItem mntmRaportToateDisciplinele;

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
		if(adminPanel == null){
			loginPanel = new LoginPanel();
			loginPanel.setParentFrame(this);
		}
			setTitle("Login");
			if(mainPanel != null){
				mainPanel.setVisible(false);
			}
			if(adminPanel != null){
				adminPanel.setVisible(false);
			}
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
	
	public void showProgressPanel(){
		menuBar.setVisible(false);
		setTitle("Loading...");
		adminPanel.setVisible(false);
		
	}
	
	public void showAdminPanel() {
		if(adminPanel == null){
			adminPanel = new AdminPanel();
			adminPanel.setParentFrame(this);
		}
		this.getContentPane().setVisible(false);
		setMenuVisible(true);
		setTitle("Administrare baza de date");
		mntmAdministrare.setVisible(false);
		mnGenerareRaport.setVisible(true);
		mnUnelte.setVisible(true);
		renewDateMnt.setVisible(true);
		if(mainPanel != null){
			mainPanel.setVisible(false);
		}
		
		setContentPane(adminPanel);
		adminPanel.setVisible(true);
		mntmDelogare.setVisible(true);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void showMainPanel() {
		if(mainPanel == null){
			mainPanel = new MainPanel();
			mainPanel.setParentFrame(this);
		}
		setMenuVisible(true);
		setTitle("Gestiune module pentru profesorul: "+Singleton.getInstance().currentUser.getProfesor().getNume());
		if(loginPanel != null){
			loginPanel.setVisible(false);
		}
		if(adminPanel != null){
			adminPanel.setVisible(false);
		}
		
		renewDateMnt.setVisible(false);
		mainPanel.loadFromDB();
		setContentPane(mainPanel);
		mainPanel.setVisible(true);
		mnUnelte.setVisible(false);
		mntmDelogare.setVisible(true);
		mntmAdministrare.setVisible(true);
		mnGenerareRaport.setVisible(false);
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
		
		mnGenerareRaport = new JMenu("Generare Raport");
		mnGenerareRaport.setMnemonic('R');
		mnUnelte.add(mnGenerareRaport);
		
		mnRaportStudenti = new JMenu("Studenti");
		mnGenerareRaport.add(mnRaportStudenti);
		
		mntmRaportDisciplina = new JMenuItem("O Disciplina");
		mntmRaportDisciplina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showStudentSingleDiscDialog();
			}
		});
		mnRaportStudenti.add(mntmRaportDisciplina);
		
		mntmRaportToateDisciplinele = new JMenuItem("Toate Disciplinele");
		mntmRaportToateDisciplinele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showStudentAllDiscDialog();
			}
		});
		mnRaportStudenti.add(mntmRaportToateDisciplinele);
		
		mntmAn = new JMenuItem("An");
		mntmAn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showYearReportDialog();
			}
		});
		mnGenerareRaport.add(mntmAn);
		
		renewDateMnt = new JMenu("Re\u00EEnnoire date");
		mnUnelte.add(renewDateMnt);
		
		mntmImport_1 = new JMenuItem("Import");
		mntmImport_1.setHorizontalAlignment(SwingConstants.LEFT);
		renewDateMnt.add(mntmImport_1);
		
		mntmImport_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				FileFilter filter = new FileNameExtensionFilter("XLS  file", "xls");
				FileInputStream fis = null;
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new java.io.File("C:"));
				fc.setDialogTitle("Import");			
				fc.setFileFilter(filter);	
				int result = fc.showOpenDialog(mntmImport_1);
				
			if(result == JFileChooser.APPROVE_OPTION){			
				try {
					fis = new FileInputStream(fc.getSelectedFile());
					Services.ImportService.doImport(fis);
					JOptionPane.showMessageDialog(null, "Import realizat cu succes!");	
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					}			
			}else if(result == JFileChooser.CANCEL_OPTION){
				System.out.println("cancel");
			}		
		}
	});
		
		mntmDataInceperii = new JMenuItem("Data \u00EEnceperii anului");
		mntmDataInceperii.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setNewYearStart();
			}
		});
		mntmDataInceperii.setHorizontalAlignment(SwingConstants.CENTER);
		renewDateMnt.add(mntmDataInceperii);
		
		mntmGenerarePrezen = new JMenuItem("Generare prezen\u021Be");
		mntmGenerarePrezen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showLoadingScreenAndGenerateRecords();
			}
		});
		renewDateMnt.add(mntmGenerarePrezen);
		
		mnUnelte.setVisible(false);
		mntmDelogare.setVisible(false);
		
	}

	public void showLoadingScreenAndGenerateRecords(){
		
		int n = JOptionPane.showConfirmDialog(
                this, "Aceasta operatie va genera prezentele pentru toti studentii inscrisi la toate modulele pe tot parcursul anului "
                        + "si de aceea necesita timp.\nAsigurati-va ca ati importat toate datele si ati introdus toate modulele.Nu exista modalitatea de a intrerupe aceasta actiune.Continuati?",
                "Generare prezente",
                JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.YES_OPTION) {
			
			JPanel p = new JPanel(new GridLayout(1, 1));
			final JProgressBar progress = new JProgressBar() {
		        @Override public void updateUI() {
		            super.updateUI();
		            setUI(new MyProgressBar());
		            setBorder(BorderFactory.createEmptyBorder(90, 125, 125, 125));
		        }
		    };
		    progress.setStringPainted(true);
		    progress.setFont(progress.getFont().deriveFont(24f));
	        p.setPreferredSize(new Dimension(750, 450));
	        p.add(progress);
	        p.setVisible(true);
	        setContentPane(p);
	        setMenuVisible(false);
	        pack();
	        
	        SwingWorker<String, Void> worker = new GenerateRecordsWorker(){
	            @Override public void done() {
	                showAdminPanel();
	            }
	        };
			worker.addPropertyChangeListener(new ProgressBarListener(progress));
	        worker.execute();
			
		} else if (n == JOptionPane.NO_OPTION) {
			return;
		} else {
			return;
		}
			
	}
	
	public void setNewYearStart(){
		new ModalFrame(this,new ResetWeeksPanel());
	}
	
	public void showYearReportDialog() {
		new ModalFrame(this, new YearReportPanel());
	}
	
	public void showStudentSingleDiscDialog() {
		new ModalFrame(this, new ReportStudentSelPanel(true));
	}
	
	public void showStudentAllDiscDialog() {
		new ModalFrame(this, new ReportStudentSelPanel(false));
	}
	
	public void hideUnelteMenu() {
		mnUnelte.setVisible(false);
	}
	
	public void showUnelteMenu() {
		mnUnelte.setVisible(true);
	}
	
	public void updateWeekBrowser() {
		mainPanel.updateWeekBrowser();
	}
	
	public MainPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

}
