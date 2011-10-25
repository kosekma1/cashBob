/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.UserDiscountTypeController;
import cz.cvut.fel.restauracefel.server.service.controllers.DiscountTypeController;
import cz.cvut.fel.restauracefel.server.service.controllers.UserController;
import java.util.Iterator;
import java.util.List;
import cz.cvut.fel.restauracefel.hibernate.DiscountType;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.hibernate.UserDiscountType;
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
public class UserDiscountTypeControllerTest {

    private static UserDiscountTypeController udtcInst;
    private static User user;
    private static DiscountType discountType;

    public UserDiscountTypeControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        udtcInst = UserDiscountTypeController.getInstance();

        UserController uc = UserController.getInstance();
        uc.createUser("testUser", "testSurname", "testUsername");
        user = uc.getUserByUsername("testUsername");

        DiscountTypeController dtc = DiscountTypeController.getInstance();
        dtc.createDiscountType("testName");
        discountType = dtc.getDiscountTypeByName("testName");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        UserDiscountType userDiscountType = udtcInst.getUserDiscountTypeByUserAndDiscountType(user.getUserId(), discountType.getDiscountTypeId());
        if(userDiscountType != null) userDiscountType.delete();
        if(user != null) user.delete();
        if(discountType != null) discountType.delete();
    }

    @Before
    public void tearUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestCreateUserDiscountType() {
        boolean result;
        result = udtcInst.createUserDiscountType(user.getUserId(), discountType.getDiscountTypeId());
        assertEquals(true, result);
        result = udtcInst.createUserDiscountType(user.getUserId(), discountType.getDiscountTypeId());
        assertEquals(false, result);
        result = udtcInst.createUserDiscountType(0, discountType.getDiscountTypeId());
        assertEquals(false, result);
        result = udtcInst.createUserDiscountType(user.getUserId(), 0);
        assertEquals(false, result);
    }

    @Test
    public void TestGetUserDiscountTypesByUserId() {
        boolean found = false;
        UserDiscountType temp;
        List<UserDiscountType> result = udtcInst.getUserDiscountTypesByUserId(user.getUserId());
        if(result == null || result.isEmpty()) fail();

        Iterator iter = result.iterator();
        while(iter.hasNext()){
            temp = (UserDiscountType) iter.next();
            if(temp.getUser() == user && temp.getDiscountType() == discountType) found = true;
        }

        assertEquals(true, found);
    }

}
