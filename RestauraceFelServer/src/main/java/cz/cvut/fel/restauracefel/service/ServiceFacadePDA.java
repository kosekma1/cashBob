package cz.cvut.fel.restauracefel.service;

import cz.cvut.fel.restauracefel.hibernate.Account;
import cz.cvut.fel.restauracefel.hibernate.AccountStatusType;
import cz.cvut.fel.restauracefel.hibernate.DiscountType;
import cz.cvut.fel.restauracefel.hibernate.Menu;
import cz.cvut.fel.restauracefel.hibernate.MenuItem;
import cz.cvut.fel.restauracefel.hibernate.MenuItemType;
import cz.cvut.fel.restauracefel.hibernate.Order;
import cz.cvut.fel.restauracefel.hibernate.OrderMenuItem;
import cz.cvut.fel.restauracefel.hibernate.Role;
import cz.cvut.fel.restauracefel.hibernate.Table;
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
import cz.cvut.fel.restauracefel.server.service.controllers.AccountController;
import cz.cvut.fel.restauracefel.server.service.controllers.AccountStatusTypeController;
import cz.cvut.fel.restauracefel.server.service.controllers.DiscountTypeController;
import cz.cvut.fel.restauracefel.server.service.controllers.MenuController;
import cz.cvut.fel.restauracefel.server.service.controllers.MenuItemController;
import cz.cvut.fel.restauracefel.server.service.controllers.MenuItemTypeController;
import cz.cvut.fel.restauracefel.server.service.controllers.OrderController;
import cz.cvut.fel.restauracefel.server.service.controllers.OrderMenuItemController;
import cz.cvut.fel.restauracefel.server.service.controllers.RoleController;
import cz.cvut.fel.restauracefel.server.service.controllers.TableController;
import cz.cvut.fel.restauracefel.server.service.controllers.UserController;
import cz.cvut.fel.restauracefel.server.service.controllers.UserRoleController;

/**
 *
 * @author Jarda
 */
public class ServiceFacadePDA extends UnicastRemoteObject implements IServiceFacadePDA {

    //singleton
    protected static ServiceFacadePDA instance = null;

    public ServiceFacadePDA() throws RemoteException {
        super();
    }

    public void initServiceFacadeRMI(Registry reg) throws java.net.UnknownHostException, RemoteException, FileNotFoundException {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        String name = "ServiceFacadePDA";
        ConfigParser config = new ConfigParser();
        InetAddress inetAddress = InetAddress.getByName(config.getServerIP());
        //Stub
        IServiceFacadePDA facade = ServiceFacadePDA.getInstance();
        reg.rebind(name, facade);
        System.out.println("Servisni fasada pro modul PDA zaregistrovana pod jmenem \"ServiceFacadePDA\"");
        System.out.println("Pripojeni pres adresu:" + inetAddress.toString() + "\n\n");

    }

    //Vraci instanci tridy ServiceFacadePDA
    public static ServiceFacadePDA getInstance() throws RemoteException {
        if (instance == null) {
            instance = new ServiceFacadePDA();
        }
        return instance;
    }


    /*
     *
     * Zde bude implementace metod deklarovanych v rozhrani IServiceFacadePDA
     *
     * metody pro modul PDA
     */
    @Override
    public String getSomething() throws RemoteException {
        return "Message from PDA";
    }

    @Override
    public User verifyUser(String username, char[] password) throws RemoteException {
        return null;
    }

    /*
     * Copy paste z ServiceFasadeManager
     * pridana metoda getMenuByName
     *
     */
    //ACCOUNTSTATUSTYPE
    public List getAllAccountStatusTypes() throws RemoteException {
        return AccountStatusTypeController.getInstance().getAllAccountStatusTypes();
    }

    public boolean createAccountStatusType(String name, String note) throws RemoteException {
        return AccountStatusTypeController.getInstance().createAccountStatusType(name, note);
    }

    public AccountStatusType getAccountStatusTypeById(int id) throws RemoteException {
        return AccountStatusTypeController.getInstance().getAccountStatusTypeById(id);
    }

