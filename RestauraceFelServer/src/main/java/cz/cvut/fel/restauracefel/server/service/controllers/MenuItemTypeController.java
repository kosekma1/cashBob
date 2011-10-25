package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.MenuItem;
import cz.cvut.fel.restauracefel.hibernate.MenuItemType;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jarda
 */
public class MenuItemTypeController {

    public static MenuItemTypeController instance = null;
    public static MenuItemType menuItemType = null;

    private MenuItemTypeController(){        
    }

    public static MenuItemTypeController getInstance(){
        if (instance == null){
            instance = new MenuItemTypeController();
        }
        return instance;
    }

    /**
     * Metoda vytvari novou instanci MenuItem.
     *
     * @param name nazev noveho druhu polozek (instance MenuItemType)
     * @return vraci true, pokud byl zaznam vytvore, jinak vraci false
     */
    public boolean createMenuItemType(String name){
        //vytvareny objekt MenuItemType musi mit jedinecne jmeno,
        //proto to testujemu
        menuItemType = MenuItemType.findByName(name);
        if (menuItemType == null){
            //na zacatku nemu MenuItemType zadny MenuItem, proto 0
            //posledni 0 znaci, ze zaznam neni smazat - to je logicky, kdyz jsme
            //ho ted vytvorili
            menuItemType = new MenuItemType(name, 0, 0);
            menuItemType.create();
            return true;
        }
        return false;
    }

    //maze stavajici objekt MenuItemType s danym Id
    public boolean deleteMenuItemType(int menuItemTypeId){
        menuItemType = MenuItemType.findById(menuItemTypeId);
        if (menuItemType == null){
            //MenuItemType s danym id neexistuje
            return false;
        }
        //!!!! mazeme pouze priznakove, tedy takto
        menuItemType.setIsDeleted(1); //1 znamena smazano
        return true;
    }

    public boolean updateMenuItemType(int menuItemTypeId, String name){
        menuItemType = MenuItemType.findById(menuItemTypeId);
        if (menuItemType == null){
            return false;
        }

        MenuItemType mit = MenuItemType.findByName(name);
        //pokud jsme nalezli pro parametr "name" jiny objekt MenuItemType,
        //tak nemuzume aktualizaci provest = prestala by platit jedinecnost
        //promenne "name"
        if (mit != null && mit != menuItemType){
            return false;
        }

        menuItemType.setName(name);
        menuItemType.update();
        return true;
    }

    public MenuItemType getMenuItemTypeById(int menuItemTypeId){
        return MenuItemType.findById(menuItemTypeId);
    }

    public MenuItemType getMenuItemTypeByName(String name){
        return MenuItemType.findByName(name);
    }

    /**
     * Metoda vrati seznam nazvu (name) vsech ulozeny instanci tridy MenuItemType.
     *
     * @return pole Stringu
     */
    public String [] getMenuItemTypeNames(){
        List<MenuItemType> list = MenuItemType.findAll();
        if (list == null || list.isEmpty()){
            return null;
        }
        String [] array = new String [list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()){
            menuItemType = (MenuItemType)it.next();
            array [i] = menuItemType.getName();
            i++;
        }
        return array;
    }

    public List<MenuItemType> getMenuItemTypesList(){
        return MenuItemType.findAll();
    }

    public Object [][] getMenuItemTypes(){
        List<MenuItemType> list = MenuItemType.findAll();
        if (list == null || list.isEmpty()){
            return null;
        }
        Object [][] array = new Object [list.size()][3];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()){
            menuItemType = (MenuItemType)it.next();
            array [i][0] = menuItemType.getMenuItemTypeId();
            array [i][1] = menuItemType.getName();
            array [i][2] = menuItemType.getItemCount();
            i++;
        }
        return array;
    }

    public boolean isDeletableMenuItemType(int menuItemTypeId){
        List<MenuItem> items = MenuItem.findByMenuItemType(menuItemTypeId);
        if (items == null || items.isEmpty()){
            return true;
        }
        return false;
    }

}
