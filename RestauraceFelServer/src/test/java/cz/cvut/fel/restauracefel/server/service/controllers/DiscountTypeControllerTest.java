/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.DiscountTypeController;
import java.util.Iterator;
import cz.cvut.fel.restauracefel.hibernate.DiscountType;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomáš
 */
public class DiscountTypeControllerTest {

    private static DiscountTypeController dtcInst;

    public DiscountTypeControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        dtcInst = DiscountTypeController.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        DiscountType discountType = dtcInst.getDiscountTypeByName("testName");
        if(discountType != null){
            discountType.delete();
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateDiscountType() {
        boolean result;
        result = dtcInst.createDiscountType("testName");
        assertEquals(true, result);
        result = dtcInst.createDiscountType("testName");
        assertEquals(false, result);
        result = dtcInst.createDiscountType("");
        assertEquals(false, result);
    }

    @Test
    public void testGetDiscountTypeById() {
        DiscountType discountType = dtcInst.getDiscountTypeById(0);
        assertEquals(null, discountType);
    }

    @Test
    public void testGetDiscountTypeByName() {
        DiscountType discountType = dtcInst.getDiscountTypeByName("testName");
        if(discountType == null) fail();
    }

    @Test
    public void testGetAllDiscountTypes() {
        boolean found = false;
        DiscountType temp;
        List<DiscountType> result = dtcInst.getAllDiscountTypes();
        if(result == null || result.isEmpty()) fail();

        Iterator iter = result.iterator();
        while(iter.hasNext()){
            temp = (DiscountType) iter.next();
            if(temp.getName().equals("testName")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testGetDiscountTypeNames() {
        boolean found = false;
        String[] result = dtcInst.getDiscountTypeNames();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("testName")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testGetDiscountTypes() {
        boolean found = false;
        Object[][] result = dtcInst.getDiscountTypes();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][1].equals("testName")) found = true;
        }
        
        assertEquals(true, found);
    }

    @Test
    public void testDeleteDiscountType() {
        DiscountType discountType = dtcInst.getDiscountTypeByName("testName");
        if(discountType == null) fail("Nenalezen zaznam s name='testName'");
        dtcInst.deleteDiscountType(discountType.getDiscountTypeId());
        assertEquals(1, discountType.getIsDeleted());
        discountType.setIsDeleted(0);
    }

}