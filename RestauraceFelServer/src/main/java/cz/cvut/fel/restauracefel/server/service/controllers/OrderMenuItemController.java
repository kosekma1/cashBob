package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.MenuItem;
import cz.cvut.fel.restauracefel.hibernate.Order;
import cz.cvut.fel.restauracefel.hibernate.OrderMenuItem;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jarda
 * @author Tomas Hnizdil
 */
public class OrderMenuItemController {

    protected static OrderMenuItemController instance = null;
    protected OrderMenuItem orderMenuItem = null;

    private OrderMenuItemController() {
    }

    public static OrderMenuItemController getInstance() {
        if (instance == null) {
            instance = new OrderMenuItemController();
        }
        return instance;
    }

    //vytvari zaznam o novem uzivateli
    public boolean createOrderMenuItem(int menuItemId, int orderId) {
        if (menuItemId != 0 && orderId != 0) {
            orderMenuItem = new OrderMenuItem();
            MenuItem menuItem = MenuItem.findById(menuItemId);
            orderMenuItem.setMenuItem(menuItem);
            Order order = Order.findById(orderId);
            orderMenuItem.setOrder(order);
            orderMenuItem.create();
            return true;
        } else {
            return false;
        }
    }

    //vraci uzivatele s danym Id
    public OrderMenuItem getOrderMenuItemById(int id) {
        return OrderMenuItem.findById(id);
    }

    //vraci vsechny uzivatele ve forme Listu
    public List getAllOrderMenuItems() {
        return OrderMenuItem.findAll();
    }

    //maze uzivatele s danym Id
    public boolean deleteOrderMenuItem(int orderMenuItemId) {
        orderMenuItem = OrderMenuItem.findById(orderMenuItemId);
        if (orderMenuItem == null){
            return false;
        }
        orderMenuItem.setIsDeleted(1);
        return true;
    }

    //v podobe pole typu String navraci prijmeni vsech uzivatelu
    public String[] getOrderMenuItemNames() {
        List list = OrderMenuItem.findAll();
        if (list == null || list.isEmpty())
            return null;
        String array[] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            array[i] = "Objednavka cislo "+((OrderMenuItem) it.next()).getOrderMenuItemId().toString();
            i++;
        }
        return array;
    }

/*
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
    public Object[][] getOrderMenuItems() {
        List<OrderMenuItem> list = OrderMenuItem.findAll();
        if (list == null || list.isEmpty())
            return null;
        Object array[][] = new Object[list.size()][6];
        int i = 0;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            OrderMenuItem a = (OrderMenuItem) it.next();
            array[i][0] = a.getOrderMenuItemId();
            array[i][1] = a.getMenuItem().getName();
            array[i][2] = a.getOrder().getAccount().getAccountId();
            array[i][3] = a.getOrder().getTime();
            array[i][4] = a.getOrder().getUser().getUsername();
            array[i][5] = a.getOrder().getIsPaid();
            i++;
        }
        return array;
    }

}
