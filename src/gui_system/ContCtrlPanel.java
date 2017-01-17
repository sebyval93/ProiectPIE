package gui_system;

import javax.swing.JPanel;

import javax.swing.JButton;

public class ContCtrlPanel extends JPanel {
	
	private JButton btnResetPass;
	private AdminPanel parent;

	public ContCtrlPanel() {
		
		setSize(734, 119);
		setLayout(null);
		
		btnResetPass = new JButton("Resetare parola");
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
