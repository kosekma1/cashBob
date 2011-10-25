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
public class UnitTypeTest {

    public UnitTypeTest() {
        UnitType ut1 = new UnitType( );
        UnitType ut2 = new UnitType( null, null, 0, 0 );

        assertFalse( ut1 ==  null );
        assertTrue( ut1 instanceof UnitType );

        assertFalse( ut2 ==  null );
        assertTrue( ut2 instanceof UnitType );
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
    public void testGetUnitTypeId() {
        UnitType ut = new UnitType( null, null, 0, 0 );

        int ID = 64768757;

        ut.setUnitTypeId( ID );
        assertFalse( ID != ut.getUnitTypeId( ) );
    }

    @Test
    public void testSetUnitTypeId() {
        UnitType ut = new UnitType( null, null, 0, 0 );

        int ID = 64768757;

        ut.setUnitTypeId( ID );
        assertFalse( ID != ut.getUnitTypeId( ) );
    }

    @Test
    public void testGetName() {
        UnitType ut = new UnitType( null, null, 0, 0 );

        String name = "nameX";

        ut.setName( name );
        assertEquals( name, ut.getName( ) );
    }

    @Test
    public void testSetName() {
        UnitType ut = new UnitType( null, null, 0, 0 );

        String name = "nameX";

        ut.setName( name );
        assertEquals( name, ut.getName( ) );
    }

    @Test
    public void testGetAbbreviation() {
    }

    @Test
    public void testSetAbbreviation() {
        UnitType ut = new UnitType( null, null, 0, 0 );

        String abb = "AbbX";

        ut.setAbbreviation( abb );
        assertEquals( abb, ut.getAbbreviation( ) );
    }

    @Test
    public void testGetTypeId() {
        UnitType ut = new UnitType( null, null, 0, 0 );

        int ID = 64768757;

        ut.setTypeId( ID );
        assertFalse( ID != ut.getTypeId( ) );
    }

    @Test
    public void testSetTypeId() {
        UnitType ut = new UnitType( null, null, 0, 0 );

        int ID = 64768757;

        ut.setTypeId( ID );
        assertFalse( ID != ut.getTypeId( ) );
    }

    @Test
    public void testGetIsDeleted() {
        UnitType ut = new UnitType( null, null, 0, 0 );

        int test = 64768757;

        ut.setIsDeleted(test);
        assertFalse( test != ut.getIsDeleted( ) );
    }

    @Test
    public void testSetIsDeleted() {
        UnitType ut = new UnitType( null, null, 0, 0 );

        int test = 64768757;

        ut.setIsDeleted(test);
        assertFalse( test != ut.getIsDeleted( ) );
    }

}