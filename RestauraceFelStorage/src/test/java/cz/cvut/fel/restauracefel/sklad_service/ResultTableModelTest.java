/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.sklad_service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vizi
 */
public class ResultTableModelTest {

    public ResultTableModelTest() {
        ResultTableModel model = new ResultTableModel( );
        assertFalse( model == null );
        assertTrue( model instanceof ResultTableModel );

        Object[][] data = { {"x", "y", "z"}, {"a", "b", "c"} };

        ResultTableModel model2 = new ResultTableModel( ResultTableModel.namesMatType, data );
        assertFalse( model2 == null );
        assertTrue( model2 instanceof ResultTableModel );
    }

    @BeforeClass
    public static void setUpClass() throws Exception 
    {
        String [] namesMaterial = {"ID", "Název suroviny", "Aktuální množství", "Druh suroviny", "Jednotka", "Čárový kód", "Minimální množství","Hustota","Váha prázdného balení","Objem balení"};
        String [] namesUser = {"ID", "Křestní jméno", "Příjmení", "Role", "PIN", "Uživatelské jméno", "Vlastní heslo"};
        String [] namesIncome = {"ID", "Datum", "Surovina", "Množství", "Jednotka", "Cena", "Odpovědná osoba", "Poznámka"};
        String [] namesExpenditure = {"ID", "Datum", "Surovina", "Množství", "Jednotka", "Odpovědná osoba", "Poznámka"};
        String [] namesDepreciation = {"ID", "Datum", "Surovina", "Množství", "Jednotka", "Důvod", "Zavinil", "Zapsal", "Poznámka"};
        String [] namesMatType = {"ID", "Název druhu", "Poznámka"};
        String [] namesReasonType = {"ID", "Název důvodu odpisu", "Bližší informace"};
        String [] namesMenu = {"ID", "Název Menu", "Datum vytvoření Menu", "Vytvoříl Menu"};
        String [] namesTable = {"ID", "Číslo stolu", "Počet míst k sezení"};
        String [] namesRecipe = {"ID", "Surovina", "Množství", "Jednotka"};
        String [] namesMenuItem = {"ID", "Kategorie", "Název položky menu", "Množství", "Cena", "Dostupnost"};
        String [] namesMenuMenuItem = {"ID", "Kategorie", "Název položky menu", "Množství", "Cena"};
        String [] namesMenuItemType = {"ID", "Název kategorie", "Počet položek menu"};
        String [] namesUzaverkaKontroly = {"Název suroviny", "Původní množství", "Nové množství", "Prodáno (dle váhy)","Prodáno (dle pokladny)","Rozdíl","Platná"};
        String [] namesUzaverka = {"ID","Datum", "Počet vážených surovin", "Uživatel", "Uzavřeno"};
        String [] namesUzaverkaStatistika = {"Surovina","Od","Do","Prodáno","Prodáno lahví","Zváženo před","Zváženo po","Objem změřený váhou","Absolutní rozdíl","Procentuální rozdíl"};

        assertArrayEquals(namesMaterial, ResultTableModel.namesMaterial);
        assertArrayEquals(namesUser, ResultTableModel.namesUser);
        assertArrayEquals(namesIncome, ResultTableModel.namesIncome);
        assertArrayEquals(namesExpenditure, ResultTableModel.namesExpenditure);
        assertArrayEquals(namesDepreciation, ResultTableModel.namesDepreciation);
        assertArrayEquals(namesMatType, ResultTableModel.namesMatType);
        assertArrayEquals(namesReasonType, ResultTableModel.namesReasonType);
        assertArrayEquals(namesMenu, ResultTableModel.namesMenu);
        assertArrayEquals(namesTable, ResultTableModel.namesTable);
        assertArrayEquals(namesRecipe, ResultTableModel.namesRecipe);
        assertArrayEquals(namesMenuItem, ResultTableModel.namesMenuItem);
        assertArrayEquals(namesMenuMenuItem, ResultTableModel.namesMenuMenuItem);
        assertArrayEquals(namesMenuItemType, ResultTableModel.namesMenuItemType);
        assertArrayEquals(namesUzaverkaKontroly, ResultTableModel.namesUzaverkaKontroly);
        assertArrayEquals(namesUzaverka, ResultTableModel.namesUzaverka);
        assertArrayEquals(namesUzaverkaStatistika, ResultTableModel.namesUzaverkaStatistika);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testGetValueAt() {
        Object[][] data = { {"x", "y", "z"}, {"a", "b", "c"} };

        ResultTableModel model2 = new ResultTableModel( ResultTableModel.namesMatType, data );
        assertEquals(model2.getValueAt(0, 0), "x");
        assertEquals(model2.getValueAt(0, 1), "y");
        assertEquals(model2.getValueAt(0, 2), "z");
        assertEquals(model2.getValueAt(1, 0), "a");
        assertEquals(model2.getValueAt(1, 1), "b");
        assertEquals(model2.getValueAt(1, 2), "c");
    }

    @Test
    public void testGetColumnCount() {
        Object[][] data = { {"x", "y", "z"}, {"a", "b", "c"} };

        ResultTableModel model = new ResultTableModel( ResultTableModel.namesMatType, data );

        assertFalse( model.getColumnCount( ) != 3 );
    }

    @Test
    public void testGetRowCount() {
        Object[][] data = { {"x", "y", "z"}, {"a", "b", "c"} };

        ResultTableModel model = new ResultTableModel( ResultTableModel.namesMatType, data );

        assertFalse( model.getRowCount( ) != 2 );
    }

    @Test
    public void testGetColumnName() {
        Object[][] data = { {"x", "y", "z"}, {"a", "b", "c"} };

        ResultTableModel model = new ResultTableModel( ResultTableModel.namesMatType, data );

        for (int i = 0; i < ResultTableModel.namesMatType.length; i++ ) {
            assertEquals( model.getColumnName( i ), ResultTableModel.namesMatType[ i ] );
        }

    }

    @Test
    public void testGetColumnData() {
        Object[][] data = { {"x", "y", "z"}, {"a", "b", "c"} };

        ResultTableModel model = new ResultTableModel( ResultTableModel.namesRecipe, data );

        assertEquals(ResultTableModel.namesRecipe, model.getColumnData( ));

    }

    @Test
    public void testSetColumnData() {
        Object[][] data = { {"x", "y", "z"}, {"a", "b", "c"} };

        ResultTableModel model = new ResultTableModel( ResultTableModel.namesReasonType, data );

        model.setColumnData( ResultTableModel.namesUzaverkaKontroly );

        assertEquals(model.getColumnData( ), ResultTableModel.namesUzaverkaKontroly );
    }

    @Test
    public void testGetTableData() {
        Object[][] data = { {"x", "y", "z"}, {"a", "b", "c"} };

        ResultTableModel model = new ResultTableModel( ResultTableModel.namesReasonType, data );


        assertEquals(model.getTableData( ), data );
    }

    @Test
    public void testSetTableData() {
        Object[][] data = { {"x", "y", "z"}, {"a", "b", "c"} };
        Object[][] data2 = { {"q", "w", "e"}, {"a", "s", "d"} };

        ResultTableModel model = new ResultTableModel( ResultTableModel.namesReasonType, data );

        model.setTableData(data2);

        assertEquals(model.getTableData( ), data2 );
    }

}