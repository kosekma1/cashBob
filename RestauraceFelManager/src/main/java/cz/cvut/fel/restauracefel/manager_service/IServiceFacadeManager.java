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
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * Hlavni interface pro RMI komunikaci se seznamem metod implementovanych na strane serveru
 *
 * @author Jarda
 * @author Tomas Hnizdil
 */
public interface IServiceFacadeManager extends Remote {

    //USER methods
    public List getAllUsers() throws RemoteException;
    public boolean createUser(String name, String surname, String pid, String username, String passwd) throws RemoteException;
    public boolean isValidUser(String username, String passwd) throws RemoteException;
    public User getUserByPID(String pid) throws RemoteException;
    public User getUserByUsername(String userName) throws RemoteException;
    public User getUserById(int id) throws RemoteException;
    public boolean deleteUser(int userId) throws RemoteException;
    public String[] getUserNames() throws RemoteException;
    public String[] getUserUsernames() throws RemoteException;
    public Object [][] getUsers() throws RemoteException;
    public boolean updateUser(Integer userId, String name, String surname, String pid, String username) throws RemoteException;
    public String getDefaultPasswd() throws RemoteException;
    public boolean updateUserPassword(Integer userId, String newPassword) throws RemoteException;
    public boolean isValidOldPasswd(Integer userId, String passwd) throws RemoteException;

    //ROLE methods
    public List getAllRoles() throws RemoteException;
    public String[] getRoleNames() throws RemoteException;
    public Role getRoleByID(int id) throws RemoteException;
    public Role getRoleByName(String name) throws RemoteException;

    //MATERIAL methods
    public boolean createMaterial(String name, int idMaterialType, int idUnitType, String barcode, double minimal,double density, double emptyPackageWeight, double packageCapacity) throws RemoteException;
    public List getAllMaterials() throws RemoteException;
    public Material getMaterialByID(int id) throws RemoteException;
    public Material getMaterialByName(String name) throws RemoteException;
    public String[] getMaterialNames() throws RemoteException;
    public Object [][] getMaterials() throws RemoteException;
    public String[] getMaterialNamesByMaterialType(int materialTypeId) throws RemoteException;
    public boolean deleteMaterial(int materialId) throws RemoteException;
    public boolean updateMaterial(Integer materialId, String name, int idMaterialType, int idUnitType, String barcode, double minimal,double density, double emptyPackageWeight, double packageCapacity) throws RemoteException;
    public boolean isDeletableMaterial(int materialId) throws RemoteException;

    //MATERIALTYPE methods
    public boolean createMaterialType(String name, String note) throws RemoteException;
    public boolean deleteMaterialType(int materialTypeId) throws RemoteException;
    public boolean updateMaterialType(int materialTypeId, String name, String note) throws RemoteException;
    public List getAllMaterialTypes() throws RemoteException;
    public MaterialType getMaterialTypeByID(int id) throws RemoteException;
    public MaterialType getMaterialTypeByName(String name) throws RemoteException;
    public String[] getMaterialTypeNames() throws RemoteException;
    public Object [][] getMaterialTypes() throws RemoteException;
    public boolean isDeletableMaterialType(int materialTypeId) throws RemoteException;

    //UNITTYPE methods
    public boolean createUnitType(String name, String abbreviation, int typeId) throws RemoteException;
    public boolean deleteUnitType(int unitTypeId) throws RemoteException;
    public boolean updateUnitType(int unitTypeId, String name, String abbreviation, int typeId) throws RemoteException;
    public List getAllUnitTypes() throws RemoteException;
    public String[] getUnitTypeAbbrs() throws RemoteException;
    public String[] getUnitTypeNames() throws RemoteException;
    public String [] getUnitTypeNamesByTypeId(int typeId) throws RemoteException;
    public String [] getUnitTypeAbbrsByTypeId(int typeId) throws RemoteException;
    public UnitType getUnitTypeByID(int id) throws RemoteException;
    public UnitType getUnitTypeByName(String name) throws RemoteException;
    public UnitType getUnitTypeByAbbr(String abbr) throws RemoteException;

