package cz.cvut.fel.restauracefel.manager_service;

import cz.cvut.fel.restauracefel.hibernate.Material;
import cz.cvut.fel.restauracefel.hibernate.MaterialType;
import cz.cvut.fel.restauracefel.hibernate.Menu;
import cz.cvut.fel.restauracefel.hibernate.MenuItem;
import cz.cvut.fel.restauracefel.hibernate.MenuItemType;
import cz.cvut.fel.restauracefel.hibernate.Role;
import cz.cvut.fel.restauracefel.hibernate.Table;
import cz.cvut.fel.restauracefel.hibernate.UnitType;
import cz.cvut.fel.restauracefel.hibernate.UsedMaterial;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.hibernate.UserRole;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.Date;
import java.util.List;
import cz.cvut.fel.restauracefel.server.service.controllers.MaterialController;
import cz.cvut.fel.restauracefel.server.service.controllers.MaterialTypeController;
import cz.cvut.fel.restauracefel.server.service.controllers.MenuController;
import cz.cvut.fel.restauracefel.server.service.controllers.MenuItemController;
import cz.cvut.fel.restauracefel.server.service.controllers.MenuItemTypeController;
import cz.cvut.fel.restauracefel.server.service.controllers.MenuMenuItemController;
import cz.cvut.fel.restauracefel.server.service.controllers.RoleController;
import cz.cvut.fel.restauracefel.server.service.controllers.TableController;
import cz.cvut.fel.restauracefel.server.service.controllers.UnitTypeController;
import cz.cvut.fel.restauracefel.server.service.controllers.UsedMaterialController;
import cz.cvut.fel.restauracefel.server.service.controllers.UserController;
import cz.cvut.fel.restauracefel.server.service.controllers.UserRoleController;

/**
 *
 * @author Jarda
 */
public class ServiceFacadeManager extends UnicastRemoteObject implements IServiceFacadeManager {

    //singleton
    protected static ServiceFacadeManager instance = null;

    public ServiceFacadeManager() throws RemoteException {
        super();
    }

    public void initServiceFacadeRMI(Registry reg) throws java.net.UnknownHostException, RemoteException, FileNotFoundException {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        String name = "ServiceFacadeManager";
        ConfigParser config = new ConfigParser();
        InetAddress inetAddress = InetAddress.getByName(config.getServerIP());
        //Stub - tato fasada se jevi klientovi jako lokalni
        IServiceFacadeManager facade = ServiceFacadeManager.getInstance();
        reg.rebind(name, facade);
        System.out.println("Servisni fasada pro modul MANAGER zaregistrovana pod jmenem \"ServiceFacadeManager\"");
        System.out.println("Pripojeni pres adresu:" + inetAddress.toString() + "\n\n");

    }

    //Vraci instanci tridy ServiceFacadeManager
    public static ServiceFacadeManager getInstance() throws RemoteException {
        if (instance == null) {
            instance = new ServiceFacadeManager();
        }
        return instance;
    }

    //USER
    @Override
    public List getAllUsers() throws RemoteException {
        return UserController.getInstance().getAllUsers();
    }

    @Override
    public boolean createUser(String name, String surname, String pid, String username, String passwd) throws RemoteException {
        return UserController.getInstance().createUser(name, surname, pid, username, passwd);
    }

    @Override
    public boolean isValidUser(String username, String passwd) throws RemoteException {
        return UserController.getInstance().isValidUser(username, passwd);
    }

    @Override
    public User getUserByPID(String pid) throws RemoteException {
        return UserController.getInstance().getUserByPID(pid);
    }

    @Override
    public User getUserByUsername(String userName) throws RemoteException {
        return UserController.getInstance().getUserByUsername(userName);
    }

    @Override
    public User getUserById(int id) throws RemoteException {
        return UserController.getInstance().getUserById(id);
    }

    @Override
    public boolean deleteUser(int userId) throws RemoteException {
        return UserController.getInstance().deleteUser(userId);
    }

    @Override
    public String[] getUserNames() throws RemoteException {
        return UserController.getInstance().getUserNames();
    }

