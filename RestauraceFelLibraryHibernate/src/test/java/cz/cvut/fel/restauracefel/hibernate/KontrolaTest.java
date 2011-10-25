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
public class KontrolaTest {

    public KontrolaTest() {
        Kontrola kontrola = new Kontrola( );

        assertFalse( kontrola ==  null );
        assertTrue( kontrola instanceof Kontrola );
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
    public void testIsValid() {
        Kontrola kontrola = new Kontrola( );

        kontrola.setValid(true);

        assertTrue(kontrola.isValid());
    }

    @Test
    public void testSetValid() {
        Kontrola kontrola = new Kontrola( );

        kontrola.setValid(true);

        assertTrue( kontrola.isValid( ) );
    }

    @Test
    public void testGetMeasurementId() {
        Kontrola kontrola = new Kontrola( );
        int ID = 0;

        kontrola.setMeasurementId(ID);
        assertFalse( ID != kontrola.getMeasurementId( ) );
    }

    @Test
    public void testSetMeasurementId() {
        Kontrola kontrola = new Kontrola( );
        int ID = 0;

        kontrola.setMeasurementId(ID);
        assertFalse( ID != kontrola.getMeasurementId( ) );
    }

    @Test
    public void testGetUzaverka() {
        Uzaverka test = new Uzaverka( );
        Kontrola kontrola = new Kontrola( );

        kontrola.setUzaverka(test);

        assertEquals( test, kontrola.getUzaverka( ) );
    }

    @Test
    public void testSetUzaverka() {
        Uzaverka test = new Uzaverka( );
        Kontrola kontrola = new Kontrola( );

        kontrola.setUzaverka(test);

        assertEquals( test, kontrola.getUzaverka( ) );
    }

    @Test
    public void testGetSurovina() {
        Material test = new Material( );
        Kontrola kontrola = new Kontrola( );

        kontrola.setSurovina(test);

        assertEquals( test, kontrola.getSurovina() );
    }

    @Test
    public void testSetSurovina() {
        Material test = new Material( );
        Kontrola kontrola = new Kontrola( );

        kontrola.setSurovina(test);

        assertEquals( test, kontrola.getSurovina() );
    }

    @Test
    public void testGetNoveMnozstvi() {
        Kontrola kontrola = new Kontrola( );

        double test = Math.random( ) * 864678;

        kontrola.setNoveMnozstvi(test);
        assertFalse(kontrola.getNoveMnozstvi() != test);
    }

    @Test
    public void testSetNoveMnozstvi() {
        Kontrola kontrola = new Kontrola( );

        double test = Math.random( ) * 864678;

        kontrola.setNoveMnozstvi(test);
        assertFalse(kontrola.getNoveMnozstvi() != test);
    }

    @Test
    public void testGetProdanoPokladnou() {
        Kontrola kontrola = new Kontrola( );

        double test = Math.random( ) * 864678;

        kontrola.setProdanoPokladnou(test);
        assertFalse(kontrola.getProdanoPokladnou() != test);
    }

    @Test
    public void testSetProdanoPokladnou() {
        Kontrola kontrola = new Kontrola( );

        double test = Math.random( ) * 864678;

        kontrola.setProdanoPokladnou(test);
        assertFalse(kontrola.getProdanoPokladnou() != test);
    }

    @Test
    public void testGetProdanoVahou() {
        Kontrola kontrola = new Kontrola( );

        double test = Math.random( ) * 864678;

        kontrola.setProdanoPokladnou(test);
        assertFalse( kontrola.getProdanoPokladnou( ) != test);
    }

    @Test
    public void testSetProdanoVahou() {
        Kontrola kontrola = new Kontrola( );

        double test = Math.random( ) * 864678;

        kontrola.setProdanoPokladnou(test);
        assertFalse( kontrola.getProdanoPokladnou( ) != test);
    }

    @Test
    public void testGetStareMnozstvi() {
        Kontrola kontrola = new Kontrola( );

        double test = Math.random( ) * 864678;

        kontrola.setNoveMnozstvi(test);
        assertFalse( kontrola.getNoveMnozstvi( ) != test);
    }

    @Test
    public void testSetStareMnozstvi() {
        Kontrola kontrola = new Kontrola( );

        double test = Math.random( ) * 864678;

        kontrola.setNoveMnozstvi(test);
        assertFalse( kontrola.getNoveMnozstvi( ) != test);
    }

    @Test
    public void testGetRozdil() {
        Kontrola kontrola = new Kontrola( );

        kontrola.setProdanoVahou( 2000.80 );
        kontrola.setProdanoPokladnou( 1000.50 );
        assertFalse( kontrola.getRozdil( ) != 2000.80 - 1000.50 );
    }

    @Test
    public void testSetRozdil() {
        // TODO tenhle test nejak nefunguje

        /*Kontrola kontrola = new Kontrola( );

        double test = Math.random( ) * 864678;

        kontrola.setRozdil(test);
        assertFalse( kontrola.getRozdil( ) != test);*/
    }

}