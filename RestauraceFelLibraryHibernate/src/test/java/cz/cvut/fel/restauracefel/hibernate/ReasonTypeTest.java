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
public class ReasonTypeTest {

    public ReasonTypeTest() {
        ReasonType type1 = new ReasonType();
        ReasonType type2 = new ReasonType(null, 0);
        ReasonType type3 = new ReasonType(null, null, 0);

        assertFalse( type1 ==  null );
        assertTrue( type1 instanceof ReasonType );

        assertFalse( type2 ==  null );
        assertTrue( type2 instanceof ReasonType );

        assertFalse( type3 ==  null );
        assertTrue( type3 instanceof ReasonType );
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
    public void testGetReasonTypeId() {
        ReasonType type = new ReasonType(null, null, 0);

        int test = 857756;

        type.setReasonTypeId(test);
        assertFalse( test!= type.getReasonTypeId( ) );
    }

    @Test
    public void testSetReasonTypeId( ) {
        ReasonType type = new ReasonType(null, null, 0);

        int test = 857756;

        type.setReasonTypeId(test);
        assertFalse( test!= type.getReasonTypeId( ) );
    }

    @Test
    public void testGetName() {
        ReasonType type = new ReasonType(null, null, 0);

        String test = "TEST";

        type.setName(test);
        assertEquals(test, type.getName( ) );
    }

    @Test
    public void testSetName() {
        ReasonType type = new ReasonType(null, null, 0);

        String test = "TEST";

        type.setName(test);
        assertEquals(test, type.getName( ) );
    }

    @Test
    public void testGetNote() {
        ReasonType type = new ReasonType(null, null, 0);

        String test = "TEST";

        type.setNote(test);
        assertEquals(test, type.getNote( ) );
    }

    @Test
    public void testSetNote() {
        ReasonType type = new ReasonType(null, null, 0);

        String test = "TEST";

        type.setNote(test);
        assertEquals(test, type.getNote( ) );
    }

    @Test
    public void testGetIsDeleted() {
        ReasonType type = new ReasonType(null, null, 0);

        int test = 857756;

        type.setIsDeleted(test);
        assertFalse( test!= type.getIsDeleted( ) );
    }

    @Test
    public void testSetIsDeleted() {
        ReasonType type = new ReasonType(null, null, 0);

        int test = 857756;

        type.setIsDeleted(test);
        assertFalse( test!= type.getIsDeleted( ) );
    }

}