    @Override
    public String[] getUserUsernames() throws RemoteException {
        return UserController.getInstance().getUserUsernames();
    }

    @Override
    public Object[][] getUsers() throws RemoteException {
        return UserController.getInstance().getUsers();
    }

    @Override
    public boolean updateUser(Integer userId, String name, String surname, String pid, String username) throws RemoteException {
        return UserController.getInstance().updateUser(userId, name, surname, pid, username);
    }

    @Override
    public String getDefaultPasswd() throws RemoteException {
        return UserController.getInstance().getDefaultPasswd();
    }

    @Override
    public boolean updateUserPassword(Integer userId, String newPassword) throws RemoteException {
        return UserController.getInstance().updateUserPassword(userId, newPassword);
    }

    @Override
    public boolean isValidOldPasswd(Integer userId, String passwd) throws RemoteException {
        return UserController.getInstance().isValidOldPasswd(userId, passwd);
    }

    //ROLE
    @Override
    public List getAllRoles() throws RemoteException {
        return RoleController.getInstance().getAllRoles();
    }

    @Override
    public String[] getRoleNames() throws RemoteException {
        return RoleController.getInstance().getRoleNames();
    }

    @Override
    public Role getRoleByID(int id) throws RemoteException {
        return RoleController.getInstance().getRoleByID(id);
    }

    @Override
    public Role getRoleByName(String name) throws RemoteException {
        return RoleController.getInstance().getRoleByName(name);
    }

    //MATERIAL    
    @Override
    public boolean createMaterial(String name, int idMaterialType, int idUnitType, String barcode, double minimal,double density, double emptyPackageWeight, double packageCapacity) throws RemoteException {
        return MaterialController.getInstance().createMaterial(name, idMaterialType, idUnitType, barcode, minimal,density, emptyPackageWeight,packageCapacity);
    }

    @Override
    public List getAllMaterials() throws RemoteException {
        return MaterialController.getInstance().getAllMaterials();
    }

    @Override
    public Material getMaterialByID(int id) throws RemoteException {
        return MaterialController.getInstance().getMaterialByID(id);
    }

    @Override
    public Material getMaterialByName(String name) throws RemoteException {
        return MaterialController.getInstance().getMaterialByName(name);
    }

    @Override
    public String[] getMaterialNames() throws RemoteException {
        return MaterialController.getInstance().getMaterialNames();
    }

    @Override
    public Object[][] getMaterials() throws RemoteException {
        return MaterialController.getInstance().getMaterials();
    }

    @Override
    public String[] getMaterialNamesByMaterialType(int materialTypeId) throws RemoteException {
        return MaterialController.getInstance().getMaterialNamesByMaterialType(materialTypeId);
    }

    @Override
    public boolean deleteMaterial(int materialId) throws RemoteException {
        return MaterialController.getInstance().deleteMaterial(materialId);
    }

    @Override
    public boolean updateMaterial(Integer materialId, String name, int idMaterialType, int idUnitType, String barcode, double minimal,double density, double emptyPackageWeight, double packageCapacity) throws RemoteException {
        return MaterialController.getInstance().updateMaterial(materialId, name, idMaterialType, idUnitType, barcode, minimal,density, emptyPackageWeight, packageCapacity);
    }

    @Override
    public boolean isDeletableMaterial(int materialId) throws RemoteException {
        return MaterialController.getInstance().isDeletableMaterial(materialId);
    }

    //MATERIALTYPE
    @Override
    public boolean createMaterialType(String name, String note) throws RemoteException {
        return MaterialTypeController.getInstance().createMaterialType(name, note);
    }

    @Override
    public boolean deleteMaterialType(int materialTypeId) throws RemoteException {
        return MaterialTypeController.getInstance().deleteMaterialType(materialTypeId);
    }

    @Override
    public boolean updateMaterialType(int materialTypeId, String name, String note) throws RemoteException {
        return MaterialTypeController.getInstance().updateMaterialType(materialTypeId, name, note);
    }