    public AccountStatusType getAccountStatusTypeByName(String name) throws RemoteException {
        AccountStatusType a = AccountStatusTypeController.getInstance().getAccountStatusTypeByName(name);
        return a;
    }

    public boolean deleteAccountStatusType(int accountStatusTypeId) throws RemoteException {
        return AccountStatusTypeController.getInstance().deleteAccountStatusType(accountStatusTypeId);
    }

    public String[] getAccountStatusTypeNames() throws RemoteException {
        return AccountStatusTypeController.getInstance().getAccountStatusTypeNames();
    }

    public Object[][] getAccountStatusTypes() throws RemoteException {
        return AccountStatusTypeController.getInstance().getAccountStatusTypes();
    }

    //ACCOUNT
    public List getAllAccounts() throws RemoteException {
        return AccountController.getInstance().getAllAccounts();
    }

    public boolean createAccount(String name, int accountStatusTypeId, int accountCategoryId, int tableId, int userId, int discountTypeId, String note) throws RemoteException {
        return AccountController.getInstance().createAccount(name, accountStatusTypeId, accountCategoryId, tableId, userId, discountTypeId, note);
    }

    public boolean updateAccount(int accountId,String name, int accountStatusTypeId, int tableId, int userId, int discountTypeId) throws RemoteException {
        return AccountController.getInstance().updateAccount(accountId, name, accountStatusTypeId, tableId, userId, discountTypeId);
    }

    public Account getAccountById(int id) throws RemoteException {
        return AccountController.getInstance().getAccountById(id);
    }

    public Account getAccountByName(String name) throws RemoteException {
        Account a = AccountController.getInstance().getAccountByName(name);
        return a;
    }

    public Object[][] getAccountsByTable(int tableId) throws RemoteException {
        return AccountController.getInstance().getAccountsByTable(tableId);
    }

    public Object[][] getAccountsByUser(int userId) throws RemoteException {
        return AccountController.getInstance().getAccountsByUser(userId);
    }

    public Object[][] getAccountsByDiscountType(int discountTypeId) throws RemoteException {
        return AccountController.getInstance().getAccountsByDiscountType(discountTypeId);
    }

    public Object[][] getAccountsByAccountStatusType(int accountStatusTypeId) throws RemoteException {
        return AccountController.getInstance().getAccountsByAccountStatusType(accountStatusTypeId);
    }

    public boolean deleteAccount(int accountId) throws RemoteException {
        return AccountController.getInstance().deleteAccount(accountId);
    }

    public String[] getAccountNames() throws RemoteException {
        return AccountController.getInstance().getAccountNames();
    }

    public Object[][] getAccounts() throws RemoteException {
        return AccountController.getInstance().getAccounts();
    }

    //DISCOUNTTYPE
    public List getAllDiscountTypes() throws RemoteException {
        return DiscountTypeController.getInstance().getAllDiscountTypes();
    }

    public boolean createDiscountType(String name) throws RemoteException {
        return DiscountTypeController.getInstance().createDiscountType(name);
    }

    public DiscountType getDiscountTypeById(int id) throws RemoteException {
        return DiscountTypeController.getInstance().getDiscountTypeById(id);
    }

    public DiscountType getDiscountTypeByName(String name) throws RemoteException {
        return DiscountTypeController.getInstance().getDiscountTypeByName(name);
    }

    public boolean deleteDiscountType(int discountTypeId) throws RemoteException {
        return DiscountTypeController.getInstance().deleteDiscountType(discountTypeId);
    }

    public String[] getDiscountTypeNames() throws RemoteException {
        return DiscountTypeController.getInstance().getDiscountTypeNames();
    }

    public Object[][] getDiscountTypes() throws RemoteException {
        return DiscountTypeController.getInstance().getDiscountTypes();
    }

    //ORDER methods
    public List getAllOrders() throws RemoteException {
        return OrderController.getInstance().getAllOrders();
    }

    public boolean createOrder(int isPaid, Date time, int accountId, int userId) throws RemoteException {
        return OrderController.getInstance().createOrder(isPaid, time, accountId, userId);
    }

