/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.UnitTypeController;
import cz.cvut.fel.restauracefel.server.service.controllers.UserController;
import cz.cvut.fel.restauracefel.server.service.controllers.DepreciationController;
import cz.cvut.fel.restauracefel.server.service.controllers.MaterialTypeController;
import cz.cvut.fel.restauracefel.server.service.controllers.MaterialController;
import cz.cvut.fel.restauracefel.server.service.controllers.ReasonTypeController;
import cz.cvut.fel.restauracefel.hibernate.MaterialType;
import cz.cvut.fel.restauracefel.hibernate.Depreciation;
import cz.cvut.fel.restauracefel.hibernate.Material;
import cz.cvut.fel.restauracefel.hibernate.ReasonType;
import cz.cvut.fel.restauracefel.hibernate.User;
import java.util.Date;
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
public class DepreciationControllerTest {

    private static DepreciationController dcInst;
    private static User userReporter, userOffender;
    private static MaterialType materialType;
    private static Material material;
    private static ReasonType reasonType;
    private static Date date;

    public DepreciationControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        dcInst = DepreciationController.getInstance();
        date = new Date();

        UserController uc = UserController.getInstance();
        uc.createUser("testName1", "testSurname1", "testUsername1");
        uc.createUser("testName2", "testSurname2", "testUsername2");
        userReporter = uc.getUserByUsername("testUsername1");
        userOffender = uc.getUserByUsername("testUsername2");

        MaterialTypeController mtc = MaterialTypeController.getInstance();
        mtc.createMaterialType("testName", "testNote");
        materialType = mtc.getMaterialTypeByName("testName");

        UnitTypeController utc = UnitTypeController.getInstance();
        
        MaterialController mc = MaterialController.getInstance();
        mc.createMaterial("testMaterial", materialType.getMaterialTypeId(), utc.getUnitTypeByName("gram").getUnitTypeId(), "testBarcode", 0.0, 1.0, 2.0, 3.0);
        material = mc.getMaterialByName("testMaterial");
        material.setCurrentQuantity(2.0);

        ReasonTypeController rtc = ReasonTypeController.getInstance();
        rtc.createReasonType("testName", "testNote");
        reasonType = rtc.getReasonTypeByName("testName");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        Depreciation toDelete = null;
        Object[][] temp = dcInst.getDepreciations();
        if(temp != null && temp.length > 0){
            for(int i = 0; i < temp.length; i ++){
                if(temp[i][2].equals("testMaterial") && temp[i][5].equals("testName") && temp[i][6].equals("testUsername2") && temp[i][7].equals("testUsername1") && temp[i][8].equals("testNote")){
                    toDelete = dcInst.getDepreciationById(Integer.parseInt(temp[i][0].toString()));
                }
            }
        }

        if(toDelete != null) toDelete.delete();
        if(userReporter != null) userReporter.delete();
        if(userOffender != null) userOffender.delete();
        if(materialType != null) materialType.delete();
        if(material != null) material.delete();
        if(reasonType != null) reasonType.delete();
    }

    @Before
    public void tearUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateDepreciation() {
        boolean result;
        result = dcInst.createDepreciation(userReporter.getUserId(), userOffender.getUserId(), material.getMaterialId(), 1.0, date, reasonType.getReasonTypeId(), "testNote");
        assertEquals(true, result);
        result = dcInst.createDepreciation(0, userOffender.getUserId(), material.getMaterialId(), 0.0, date, reasonType.getReasonTypeId(), "testNote");
        assertEquals(false, result);
        result = dcInst.createDepreciation(userReporter.getUserId(), 0, material.getMaterialId(), 0.0, date, reasonType.getReasonTypeId(), "testNote");
        assertEquals(false, result);
        result = dcInst.createDepreciation(userReporter.getUserId(), userOffender.getUserId(), 0, 0.0, date, reasonType.getReasonTypeId(), "testNote");
        assertEquals(false, result);
        result = dcInst.createDepreciation(userReporter.getUserId(), userOffender.getUserId(), material.getMaterialId(), 100.0, date, reasonType.getReasonTypeId(), "testNote");
        assertEquals(false, result);
        result = dcInst.createDepreciation(userReporter.getUserId(), userOffender.getUserId(), material.getMaterialId(), 0.0, date, 0, "testNote");
        assertEquals(false, result);
    }

    @Test
    public void TestUpdateDepreciation() {
        boolean result;

        Depreciation depreciation = null;
        Object[][] temp = dcInst.getDepreciations();
        if(temp != null && temp.length > 0){
            for(int i = 0; i < temp.length; i ++){
                if(temp[i][2].equals("testMaterial") && temp[i][5].equals("testName") && temp[i][6].equals("testUsername2") && temp[i][7].equals("testUsername1") && temp[i][8].equals("testNote")){
                    depreciation = dcInst.getDepreciationById(Integer.parseInt(temp[i][0].toString()));
                }
            }
        }
        if(depreciation == null) fail();

        result = dcInst.updateDepreciation(depreciation.getDepreciationId(), userReporter.getUserId(), userOffender.getUserId(), material.getMaterialId(), 1.0, date, reasonType.getReasonTypeId(), "testNote");
        assertEquals(true, result);
        result = dcInst.updateDepreciation(0, userReporter.getUserId(), userOffender.getUserId(), material.getMaterialId(), 1.0, date, reasonType.getReasonTypeId(), "testNote");
        assertEquals(false, result);
        result = dcInst.updateDepreciation(depreciation.getDepreciationId(), 0, userOffender.getUserId(), material.getMaterialId(), 1.0, date, reasonType.getReasonTypeId(), "testNote");
        assertEquals(false, result);
        result = dcInst.updateDepreciation(depreciation.getDepreciationId(), userReporter.getUserId(), 0, material.getMaterialId(), 1.0, date, reasonType.getReasonTypeId(), "testNote");
        assertEquals(false, result);
        result = dcInst.updateDepreciation(depreciation.getDepreciationId(), userReporter.getUserId(), userOffender.getUserId(), 0, 1.0, date, reasonType.getReasonTypeId(), "testNote");
        assertEquals(false, result);
        result = dcInst.updateDepreciation(depreciation.getDepreciationId(), userReporter.getUserId(), userOffender.getUserId(), material.getMaterialId(), 1.0, date, 0, "testNote");
        assertEquals(false, result);
    }

    @Test
    public void TestGetDepreciations() {
        boolean found = false;
        Object[][] result = dcInst.getDepreciations();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][2].equals("testMaterial") && result[i][5].equals("testName") && result[i][6].equals("testUsername2") && result[i][7].equals("testUsername1") && result[i][8].equals("testNote")) found = true;
        }

        assertEquals(true, found);
    }
}
