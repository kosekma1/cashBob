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
public class UzaverkaTest {

    public UzaverkaTest() {
        Uzaverka uzaverka = new Uzaverka();

        assertFalse( uzaverka ==  null );
        assertTrue( uzaverka instanceof Uzaverka );
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
    public void testGetId() {
        Uzaverka uzaverka = new Uzaverka();

        int test = 7587326;

        uzaverka.setId( test );
        assertFalse( test != uzaverka.getId( ) );
    }

    @Test
    public void testSetId() {
        Uzaverka uzaverka = new Uzaverka();

        int test = 7587326;

        uzaverka.setId( test );
        assertFalse( test != uzaverka.getId( ) );
    }

    @Test
    public void testGetDate() {
        Uzaverka uzaverka = new Uzaverka();

        Date date = new Date( );
        uzaverka.setDate(date);

        assertEquals(date, uzaverka.getDate( ) );
    }

    @Test
    public void testSetDate() {
        Uzaverka uzaverka = new Uzaverka();

        Date date = new Date( );
        uzaverka.setDate(date);

        assertEquals(date, uzaverka.getDate( ) );
    }

    @Test
    public void testGetUser() {
        Uzaverka uzaverka = new Uzaverka();

        User user = new User( );
        uzaverka.setUser(user);

        assertEquals(user, uzaverka.getUser() );
    }

    @Test
    public void testSetUser() {
        Uzaverka uzaverka = new Uzaverka();

        User user = new User( );
        uzaverka.setUser(user);

        assertEquals(user, uzaverka.getUser() );
    }

}