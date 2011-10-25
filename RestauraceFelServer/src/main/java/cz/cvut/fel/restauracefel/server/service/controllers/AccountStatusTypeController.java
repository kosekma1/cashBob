package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.AccountStatusType;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Tomas Hnizdil
 */
public class AccountStatusTypeController {

    protected static AccountStatusTypeController instance = null;
    protected AccountStatusType accountStatusType = null;

    private AccountStatusTypeController() {
    }

    public static AccountStatusTypeController getInstance() {
        if (instance == null) {
            instance = new AccountStatusTypeController();
        }
        return instance;
    }

    //vytvari zaznam o novem uzivateli
    public boolean createAccountStatusType(String name, String note) {
        if (name!=null&&!name.equals("")&&getAccountStatusTypeByName(name)==null) {
            accountStatusType = new AccountStatusType();
            accountStatusType.setName(name);
            accountStatusType.setNote(note);
            accountStatusType.create();
            return true;
        } else {
            return false;
        }
    }

    //vraci uzivatele s danym Id
    public AccountStatusType getAccountStatusTypeById(int id) {
        return AccountStatusType.findById(id);
    }

    public AccountStatusType getAccountStatusTypeByName(String name) {
        return AccountStatusType.findByName(name);
    }

    //vraci vsechny uzivatele ve forme Listu
    public List getAllAccountStatusTypes() {
        return AccountStatusType.findAll();
    }

    //maze uzivatele s danym Id
    public boolean deleteAccountStatusType(int accountStatusTypeId) {
        accountStatusType = AccountStatusType.findById(accountStatusTypeId);
        if (accountStatusType == null) {
            return false;
        }
        accountStatusType.setIsDeleted(1);
        return true;
    }

    //v podobe pole typu String navraci prijmeni vsech uzivatelu
    public String[] getAccountStatusTypeNames() {
        List list = AccountStatusType.findAll();
        if (list == null || list.isEmpty()) {
            return null;
        }
        String array[] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            array[i] = ((AccountStatusType) it.next()).getName();
            i++;
        }
        return array;
    }

    /*
    //v podobe pole typu String navraci prijmeni vsech uzivatelu
    public String[] getUserNames() {
    List list = User.findAll();
    if (list == null || list.isEmpty())
    return null;
    String array[] = new String[list.size()];
    Iterator it = list.iterator();
    int i = 0;
    while (it.hasNext()) {
    array[i] = ((User) it.next()).getLastName();
    i++;
    }
    return array;
    }

    public String[] getUserUsernames(){
    List list = User.findAll();
    if (list == null || list.isEmpty())
    return null;
    String array[] = new String[list.size()];
    Iterator it = list.iterator();
    int i = 0;
    while (it.hasNext()) {
    array[i] = ((User) it.next()).getUsername();
    i++;
    }
    return array;
    }

    //v podobe dvojrozmerneho pole typu Object navraci udaje o vsech uzivatelych'
     */
    public Object[][] getAccountStatusTypes() {
        List<AccountStatusType> list = AccountStatusType.findAll();
        if (list == null || list.isEmpty()) {
            return null;
        }
        Object array[][] = new Object[list.size()][3];
        int i = 0;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AccountStatusType u = (AccountStatusType) it.next();
            array[i][0] = u.getAccountStatusTypeId();
            array[i][1] = u.getName();
            array[i][2] = u.getNote();
            i++;
        }
        return array;
    }
}
