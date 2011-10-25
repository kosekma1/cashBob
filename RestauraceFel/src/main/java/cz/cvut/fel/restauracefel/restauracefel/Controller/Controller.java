/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.restauracefel.Controller;

import cz.cvut.fel.restauracefel.library.model.CommonModelController;
import cz.cvut.fel.restauracefel.library.Enums.EnumLoginResult;
import cz.cvut.fel.restauracefel.library.controller.CommonController;
import cz.cvut.fel.restauracefel.restauracefel.gui.ViewController;
import cz.cvut.fel.restauracefel.library.interfaces.IModuleInteface;
import cz.cvut.fel.restauracefel.manager.ManagerController.ManagerController;
import cz.cvut.fel.restauracefel.pokladna.PokladnaController.PokladnaController;
import cz.cvut.fel.restauracefel.smeny.SmenyController.SmenyController;
import cz.cvut.fel.restauracefel.storage.storageController.StorageController;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel;

/**
 *
 * @author Lukáš Viezán
 */
public class Controller implements IModuleInteface {

    private static final Controller instance = new Controller();
    private CommonModelController model;
    private ViewController view;
    private final CommonController commonController;

    /**
     * Privátní konstruktor třídy Controller, součást návrhového vzoru singlethon
     */
    private Controller() {
        model = CommonModelController.getInstance();
        view = ViewController.getInstance();
        commonController = CommonController.getInstance();
    }

    /**
     * Součást návrhového vzoru singlethon
     * @return Vrací instanci třídy Controller společnou pro celou aplikaci
     */   
    public static Controller getInstance() {
        return instance;
    }

    /**
     * Spustí aplikaci a zobrazí základní přihlašovací okno
     */
    public void run() {
        // Nastavení defaultního vzhledu JFrame podle knihovny LookAndFeelDecorated
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(new SubstanceOfficeSilver2007LookAndFeel());
                    UIManager.put("ScrollBar.width", 32);
                } catch (Exception e) {
                    System.out.println("Substance Raven Graphite failed to initialize");
                }

                // Vytvoření a zobrazení hlavního přihlašovacího okna
                view.run();
            }
        });
    }

    /**
     * Otestuje přihlašovací údaje zadané uživatelem
     * @param username - Zadané uživatelské jméno typu String
     * @param password - Zadané uživatelské heslo typu String
     * @return Vrací výsledek přihlašování v datovém typu EnumLoginResult umístěném ve FELLibrary
     */
    public EnumLoginResult login(String username, String password, String requestedRight) {
        EnumLoginResult result = commonController.login(username, password, requestedRight);
        
        if (result == EnumLoginResult.LoginSuccesful && requestedRight == null) {
            view.showUserGate(model.getLoggedUser().getUsername());
        }
        return result;
    }

    /**
     * Odhlásí aktuálně přuhlášeného uživatele
     */
    public void logout() {
        model.setLoggedUser(null);
    }

    /**
     * Zobrazí modul pokladny, pokud již není zobrazen
     */
    public void showPokladna() {
        if (!PokladnaController.getInstance().isActive()) {
            PokladnaController.getInstance().run(model.getLoggedUser(), model.getRights());
        }
    }

    /**
     * Zobrazí modul manager na obrazovku, pokud již není zobrazen
     */
    public void showManager() {
        if (!ManagerController.getInstance().isActive()) {
            ManagerController.getInstance().run(model.getLoggedUser(), model.getRights());
        }
    }

    /**
     * Zobrazí modul storage na obrazovku, pokud již není zobrazen
     */
    public void showStorage() {
        if (!StorageController.getInstance().isActive()) {
            StorageController.getInstance().run(model.getLoggedUser(), model.getRights());
        }
    }
    
    /**
     * Zobrazí modul smeny, pokud již není zobrazen
     */
    public void showSmeny() {
        if (!SmenyController.getInstance().isActive()) {
            SmenyController.getInstance().run(model.getLoggedUser(), model.getRights());
        }
    }

    /**
     *
     * @return Vrací instanci vrstvy Model
     */
    public CommonModelController getModel() {
        return model;
    }

    /**
     * Metoda pro zjištění, zda aktuálně přihlášený uživatel má potřebné oprávnění
     * @param right požadované oprávnění od uživatele
     * @return
     */
    public boolean hasRights(String right) {
        return model.hasRights(right);
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
