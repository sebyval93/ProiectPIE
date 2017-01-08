package gui_system;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.sun.javafx.font.Disposer;

import Services.UtilizatorService;
import Singleton.Singleton;
import Utils.EncryptService;
import entity.Utilizator;
import main.MainFrame;

public class NewPasswordModal {

	MainFrame frame;
	JOptionPane mySelf;
	
	final JPasswordField field = new JPasswordField();
    final JPasswordField field1 = new JPasswordField();
    Utilizator utilizator;

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
	   
	   public NewPasswordModal(Utilizator user){
	       utilizator = user;
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
	                    	mySelf = getOptionPane((JComponent)e.getSource());
								utilizator.setPassword(String.valueOf(field.getPassword()));
								try {
									UtilizatorService.updateUtilizator(utilizator.getId().intValue(), utilizator.getUsername(),
											utilizator.getPassword(), utilizator.getProfesor());
									Singleton.getInstance().currentUser = UtilizatorService.getUtilizatorByID(utilizator.getId().intValue());
									//todo
									Singleton.getInstance().currentUser = utilizator;
									Singleton.getInstance().getCurrentProfesor();
									frame.showMainPanel();
									mySelf.getRootFrame().dispose();   
								} catch (NoSuchAlgorithmException e1) {
									e1.printStackTrace();
								}
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
	        		        field,
	        		        new JLabel("Rescrieti parola"),
	        		        field1	        		       
	        		};
	                field.getDocument().addDocumentListener(new DocumentListener() {
	                    protected void update() {
	                        okay.setEnabled(String.valueOf(field.getPassword()).equals(String.valueOf(field1.getPassword())));
	                    }

	                    @Override
	                    public void insertUpdate(DocumentEvent e) {
	                        update();
	                    }

	                    @Override
	                    public void removeUpdate(DocumentEvent e) {
	                        update();
	                    }

	                    @Override
	                    public void changedUpdate(DocumentEvent e) {
	                        update();
	                    }
	                });
	                
	                field1.getDocument().addDocumentListener(new DocumentListener() {
	                    protected void update() {
	                        okay.setEnabled(String.valueOf(field.getPassword()).equals(String.valueOf(field1.getPassword())));
	                    }

	                    @Override
	                    public void insertUpdate(DocumentEvent e) {
	                        update();
	                    }

	                    @Override
	                    public void removeUpdate(DocumentEvent e) {
	                        update();
	                    }

	                    @Override
	                    public void changedUpdate(DocumentEvent e) {
	                        update();
	                    }
	                });

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

