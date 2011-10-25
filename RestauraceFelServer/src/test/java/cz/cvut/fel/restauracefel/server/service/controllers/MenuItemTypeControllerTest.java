/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.MenuItemTypeController;
import java.util.Iterator;
import java.util.List;
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
public class MenuItemTypeControllerTest {

    private static MenuItemTypeController mitcInst = MenuItemTypeController.getInstance();

    public MenuItemTypeControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        MenuItemType MenuItemType = mitcInst.getMenuItemTypeByName("testName");
        if(MenuItemType != null){
            MenuItemType.delete();
        }
    }

    @Before
    public void tearUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestCreateMenuItemType(){
        boolean result;
        result = mitcInst.createMenuItemType("testName");
        assertEquals(true, result);
        result = mitcInst.createMenuItemType("testName");
        assertEquals(false, result);
    }

    @Test
    public void TestDeleteMenuItemType() {
        MenuItemType MenuItemType = mitcInst.getMenuItemTypeByName("testName");
        if(MenuItemType == null) fail("Nenalezen zaznam s name='testName'");
        mitcInst.deleteMenuItemType(MenuItemType.getMenuItemTypeId());
        assertEquals(1, MenuItemType.getIsDeleted());
        MenuItemType.setIsDeleted(0);
    }

    @Test
    public void TestUpdateMenuItemType() {
        boolean result;
        MenuItemType MenuItemType = mitcInst.getMenuItemTypeByName("testName");
        result = mitcInst.updateMenuItemType(MenuItemType.getMenuItemTypeId(), "testNameEdit");
        assertEquals(true, result);
        result = mitcInst.updateMenuItemType(MenuItemType.getMenuItemTypeId(), "testName");
        assertEquals(true, result);
    }

    @Test
    public void  TestGetMenuItemTypeByID() {
        MenuItemType MenuItemType = mitcInst.getMenuItemTypeById(0);
        assertEquals(null, MenuItemType);
    }

    @Test
    public void  TestGetMenuItemTypeByName() {
        MenuItemType MenuItemType = mitcInst.getMenuItemTypeByName("testName");
        if(MenuItemType == null) fail();
    }

    @Test
    public void TestGetMenuItemTypeNames() {
        boolean found = false;
        String[] result = mitcInst.getMenuItemTypeNames();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("testName")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetAllMenuItemTypes() {
        boolean found = false;
        MenuItemType temp;
        List<MenuItemType> result = mitcInst.getMenuItemTypesList();
        if(result == null || result.isEmpty()) fail();

        Iterator iter = result.iterator();
        while(iter.hasNext()){
            temp = (MenuItemType) iter.next();
            if(temp.getName().equals("testName") && temp.getItemCount() == 0) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetMenuItemTypes() {
        boolean found = false;
        Object[][] result = mitcInst.getMenuItemTypes();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][1].equals("testName") && result[i][2].equals(0)) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestIsDeletableMenuItemType() {
        MenuItemType MenuItemType = mitcInst.getMenuItemTypeByName("testName");
        assertEquals(true, mitcInst.isDeletableMenuItemType(MenuItemType.getMenuItemTypeId()));
    }

}
