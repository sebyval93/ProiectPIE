package gui_system;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import Services.SaptamanaService;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

public class ResetWeeksPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	JLabel startLbl;
	JButton CancelButton;
	JButton okButton;
	JDateChooser dateChooser;
	
	public ResetWeeksPanel() {
		setLayout(null);
		this.setPreferredSize(new Dimension(350, 130));
		startLbl = new JLabel("Selectati data de inceput al anului unveristar!");
		startLbl.setHorizontalAlignment(SwingConstants.CENTER);
		startLbl.setBounds(10, 21, 330, 14);
		add(startLbl);
		
		CancelButton = new JButton("Anulare");
		CancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeWindowWithQuestion();
			}
		});
		CancelButton.setBounds(185, 96, 100, 23);
		add(CancelButton);
		
		okButton = new JButton("Actualizare");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetWeeks();
			}
		});
		okButton.setBounds(75, 96, 100, 23);
		add(okButton);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd-MMM-yyyy");
		dateChooser.setBounds(36, 56, 280, 20);
		add(dateChooser);

		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
		editor.setHorizontalAlignment(JTextField.CENTER);
		editor.setEditable(false);
		dateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
		    @Override
		    public void propertyChange(PropertyChangeEvent evt) {
		        Date date = (Date)evt.getNewValue();
		        if(date != null){
		        	okButton.setEnabled(true);
		        }
		    }
		});
		
		okButton.setEnabled(false);		
	}
	
	public void closeWindow(){
		ModalFrame topFrame = (ModalFrame) SwingUtilities.getWindowAncestor(this);
		topFrame.closeThis();
		
	}
	
	public void closeWindowWithQuestion(){
		ModalFrame topFrame = (ModalFrame) SwingUtilities.getWindowAncestor(this);
		topFrame.closeThisWithQuestion();
		
	}
	
	
	public void resetWeeks(){
		Date date = dateChooser.getDate();
		if(date != null){
			boolean done = SaptamanaService.updateSaptamaniForNewYear(date);
			if(done){
				int res = JOptionPane.showOptionDialog(null, "Succes", "", JOptionPane.DEFAULT_OPTION,
				        JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if(res == 0 || res == -1){
					closeWindow();
				}
			}
		}
	}
	
}