    public boolean deleteOrder(int orderId) throws RemoteException {
        return OrderController.getInstance().deleteOrder(orderId);
    }

    public Order getOrderById(int orderId) throws RemoteException {
        return OrderController.getInstance().getOrderById(orderId);
    }

    public String [] getOrderNames() throws RemoteException {
        return OrderController.getInstance().getOrderNames();
    }

    public Object [][] getOrders() throws RemoteException {
        return OrderController.getInstance().getOrders();
    }

    public Object [][] getOrdersByAccount(int accountId) throws RemoteException {
        return OrderController.getInstance().getOrdersByAccount(accountId);
    }

    public boolean payNMenuItemsByAccount(int n, int menuItemId, int accountId) throws RemoteException {
        return OrderController.getInstance().payNMenuItemsByAccount(n, menuItemId, accountId);
    }

    public boolean moveNMenutItemsByAccount(int n, int menuItemId, int sourceAccountId, int targetAccountId) throws RemoteException {
        return OrderController.getInstance().moveNMenuItemsByAccount(n, menuItemId, sourceAccountId, targetAccountId);
    }

    //ORDERMENUITEM methods
    public List getAllOrderMenuItems() throws RemoteException {
        return OrderMenuItemController.getInstance().getAllOrderMenuItems();
    }

    public boolean createOrderMenuItem(int menuItemId, int orderId) throws RemoteException {
        return OrderMenuItemController.getInstance().createOrderMenuItem(menuItemId, orderId);
    }

    public boolean deleteOrderMenuItem(int orderMenuItemId) throws RemoteException {
        return OrderMenuItemController.getInstance().deleteOrderMenuItem(orderMenuItemId);
    }

    public OrderMenuItem getOrderMenuItemById(int orderMenuItemId) throws RemoteException {
        return OrderMenuItemController.getInstance().getOrderMenuItemById(orderMenuItemId);
    }

    public String [] getOrderMenuItemNames() throws RemoteException {
        return OrderMenuItemController.getInstance().getOrderMenuItemNames();
    }

    public Object [][] getOrderMenuItems() throws RemoteException {
        return OrderMenuItemController.getInstance().getOrderMenuItems();
    }

    //USER
    public List getAllUsers() throws RemoteException {
        return UserController.getInstance().getAllUsers();
    }

    public boolean createUser(String name, String surname, String pid, String username, String passwd) throws RemoteException {
        return UserController.getInstance().createUser(name, surname, pid, username, passwd);
    }

    public boolean createUser(String name, String surname, String pid, String username) throws RemoteException {
        return UserController.getInstance().createUser(name, surname, username);
    }

    public boolean isValidUser(String username, String passwd) throws RemoteException {
        return UserController.getInstance().isValidUser(username, passwd);
    }

    public User getUserByPID(String pid) throws RemoteException {
        return UserController.getInstance().getUserByPID(pid);
    }

    public User getUserByUsername(String userName) throws RemoteException {
        return UserController.getInstance().getUserByUsername(userName);
    }

    public User getUserById(int id) throws RemoteException {
        return UserController.getInstance().getUserById(id);
    }

    public boolean deleteUser(int userId) throws RemoteException {
        return UserController.getInstance().deleteUser(userId);
    }

    public String[] getUserNames() throws RemoteException {
        return UserController.getInstance().getUserNames();
    }

    public String[] getUserUsernames() throws RemoteException {
        return UserController.getInstance().getUserUsernames();
    }

    public Object[][] getUsers() throws RemoteException {
        return UserController.getInstance().getUsers();
    }

    public boolean updateUser(Integer userId, String name, String surname, String pid, String username) throws RemoteException {
        return UserController.getInstance().updateUser(userId, name, surname, pid, username);
    }

    public boolean updateUser(Integer userId, double credit) throws RemoteException {
        return UserController.getInstance().updateUser(userId, credit);
    }

    public String getDefaultPasswd() throws RemoteException {
        return UserController.getInstance().getDefaultPasswd();
    }

