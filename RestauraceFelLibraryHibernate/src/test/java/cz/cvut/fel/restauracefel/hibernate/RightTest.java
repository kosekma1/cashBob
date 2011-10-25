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
public class RightTest {

    public RightTest() {
        Right prava1 = new Right();
        Right prava2 = new Right(null, 0);

        assertFalse( prava1 ==  null );
        assertTrue( prava1 instanceof Right );

        assertFalse( prava2 ==  null );
        assertTrue( prava2 instanceof Right );
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
    public void testGetRightId() {
        Right prava = new Right(null, 0);

        int test = 36757;

        prava.setRightId(test);
        assertFalse( test != prava.getRightId( ) );
    }

    @Test
    public void testSetRightId() {
        Right prava = new Right(null, 0);

        int test = 36757;

        prava.setRightId(test);
        assertFalse( test != prava.getRightId( ) );
    }

    @Test
    public void testGetName() {
        Right prava = new Right(null, 0);

        String test = "TEST";

        prava.setName(test);
        assertEquals(test, prava.getName( ) );
    }

    @Test
    public void testSetName() {
        Right prava = new Right(null, 0);

        String test = "TEST";

        prava.setName(test);
        assertEquals(test, prava.getName( ) );
    }

    @Test
    public void testGetIsDeleted() {
        Right prava = new Right(null, 0);

        int test = 36757;

        prava.setIsDeleted(test);
        assertFalse( test != prava.getIsDeleted( ) );
    }

    @Test
    public void testSetIsDeleted() {
        Right prava = new Right(null, 0);

        int test = 36757;

        prava.setIsDeleted(test);
        assertFalse( test != prava.getIsDeleted( ) );
    }

}