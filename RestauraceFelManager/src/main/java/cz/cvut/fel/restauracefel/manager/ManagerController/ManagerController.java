/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.manager.ManagerController;

import cz.cvut.fel.restauracefel.library.Enums.EnumLoginResult;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.library.interfaces.IModuleInteface;
import cz.cvut.fel.restauracefel.library.library_gui.PrintDialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import cz.cvut.fel.restauracefel.manager.managerModel.ManagerModelController;
import cz.cvut.fel.restauracefel.manager.manager_gui.ManagerViewController;
import cz.cvut.fel.restauracefel.manager_service.ServiceFacade;
import cz.cvut.fel.restauracefel.library.service.CodeList;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;

/**
 *
 * @author Lukáš Viezán
 */
public class ManagerController implements IModuleInteface {

    private static final ManagerController instance = new ManagerController( );

    private ManagerViewController view = null;
    private ManagerModelController model = null;

    private ManagerController( ) {
        view = ManagerViewController.getInstance( );
        model = ManagerModelController.getInstance();
    }

    public static ManagerController getInstance( ) {
        return instance;
    }

    public void run( User user, String[ ] prava ) {
        model.setLoggedUser(user);
        model.setRights(prava);

        view.run( );
    }

    public boolean isActive( ) {
        return view.isActive( );
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
        int userId = getLoggedUser().getUserId();

        if(oldPassword.isEmpty()){
            return ChangePasswordResult.EmptyOldPassword;
        }
        if(newPassword.isEmpty() || newPassword.length() < 4){
            return ChangePasswordResult.InvalidNewPassword;
        }
        if(!newPassword.equals(validPassword)){
            return ChangePasswordResult.NewPasswordsNotMatch;
        }

        try {
            if(!ServiceFacade.getInstance().isValidOldPasswd(userId, oldPassword)){
                return ChangePasswordResult.WrongOldPassword;
            }

            if(ServiceFacade.getInstance().updateUserPassword(userId, newPassword)){
                return ChangePasswordResult.ChangePasswordSuccesful;
            }else{
                return ChangePasswordResult.ChangePasswordFail;
            }
        } catch (FileNotFoundException fnfe){
            return ChangePasswordResult.ConfigFileNotFound;
        } catch (Exception ex){
            return ChangePasswordResult.ConnectionFail;
        }
    }

    /* ================= LoginDialog ================= */
    /**
     * Metoda pro overeni prihlasovacich udaju a nastaveni prihlaseneho uzivatele, pokud jsou udaje vporadku
     * @param password
     * @param username
     * @return
     */
    /*public EnumLoginResult login(String password, String username) {
        try {
            if(!ServiceFacade.getInstance().isValidUser(username, password)){
                return EnumLoginResult.WrongLoginData;
            }

            int roleManagerId = ServiceFacade.getInstance().getRoleByName("manager").getRoleId();
            User user = ServiceFacade.getInstance().getUserByUsername(username);

            if(ServiceFacade.getInstance().isUserRole(user.getUserId(), roleManagerId)){
                model.setLoggedUser(user);
                return EnumLoginResult.LoginSuccesful;
            }else{
                return EnumLoginResult.NotManager;
            }
        } catch (FileNotFoundException fnfe){
            return EnumLoginResult.ConfigFileNotFound;
        } catch (Exception ex){
            return EnumLoginResult.ConnectionFail;
        }
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
    public MenuResult insertMenu(String name, Date date, String username, int menuId) {
        if(name.isEmpty()) return MenuResult.EmptyName;
        if(date == null) return MenuResult.InvalidDateFormat;

        try {
            int userId = ServiceFacade.getInstance().getUserByUsername(username).getUserId();

            if(menuId == -1){
                if(ServiceFacade.getInstance().createMenu(userId, name, date)) return MenuResult.Succesful;
            }else{
                if(ServiceFacade.getInstance().updateMenu(menuId, userId, name, date)) return MenuResult.Succesful;
            }

            return MenuResult.NameExists;
        } catch (FileNotFoundException fnfe){
            return MenuResult.ConfigFileNotFound;
        } catch (Exception ex){
            return MenuResult.ConnectionFail;
        }
    }

    /**
     * Metoda pro vymazani menu
     * @param menuId
     * @return
     */
    public MenuResult deleteMenu(int menuId) {
        try {
            if (ServiceFacade.getInstance().deleteMenu(menuId)) {
                return MenuResult.Succesful;
            }

            return MenuResult.Fail;
        } catch (Exception ex) {
            return MenuResult.Fail;
        }
    }

