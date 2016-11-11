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

import main.MainFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginPanel extends JPanel {
	private JTextField txtID;
	private JPasswordField txtPassword;
	
	main.MainFrame parentFrame;

	public LoginPanel() {
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(271, 188, 190, 73);
		//setBorder(new EmptyBorder(5, 5, 5, 5));
		add(panel);
		panel.setLayout(null);
		
		JLabel lblID = new JLabel("ID");
		lblID.setBounds(9, 3, 11, 14);
		panel.add(lblID);
		
		txtID = new JTextField();
		txtID.setHorizontalAlignment(SwingConstants.CENTER);
		txtID.setBounds(35, 0, 155, 20);
		panel.add(txtID);
		txtID.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Parola");
		lblNewLabel_1.setBounds(0, 28, 30, 14);
		panel.add(lblNewLabel_1);
		
		txtPassword = new JPasswordField();
		txtPassword.setHorizontalAlignment(SwingConstants.CENTER);
		txtPassword.setBounds(35, 25, 155, 20);
		panel.add(txtPassword);
		txtPassword.setColumns(10);
		
		JButton btnNewButton = new JButton("Logare");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// cauta in baza de date utilizatorul introdus
				// calculeaza hash-ul parolei introduse
				// verifica daca hash-ul calculat este acelasi cu cel din DB ptr user
				
				// hash-ul a fost verificat, we have a match -> show main panel
				// argumentul reprezinta ID-ul userului conectat
				parentFrame.showMainPanel(txtID.getText());
			}
		});
		btnNewButton.setBounds(35, 50, 155, 23);
		panel.add(btnNewButton);
		
	}
	
	public void setParentFrame(MainFrame frame) {
		parentFrame = frame;
	}
	
	public void resetState() {
		txtID.setText("");
		txtPassword.setText("");
	}

}
