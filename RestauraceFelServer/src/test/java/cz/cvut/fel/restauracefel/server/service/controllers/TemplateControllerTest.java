/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Template;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kosekm
 */
public class TemplateControllerTest {

    private static TemplateController instance;

    public TemplateControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        instance = TemplateController.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        Template template = instance.findTemplateByName("testName");       
        if(template!=null) template.delete();                    
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createTemplate method, of class TemplateController.
     */
    @Test
    public void testCreateTemplate() {
        Template template = new Template();
        template.setName("testName");
        template.setIsDeleted(0);
        template.setStatus(1);
        boolean result = instance.createTemplate(template);
        assertEquals(true, result);
    }

    /**
     * Test of findTemplateByName method, of class TemplateController.
     */
    @Test
    public void testFindTemplateByName() {
        String name = "testName";
        Template result = instance.findTemplateByName(name);
        assertEquals(true, result != null);
    }

    /**
     * Test of getTemplates method, of class TemplateController.
     */
    @Test
    public void testGetTemplates() {
        List resultList = instance.getTemplates();
        boolean result = resultList != null && !resultList.isEmpty();
        assertEquals(true, result);
    }

    /**
     * Test of createNewTemplateList method, of class TemplateController.
     */
    @Test
    public void testCreateNewTemplateList() {
        int idTemplate = instance.findTemplateByName("testName").getIdTemplate();
        int idTypeWorkShift = 1;
        boolean result = instance.createNewTemplateList(idTemplate, idTypeWorkShift);
        assertEquals(true, result);
    }

    /**
     * Test of getTemplateListByTemplateId method, of class TemplateController.
     */
    @Test
    public void testGetTemplateListByTemplateId() {
        int idTemplate = instance.findTemplateByName("testName").getIdTemplate();
        List resultList = instance.getTemplateListByTemplateId(idTemplate);
        boolean result = resultList != null && !resultList.isEmpty();
        assertEquals(true, result);
    }

    /**
     * Test of deleteTemplateByName method, of class TemplateController.
     */
    @Test
    public void testDeleteTemplateByName() {
        String name = "testName";
        boolean result = instance.deleteTemplateByName(name);
        assertEquals(true, result);
    }
}
