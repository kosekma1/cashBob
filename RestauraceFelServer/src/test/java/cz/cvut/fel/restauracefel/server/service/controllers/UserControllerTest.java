/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.UserController;
import java.util.Iterator;
import cz.cvut.fel.restauracefel.hibernate.User;
import java.util.List;
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
public class UserControllerTest {

    private static UserController ucInst;

    public UserControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        ucInst = UserController.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        User user;
        user = ucInst.getUserByUsername("testUsername1");
        if(user != null) user.delete();
        user = ucInst.getUserByUsername("testUsername2");
        if(user != null) user.delete();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateUser() {
        boolean result;
        result = ucInst.createUser("testName", "testSurname", "0000", "testUsername1", "testPasswd");
        assertEquals(true, result);
        result = ucInst.createUser("testName", "testSurname", "testUsername2");
        assertEquals(true, result);
        result = ucInst.createUser("testName", "testSurname", "0000", "testUsername1", "testPasswd");
        assertEquals(false, result);
        result = ucInst.createUser("testName", "testSurname", "testUsername2");
        assertEquals(false, result);
    }

    @Test
    public void testDeleteUser() {
        User user = ucInst.getUserByUsername("testUsername1");
        ucInst.deleteUser(user.getUserId());
        assertEquals(1, user.getIsDeleted());
        user.setIsDeleted(0);
    }

    @Test
    public void testIsExistedByUsername() {
        boolean result;
        result = ucInst.isExistedByUsername("testUsername1");
        assertEquals(true, result);
        result = ucInst.isExistedByUsername("testUsername2");
        assertEquals(true, result);
    }

    @Test
    public void testIsExistedByPID() {
        boolean result;
        result = ucInst.isExistedByPID("0000");
        assertEquals(true, result);
    }

    @Test
    public void testIsValidUser() {
        boolean result;
        result = ucInst.isValidUser("testUsername1", "testPasswd");
        assertEquals(true, result);
        result = ucInst.isValidUser("testUsername1", "");
        assertEquals(false, result);
        result = ucInst.isValidUser("", "testPasswd");
        assertEquals(false, result);
    }

    @Test
    public void testGetUserByPID() {
        User user;
        user = ucInst.getUserByPID("0000");
        assertEquals("testName", user.getFirstName());
        assertEquals("testUsername1", user.getUsername());
    }

    @Test
    public void testGetUserByUsername() {
        User user;
        user = ucInst.getUserByUsername("testUsername1");
        assertEquals("testName", user.getFirstName());
    }

    @Test
    public void testGetAllUsers() {
        boolean found = false;
        User temp;
        List<User> result = ucInst.getAllUsers();
        if(result == null || result.isEmpty()) fail();

        Iterator iter = result.iterator();
        while(iter.hasNext()){
            temp = (User) iter.next();
            if(temp.getFirstName().equals("testName") && temp.getLastName().equals("testSurname")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testGetUserNames() {
        boolean found = false;
        String[] result = ucInst.getUserNames();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("testSurname")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testGetUserUsernames() {
        boolean found = false;
        String[] result = ucInst.getUserUsernames();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("testUsername1")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testGetUsers() {
        boolean found = false;
        Object[][] result = ucInst.getUsers();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][1].equals("testName") && result[i][2].equals("testSurname") && result[i][5].equals("testUsername1")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testUpdateUser() {
        User user = ucInst.getUserByUsername("testUsername1");
        boolean result;
        result = ucInst.updateUser(user.getUserId(), "testNameEdit", "testSurnameEdit", "0000", "testUsername1");
        assertEquals(true, result);
        result = ucInst.updateUser(user.getUserId(), "testNameEdit", "testSurnameEdit", "0000", "testUsername2");
        assertEquals(false, result);
        result = ucInst.updateUser(user.getUserId(), "testNameEdit", "testSurnameEdit", "0000", "testUsername1");
        assertEquals(true, result);

        result = ucInst.updateUser(user.getUserId(), 10.0);
        assertEquals(true, result);
        assertEquals(10.0, user.getCredit(), 0);
        result = ucInst.updateUser(user.getUserId(), -10.0);
        assertEquals(true, result);
        assertEquals(0.0, user.getCredit(), 0);
    }

    @Test
    public void testUpdateUserPassword() {
        User user = ucInst.getUserByUsername("testUsername1");
        boolean result;
        result = ucInst.updateUserPassword(user.getUserId(), "testPasswdEdit");
        assertEquals(true, result);
        assertEquals("testPasswdEdit", user.getPassword());
    }

    @Test
    public void testIsValidOldPasswd() {
        User user = ucInst.getUserByUsername("testUsername1");
        boolean result;
        result = ucInst.isValidOldPasswd(user.getUserId(), "testPasswdEdit");
        assertEquals(true, result);
        result = ucInst.isValidOldPasswd(user.getUserId(), "sgsgd");
        assertEquals(false, result);
    }

}