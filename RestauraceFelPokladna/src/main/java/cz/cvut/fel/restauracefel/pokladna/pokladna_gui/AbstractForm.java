package cz.cvut.fel.restauracefel.pokladna.pokladna_gui;

import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Abstraktni trida, ze ktere dedi klientske JPanel tridy.
 *
 * @author Jarda
 * @author Tomas Hnizdil
 */
public abstract class AbstractForm extends JPanel {

    ImageIcon image = new ImageIcon("images/back.png");

    /**
     * Konstruktor tridy AbstractForm.
     */
    public AbstractForm() {
    }    

    /**
     * Metoda provadi aktualizaci vsech comboBoxu a aktualizaci tabulky.
     * Zaroven prenastavuje statusBar.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    protected void refresh() throws FileNotFoundException, NotBoundException, Exception {};

    /**
     * Metoda kontrolujici spravnost vyplnenych udaju.
     *
     * @return Vraci index urcujici vstupni komponentu, ktera obsahuje
     * neplatny vstup.
     */
    protected EnumSpravnost isValidInput() {return EnumSpravnost.JeToSpravne;};

    /**
     * Metoda cisti vsechny vstupni formulare, formular pro datum nastavuje na
     * aktualni datum a u comboBoxu nastavuje aktualni vybranou polozku na
     * prvni polozku daneho comboBoxu.
     */
    protected void clearFields() {};

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image.getImage(), 0, 0, g.getClipBounds().width, g.getClipBounds().height, this);
        }
    }

}
