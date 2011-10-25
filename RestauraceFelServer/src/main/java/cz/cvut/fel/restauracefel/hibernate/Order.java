package cz.cvut.fel.restauracefel.hibernate;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Tomas Hnizdil
 */
public class Order extends DBEntity {

    private Integer orderId;
    private int isPaid;
    private Date time;
    private Account account;
    private User user;
    private int isDeleted;

    public Order() {
    }

    public Order(int isPaid, Date time, int isDeleted) {
        this.isPaid = isPaid;
        this.time = time;
        this.isDeleted = isDeleted;
    }

    public Order(int isPaid, Date time, Account account, User user, int isDeleted) {
        this.isPaid = isPaid;
        this.time = time;
        this.account = account;
        this.user = user;
        this.isDeleted = isDeleted;
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public int getIsPaid() {
        return this.isPaid;
    }

    public void setIsPaid(int isPaid) {
        this.isPaid = isPaid;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void create() {
        create(this);
    }

    public void delete() {
        delete(this);
    }

    public void update() {
        update(this);
    }

    public static Order findById(Integer id) {
        //return (Order) findById("Order", "orderId", id);
        return (Order) findByIdNotDeleted("Order", "orderId", id, "isDeleted", 0);
    }

    public static List findByAccount(Integer accountId) {
        String query = "from Order ord where ord.account.accountId = :id1 and ord.isDeleted = :id2";
        String[] paramNames = new String[]{"id1", "id2"};
        String[] paramTypes = new String[]{"Integer", "Integer"};
        Integer[] paramValues = new Integer[]{accountId, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty()) {
            return null;
        }
        return res;
    }

    public static List findByUser(Integer userId) {
        String query = "from Order ord where ord.user.userId = :id1 and ord.isDeleted = :id2";
        String[] paramNames = new String[]{"id1", "id2"};
        String[] paramTypes = new String[]{"Integer", "Integer"};
        Integer[] paramValues = new Integer[]{userId, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty()) {
            return null;
        }
        return res;
    }

    public static List findByDateAfter(Date date){
        List<Order> allOrders = findAll();
        if (allOrders == null) return null;
        List<Order> result = new LinkedList<Order>();
        for(Order o:allOrders) {
            if(o.getTime().after(date)){
                result.add(o);
            }
        }
        return result;
    }

    public static List findNByAccountAndMenuItem(int n, int menuItemId, int accountId) {
        String query = "select order from OrderMenuItem omi where omi.menuItem.menuItemId = :id1 and omi.order.account.accountId = :id2 and omi.order.isPaid = :id3 and omi.order.isDeleted = :id4";
        String[] paramNames = new String[]{"id1", "id2", "id3", "id4"};
        String[] paramTypes = new String[]{"Integer", "Integer", "Integer", "Integer"};
        Integer[] paramValues = new Integer[]{menuItemId, accountId, 0, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        for (int i=n; i<res.size();) res.remove(i);
        if (res == null || res.isEmpty()) {
            return null;
        }
        return res;
    }

    public static List findAll() {
        return findAllNotDeleted("Order", "isDeleted", 0);
    }
}


