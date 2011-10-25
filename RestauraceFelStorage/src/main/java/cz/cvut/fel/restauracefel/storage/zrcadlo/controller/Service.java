package cz.cvut.fel.restauracefel.storage.zrcadlo.controller;

/**
 *
 * @author Vojta
 */
public class Service {

    private Service() {

    }

    public static double spocitejObjem(double m, double hustota) {
        return m / hustota;
    }

    public static double spocitejHmotnost(double v, double hustota) {
        return v * hustota;
    }

    public static double zaokrouhliNaJednotky(double x) {
        return (double) ((int) x);
    }

}
