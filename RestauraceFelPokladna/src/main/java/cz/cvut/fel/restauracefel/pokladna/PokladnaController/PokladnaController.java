/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.pokladna.PokladnaController;

import cz.cvut.fel.restauracefel.pokladna.PokladnaModel.PokladnaModel;
import cz.cvut.fel.restauracefel.hibernate.Account;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.library.controller.CommonController;
import cz.cvut.fel.restauracefel.library.interfaces.IModuleInteface;
import cz.cvut.fel.restauracefel.library.model.CommonModelController;
import java.util.LinkedList;
import java.util.List;
import cz.cvut.fel.restauracefel.pokladna.pokladna_gui.PokladnaViewController;

/**
 *
 * @author Lukáš Viezán  
 */
public class PokladnaController implements IModuleInteface {

    private static final PokladnaController instance = new PokladnaController();
    private final CommonController commonController = CommonController.getInstance();
    private PokladnaViewController view;
    public User user;
    public String[] prava;
    private CommonModelController commonModelController;

    private PokladnaController() {
        commonModelController = CommonModelController.getInstance();
        view = PokladnaViewController.getInstance();
    }

    public static PokladnaController getInstance() {
        return instance;
    }

    public void run(User user, String[] prava) {
        this.prava = prava;
        this.user = user;

//        MainFrame pokladna = new MainFrame( );
//        pokladna.setVisible(true);
        view.run();
    }

    @Override
    public boolean isActive() {
        return view.isActive();
    }

    public static boolean payNMenuItemsByAccount(List<MenuItemObject> menuItems, Integer accountId) {
        for (int j = 0; j < menuItems.size(); j++) {
            MenuItemObject menuItem = menuItems.get(j);
            boolean isOK = PokladnaModel.payNMenuItemsByAccount((int) menuItem.getCount(), menuItem.getID(), accountId);
            if (!isOK) {
                PokladnaViewController.getInstance().showMessageDialogInformation("Položka menu nemohla být zaplacena.", "Placení položky");
                return false;
            }
        }
        return true;
    }

    /**
     * MenuItem objekty se predavaji ve forme dvojrozmerneho pole typu Object.
     * Tato trida vytvori List techto objektu.
     * @param objects seznam MenuItem objektu.
     * @return seznam vsech objektu.
     */
    public static List<MenuItemObject> menuItems(Object[][] objects) {
        LinkedList<MenuItemObject> seznam = new LinkedList<MenuItemObject>();
        if (objects != null) {
            for (int i = 0; i < objects.length; i++) {
                seznam.add(new MenuItemObject(objects[i]));
            }
        }

        return seznam;
    }

    /**
     * Vraci celkovou hodnotu vsech polozek na seznamu
     * @param menuItems polozky seznamu
     * @return hodnotu vsech polozek
     */
    public static double menuItemsSum(List<MenuItemObject> menuItems) {
        double sum = 0;
        for (int i = 0; i < menuItems.size(); i++) {
            sum += menuItems.get(i).value();
        }
        return sum;
    }

    /**
     * Rozhodne, zda se ma uzavrit ucet a take se na to zepta.
     * @param menuItems seznam vsech objektu, na zaklade kterych se rozhoduje
     * @return true, pokud byl ucet uzavren
     */
    public boolean uzavritUcet(List<MenuItemObject> menuItems, Account account) {
        if (PokladnaController.menuItemsSum(menuItems) <= 0) {
            if (view.showConfirmDialogStandard("Na účtu už nezbývá žádná suma k zaplacení. Chcete účet uzavřít?", "Uzavřít účet") == 0) {
                PokladnaModel.updateAccount(account, 2);
                return true;
            }
        }
        return false;
    }
}
