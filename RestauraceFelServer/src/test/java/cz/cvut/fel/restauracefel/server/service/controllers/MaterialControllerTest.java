/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.server.service.controllers.MaterialController;
import cz.cvut.fel.restauracefel.server.service.controllers.MaterialTypeController;
import java.util.Iterator;
import java.util.List;
import cz.cvut.fel.restauracefel.hibernate.Material;
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
public class MaterialControllerTest {

    private static MaterialController mcInst;
    private static MaterialType materialType;

    public MaterialControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        mcInst = MaterialController.getInstance();

        MaterialTypeController mtc = MaterialTypeController.getInstance();
        mtc.createMaterialType("testName", "testNote");
        materialType = mtc.getMaterialTypeByName("testName");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        Material material = mcInst.getMaterialByName("testName");
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
    public void TestCreateMaterial() {
        boolean result;
        result = mcInst.createMaterial("testName", materialType.getMaterialTypeId(), 1, "testBarcode", 0.0, 1.0, 2.0, 3.0);
        assertEquals(true, result);
        result = mcInst.createMaterial("testName", materialType.getMaterialTypeId(), 1, "testBarcode2", 0.0, 1.0, 2.0, 3.0);
        assertEquals(false, result);
        result = mcInst.createMaterial("testName2", materialType.getMaterialTypeId(), 1, "testBarcode", 0.0, 1.0, 2.0, 3.0);
        assertEquals(false, result);
        result = mcInst.createMaterial("testName", 0, 1, "testBarcode", 0.0, 1.0, 2.0, 3.0);
        assertEquals(false, result);
        result = mcInst.createMaterial("testName", materialType.getMaterialTypeId(), 0, "testBarcode", 0.0, 1.0, 2.0, 3.0);
        assertEquals(false, result);
    }

    @Test
    public void TestIsExistedMaterial() {
        boolean result;
        result = mcInst.isExistedMaterial("testName", "testBarcode");
        assertEquals(true, result);
        result = mcInst.isExistedMaterial(null, "testBarcode");
        assertEquals(true, result);
        result = mcInst.isExistedMaterial("testName", null);
        assertEquals(true, result);
        result = mcInst.isExistedMaterial(null, null);
        assertEquals(false, result);
    }

    @Test
    public void TestIsExistedByBarcode() {
        boolean result;
        result = mcInst.isExistedByBarcode("testBarcode");
        assertEquals(true, result);
        result = mcInst.isExistedByBarcode(null);
        assertEquals(false, result);
    }

    @Test
    public void TestGetMaterialByName() {
        Material material;
        material = mcInst.getMaterialByName("testName");
        if(material == null) fail();
    }

    @Test
    public void TestGetAllMaterials() {
        boolean found = false;
        Material temp;
        List<Material> result = mcInst.getAllMaterials();
        if(result == null || result.isEmpty()) fail();

        Iterator iter = result.iterator();
        while(iter.hasNext()){
            temp = (Material) iter.next();
            if(temp.getName().equals("testName") && temp.getBarcode().equals("testBarcode")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetMaterialNames() {
        boolean found = false;
        String[] result = mcInst.getMaterialNames();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("testName")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetMaterialNamesByMaterialType() {
        boolean found = false;
        String[] result = mcInst.getMaterialNamesByMaterialType(materialType.getMaterialTypeId());
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i].equals("testName")) found = true;
        }

        assertEquals(true, found);
    }

    @Test
    public void TestGetMaterials() {
        boolean found = false;
        Object[][] result = mcInst.getMaterials();
        if(result == null || result.length < 1) fail();

        for(int i = 0; i < result.length; i ++){
            if(result[i][1].equals("testName") && result[i][5].equals("testBarcode")) found = true;
        }
        
        assertEquals(true, found);
    }

    @Test
    public void TestUpdateMaterial() {
        boolean result;
        Material material = mcInst.getMaterialByName("testName");
        result = mcInst.updateMaterial(material.getMaterialId(), "testName", materialType.getMaterialTypeId(), 1, "testBarcodeEdit", 0.0, 1.0, 2.0, 3.0);
        assertEquals(true, result);
        assertEquals("testBarcodeEdit", material.getBarcode());
        material.setBarcode("testBarcode");
        result = mcInst.updateMaterial(0, "testName", materialType.getMaterialTypeId(), 1, "testBarcodeEdit", 0.0, 1.0, 2.0, 3.0);
        assertEquals(false, result);
        result = mcInst.updateMaterial(material.getMaterialId(), "testName", 0, 1, "testBarcodeEdit", 0.0, 1.0, 2.0, 3.0);
        assertEquals(false, result);
    }

}
