package gui_system;

import javax.swing.*;

import main.MainFrame;

public class ModalFrame extends JFrame{
	
	MainFrame parent;
	
	public ModalFrame(MainFrame parent,JPanel panel){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		this.parent = parent;
		this.setContentPane(panel);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.parent.setEnabled(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	realyClosing();
		    }
		});
	}
	
	 
	
	public void closeThisWithQuestion(){
		realyClosing();
	}
	
	public void closeThis(){
		parent.setEnabled(true);
		this.dispose();
	}
	

	
	public void realyClosing(){
		if (JOptionPane.showConfirmDialog(null, 
	            "Esti sigur ca vrei sa inchizi aceasta fereastra?", "", 
	            JOptionPane.YES_NO_OPTION,
	            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
	            	closeThis();
	        }
	}
	

}
