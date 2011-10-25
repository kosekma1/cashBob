/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.library.service;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import cz.cvut.fel.restauracefel.hibernate.MenuItem;
import java.util.ResourceBundle;

/**
 *
 * @author Lukas Kotrba <lukas.kotrba@hotmail.com>
 */
public class PrinterTest {

    private static String separator = System.getProperty("file.separator");
    private static String userDir = System.getProperty("user.dir");
    private ResourceBundle rb = LocalizationManager.getInstance().getResourceBundle("cz.cvut.fel.restauracefel.localization.restaurace_fel_bundle");

    public PrinterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of printFromTableToCSV method, of class Printer.
     */
    @Test
    public void testPrintFromTableToCSV() {
        System.out.println("printFromTableToCSV");

        String[] colNames = {"RegularCol", "Col with space", "Col ; with semicolon", "Col with \"double quote\"", " ColWithSpaceFirst", "ColWithSpaceLast "};
        String[][] data = {{"RegularData", "Data with space", "Data with semicolon ;", "Data with \"double quote\"", " Data with space first", "Data with space last "}};
        JTable table = new JTable(data, colNames);

        String path = userDir + separator + "target" + separator + "printTableCSVTest.csv";

        Printer.printFromTableToCSV(table.getModel(), path);

        String expected = "RegularCol;Col with space;\"Col ; with semicolon\";\"Col with \"\"double quote\"\"\";\" ColWithSpaceFirst\";\"ColWithSpaceLast \";\nRegularData;Data with space;\"Data with semicolon ;\";\"Data with \"\"double quote\"\"\";\" Data with space first\";\"Data with space last \";\n";
        String actual = "";
        try {
            File file = new File(path);
            file.deleteOnExit();
            FileInputStream fis;
            fis = new FileInputStream(file);
            byte[] b = new byte[(int) file.length()];
            fis.read(b);
            fis.close();
            actual = new String(b);
            assertEquals("Saved file doesn't match expected file.", expected, actual);
        } catch (Exception ex) {
            Logger.getLogger(PrinterTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Error while reading printed CSV.");
        }
    }

    /**
     * Test of printFromTableToPrinter method, of class Printer.
     */
    //@Test //uncoment only for non-automated tests
    public void testPrintFromTableToPrinter() throws Exception {
        System.out.println("printFromTableToPrinter");

        String[] colNames = {rb.getString("material"), rb.getString("initialState"), rb.getString("sold")};
        Object[][] data = {{"Řádek 1", (double) 1, (double) 2},
            {"Řádek 2", (double) 3, (double) 4},
            {"Řádek 3", (double) 5, (double) 6}};
        JTable table = new JTable(data, colNames);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", "test");
        /*params.put("surovina", "Surovina");
        params.put("prodano", "Prodáno");
        params.put("puvodniStav", "Původní stav");*/

        String templatePath = userDir + separator + "testStuff" + separator + "stats.jasper";
        File templateFile = new File(templatePath);
        if (!templateFile.exists()) {
            fail("Template file doesn't exist!");
        }

        Printer.printFromTableToPrinter(params, table.getModel(), templateFile);
    }

    /**
     * Test of printFromTableToPrinter method, of class Printer.
     */
    @Test
    public void testPrintFromTableToPDF() throws Exception {
        System.out.println("printFromTableToPDF");

        //<editor-fold defaultstate="collapsed" desc="Stats">
        System.out.println("    - stats");

        String[] colNames = {rb.getString("material"), rb.getString("initialState"), rb.getString("sold")};
        Object[][] data = {{"Řádek 1", (double) 1, (double) 2},
            {"Řádek 2", (double) 3, (double) 4},
            {"Řádek 3", (double) 5, (double) 6}};
        JTable table = new JTable(data, colNames);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", "Test statistiky");
        /*params.put("material", "Surovina");
        params.put("sold", "Prodáno");
        params.put("initialState", "Původní stav");*/

        String templatePath = userDir + separator + "testStuff" + separator + "stats.jasper";
        File templateFile = new File(templatePath);
        if (!templateFile.exists()) {
            fail("Template file doesn't exist!");
        }

        String destinationPath = userDir + separator + "target" + separator + "statsTest.pdf";

        Printer.printFromTableToPDF(params, table.getModel(), templateFile, destinationPath);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Balance">
        System.out.println("    - balance");

        colNames = new String[]{rb.getString("material"), rb.getString("initialAmount"), rb.getString("endAmount"), rb.getString("soldByWeight"), rb.getString("soldByCashDesk"), rb.getString("difference"), rb.getString("valid")};
        data = new Object[][]{{"Řádek 1", (double) 1, (double) 1, (double) 1, (double) 1, (double) 1, "OK"},
            {"Řádek 2", (double) 2, (double) 2, (double) 2, (double) 2, (double) 2, "--"},
            {"Řádek 3", (double) 3, (double) 3, (double) 3, (double) 3, (double) 3, "OK"}};
        table = new JTable(data, colNames);

        params = new HashMap<String, Object>();
        params.put("title", "Test uzaverky");
        /*params.put("material", "Surovina");
        params.put("initialAmount", "Počáteční množství");
        params.put("endAmount", "Koncové množství");
        params.put("soldByWeight", "Podáno podle váhy");
        params.put("soldByCashDesk", "Prodáno podle pokladny");
        params.put("difference", "Rozdíl");
        params.put("valid", "Platné");*/

        templatePath = userDir + separator + "testStuff" + separator + "balance.jasper";
        templateFile = new File(templatePath);
        if (!templateFile.exists()) {
            fail("Template file doesn't exist!");
        }

        destinationPath = userDir + separator + "target" + separator + "balanceTest.pdf";

        Printer.printFromTableToPDF(params, table.getModel(), templateFile, destinationPath);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Bill">
        System.out.println("    - bill");

        colNames = new String[]{rb.getString("amount"), rb.getString("name"), rb.getString("price")};
        data = new Object[][]{{(int) 1, "Položka 1", (double) 1},
            {(int) 2, "Položka 2", (double) 2},
            {(int) 3, "Položka 3", (double) 3}};
        table = new JTable(data, colNames);

        params = new HashMap<String, Object>();
        params.put("companyName", "Mlýnská kavárna");
        /*params.put("amount", "Počet");
        params.put("name", "Název");
        params.put("price", "Cena");*/

        templatePath = userDir + separator + "testStuff" + separator + "bill.jasper";
        templateFile = new File(templatePath);
        if (!templateFile.exists()) {
            fail("Template file doesn't exist!");
        }

        destinationPath = userDir + separator + "target" + separator + "billTest.pdf";

        Printer.printFromTableToPDF(params, table.getModel(), templateFile, destinationPath);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Material">
        System.out.println("    - material");

        colNames = new String[]{rb.getString("name"), rb.getString("unit"), rb.getString("minimalAmount"), rb.getString("actualAmount")};
        data = new Object[][]{{"Položka 1", "kg", (double) 1, (double) 1},
            {"Položka 2", "kg", (double) 2, (double) 2},
            {"Položka 3", "ks", (double) 3, (double) 3}};
        table = new JTable(data, colNames);

        params = new HashMap<String, Object>();
        params.put("title", "Suroviny");
        /*params.put("name", "Název");
        params.put("actualAmount", "Aktuální počet");
        params.put("unit", "Jednotka");
        params.put("minimalAmount", "Minimální počet");
        params.put("generated", "Vygenerováno");*/

        templatePath = userDir + separator + "testStuff" + separator + "material.jasper";
        templateFile = new File(templatePath);
        if (!templateFile.exists()) {
            fail("Template file doesn't exist!");
        }

        destinationPath = userDir + separator + "target" + separator + "materialTest.pdf";

        Printer.printFromTableToPDF(params, table.getModel(), templateFile, destinationPath);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Menu">
        System.out.println("    - menu");

        colNames = new String[]{rb.getString("quantity"), rb.getString("name"), rb.getString("price")};
        data = new Object[][]{{"1", "Položka 1", (double) 1},
            {"2", "Položka 2", (double) 2},
            {"3", "Položka 3", (double) 3}};
        table = new JTable(data, colNames);

        params = new HashMap<String, Object>();
        params.put("title", "Menu");
        params.put("currency", "CZK");
        /*params.put("quantity", "Množství");
        params.put("name", "Název");
        params.put("price", "Cena");
        params.put("footerText", "Přejeme dobrou chuť");*/

        templatePath = userDir + separator + "testStuff" + separator + "menu.jasper";
        templateFile = new File(templatePath);
        if (!templateFile.exists()) {
            fail("Template file doesn't exist!");
        }

        destinationPath = userDir + separator + "target" + separator + "menuTest.pdf";

        Printer.printFromTableToPDF(params, table.getModel(), templateFile, destinationPath);
        //</editor-fold>
    }

    /**
     * Test of printFromListToPrinter method, of class Printer.
     */
    //@Test //uncoment only for non-automated tests
    public void testPrintFromListToPrinter() throws Exception {
        System.out.println("printFromListToPrinter");
        System.out.println("    - menu");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("menu", "Menu");
        params.put("currency", "CZK");
        /*params.put("quantity", "Množství");
        params.put("name", "Název");
        params.put("price", "Cena");
        params.put("footerText", "Přejeme dobrou chuť");*/

        Collection<MenuItem> menuItems = new ArrayList<MenuItem>();
        menuItems.add(new MenuItem("Polozka 1", 1, "1", 1, 0));
        menuItems.add(new MenuItem("Polozka 2", 2, "2", 1, 0));
        menuItems.add(new MenuItem("Polozka 3", 3, "3", 1, 0));

        String pressKit = userDir + separator + "testStuff" + separator + "menu.jasper";
        File templateFile = new File(pressKit);
        if (!templateFile.exists()) {
            fail("Template file doesn't exist!");
        }
        Printer.printFromListToPrinter(params, menuItems, templateFile);
    }

    /**
     * Test of printFromListToPDF method, of class Printer.
     */
    @Test
    public void testPrintFromListToPDF() throws Exception {
        System.out.println("printFromListToPDF");
        System.out.println("    - menu");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("menu", "Menu");
        params.put("currency", "CZK");
        /*params.put("quantity", "Množství");
        params.put("name", "Název");
        params.put("price", "Cena");
        params.put("footerText", "Přejeme dobrou chuť");*/

        Collection<MenuItem> menuItems = new ArrayList<MenuItem>();
        menuItems.add(new MenuItem("Polozka 1", 1, "1", 1, 0));
        menuItems.add(new MenuItem("Polozka 2", 2, "2", 1, 0));
        menuItems.add(new MenuItem("Polozka 3", 3, "3", 1, 0));

        String pressKit = userDir + separator + "testStuff" + separator + "menu.jasper";
        File templateFile = new File(pressKit);
        if (!templateFile.exists()) {
            fail("Template file doesn't exist!");
        }

        String destinationPath = userDir + separator + "target" + separator + "menuCollectionTest.pdf";

        Printer.printFromListToPDF(params, menuItems, templateFile, destinationPath);
    }
}
