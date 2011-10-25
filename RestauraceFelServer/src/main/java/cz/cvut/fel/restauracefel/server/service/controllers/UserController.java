package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.hibernate.UserRole;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jarda
 * @author Tomas Hnizdil
 */
public class UserController {

    private static final String DEFAULT_PASSWD = "1234";

    protected static UserController instance = null;
    protected User user = null;

    private UserController() {
    }

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    //vytvari zaznam o novem uzivateli
    public boolean createUser(String name, String surname, String pid, String username, String passwd) {
        if (!isExistedByPID(pid) && !isExistedByUsername(username)) {
            user = new User();
            user.setFirstName(name);
            user.setLastName(surname);
            user.setPersonalIdentificationNumber(pid);
            user.setUsername(username);
            user.setPassword(passwd);
            user.create();            
            return true;
        } else {            
            return false;
        }
    }

    // vytvari zaznam o novem zakaznikovi s predplacenym kreditem
    public boolean createUser(String name, String surname, String username) {
        if (!isExistedByUsername(username)) {
            user = new User();
            user.setFirstName(name);
            user.setLastName(surname);
            user.setUsername(username);
            user.setCredit(0);
            user.create();
            return true;
        } else {
            return false;
        }
    }

    //kontroluje zda existuje uzivatel s danym username
    public boolean isExistedByUsername(String username) {
        if (User.findByUsername(username) == null) {
            return false;
        } else {
            return true;
        }
    }

    //kontroluje zda existuje uzivatel s danym PID
    public boolean isExistedByPID(String pid) {
        if (User.findByPID(pid) == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isValidUser(String username, String passwd){
        user = User.findByUsername(username);
        if (user == null){
            return false;
        }
        if (user.getPassword().equals(passwd)){
            return true;
        }        
        return false;
    }

    //vraci uzivatele s danym PID
    public User getUserByPID(String pid) {
        return User.findByPID(pid);
    }

    //vraci uzivatele s danym username
    public User getUserByUsername(String userName) {
        return User.findByUsername(userName);
    }

    //vraci uzivatele s danym Id
    public User getUserById(int id) {
        return User.findById(id);
    }

    //vraci vsechny uzivatele ve forme Listu
    public List getAllUsers() {
        return User.findAll();
    }

    //maze uzivatele s danym Id
    public boolean deleteUser(int userId) {
        user = User.findById(userId);
        if (user == null){
            return false;
        }
        List<UserRole> urrList = UserRole.findByUser(user.getUserId());
        if (urrList != null && !urrList.isEmpty()){
            Iterator itUrr = urrList.iterator();
            while (itUrr.hasNext()) {
                UserRole urr = (UserRole) itUrr.next();
                //urr.delete();
                urr.setIsDeleted(1);
            }
        }
        //user.delete();
        user.setIsDeleted(1);
        return true;
    }

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

    //v podobe dvojrozmerneho pole typu Object navraci udaje o vsech uzivatelych
    public Object[][] getUsers() {
        List<User> list = User.findAll();
        if (list == null || list.isEmpty())
            return null;
        Object array[][] = new Object[list.size()][8];
        int i = 0;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            User u = (User) it.next();

            array[i][0] = u.getUserId();
            array[i][1] = u.getFirstName();
            array[i][2] = u.getLastName();
            List<UserRole> uRoles = UserRoleController.getInstance().getUserRoleByUserId(u.getUserId());
            String roles = "";
            if (uRoles == null) {
                roles = "Žádné definované role";
            } else {
                Iterator it2 = uRoles.iterator();
                int x = 0;
                while (it2.hasNext()) {
                    if (x == 0) {
                        roles += ((UserRole) it2.next()).getRole().getName();
                    } else {
                        roles += ", " + ((UserRole) it2.next()).getRole().getName();
                    }
                    x++;
                }
            }

            array[i][3] = roles;
            array[i][4] = u.getPersonalIdentificationNumber();
            array[i][5] = u.getUsername();
            String pass = u.getPassword();
            if (pass==null) array[i][6] = "-";
            else if (pass.equals(DEFAULT_PASSWD)){
                array[i][6] = "NE";
            } else {
                array[i][6] = "ANO";
            }
            array[i][7] = u.getCredit();
            i++;
        }
        return array;
    }

    //aktualizuje uzivatele s danym Id
    public boolean updateUser(Integer userId, String name, String surname, String pid, String username) {
        user = User.findById(userId);
        if (user == null){
            return false;
        }
        User u = null;
        u = User.findByPID(pid);
        if (u != null && u != user){
            return false;
        }
        u = User.findByUsername(username);
        if (u != null && u != user){
            return false;
        }
        user.setFirstName(name);
        user.setLastName(surname);
        user.setPersonalIdentificationNumber(pid);
        user.setUsername(username);        
        user.update();
        return true;
    }

    //nastavuje kredit uzivateli s danym Id
    public boolean updateUser(Integer userId, double credit) {
        user = User.findById(userId);
        if (user == null){
            return false;
        }
        user.setCredit(user.getCredit()+credit);
        user.update();
        return true;
    }

    public boolean updateUserPassword(Integer userId, String newPassword){
        user = User.findById(userId);
        if (user == null){
            return false;
        }
        user.setPassword(newPassword);
        user.update();
        return true;
    }

    public String getDefaultPasswd(){
        return DEFAULT_PASSWD;
    }

    public boolean isValidOldPasswd(Integer userId, String passwd){
        user = User.findById(userId);
        if (user == null){
            return false;
        }
        if (user.getPassword().equals(passwd)){
            return true;
        }
        return false;
    }

    public double getUserCredit(int userId) {
        user = User.findById(userId);
        if (user == null) return 0;
        return user.getCredit();
    }
}
