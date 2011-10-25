
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.server.service.controllers;

//~--- non-JDK imports --------------------------------------------------------

import cz.cvut.fel.restauracefel.server.service.controllers.AccountCategoryController;
import cz.cvut.fel.restauracefel.hibernate.AccountCategory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

//~--- JDK imports ------------------------------------------------------------

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author honza
 */
public class AccountCategoryControllerTest {
    private static AccountCategoryController accInst;

    public AccountCategoryControllerTest() {}

    @BeforeClass
    public static void setUpClass() throws Exception {
        accInst = AccountCategoryController.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        AccountCategory accountCategory = accInst.getAccountCategoryByName("testName");

        if (accountCategory != null) {
            accountCategory.delete();
        }
    }

    @Before
    public void tearUp() {}

    @After
    public void tearDown() {}

    @Test
    public void testCreateAccountCategory() {
        boolean result;

        result = accInst.createAccountCategory("", "");
        assertEquals(false, result);
        result = accInst.createAccountCategory("testName", "testNote");
        assertEquals(true, result);
        result = accInst.createAccountCategory("testName", "testNote2");
        assertEquals(false, result);
    }

    @Test
    public void testGetAccountCategoryById() {
        AccountCategory accountCategory = accInst.getAccountCategoryById(0);

        assertEquals(null, accountCategory);
    }

    @Test
    public void testGetAccountCategoryByName() {
        AccountCategory accountCategory = accInst.getAccountCategoryByName("testName");

        if (accountCategory == null) {
            fail();
        }
    }

    @Test
    public void testGetAllAccountCategories() {
        boolean               found = false;
        AccountCategory       temp;
        List<AccountCategory> result = accInst.getAllAccountCategories();

        if ((result == null) || result.isEmpty()) {
            fail();
        }

        Iterator iter = result.iterator();

        while (iter.hasNext()) {
            temp = (AccountCategory) iter.next();

            if (temp.getName().equals("testName") && temp.getNote().equals("testNote")) {
                found = true;
            }
        }

        assertEquals(true, found);
    }

    @Test
    public void testGetAccountCategoryNames() {
        boolean  found  = false;
        String[] result = accInst.getAccountCategoryNames();

        if ((result == null) || (result.length < 1)) {
            fail();
        }

        for (int i = 0; i < result.length; i++) {
            if (result[i].equals("testName")) {
                found = true;
            }
        }

        assertEquals(true, found);
    }

    @Test
    public void testGetAccountCategories() {
        boolean    found  = false;
        Object[][] result = accInst.getAccountCategories();

        if ((result == null) || (result.length < 1)) {
            fail();
        }

        for (int i = 0; i < result.length; i++) {
            if (result[i][1].equals("testName") && result[i][2].equals("testNote")) {
                found = true;
            }
        }

        assertEquals(true, found);
    }

    @Test
    public void testDeleteAccountCategory() {
        AccountCategory accountCategory = accInst.getAccountCategoryByName("testName");

        if (accountCategory == null) {
            fail("Nenalezen zaznam s name='testName'");
        }

        accInst.deleteAccountCategory(accountCategory.getAccountCategoryId());
        assertEquals(1, accountCategory.getIsDeleted());
        accountCategory.setIsDeleted(0);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
