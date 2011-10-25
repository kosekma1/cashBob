package cz.cvut.fel.restauracefel.hibernate;

import cz.cvut.fel.restauracefel.library.service.DateFunctions;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @author Lukas Camra
 */
public class Uzaverka extends DBEntity {

    private static final long serialVersionUID = -3485621218713955338L;

    private int id;
    private User user;
    private Date date;
    private boolean closed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
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

    public static List<Uzaverka> findAllOrderedDescendingByDate() {
        String query = "SELECT u FROM Uzaverka u ORDER BY u.date desc";
        String[] paramNames = new String[]{};
        String[] paramTypes = new String[]{};
        String[] paramValues = new String[]{};
        List<Uzaverka> res = executeQuery(query, paramNames, paramTypes, paramValues);
        return res;
    }
    
    public static Uzaverka findById(Integer id) {
        return (Uzaverka) findById("Uzaverka", "id", id);
    }

    public static List<Uzaverka> findPreviousUzaverky(Uzaverka u) {
        Date beforePlusDay = DateFunctions.changeDateByNumberOfDays(u.getDate(), 1);
        String query = "SELECT u FROM Uzaverka u WHERE u.date < :date ORDER BY u.date desc";
        String[] paramNames = new String[]{"date"};
        String[] paramTypes = new String[]{"Date"};
        Object[] paramValues = new Object[]{beforePlusDay};

        List<Uzaverka> l = executeQuery(query, paramNames, paramTypes, paramValues);
        List<Uzaverka> res = new ArrayList<Uzaverka>();
        for (Uzaverka u2 : l) {
            if ((u2.getDate().before(u.getDate())) && (u.getId() != u2.getId())) {
                res.add(u2);
            }
        }
        if (res == null || res.isEmpty()) {
            return null;
        }
        return res;
    }
}