    public boolean updateUserPassword(Integer userId, String newPassword) throws RemoteException {
        return UserController.getInstance().updateUserPassword(userId, newPassword);
    }

    public boolean isValidOldPasswd(Integer userId, String passwd) throws RemoteException {
        return UserController.getInstance().isValidOldPasswd(userId, passwd);
    }

    //ROLE
    public List getAllRoles() throws RemoteException {
        return RoleController.getInstance().getAllRoles();
    }

    public String[] getRoleNames() throws RemoteException {
        return RoleController.getInstance().getRoleNames();
    }

    public Role getRoleByID(int id) throws RemoteException {
        return RoleController.getInstance().getRoleByID(id);
    }

    public Role getRoleByName(String name) throws RemoteException {
        return RoleController.getInstance().getRoleByName(name);
    }

    //MENUITEM
    public boolean createMenuItem(String name, double price, String quantity, int isAvailable, int menuItemTypeId) throws RemoteException {
        return MenuItemController.getInstance().createMenuItem(name, price, quantity, isAvailable, menuItemTypeId);
    }

    public boolean deleteMenuItem(int menuItemId) throws RemoteException {
        return MenuItemController.getInstance().deleteMenuItem(menuItemId);
    }

    public boolean updateMenuItem(int menuItemId, String name, double price, String quantity, int isAvailable, int menuItemTypeId) throws RemoteException {
        return MenuItemController.getInstance().updateMenuItem(menuItemId, name, price, quantity, isAvailable, menuItemTypeId);
    }

    public MenuItem getMenuItemById(int menuItemId) throws RemoteException {
        return MenuItemController.getInstance().getMenuItemById(menuItemId);
    }

    public MenuItem getMenuItemByName(String name) throws RemoteException {
        return MenuItemController.getInstance().getMenuItemByName(name);
    }

    public String[] getMenuItemNames() throws RemoteException {
        return MenuItemController.getInstance().getMenuItemNames();
    }

    public Object[][] getMenuItems() throws RemoteException {
        return MenuItemController.getInstance().getMenuItems();
    }

    public Object[][] getMenuItemsByMenu(int menuId) throws RemoteException {
        return MenuItemController.getInstance().getMenuItemsByMenu(menuId);
    }

    public List<MenuItem> getMenuItemsByMenuList(int menuId) throws RemoteException {
        return MenuItemController.getInstance().getMenuItemsByMenuList(menuId);
    }

    public Object[][] getMenuItemsByAccount(int accountId) throws RemoteException {
        return MenuItemController.getInstance().getMenuItemsByAccount(accountId);
    }

    public Object[][] getMenuItemsByMenuItemType(int menuItemTypeId) throws RemoteException {
        return MenuItemController.getInstance().getMenuItemsByMenuItemType(menuItemTypeId);
    }

    public Object [][] getAllMenuItemsByAccount(int accountId) throws RemoteException {
        return MenuItemController.getInstance().getAllMenuItemsByAccount(accountId);
    }

    public List<MenuItem> getMenuItemsByMenuItemTypeList(int menuItemTypeId) throws RemoteException {
        return MenuItemController.getInstance().getMenuItemsByMenuItemTypeList(menuItemTypeId);
    }

    //MENU
    public boolean createMenu(int userId, String name, Date date) throws RemoteException {
        return MenuController.getInstance().createMenu(userId, name, date);
    }

    public boolean deleteMenu(int menuId) throws RemoteException {
        return MenuController.getInstance().deleteMenu(menuId);
    }

    public boolean updateMenu(int menuId, int userId, String name, Date date) throws RemoteException {
        return MenuController.getInstance().updateMenu(menuId, userId, name, date);
    }

    public Menu getMenuById(int menuId) throws RemoteException {
        Menu menu = MenuController.getInstance().getMenuById(menuId);
        return menu;
    }

    public Menu getMenuByName(String name) throws RemoteException {
        return MenuController.getInstance().getMenuByName(name);
    }

