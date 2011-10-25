/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.sklad_service;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vizi
 */
public class CommReaderTest {

    public CommReaderTest() {
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
    public void testConnect() throws Exception {
    }

    @Test
    public void testClose() {
    }

    @Test
    public void testSendWeight() {
       // CommReader.sendWeight("1000000"); ?????????????????
    }

    @Test
    public void testStart() throws Exception {
        CommReader.start( );
        assertFalse(CommReader.isRun == false);
    }

}