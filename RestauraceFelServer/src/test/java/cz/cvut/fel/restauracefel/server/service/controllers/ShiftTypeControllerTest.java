/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Typeworkshift;
import java.util.Calendar;
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
public class ShiftTypeControllerTest {
    
    private static ShiftTypeController instance;
    
    public ShiftTypeControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        instance = ShiftTypeController.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        Typeworkshift shiftType = instance.findTypeworkshiftByName("testName");       
        if(shiftType!=null) shiftType.delete();                    
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

 
     /**
     * Test of createWorkshiftType method, of class ShiftTypeController.
     */
    @Test
    public void testCreateWorkshiftType() {        
        Typeworkshift typeWorkshift = new Typeworkshift();
        typeWorkshift.setName("testName");
        Calendar cal = Calendar.getInstance();        
        cal.set(1970, 1, 1, 0, 0);
        typeWorkshift.setFromTime(cal.getTime());
        cal.set(1970, 1, 1, 2, 0);
        typeWorkshift.setToTime(cal.getTime());
        typeWorkshift.setIdWorkshiftRole(1);
        typeWorkshift.setIsDeleted(0);
        typeWorkshift.setStatus(1);        
        boolean result = instance.createWorkshiftType(typeWorkshift);
        assertEquals(true, result);        
    }
    
    /**
     * Test of findTypeworkshiftByName method, of class ShiftTypeController.
     */
    @Test
    public void testFindTypeworkshiftByName() {        
        String name = "testName";                
        Typeworkshift result = instance.findTypeworkshiftByName(name);
        assertEquals(true, result!=null);        
    }
    
    
    /**
     * Test of getTypeWorkShifts method, of class ShiftTypeController.
     */
    @Test
    public void testGetTypeWorkShifts() {        
        List resultList = instance.getTypeWorkShifts();
        boolean result = resultList!=null && !resultList.isEmpty();
        assertEquals(true, result);        
    }
     

    /**
     * Test of getTypeWorkShiftById method, of class ShiftTypeController.
     */
    @Test
    public void testGetTypeWorkShiftById() {        
        Typeworkshift expResult = instance.findTypeworkshiftByName("testName");        
        Typeworkshift result = instance.getTypeWorkShiftById(expResult.getIdTypeWorkshift());
        assertEquals(expResult, result);        
    }
}
