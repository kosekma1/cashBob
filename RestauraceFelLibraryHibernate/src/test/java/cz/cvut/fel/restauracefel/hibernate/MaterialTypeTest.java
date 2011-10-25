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
public class MaterialTypeTest {

    public MaterialTypeTest() {
        MaterialType matType1 = new MaterialType( );
        MaterialType matType2 = new MaterialType(null, 0);
        MaterialType matType3 = new MaterialType(null, null, 0);

        assertFalse( matType1 ==  null );
        assertTrue( matType1 instanceof MaterialType );

        assertFalse( matType2 ==  null );
        assertTrue( matType2 instanceof MaterialType );

        assertFalse( matType3 ==  null );
        assertTrue( matType3 instanceof MaterialType );
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
    public void testGetMaterialTypeId() {
        MaterialType matType = new MaterialType(null, null, 0);

        int ID = 75432;
        matType.setMaterialTypeId(ID);
        assertFalse( ID != matType.getMaterialTypeId( ) );
    }

    @Test
    public void testSetMaterialTypeId() {
        MaterialType matType = new MaterialType(null, null, 0);

        int ID = 75432;
        matType.setMaterialTypeId(ID);
        assertFalse( ID != matType.getMaterialTypeId( ) );
    }

    @Test
    public void testGetName() {
        MaterialType matType = new MaterialType(null, null, 0);

        String test = "TEST";

        matType.setName(test);
        assertEquals(test, matType.getName( ) );
    }

    @Test
    public void testSetName() {
        MaterialType matType = new MaterialType(null, null, 0);

        String test = "TEST";

        matType.setName(test);
        assertEquals(test, matType.getName( ) );
    }

    @Test
    public void testGetNote() {
        MaterialType matType = new MaterialType(null, null, 0);

        String test = "TEST";

        matType.setNote(test);
        assertEquals(test, matType.getNote( ) );
    }

    @Test
    public void testSetNote() {
        MaterialType matType = new MaterialType(null, null, 0);

        String test = "TEST";

        matType.setNote(test);
        assertEquals(test, matType.getNote( ) );
    }

    @Test
    public void testGetIsDeleted() {
        MaterialType matType = new MaterialType(null, null, 0);

        int test = 52323;
        matType.setIsDeleted(test);
        assertFalse( matType.getIsDeleted() != test );
    }

    @Test
    public void testSetIsDeleted() {
        MaterialType matType = new MaterialType(null, null, 0);

        int test = 52323;
        matType.setIsDeleted(test);
        assertFalse( matType.getIsDeleted() != test );
    }

}