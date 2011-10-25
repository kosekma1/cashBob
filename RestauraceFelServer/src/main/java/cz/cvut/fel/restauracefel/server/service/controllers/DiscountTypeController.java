package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.DiscountType;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Tomas Hnizdil
 */
public class DiscountTypeController {

    protected static DiscountTypeController instance = null;
    protected DiscountType discountType = null;

    private DiscountTypeController() {
    }

    public static DiscountTypeController getInstance() {
        if (instance == null) {
            instance = new DiscountTypeController();
        }
        return instance;
    }

    //vytvari zaznam o novem typu slevy
    public boolean createDiscountType(String name) {
        if (name!=null&&!name.equals("")&&getDiscountTypeByName(name)==null) {
            discountType = new DiscountType();
            discountType.setName(name);
            discountType.create();
            return true;
        } else {
            return false;
        }
    }

    //vraci typ slevy s danym Id
    public DiscountType getDiscountTypeById(int id) {
        return DiscountType.findById(id);
    }

    //vraci typ slevy s danym jmenem
    public DiscountType getDiscountTypeByName(String name) {
        return DiscountType.findByName(name);
    }

    //vraci vsechny typy slev ve forme Listu
    public List getAllDiscountTypes() {
        return DiscountType.findAll();
    }

    //maze typ slevy s danym Id
    public boolean deleteDiscountType(int discountTypeId) {
        discountType = DiscountType.findById(discountTypeId);
        if (discountType == null){
            return false;
        }
        discountType.setIsDeleted(1);
        return true;
    }

    //v podobe pole typu String navraci jmena vsech typu slev
    public String[] getDiscountTypeNames() {
        List list = DiscountType.findAll();
        if (list == null || list.isEmpty())
            return null;
        String array[] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            array[i] = ((DiscountType) it.next()).getName();
            i++;
        }
        return array;
    }

    //v podobe dvojrozmerneho pole typu Object navraci udaje o vsech typech slev
    public Object[][] getDiscountTypes() {
        List<DiscountType> list = DiscountType.findAll();
        if (list == null || list.isEmpty())
            return null;
        Object array[][] = new Object[list.size()][2];
        int i = 0;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            DiscountType u = (DiscountType) it.next();
            array[i][0] = u.getDiscountTypeId();
            array[i][1] = u.getName();
            i++;
        }
        return array;
    }

}
