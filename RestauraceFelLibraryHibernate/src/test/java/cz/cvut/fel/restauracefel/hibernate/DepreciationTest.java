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
public class DepreciationTest {

    public DepreciationTest() {
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );
        assertFalse( dep1 ==  null );
        assertTrue( dep1 instanceof Depreciation );

        Depreciation dep2 = new Depreciation( new User( ), new User( ), new Material( ), new ReasonType( "jmeno", 0 ), 0.0f, new Date( ), "Text", 0 );
        assertFalse( dep2 ==  null );
        assertTrue( dep2 instanceof Depreciation );
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
    public void testGetDepreciationId() {
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );
        int ID = 0;

        dep1.setDepreciationId(ID);
        assertFalse( ID != dep1.getDepreciationId( ) );
    }

    @Test
    public void testSetDepreciationId() {
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );
        int ID = 0;

        dep1.setDepreciationId(ID);
        assertFalse( ID != dep1.getDepreciationId( ) );
    }

    @Test
    public void testGetUserReporter() {
        User test = new User( );
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );

        dep1.setUserReporter(test);

        assertEquals( test, dep1.getUserReporter( ) );
    }

    @Test
    public void testSetUserReporter() {
        User test = new User( );
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );

        dep1.setUserReporter(test);

        assertEquals( test, dep1.getUserReporter( ) );
    }

    @Test
    public void testGetUserOffender() {
        User test = new User( );
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );

        dep1.setUserOffender(test);

        assertEquals( test, dep1.getUserOffender( ) );
    }

    @Test
    public void testSetUserOffender() {
        User test = new User( );
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );

        dep1.setUserOffender(test);

        assertEquals( test, dep1.getUserOffender( ) );
    }

    @Test
    public void testGetMaterial() {
        Material test = new Material( );
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );

        dep1.setMaterial(test);

        assertEquals( test, dep1.getMaterial( ) );
    }

    @Test
    public void testSetMaterial() {
        Material test = new Material( );
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );

        dep1.setMaterial(test);

        assertEquals( test, dep1.getMaterial( ) );
    }

    @Test
    public void testGetReasonType() {
        ReasonType test = new ReasonType( );
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );

        dep1.setReasonType(test);

        assertEquals( test, dep1.getReasonType( ) );
    }

    @Test
    public void testSetReasonType() {
        ReasonType test = new ReasonType( );
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );

        dep1.setReasonType(test);

        assertEquals( test, dep1.getReasonType( ) );
    }

    @Test
    public void testGetQuantity() {
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );

        dep1.setQuantity( 20.0 );

        assertTrue( dep1.getQuantity( ) == 20.0 );
        assertFalse( dep1.getQuantity( ) != 20.0 );
    }

    @Test
    public void testSetQuantity() {
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );
        
        dep1.setQuantity( 20.0 );

        assertTrue( dep1.getQuantity( ) == 20.0 );
        assertFalse( dep1.getQuantity( ) != 20.0 );
    }

    @Test
    public void testGetDate() {
        Date test = new Date( );
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );

        dep1.setDate(test);

        assertEquals( test, dep1.getDate( ) );
    }

    @Test
    public void testSetDate() {
        Date test = new Date( );
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );

        dep1.setDate(test);

        assertEquals( test, dep1.getDate( ) );
    }

    @Test
    public void testGetNote() {
        String test = "test";
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );

        dep1.setNote(test);

        assertEquals( test, dep1.getNote( ) );
    }

    @Test
    public void testSetNote() {
        String test = "test";
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );

        dep1.setNote(test);

        assertEquals( test, dep1.getNote( ) );
    }

    @Test
    public void testGetIsDeleted() {
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );

        int test = (int) ( Math.random( ) * 10000 );

        dep1.setIsDeleted( test );

        assertTrue( dep1.getIsDeleted( ) == test );
        assertFalse( dep1.getIsDeleted( ) != test );
    }

    @Test
    public void testSetIsDeleted() {
        Depreciation dep1 = new Depreciation( new User( ), new User( ), new Material( ), 0.0f, new Date( ), 0 );

        int test = (int) ( Math.random( ) * 10000 );

        dep1.setIsDeleted( test );

        assertTrue( dep1.getIsDeleted( ) == test );
        assertFalse( dep1.getIsDeleted( ) != test );
    }

}