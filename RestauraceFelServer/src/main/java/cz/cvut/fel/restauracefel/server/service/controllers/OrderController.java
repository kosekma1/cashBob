package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Account;
import cz.cvut.fel.restauracefel.hibernate.Order;
import cz.cvut.fel.restauracefel.hibernate.User;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jarda
 * @author Tomas Hnizdil
 */
public class OrderController {

    protected static OrderController instance = null;
    protected Order order = null;

    private OrderController() {
    }

    public static OrderController getInstance() {
        if (instance == null) {
            instance = new OrderController();
        }
        return instance;
    }

    //vytvari zaznam o nove objednavce
    public boolean createOrder(int isPaid, Date time, int accountId, int userId) {
        if (userId != 0 && accountId != 0) {
            order = new Order();
            order.setIsPaid(isPaid);
            order.setTime(time);
            Account account = Account.findById(accountId);
            order.setAccount(account);
            User user = User.findById(userId);
            order.setUser(user);
            order.create();
            return true;
        } else {
            return false;
        }
    }

    //vraci objednavku s danym Id
    public Order getOrderById(int id) {
        return Order.findById(id);
    }

    //vraci vsechny objednavky ve forme Listu
    public List getAllOrders() {
        return Order.findAll();
    }

    //maze objednavku s danym Id
    public boolean deleteOrder(int orderId) {
        order = Order.findById(orderId);
        if (order == null){
            return false;
        }
        order.setIsDeleted(1);
        return true;
    }

    //v podobe pole typu String navraci nazvy vsech objednavek
    public String[] getOrderNames() {
        List list = Order.findAll();
        if (list == null || list.isEmpty())
            return null;
        String array[] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            array[i] = "Objednavka cislo "+((Order) it.next()).getOrderId().toString();
            i++;
        }
        return array;
    }

    public Object [][] getOrdersByAccount(int accountId){
        List<Order> list = Order.findByAccount(accountId);
        if (list == null || list.isEmpty())
            return null;
        Object [][] array = new Object [list.size()][5];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()){
            order = (Order)it.next();
            array[i][0] = order.getOrderId();
            array[i][1] = order.getAccount().getAccountId();
            array[i][2] = order.getTime();
            array[i][3] = order.getUser().getUsername();
            array[i][4] = order.getIsPaid();
            i++;
        }
        return array;
    }

    public boolean payNMenuItemsByAccount(int n, int menuItemId, int accountId) {
        List<Order> list = Order.findNByAccountAndMenuItem(n, menuItemId, accountId);
        if (list == null || list.isEmpty()) return false;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            order = (Order)it.next();
            order.setIsPaid(1);
        }
        return true;
    }

    /**
     * Presune n polozek s id menuItemId z uctu sourceAccountId na ucet targetAccountId.
     *
     * @param n pocet polozek
     * @param menuItemId id polozky
     * @param sourceAccountId id uctu, ze ktereho se presouva
     * @param targetAccountId id uctu, na ktery se presouva
     */
    public boolean moveNMenuItemsByAccount(int n, int menuItemId, int sourceAccountId, int targetAccountId) {
        List<Order> list = Order.findNByAccountAndMenuItem(n, menuItemId, sourceAccountId);
        Account account = AccountController.getInstance().getAccountById(targetAccountId);
        if (list == null || list.isEmpty() || account == null) return false;

        Iterator it = list.iterator();
        while (it.hasNext()) {
            order = (Order)it.next();
            order.setAccount(account);
        }
        return true;
    }

    //v podobe dvojrozmerneho pole typu Object navraci udaje o vsech objednavkach
    public Object[][] getOrders() {
        List<Order> list = Order.findAll();
        if (list == null || list.isEmpty())
            return null;
        Object array[][] = new Object[list.size()][5];
        int i = 0;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Order a = (Order) it.next();
            array[i][0] = a.getOrderId();
            array[i][1] = a.getAccount().getAccountId();
            array[i][2] = a.getTime();
            array[i][3] = a.getUser().getUsername();
            array[i][4] = a.getIsPaid();
            i++;
        }
        return array;
    }

}
