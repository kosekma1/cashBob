package cz.cvut.fel.restauracefel.smeny.smeny_gui;

import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import cz.cvut.fel.restauracefel.library.service.EmptyListException;

/**
 * Abstraktni trida, ze ktere dedi klientske JDialog tridy.
 *
 * @author Jarda
 * @author Tomas Hnizdil
 */
public abstract class AbstractDialog extends JDialog {

    /**
     * Konstruktor tridy AbstractDialog.
     */
    public AbstractDialog(JFrame parent, boolean modal) {
        super(parent, modal);
    }

    /**
     * Metoda provadi aktualizaci vsech comboBoxu a aktualizaci tabulky na danem
     * JDialogu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     * @throws service.EmptyListException
     */
    protected abstract void refresh() throws RemoteException, NotBoundException, FileNotFoundException, EmptyListException;
}
