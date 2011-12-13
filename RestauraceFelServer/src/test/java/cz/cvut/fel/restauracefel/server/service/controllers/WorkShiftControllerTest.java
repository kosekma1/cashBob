/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Workshift;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kosekm
 */
public class WorkShiftControllerTest {

    private static WorkShiftController instance;
    private static Date date = new Date();
    private static Integer idWorkShift = null;
    private static Integer idUser = 100;
    private static int idTypeWorkShift = 1;

    public WorkShiftControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        instance = WorkShiftController.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        boolean result = instance.delete(idWorkShift);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createNewWorkshift method, of class WorkShiftController.
     */
    @Test
    public void testCreateNewWorkshift() {
        boolean result = instance.createNewWorkshift(date, idTypeWorkShift);
        assertEquals(true, result);
    }

    /**
     * Test of getWorkshiftsFromTo method, of class WorkShiftController.
     */
    @Test
    public void testGetWorkshiftsFromTo() {
        Date dateFrom = date;
        Date dateTo = date;
        List resultList = instance.getWorkshiftsFromTo(dateFrom, dateTo);
        boolean result = resultList != null && !resultList.isEmpty();
        if (result) {
            Workshift ws = (Workshift) resultList.get(0);
            idWorkShift = ws.getIdWorkshift();
        }
        assertEquals(true, result);
    }

    /**
     * Test of getWorkshiftById method, of class WorkShiftController.
     */
    @Test
    public void testGetWorkshiftById() {
        Workshift result = instance.getWorkshiftById(idWorkShift);
        assertEquals(true, result != null);
    }

    /**
     * Test of updateWorkshiftLogin method, of class WorkShiftController.
     */
    @Test
    public void testUpdateWorkshiftLogin() {
        boolean result = instance.updateWorkshiftLogin(idWorkShift, idUser);
        assertEquals(true, result);
    }

    /**
     * Test of getWorkshiftByUserId method, of class WorkShiftController.
     */
    @Test
    public void testGetWorkshiftByUserId() {
        List resultList = instance.getWorkshiftByUserId(idUser);
        boolean result = resultList != null && !resultList.isEmpty();
        assertEquals(true, result);
    }

    /**
     * Test of getWorkshiftByTypeWorkshiftId method, of class WorkShiftController.
     */
    @Test
    public void testGetWorkshiftByTypeWorkshiftId() {
        List resultList = instance.getWorkshiftByTypeWorkshiftId(idTypeWorkShift);
        boolean result = resultList != null && !resultList.isEmpty();
        assertEquals(true, result);
    }

    /**
     * Test of getAllActiveWorkShifts method, of class WorkShiftController.
     */
    @Test
    public void testGetAllActiveWorkShifts() {
        List resultList = instance.getAllActiveWorkShifts(date);
        boolean result = resultList != null && !resultList.isEmpty();
        assertEquals(true, result);
    }

    /**
     * Test of updateWorkshiftOccupation method, of class WorkShiftController.
     */
    @Test
    public void testUpdateWorkshiftOccupation() {
        String message = "testMessage";
        boolean result = instance.updateWorkshiftOccupation(idWorkShift, message);
        assertEquals(true, result);
    }
}
