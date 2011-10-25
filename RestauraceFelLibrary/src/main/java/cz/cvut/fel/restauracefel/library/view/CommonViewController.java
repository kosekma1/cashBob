/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.library.view;

import cz.cvut.fel.restauracefel.library.controller.CommonController;
import cz.cvut.fel.restauracefel.library.library_gui.AdditionalLogin;
import cz.cvut.fel.restauracefel.library.library_gui.BaseAdditionalLogin;
import javax.swing.JFrame;

/**
 *
 * @author basekjin
 */
public class CommonViewController {
    private static final CommonViewController instance = new CommonViewController( );
    
    private CommonViewController() {
        
    }
    
    public static CommonViewController getInstance( ) {
        return instance;
    }
    
    /**
     * Method shows additional login dialog and perform additional login for
     * given right. If additional login is successfull true is returned otherwise
     * false is returned.
     * 
     * @author basekjin
     * @param frame parent JFrame to place additional login dialog
     * @param forRigth reguested rigth
     * @return true if login successfull
     */
    public boolean performAdditionalLogin(JFrame frame, String forRigth) {
        assert (true);
        new AdditionalLogin(frame, true, "Změna přístupových práv");

        if (!CommonController.getInstance().isAdditionalUserAccessable()) {
            return false;
        }
        CommonController.getInstance().setAdditionalUserAccessable(false);
        return true;
    }
    
    /**
     * Method shows base additional login dialog and perform additional login for
     * given right. If additional login is successfull true is returned otherwise
     * false is returned.
     * 
     * @author basekjin
     * @param frame parent JFrame to place additional login dialog
     * @param forRigth reguested rigth
     * @return true if login successfull
     */
    public boolean performBaseAdditionalLogin(JFrame frame, String forRigth) {
        assert (true);
        new BaseAdditionalLogin(frame, true, "Změna přístupových práv");

        if (!CommonController.getInstance().isAdditionalUserAccessable()) {
            return false;
        }
        CommonController.getInstance().setAdditionalUserAccessable(false);
        return true;
    }
    
}
