package cz.cvut.fel.restauracefel.hibernate;
// Generated 16.3.2009 21:36:54 by Hibernate Tools 3.2.1.GA

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;

/**
 * Menu generated by hbm2java
 */
public class Menu extends DBEntity {

    private static final long serialVersionUID = -4080089458742922843L;
    
    private Integer menuId;
    private User user;
    private String name;
    private Date date;
    private int isDeleted;

    public Menu() {
    }

    public Menu(User user, String name, Date date, int isDeleted) {
        this.user = user;
        this.name = name;
        this.date = date;
        this.isDeleted = isDeleted;
    }

    //tato metoda zde musi byt, a to z duvodu, aby mohla probehnout spravne serializace
    //zapis objektu do proudu bajtu
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeInt(menuId);

        User u = new User(user.getFirstName(), user.getLastName(), user.getPersonalIdentificationNumber(), user.getUsername(), user.getPassword(), user.getIsDeleted());
        u.setUserId(user.getUserId());
        stream.writeObject(u);

        stream.writeObject(name);
        stream.writeObject(date);
        stream.writeInt(isDeleted);
    }

    //tato metoda zde musi byt, a to z duvodu, aby mohla probehnout spravne serializace
    //cteni a rekonstrukce objektu z proudu bajtu
    private void readObject(ObjectInputStream stream) throws IOException {
        try {
            menuId = stream.readInt();
            user = (User) stream.readObject();
            name = (String) stream.readObject();
            date = (Date) stream.readObject();
            isDeleted = stream.readInt();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public Integer getMenuId() {
        return this.menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public void update() {
        update(this);
    }

    public void delete() {
        delete(this);
    }

    public static Menu findById(Integer id) {
        //return (Menu) findById("Menu", "menuId", id);
        return (Menu) findByIdNotDeleted("Menu", "menuId", id, "isDeleted", 0);
    }

    public static Menu findByName(String name) {
        //return (Menu) findByStringName("Menu", "name", name);
        return (Menu) findByStringNameNotDeleted("Menu", "name", name, "isDeleted", 0);
    }

    //vrati vsechny menu, ktere nejsou oznaceny jako smazane
    public static List findAll(){
        return findAllNotDeleted("Menu", "isDeleted", 0);
    }

    public static List findByUser(Integer userId) {
        String query = "from Menu men where men.user.userId = :id1 and men.isDeleted = :id2";
        String[] paramNames = new String[]{"id1", "id2"};
        String[] paramTypes = new String[]{"Integer", "Integer"};
        Integer[] paramValues = new Integer[]{userId, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty()) {
            return null;
        }
        return res;
    }
}