    public Object[][] getMenus() throws RemoteException {
        return MenuController.getInstance().getMenus();
    }

    public String[] getMenuNames() throws RemoteException {
        return MenuController.getInstance().getMenuNames();
    }

    //USERROLE
    public boolean createUserRole(int userId, int roleId) throws RemoteException {
        return UserRoleController.getInstance().createUserRole(userId, roleId);
    }

    public void deleteUserRole(int userId, int roleId) throws RemoteException {
        UserRoleController.getInstance().deleteUserRole(userId, roleId);
    }

    public UserRole getUserRoleById(int userRoleId) throws RemoteException {
        return UserRoleController.getInstance().getUserRoleById(userRoleId);
    }

    public List getUserRoleByUserId(int userId) throws RemoteException {
        return UserRoleController.getInstance().getUserRoleByUserId(userId);
    }

    public boolean isExistedUserRole(int userId, int roleId) throws RemoteException {
        UserRole ur = UserRoleController.getInstance().getUserRoleByUserAndRole(userId, roleId);
        if (ur == null) {
            return false;
        }
        return true;
    }

    public boolean[] getUserRoles(int userId) throws RemoteException {
        return UserRoleController.getInstance().getUserRoles(userId);
    }

    public boolean isUserRole(int userId, int roleId) throws RemoteException {
        return UserRoleController.getInstance().isUserRole(userId, roleId);
    }

    //TABLE
    public boolean createTable(int tableNumber, int numberOfPlaces) throws RemoteException {
        return TableController.getInstance().createTable(tableNumber, numberOfPlaces);
    }

    public boolean deleteTable(int tableId) throws RemoteException {
        return TableController.getInstance().deleteTable(tableId);
    }

    public boolean updateTable(int tableId, int tableNumber, int numberOfPlaces) throws RemoteException {
        return TableController.getInstance().updateTable(tableId, tableNumber, numberOfPlaces);
    }

    public Table getTableById(int tableId) throws RemoteException {
        return TableController.getInstance().getTableById(tableId);
    }

    public Table getTableByTableNumber(int tableNumber) throws RemoteException {
        return TableController.getInstance().getTableByTableNumber(tableNumber);
    }

    public int[] getTableNumbers() throws RemoteException {
        return TableController.getInstance().getTableNumbers();
    }

    public String[] getTableNames() throws RemoteException {
        return TableController.getInstance().getTableNames();
    }

    public Object[][] getTables() throws RemoteException {
        return TableController.getInstance().getTables();
    }

    //MENUITEMTYPE
    public boolean createMenuItemType(String name) throws RemoteException {
        return MenuItemTypeController.getInstance().createMenuItemType(name);
    }

    public boolean deleteMenuItemType(int menuItemTypeId) throws RemoteException {
        return MenuItemTypeController.getInstance().deleteMenuItemType(menuItemTypeId);
    }

    public boolean updateMenuItemType(int menuItemTypeId, String name) throws RemoteException {
        return MenuItemTypeController.getInstance().updateMenuItemType(menuItemTypeId, name);
    }

    public MenuItemType getMenuItemTypeById(int menuItemTypeId) throws RemoteException {
        return MenuItemTypeController.getInstance().getMenuItemTypeById(menuItemTypeId);
    }

    public MenuItemType getMenuItemTypeByName(String name) throws RemoteException {
        return MenuItemTypeController.getInstance().getMenuItemTypeByName(name);
    }

    public String[] getMenuItemTypeNames() throws RemoteException {
        return MenuItemTypeController.getInstance().getMenuItemTypeNames();
    }

    public List<MenuItemType> getMenuItemTypesList() throws RemoteException {
        return MenuItemTypeController.getInstance().getMenuItemTypesList();
    }

    public Object[][] getMenuItemTypes() throws RemoteException {
        return MenuItemTypeController.getInstance().getMenuItemTypes();
    }

    public boolean isDeletableMenuItemType(int menuItemTypeId) throws RemoteException {
        return MenuItemTypeController.getInstance().isDeletableMenuItemType(menuItemTypeId);
    }
}
