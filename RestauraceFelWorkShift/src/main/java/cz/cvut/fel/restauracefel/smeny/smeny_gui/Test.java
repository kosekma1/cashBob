/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.smeny.smeny_gui;

import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class Test {
    public static void main(String[] args) throws NotBoundException {
        
    }
    
    public void testCreateTemplateForm(){
        MainFrame mainFrame = new MainFrame();
        try {
            CreateTemplateForm ctf;
            try {
                try {
                    ctf = new CreateTemplateForm(mainFrame, new StatusBar());
                    ctf.setVisible(true);
                } catch (NotBoundException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } catch (RemoteException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }            
        } catch (FileNotFoundException ex) {
            
        }
    }
}