    public PrintMenuResult printMenu(String title, int menuId) {
        Map<String, Object> params = new HashMap<String, Object>();

        try {
            ConfigParser config = new ConfigParser();
            String printingFilePath = config.getTemplatesDir() + System.getProperty("file.separator") + "menu.jasper";

            File printFile = new File(printingFilePath);
            if (!printFile.exists()){
                return PrintMenuResult.TemplateNotFound;
            }

            params.put("title", title);
            params.put("currency", config.getMoney());

            PrintDialog printDialog = new PrintDialog(null, true, params, printFile);
            printDialog.setCollection(ServiceFacade.getInstance().getMenuItemsByMenuList(menuId));
            printDialog.setVisible(true);

            return PrintMenuResult.Succesful;
        } catch (FileNotFoundException ex){
            return PrintMenuResult.ConfigFileNotFound;
        } catch (Exception ex) {
            return PrintMenuResult.ConnectionFail;
        }
    }
    
    /**
     * Metoda vraci vsechny menu
     * @return
     */
    public Object[][] getMenus() {
        try {
            return ServiceFacade.getInstance().getMenus();
        } catch (Exception ex) {
            return null;
        }
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
        if(name.isEmpty()) return MenuItemResult.EmptyName;
        if(price.isEmpty()) return MenuItemResult.InvalidPrice;
        if(quantity.isEmpty()) return MenuItemResult.InvalidQuantity;

        double priceD;

        try {
            priceD = Double.parseDouble(price);
        } catch(Exception ex){
            return MenuItemResult.InvalidPrice;
        }
        if(priceD < 0) return  MenuItemResult.InvalidPrice;

        try {
            int menuItemTypeId = ServiceFacade.getInstance().getMenuItemTypeByName(menuItemType).getMenuItemTypeId();

            if(menuItemId == -1){
                if(ServiceFacade.getInstance().createMenuItem(name, priceD, quantity, avaible, menuItemTypeId)) return MenuItemResult.Succesful;
            }else{
                if(ServiceFacade.getInstance().updateMenuItem(menuItemId, name, priceD, quantity, avaible, menuItemTypeId)) return MenuItemResult.Succesful;
            }

            return MenuItemResult.NameExists;
        } catch (FileNotFoundException fnfe){
            return MenuItemResult.ConfigFileNotFound;
        } catch (Exception ex){
            return MenuItemResult.ConnectionFail;
        }
    }

    /**
     * Metoda pro vymazani polozky menu
     * @param menuItemId
     * @return
     */
    public MenuItemResult deleteMenuItem(int menuItemId) {
        try {
            if(ServiceFacade.getInstance().deleteMenuItem(menuItemId)) return MenuItemResult.Succesful;

            return MenuItemResult.Fail;
        } catch(Exception ex){
            return MenuItemResult.Fail;
        }
    }

    /**
     * Metoda vraci vsechny polozky menu
     * @return
     */
    public Object[][] getMenuItems() {
        try {
            return ServiceFacade.getInstance().getMenuItems();
        } catch(Exception ex) {
            return null;
        }
    }

    /* ================= MenuItemTypeDialog ================= */
    /**
     * Metoda pro vytvoreni a editaci typu polozky menu
     * @param name
     * @param menuItemTypeId Pokud je -1 vytvori se novy zaznam, jinak editace
     * @return
     */
    public MenuItemTypeResult insertMenuItemType(String name, int menuItemTypeId) {
        if(name.isEmpty()) return MenuItemTypeResult.EmptyName;

        try {
            if(menuItemTypeId == -1){
                if(ServiceFacade.getInstance().createMenuItemType(name)) return MenuItemTypeResult.Succesful;
            }else{
                if(ServiceFacade.getInstance().updateMenuItemType(menuItemTypeId, name)) return MenuItemTypeResult.Succesful;
            }

            return MenuItemTypeResult.NameExists;
        } catch (FileNotFoundException fnfe){
            return MenuItemTypeResult.ConfigFileNotFound;
        } catch (Exception ex){
            return MenuItemTypeResult.ConnectionFail;
        }
    }

