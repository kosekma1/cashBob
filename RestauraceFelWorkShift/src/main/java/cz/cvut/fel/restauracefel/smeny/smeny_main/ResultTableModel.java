/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.smeny.smeny_main;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Martin
 */
public class ResultTableModel extends AbstractTableModel {
          
    private String [] columnData = null;
    private Object [][] tableData = null;
    
    
    /**
     * Konstruktor tridy ResultTableModel. Ze zadanyho pole dat a nazvu sloupcu
     * vytvori tabulkovy datovy model.
     *
     * @param headerNames nazvy sloupcu
     * @param data data tabulky
     */
    public ResultTableModel(String [] headerNames, Object [][] data) {
        this.columnData = headerNames;
        this.tableData = data;
    }
    
    public int getRowCount() {
        if(this.tableData!=null){
            return this.tableData.length;
        } else {
            return 0;
        }        
    }

    public int getColumnCount() {
        if(this.columnData!=null){
            return this.columnData.length;
        } else {
            return 0;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if(this.columnData!=null){
          return this.tableData[rowIndex][columnIndex];    
        } else {
            return null;
        }
        
    }
    
      /**
     * Metoda vraci nazev sloupce na dane pozici.
     *
     * @param column cislo sloupce
     * @return nazev sloupce
     */
    @Override
    public String getColumnName(int column) {
        return this.columnData[column];
    }
}
