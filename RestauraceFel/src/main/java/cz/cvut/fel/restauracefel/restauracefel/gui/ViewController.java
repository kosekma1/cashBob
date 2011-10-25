package cz.cvut.fel.restauracefel.restauracefel.gui;

import cz.cvut.fel.restauracefel.library.Enums.EnumLoginResult;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.restauracefel.Controller.Controller;

/**
 *
 * @author Lukáš Viezán
 */
public class ViewController {

    private static final ViewController instance = new ViewController();
    private Controller controller = null;
    private MainFrame mainFrame = null;

    private ViewController() {
    }

    public static ViewController getInstance() {
        return instance;
    }

    /**
     * Zobrazení základního přihlašovacího okna
     */
    public void run() {
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    /**
     * Dotaz na ověření přihlašovacích údajů a následné zobrazení uživately
     * @param username
     * @param password
     * @return
     */
    public EnumLoginResult login(String username, String password, String requestRight) {
        EnumLoginResult result = getController().login(username, password, requestRight);

        switch (result) {
            case ConnectionFail:
                mainFrame.showErrorMessage("Nelze navázat spojení se serverem.", "Chyba komunikace");
                break;

            case ConfigFileNotFound:
                mainFrame.showErrorMessage("Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba");
                break;
        }

        return result;
    }

    /**
     * Zobrazení chybové hlášky
     * @param text popis chybové hlášky
     * @param title nadpis zprávy
     */
    private void showErrorMessage(String text, String title) {
        if (mainFrame != null) {
            mainFrame.showErrorMessage(text, title);
        }
    }

    /**
     * Zobrazení chybové zprávy o nenalezeném konfiguračním souboru
     */
    public void configFileError() {
        showErrorMessage("Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba");
    }

    /**
     * Zobrazení chybové hlášky o nepodařeném spojení s databází
     */
    public void connectionFailedError() {
        showErrorMessage("Nelze navázat spojení se serverem.", "Chyba komunikace");
    }

    /**
     * Zobrazení hlavní nabídky pro přihlášeného uživatele
     * @param username - Přihlašovací jméno uživatele
     */
    public void showUserGate(String username) {
        mainFrame.showUserGate(username);
    }

    /**
     * Metoda pro zjištění, zda aktuálně přihlášený uživatel má potřebné oprávnění
     * @param right požadované oprávnění od uživatele
     * @return
     */
    public boolean hasRights(String right) {
        return controller.hasRights(right);
    }

    /**
     * Odhlášení aktuálního uživatele
     */
    public void logout() {
        controller.logout();
    }

    /**
     * Zobrazí modul pro pokladnu
     */
    public void showPokladna() {
        getController().showPokladna();
    }

    /**
     * Zobrazí modul managera
     */
    public void showManager() {
        getController().showManager();
    }

    /**
     * Zobrazí modul storage
     */
    public void showStorage() {
        getController().showStorage();
    }
    
     /* Zobrazí modul pro smeny
     */
    public void showSmeny() {
        getController().showSmeny();
    }

    /**
     *
     * @return Vrací instanci hlavního Controlleru
     */
    private Controller getController() {
        if (controller == null) {
            controller = Controller.getInstance();
        }

        return controller;
    }
}
