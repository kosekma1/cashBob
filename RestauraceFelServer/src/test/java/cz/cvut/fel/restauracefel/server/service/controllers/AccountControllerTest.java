package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.AccountController;
import java.util.Iterator;
import cz.cvut.fel.restauracefel.hibernate.Account;
import cz.cvut.fel.restauracefel.hibernate.AccountStatusType;
import cz.cvut.fel.restauracefel.hibernate.DiscountType;
import cz.cvut.fel.restauracefel.hibernate.Table;
import cz.cvut.fel.restauracefel.hibernate.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jambojak
 */
public class AccountControllerTest {

    private static AccountController acInst;

    public AccountControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        acInst = AccountController.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        Account account = acInst.getAccountByName("testName");
        if(account != null) account.delete();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateAccount() {
        boolean result;
        result = acInst.createAccount("testName", 1, 0, 0, 0, 0, "testNote");
        assertEquals(true, result);
    }

    @Test
    public void testGetAccountsByAccountStatusType() {
        boolean found = false;
        Object[][] result = acInst.getAccountsByAccountStatusType(1);
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][1].equals("testName") && result[i][7].equals("testNote")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testGetAllAccounts() {
        boolean found = false;
        Account temp;
        List<Account> result = acInst.getAllAccounts();
        if(result == null || result.isEmpty()) fail();

        Iterator iter = result.iterator();
        while(iter.hasNext()){
            temp = (Account) iter.next();
            if(temp.getName().equals("testName") && temp.getNote().equals("testNote")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testGetAccountNames() {
        boolean found = false;
        String[] result = acInst.getAccountNames();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("testName")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testGetAccounts() {
        boolean found = false;
        Object[][] result = acInst.getAccounts();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][1].equals("testName") && result[i][7].equals("testNote")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testUpdateAccount() {
        boolean result;
        Account account = acInst.getAccountByName("testName");
        result = acInst.updateAccount(account.getAccountId(), 1);
        assertEquals(true, result);
        result = acInst.updateAccount(account.getAccountId(), "testName", 1, 0, 0, 0);
        assertEquals(true, result);
        result = acInst.updateAccount(account.getAccountId(), "", 1, 0, 0, 0);
        assertEquals(false, result);
        result = acInst.updateAccount(0, "testName", 1, 0, 0, 0);
        assertEquals(false, result);
        result = acInst.updateAccount(account.getAccountId(), "testName", 0, 0, 0, 0);
        assertEquals(false, result);
    }

    @Test
    public void testDeleteAccount() {
        boolean result;
        Account account = acInst.getAccountByName("testName");
        result = acInst.deleteAccount(account.getAccountId());
        assertEquals(true, result);
        assertEquals(1, account.getIsDeleted());
        account.setIsDeleted(0);
    }

}
