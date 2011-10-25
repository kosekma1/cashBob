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
public class IncomeTest {

    public IncomeTest() {
        Income inc1 = new Income( null, null, 0.0, 0.0, null, 0 );
        Income inc2 = new Income( null,null, 0.0, 0.0, null, null, 0 );

        assertFalse( inc1 ==  null );
        assertTrue( inc2 instanceof Income );

        assertFalse( inc2 ==  null );
        assertTrue( inc2 instanceof Income );
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
    public void testGetIncomeId() {
        Income inc = new Income( null, null, 0.0, 0.0, null, 0 );
        int test = (int) (Math.random() * 162718);

        inc.setIncomeId(test);
        assertFalse( inc.getIncomeId() != test );
    }

    @Test
    public void testSetIncomeId() {
        Income inc = new Income( null, null, 0.0, 0.0, null, 0 );
        int test = (int) (Math.random() * 162718);

        inc.setIncomeId(test);
        assertFalse( inc.getIncomeId() != test );
    }

    @Test
    public void testGetUser() {
        Income inc = new Income( null, null, 0.0, 0.0, null, 0 );

        User test = new User( );

        inc.setUser(test);
        assertEquals(test, inc.getUser());
    }

    @Test
    public void testSetUser() {
        Income inc = new Income( null, null, 0.0, 0.0, null, 0 );

        User test = new User( );

        inc.setUser(test);
        assertEquals(test, inc.getUser());
    }

    @Test
    public void testGetMaterial() {
        Income inc = new Income( null, null, 0.0, 0.0, null, 0 );

        Material test = new Material( );

        inc.setMaterial(test);
        assertEquals(test, inc.getMaterial());
    }

    @Test
    public void testSetMaterial() {
        Income inc = new Income( null, null, 0.0, 0.0, null, 0 );

        Material test = new Material( );

        inc.setMaterial(test);
        assertEquals(test, inc.getMaterial());
    }

    @Test
    public void testGetQuantity() {
        Income inc = new Income( null, null, 0.0, 0.0, null, 0 );
        double test = Math.random( ) * 162718;

        inc.setQuantity(test);
        assertFalse( inc.getQuantity() != test );
    }

    @Test
    public void testSetQuantity() {
        Income inc = new Income( null, null, 0.0, 0.0, null, 0 );
        double test = Math.random( ) * 162718;

        inc.setQuantity(test);
        assertFalse( inc.getQuantity() != test );
    }

    @Test
    public void testGetPrice() {
        Income inc = new Income( null, null, 0.0, 0.0, null, 0 );
        double test = Math.random( ) * 162718;

        inc.setPrice(test);
        assertFalse( inc.getPrice() != test );
    }

    @Test
    public void testSetPrice() {
        Income inc = new Income( null, null, 0.0, 0.0, null, 0 );
        double test = Math.random( ) * 162718;

        inc.setPrice(test);
        assertFalse( inc.getPrice() != test );
    }

    @Test
    public void testGetDate() {
        Income inc = new Income( null, null, 0.0, 0.0, null, 0 );

        Date test = new Date( 2000, 6, 10 );

        inc.setDate(test);
        assertEquals(test, inc.getDate());
    }

    @Test
    public void testSetDate() {
        Income inc = new Income( null, null, 0.0, 0.0, null, 0 );

        Date test = new Date( 2000, 6, 10 );

        inc.setDate(test);
        assertEquals(test, inc.getDate());
    }

    @Test
    public void testGetNote() {
        Income inc = new Income( null, null, 0.0, 0.0, null, 0 );

        String test = "note";

        inc.setNote(test);
        assertEquals(test, inc.getNote());
    }

    @Test
    public void testSetNote() {
        Income inc = new Income( null, null, 0.0, 0.0, null, 0 );

        String test = "note";

        inc.setNote(test);
        assertEquals(test, inc.getNote());
    }

    @Test
    public void testGetIsDeleted() {
        Income inc = new Income( null, null, 0.0, 0.0, null, 0 );
        int test = (int) (Math.random() * 162718);

        inc.setIsDeleted(test);
        assertFalse( inc.getIsDeleted() != test );
    }

    @Test
    public void testSetIsDeleted() {
        Income inc = new Income( null, null, 0.0, 0.0, null, 0 );
        int test = (int) (Math.random() * 162718);

        inc.setIsDeleted(test);
        assertFalse( inc.getIsDeleted() != test );
    }

}