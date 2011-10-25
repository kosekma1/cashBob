package cz.cvut.fel.restauracefel.hibernate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @author Tomas Hnizdil
 */
public class MenuItem extends DBEntity {

    private static final long serialVersionUID = 8674356004460574251L;

    private Integer menuItemId;
    private String name;
    private double price;    
    private String quantity;
    private int isAvailable;
    private int isDeleted;
    private MenuItemType menuItemType;

    public MenuItem() {
    }

    public MenuItem(String name, double price, String quantity, int isAvailable, int isDeleted) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.isAvailable = isAvailable;
        this.isDeleted = isDeleted;
    }

    public MenuItem(String name, double price, String quantity, int isAvailable, int isDeleted, MenuItemType menuItemType) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.isAvailable = isAvailable;
        this.menuItemType = menuItemType;
    }

    public MenuItem(Object[] source){
        
    }

    //tato metoda zde musi byt, a to z duvodu, aby mohla probehnout spravne serializace
    //zapis objektu do proudu bajtu
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeInt(menuItemId);
        stream.writeObject(name);
        stream.writeDouble(price);
        stream.writeObject(quantity);
        stream.writeInt(isAvailable);
        stream.writeInt(isDeleted);
        //stream.writeInt(menuItemTypeID);

        MenuItemType mit = new MenuItemType(menuItemType.getName(), menuItemType.getItemCount(), menuItemType.getIsDeleted());
        mit.setMenuItemTypeId(menuItemType.getMenuItemTypeId());
        stream.writeObject(mit);
    }

    //tato metoda zde musi byt, a to z duvodu, aby mohla probehnout spravne serializace
    //cteni a rekonstrukce objektu z proudu bajtu
    private void readObject(ObjectInputStream stream) throws IOException {
        try {
            menuItemId = stream.readInt();
            name = (String)stream.readObject();
            price = stream.readDouble();
            quantity = (String)stream.readObject();
            isAvailable = stream.readInt();
            isDeleted = stream.readInt();
            menuItemType = (MenuItemType) stream.readObject();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public Integer getMenuItemId() {
        return this.menuItemId;
    }

    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getIsAvailable() {
        return this.isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public MenuItemType getMenuItemType() {
        return this.menuItemType;
    }

    public void setMenuItemType(MenuItemType menuType) {
        this.menuItemType = menuType;
    }
    
    public void create() {
        create(this);
    }

    public void update() {
        update(this);
    }

    public void delete() {
        delete(this);
    }

    public static MenuItem findById(Integer id) {
        //return (MenuItem) findById("MenuItem", "menuItemId", id);
        return (MenuItem) findByIdNotDeleted("MenuItem", "menuItemId", id, "isDeleted", 0);
    }

    //vrati vsechny polozky menu, ktere nejsou oznaceny jako smazane
    public static List findAll(){
        return findAllNotDeleted("MenuItem", "isDeleted", 0);
    }

    public static MenuItem findByName(String name) {
        //return (MenuItem) findByStringName("MenuItem", "name", name);
        return (MenuItem) findByStringNameNotDeleted("MenuItem", "name", name, "isDeleted", 0);
    }

    public static List findByMenu(int menuId){
        String query = "select menuItem from MenuMenuItem mmi where mmi.menu.menuId = :id1 and mmi.isDeleted = :id2";
        String[] paramNames = new String[]{"id1", "id2"};
        String[] paramTypes = new String[]{"Integer", "Integer"};
        Integer[] paramValues = new Integer[]{menuId, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty()) {
            return null;
        }
        return res;
    }

    public static MenuItem findByOrder(int orderId) {
        return ((OrderMenuItem) findByIdNotDeleted("OrderMenuItem", "order.orderId", orderId, "isDeleted", 0)).getMenuItem();
    }

    public static List findAllByAccount(int accountId){
        String query = "select menuItem, count(*) from OrderMenuItem omi where omi.order.account.accountId = :id1 group by omi.menuItem.menuItemId";
        String[] paramNames = new String[]{"id1"};
        String[] paramTypes = new String[]{"Integer"};
        Integer[] paramValues = new Integer[]{accountId};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty()) {
            return null;
        }
        return res;
    }

    public static List findByAccount(int accountId){
        String query = "select menuItem, count(*) from OrderMenuItem omi where omi.order.account.accountId = :id1 and omi.order.isPaid = :id2 group by omi.menuItem.menuItemId";
        String[] paramNames = new String[]{"id1", "id2"};
        String[] paramTypes = new String[]{"Integer", "Integer"};
        Integer[] paramValues = new Integer[]{accountId, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty()) {
            return null;
        }
        return res;
    }

    public static List findByMenuItemType(int menuItemTypeId) {
        String query = "from MenuItem mi where mi.menuItemType.menuItemTypeId = :id";
        String[] paramNames = new String[]{"id"};
        String[] paramTypes = new String[]{"Integer"};
        Integer[] paramValues = new Integer[]{menuItemTypeId};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty()) {
            return null;
        }
        return res;
    }
}


