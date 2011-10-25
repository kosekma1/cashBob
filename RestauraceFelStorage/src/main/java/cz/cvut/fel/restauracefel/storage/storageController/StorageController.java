/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.storage.storageController;

import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.library.interfaces.IModuleInteface;
import cz.cvut.fel.restauracefel.storage.sklad_gui.StorageViewController;

/**
 *
 * @author Lukáš Viezán
 */
public class StorageController implements IModuleInteface {

    public static final String DEFAULT_BUNDLE_BASE_NAME = "cz.cvut.fel.restauracefel.localization.restaurace_fel_storage_bundle";
    public static final String MESSAGE_BUNDLE_BASE_NAME = "cz.cvut.fel.restauracefel.localization.restaurace_fel_storage_message_bundle";

    private static final StorageController instance = new StorageController();
    private StorageViewController view = null;
    public User user;
    public String[] prava;

    private StorageController() {
        view = StorageViewController.getInstance();
    }

    public static StorageController getInstance() {
        return instance;
    }

    public void run(User user, String[] prava) {
        this.prava = prava;
        this.user = user;

        view.run();
    }

    @Override
    public boolean isActive() {
        return view.isActive();
    }
}
