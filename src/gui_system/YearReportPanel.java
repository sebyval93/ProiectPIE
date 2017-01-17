package gui_system;

import javax.swing.JPanel;

import Services.GrupaService;
import Services.SubgrupaService;
import entity.Subgrupa;
import rapoarte.RaportAn;
import rapoarte.RaportAnTables;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JComboBox;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class YearReportPanel extends JPanel {

	private JComboBox<String> cbAn;
	public JButton btnGenerare, btnAnulare;
	/**
	 * Create the panel.
	 */
	public YearReportPanel() {
		setLayout(null);
		setPreferredSize(new Dimension(300, 160));
		
		JLabel lblTitle = new JLabel("Selectati anul pentru care doriti sa generati raportul");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(25, 21, 248, 14);
		add(lblTitle);
		
		JPanel panel = new JPanel();
		panel.setBounds(114, 70, 69, 20);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblAn = new JLabel("An:");
		lblAn.setBounds(0, 3, 17, 14);
		panel.add(lblAn);
		
		cbAn = new JComboBox<String>();
		cbAn.setBounds(27, 0, 42, 20);
		panel.add(cbAn);
		
		cbAn.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				//JComboBox<String> cb = (JComboBox)e.getSource();
				
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (cbAn.getSelectedIndex() == -1 || cbAn.getSelectedIndex() == 0) {
						btnGenerare.setEnabled(false);
						return;
					}
					btnGenerare.setEnabled(true);
				}
				
			}
			
		});
		
		btnGenerare = new JButton("Generare");
		btnGenerare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cbAn.getSelectedIndex() >= 1) {
					int an = Integer.parseInt((String)cbAn.getSelectedItem());
					RaportAn.MakeYearReport(an);
					RaportAnTables.MakeYearReport(an);
				}
			}
		});
		btnGenerare.setEnabled(false);
		btnGenerare.setBounds(25, 126, 89, 23);
		add(btnGenerare);
		
		btnAnulare = new JButton("Anulare");
		btnAnulare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeWindowWithQuestion();
			}
		});
		btnAnulare.setBounds(184, 126, 89, 23);
		add(btnAnulare);
		
		initCombo();
		
	}
	
	private void initCombo() {
		cbAn.addItem("");
		cbAn.addItem("1");
		cbAn.addItem("2");
		cbAn.addItem("3");
		cbAn.addItem("4");
	}
	
	private void closeWindowWithQuestion(){
		ModalFrame topFrame = (ModalFrame) SwingUtilities.getWindowAncestor(this);
		topFrame.closeThisWithQuestion();
		
	}
}
