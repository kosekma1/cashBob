package cz.cvut.fel.restauracefel.hibernate;

import java.io.Serializable;


/**
 * @author Tomas Hnizdil
 */
public class Discount implements Serializable {

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
}


