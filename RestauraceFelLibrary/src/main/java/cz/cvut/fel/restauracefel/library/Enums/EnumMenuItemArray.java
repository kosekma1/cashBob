/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.library.Enums;

/**
 *
 * @author libik
 */
public enum EnumMenuItemArray {
    ItemID(0), TypeName(1), ItemName(2), ItemPrice(3), DiscountCoefficient(4), DiscountAmount(5), Count(6);
    private int code;

    /**
     * Private konstruktor
     */
    private EnumMenuItemArray(int c) {
        code = c;
    }

    /**
     */
    public int getCode() {
        return code;
    }

}
