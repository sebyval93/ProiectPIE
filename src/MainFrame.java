import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	//private JPanel cardsPane;
	//private gui.LoginPanel loginPanel;
	//private gui.ManagePanel managePanel;
	private gui_system.LoginFrame loginFrame;
	private JTable table;
	private DefaultTableModel moduleModel, grupeModel, studentiModel;
	private DefaultTableCellRenderer centerCellRenderer;
	//                        0        1
	private enum TipGrupa { SUBGRUPA, GRUPA };
	//                        0        1
	private enum Prezenta { ABSENT, PREZENT };
	//                         0      1       2
	private enum Saptamana { IMPARA, PARA, INTEGRAL };
	//                         0       1         2         3
	private enum Activitate { CURS, SEMINAR, LABORATOR, PROIECT };
	
	private String moduleColumns[] = { "Nume Disciplina", "Activitate", "An Disciplina", 
			"Tip Saptamana", "Numar Saptamana" ,"Tip Grupa" 
	};
	private String grupeColumns[] = { "Denumire", "Tip" };
	private String studentiColumns[] = { "Nume Student", "Prezenta" };
	
	private Object moduleData[][] = { 
			{ "Baze de Date", Activitate.LABORATOR, 2, Saptamana.IMPARA, 3, TipGrupa.SUBGRUPA },
			{ "Proiectarea Bazelor de Date", Activitate.LABORATOR, 3, Saptamana.PARA, 3, TipGrupa.SUBGRUPA },
			{ "Proiect Informatic in Echipa", Activitate.PROIECT, 4, Saptamana.INTEGRAL, 3, TipGrupa.GRUPA }
	};
	private Object grupeData1[][] = {
			{ "22c31a", TipGrupa.SUBGRUPA },
			{ "22c31b", TipGrupa.SUBGRUPA },
			{ "22c31c", TipGrupa.SUBGRUPA }
	};
	private Object grupeData2[][] = {
			{ "22c32a", TipGrupa.SUBGRUPA },
			{ "22c32b", TipGrupa.SUBGRUPA }
	};
	private Object grupeData3[][] = {
			{ "22c31", TipGrupa.GRUPA },
			{ "22c32", TipGrupa.GRUPA },
			{ "22c33", TipGrupa.GRUPA }
	};
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		setTitle("DB");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750,558);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 724, 291);
		contentPane.add(scrollPane);
		
		//for (int i = 0; i < 20; ++i)
		//	moduleModel.addRow(new Object[] {i, (char)(i+60)});
		
		centerCellRenderer = new DefaultTableCellRenderer();
		centerCellRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(25);
		table.setCellSelectionEnabled(false);
		
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable t =(JTable) me.getSource();
				Point p = me.getPoint();
				int row = t.rowAtPoint(p);
				if (me.getClickCount() == 2) {
					DefaultTableModel model = (DefaultTableModel) t.getModel();
					if (model == moduleModel)
						loadGrupe(row);
					else if (model == grupeModel)
						loadStudenti();
				}
			}
		});
		
		
		//table.setModel(moduleModel);
		//table.getColumnModel().getColumn(0).setCellRenderer(centerCellRenderer);
		
		
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Inapoi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getModel() == grupeModel) {
					table.setModel(moduleModel);
					for (int i = 0; i < table.getColumnCount(); ++i) {
						table.getColumnModel().getColumn(i).setCellRenderer(centerCellRenderer);
					}
					table.getColumnModel().getColumn(0).setPreferredWidth(120);
				}
				else if (table.getModel() == studentiModel) {
					table.setModel(grupeModel);
					for (int i = 0; i < table.getColumnCount(); ++i) {
						table.getColumnModel().getColumn(i).setCellRenderer(centerCellRenderer);
					}
				}
			}
		});
		btnNewButton.setBounds(10, 308, 724, 23);
		contentPane.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Instrumente Cautare", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(143, 356, 457, 149);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblCautareDisciplina = new JLabel("Cautare Disciplina:");
		lblCautareDisciplina.setBounds(10, 29, 99, 14);
		panel.add(lblCautareDisciplina);
		
		JLabel lblCautareActivitate = new JLabel("Cautare Activitate:");
		lblCautareActivitate.setBounds(10, 54, 99, 14);
		panel.add(lblCautareActivitate);
		
		JLabel lblCautareAn = new JLabel("Cautare An:");
		lblCautareAn.setBounds(10, 79, 89, 14);
		panel.add(lblCautareAn);
		
		textField = new JTextField();
		textField.setBounds(119, 26, 86, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(119, 51, 86, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(119, 76, 86, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblCautareTipGrupa = new JLabel("Cautare Tip Saptamana:");
		lblCautareTipGrupa.setBounds(231, 40, 117, 14);
		panel.add(lblCautareTipGrupa);
		
		JLabel label = new JLabel("Cautare Tip Grupa:");
		label.setBounds(231, 65, 117, 14);
		panel.add(label);
		
		JComboBox<String> cbTipSapt = new JComboBox<String>();
		cbTipSapt.setBounds(358, 37, 89, 20);
		panel.add(cbTipSapt);
		
		JComboBox<String> cbTipGrupa = new JComboBox<String>();
		cbTipGrupa.setBounds(358, 62, 89, 20);
		panel.add(cbTipGrupa);
		
		JButton btnNewButton_1 = new JButton("Cautare");
		btnNewButton_1.setBounds(184, 107, 89, 23);
		panel.add(btnNewButton_1);
		cbTipGrupa.addItem("");
		cbTipGrupa.addItem("GRUPA");
		cbTipGrupa.addItem("SUBGRUPA");
		cbTipSapt.addItem("");
		cbTipSapt.addItem("PARA");
		cbTipSapt.addItem("IMPARA");
		cbTipSapt.addItem("INTEGRAL");
		
		setupModule();
		setupGrupe();
		setupStudenti();
		
		loadModule();
		
		
	}
	
	public void setupModule() {
		
		moduleModel = new DefaultTableModel() {
		      public boolean isCellEditable(int row, int column){  
		          return false;
		      }
		};
		/*{
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
		};
		*/
		
		for (int i = 0; i < moduleColumns.length; ++i) {
			moduleModel.addColumn(moduleColumns[i]);
		}
		
		((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}
	
	public void setupGrupe() {
		grupeModel = new DefaultTableModel() {
		      public boolean isCellEditable(int row, int column){  
		          return false;
		      }
		};
		
		for (int i = 0; i < grupeColumns.length; ++i) {
			grupeModel.addColumn(grupeColumns[i]);
		}
		
        ((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}
	
	public void setupStudenti() {
		studentiModel = new DefaultTableModel() {
		      public boolean isCellEditable(int row, int column){  
		    	  if (column == 1)
		    		  return true;
		    	  
		          return false;
		      }
		      
	            public Class getColumnClass(int column) {
	            	switch (column) {
	                   case 0:
	                       return String.class;
	                   default:
	                       return Boolean.class;
	               }
	           };
		};
		
		for (int i = 0; i < studentiColumns.length; ++i) {
			studentiModel.addColumn(studentiColumns[i]);
		}
		
		((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}
	
	//--
	
	public void loadModule() {
		for (int i = 0; i < moduleData.length; ++i) {
			moduleModel.addRow(moduleData[i]);
		}
		
		table.setModel(moduleModel);
		
		for (int i = 0; i < table.getColumnCount(); ++i) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerCellRenderer);
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		
		//table.getColumnModel().getColumn(0).setCellRenderer(centerCellRenderer);
	}
	
	public void loadGrupe(int nr) {
		if (grupeModel.getRowCount() > 0) {
			grupeModel.setRowCount(0);
		}
		Object grupeData[][] = null;
		switch(nr) {
		case 0:
			grupeData = grupeData1;
			break;
		case 1:
			grupeData = grupeData2;
			break;
		case 2:
			grupeData = grupeData3;
			break;
		}
		
		for (int i = 0; i < grupeData.length; ++i) {
			grupeModel.addRow(grupeData[i]);
		}
		
		table.setModel(grupeModel);
		
		for (int i = 0; i < table.getColumnCount(); ++i) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerCellRenderer);
		}
	}
	
	public void loadStudenti() {
		if (studentiModel.getRowCount() > 0) {
			studentiModel.setRowCount(0);
		}
		
		for (int i = 0; i < 20; ++i) {
			studentiModel.addRow(new Object[] { "Student " + i, false });
		}
		
		table.setModel(studentiModel);
		
		for (int i = 0; i < table.getColumnCount() - 1; ++i) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerCellRenderer);
		}
	}
}
