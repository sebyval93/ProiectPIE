package gui_system;

import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import Services.UtilizatorService;
import Utils.EncryptService;
import Utils.Functions;
import entity.Utilizator;
import main.MainFrame;

import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;
import Singleton.*;
public class LoginPanel extends JPanel {
	private JTextField IdTF;
	private JPasswordField PasswordTF;
	
	main.MainFrame parentFrame;

	public LoginPanel() {
		Singleton.getInstance().getCurrentWeek();
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(77, 89, 190, 73);
		add(panel);
		panel.setLayout(null);
		
		JLabel IdLbl = new JLabel("ID");
		IdLbl.setBounds(9, 3, 11, 14);
		panel.add(IdLbl);
		
		IdTF = new JTextField();
		IdTF.setHorizontalAlignment(SwingConstants.CENTER);
		IdTF.setBounds(35, 0, 155, 20);
		panel.add(IdTF);
		IdTF.setColumns(10);
		
		JLabel PasswordLbl = new JLabel("Parola");
		PasswordLbl.setBounds(0, 28, 30, 14);
		panel.add(PasswordLbl);
		
		PasswordTF = new JPasswordField();
		PasswordTF.setHorizontalAlignment(SwingConstants.CENTER);
		PasswordTF.setBounds(35, 25, 155, 20);
		panel.add(PasswordTF);
		PasswordTF.setColumns(10);
		
		JLabel warningLbl = new JLabel("");
		warningLbl.setForeground(Color.RED);
		warningLbl.setBounds(77, 173, 146, 14);
		
		add(warningLbl);
		
		JButton LoginButton = new JButton("Logare");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(	!Functions.stringIsNullOrEmpty(IdTF.getText()) &&
						!Functions.stringIsNullOrEmpty(String.valueOf(PasswordTF.getPassword()))){					
					Utilizator user = UtilizatorService.getUtilizatorByUsername(IdTF.getText());
					if(user != null){
						try {
							if(user.getPassword().equals(
									EncryptService.getHashOfString(String.valueOf(PasswordTF.getPassword())))){
								Singleton.getInstance().currentUser = user;
								PasswordTF.setText("");;
								IdTF.setText("");
								parentFrame.showMainPanel();
							}else{
								warningLbl.setText("Datele cu corespund!");
							}
						} catch (NoSuchAlgorithmException e1) {
							e1.printStackTrace();
						}
					}else{
						warningLbl.setText("Acest username nu exista!");	
					}
									
				}else{										
					warningLbl.setText("Completeaza toate campurile!");					
				}

			}
		});
		LoginButton.setBounds(35, 50, 155, 23);
		panel.add(LoginButton);
		setPreferredSize(new Dimension(350, 250));
		
		
	}
	
	public void setParentFrame(MainFrame frame) {
		parentFrame = frame;
	}
	
	
	public void resetState() {
		IdTF.setText("");
		PasswordTF.setText("");
	}
}
