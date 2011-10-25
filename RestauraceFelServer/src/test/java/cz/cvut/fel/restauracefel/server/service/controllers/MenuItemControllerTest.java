/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.MenuItemController;
import cz.cvut.fel.restauracefel.server.service.controllers.MenuItemTypeController;
import cz.cvut.fel.restauracefel.hibernate.MenuItem;
import cz.cvut.fel.restauracefel.hibernate.MenuItemType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author honza
 */
public class MenuItemControllerTest {

    private static MenuItemController micInst;
    private static MenuItemType menuItemType;

    public MenuItemControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        micInst = MenuItemController.getInstance();

        MenuItemTypeController mitc = MenuItemTypeController.getInstance();
        mitc.createMenuItemType("testName");
        menuItemType = mitc.getMenuItemTypeByName("testName");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        MenuItem menuItem = micInst.getMenuItemByName("testName");
        if(menuItem != null) menuItem.delete();
        if(menuItemType != null) menuItemType.delete();
    }

    @Before
    public void tearUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestCreateMenuItem() {
        boolean result;
        result = micInst.createMenuItem("testName", 1.0, "1", 1, menuItemType.getMenuItemTypeId());
        assertEquals(true, result);
        result = micInst.createMenuItem("testName", 1.0, "1", 1, menuItemType.getMenuItemTypeId());
        assertEquals(false, result);
        result = micInst.createMenuItem("testName", 1.0, "1", 1, 0);
        assertEquals(false, result);
    }

    @Test
    public void TestUpdateMenuItem() {
        boolean result;
        MenuItem menuItem = micInst.getMenuItemByName("testName");
        result = micInst.updateMenuItem(menuItem.getMenuItemId(), "testName", 2.0, "2", 0, menuItemType.getMenuItemTypeId());
        assertEquals(true, result);
        assertEquals(2.0, menuItem.getPrice(), 0.0);
        assertEquals("2", menuItem.getQuantity());
        assertEquals(0, menuItem.getIsAvailable());
        result = micInst.updateMenuItem(0, "testName", 2.0, "2", 0, menuItemType.getMenuItemTypeId());
        assertEquals(false, result);
        result = micInst.updateMenuItem(menuItem.getMenuItemId(), "testName", 2.0, "2", 0, 0);
        assertEquals(false, result);
    }

    @Test
    public void TestGetMenuItemNames() {
        boolean found = false;
        String[] result = micInst.getMenuItemNames();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("testName")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetMenuItems() {
        boolean found = false;
        Object[][] result = micInst.getMenuItems();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][1].equals("testName") && result[i][2].equals("testName")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetMenuItemsByMenuItemType() {
        boolean found = false;
        Object[][] result = micInst.getMenuItemsByMenuItemType(menuItemType.getMenuItemTypeId());
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][1].equals("testName") && result[i][2].equals("testName")) found = true;
        }

        assertEquals(true, found);
    }

}
