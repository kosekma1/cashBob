/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.pokladna.PokladnaInterfaces;

import cz.cvut.fel.restauracefel.pokladna.PokladnaController.MenuItemObject;
import cz.cvut.fel.restauracefel.pokladna.pokladna_gui.EnumMenuItem;

/**
 * Potrebuji interface, ktery implementuje metodu useMenuItem, kterou pouziju v nekolika tridach
 * @author libik
 */
public interface InterfacePressed {
    public void useMenuItem(MenuItemObject menuItem, EnumMenuItem akce);
}
