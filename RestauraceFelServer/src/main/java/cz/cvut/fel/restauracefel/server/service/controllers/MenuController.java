package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Menu;
import cz.cvut.fel.restauracefel.hibernate.MenuMenuItem;
import cz.cvut.fel.restauracefel.hibernate.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jarda
 */
public class MenuController {

    private static MenuController instance = null;
    private Menu menu = null;

    private MenuController() {
    }

    public static MenuController getInstance() {
        if (instance == null) {
            instance = new MenuController();
        }
        return instance;
    }

    //vytvari zaznam o novem menu
    public boolean createMenu(int userId, String name, Date date) {
        menu = Menu.findByName(name);
        if (menu == null) {
            menu = new Menu();
            User user = User.findById(userId);
            if (user == null) {
                return false;
            }
            menu.setUser(user);
            menu.setName(name);
            menu.setDate(date);
            menu.create();
            return true;
        } else {
            return false;
        }

    }

    //odstrani menu podle menuId, predtim vsak odstrani vsechny vazby na polozky menu (polozky menu neodstranuje)
    public boolean deleteMenu(int menuId) {
        menu = Menu.findById(menuId);
        if (menu == null) {
            return false;
        } else {            
            List<MenuMenuItem> list = MenuMenuItem.findByMenu(menuId);
            if (list != null){
                Iterator it = list.iterator();
                MenuMenuItem mmi = null;
                while (it.hasNext()){
                    mmi = (MenuMenuItem) it.next();
                    //mmi.delete();
                    mmi.setIsDeleted(1);
                }
            }
            //menu.delete();
            menu.setIsDeleted(1);
            return true;
        }
    }

    //aktualizuje menu s danym Id
    public boolean updateMenu(int menuId, int userId, String name, Date date) {
        menu = Menu.findById(menuId);
        if (menu == null) {
            return false;
        } else {
            User u = User.findById(userId);
            if (u == null) {
                return false;
            }
            Menu m = Menu.findByName(name);
            if (m != null && m != menu) {
                return false;
            }
            menu.setUser(u);
            menu.setName(name);
            menu.setDate(date);
            menu.update();
            return true;
        }
    }

    //vraci menu s danym Id
    public Menu getMenuById(int menuId) {
        return Menu.findById(menuId);
    }

    //vraci menu s danym jmenem
    public Menu getMenuByName(String name) {
        return Menu.findByName(name);
    }

    //vraci Object pole se zaznamy jednotlivych menu
    public Object[][] getMenus() {
        List<Menu> list = Menu.findAll();
        if (list == null || list.isEmpty()) {
            return null;
        }
        Object[][] array = new Object[list.size()][4];
        Iterator it = list.iterator();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        int i = 0;
        while (it.hasNext()) {
            menu = (Menu) it.next();
            array[i][0] = menu.getMenuId();            
            array[i][1] = menu.getName();
            array[i][2] = sdf.format(menu.getDate());
            if (menu.getUser().getIsDeleted() == 1){
                array [i][3] = "Uživatel smazán";
            } else {
                array[i][3] = menu.getUser().getUsername();
            }
            i++;
        }
        return array;
    }

    //vraci pole se jmeny jednotlivych menu
    public String[] getMenuNames() {
        List<Menu> list = Menu.findAll();
        if (list == null || list.isEmpty()) {
            return null;
        }
        String names[] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            menu = (Menu) it.next();            
            names[i] = menu.getName();
            i++;
        }
        return names;
    }

}
