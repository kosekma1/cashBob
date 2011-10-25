package cz.cvut.fel.restauracefel.hibernate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class MenuItemType extends DBEntity {

    private static final long serialVersionUID = -8750686683872232073L;

    private Integer menuItemTypeId;
    private String name;
    private int itemCount;
    private int isDeleted;

    public MenuItemType() {
    }

    public MenuItemType(String name, int itemCount, int isDeleted) {
        this.name = name;
        this.itemCount = itemCount;
        this.isDeleted = isDeleted;
    }
    
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeInt(menuItemTypeId);
        stream.writeObject(name);
        stream.writeInt(itemCount);
    }

    private void readObject(ObjectInputStream stream) throws IOException {
        try {
            menuItemTypeId = stream.readInt();
            name = (String)(stream.readObject());
            itemCount = stream.readInt();
        } catch (Exception e){}
    }

    public Integer getMenuItemTypeId() {
        return this.menuItemTypeId;
    }

    public void setMenuItemTypeId(Integer menuItemTypeId) {
        this.menuItemTypeId = menuItemTypeId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemCount() {
        return this.itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
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

    public static MenuItemType findById(Integer id) {
        return (MenuItemType) findByIdNotDeleted("MenuItemType", "menuItemTypeId", id, "isDeleted", 0);        
    }

    public static List findAll() {
        return findAllNotDeleted("MenuItemType", "isDeleted", 0);
    }

    /**
     * Metoda vrati objekt tridy MenuItemType, jehoz nazev odpivida parametru.
     *
     * @param name nazev
     * @return objekt tridy MenuItemType, pokud odpovidajici zaznam neexistuje,
     * tak vrati null
     */
    public static MenuItemType findByName(String name){
        return (MenuItemType) findByStringNameNotDeleted("MenuItemType", "name", name, "isDeleted", 0);
    }
    
}