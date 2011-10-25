/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.ExpenditureController;
import cz.cvut.fel.restauracefel.server.service.controllers.UserController;
import cz.cvut.fel.restauracefel.server.service.controllers.MaterialTypeController;
import cz.cvut.fel.restauracefel.server.service.controllers.MaterialController;
import cz.cvut.fel.restauracefel.hibernate.Expenditure;
import cz.cvut.fel.restauracefel.hibernate.MaterialType;
import cz.cvut.fel.restauracefel.hibernate.Material;
import cz.cvut.fel.restauracefel.hibernate.User;
import java.util.Date;
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
public class ExpenditureControllerTest {

    private static ExpenditureController ecInst;
    private static User user;
    private static MaterialType materialType;
    private static Material material;
    private static Date date;

    public ExpenditureControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        ecInst = ExpenditureController.getInstance();
        date = new Date();

        UserController uc = UserController.getInstance();
        uc.createUser("testName", "testSurname", "testUsername");
        user = uc.getUserByUsername("testUserName");

        MaterialTypeController mtc = MaterialTypeController.getInstance();
        mtc.createMaterialType("testName", "testNote");
        materialType = mtc.getMaterialTypeByName("testName");

        MaterialController mc = MaterialController.getInstance();
        mc.createMaterial("testName", materialType.getMaterialTypeId(), 1, "testBarcode", 0.0, 1.0, 2.0, 3.0);
        material = mc.getMaterialByName("testName");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        Expenditure expenditure = null;
        Object[][] temp = ecInst.getExpenditures();
        if(temp != null && temp.length > 0){
            for(int i = 0; i < temp.length; i ++){
                if(temp[i][2].equals("testName") && temp[i][5].equals("testUsername") && temp[i][6].equals("testNote")){
                    expenditure = ecInst.getExpenditureById(Integer.parseInt(temp[i][0].toString()));
                }
            }
        }

        if(expenditure != null) expenditure.delete();
        if(user != null) user.delete();
        if(material != null) material.delete();
        if(materialType != null) materialType.delete();
    }

    @Before
    public void tearUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestCreateExpenditure() {
        boolean result;
        result = ecInst.createExpenditure(date, material.getMaterialId(), 0.0, user.getUserId(), "testNote");
        assertEquals(true, result);
        result = ecInst.createExpenditure(date, 0, 1.0, user.getUserId(), "testNote");
        assertEquals(false, result);
        result = ecInst.createExpenditure(date, material.getMaterialId(), 100.0, user.getUserId(), "testNote");
        assertEquals(false, result);
        result = ecInst.createExpenditure(date, material.getMaterialId(), 1.0, 0, "testNote");
        assertEquals(false, result);
    }

    @Test
    public void TestUpdateExpenditure() {
        boolean result;

        Expenditure expenditure = null;
        Object[][] temp = ecInst.getExpenditures();
        if(temp != null && temp.length > 0){
            for(int i = 0; i < temp.length; i ++){
                if(temp[i][2].equals("testName") && temp[i][5].equals("testUsername") && temp[i][6].equals("testNote")){
                    expenditure = ecInst.getExpenditureById(Integer.parseInt(temp[i][0].toString()));
                }
            }
        }
        if(expenditure == null) fail();

        result = ecInst.updateExpenditure(expenditure.getExpenditureId(), date, material.getMaterialId(), 0.0, user.getUserId(), "testNote");
        assertEquals(true, result);
        result = ecInst.updateExpenditure(0, date, material.getMaterialId(), 0.0, user.getUserId(), "testNote");
        assertEquals(false, result);
        result = ecInst.updateExpenditure(expenditure.getExpenditureId(), date, 0, 0.0, user.getUserId(), "testNote");
        assertEquals(false, result);
        result = ecInst.updateExpenditure(expenditure.getExpenditureId(), date, material.getMaterialId(), 100.0, user.getUserId(), "testNote");
        assertEquals(false, result);
        result = ecInst.updateExpenditure(expenditure.getExpenditureId(), date, material.getMaterialId(), 0.0, 0, "testNote");
        assertEquals(false, result);
    }

    @Test
    public void TestGetExpenditures() {
        boolean found = false;
        Object[][] result = ecInst.getExpenditures();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][2].equals("testName") && result[i][5].equals("testUsername") && result[i][6].equals("testNote")) found = true;
        }
        
        assertEquals(true, found);
    }

}
