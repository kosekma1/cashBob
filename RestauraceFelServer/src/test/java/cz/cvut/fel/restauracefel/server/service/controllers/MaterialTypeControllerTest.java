/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.MaterialTypeController;
import java.util.Iterator;
import java.util.List;
import cz.cvut.fel.restauracefel.hibernate.MaterialType;
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
public class MaterialTypeControllerTest {

    private static MaterialTypeController mtcInst = MaterialTypeController.getInstance();

    public MaterialTypeControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        MaterialType materialType = mtcInst.getMaterialTypeByName("testName");
        if(materialType != null){
            materialType.delete();
        }
    }

    @Before
    public void tearUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestCreateMaterialType(){
        boolean result;
        result = mtcInst.createMaterialType("testName", "testNote");
        assertEquals(true, result);
        result = mtcInst.createMaterialType("testName", "testNote2");
        assertEquals(false, result);
    }

    @Test
    public void TestDeleteMaterialType() {
        MaterialType materialType = mtcInst.getMaterialTypeByName("testName");
        if(materialType == null) fail("Nenalezen zaznam s name='testName'");
        mtcInst.deleteMaterialType(materialType.getMaterialTypeId());
        assertEquals(1, materialType.getIsDeleted());
        materialType.setIsDeleted(0);
    }

    @Test
    public void TestUpdateMaterialType() {
        boolean result;
        MaterialType materialType = mtcInst.getMaterialTypeByName("testName");
        result = mtcInst.updateMaterialType(materialType.getMaterialTypeId(), "testNameEdit", "testNoteEdit");
        assertEquals(true, result);
        result = mtcInst.updateMaterialType(materialType.getMaterialTypeId(), "testName", "testNote");
        assertEquals(true, result);
    }

    @Test
    public void  TestGetMaterialTypeByID() {
        MaterialType materialType = mtcInst.getMaterialTypeByID(0);
        assertEquals(null, materialType);
    }

    @Test
    public void  TestGetMaterialTypeByName() {
        MaterialType materialType = mtcInst.getMaterialTypeByName("testName");
        if(materialType == null) fail();
    }

    @Test
    public void TestGetMaterialTypeNames() {
        boolean found = false;
        String[] result = mtcInst.getMaterialTypeNames();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("testName")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetAllMaterialTypes() {
        boolean found = false;
        MaterialType temp;
        List<MaterialType> result = mtcInst.getAllMaterialTypes();
        if(result == null || result.isEmpty()) fail();

        Iterator iter = result.iterator();
        while(iter.hasNext()){
            temp = (MaterialType) iter.next();
            if(temp.getName().equals("testName") && temp.getNote().equals("testNote")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetMaterialTypes() {
        boolean found = false;
        Object[][] result = mtcInst.getMaterialTypes();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][1].equals("testName") && result[i][2].equals("testNote")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestIsDeletableMaterialType() {
        MaterialType materialType = mtcInst.getMaterialTypeByName("testName");
        assertEquals(true, mtcInst.isDeletableMaterialType(materialType.getMaterialTypeId()));
    }

}
