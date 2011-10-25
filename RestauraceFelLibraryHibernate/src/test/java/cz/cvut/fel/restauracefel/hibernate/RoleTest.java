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
public class RoleTest {

    public RoleTest() {
        Role role1 = new Role();
        Role role2 = new Role(null, 0);

        assertFalse( role1 ==  null );
        assertTrue( role1 instanceof Role );

        assertFalse( role2 ==  null );
        assertTrue( role2 instanceof Role );
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
    public void testGetRoleId() {
        Role role = new Role(null, 0);

        int ID = 4535;

        role.setRoleId(ID);
        assertFalse( ID != role.getRoleId( ) );
    }

    @Test
    public void testSetRoleId() {
        Role role = new Role(null, 0);

        int ID = 4535;

        role.setRoleId(ID);
        assertFalse( ID != role.getRoleId( ) );
    }

    @Test
    public void testGetName() {
        Role role = new Role(null, 0);

        String test = "TEST";

        role.setName(test);
        assertEquals(test, role.getName( ) );
    }

    @Test
    public void testSetName() {
        Role role = new Role(null, 0);

        String test = "TEST";

        role.setName(test);
        assertEquals(test, role.getName( ) );
    }

    @Test
    public void testGetIsDeleted() {
        Role role = new Role(null, 0);

        int ID = 4535;
        
        role.setIsDeleted(ID);
        assertFalse( ID != role.getIsDeleted( ) );
    }

    @Test
    public void testSetIsDeleted() {
        Role role = new Role(null, 0);

        int ID = 4535;

        role.setIsDeleted(ID);
        assertFalse( ID != role.getIsDeleted( ) );
    }

}