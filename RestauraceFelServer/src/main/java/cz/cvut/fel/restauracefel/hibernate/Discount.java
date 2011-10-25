package cz.cvut.fel.restauracefel.hibernate;

import java.util.List;


/**
 * @author Tomas Hnizdil
 */
public class Discount extends DBEntity {

    private static final long serialVersionUID = 2552588383482311147L;

    private Integer discountId;
    private DiscountType discountType;
    private MenuItem menuItem;
    private double amount;
    private double coefficient;
    private int isDeleted;

    public Discount() {
    }

    public Discount(DiscountType discountType, MenuItem menuItem, double amount, double coefficient, int isDeleted) {
        this.discountType = discountType;
        this.menuItem = menuItem;
        this.amount = amount;
        this.coefficient = coefficient;
        this.isDeleted = isDeleted;
    }

    public Integer getDiscountId() {
        return this.discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public DiscountType getDiscountType() {
        return this.discountType;
    }

    public void setDiscountType(DiscountType discounttype) {
        this.discountType = discounttype;
    }

    public MenuItem getMenuItem() {
        return this.menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCoefficient() {
        return this.coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
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

    public static Discount findById(Integer id) {
        //return (Discount) findById("Discount", "discountId", id);
        return (Discount) findByIdNotDeleted("Discount", "discountId", id, "isDeleted", 0);
    }

    public static List findAll() {
        return findAllNotDeleted("Discount", "isDeleted", 0);
    }

    public static Discount findByDiscountTypeAndMenuItem(Integer discountTypeId, Integer menuItemId) {
        String query = "from Discount d where d.discountType.discountTypeId = :discountTypeId and d.menuItem.menuItemId = :menuItemId and d.isDeleted = :id3";
        String[] paramNames = new String[]{"discountTypeId", "menuItemId", "id3"};
        String[] paramTypes = new String[]{"Integer", "Integer", "Integer"};
        Integer[] paramValues = new Integer[]{discountTypeId, menuItemId, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty()) {
            return null;
        }
        return (Discount) res.get(0);
    }
}


