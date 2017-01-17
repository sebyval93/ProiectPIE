package gui_system;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

import Services.AnUniversitarService;
import Services.DisciplinaService;
import Services.GrupaService;
import Services.ModulService;
import Services.SaptamanaService;
import Services.SemestruService;
import Services.StudentService;
import Services.SubgrupaService;
import Services.UtilizatorService;
import Utils.EncryptService;
import Utils.Functions;
import Utils.Week;
import Utils.Workers.AdminLoginWorker;
import Utils.Workers.NormalLoginWorker;
import entity.AnUniversitar;
import entity.Disciplina;
import entity.Profesor;
import entity.Saptamana;
import entity.Utilizator;
import main.MainFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;
import Singleton.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class LoginPanel extends JPanel {
	private JTextField IdTF;
	private JPasswordField PasswordTF;
	private JButton LoginButton;
	private JLabel warningLbl;
	private JLabel GifLbl;
	private JPanel panel;
	main.MainFrame parentFrame;

	public LoginPanel() {
		Singleton.getInstance().getCurrentWeek();
		setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(30, 89, 190, 73);
		add(panel);
		panel.setLayout(null);
		
		JLabel IdLbl = new JLabel("ID");
		IdLbl.setBounds(9, 3, 11, 14);
		panel.add(IdLbl);
		
		IdTF = new JTextField();
		IdTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					doLogin();
			}
		});
		IdTF.setHorizontalAlignment(SwingConstants.CENTER);
		IdTF.setBounds(35, 0, 155, 20);
		panel.add(IdTF);
		IdTF.setColumns(10);
		
		JLabel PasswordLbl = new JLabel("Parola");
		PasswordLbl.setBounds(0, 28, 30, 14);
		panel.add(PasswordLbl);
		
		PasswordTF = new JPasswordField();
		PasswordTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					doLogin();
			}
		});
		PasswordTF.setHorizontalAlignment(SwingConstants.CENTER);
		PasswordTF.setBounds(35, 25, 155, 20);
		panel.add(PasswordTF);
		PasswordTF.setColumns(10);
		
		warningLbl = new JLabel("");
		warningLbl.setForeground(Color.RED);
		warningLbl.setBounds(77, 173, 146, 14);
		
		add(warningLbl);
		
		LoginButton = new JButton("Logare");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doLogin();
			}
		});
		LoginButton.setBounds(35, 50, 155, 23);
		panel.add(LoginButton);
		setPreferredSize(new Dimension(250, 250));
		
		GifLbl = new JLabel("");
		GifLbl.setBounds(25, 25, 200, 200);
		
		URL url = MainFrame.class.getResource("/res/load200.gif");
	    Icon icon = new ImageIcon(url);
	    GifLbl.setIcon(icon);
	    GifLbl.setVisible(false);
		add(GifLbl);
	}
	
	public void doLogin() {
		if(	!Functions.stringIsNullOrEmpty(IdTF.getText())){					
			Utilizator user = UtilizatorService.getUtilizatorByUsername(IdTF.getText());
			if(user != null){
				try {
					if(user.getPassword() == null){
						NewPasswordModal modalPanel = new NewPasswordModal(user);
						modalPanel.setParentFrame(parentFrame);
					}else{						
						if(user.getPassword().equals(
								EncryptService.getHashOfString(String.valueOf(PasswordTF.getPassword())))){
							Singleton.getInstance().currentUser = user;
							Singleton.getInstance().getCurrentProfesor();
							if (IdTF.getText().equalsIgnoreCase("admin"))
							{	
								showLoadingGif();
								PasswordTF.setText("");
								IdTF.setText("");
								warningLbl.setText("");
								//load list with special thread;
								AdminLoginWorker myWorker = new AdminLoginWorker(){
									@Override public void done() {
										parentFrame.showAdminPanel();
										parentFrame.showUnelteMenu();
										hideLoadingGif();
						            }
								};
								myWorker.execute();		

								
								return;
								
							}else{
								showLoadingGif();
								PasswordTF.setText("");
								IdTF.setText("");
								warningLbl.setText("");
								parentFrame.hideUnelteMenu();
								NormalLoginWorker myWorker = new NormalLoginWorker(){
									@Override public void done() {
										parentFrame.showMainPanel();
										parentFrame.getMainPanel().setCurrWeek(new Week(Singleton.getInstance().currentWeekStatic));
										parentFrame.updateWeekBrowser();
										hideLoadingGif();
										
						            }
								};
								myWorker.execute();	
							}
							
						}else{
							warningLbl.setText("Datele nu corespund!");
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}else{
				warningLbl.setText("Nu exista acest cont!");	
			}
		}else{										
			warningLbl.setText("Introduceti un nume de cont!");					
		}
	}
	
	public void setParentFrame(MainFrame frame) {
		parentFrame = frame;
	}

	public void resetState() {
		IdTF.setText("");
		PasswordTF.setText("");
	}
	
	public void showLoadingGif(){
		GifLbl.setVisible(true);
		panel.setVisible(false);
	}
	
	public void hideLoadingGif(){
		GifLbl.setVisible(false);
		panel.setVisible(true);
	}
	
	public void createPasswordForNewUser(Utilizator x){
		//if(x.getPassword() == null){
			
		//}
		
		JPasswordField Password = new JPasswordField();
		JPasswordField PasswordAgain = new JPasswordField();
		JLabel warningLabel = new JLabel();
		warningLabel.setForeground(Color.red);
		final JComponent[] inputs = new JComponent[] {
		        new JLabel("Parola"),
		        Password,
		        new JLabel("Rescrieti parola"),
		        PasswordAgain,
		        warningLabel
		       
		};
		int result = JOptionPane.showConfirmDialog(null, inputs, "Seteaza o parola contului", JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			if(String.valueOf(PasswordAgain.getPassword()).equals(String.valueOf(PasswordAgain.getPassword()))){
				System.out.println("You entered " +
			    		String.valueOf(Password.getPassword()) + ", " +
			    		String.valueOf(PasswordAgain.getPassword()));
			}else{
				warningLabel.setText("Parolele nu corespund");
			}
		    
		} else {
		    System.out.println("User canceled / closed the dialog, result = " + result);
		}
	}
}
