package cz.cvut.fel.restauracefel.library.service;

/**
 * Trida vyjimky vyvolavana v pripade nacteni prazdneho seznamu do komponenty JList
 *
 * @author Tomas Hnizdil
 */
public class EmptyListException extends Exception {

    private String title = "";
    private String message = "";

    /**
     * Konstruktor tridy EmptyListException
     *
     * @param title titulek chybove hlasky
     * @param message obsah chybove hlasky
     */
    public EmptyListException(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
