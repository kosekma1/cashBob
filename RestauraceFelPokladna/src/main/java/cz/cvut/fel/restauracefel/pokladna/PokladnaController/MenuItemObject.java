/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.pokladna.PokladnaController;

import cz.cvut.fel.restauracefel.library.Enums.EnumMenuItemArray;


/**
 * Predavani MenuItem probiha zvlastnim zpusobem pres dvojrozmerne pole Object s nejakou tou hodnotou navic...
 * Tato trida slouzi jako prostrednik
 * @author libik
 */
public class MenuItemObject {

    private int ID;
    private String typeName;
    private String name;
    private double price;
    private double discountCoefficient;
    private double dicountAmount;
    private long Count;

    public MenuItemObject(Object[] objects) {
        this.ID = Integer.parseInt(objects[EnumMenuItemArray.ItemID.getCode()].toString());
        this.typeName = objects[EnumMenuItemArray.TypeName.getCode()].toString();
        this.name = objects[EnumMenuItemArray.ItemName.getCode()].toString();
        this.price = (Double) objects[EnumMenuItemArray.ItemPrice.getCode()];
        this.discountCoefficient = (Double) objects[EnumMenuItemArray.DiscountCoefficient.getCode()];
        this.dicountAmount = (Double) objects[EnumMenuItemArray.DiscountAmount.getCode()];
        this.Count = Integer.parseInt(objects[EnumMenuItemArray.Count.getCode()].toString());
    }

    public MenuItemObject(MenuItemObject object){
        this.ID = object.getID();
        this.typeName = object.getTypeName();
        this.name = object.getName();
        this.Count = object.getCount();
        this.dicountAmount = object.getDicountAmount();
        this.discountCoefficient = object.getDiscountCoefficient();
        this.price = object.getPrice();
    }

    public double singleValue() {
        return (this.price * this.discountCoefficient - this.dicountAmount);
    }

    public double value() {
        return this.singleValue() * this.Count;
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return the typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * @param typeName the typeName to set
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the discountCoefficient
     */
    public double getDiscountCoefficient() {
        return discountCoefficient;
    }

    /**
     * @param discountCoefficient the discountCoefficient to set
     */
    public void setDiscountCoefficient(double discountCoefficient) {
        this.discountCoefficient = discountCoefficient;
    }

    /**
     * @return the dicountAmount
     */
    public double getDicountAmount() {
        return dicountAmount;
    }

    /**
     * @param dicountAmount the dicountAmount to set
     */
    public void setDicountAmount(double dicountAmount) {
        this.dicountAmount = dicountAmount;
    }

    /**
     * @return the Count
     */
    public long getCount() {
        return Count;
    }

    /**
     * @param Count the Count to set
     */
    public void setCount(long Count) {
        this.Count = Count;
    }
}
