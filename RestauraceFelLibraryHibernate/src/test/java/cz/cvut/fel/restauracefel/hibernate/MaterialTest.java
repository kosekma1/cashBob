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
public class MaterialTest {

    public MaterialTest( ) {
        Material mat1 = new Material( );
        Material mat2 = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );

        assertFalse( mat1 ==  null );
        assertTrue( mat1 instanceof Material );
        assertFalse( mat2 ==  null );
        assertTrue( mat2 instanceof Material );
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
    public void testGetDensity( ) {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );

        double test = Math.random( ) * 743527;

        mat.setDensity( test );
        assertFalse( test != mat.getDensity( ) );
    }

    @Test
    public void testSetDensity() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );

        double test = Math.random( ) * 743527;

        mat.setDensity( test );
        assertFalse( test != mat.getDensity( ) );
    }

    @Test
    public void testGetEmptyPackageWeight() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );

        double test = Math.random( ) * 743527;

        mat.setEmptyPackageWeight(test);
        assertFalse( test != mat.getEmptyPackageWeight( ) );
    }

    @Test
    public void testSetEmptyPackageWeight() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );

        double test = Math.random( ) * 743527;

        mat.setEmptyPackageWeight(test);
        assertFalse( test != mat.getEmptyPackageWeight( ) );
    }

    @Test
    public void testGetPackageCapacity() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );

        double test = Math.random( ) * 743527;

        mat.setPackageCapacity(test);
        assertFalse( test != mat.getPackageCapacity( ) );
    }

    @Test
    public void testSetPackageCapacity() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );

        double test = Math.random( ) * 743527;

        mat.setPackageCapacity(test);
        assertFalse( test != mat.getPackageCapacity( ) );
    }

    @Test
    public void testGetMaterialId() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );

        int ID = 2752;
        mat.setMaterialId( ID );
        assertFalse( mat.getMaterialId( ) != ID );
    }

    @Test
    public void testSetMaterialId() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );

        int ID = 2752;
        mat.setMaterialId( ID );
        assertFalse( mat.getMaterialId( ) != ID );
    }

    @Test
    public void testGetUnitType() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );
        UnitType type = new UnitType( );

        mat.setUnitType(type);
        assertEquals(type, mat.getUnitType());
    }

    @Test
    public void testSetUnitType() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );
        UnitType type = new UnitType( );

        mat.setUnitType(type);
        assertEquals(type, mat.getUnitType());
    }

    @Test
    public void testGetMaterialType() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );
        MaterialType type = new MaterialType( );

        mat.setMaterialType(type);
        assertEquals( type, mat.getMaterialType( ) );
    }

    @Test
    public void testSetMaterialType() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );
        MaterialType type = new MaterialType( );

        mat.setMaterialType(type);
        assertEquals( type, mat.getMaterialType( ) );
    }

    @Test
    public void testGetName() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );
        String test = "Test";

        mat.setName(test);
        assertEquals( test, mat.getName( ) );
    }

    @Test
    public void testSetName() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );
        String test = "Test";

        mat.setName(test);
        assertEquals( test, mat.getName( ) );
    }

    @Test
    public void testGetCurrentQuantity( ) {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );

        double test = Math.random( ) * 743527;

        mat.setCurrentQuantity(test);
        assertFalse( test != mat.getCurrentQuantity( ) );
    }

    @Test
    public void testSetCurrentQuantity( ) {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );

        double test = Math.random( ) * 743527;

        mat.setCurrentQuantity(test);
        assertFalse( test != mat.getCurrentQuantity( ) );
    }

    @Test
    public void testGetBarcode() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );
        String test = "Test";

        mat.setBarcode( test );
        assertEquals( test, mat.getBarcode( ) );
    }

    @Test
    public void testSetBarcode() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );
        String test = "Test";

        mat.setBarcode( test );
        assertEquals( test, mat.getBarcode( ) );
    }

    @Test
    public void testGetMinimal() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );

        double test = Math.random( ) * 743527;

        mat.setMinimal(test);
        assertFalse( test != mat.getMinimal( ) );
    }

    @Test
    public void testSetMinimal() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );

        double test = Math.random( ) * 743527;

        mat.setMinimal(test);
        assertFalse( test != mat.getMinimal( ) );
    }

    @Test
    public void testGetIsDeleted() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );

        int ID = 2752;
        mat.setIsDeleted(ID);
        assertFalse( mat.getIsDeleted( ) != ID );
    }

    @Test
    public void testSetIsDeleted() {
        Material mat = new Material( 0, null, null, null, 0.0f, null, 0.0f, 0, 0.0f, 0.0f, 0.0f );

        int ID = 2752;
        mat.setIsDeleted(ID);
        assertFalse( mat.getIsDeleted( ) != ID );
    }

}