    @Override
    public List getAllMaterialTypes() throws RemoteException {
        return MaterialTypeController.getInstance().getAllMaterialTypes();
    }

    @Override
    public MaterialType getMaterialTypeByID(int id) throws RemoteException {
        return MaterialTypeController.getInstance().getMaterialTypeByID(id);
    }

    @Override
    public MaterialType getMaterialTypeByName(String name) throws RemoteException {
        return MaterialTypeController.getInstance().getMaterialTypeByName(name);
    }

    @Override
    public String[] getMaterialTypeNames() throws RemoteException {
        return MaterialTypeController.getInstance().getMaterialTypeNames();
    }

    @Override
    public Object[][] getMaterialTypes() throws RemoteException {
        return MaterialTypeController.getInstance().getMaterialTypes();
    }

    @Override
    public boolean isDeletableMaterialType(int materialTypeId) throws RemoteException {
        return MaterialTypeController.getInstance().isDeletableMaterialType(materialTypeId);
    }

    //UNITTYPE
    @Override
    public boolean createUnitType(String name, String abbreviation, int typeId) throws RemoteException {
        return UnitTypeController.getInstance().createUnitType(name, abbreviation, typeId);
    }

    @Override
    public boolean deleteUnitType(int unitTypeId) throws RemoteException {
        return UnitTypeController.getInstance().deleteUnitType(unitTypeId);
    }

    @Override
    public boolean updateUnitType(int unitTypeId, String name, String abbreviation, int typeId) throws RemoteException {
        return UnitTypeController.getInstance().updateUnitType(unitTypeId, name, abbreviation, typeId);
    }

    @Override
    public List getAllUnitTypes() throws RemoteException {
        return UnitTypeController.getInstance().getAllUnitTypes();
    }

    @Override
    public String[] getUnitTypeAbbrs() throws RemoteException {
        return UnitTypeController.getInstance().getUnitTypeAbbrs();
    }

    @Override
    public String[] getUnitTypeNames() throws RemoteException {
        return UnitTypeController.getInstance().getUnitTypeNames();
    }

    @Override
    public String[] getUnitTypeNamesByTypeId(int typeId) throws RemoteException {
        return UnitTypeController.getInstance().getUnitTypeNamesByTypeId(typeId);
    }

    @Override
    public String[] getUnitTypeAbbrsByTypeId(int typeId) throws RemoteException {
        return UnitTypeController.getInstance().getUnitTypeAbbrsByTypeId(typeId);
    }

    @Override
    public UnitType getUnitTypeByID(int id) throws RemoteException {
        return UnitTypeController.getInstance().getUnitTypeByID(id);
    }

    @Override
    public UnitType getUnitTypeByName(String name) throws RemoteException {
        return UnitTypeController.getInstance().getUnitTypeByName(name);
    }

    @Override
    public UnitType getUnitTypeByAbbr(String abbr) throws RemoteException {
        return UnitTypeController.getInstance().getUnitTypeByAbbr(abbr);
    }

    //MENUITEM
    @Override
    public boolean createMenuItem(String name, double price, String quantity, int isAvailable, int menuItemTypeId) throws RemoteException {
        return MenuItemController.getInstance().createMenuItem(name, price, quantity, isAvailable, menuItemTypeId);
    }

    @Override
    public boolean deleteMenuItem(int menuItemId) throws RemoteException {
        return MenuItemController.getInstance().deleteMenuItem(menuItemId);
    }

    @Override
    public boolean updateMenuItem(int menuItemId, String name, double price, String quantity, int isAvailable, int menuItemTypeId) throws RemoteException {
        return MenuItemController.getInstance().updateMenuItem(menuItemId, name, price, quantity, isAvailable, menuItemTypeId);
    }

    @Override
    public MenuItem getMenuItemById(int menuItemId) throws RemoteException {
        return MenuItemController.getInstance().getMenuItemById(menuItemId);
    }

    @Override
    public MenuItem getMenuItemByName(String name) throws RemoteException {
        return MenuItemController.getInstance().getMenuItemByName(name);
    }

