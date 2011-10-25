/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.UserRoleController;
import cz.cvut.fel.restauracefel.server.service.controllers.UserController;
import java.util.Iterator;
import java.util.List;
import cz.cvut.fel.restauracefel.hibernate.UserRole;
import cz.cvut.fel.restauracefel.hibernate.User;
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
public class UserRoleControllerTest {

    private static UserRoleController urcInst;
    private static User user;

    public UserRoleControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        urcInst = UserRoleController.getInstance();

        UserController uc = UserController.getInstance();
        uc.createUser("testName", "testSurname", "testUsername");
        user = uc.getUserByUsername("testUsername");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        UserRole userRole = urcInst.getUserRoleByUserAndRole(user.getUserId(), 1);
        if(userRole != null) userRole.delete();
        if(user != null) user.delete();
    }

    @Before
    public void tearUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestCreateUserRole() {
        boolean result;
        result = urcInst.createUserRole(user.getUserId(), 1);
        assertEquals(true, result);
        result = urcInst.createUserRole(user.getUserId(), 1);
        assertEquals(false, result);
        result = urcInst.createUserRole(0, 1);
        assertEquals(false, result);
        result = urcInst.createUserRole(user.getUserId(), 0);
        assertEquals(false, result);
    }

    @Test
    public void TestGetUserRoleByUserId() {
        boolean found = false;
        UserRole temp;
        List<UserRole> result = urcInst.getUserRoleByUserId(user.getUserId());
        if(result == null || result.isEmpty()) fail();

        Iterator iter = result.iterator();
        while(iter.hasNext()){
            temp = (UserRole) iter.next();
            if(temp.getUser() == user && temp.getRole().getRoleId() == 1) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetUserRoles() {
        boolean[] result = urcInst.getUserRoles(user.getUserId());
        assertEquals(true, result[0]);
        assertEquals(false, result[1]);
        assertEquals(false, result[2]);
    }

}