    //MENUITEM methods
    public boolean createMenuItem(String name, double price, String quantity, int isAvailable, int menuItemTypeId) throws RemoteException;
    public boolean deleteMenuItem(int menuItemId) throws RemoteException;
    public boolean updateMenuItem(int menuItemId, String name, double price, String quantity, int isAvailable, int menuItemTypeId) throws RemoteException;
    public MenuItem getMenuItemById(int menuItemId) throws RemoteException;
    public MenuItem getMenuItemByName(String name) throws RemoteException;
    public String [] getMenuItemNames() throws RemoteException;
    public Object [][] getMenuItems() throws RemoteException;
    public Object [][] getMenuItemsByMenu(int menuId) throws RemoteException;
    public List<MenuItem> getMenuItemsByMenuList(int menuId) throws RemoteException;
    public Object [][] getMenuItemsByMenuItemType(int menuItemTypeId) throws RemoteException;
    public List<MenuItem> getMenuItemsByMenuItemTypeList(int menuItemTypeId) throws RemoteException;

    //MENU methods
    public boolean createMenu(int userId, String name, Date date) throws RemoteException;
    public boolean deleteMenu(int menuId) throws RemoteException;
    public boolean updateMenu(int menuId, int userId, String name, Date date) throws RemoteException;
    public Menu getMenuById(int menuId) throws RemoteException;
    public Menu getMenuByName(String name) throws RemoteException;
    public Object [][] getMenus() throws RemoteException;
    public String [] getMenuNames() throws RemoteException;

    //MENUMENUITEM methods
    public boolean createMenuMenuItem(int menuId, int menuItemId) throws RemoteException;
    public boolean deleteMenuMenuItem(int menuId, int menuItemId) throws RemoteException;
    public boolean updateMenuMenuItem(int menuMenuItemId, int menuId, int menuItemId) throws RemoteException;

    //USEDMATERIAL methods
    public void createUsedMaterial(int materialId, int menuItemId, double quantity) throws RemoteException;
    public boolean deleteUsedMaterial(int usedMaterialId) throws RemoteException;
    public boolean updateUsedMaterial(int usedMaterialId, int materialId, int menuItemId, double quantity) throws RemoteException;
    public Object [][] getUsedMaterials() throws RemoteException;
    public Object [][] getUsedMaterialsByMenuItem(int menuItemId) throws RemoteException;
    public UsedMaterial getUsedMaterialById(int usedMaterialId) throws RemoteException;

    //USERROLE methods
    public boolean createUserRole(int userId, int roleId) throws RemoteException;
    public void deleteUserRole(int userId, int roleId) throws RemoteException;
    public UserRole getUserRoleById(int userRoleId) throws RemoteException;
    //public UserRole getUserRoleByUserAndRole(int userId, int roleId) throws RemoteException;
    public List getUserRoleByUserId(int user) throws RemoteException;
    public boolean isExistedUserRole(int userId, int roleId) throws RemoteException;
    public boolean [] getUserRoles(int userId) throws RemoteException;
    public boolean isUserRole(int userId, int roleId) throws RemoteException;

    //TABLE methods
    public boolean createTable(int tableNumber, int numberOfPlaces) throws RemoteException;
    public boolean deleteTable(int tableId) throws RemoteException;
    public boolean updateTable(int tableId, int tableNumber, int numberOfPlaces) throws RemoteException;
    public Table getTableById(int tableId) throws RemoteException;
    public Table getTableByTableNumber(int tableNumber) throws RemoteException;
    public int [] getTableNumbers() throws RemoteException;
    public Object [][] getTables() throws RemoteException;

    //MENUITEMTYPE methods
    public boolean createMenuItemType(String name) throws RemoteException;
    public boolean deleteMenuItemType(int menuItemTypeId) throws RemoteException;
    public boolean updateMenuItemType(int menuItemTypeId, String name) throws RemoteException;
    public MenuItemType getMenuItemTypeById(int menuItemTypeId) throws RemoteException;
    public MenuItemType getMenuItemTypeByName(String name) throws RemoteException;
    public String [] getMenuItemTypeNames() throws RemoteException;
    public List<MenuItemType> getMenuItemTypesList() throws RemoteException;
    public Object [][] getMenuItemTypes() throws RemoteException;
    public boolean isDeletableMenuItemType(int menuItemTypeId) throws RemoteException;

}