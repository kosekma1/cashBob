/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.OrderController;
import cz.cvut.fel.restauracefel.server.service.controllers.UserController;
import cz.cvut.fel.restauracefel.server.service.controllers.AccountController;
import java.util.Iterator;
import cz.cvut.fel.restauracefel.hibernate.Account;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.hibernate.Order;
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
 * @author Tomáš
 */
public class OrderControllerTest {

    private static OrderController ocInst;
    private static User user;
    private static Account account;
    private static Date date;

    public OrderControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        ocInst = OrderController.getInstance();
        date = new Date();

        UserController uc = UserController.getInstance();
        uc.createUser("testName", "testSurname", "testUsername");
        user = uc.getUserByUsername("testUsername");

        AccountController ac = AccountController.getInstance();
        ac.createAccount("testName", 1, 0, 0, 0, 0, "testNote");
        account = ac.getAccountByName("testName");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        Order order = null;
        
        List<Order> result = ocInst.getAllOrders();
        if(result != null && !result.isEmpty()){
            Iterator iter = result.iterator();
            while(iter.hasNext()){
                order = (Order) iter.next();
                if(order.getTime().equals(date) && order.getUser() == user) break;
            }
        }

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
    public void testCreateOrder() {
        boolean result;
        result = ocInst.createOrder(0, date, account.getAccountId(), user.getUserId());
        assertEquals(true, result);
        result = ocInst.createOrder(0, date, account.getAccountId(), 0);
        assertEquals(false, result);
        result = ocInst.createOrder(0, date, 0, user.getUserId());
        assertEquals(false, result);
    }

    @Test
    public void testGetAllOrders() {
        boolean found = false;
        Order temp;
        List<Order> result = ocInst.getAllOrders();
        if(result == null || result.isEmpty()) fail();

        Iterator iter = result.iterator();
        while(iter.hasNext()){
            temp = (Order) iter.next();
            if(temp.getAccount() == account && temp.getIsPaid() == 0 && temp.getTime() == date) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testGetOrderNames() {
        String[] result = ocInst.getOrderNames();
        if(result == null || result.length < 1) fail();
    }

    @Test
    public void testGetOrdersByAccount() {
        boolean found = false;
        Object[][] result = ocInst.getOrdersByAccount(account.getAccountId());
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][2].equals(date)) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testGetOrders() {
        boolean found = false;
        Object[][] result = ocInst.getOrders();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][1] == account.getAccountId() && result[i][2].equals(date)) found = true;
        }

        assertEquals(true, found);
    }

}