/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.DiscountTypeController;
import cz.cvut.fel.restauracefel.server.service.controllers.MenuItemTypeController;
import cz.cvut.fel.restauracefel.server.service.controllers.MenuItemController;
import cz.cvut.fel.restauracefel.server.service.controllers.DiscountController;
import java.util.Iterator;
import java.util.List;
import cz.cvut.fel.restauracefel.hibernate.Discount;
import cz.cvut.fel.restauracefel.hibernate.MenuItemType;
import org.junit.Test;
import cz.cvut.fel.restauracefel.hibernate.MenuItem;
import cz.cvut.fel.restauracefel.hibernate.DiscountType;
import org.junit.After;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 *
 * @author honza
 */
public class DiscountControllerTest {

    private static DiscountController dcInst;
    private static DiscountType discountType;
    private static MenuItemType menuItemType;
    private static MenuItem menuItem;

    public DiscountControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        dcInst = DiscountController.getInstance();

        DiscountTypeController dtc = DiscountTypeController.getInstance();
        dtc.createDiscountType("testName");
        discountType = dtc.getDiscountTypeByName("testName");

        MenuItemTypeController mitc = MenuItemTypeController.getInstance();
        mitc.createMenuItemType("testName");
        menuItemType = mitc.getMenuItemTypeByName("testName");

        MenuItemController mic = MenuItemController.getInstance();
        mic.createMenuItem("testName", 100.0, "1", 1, menuItemType.getMenuItemTypeId());
        menuItem = mic.getMenuItemByName("testName");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        Discount discount = dcInst.getDiscountByDiscountTypeAndMenuItem(discountType.getDiscountTypeId(), menuItem.getMenuItemId());

        if(discount != null) discount.delete();
        if(discountType != null) discountType.delete();
        if(menuItem != null) menuItem.delete();
        if(menuItemType != null) menuItemType.delete();
    }

    @Before
    public void tearUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestCreateDiscount() {
        boolean result;
        result = dcInst.createDiscount(discountType.getDiscountTypeId(), menuItem.getMenuItemId(), 2.0, 1.0);
        assertEquals(true, result);
        result = dcInst.createDiscount(0, menuItem.getMenuItemId(), 2.0, 1.0);
        assertEquals(false, result);
        result = dcInst.createDiscount(discountType.getDiscountTypeId(), 0, 2.0, 1.0);
        assertEquals(false, result);
    }

    @Test
    public void TestDeleteDiscount() {
        boolean result;
        Discount discount = dcInst.getDiscountByDiscountTypeAndMenuItem(discountType.getDiscountTypeId(), menuItem.getMenuItemId());
        result = dcInst.deleteDiscount(discount.getDiscountId());
        assertEquals(true, result);
        assertEquals(1, discount.getIsDeleted());
        discount.setIsDeleted(0);
    }

    @Test
    public void TestGetAllDiscounts() {
        boolean found = false;
        Discount temp;
        List<Discount> result = dcInst.getAllDiscounts();
        if(result == null || result.isEmpty()) fail();

        Iterator iter = result.iterator();
        while(iter.hasNext()){
            temp = (Discount) iter.next();
            if(temp.getDiscountType() == discountType && temp.getMenuItem() == menuItem) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetDiscounts() {
        boolean found = false;
        Object[][] result = dcInst.getDiscounts();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][1] == discountType && result[i][2] == menuItem) found = true;
        }
        
        assertEquals(true, found);
    }

}
