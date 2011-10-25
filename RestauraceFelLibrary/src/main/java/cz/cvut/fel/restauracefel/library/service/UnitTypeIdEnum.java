package cz.cvut.fel.restauracefel.library.service;

/**
 */
public enum UnitTypeIdEnum {
    GRAMS(1), KILOGRAMS(2), MILILITRES(3), DECILITRES(4), LITRES(5), BUTTS50(6), BUTTS30(7);
    private int code;

    /**
     * Private konstruktor
     */
    private UnitTypeIdEnum(int c) {
        code = c;
    }

    /**
     */
    public int getCode() {
        return code;
    }
}