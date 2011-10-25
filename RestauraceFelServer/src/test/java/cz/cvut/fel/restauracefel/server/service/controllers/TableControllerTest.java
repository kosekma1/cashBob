
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.TableController;
import cz.cvut.fel.restauracefel.hibernate.Table;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jambojak
 */
public class TableControllerTest {

    private static TableController tcInst;

    public TableControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        tcInst = TableController.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        Table toDelete = Table.findByTableNumer(-1);
        toDelete.delete();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateTable() {
        boolean result;
        result = tcInst.createTable(-1, 1);
        assertEquals(true, result);
        result = tcInst.createTable(-1, 2);
        assertEquals(false, result);
        result = tcInst.createTable(-2, 0);
        assertEquals(false, result);
    }

    @Test
    public void testGetTableByTableNumber() {
        Table table = tcInst.getTableByTableNumber(-1);
        if(table == null) fail();
    }

    @Test
    public void testGetTableNumbers() {
        boolean found = false;
        int result[] = tcInst.getTableNumbers();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i] == -1) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testGetTableNames() {
        String result[] = tcInst.getTableNames();
        if(result == null || result.length < 1) fail();
    }

    @Test
    public void testGetTables() {
        boolean found = false;
        Object[][] result = tcInst.getTables();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][1].equals(-1) && result[i][2].equals(1)) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testUpdateTable() {
        boolean result;
        result = tcInst.updateTable(tcInst.getTableByTableNumber(-1).getTableId(), -2, 2);
        assertEquals(true, result);
        result = tcInst.updateTable(tcInst.getTableByTableNumber(-2).getTableId(), -1, 1);
        assertEquals(true, result);
    }

}
