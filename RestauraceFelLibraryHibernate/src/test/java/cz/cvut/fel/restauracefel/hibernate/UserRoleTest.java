/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.hibernate;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vizi
 */
public class UserRoleTest {

    public UserRoleTest() {
        UserRole userrole1 = new UserRole();
        UserRole userrole2 = new UserRole(null, null, 0);

        assertFalse( userrole1 ==  null );
        assertTrue( userrole1 instanceof UserRole );

        assertFalse( userrole2 ==  null );
        assertTrue( userrole2 instanceof UserRole );
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

    @Test
    public void testGetUserRoleId() {
        UserRole userRole = new UserRole(null, null, 0);

        int test = 634575;

        userRole.setUserRoleId(test);

        assertFalse(test != userRole.getUserRoleId());
    }

    @Test
    public void testSetUserRoleId() {
        UserRole userRole = new UserRole(null, null, 0);

        int test = 634575;

        userRole.setUserRoleId(test);

        assertFalse(test != userRole.getUserRoleId());
    }

    @Test
    public void testGetUser() {
        UserRole userRole = new UserRole(null, null, 0);

        User user = new User(null, null, null, null, null, 0);

        userRole.setUser(user);

        assertEquals(user, userRole.getUser());
    }

    @Test
    public void testSetUser() {
        UserRole userRole = new UserRole(null, null, 0);

        User user = new User(null, null, null, null, null, 0);

        userRole.setUser(user);

        assertEquals(user, userRole.getUser());
    }

    @Test
    public void testGetRole() {
        UserRole userRole = new UserRole(null, null, 0);

        Role test = new Role(null, 0);

        userRole.setRole(test);

        assertEquals(test, userRole.getRole());
    }

    @Test
    public void testSetRole() {
        UserRole userRole = new UserRole(null, null, 0);

        Role test = new Role(null, 0);

        userRole.setRole(test);

        assertEquals(test, userRole.getRole());
    }

    @Test
    public void testGetIsDeleted() {
        UserRole userRole = new UserRole(null, null, 0);

        int test = 634575;

        userRole.setIsDeleted(test);

        assertFalse(test != userRole.getIsDeleted());
    }

    @Test
    public void testSetIsDeleted() {
        UserRole userRole = new UserRole(null, null, 0);

        int test = 634575;

        userRole.setIsDeleted(test);

        assertFalse(test != userRole.getIsDeleted());
    }

}