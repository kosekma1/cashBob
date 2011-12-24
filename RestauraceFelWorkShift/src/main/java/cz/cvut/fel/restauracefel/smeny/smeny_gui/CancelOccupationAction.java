package cz.cvut.fel.restauracefel.smeny.smeny_gui;

import cz.cvut.fel.restauracefel.smeny.SmenyController.SmenyController;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JTable;

/**
 * Trida akce pro zruseni obsazeni uzivatele pro kontextove menu ve formulari OverViewShiftForm. 
 * 
 * @author Martin Kosek
 */
public class CancelOccupationAction extends AbstractAction {
   
    private JTable table = null;
    private OverviewLeaderShiftForm parent = null;

    public CancelOccupationAction(OverviewLeaderShiftForm parent, JTable table) {
        super("Zrušit obsazení");        
        this.parent = parent;
        this.table = table;        
    }

    public void actionPerformed(ActionEvent e) {
        try {
            SmenyController.getInstance().cancelOccupationWorkshift(table);
            parent.reloadTable(parent.getCurrentFilter());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CancelOccupationAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(CancelOccupationAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(CancelOccupationAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
