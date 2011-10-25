package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Discount;
import cz.cvut.fel.restauracefel.hibernate.DiscountType;
import cz.cvut.fel.restauracefel.hibernate.MenuItem;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Tomas Hnizdil
 */
public class DiscountController {

    protected static DiscountController instance = null;
    protected Discount discount = null;

    private DiscountController() {
    }

    public static DiscountController getInstance() {
        if (instance == null) {
            instance = new DiscountController();
        }
        return instance;
    }

    //vytvari zaznam o novem typu slevy
    public boolean createDiscount(int discountTypeId, int menuItemId, double amount, double coefficient) {
        if(discountTypeId < 1 || menuItemId < 1){
            return false;
        }

        discount = new Discount();
        DiscountType discountType = DiscountType.findById(discountTypeId);
        discount.setDiscountType(discountType);
        MenuItem menuItem = MenuItem.findById(menuItemId);
        discount.setMenuItem(menuItem);
        discount.setAmount(amount);
        discount.setCoefficient(coefficient);
        discount.create();
        return true;
    }

    //vraci slevu s danym Id
    public Discount getDiscountById(int id) {
        return Discount.findById(id);
    }

    //vraci vsechny slevy ve forme Listu
    public List getAllDiscounts() {
        return Discount.findAll();
    }

    //maze typ slevy s danym Id
    public boolean deleteDiscount(int discountId) {
        discount = Discount.findById(discountId);
        if (discount == null) {
            return false;
        }
        discount.setIsDeleted(1);
        return true;
    }

    public Discount getDiscountByDiscountTypeAndMenuItem(int discountTypeId, int menuItemId) {
        return Discount.findByDiscountTypeAndMenuItem(discountTypeId, menuItemId);
    }

    //v podobe dvojrozmerneho pole typu Object navraci udaje o vsech typech slev
    public Object[][] getDiscounts() {
        List<Discount> list = Discount.findAll();
        if (list == null || list.isEmpty()) {
            return null;
        }
        Object array[][] = new Object[list.size()][5];
        int i = 0;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Discount d = (Discount) it.next();
            array[i][0] = d.getDiscountId();
            array[i][1] = d.getDiscountType();
            array[i][2] = d.getMenuItem();
            array[i][3] = d.getAmount();
            array[i][4] = d.getCoefficient();
            i++;
        }
        return array;
    }
}
