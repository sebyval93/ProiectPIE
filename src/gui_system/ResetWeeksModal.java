package gui_system;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.toedter.calendar.JDateChooser;

import Services.UtilizatorService;
import entity.Utilizator;
import main.MainFrame;

public class ResetWeeksModal {
	MainFrame frame;
	JOptionPane mySelf;
	
	final JDateChooser chooser = new JDateChooser();

	   protected JOptionPane getOptionPane(JComponent parent) {
	        JOptionPane pane = null;
	        if (!(parent instanceof JOptionPane)) {
	            pane = getOptionPane((JComponent)parent.getParent());
	        } else {
	            pane = (JOptionPane) parent;
	        }
	        return pane;
	    }
	   
	   public void setParentFrame(MainFrame frame){
		   this.frame = frame;
	   }
	   
	   public ResetWeeksModal(){

		   EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	                }

	                final JButton okay = new JButton("Ok");
	                okay.addActionListener(new ActionListener() {
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                    	
	                    }
	                });
	                okay.setEnabled(false);
	                final JButton cancel = new JButton("Cancel");
	                cancel.addActionListener(new ActionListener() {
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                        JOptionPane pane = getOptionPane((JComponent)e.getSource());
	                        pane.setValue(cancel);
	                    }
	                });

	                final JComponent[] inputs = new JComponent[] {
	        		        new JLabel("Parola"),
	        		        chooser	        		           		       
	        		};
	                

	                JOptionPane.showOptionDialog(
	                                null, 
	                                inputs, 
	                                "Alegeti o parola contului", 
	                                JOptionPane.NO_OPTION, 
	                                JOptionPane.INFORMATION_MESSAGE, 
	                                null, 
	                                new Object[]{okay, cancel}, 
	                                okay);
	            }
	        });
	    }
}
