/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.UnitTypeController;
import java.util.Iterator;
import java.util.List;
import cz.cvut.fel.restauracefel.hibernate.UnitType;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 *
 * @author honza
 */
public class UnitTypeControllerTest {

    private static UnitTypeController utcInst;

    public UnitTypeControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        utcInst = UnitTypeController.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        UnitType unitType = utcInst.getUnitTypeByName("testName");
        unitType.delete();
    }

    @Before
    public void tearUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestCreateUnitType() {
        boolean result;
        result = utcInst.createUnitType("testName", "tn", 0);
        assertEquals(true, result);
        result = utcInst.createUnitType("testName", "tn", 0);
        assertEquals(false, result);
        result = utcInst.createUnitType(null, null, 0);
        assertEquals(false, result);
        result = utcInst.createUnitType("", "", 0);
        assertEquals(false, result);
    }

    @Test
    public void TestDeleteUnitType() {
        boolean result;
        UnitType unitType = utcInst.getUnitTypeByName("testName");
        result = utcInst.deleteUnitType(unitType.getUnitTypeId());
        assertEquals(true, result);
        assertEquals(1, unitType.getIsDeleted());
        unitType.setIsDeleted(0);
    }

    @Test
    public void TestUpdateUnitType() {
        boolean result;
        UnitType unitType = utcInst.getUnitTypeByName("testName");
        result = utcInst.updateUnitType(unitType.getUnitTypeId(), "testNameEdit", "tnEdit", 1);
        assertEquals(true, result);
        assertEquals("testNameEdit", unitType.getName());
        assertEquals("tnEdit", unitType.getAbbreviation());
        assertEquals((Integer) 1, unitType.getTypeId());
        result = utcInst.updateUnitType(unitType.getUnitTypeId(), "testName", "tn", 0);
        assertEquals(true, result);
        result = utcInst.updateUnitType(unitType.getUnitTypeId(), null, null, 0);
        assertEquals(false, result);
        result = utcInst.updateUnitType(unitType.getUnitTypeId(), "", "", 0);
        assertEquals(false, result);
    }

    @Test
    public void TestIsExistedUnitType() {
        boolean result;
        result = utcInst.isExistedUnitType("testName", "tn");
        assertEquals(true, result);
        result = utcInst.isExistedUnitType("testName", null);
        assertEquals(true, result);
        result = utcInst.isExistedUnitType(null, "tn");
        assertEquals(true, result);
        result = utcInst.isExistedUnitType(null, null);
        assertEquals(false, result);
    }

    @Test
    public void TestGetAllUnitTypes() {
        boolean found = false;
        UnitType temp;
        List<UnitType> result = utcInst.getAllUnitTypes();
        if(result == null || result.isEmpty()) fail();

        Iterator iter = result.iterator();
        while(iter.hasNext()){
            temp = (UnitType) iter.next();
            if(temp.getName().equals("testName") && temp.getAbbreviation().equals("tn")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetUnitTypeAbbrs() {
        boolean found = false;
        String[] result = utcInst.getUnitTypeAbbrs();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("tn")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetUnitTypeNames() {
        boolean found = false;
        String[] result = utcInst.getUnitTypeNames();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("testName")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetUnitTypeAbbrsByTypeId() {
        boolean found = false;
        String[] result = utcInst.getUnitTypeAbbrsByTypeId(0);
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("tn")) found = true;
        }

        assertEquals(true, found);
    }
    
    @Test
    public void TestGetUnitTypeNamesByTypeId() {
        boolean found = false;
        String[] result = utcInst.getUnitTypeNamesByTypeId(0);
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("testName")) found = true;
        }

        assertEquals(true, found);
    }

}
