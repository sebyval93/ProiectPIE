package Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Prezenta;
import entity.Student;

public class RecordTableModel extends AbstractTableModel {

    private String[] columnNames = new String[]{"Student", "Prezenta"};
    private List<Prezenta> data;

    public RecordTableModel() {
        data = new ArrayList<>(25);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? String.class : Boolean.class;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
    	return column == 1;
    }

    @Override
    public void setValueAt(Object aValue, int row, int col) {
        boolean b = (Boolean) aValue;
        Prezenta p = data.get(row);
        p.setPrezent(new BigDecimal((b) ? 1 : 0));
        data.set(row, p);
        fireTableRowsUpdated(row, row);
        
    }
    
    @Override
    public Object getValueAt(int row, int col) {
    	Prezenta prez = data.get(row);
        Object value = null;
        switch (col) {
            case 0:
                value = prez.getStudent().getNume();
                break;
            case 1:
            	if(prez.getPrezent().intValue() == 0){
            		value = false;
            	}else if(prez.getPrezent().intValue() == 1){
            		value = true;
            	}

                break;
        }
        return value;
    }

    public void addRow(Prezenta value) {
        int rowCount = getRowCount();
        data.add(value);
        fireTableRowsInserted(rowCount, rowCount);
    }

    public void addRows(Prezenta... value) {
        addRows(Arrays.asList(value));
    }

    public void addRows(List<Prezenta> rows) {
        int rowCount = getRowCount();
        data.addAll(rows);
        fireTableRowsInserted(rowCount, getRowCount() - 1);
    }
}