    @Override
    public String[] getMenuItemNames() throws RemoteException {
        return MenuItemController.getInstance().getMenuItemNames();
    }

    @Override
    public Object[][] getMenuItems() throws RemoteException {
        return MenuItemController.getInstance().getMenuItems();
    }

    @Override
    public Object[][] getMenuItemsByMenu(int menuId) throws RemoteException {
        return MenuItemController.getInstance().getMenuItemsByMenu(menuId);
    }

    @Override
    public List<MenuItem> getMenuItemsByMenuList(int menuId) throws RemoteException {
        return MenuItemController.getInstance().getMenuItemsByMenuList(menuId);
    }

    @Override
    public Object[][] getMenuItemsByMenuItemType(int menuItemTypeId) throws RemoteException {
        return MenuItemController.getInstance().getMenuItemsByMenuItemType(menuItemTypeId);
    }

    @Override
    public List<MenuItem> getMenuItemsByMenuItemTypeList(int menuItemTypeId) throws RemoteException {
        return MenuItemController.getInstance().getMenuItemsByMenuItemTypeList(menuItemTypeId);
    }

    //MENU
    @Override
    public boolean createMenu(int userId, String name, Date date) throws RemoteException {
        return MenuController.getInstance().createMenu(userId, name, date);
    }

    @Override
    public boolean deleteMenu(int menuId) throws RemoteException {
        return MenuController.getInstance().deleteMenu(menuId);
    }

    @Override
    public boolean updateMenu(int menuId, int userId, String name, Date date) throws RemoteException {
        return MenuController.getInstance().updateMenu(menuId, userId, name, date);
    }

    @Override
    public Menu getMenuById(int menuId) throws RemoteException {
        return MenuController.getInstance().getMenuById(menuId);
    }

    @Override
    public Menu getMenuByName(String name) throws RemoteException {
        return MenuController.getInstance().getMenuByName(name);
    }

    @Override
    public Object[][] getMenus() throws RemoteException {
        return MenuController.getInstance().getMenus();
    }

    @Override
    public String[] getMenuNames() throws RemoteException {
        return MenuController.getInstance().getMenuNames();
    }

    //MENUMENUITEM
    @Override
    public boolean createMenuMenuItem(int menuId, int menuItemId) throws RemoteException {
        return MenuMenuItemController.getInstance().createMenuMenuItem(menuId, menuItemId);
    }

    @Override
    public boolean deleteMenuMenuItem(int menuId, int menuItemId) throws RemoteException {
        return MenuMenuItemController.getInstance().deleteMenuMenuItem(menuId, menuItemId);
    }

    @Override
    public boolean updateMenuMenuItem(int menuMenuItemId, int menuId, int menuItemId) throws RemoteException {
        return MenuMenuItemController.getInstance().updateMenuMenuItem(menuMenuItemId, menuId, menuItemId);
    }

    //USEDMATERIAL
    @Override
    public void createUsedMaterial(int materialId, int menuItemId, double quantity) throws RemoteException {
        UsedMaterialController.getInstance().createUsedMaterial(materialId, menuItemId, quantity);
    }

    @Override
    public boolean deleteUsedMaterial(int usedMaterialId) throws RemoteException {
        return UsedMaterialController.getInstance().deleteUsedMaterial(usedMaterialId);
    }

    @Override
    public boolean updateUsedMaterial(int usedMaterialId, int materialId, int menuItemId, double quantity) throws RemoteException {
        return UsedMaterialController.getInstance().updateUsedMaterial(usedMaterialId, materialId, menuItemId, quantity);
    }

    @Override
    public Object[][] getUsedMaterials() throws RemoteException {
        return UsedMaterialController.getInstance().getUsedMaterials();
    }

    @Override
    public Object[][] getUsedMaterialsByMenuItem(int menuItemId) throws RemoteException {
        return UsedMaterialController.getInstance().getUsedMaterialsByMenuItem(menuItemId);
    }

    @Override
    public UsedMaterial getUsedMaterialById(int usedMaterialId) throws RemoteException {
        return UsedMaterialController.getInstance().getUsedMaterialById(usedMaterialId);
    }

