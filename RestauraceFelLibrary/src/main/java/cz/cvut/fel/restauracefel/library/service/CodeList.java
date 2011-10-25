package cz.cvut.fel.restauracefel.library.service;

import cz.cvut.fel.restauracefel.library.service.UnitTypeIdEnum;

/**
 * Tato trida se stara o prevadeni mernych jednotek.
 *
 * @author Jarda
 */
public class CodeList {
    /**
     * Soukromy konstruktor tridy CodeList.
     */
    private CodeList(){
    }

    /**
     * Metoda prevadi mnozstvi suroviny (atribut quantity) z jednotky
     * s identifikatorem "fromUnitTypeId" na jednotku s identifikatorem
     * "toUnitTypeId".
     *
     * @param fromUnitTypeId identifikator vychozi jednotky
     * @param toUnitTypeId identifikator koncove jednotky
     * @param quantity mnozstvi suroviny ve vychozi jednotce
     * @return mnozstvi suroviny v koncove jednotce
     */
    public static double transfer(int fromUnitTypeId, int toUnitTypeId, double quantity){
        if (fromUnitTypeId == toUnitTypeId){
            return quantity;
        }
        if (fromUnitTypeId == UnitTypeIdEnum.GRAMS.getCode() && toUnitTypeId == UnitTypeIdEnum.KILOGRAMS.getCode()){
            return fromGramsToKilograms(quantity);
        }
        if (fromUnitTypeId == UnitTypeIdEnum.KILOGRAMS.getCode() && toUnitTypeId == UnitTypeIdEnum.GRAMS.getCode()){
            return fromKilogramsToGrams(quantity);
        }

        double litres = 0;
        if (fromUnitTypeId == UnitTypeIdEnum.MILILITRES.getCode()) litres = fromMililitresToLitres(quantity);
        if (fromUnitTypeId == UnitTypeIdEnum.DECILITRES.getCode()) litres = fromDecilitresToLitres(quantity);
        if (fromUnitTypeId == UnitTypeIdEnum.LITRES.getCode()) litres = quantity;
        if (fromUnitTypeId == UnitTypeIdEnum.BUTTS50.getCode()) litres = fromButts50ToLitres(quantity);
        if (fromUnitTypeId == UnitTypeIdEnum.BUTTS30.getCode()) litres = fromButts30ToLitres(quantity);

        if (toUnitTypeId == UnitTypeIdEnum.MILILITRES.getCode()) return (fromLitresToMililitres(litres));
        if (toUnitTypeId == UnitTypeIdEnum.DECILITRES.getCode()) return (fromLitresToDecilitres(litres));
        if (toUnitTypeId == UnitTypeIdEnum.LITRES.getCode()) return (litres);
        if (toUnitTypeId == UnitTypeIdEnum.BUTTS50.getCode()) return (fromLitresToButts50(litres));
        if (toUnitTypeId == UnitTypeIdEnum.BUTTS30.getCode()) return (fromLitresToButts30(litres));
        return -1;
    }

    /**
     * Metoda prevadi kilogramy na gramy.
     *
     * @param kilos kilogramu
     * @return gramu
     */
    public static double fromKilogramsToGrams(double kilos){
        return kilos * 1000;
    }

    /**
     * Metoda prevadi gramy na kilogramy.
     *
     * @param grams gramu
     * @return kilogramu
     */
    public static double fromGramsToKilograms(double grams){
        return grams / 1000;
    }

    /**
     * Metoda prevadi 50ti litrove sudy na litry.
     *
     * @param butts sudu
     * @return litru
     */
    public static double fromButts50ToLitres(double butts){
        return butts * 50;
    }

    /**
     * Metoda prevadi litry na 50ti litrove sudy.
     *
     * @param litres litru
     * @return sudu
     */
    public static double fromLitresToButts50(double litres){
        return litres / 50;
    }

    /**
     * Metoda prevadi 30ti litrove sudy na litry.
     *
     * @param butts sudu
     * @return litru
     */
    public static double fromButts30ToLitres(double butts){
        return butts * 30;
    }

    /**
     * Metoda prevadi litry na 30ti litrove sudy.
     *
     * @param litres litru
     * @return sudu
     */
    public static double fromLitresToButts30(double litres){
        return litres / 30;
    }

    /**
     * Metoda prevadi litry na decilitry.
     *
     * @param litres litru
     * @return decilitru
     */
    public static double fromLitresToDecilitres(double litres){
        return litres * 10;
    }

    /**
     * Metoda prevadi decilitry na litry.
     *
     * @param decilitres decilitru
     * @return litru
     */
    public static double fromDecilitresToLitres(double decilitres){
        return decilitres / 10;
    }

    /**
     * Metoda prevadi litry na mililitry.
     *
     * @param litres litru
     * @return mililitru
     */
    public static double fromLitresToMililitres(double litres){
        return litres * 1000;
    }

    /**
     * Metoda prevadi mililitry na litry
     *
     * @param mililitres mililitru
     * @return litru
     */
    public static double fromMililitresToLitres(double mililitres){
        return mililitres / 1000;
    }
}