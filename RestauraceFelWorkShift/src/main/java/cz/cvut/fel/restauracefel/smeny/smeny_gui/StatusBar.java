package cz.cvut.fel.restauracefel.smeny.smeny_gui;

import java.awt.Dimension;
import javax.swing.JLabel;

/**
 * Trida reprezentujici statusBar.
 *
 * @author Jarda
 */
public class StatusBar extends JLabel {

    private static final String GAP = "  ";

    /**
     * Konstruktor tridy StatusBar.
     */
    public StatusBar(){
        super();
        super.setPreferredSize(new Dimension(100, 20));
        setMessage(GAP + "Ready");
    }

    /**
     * Metoda nastavi do statusBaru zpravu zapsanou jako jeji atribut.
     *
     * @param message zprava, ktera se zapise do statusBaru
     */
    public void setMessage(String message){        
        setText(GAP + message);
    }

    /**
     * Metoda smaze obsah statusBaru.
     */
    public void clearMessage(){
        setText("");
    }
}
