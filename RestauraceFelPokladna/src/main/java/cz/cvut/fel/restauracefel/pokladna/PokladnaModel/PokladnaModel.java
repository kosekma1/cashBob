/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.pokladna.PokladnaModel;

import cz.cvut.fel.restauracefel.hibernate.Account;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import cz.cvut.fel.restauracefel.pokladna_service.ServiceFacade;

/**
 *
 * @author libik
 */
public class PokladnaModel {
    /**
     * Slouzi k uprave uctu, napriklad pri smazani uctu
     * @param account Ktery ucet se vezme v potaz
     * @param action Co se ma stat
     */
    public static void updateAccount(Account account, int action){
        try {
            ServiceFacade.getInstance().updateAccount(account.getAccountId(), action);
        } catch (RemoteException ex) {
            Logger.getLogger(PokladnaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PokladnaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(PokladnaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean payNMenuItemsByAccount(int count, Integer menuItemId, Integer accountId){
        try {
            return ServiceFacade.getInstance().payNMenuItemsByAccount(count, menuItemId, accountId);
        } catch (RemoteException ex) {
            Logger.getLogger(PokladnaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PokladnaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(PokladnaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
