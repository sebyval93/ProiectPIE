package gui_system;

import javax.swing.JPanel;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ContCtrlPanel extends JPanel {
	
	private JButton btnResetPass;
	private AdminPanel parent;

	public ContCtrlPanel() {
		
		setSize(734, 119);
		setLayout(null);
		
		btnResetPass = new JButton("Resetare parola");
		btnResetPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doReset();
			}
		});
		btnResetPass.setEnabled(false);
		btnResetPass.setBounds(220, 48, 293, 23);
		add(btnResetPass);

	}
	
	public void setParent(AdminPanel panel) {
		parent = panel;
	}
	
	public void enableReset(boolean visible) {
		btnResetPass.setEnabled(visible);
	}
	
	public void doReset() {
		parent.resetPassword();
	}
}