    /**
     * Metoda pro smazani typu polozky menu
     * @param menuItemTypeId
     * @return
     */
    public MenuItemTypeResult deleteMenuItemType(int menuItemTypeId) {
        try {
            if(!ServiceFacade.getInstance().isDeletableMenuItemType(menuItemTypeId)) return MenuItemTypeResult.HasLinks;

            if(ServiceFacade.getInstance().deleteMenuItemType(menuItemTypeId)) return MenuItemTypeResult.Succesful;

            return MenuItemTypeResult.Fail;
        } catch (FileNotFoundException fnfe){
            return MenuItemTypeResult.ConfigFileNotFound;
        } catch (Exception ex){
            return MenuItemTypeResult.ConnectionFail;
        }
    }

    /**
     * Metoda vraci vsechny typy polozek menu
     * @return
     */
    public Object[][] getMenuItemTypes() {
        try {
            return ServiceFacade.getInstance().getMenuItemTypes();
        } catch(Exception ex) {
            return null;
        }
    }

    /**
     * Metoda vraci nazvy typu polozek menu
     * @return
     */
    public String[] getMenuItemTypeNames() {
        try {
            return ServiceFacade.getInstance().getMenuItemTypeNames();
        } catch(Exception ex) {
            return null;
        }
    }

    /* ================= MenuMenuItemDialog ================= */
    /**
     * Metoda pro prirazeni polozky menu do prislusneho menu
     * @param menuId Id menu, do ktereho ma byt polozka prirazena
     * @param name Nazev polozky, ktera se ma priradit
     * @return
     */
    public MenuMenuItemResult insertMenuMenuItem(int menuId, String name) {
        try {
            int menuItemId = ServiceFacade.getInstance().getMenuItemByName(name).getMenuItemId();

            if(ServiceFacade.getInstance().createMenuMenuItem(menuId, menuItemId)) return MenuMenuItemResult.Succesful;

            return MenuMenuItemResult.Exists;
        } catch (FileNotFoundException fnfe){
            return MenuMenuItemResult.ConfigFileNotFound;
        } catch (Exception ex){
            return MenuMenuItemResult.ConnectionFail;
        }
    }

    /**
     * Metoda pro odstraneni polozky menu z daneho menu
     * @param menuId
     * @param menuItemName
     * @return
     */
    public MenuMenuItemResult deleteMenuMenuItem(int menuId, String menuItemName) {
        try {
            int menuItemId = ServiceFacade.getInstance().getMenuItemByName(menuItemName).getMenuItemId();

            if(ServiceFacade.getInstance().deleteMenuMenuItem(menuId, menuItemId)) return MenuMenuItemResult.Succesful;

            return MenuMenuItemResult.Fail;
        } catch (FileNotFoundException fnfe){
            return MenuMenuItemResult.ConfigFileNotFound;
        } catch (Exception ex){
            return MenuMenuItemResult.ConnectionFail;
        }
    }

    /**
     * Metoda vraci nazev menu podle jeho Id
     * @param menuId
     * @return
     */
    public String getMenuNameById(int menuId) {
        try {
            return ServiceFacade.getInstance().getMenuById(menuId).getName();
        } catch(Exception ex) {
            return null;
        }
    }

