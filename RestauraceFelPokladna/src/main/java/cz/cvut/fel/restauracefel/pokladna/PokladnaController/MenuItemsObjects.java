/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.pokladna.PokladnaController;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Obsahuje v sobe vsechny objekty MenuItemObject a umoznuje jednoduchou manipulaci.
 * @author libik
 */
public class MenuItemsObjects {
    private List<MenuItemObject> seznam;

    public MenuItemsObjects(){
        seznam = new LinkedList<MenuItemObject>();
    }

    public void clear(){
        seznam = new LinkedList<MenuItemObject>();
    }

    public boolean isEmpty(){
        return seznam.isEmpty();
    }

    public void add(MenuItemObject menuItem){
        MenuItemObject polozka = findByID(menuItem.getID());
        if (polozka == null){
            seznam.add(new MenuItemObject(menuItem));
            findByID(menuItem.getID()).setCount(1);
        } else {
            polozka.setCount(polozka.getCount()+1);
        }
    }

    public void remove(MenuItemObject menuItem){
        MenuItemObject polozka = findByID(menuItem.getID());
        if (polozka != null){
            if (polozka.getCount() <= 1){
                seznam.remove(polozka);
            } else {
                polozka.setCount(polozka.getCount()-1);
            }
        }
    }

    public MenuItemObject findByID(int ID){
        MenuItemObject vysledek = null;
        for (int i=0;i<seznam.size();i++){
            if (seznam.get(i).getID() == ID){
                return seznam.get(i);
            }
        }
        return vysledek;
    }

    /**
     * @return the seznam
     */
    public List<MenuItemObject> getSeznam() {
        return seznam;
    }    

}
