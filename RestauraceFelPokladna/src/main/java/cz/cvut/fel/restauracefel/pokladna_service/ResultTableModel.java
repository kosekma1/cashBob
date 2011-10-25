package cz.cvut.fel.restauracefel.pokladna_service;

import cz.cvut.fel.restauracefel.library.service.LocalizationManager;
import java.util.ResourceBundle;
import javax.swing.table.AbstractTableModel;

/**
 * Trida reprezentujici datovy model pro tabulky.
 *
 * @author Jarda
 * @author Tomas Hnizdil
 */
public class ResultTableModel extends AbstractTableModel {

    //column names
    private static final ResourceBundle rb = LocalizationManager.getInstance().getResourceBundle("cz.cvut.fel.restauracefel.localization.restaurace_fel_bundle");
    public static String[] namesMaterial = {"ID", "Název suroviny", "Aktuální množství", "Druh suroviny", "Jednotka", "Čárový kód", "Minimální množství"};
    public static String[] namesAccount = {"ID", "Název", "Stůl", "Osoba", "Typ slevy", "Stav", "Kategorie", "Poznámka", "Celková suma", "Nezaplacená suma"};
    public static String[] namesBill = {rb.getString("amount"), rb.getString("name"), rb.getString("price")};
    private String[] columnData = null;
    private Object[][] tableData = null;

    /**
     * Konstruktor tridy ResultTableModel. Ze zadanyho pole dat a nazvu sloupcu
     * vytvori tabulkovy datovy model.
     *
     * @param headerNames nazvy sloupcu
     * @param data data tabulky
     */
    public ResultTableModel(String[] headerNames, Object[][] data) {
        this.columnData = headerNames;
        this.tableData = data;
    }

    /**
     * Bezparametricky konstruktor tridy ResultTableModel.
     */
    public ResultTableModel() {
    }

    /**
     * Vraci hodnotu z datoveho modelu pro dany radek a sloupec.
     *
     * @param rowIndex index radku
     * @param columnIndex index sloupce
     * @return hodnota reprezentovana objektem tridy Object
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (this.tableData == null) {
            return null;
        }
        return this.tableData[rowIndex][columnIndex];
    }

    /**
     * Metoda vraci pocet sloupcu sloupcu.
     *
     * @return pocet sloupcu
     */
    public int getColumnCount() {
        return this.columnData.length;
    }

    /**
     * Metoda vraci pocet radku.
     *
     * @return pocet radku
     */
    public int getRowCount() {
        if (this.tableData == null) {
            return 0;
        }
        return this.tableData.length;
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

    /**
     * Metoda vraci pole obsahujici nazvy sloupcu.
     *
     * @return pole String retezcu s nazvy sloupcu
     */
    public String[] getColumnData() {
        return columnData;
    }

    /**
     * Metoda nastavujici pro datovy model nazvy sloupcu.
     *
     * @param columnData pole String retezcu obsahujici nazvy sloupcu
     */
    public void setColumnData(String[] columnData) {
        this.columnData = columnData;
    }

    /**
     * Metoda navraci data datoveho modelu.
     *
     * @return datovy model
     */
    public Object[][] getTableData() {
        return tableData;
    }

    /**
     * Metoda nastavujici data datoveho modelu.
     *
     * @param tableData datovy model
     */
    public void setTableData(Object[][] tableData) {
        this.tableData = tableData;
    }
}
