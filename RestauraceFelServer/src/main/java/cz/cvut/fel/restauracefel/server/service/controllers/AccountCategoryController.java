package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.AccountCategory;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Tomas Hnizdil
 */
public class AccountCategoryController {

    protected static AccountCategoryController instance = null;
    protected AccountCategory accountCategory = null;

    private AccountCategoryController() {
    }

    public static AccountCategoryController getInstance() {
        if (instance == null) {
            instance = new AccountCategoryController();
        }
        return instance;
    }

    //vytvari zaznam o novem uzivateli
    public boolean createAccountCategory(String name, String note) {
        if (name!=null&&!name.equals("")&&getAccountCategoryByName(name)==null) {
            accountCategory = new AccountCategory();
            accountCategory.setName(name);
            accountCategory.setNote(note);
            accountCategory.create();
            return true;
        } else {
            return false;
        }
    }

    //vraci uzivatele s danym Id
    public AccountCategory getAccountCategoryById(int id) {
        return AccountCategory.findById(id);
    }

    public AccountCategory getAccountCategoryByName(String name) {
        return AccountCategory.findByName(name);
    }

    //vraci vsechny uzivatele ve forme Listu
    public List getAllAccountCategories() {
        return AccountCategory.findAll();
    }

    //maze uzivatele s danym Id
    public boolean deleteAccountCategory(int accountCategoryId) {
        accountCategory = AccountCategory.findById(accountCategoryId);
        if (accountCategory == null) {
            return false;
        }
        accountCategory.setIsDeleted(1);
        return true;
    }

    //v podobe pole typu String navraci prijmeni vsech uzivatelu
    public String[] getAccountCategoryNames() {
        List list = AccountCategory.findAll();
        if (list == null || list.isEmpty()) {
            return null;
        }
        String array[] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            array[i] = ((AccountCategory) it.next()).getName();
            i++;
        }
        return array;
    }

    //v podobe dvojrozmerneho pole typu Object navraci udaje o vsech uzivatelych'
    public Object[][] getAccountCategories() {
        List<AccountCategory> list = AccountCategory.findAll();
        if (list == null || list.isEmpty()) {
            return null;
        }
        Object array[][] = new Object[list.size()][3];
        int i = 0;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AccountCategory u = (AccountCategory) it.next();
            array[i][0] = u.getAccountCategoryId();
            array[i][1] = u.getName();
            array[i][2] = u.getNote();
            i++;
        }
        return array;
    }
}
