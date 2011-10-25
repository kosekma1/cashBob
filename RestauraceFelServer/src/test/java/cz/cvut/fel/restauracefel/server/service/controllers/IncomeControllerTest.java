/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.UserController;
import cz.cvut.fel.restauracefel.server.service.controllers.MaterialTypeController;
import cz.cvut.fel.restauracefel.server.service.controllers.MaterialController;
import cz.cvut.fel.restauracefel.server.service.controllers.IncomeController;
import cz.cvut.fel.restauracefel.hibernate.Income;
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
public class IncomeControllerTest {


    private static IncomeController icInst;
    private static User user;
    private static MaterialType materialType;
    private static Material material;
    private static Date date;

    public IncomeControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        icInst = IncomeController.getInstance();
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
        Income income = null;
        Object[][] temp = icInst.getIncomes();
        if(temp != null && temp.length > 0){
            for(int i = 0; i < temp.length; i ++){
                if(temp[i][2].equals("testName") && temp[i][6].equals("testUsername") && temp[i][7].equals("testNote")){
                    income = icInst.getIncomeByID(Integer.parseInt(temp[i][0].toString()));
                }
            }
        }

        if(income != null) income.delete();
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
    public void TestCreateIncome() {
        boolean result;
        result = icInst.createIncome(date, material.getMaterialId(), 0.0, 1.0, user.getUserId(), "testNote");
        assertEquals(true, result);
        result = icInst.createIncome(date, 0, 0.0, 1.0, user.getUserId(), "testNote");
        assertEquals(false, result);
        result = icInst.createIncome(date, material.getMaterialId(), 0.0, 1.0, 0, "testNote");
        assertEquals(false, result);
    }

    @Test
    public void TestUpdateIncome() {
        boolean result;

        Income income = null;
        Object[][] temp = icInst.getIncomes();
        if(temp != null && temp.length > 0){
            for(int i = 0; i < temp.length; i ++){
                if(temp[i][2].equals("testName") && temp[i][6].equals("testUsername") && temp[i][7].equals("testNote")){
                    income = icInst.getIncomeByID(Integer.parseInt(temp[i][0].toString()));
                }
            }
        }
        if(income == null) fail();

        result = icInst.updateIncome(income.getIncomeId(), date, material.getMaterialId(), 1.0, 2.0, user.getUserId(), "testNote");
        assertEquals(true, result);
        result = icInst.updateIncome(0, date, material.getMaterialId(), 1.0, 2.0, user.getUserId(), "testNote");
        assertEquals(false, result);
        result = icInst.updateIncome(income.getIncomeId(), date, 0, 1.0, 2.0, user.getUserId(), "testNote");
        assertEquals(false, result);
        result = icInst.updateIncome(income.getIncomeId(), date, material.getMaterialId(), 1.0, 2.0, 0, "testNote");
        assertEquals(false, result);
    }

    @Test
    public void TestGetIncomes() {
        boolean found = false;
        Object[][] result = icInst.getIncomes();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][2].equals("testName") && result[i][6].equals("testUsername") && result[i][7].equals("testNote")) found = true;
        }

        assertEquals(true, found);
    }

}
