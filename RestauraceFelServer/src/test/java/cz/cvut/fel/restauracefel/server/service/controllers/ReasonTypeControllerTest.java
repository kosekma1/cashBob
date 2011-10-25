/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.ReasonTypeController;
import cz.cvut.fel.restauracefel.hibernate.ReasonType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author honza
 */
public class ReasonTypeControllerTest {

    private static ReasonTypeController rtcInst;

    public ReasonTypeControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        rtcInst = ReasonTypeController.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        ReasonType reasonType = rtcInst.getReasonTypeByName("testName");
        reasonType.delete();
    }

    @Before
    public void tearUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestCreateReasonType() {
        boolean result;
        result = rtcInst.createReasonType("testName", "testNote");
        assertEquals(true, result);
        result = rtcInst.createReasonType("testName", "testNote2");
        assertEquals(false, result);
    }

    @Test
    public void TestUpdateReasonType() {
        boolean result;
        ReasonType reasonType = rtcInst.getReasonTypeByName("testName");
        result = rtcInst.updateReasonType(reasonType.getReasonTypeId(), "testNameEdit", "testNoteEdit");
        assertEquals(true, result);
        assertEquals("testNameEdit", reasonType.getName());
        assertEquals("testNoteEdit", reasonType.getNote());
        result = rtcInst.updateReasonType(reasonType.getReasonTypeId(), "testName", "testNote");
        assertEquals(true, result);
    }

    @Test
    public void TestDeleteReasonType() {
        boolean result;
        ReasonType reasonType = rtcInst.getReasonTypeByName("testName");
        result = rtcInst.deleteReasonType(reasonType.getReasonTypeId());
        assertEquals(true, result);
        assertEquals(1, reasonType.getIsDeleted());
        reasonType.setIsDeleted(0);
    }

    @Test
    public void TestGetReasonTypeByName() {
        ReasonType reasonType = rtcInst.getReasonTypeByName("testName");
        if(reasonType == null) fail();
    }

    @Test
    public void TestGetReasonTypes() {
        boolean found = false;
        Object[][] result = rtcInst.getReasonTypes();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][1].equals("testName") && result[i][2].equals("testNote")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetReasonTypeNames() {
        boolean found = false;
        String[] result = rtcInst.getReasonTypeNames();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("testName")) found = true;
        }

        assertEquals(true, found);
    }

}
