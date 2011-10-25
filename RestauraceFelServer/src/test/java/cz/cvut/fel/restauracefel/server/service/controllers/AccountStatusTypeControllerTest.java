/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.AccountStatusTypeController;
import java.util.Iterator;
import cz.cvut.fel.restauracefel.hibernate.AccountStatusType;
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
public class AccountStatusTypeControllerTest {

    private static AccountStatusTypeController astcInst;

    public AccountStatusTypeControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        astcInst = AccountStatusTypeController.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        AccountStatusType accountStatusType = astcInst.getAccountStatusTypeByName("testName");
        if(accountStatusType != null){
            accountStatusType.delete();
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateAccountStatusType() {
        boolean result;
        result = astcInst.createAccountStatusType("", "");
        assertEquals(false, result);
        result = astcInst.createAccountStatusType("testName", "testNote");
        assertEquals(true, result);
        result = astcInst.createAccountStatusType("testName", "testNote2");
        assertEquals(false, result);
    }

    @Test
    public void testGetAccountStatusTypeById() {
        AccountStatusType accountStatusType = astcInst.getAccountStatusTypeById(0);
        assertEquals(null, accountStatusType);
    }

    @Test
    public void testGetAccountStatusTypeByName() {
        AccountStatusType accountStatusType = astcInst.getAccountStatusTypeByName("testName");
        if(accountStatusType == null) fail();
    }

    @Test
    public void testGetAllAccountStatusTypes() {
        boolean found = false;
        AccountStatusType temp;
        List result = astcInst.getAllAccountStatusTypes();
        if(result == null || result.isEmpty()) fail();

        Iterator iter = result.iterator();
        while(iter.hasNext()){
            temp = (AccountStatusType) iter.next();
            if(temp.getName().equals("testName") && temp.getNote().equals("testNote")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testGetAccountStatusTypeNames() {
        boolean found = false;
        String[] result = astcInst.getAccountStatusTypeNames();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("testName")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testGetAccountStatusTypes() {
        boolean found = false;
        Object[][] result = astcInst.getAccountStatusTypes();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][1].equals("testName") && result[i][2].equals("testNote")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void testDeleteAccountStatusType() {
        AccountStatusType accountStatusType = astcInst.getAccountStatusTypeByName("testName");
        if(accountStatusType == null) fail();
        astcInst.deleteAccountStatusType(accountStatusType.getAccountStatusTypeId());
        assertEquals(1, accountStatusType.getIsDeleted());
        accountStatusType.setIsDeleted(0);
    }
}