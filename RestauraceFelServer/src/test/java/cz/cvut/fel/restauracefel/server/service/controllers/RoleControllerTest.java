/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.RoleController;
import cz.cvut.fel.restauracefel.hibernate.Role;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vizi
 */
public class RoleControllerTest {

    private static RoleController instance = null;
    private static Role role = null;

    public RoleControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        instance = RoleController.getInstance( );

        role = new Role( "Test", 0 );
        role.create( );
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        role.delete( );
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testGetInstance() {
        assertFalse( RoleController.getInstance( ) == null );
        assertTrue( RoleController.getInstance( ) instanceof RoleController );
    }

    @Test
    public void testGetAllRoles( ) {
        List<Role> test = instance.getAllRoles( );
        assertFalse( test.isEmpty( ) );
        assertTrue( test.contains( role ) );
    }

    @Test
    public void testGetRoleNames() {
        String[ ] names = instance.getRoleNames( );
        assertFalse( names.length == 0 );
        assertTrue( names[ names.length - 1 ].equals( role.getName( ) ) );
    }

    @Test
    public void testGetRoleByID() {
        assertEquals( role, instance.getRoleByID( role.getRoleId( ) ) );
    }

    @Test
    public void testGetRoleByName( ) {
        assertEquals( role, instance.getRoleByName( role.getName( ) ) );
    }

}