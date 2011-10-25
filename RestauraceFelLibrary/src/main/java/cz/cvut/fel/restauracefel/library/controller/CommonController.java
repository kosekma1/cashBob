/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.library.controller;

import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.library.Enums.EnumLoginResult;
import cz.cvut.fel.restauracefel.library.model.CommonModelController;
import cz.cvut.fel.restauracefel.library.service.CommonServiceFacade;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Controller layer class common for all modules. Provide login functions.
 * 
 * @author basekjin
 */
public class CommonController {

    private CommonModelController commonModelController;
    private static CommonController instance = null;

    public static CommonController getInstance() {
        if (instance == null) {
            instance = new CommonController();
        }
        return instance;
    }
    
    private CommonController() {
        commonModelController = CommonModelController.getInstance();
    }

    /**
     * Otestuje přihlašovací údaje zadané uživatelem
     * @param username - Zadané uživatelské jméno typu String
     * @param password - Zadané uživatelské heslo typu String
     * @return Vrací výsledek přihlašování v datovém typu EnumLoginResult umístěném ve FELLibrary
     */
    public EnumLoginResult login(String username, String password, String requestedRight) {

        boolean isLoginSucceful = false;

        try {
            isLoginSucceful = CommonServiceFacade.getInstance().isValidUser(username, password);

            if (!isLoginSucceful) {
                return EnumLoginResult.WrongLoginData;
            } else {

                if (requestedRight != null) {
                    if (hasRightByUsername(username, requestedRight)) {
                        return EnumLoginResult.LoginSuccesful;
                    } else {
                        return EnumLoginResult.ScantRights;
                    }

                }

                userGate(username);
                return EnumLoginResult.LoginSuccesful;
            }

        } catch (FileNotFoundException ex) {
            return EnumLoginResult.ConfigFileNotFound;
        } catch (RemoteException ex) {

            System.out.println(ex.getMessage());
            return EnumLoginResult.ConnectionFail;
        } catch (Exception ex) {
            return EnumLoginResult.ConnectionFail;
        }

    }

    private boolean hasRightByUsername(String username, String requestedRight) throws RemoteException, FileNotFoundException, FileNotFoundException, NotBoundException {

        String[] rights = null;

        User user = CommonServiceFacade.getInstance().getUserByUsername(username);
        rights = CommonServiceFacade.getInstance().getRightsByUser(user.getUserId());

        for (int i = 0; i < rights.length; i++) {
            if (rights[i].equals(requestedRight)) {
                return true;
            }
        }

        return false;
    }

    public void userGate(String username) throws NotBoundException, FileNotFoundException, RemoteException {
        commonModelController.setLoggedUser(CommonServiceFacade.getInstance().getUserByUsername(username));
        commonModelController.setRights(CommonServiceFacade.getInstance().getRightsByUser(commonModelController.getLoggedUser().getUserId()));
    }
    

    public EnumLoginResult additionalLogin(String username, String password, String requestedRight) {
        return login(username, password, requestedRight);
    }

    public void setAdditionalUserAccessable(boolean accessable) {
        commonModelController.setAditionalUserAccessable(accessable);
    }

    public boolean isAdditionalUserAccessable() {
        return commonModelController.isAditionalUserAccessable();
    }
}
