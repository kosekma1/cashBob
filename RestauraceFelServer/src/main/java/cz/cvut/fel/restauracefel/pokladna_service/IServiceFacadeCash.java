package cz.cvut.fel.restauracefel.pokladna_service;

import cz.cvut.fel.restauracefel.hibernate.Account;
import cz.cvut.fel.restauracefel.hibernate.AccountCategory;
import cz.cvut.fel.restauracefel.hibernate.AccountStatusType;
import cz.cvut.fel.restauracefel.hibernate.Discount;
import cz.cvut.fel.restauracefel.hibernate.DiscountType;
import cz.cvut.fel.restauracefel.hibernate.Menu;
import cz.cvut.fel.restauracefel.hibernate.MenuItem;
import cz.cvut.fel.restauracefel.hibernate.MenuItemType;
import cz.cvut.fel.restauracefel.hibernate.Order;
import cz.cvut.fel.restauracefel.hibernate.OrderMenuItem;
import cz.cvut.fel.restauracefel.hibernate.Role;
import cz.cvut.fel.restauracefel.hibernate.RoleDiscountType;
import cz.cvut.fel.restauracefel.hibernate.Table;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.hibernate.UserDiscountType;
import cz.cvut.fel.restauracefel.hibernate.UserRole;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * Interface RMI metod pro modul Pokladna
 *
 * @author Jarda
 * @author Tomas Hnizdil
 */
public interface IServiceFacadeCash extends Remote {

    public String getSomething() throws RemoteException;
    public User verifyUser(String username, char[] password) throws RemoteException;

    //ACCOUNTSTATUSTYPE
    public List getAllAccountStatusTypes() throws RemoteException;
    public boolean createAccountStatusType(String name, String note) throws RemoteException;
    public AccountStatusType getAccountStatusTypeById(int id) throws RemoteException;
    public AccountStatusType getAccountStatusTypeByName(String name) throws RemoteException;
    public boolean deleteAccountStatusType(int accountStatusTypeId) throws RemoteException;
    public String[] getAccountStatusTypeNames() throws RemoteException;
    public Object[][] getAccountStatusTypes() throws RemoteException;

    //ACCOUNTCATEGORY
    public List getAllAccountCategories() throws RemoteException;
    public boolean createAccountCategory(String name, String note) throws RemoteException;
    public AccountCategory getAccountCategoryById(int id) throws RemoteException;
    public AccountCategory getAccountCategoryByName(String name) throws RemoteException;
    public boolean deleteAccountCategory(int accountCategoryId) throws RemoteException;
    public String[] getAccountCategoryNames() throws RemoteException;
    public Object[][] getAccountCategories() throws RemoteException;

    //ACCOUNT methods
    public List getAllAccounts() throws RemoteException;
    public boolean createAccount(String name, int accountStatusTypeId, int accountCategoryId, int tableId, int userId, int discountTypeId, String note) throws RemoteException;
    public Account getAccountById(int id) throws RemoteException;
    public Account getAccountByName(String name) throws RemoteException;
    public Object[][] getAccountsByTable(int tableId) throws RemoteException;
    public Object[][] getAccountsByUser(int userId) throws RemoteException;
    public Object[][] getAccountsByDiscountType(int discountTypeId) throws RemoteException;
    public Object[][] getAccountsByAccountCategory(int accountCategoryId) throws RemoteException;
    public Object[][] getAccountsByAccountStatusType(int accountStatusTypeId) throws RemoteException;
    public Object[][] getAccountsByDate(String dateFrom, String dateTo) throws RemoteException;
    public boolean deleteAccount(int accountId) throws RemoteException;
    public String[] getAccountNames() throws RemoteException;
    public Object[][] getAccounts() throws RemoteException;
    public boolean updateAccount(int accountId, int accountStatusTypeId) throws RemoteException;

    //DISCOUNT methods
    public List getAllDiscounts() throws RemoteException;
    public boolean createDiscount(int discountTypeId, int menuItemId, double amount, double coefficient) throws RemoteException;
    public Discount getDiscountById(int id) throws RemoteException;
    public boolean deleteDiscount(int discountId) throws RemoteException;
    public Discount getDiscountByDiscountTypeAndMenuItem(int discountTypeId, int menuItemId) throws RemoteException;
    public Object[][] getDiscounts() throws RemoteException;