    /**
     * Metoda vraci vsechny nazvy polozek menu
     * @return
     */
    public String[] getMenuItemNames() {
        try {
            return ServiceFacade.getInstance().getMenuItemNames();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Metoda vraci polozky menu podle Id menu, do ktereho jsou prirazeny
     * @param menuId
     * @return
     */
    public Object[][] getMenuItemsByMenuId(int menuId) {
        try {
            return ServiceFacade.getInstance().getMenuItemsByMenu(menuId);
        } catch (Exception ex) {
            return null;
        }
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
    public RecipeDialogResult insertUsedMaterial(int menuItemId, String quantityS, String material, String fromUnitType, int usedMaterialId) {
        if(material == null || material.isEmpty()) return RecipeDialogResult.InvalidMaterial;
        if(material == null || fromUnitType.isEmpty()) return RecipeDialogResult.InvalidUnitType;

        double quantity;
        if(quantityS.isEmpty()) return RecipeDialogResult.InvalidQuantity;
        try {
            quantity = Double.parseDouble(quantityS);
        } catch (Exception e) {
            return RecipeDialogResult.InvalidQuantity;
        }
        if(quantity <= 0){
            return RecipeDialogResult.InvalidQuantity;
        }

        try {
            int materialId = ServiceFacade.getInstance().getMaterialByName(material).getMaterialId();
            int fromUnitTypeId = ServiceFacade.getInstance().getUnitTypeByAbbr(fromUnitType).getUnitTypeId();
            int toUnitTypeId = ServiceFacade.getInstance().getMaterialByID(materialId).getUnitType().getUnitTypeId();

            quantity = CodeList.transfer(fromUnitTypeId, toUnitTypeId, quantity);

            if(usedMaterialId == -1){
                ServiceFacade.getInstance().createUsedMaterial(materialId, menuItemId, quantity);
                return RecipeDialogResult.Succesful;
            }else{
                if(ServiceFacade.getInstance().updateUsedMaterial(usedMaterialId, materialId, menuItemId, quantity)) return RecipeDialogResult.Succesful;
            }

            return RecipeDialogResult.Fail;
        } catch (FileNotFoundException fnfe){
            return RecipeDialogResult.ConfigFileNotFound;
        } catch (Exception ex){
            return RecipeDialogResult.ConnectionFail;
        }
    }

    /**
     * Metoda pro odstraneni receptury
     * @param usedMaterialId
     * @return
     */
    public RecipeDialogResult deleteUsedMaterial(int usedMaterialId) {
        try {
            if(ServiceFacade.getInstance().deleteUsedMaterial(usedMaterialId)) return RecipeDialogResult.Succesful;

            return RecipeDialogResult.Fail;
        } catch (FileNotFoundException fnfe){
            return RecipeDialogResult.ConfigFileNotFound;
        } catch (Exception ex){
            return RecipeDialogResult.ConnectionFail;
        }
    }

    /**
     * Metoda vraci nazvy typu surovin
     * @return
     */
    public String[] getMaterialTypeNames() {
        try {
            return ServiceFacade.getInstance().getMaterialTypeNames();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Metoda vraci suroviny podle Id polozky menu
     * @param menuItemId
     * @return
     */
    public Object[][] getUsedMaterialsByMenuItem(int menuItemId) {
        try {
            return ServiceFacade.getInstance().getUsedMaterialsByMenuItem(menuItemId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Metoda vraci nazev polozky menu podle Id
     * @param menuItemId
     * @return
     */
    public String getMenuItemNameById(int menuItemId) {
        try {
            return ServiceFacade.getInstance().getMenuItemById(menuItemId).getName();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Metoda vraci nazvy typu surovin podle nazvu suroviny
     * @param material
     * @return
     */
    public String getMaterialTypeNameByMaterialName(String material) {
        try {
            return ServiceFacade.getInstance().getMaterialByName(material).getMaterialType().getName();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Metoda vraci zkratky mernych jednotek
     * @return
     */
    public String[] getUnitTypeAbbrs() {
        try {
            return ServiceFacade.getInstance().getUnitTypeAbbrs();
        } catch (Exception e) {
            return null;
        }
    }

    /* ================= TableDialog ================= */
    /**
     * Metoda pro vytvore a editaci stolu
     * @param tableNS Cislo stolu
     * @param placesS Pocet mistu u stolu
     * @param tableId Pokud je -1 vytvori se novy zaznam, jinak editace
     * @return
     */
    public TableDialogResult insetTable(String tableNS, String placesS, int tableId) {
        int tableN, places;
        try {
            tableN = Integer.valueOf(tableNS);
        } catch (Exception e) {
            return TableDialogResult.InvalidTableNum;
        }
        try {
            places = Integer.valueOf(placesS);
        } catch (Exception e) {
            return TableDialogResult.InvalidPlaces;
        }

        try {
            if(tableId == -1){
                if(ServiceFacade.getInstance().createTable(tableN, places)) return TableDialogResult.Succesful;
            }else{
                if(ServiceFacade.getInstance().updateTable(tableId, tableN, places)) return TableDialogResult.Succesful;
            }

            return TableDialogResult.Exists;
        } catch (FileNotFoundException fnfe){
            return TableDialogResult.ConfigFileNotFound;
        } catch (Exception ex){
            return TableDialogResult.ConnectionFail;
        }
    }

    /**
     * Metoda pro odtraneni stolu
     * @param tableId
     * @return
     */
    public TableDialogResult deleteTable(int tableId) {
        try {
            if(ServiceFacade.getInstance().deleteTable(tableId)) return TableDialogResult.Succesful;

            return TableDialogResult.Fail;
        } catch (FileNotFoundException fnfe){
            return TableDialogResult.ConfigFileNotFound;
        } catch (Exception ex){
            return TableDialogResult.ConnectionFail;
        }
    }

    /**
     * Metoda vraci vsechny stoly
     * @return
     */
    public Object[][] getTables() {
        try {
            return ServiceFacade.getInstance().getTables();
        } catch (Exception e) {
            return null;
        }
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
        if(name.isEmpty()) return UserFormResult.InvalidName;
        if(surname.isEmpty()) return UserFormResult.InvalidSurname;
        if(pin.isEmpty()) return UserFormResult.InvalidPin;
        if(username.isEmpty()) return UserFormResult.InvalidUsername;

        try {
            if(userId == -1){
                if(ServiceFacade.getInstance().createUser(name, surname, pin, username, passwd)) return UserFormResult.Succesful;
            }else{
                if(ServiceFacade.getInstance().updateUser(userId, name, surname, pin, username)) return UserFormResult.Succesful;
            }

            return UserFormResult.Exists;
        } catch (FileNotFoundException fnfe){
            return UserFormResult.ConfigFileNotFound;
        } catch (Exception ex){
            return UserFormResult.ConnectionFail;
        }
    }

    /**
     * Metoda pro vymazani uzivatele
     * @param userId
     * @return
     */
    public UserFormResult deleteUser(int userId) {
        if(getLoggedUser().getUserId() == userId) return UserFormResult.InUse;

        try {
            if(ServiceFacade.getInstance().deleteUser(userId)) return UserFormResult.Succesful;

            return UserFormResult.Fail;
        } catch (FileNotFoundException fnfe){
            return UserFormResult.ConfigFileNotFound;
        } catch (Exception ex){
            return UserFormResult.ConnectionFail;
        }
    }

    /**
     * Metoda vraci vsechny uzivatele
     * @return
     */
    public Object[][] getUsers() {
        try {
            return ServiceFacade.getInstance().getUsers();
        } catch (Exception e) {
            return null;
        }
    }

    /* ================= UserRoleDialog ================= */
    /**
     * Metoda pro upravu roli u daneho uzivatele
     * @param userId
     * @param newRoles
     * @return
     */
    public void updateUserRoles(int userId, boolean[] newRoles) {
        boolean[] currentRoles = getUserRoles(userId);

        try {
            for (int i = 0; i < newRoles.length; i ++) {
                if (newRoles[i] == currentRoles[i]) continue;
                if (newRoles[i] == true && currentRoles[i] == false){
                    ServiceFacade.getInstance().createUserRole(userId, i + 1);
                }
                if (newRoles[i] == false && currentRoles[i] == true){
                    ServiceFacade.getInstance().deleteUserRole(userId, i + 1);
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * Metoda vraci uzivatele podle Id
     * @param userId
     * @return
     */
    public User getUserById(int userId) {
        try {
            return ServiceFacade.getInstance().getUserById(userId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Metoda vraci role daneho uzivatele
     * @param userId
     * @return
     */
    public boolean[] getUserRoles(int userId) {
        try {
            return ServiceFacade.getInstance().getUserRoles(userId);
        } catch (Exception e) {
            return null;
        }
    }

    /* ================= Other methods ================= */
    /**
     * Metoda vraci vsechny uzivatelka jmena
     * @return
     */
    public String[] getUsernames() {
        try {
            return ServiceFacade.getInstance().getUserUsernames();
        } catch(Exception ex) {
            return null;
        }
    }

    /**
     * Metoda vraci aktualne prihlaseneho uzivatele
     * @return
     */
    public User getLoggedUser() {
        return model.getLoggedUser();
    }

    /**
     * Metoda vraci prava aktualne prihlaseneho uzivatele
     * @return
     */
    public String[] getRights() {
        return model.getRights();
    }

    /**
     * Metoda vraci hodnotu vychoziho hesla
     * @return
     */
    public String getDefaultPassword() {
        try {
            return ServiceFacade.getInstance().getDefaultPasswd();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Metoda vraci menu uvedenou v konfiguracnim souboru
     * @return
     */
    public String getMoneyFromConfig() {
        try {
            ConfigParser config = new ConfigParser();
            return config.getMoney();
        } catch (FileNotFoundException ex) {
            return null;
        }
    }
}
