package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Menu;
import cz.cvut.fel.restauracefel.hibernate.MenuItem;
import cz.cvut.fel.restauracefel.hibernate.MenuMenuItem;

/**
 *
 * @author Jarda
 */
public class MenuMenuItemController {

    private static MenuMenuItemController instance = null;
    private MenuMenuItem menuMenuItem = null;

    private MenuMenuItemController(){
    }

    public static MenuMenuItemController getInstance(){
        if (instance == null){
            instance = new MenuMenuItemController();
        }
        return instance;
    }

    public boolean createMenuMenuItem(int menuId, int menuItemId){
        menuMenuItem = MenuMenuItem.findByMenuAndMenuItem(menuId, menuItemId);
        if (menuMenuItem == null){
            menuMenuItem = new MenuMenuItem();
            Menu menu = Menu.findById(menuId);
            if (menu == null){
                return false;
            }
            menuMenuItem.setMenu(menu);
            MenuItem menuItem = MenuItem.findById(menuItemId);
            if (menuItem == null){
                return false;
            }
            menuMenuItem.setMenuItem(menuItem);
            menuMenuItem.create();
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteMenuMenuItem(int menuId, int menuItemId){
        menuMenuItem = MenuMenuItem.findByMenuAndMenuItem(menuId, menuItemId);
        if (menuMenuItem == null){
            return false;
        }
        //menuMenuItem.delete();
        menuMenuItem.setIsDeleted(1);
        return true;
    }

    public boolean updateMenuMenuItem(int menuMenuItemId, int menuId, int menuItemId){
        menuMenuItem = MenuMenuItem.findById(menuMenuItemId);
        if (menuMenuItem == null){
            return false;
        }
        Menu menu = Menu.findById(menuId);
        if (menu == null){
            return false;
        }
        menuMenuItem.setMenu(menu);
        MenuItem menuItem = MenuItem.findById(menuItemId);
        if (menuItem == null){
            return false;
        }
        menuMenuItem.setMenuItem(menuItem);
        menuMenuItem.update();
        return true;
    }    
    
}