    //DISCOUNTTYPE methods
    public List getAllDiscountTypes() throws RemoteException;
    public boolean createDiscountType(String name) throws RemoteException;
    public DiscountType getDiscountTypeById(int id) throws RemoteException;
    public DiscountType getDiscountTypeByName(String name) throws RemoteException;
    public boolean deleteDiscountType(int discountTypeId) throws RemoteException;
    public String[] getDiscountTypeNames() throws RemoteException;
    public Object[][] getDiscountTypes() throws RemoteException;

    //ROLEDISCOUNTTYPE methods
    public boolean createRoleDiscountType(int roleId, int discountTypeId) throws RemoteException;
    public boolean deleteRoleDiscountType(int roleId, int discountTypeId) throws RemoteException;
    public RoleDiscountType getRoleDiscountTypeById(int roleDiscountTypeId) throws RemoteException;
    public RoleDiscountType getRoleDiscountTypeByRoleAndDiscountType(int roleId, int discountTypeId) throws RemoteException;
    public String[] getDiscountTypeNamesByRole(int roleId) throws RemoteException;
    public List getRoleDiscountTypesByRoleId(int roleId) throws RemoteException;

    //USERDISCOUNTTYPE methods
    public boolean createUserDiscountType(int userId, int discountTypeId) throws RemoteException;
    public boolean deleteUserDiscountType(int userId, int discountTypeId) throws RemoteException;
    public UserDiscountType getUserDiscountTypeById(int userDiscountTypeId) throws RemoteException;
    public UserDiscountType getUserDiscountTypeByUserAndDiscountType(int userId, int discountTypeId) throws RemoteException;
    public String[] getDiscountTypeNamesByUser(int userId) throws RemoteException;
    public List getUserDiscountTypesByUserId(int userId) throws RemoteException;

    //ORDER methods
    public List getAllOrders() throws RemoteException;
    public boolean createOrder(int isPaid, Date time, int accountId, int userId) throws RemoteException;
    public boolean deleteOrder(int orderId) throws RemoteException;
    public Order getOrderById(int orderId) throws RemoteException;
    public String [] getOrderNames() throws RemoteException;
    public Object [][] getOrders() throws RemoteException;
    public Object [][] getOrdersByAccount(int accountId) throws RemoteException;
    public boolean payNMenuItemsByAccount(int n, int menuItemId, int accountId) throws RemoteException;
    public boolean moveNMenutItemsByAccount(int n, int menuItemId, int sourceAccountId, int targetAccountId) throws RemoteException;

    //ORDERMENUITEM methods
    public List getAllOrderMenuItems() throws RemoteException;
    public boolean createOrderMenuItem(int menuItemId, int orderId) throws RemoteException;
    public boolean deleteOrderMenuItem(int orderMenuItemId) throws RemoteException;
    public OrderMenuItem getOrderMenuItemById(int orderMenuItemId) throws RemoteException;
    public String [] getOrderMenuItemNames() throws RemoteException;
    public Object [][] getOrderMenuItems() throws RemoteException;

    //USER methods
    public List getAllUsers() throws RemoteException;
    public boolean createUser(String name, String surname, String pid, String username, String passwd) throws RemoteException;
    public boolean createUser(String name, String surname, String username) throws RemoteException;
    public boolean isValidUser(String username, String passwd) throws RemoteException;
    public User getUserByPID(String pid) throws RemoteException;
    public User getUserByUsername(String userName) throws RemoteException;
    public User getUserById(int id) throws RemoteException;
    public boolean deleteUser(int userId) throws RemoteException;
    public String[] getUserNames() throws RemoteException;
    public String[] getUserUsernames() throws RemoteException;
    public Object [][] getUsers() throws RemoteException;
    public boolean updateUser(Integer userId, String name, String surname, String pid, String username) throws RemoteException;
    public boolean updateUser(Integer userId, double credit) throws RemoteException;
    public String getDefaultPasswd() throws RemoteException;
    public boolean updateUserPassword(Integer userId, String newPassword) throws RemoteException;
    public boolean isValidOldPasswd(Integer userId, String passwd) throws RemoteException;
    public double getUserCredit(int userId) throws RemoteException;

    //ROLE methods
    public List getAllRoles() throws RemoteException;
    public String[] getRoleNames() throws RemoteException;
    public Role getRoleByID(int id) throws RemoteException;
    public Role getRoleByName(String name) throws RemoteException;

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
    public Object [][] getMenuItemsByAccount(int accountId) throws RemoteException;
    public Object [][] getAllMenuItemsByAccount(int accountId) throws RemoteException;
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
    public String [] getTableNames() throws RemoteException;
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