    //USERROLE
    @Override
    public boolean createUserRole(int userId, int roleId) throws RemoteException {
        return UserRoleController.getInstance().createUserRole(userId, roleId);
    }

    @Override
    public void deleteUserRole(int userId, int roleId) throws RemoteException {
        UserRoleController.getInstance().deleteUserRole(userId, roleId);
    }

    @Override
    public UserRole getUserRoleById(int userRoleId) throws RemoteException {
        return UserRoleController.getInstance().getUserRoleById(userRoleId);
    }

    @Override
    public List getUserRoleByUserId(int userId) throws RemoteException {
        return UserRoleController.getInstance().getUserRoleByUserId(userId);
    }

    @Override
    public boolean isExistedUserRole(int userId, int roleId) throws RemoteException {
        UserRole ur = UserRoleController.getInstance().getUserRoleByUserAndRole(userId, roleId);
        if (ur == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean[] getUserRoles(int userId) throws RemoteException {
        return UserRoleController.getInstance().getUserRoles(userId);
    }

    @Override
    public boolean isUserRole(int userId, int roleId) throws RemoteException {
        return UserRoleController.getInstance().isUserRole(userId, roleId);
    }

    //TABLE
    @Override
    public boolean createTable(int tableNumber, int numberOfPlaces) throws RemoteException {
        return TableController.getInstance().createTable(tableNumber, numberOfPlaces);
    }

    @Override
    public boolean deleteTable(int tableId) throws RemoteException {
        return TableController.getInstance().deleteTable(tableId);
    }

    @Override
    public boolean updateTable(int tableId, int tableNumber, int numberOfPlaces) throws RemoteException {
        return TableController.getInstance().updateTable(tableId, tableNumber, numberOfPlaces);
    }

    @Override
    public Table getTableById(int tableId) throws RemoteException {
        return TableController.getInstance().getTableById(tableId);
    }

    @Override
    public Table getTableByTableNumber(int tableNumber) throws RemoteException {
        return TableController.getInstance().getTableByTableNumber(tableNumber);
    }

    @Override
    public int[] getTableNumbers() throws RemoteException {
        return TableController.getInstance().getTableNumbers();
    }

    @Override
    public Object[][] getTables() throws RemoteException {
        return TableController.getInstance().getTables();
    }

    //MENUITEMTYPE
    @Override
    public boolean createMenuItemType(String name) throws RemoteException {
        return MenuItemTypeController.getInstance().createMenuItemType(name);
    }

    @Override
    public boolean deleteMenuItemType(int menuItemTypeId) throws RemoteException {
        return MenuItemTypeController.getInstance().deleteMenuItemType(menuItemTypeId);
    }

    @Override
    public boolean updateMenuItemType(int menuItemTypeId, String name) throws RemoteException {
        return MenuItemTypeController.getInstance().updateMenuItemType(menuItemTypeId, name);
    }

    @Override
    public MenuItemType getMenuItemTypeById(int menuItemTypeId) throws RemoteException {
        return MenuItemTypeController.getInstance().getMenuItemTypeById(menuItemTypeId);
    }

    @Override
    public MenuItemType getMenuItemTypeByName(String name) throws RemoteException {
        return MenuItemTypeController.getInstance().getMenuItemTypeByName(name);
    }

    @Override
    public String[] getMenuItemTypeNames() throws RemoteException {
        return MenuItemTypeController.getInstance().getMenuItemTypeNames();
    }

    @Override
    public List<MenuItemType> getMenuItemTypesList() throws RemoteException {
        return MenuItemTypeController.getInstance().getMenuItemTypesList();
    }

    @Override
    public Object[][] getMenuItemTypes() throws RemoteException {
        return MenuItemTypeController.getInstance().getMenuItemTypes();
    }

    @Override
    public boolean isDeletableMenuItemType(int menuItemTypeId) throws RemoteException {
        return MenuItemTypeController.getInstance().isDeletableMenuItemType(menuItemTypeId);
    }
}
