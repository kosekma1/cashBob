/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.manager.manager_gui;

import cz.cvut.fel.restauracefel.library.Enums.EnumLoginResult;
import cz.cvut.fel.restauracefel.manager.ManagerController.ChangePasswordResult;
import cz.cvut.fel.restauracefel.manager.ManagerController.ManagerController;
import cz.cvut.fel.restauracefel.manager.ManagerController.MenuItemResult;
import cz.cvut.fel.restauracefel.manager.ManagerController.MenuItemTypeResult;
import cz.cvut.fel.restauracefel.manager.ManagerController.MenuMenuItemResult;
import cz.cvut.fel.restauracefel.manager.ManagerController.MenuResult;
import cz.cvut.fel.restauracefel.manager.ManagerController.PrintMenuResult;
import cz.cvut.fel.restauracefel.manager.ManagerController.RecipeDialogResult;
import cz.cvut.fel.restauracefel.manager.ManagerController.TableDialogResult;
import cz.cvut.fel.restauracefel.manager.ManagerController.UserFormResult;
import cz.cvut.fel.restauracefel.hibernate.User;
import java.util.Date;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;

/**
 *
 * @author Lukáš Viezán
 */
public class ManagerViewController {

    private static final ManagerViewController instance = new ManagerViewController();

    private ManagerController controller = null;
    private MainFrame mainFrame = null;

    private ManagerViewController( ) { }

    public static ManagerViewController getInstance( ) {
        return instance;
    }

    public void run( ) {
        mainFrame = new MainFrame( );
        mainFrame.setVisible( true );
    }

    public boolean isActive( ) {
        if ( mainFrame == null ) {
            return false;
        }

        return mainFrame.isVisible( );
    }

    /* ================= ChangePasswordDialog ================= */
    /**
     * Metoda pro zmenu prihlasovaciho hesla prave prihlaseneho uzivateli
     * @param oldPassword
     * @param newPassword
     * @param validPassword
     * @return
     */
    public ChangePasswordResult changePassword(String oldPassword, String newPassword, String validPassword) {
        ChangePasswordResult result = getControler().changePassword(oldPassword, newPassword, validPassword);

        switch(result){
            case ConnectionFail:
                mainFrame.showErrorMessage( "Nelze navázat spojení se serverem.", "Chyba komunikace" );
            break;

            case ConfigFileNotFound:
                mainFrame.showErrorMessage( "Konfigurační soubor \"" + ConfigParser.getConfigFile( ) + "\" nebyl nalezen.", "Chyba" );
            break;
            
            case EmptyOldPassword:
                mainFrame.showErrorMessage("Musí být uvedeno stávající heslo.", "Změna hesla");
            break;
            
            case WrongOldPassword:
                mainFrame.showErrorMessage("Uvedeno chýbné stávající heslo uživatele.", "Změna hesla");
            break;
            
            case InvalidNewPassword:
                mainFrame.showErrorMessage("Musí být uvedeno nové heslo, které má minimální délku 4 znaky", "Změna hesla");
            break;
            
            case NewPasswordsNotMatch:
                mainFrame.showErrorMessage("Nové heslo se musí shodovat s heslem v kolonce \"Ověření hesla\".", "Změna hesla");
            break;
            
            case ChangePasswordFail:
                mainFrame.showErrorMessage("Při změně hesla došlo k chybě k neočekávané chybě!", "Změna hesla");
            break;

            case ChangePasswordSuccesful:
                mainFrame.showErrorMessage("Změna hesla proběhla úspěšně.", "Změna hesla");
            break;
        }
        
        return result;
    }

