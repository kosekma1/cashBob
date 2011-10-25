/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.MenuController;
import cz.cvut.fel.restauracefel.server.service.controllers.UserController;
import cz.cvut.fel.restauracefel.hibernate.Menu;
import cz.cvut.fel.restauracefel.hibernate.User;
import java.util.Date;
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
public class MenuControllerTest {

    private static MenuController mcInst;
    private static User user;
    private static Date date;

    public MenuControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        mcInst = MenuController.getInstance();
        date = new Date();

        UserController uc = UserController.getInstance();
        uc.createUser("testName", "testSurname", "testUsername");
        user = uc.getUserByUsername("testUsername");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        Menu menu = mcInst.getMenuByName("testName");
        if(menu != null) menu.delete();
        if(user != null) user.delete();
    }

    @Before
    public void tearUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestCreateMenu() {
        boolean result;
        result = mcInst.createMenu(user.getUserId(), "testName", date);
        assertEquals(true, result);
        result = mcInst.createMenu(user.getUserId(), "testName", date);
        assertEquals(false, result);
        result = mcInst.createMenu(0, "testName", date);
        assertEquals(false, result);
    }

    @Test
    public void TestUpdateMenu() {
        boolean result;
        Menu menu = mcInst.getMenuByName("testName");
        result = mcInst.updateMenu(menu.getMenuId(), user.getUserId(), "testName", date);
        assertEquals(true, result);
        result = mcInst.updateMenu(0, user.getUserId(), "testName", date);
        assertEquals(false, result);
        result = mcInst.updateMenu(menu.getMenuId(), 0, "testName", date);
        assertEquals(false, result);
    }

    @Test
    public void TestGetMenus() {
        boolean found = false;
        Object[][] result = mcInst.getMenus();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][1].equals("testName") && result[i][3].equals("testUsername")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetMenuNames() {
        boolean found = false;
        String[] result = mcInst.getMenuNames();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("testName")) found = true;
        }

        assertEquals(true, found);
    }

}
