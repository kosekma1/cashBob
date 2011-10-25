/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.OrderMenuItemController;
import cz.cvut.fel.restauracefel.server.service.controllers.OrderController;
import cz.cvut.fel.restauracefel.server.service.controllers.UserController;
import cz.cvut.fel.restauracefel.server.service.controllers.AccountController;
import java.util.Iterator;
import java.util.Date;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.hibernate.Account;
import cz.cvut.fel.restauracefel.hibernate.Order;
import cz.cvut.fel.restauracefel.hibernate.OrderMenuItem;
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
public class OrderMenuItemControllerTest {

    private static OrderMenuItemController omicInst;
    private static User user;
    private static Account account;
    private static Order order;
    private static Date date;

    public OrderMenuItemControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        omicInst = OrderMenuItemController.getInstance();
        date = new Date();

        UserController uc = UserController.getInstance();
        uc.createUser("testName", "testSurname", "testUsername");
        user = uc.getUserByUsername("testUsername");

        AccountController ac = AccountController.getInstance();
        ac.createAccount("testName", 1, 0, 0, 0, 0, "testNote");
        account = ac.getAccountByName("testName");

        OrderController oc = OrderController.getInstance();
        oc.createOrder(0, date, account.getAccountId(), user.getUserId());
        List<Order> result = oc.getAllOrders();
        if(result != null && !result.isEmpty()){
            Iterator iter = result.iterator();
            while(iter.hasNext()){
                order = (Order) iter.next();
                if(order.getTime().equals(date) && order.getUser() == user) break;
            }
        }
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        OrderMenuItem orderMenuItem = null;

        List<OrderMenuItem> result = omicInst.getAllOrderMenuItems();
        if(result != null && !result.isEmpty()){
            Iterator iter = result.iterator();
            while(iter.hasNext()){
                orderMenuItem = (OrderMenuItem) iter.next();
                if(orderMenuItem.getOrder() == order && orderMenuItem.getOrderMenuItemId() == 1) break;
            }
        }

        if(orderMenuItem != null) orderMenuItem.delete();
        if(order != null) order.delete();
        if(user != null) user.delete();
        if(account != null) account.delete();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateOrderMenuItem() {
        boolean result;
        result = omicInst.createOrderMenuItem(1, order.getOrderId());
        assertEquals(true, result);
        result = omicInst.createOrderMenuItem(0, order.getOrderId());
        assertEquals(false, result);
        result = omicInst.createOrderMenuItem(1, 0);
        assertEquals(false, result);
    }

    @Test
    public void testGetAllOrderMenuItems() {
        boolean found = false;
        OrderMenuItem temp;
        List<OrderMenuItem> result = omicInst.getAllOrderMenuItems();
        if(result == null || result.isEmpty()) fail();

        Iterator iter = result.iterator();
        while(iter.hasNext()){
            temp = (OrderMenuItem) iter.next();
            if(temp.getOrder() == order) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testGetOrderMenuItemNames() {
        String[] result = omicInst.getOrderMenuItemNames();
        if(result == null || result.length < 1) fail();
    }

    @Test
    public void testGetOrderMenuItems() {
        boolean found = false;
        Object[][] result = omicInst.getOrderMenuItems();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][2] == account.getAccountId() && result[i][3].equals(date)) found = true;
        }

        assertEquals(true, found);
    }

}