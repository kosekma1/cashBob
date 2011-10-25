package cz.cvut.fel.restauracefel.manager_service;

import javax.swing.table.AbstractTableModel;

/**
 * Trida reprezentujici datovy model pro tabulky.
 *
 * @author Jarda
 */
public class ResultTableModel extends AbstractTableModel {

    //column names
    public static String [] namesMaterial = {"ID", "Název suroviny",
    "Aktuální množství", "Druh suroviny", "Jednotka", "Čárový kód", "Minimální množství","Hustota","Váha prázdného balení","Objem balení"};

    public static String [] namesUser = {"ID", "Křestní jméno", "Příjmení", "Role", "PIN", "Uživatelské jméno", "Vlastní heslo"};
    public static String [] namesIncome = {"ID", "Datum", "Surovina", "Množství", "Jednotka", "Cena", "Odpovědná osoba", "Poznámka"};
    public static String [] namesExpenditure = {"ID", "Datum", "Surovina", "Množství", "Jednotka", "Odpovědná osoba", "Poznámka"};
    public static String [] namesDepreciation = {"ID", "Datum", "Surovina", "Množství", "Jednotka", "Důvod", "Zavinil", "Zapsal", "Poznámka"};
    public static String [] namesMatType = {"ID", "Název druhu", "Poznámka"};
    public static String [] namesReasonType = {"ID", "Název důvodu odpisu", "Bližší informace"};
    public static String [] namesMenu = {"ID", "Název Menu", "Datum vytvoření Menu", "Vytvoříl Menu"};
    public static String [] namesTable = {"ID", "Číslo stolu", "Počet míst k sezení"};
    public static String [] namesRecipe = {"ID", "Surovina", "Množství", "Jednotka"};
    public static String [] namesMenuItem = {"ID", "Kategorie", "Název položky menu", "Množství", "Cena", "Dostupnost"};
    public static String [] namesMenuMenuItem = {"ID", "Kategorie", "Název položky menu", "Množství", "Cena"};
    public static String [] namesMenuItemType = {"ID", "Název kategorie", "Počet položek menu"};
    public static String [] namesUzaverkaKontroly = {"Název suroviny", "Původní množství", "Nové množství", "Prodáno (dle váhy)","Prodáno (dle pokladny)","Rozdíl","Platná"};
    public static String [] namesUzaverka = {"ID","Datum", "Počet vážených surovin", "Uživatel"};
    public static String [] namesUzaverkaStatistika = {"Surovina","Původní stav","Prodáno"};


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

    /**
     * Bezparametricky konstruktor tridy ResultTableModel.
     */
    public ResultTableModel(){}
    
    /**
     * Vraci hodnotu z datoveho modelu pro dany radek a sloupec.
     *
     * @param rowIndex index radku
     * @param columnIndex index sloupce
     * @return hodnota reprezentovana objektem tridy Object
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (this.tableData == null){
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
        if (this.tableData == null){
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
    public Object [][] getTableData() {
        return tableData;
    }

    /**
     * Metoda nastavujici data datoveho modelu.
     *
     * @param tableData datovy model
     */
    public void setTableData(Object [][] tableData) {
        this.tableData = tableData;
    }

}
