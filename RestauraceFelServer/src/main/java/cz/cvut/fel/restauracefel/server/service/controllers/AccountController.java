package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Account;
import cz.cvut.fel.restauracefel.hibernate.AccountCategory;
import cz.cvut.fel.restauracefel.hibernate.AccountStatusType;
import cz.cvut.fel.restauracefel.hibernate.Discount;
import cz.cvut.fel.restauracefel.hibernate.DiscountType;
import cz.cvut.fel.restauracefel.hibernate.MenuItem;
import cz.cvut.fel.restauracefel.hibernate.Table;
import cz.cvut.fel.restauracefel.hibernate.User;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Tomas Hnizdil
 */
public class AccountController {

    protected static AccountController instance = null;
    protected Account account = null;

    private AccountController() {
    }

    public static AccountController getInstance() {
        if (instance == null) {
            instance = new AccountController();
        }
        return instance;
    }

    //vytvari zaznam o novem uctu
    public boolean createAccount(String name, int accountStatusTypeId, int accountCategoryId, int tableId, int userId, int discountTypeId, String note) {
        if (name!=null&&!name.equals("")) {
            if (getAccountByNameAndAccountStatusType(name, 1) != null) return false;
            account = new Account();
            account.setName(name);
            AccountStatusType accountStatusType = AccountStatusType.findById(accountStatusTypeId);
            account.setAccountStatusType(accountStatusType);
            AccountCategory accountCategory = AccountCategory.findById(accountCategoryId);
            account.setAccountCategory(accountCategory);
            Table table = Table.findById(tableId);
            account.setTable(table);
            User user = User.findById(userId);
            account.setUser(user);
            DiscountType discountType = DiscountType.findById(discountTypeId);
            account.setDiscountType(discountType);
            account.setNote(note);
            account.create();
            return true;
        } else {
            return false;
        }
    }

    //vraci account s danym Id
    public Account getAccountById(int id) {
        return Account.findById(id);
    }

    //vraci account s danym jmenem
    public Account getAccountByName(String name) {
        return Account.findByName(name);
    }

    public Account getAccountByNameAndAccountStatusType(String name, int accountStatusTypeId) {
        return Account.findByNameAndAccountStatusType(name, accountStatusTypeId);
    }

    private double getSumByAccount(Account a, boolean onlyPaid) {
        double sum = 0;

        List<Object[]> menuItems = onlyPaid ? MenuItem.findByAccount(a.getAccountId()) : MenuItem.findAllByAccount(a.getAccountId());
        if (menuItems == null) return sum;
        DiscountType dt = a.getDiscountType();
        Iterator it = menuItems.iterator();
        while (it.hasNext()) {
            Object[] menuItem = (Object[]) it.next();
            MenuItem mi = (MenuItem) menuItem[0];

            Discount d = (dt!=null) ? Discount.findByDiscountTypeAndMenuItem(dt.getDiscountTypeId(), mi.getMenuItemId()) : null;
            double coefficient = (d!=null) ? 1-(d.getCoefficient()/100) : 1;
            double amount = (d!=null) ? d.getAmount() : 0;
            sum += (Long)menuItem[1] * (coefficient * mi.getPrice() - amount);
        }
        return sum;
    }

    //vytvari dvojrozmerne pole typu Object pro metody getAccountsByTable, getAccountsByUser, getAccountsByDiscountType, getAccountsByAccountStatusType, getAccounts
    public Object [][] createAccountArray(List<Account> list) {
        if (list == null || list.isEmpty()) return null;
        Object [][] array = new Object [list.size()][10];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()){
            Account a = (Account) it.next();
            Table table = a.getTable();
            User user = a.getUser();
            DiscountType discountType = a.getDiscountType();
            AccountCategory accountCategory = a.getAccountCategory();

            array[i][0] = a.getAccountId();
            array[i][1] = a.getName();
            array[i][2] = (table==null?null:table.getTableNumber());
            array[i][3] = (user==null?null:user.getUsername());
            array[i][4] = (discountType==null)?null:discountType.getName();
            array[i][5] = a.getAccountStatusType().getName();
            array[i][6] = (accountCategory==null)?null:accountCategory.getName();
            array[i][7] = a.getNote();
            array[i][8] = getSumByAccount(a, false);
            array[i][9] = getSumByAccount(a, true);
            i++;
        }
        return array;
    }

    // vraci vsechny ucty prirazene stolu s danym id
    public Object [][] getAccountsByTable(int tableId){
        List<Account> list = Account.findByTable(tableId);
        return createAccountArray(list);
    }

    // vraci vsechny ucty prirazene osobe s danym id
    public Object [][] getAccountsByUser(int userId){
        List<Account> list = Account.findByUser(userId);
        return createAccountArray(list);
    }

    // vraci vsechny ucty s typem slevy s danym id
    public Object [][] getAccountsByDiscountType(int discountTypeId){
        List<Account> list = Account.findByDiscountType(discountTypeId);
        return createAccountArray(list);
    }

    public Object [][] getAccountsByAccountCategory(int accountCategoryId) {
        List<Account> list = Account.findByAccountCategory(accountCategoryId);
        return createAccountArray(list);
    }

    // vraci vsechny ucty se stavem s danym id
    public Object [][] getAccountsByAccountStatusType(int accountStatusTypeId){
        List<Account> list = Account.findByAccountStatusType(accountStatusTypeId);
        return createAccountArray(list);
    }

    public Object [][] getAccountsByDate(String dateFrom, String dateTo) {
        List<Account> list = Account.findByDate(dateFrom, dateTo);
        return createAccountArray(list);
    }

    //vraci vsechny ucty ve forme Listu
    public List getAllAccounts() {
        return Account.findAll();
    }

    //maze ucet s danym Id
    public boolean deleteAccount(int accountId) {
        account = Account.findById(accountId);
        if (account == null) {
            return false;
        }
        account.setIsDeleted(1);
        return true;
    }

    //nastavuje uctu s danym Id novy status
    public boolean updateAccount(Integer accountId, Integer accountStatusTypeId) {
        account = Account.findById(accountId);
        if (account == null) return false;
        AccountStatusType status = AccountStatusType.findById(accountStatusTypeId);
        account.setAccountStatusType(status);
        return true;
    }

    //v podobe pole typu String navraci nazvy vsech uctu
    public String[] getAccountNames() {
        List list = Account.findAll();
        if (list == null || list.isEmpty()) {
            return null;
        }
        String array[] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            array[i] = ((Account) it.next()).getName();
            i++;
        }
        return array;
    }

    //v podobe dvojrozmerneho pole typu Object navraci udaje o vsech uctech
    public Object[][] getAccounts() {
        List<Account> list = Account.findAll();
        return createAccountArray(list);
    }

    public boolean updateAccount(int accountId,String name, int accountStatusTypeId, int tableId, int userId, int discountTypeId){
        Account acc = Account.findById(accountId);
        if (acc == null) return false;
        if (name.equals("")) return false;
        
        AccountStatusType ast = AccountStatusType.findById(accountStatusTypeId);
        Table table = Table.findById(tableId);
        DiscountType discount = DiscountType.findById(discountTypeId);
        User user = User.findById(userId);
        if (ast == null) return false;
        acc.setAccountStatusType(ast);
        if (getAccountByName(name) == null) acc.setName(name);
        acc.setTable(table);
        acc.setDiscountType(discount);
        acc.setUser(user);
        acc.update();
        return true;
    }
}