    /* ================= LoginDialog ================= */
    /**
     * Metoda pro overeni prihlasovacich udaju a nastaveni prihlaseneho uzivatele, pokud jsou udaje vporadku
     * @param password
     * @param username
     * @return
     */
    /*public EnumLoginResult login(String password, String username){
        EnumLoginResult result = getControler().login(password, username);

        switch(result){
            case WrongLoginData:
                mainFrame.showErrorMessage("Nesprávné uživatelské jméno nebo heslo", "Chyba");
            break;
            
            case NotManager:
                mainFrame.showErrorMessage("Uživatel nemá oprávnění pro přístup do systému.\n" +
                            "Přihlásit se smějí jen uživatele s rolí \"manager\".", "Chyba");
            break;

            case ConnectionFail:
                mainFrame.showErrorMessage("Nelze navázat spojení se serverem.", "Chyba komunikace");
            break;

            case ConfigFileNotFound:
                mainFrame.showErrorMessage("Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba");
            break;
        }

        return result;
    }*/

    /* ================= MenuForm ================= */
    /**
     * Metoda pro vytvoreni a editaci menu
     * @param name
     * @param date
     * @param username
     * @param menuId Pokud je -1 vytvori se novy zaznam, jinak editace prislusneho menu
     * @return
     */
    public MenuResult insertMenu(String name, Date date, String username, int menuId){
        MenuResult result = getControler().insertMenu(name, date, username, menuId);

        switch(result){
            case EmptyName:
                mainFrame.showErrorMessage("Musí být uveden název nového Menu.", "Menu");
            break;
            
            case InvalidDateFormat:
                mainFrame.showErrorMessage("Musí být uvedeno datum ve formatu dd.MM.yyyy.", "Menu");
            break;
            
            case NameExists:
                mainFrame.showErrorMessage("Záznam nemohl být uložen, protože záznam se stejným názvem Menu je již uložen.", "Menu");
            break;
            
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
     * Metoda pro vymazani menu
     * @param menuId
     * @return
     */
    public MenuResult deleteMenu(int menuId) {
        MenuResult result = getControler().deleteMenu(menuId);
        
        switch(result){
            case Fail:
                mainFrame.showErrorMessage("Záznam nebyl smazán.", "Menu");
            break;

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
     * 
     * @param title
     * @param menuId
     * @return
     */
    public PrintMenuResult printMenu(String title, int menuId){
        PrintMenuResult result = getControler().printMenu(title, menuId);

        switch(result){
            case TemplateNotFound:
                mainFrame.showErrorMessage("Nebyla nalezena tisková šablona pro tisk menu.", "Tisková sestava nenalezena");
            break;

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
     * Metoda vraci vsechny menu
     * @return
     */
    public Object[][] getMenus() {
        return getControler().getMenus();
    }

    /* ================= MenuItemForm ================= */
    /**
     * Metoda pro vytvoreni a editaci polozky menu
     * @param name
     * @param price Kladne desetine cislo
     * @param quantity
     * @param menuItemType
     * @param avaible
     * @param menuItemId Pokud je -1 vytvori se novy zaznam, jinak editace
     * @return
     */
    public MenuItemResult insertMenuItem(String name, String price, String quantity, String menuItemType, int avaible, int menuItemId) {
        MenuItemResult result = getControler().insertMenuItem(name, price, quantity, menuItemType, avaible, menuItemId);

        switch(result){
            case EmptyName:
                mainFrame.showErrorMessage("Údaj o názvu položky menu musí být vyplněn.", "Položka menu");
            break;

            case InvalidPrice:
                mainFrame.showErrorMessage("Údaj o ceně položky menu musí být vyplněn.", "Položka menu");
            break;

            case InvalidQuantity:
                mainFrame.showErrorMessage("Musí být uvedeno množství.", "Položky menu");
            break;

            case NameExists:
                mainFrame.showErrorMessage("Položka menu nemohla být uložena, protože položka se stejným názvem již existuje.", "Položka menu");
            break;

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
     * Metoda pro vymazani polozky menu
     * @param menuItemId
     * @return
     */
    public MenuItemResult deleteMenuItem(int menuItemId) {
        MenuItemResult result = getControler().deleteMenuItem(menuItemId);
        
        switch(result){
            case Fail:
                mainFrame.showErrorMessage("Záznam nebyl smazán.", "Položka menu");
            break;

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
     * Metoda vraci vsechny polozky menu
     * @return
     */
    public Object[][] getMenuItems() {
        return getControler().getMenuItems();
    }

    /* ================= MenuItemTypeDialog ================= */
    /**
     * Metoda pro vytvoreni a editaci typu polozky menu
     * @param name
     * @param menuItemTypeId Pokud je -1 vytvori se novy zaznam, jinak editace
     * @return
     */
    public MenuItemTypeResult insertMenuItemType(String name, int menuItemTypeId) {
        MenuItemTypeResult result = getControler().insertMenuItemType(name, menuItemTypeId);

        switch(result){
            case EmptyName:
                mainFrame.showErrorMessage("Musí být uveden název nové kategorie.", "Kategorie položek menu");
            break;
            
            case NameExists:
                mainFrame.showErrorMessage("Záznam nemohl být uložen, protože záznam se stejným názvem kategorie již uložen.", "Kategorie položek menu");
            break;

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
     * Metoda pro smazani typu polozky menu
     * @param menuItemTypeId
     * @return
     */
    public MenuItemTypeResult deleteMenuItemType(int menuItemTypeId) {
        MenuItemTypeResult result = getControler().deleteMenuItemType(menuItemTypeId);

        switch(result){
            case Fail:
                mainFrame.showErrorMessage("Záznam nebyl smazán.", "Kategorie položek menu");
            break;

            case HasLinks:
                mainFrame.showErrorMessage("Kategorie nemůže být smazána, protože se na ní odkazují existující položky menu.", "Kategorie položek menu");
            break;

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
     * Metoda vraci vsechny typy polozek menu
     * @return
     */
    Object[][] getMenuItemTypes() {
        return getControler().getMenuItemTypes();
    }

    /**
     * Metoda vraci nazvy typu polozek menu
     * @return
     */
    public String[] getMenuItemTypeNames() {
        return getControler().getMenuItemTypeNames();
    }

    /* ================= MenuMenuItemDialog ================= */
    /**
     * Metoda pro prirazeni polozky menu do prislusneho menu
     * @param menuId Id menu, do ktereho ma byt polozka prirazena
     * @param name Nazev polozky, ktera se ma priradit
     * @return
     */
    MenuMenuItemResult insertMenuMenuItem(int menuId, String name) {
        MenuMenuItemResult result = getControler().insertMenuMenuItem(menuId, name);

        switch(result){
            case Exists:
                mainFrame.showErrorMessage("Daná položka menu se již v menu nachází.", "Přiřazení položky menu");
            break;

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
     * Metoda vraci nazev menu podle jeho Id
     * @param menuId
     * @return
     */
    public MenuMenuItemResult deleteMenuMenuItem(int menuId, String menuItemName) {
        MenuMenuItemResult result = getControler().deleteMenuMenuItem(menuId, menuItemName);

        switch(result){
            case Fail:
                mainFrame.showErrorMessage("Záznam nebyl smazán.", "Přiřazení položky menu");
            break;

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
     * Metoda vraci nazev menu podle jeho Id
     * @param menuId
     * @return
     */
    public String getMenuNameById(int menuId) {
        return getControler().getMenuNameById(menuId);
    }

    /**
     * Metoda vraci vsechny nazvy polozek menu
     * @return
     */
    public String[] getMenuItemNames() {
        return getControler().getMenuItemNames();
    }

    /**
     * Metoda vraci polozky menu podle Id menu, do ktereho jsou prirazeny
     * @param menuId
     * @return
     */
    public Object[][] getMenuItemsByMenuId(int menuId) {
        return getControler().getMenuItemsByMenuId(menuId);
    }

    /* ================= RecipeDialog ================= */
    /**
     * Metoda pro vytvoreni a editaci receptury
     * @param menuItemId
     * @param quantity Kladne desetine cislo
     * @param material
     * @param fromUnitType
     * @param usedMaterialId Pokud je -1 vytvori se novy zaznam, jinak editace
     * @return
     */
    public RecipeDialogResult insertUsedMaterial(int menuItemId, String quantity, String material, String fromUnitType, int usedMaterialId) {
        RecipeDialogResult result = getControler().insertUsedMaterial(menuItemId, quantity, material, fromUnitType, usedMaterialId);

        switch(result){
            case Fail:
                mainFrame.showErrorMessage("Záznam nemohl být aktualizován.", "Receptura");
            break;

            case InvalidQuantity:
                mainFrame.showErrorMessage("Musí být určeno množství použité suroviny.", "Receptura");
            break;

            case InvalidMaterial:
                mainFrame.showErrorMessage("Musí být vybrána surovina.", "Receptura");
            break;

            case InvalidUnitType:
                mainFrame.showErrorMessage("Musí být vybrána měrná jednotka.", "Receptura");
            break;

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
     * Metoda pro odstraneni receptury
     * @param usedMaterialId
     * @return
     */
    public RecipeDialogResult deleteUsedMaterial(int usedMaterialId) {
        RecipeDialogResult result = getControler().deleteUsedMaterial(usedMaterialId);

        switch(result){
            case Fail:
                mainFrame.showErrorMessage("Záznam nebyl smazán.", "Receptura");
            break;

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
     * Metoda vraci nazvy typu surovin
     * @return
     */
    public String[] getMaterialTypeNames() {
        return getControler().getMaterialTypeNames();
    }

    /**
     * Metoda vraci suroviny podle Id polozky menu
     * @param menuItemId
     * @return
     */
    public Object[][] getUsedMaterialsByMenuItem(int menuItemId) {
        return getControler().getUsedMaterialsByMenuItem(menuItemId);
    }

    /**
     * Metoda vraci nazev polozky menu podle Id
     * @param menuItemId
     * @return
     */
    public String getMenuItemNameById(int menuItemId) {
        return getControler().getMenuItemNameById(menuItemId);
    }

    /**
     * Metoda vraci nazvy typu surovin podle nazvu suroviny
     * @param material
     * @return
     */
    public String getMaterialTypeNameByMaterialName(String material) {
        return getControler().getMaterialTypeNameByMaterialName(material);
    }

    /**
     * Metoda vraci zkratky mernych jednotek
     * @return
     */
    public String[] getUnitTypeAbbrs() {
        return getControler().getUnitTypeAbbrs();
    }

    /* ================= TableDialog ================= */
    /**
     * Metoda pro vytvore a editaci stolu
     * @param tableNS Cislo stolu
     * @param placesS Pocet mistu u stolu
     * @param tableId Pokud je -1 vytvori se novy zaznam, jinak editace
     * @return
     */
    public TableDialogResult insertTable(String tableN, String places, int tableId) {
        TableDialogResult result = getControler().insetTable(tableN, places, tableId);

        switch(result){
            case Exists:
                mainFrame.showErrorMessage("Záznam nemohl být uložen, protože záznam se stejným číslem stolu je již uložen.", "Stůl");
            break;

            case InvalidTableNum:
                mainFrame.showErrorMessage("Musí být uvedeno číslo vkládaného stolu.", "Stůl");
            break;

            case InvalidPlaces:
                mainFrame.showErrorMessage("Musí být uveden počet míst k sezení pro daný stůl.", "Stůl");
            break;

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
     * Metoda pro odtraneni stolu
     * @param tableId
     * @return
     */
    public TableDialogResult deleteTable(int tableId) {
        TableDialogResult result = getControler().deleteTable(tableId);

        switch(result){
            case Fail:
                mainFrame.showErrorMessage("Záznam nebyl smazán.", "Stůl");
            break;

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
     * Metoda vraci vsechny stoly
     * @return
     */
    public Object[][] getTables() {
        return getControler().getTables();
    }

    /* ================= UserForm ================= */
    /**
     * Metoda pro vytvoreni a editaci uzivatelu
     * @param name
     * @param surname
     * @param pin
     * @param username
     * @param passwd
     * @param userId Pokud je -1 vytvori se novy zaznam, jinak editace
     * @return
     */
    public UserFormResult insertUser(String name, String surname, String pin, String username, String passwd, int userId) {
        UserFormResult result = getControler().insertUser(name, surname, pin, username, passwd, userId);

        switch(result){
            case Exists:
                mainFrame.showErrorMessage("Záznam nemohl být uložen, protože záznam se stejným uživatelským jménem nebo PID číslem je již uložen.", "Zaměstnanci");
            break;

            case InvalidName:
                mainFrame.showErrorMessage("Musí být uvedeno křestní jméno uživatele.", "Nový uživatel");
            break;

            case InvalidSurname:
                mainFrame.showErrorMessage("Musí být uvedeno příjmení uživatele.", "Nový uživatel");
            break;

            case InvalidPin:
                mainFrame.showErrorMessage("Musí být uvedeno personální identifikační číslo (PIN) uživatele.", "Nový uživatel");
            break;

            case InvalidUsername:
                mainFrame.showErrorMessage("Musí být uvedeno uživatelské jméno nového uživatele.", "Nový uživatel");
            break;

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
     * Metoda pro vymazani uzivatele
     * @param userId
     * @return
     */
    public UserFormResult deleteUser(int userId) {
        UserFormResult result = getControler().deleteUser(userId);

        switch(result){
            case Fail:
                mainFrame.showErrorMessage("Záznam nebyl smazán.", "Uživatel");
            break;

            case InUse:
                mainFrame.showErrorMessage("Není možné odstranit záznam právě přihlášeného uživatele.", "Uživatel");
            break;

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
     * Metoda vraci vsechny uzivatele
     * @return
     */
    public Object[][] getUsers() {
        return getControler().getUsers();
    }

    /* ================= UserRoleDialog ================= */
    /**
     * Metoda pro upravu roli u daneho uzivatele
     * @param userId
     * @param newRoles
     * @return
     */
    public void updateUserRoles(int userId, boolean[] newRoles) {
        getControler().updateUserRoles(userId, newRoles);
    }

    /**
     * Metoda vraci uzivatele podle Id
     * @param userId
     * @return
     */
    public User getUserById(int userId) {
        return getControler().getUserById(userId);
    }

    /**
     * Metoda vraci role daneho uzivatele
     * @param userId
     * @return
     */
    public boolean[] getUserRoles(int userId) {
        return getControler().getUserRoles(userId);
    }

    /* ================= Other methods ================= */
    /**
     * Metoda vraci vsechny uzivatelka jmena
     * @return
     */
    public String[] getUsernames() {
        return getControler().getUsernames();
    }

    /**
     * Metoda vraci aktualne prihlaseneho uzivatele
     * @return
     */
    public User getLoggedUser(){
        return getControler().getLoggedUser();
    }

    /**
     * Metoda vraci prava aktualne prihlaseneho uzivatele
     * @return
     */
    public String[] getRights(){
        return getControler().getRights();
    }

    /**
     * Metoda vraci hodnotu vychoziho hesla
     * @return
     */
    public String getDefaultPassword() {
        return getControler().getDefaultPassword();
    }

    /**
     * Metoda vraci menu uvedenou v konfiguracnim souboru
     * @return
     */
    public String getMoneyFromConfig(){
        return getControler().getMoneyFromConfig();
    }

    private ManagerController getControler() {
        if(controller == null){
            controller = ManagerController.getInstance();
        }

        return controller;
    }
}
