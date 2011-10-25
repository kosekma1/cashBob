/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.DiscountTypeController;
import cz.cvut.fel.restauracefel.server.service.controllers.RoleDiscountTypeController;
import java.util.Iterator;
import java.util.List;
import cz.cvut.fel.restauracefel.hibernate.RoleDiscountType;
import cz.cvut.fel.restauracefel.hibernate.DiscountType;
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
public class RoleDiscountTypeControllerTest {

    private static RoleDiscountTypeController rdtcInst;
    private static DiscountType discountType;

    public RoleDiscountTypeControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        rdtcInst = RoleDiscountTypeController.getInstance();

        DiscountTypeController dtc = DiscountTypeController.getInstance();
        dtc.createDiscountType("testName");
        discountType = dtc.getDiscountTypeByName("testName");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        RoleDiscountType roleDiscountType = rdtcInst.getRoleDiscountTypeByRoleAndDiscountType(1, discountType.getDiscountTypeId());
        if(roleDiscountType != null) roleDiscountType.delete();
        if(discountType != null) discountType.delete();
    }

    @Before
    public void tearUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestCreateRoleDiscountType() {
        boolean result;
        result = rdtcInst.createRoleDiscountType(1, discountType.getDiscountTypeId());
        assertEquals(true, result);
        result = rdtcInst.createRoleDiscountType(1, discountType.getDiscountTypeId());
        assertEquals(false, result);
        result = rdtcInst.createRoleDiscountType(0, discountType.getDiscountTypeId());
        assertEquals(false, result);
        result = rdtcInst.createRoleDiscountType(1, 0);
        assertEquals(false, result);
    }

    @Test
    public void TestGetDiscountTypeNamesByRole() {
        boolean found = false;
        String[] result = rdtcInst.getDiscountTypeNamesByRole(1);
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("testName")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetRoleDiscountTypesByRoleId() {
        boolean found = false;
        RoleDiscountType temp;
        List<RoleDiscountType> result = rdtcInst.getRoleDiscountTypesByRoleId(1);
        if(result == null || result.isEmpty()) fail();

        Iterator iter = result.iterator();
        while(iter.hasNext()){
            temp = (RoleDiscountType) iter.next();
            if(temp.getDiscountType() == discountType && temp.getRole().getRoleId() == 1) found = true;
        }

        assertEquals(true, found);
    }

}
