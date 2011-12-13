/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Workshift;
import java.util.Date;
import cz.cvut.fel.restauracefel.hibernate.Attendance;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin
 */
public class AttendanceControllerTest {
    
    private static AttendanceController instance;    
    private static Integer attendanceId = null;
    private static int userId = 1;
    private static Integer workShiftId; 
    private static Date date;
    
    public AttendanceControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        instance = AttendanceController.getInstance();
        date = new Date();
        int idTypeWorkShift = 1;
        WorkShiftController.getInstance().createNewWorkshift(date, idTypeWorkShift);
        List list = WorkShiftController.getInstance().getWorkshiftsFromTo(date, date);
        if(list!=null && !list.isEmpty() && list.size()==1) {
            Workshift ws = (Workshift)list.get(0);
            workShiftId = ws.getIdWorkshift();
        }        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        List list = WorkShiftController.getInstance().getWorkshiftsFromTo(date, date);
        for(Object o : list) {
            Workshift ws = (Workshift)o;
            ws.delete();
        }
        instance.deleteById(attendanceId);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        if(attendanceId!=null) {
            //instance.deleteById(attendanceId);
        }            
    }
    
    /**
     * Test of createNewAttendance method, of class AttendanceController.
     */
    @Test
    public void testCreateNewAttendance() {              
        boolean result = instance.createNewAttendance(userId, workShiftId);        
        assertEquals(true, result);
    }

    /**
     * Test of findByWorkShiftId method, of class AttendanceController.
     */
    @Test
    public void testFindByWorkShiftId() {        
        List resultList = instance.findByWorkShiftId(workShiftId);
        boolean result = resultList != null && !resultList.isEmpty();        
        if (result) {
          Attendance att = (Attendance)resultList.get(0);    
          attendanceId = att.getIdAttendance();
        }        
        assertEquals(true, result);        
    }

    /**
     * Test of findById method, of class AttendanceController.
     */
    @Test
    public void testFindById() {        
        Attendance result = instance.findById(attendanceId);
        assertEquals(true, result!=null);        
    }
    
    /**
     * Test of findByWorkShiftAndUser method, of class AttendanceController.
     */
    @Test
    public void testFindByWorkShiftAndUser() {                        
        Attendance result = instance.findByWorkShiftAndUser(workShiftId, userId);
        assertEquals(true, result!=null);                
    }
    
    /**
     * Test of deleteById method, of class AttendanceController.
     */
    @Test
    public void testDeleteById() {                        
        boolean result = instance.deleteById(attendanceId);        
        assertEquals(true, result);                
    }
}
