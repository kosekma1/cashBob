package cz.cvut.fel.restauracefel.hibernate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MenuItemType implements Serializable {

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

}