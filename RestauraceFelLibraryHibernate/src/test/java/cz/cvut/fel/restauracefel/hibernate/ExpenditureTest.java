/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.hibernate;

import java.util.Date;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vizi
 */
public class ExpenditureTest {

    public ExpenditureTest() {
        Expenditure test1 = new Expenditure( new Date( ), new Material( ), 0.0, new User( ), 0 );
        Expenditure test2 = new Expenditure( new Date( ), new Material( ), 0.0, new User( ), "Poznámka", 0 );

        assertFalse( test1 ==  null );
        assertTrue( test1 instanceof Expenditure );

        assertFalse( test2 ==  null );
        assertTrue( test2 instanceof Expenditure );
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
    public void testGetExpenditureId() {
        Expenditure exp = new Expenditure( new Date( ), new Material( ), 0.0, new User( ), 0 );
        int ID = 0;

        exp.setExpenditureId(ID);
        assertFalse( ID != exp.getExpenditureId( ) );
    }

    @Test
    public void testSetExpenditureId() {
        Expenditure exp = new Expenditure( new Date( ), new Material( ), 0.0, new User( ), 0 );
        int ID = 0;

        exp.setExpenditureId(ID);
        assertFalse( ID != exp.getExpenditureId( ) );
    }

    @Test
    public void testGetUser() {
        User test = new User( );
        Expenditure exp = new Expenditure( new Date( ), new Material( ), 0.0, new User( ), 0 );

        exp.setUser(test);

        assertEquals( test, exp.getUser( ) );
    }

    @Test
    public void testSetUser() {
        User test = new User( );
        Expenditure exp = new Expenditure( new Date( ), new Material( ), 0.0, new User( ), 0 );

        exp.setUser(test);

        assertEquals( test, exp.getUser( ) );
    }

    @Test
    public void testGetMaterial() {
        Material test = new Material( );
        Expenditure exp = new Expenditure( new Date( ), new Material( ), 0.0, new User( ), 0 );

        exp.setMaterial(test);

        assertEquals( test, exp.getMaterial( ) );
    }

    @Test
    public void testSetMaterial() {
        Material test = new Material( );
        Expenditure exp = new Expenditure( new Date( ), new Material( ), 0.0, new User( ), 0 );

        exp.setMaterial(test);

        assertEquals( test, exp.getMaterial( ) );
    }

    @Test
    public void testGetQuantity() {
        Expenditure exp = new Expenditure( new Date( ), new Material( ), 0.0, new User( ), 0 );

        exp.setQuantity( 20.0 );

        assertTrue( exp.getQuantity( ) == 20.0 );
        assertFalse( exp.getQuantity( ) != 20.0 );
    }

    @Test
    public void testSetQuantity() {
        Expenditure exp = new Expenditure( new Date( ), new Material( ), 0.0, new User( ), 0 );

        exp.setQuantity( 20.0 );

        assertTrue( exp.getQuantity( ) == 20.0 );
        assertFalse( exp.getQuantity( ) != 20.0 );
    }

    @Test
    public void testGetDate() {
        Date test = new Date( );
        Expenditure exp = new Expenditure( new Date( ), new Material( ), 0.0, new User( ), 0 );

        exp.setDate(test);

        assertEquals( test, exp.getDate( ) );
    }

    @Test
    public void testSetDate() {
        Date test = new Date( );
        Expenditure exp = new Expenditure( new Date( ), new Material( ), 0.0, new User( ), 0 );

        exp.setDate(test);

        assertEquals( test, exp.getDate( ) );
    }

    @Test
    public void testGetNote() {
        String test = "test";
        Expenditure exp = new Expenditure( new Date( ), new Material( ), 0.0, new User( ), 0 );

        exp.setNote(test);

        assertEquals( test, exp.getNote( ) );
    }

    @Test
    public void testSetNote() {
        String test = "test";
        Expenditure exp = new Expenditure( new Date( ), new Material( ), 0.0, new User( ), 0 );

        exp.setNote(test);

        assertEquals( test, exp.getNote( ) );
    }

    @Test
    public void testGetIsDeleted() {
        Expenditure exp = new Expenditure( new Date( ), new Material( ), 0.0, new User( ), 0 );

        int test = (int) ( Math.random( ) * 10000 );

        exp.setIsDeleted( test );

        assertTrue( exp.getIsDeleted( ) == test );
        assertFalse( exp.getIsDeleted( ) != test );
    }

    @Test
    public void testSetIsDeleted() {
        Expenditure exp = new Expenditure( new Date( ), new Material( ), 0.0, new User( ), 0 );

        int test = (int) ( Math.random( ) * 10000 );

        exp.setIsDeleted( test );

        assertTrue( exp.getIsDeleted( ) == test );
        assertFalse( exp.getIsDeleted( ) != test );
    }

}