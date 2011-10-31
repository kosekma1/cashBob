/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.smeny.SmenyController;

import cz.cvut.fel.restauracefel.hibernate.Role;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.smeny.smeny_main.ResultTableModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import junit.framework.TestCase;

/**
 *
 * @author Martin
 */
public class SmenyControllerTest extends TestCase {
    
    public SmenyControllerTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getInstance method, of class SmenyController.
     */
    public void testGetInstance() {
        System.out.println("getInstance");        
        SmenyController result = SmenyController.getInstance();
        boolean expResult = true;
        assertEquals(expResult, (result!=null));                        
    }

    /**
     * Test of run method, of class SmenyController.
     */
    public void testRun() {
        System.out.println("run");
        User user = new User();
        String[] prava = new String[]{"Vkládat","Mazat","Editovat"};
        SmenyController instance = new SmenyController();        
        instance.run(user, prava);
        
        boolean expResult = true;
        boolean result = false;
        if(instance.prava==prava && instance.user == user) result = true;
        assertEquals(expResult, result);              
    }

    /**
     * Test of isActive method, of class SmenyController.
     */
    public void testIsActive() {
        System.out.println("isActive");
        SmenyController instance = new SmenyController();
        boolean expResult = true;
        boolean result = instance.isActive();
        assertEquals(expResult, result);        
    }

    /**
     * Test of generateTableDataTypeShifts method, of class SmenyController.
     */
    public void testGenerateTableDataTypeShifts() throws Exception {
        //pro unit testovani musi byt nezavisle na databazi
        /*System.out.println("generateTableDataTypeShifts");
        SmenyController instance = new SmenyController();
        instance.generateTableDataTypeShifts();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
         * 
         */
    }

    /**
     * Test of generateComboBoxRoles method, of class SmenyController.
     */
    public void testGenerateComboBoxRoles() throws Exception {
        System.out.println("generateComboBoxRoles");
        SmenyController instance = new SmenyController();
        List<Role> roles = new ArrayList<Role>();        
        roles.add(new Role());
        roles.add(new Role());
        roles.add(new Role());
        instance.generateComboBoxRoles(roles);                
    }

    /**
     * Test of addWorkShift method, of class SmenyController.
     */
    public void testAddWorkShift() {
        
        SmenyController.getInstance().addWorkShift("test1");
        SmenyController.getInstance().addWorkShift("test2");
        SmenyController.getInstance().addWorkShift("test3");                     
        SmenyController.getInstance().printTestTableWorkShiftData();                             
        /*System.out.println("addWorkShift");
        String nameWorkShift = "";
        SmenyController instance = new SmenyController();
        instance.addWorkShift(nameWorkShift);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
         * 
         */
    }

    /**
     * Test of test method, of class SmenyController.
     */
    public void testTest() {
        /*System.out.println("test");
        SmenyController instance = new SmenyController();
        instance.test();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
        
    }

    /**
     * Test of main method, of class SmenyController.
     */
    public void testMain() {
        /*System.out.println("main");
        String[] args = null;
        SmenyController.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
         * 
         */
    }

    /**
     * Test of getModelTypeWorkShift method, of class SmenyController.
     */
    public void testGetModelTypeWorkShift() {
        /*System.out.println("getModelTypeWorkShift");
        SmenyController instance = new SmenyController();
        ResultTableModel expResult = null;
        ResultTableModel result = instance.getModelTypeWorkShift();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
         * 
         */
    }

    /**
     * Test of getModelWorkShift method, of class SmenyController.
     */
    public void testGetModelWorkShift() {
        /*System.out.println("getModelWorkShift");
        SmenyController instance = new SmenyController();
        ResultTableModel expResult = null;
        ResultTableModel result = instance.getModelWorkShift();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
         * 
         */
    }

    /**
     * Test of getModelRoles method, of class SmenyController.
     */
    public void testGetModelRoles() {
        /*System.out.println("getModelRoles");
        SmenyController instance = new SmenyController();
        DefaultComboBoxModel expResult = null;
        DefaultComboBoxModel result = instance.getModelRoles();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
         * 
         */
    }
    
    public void testgenerateDataList() throws Exception {
        
    }
}
