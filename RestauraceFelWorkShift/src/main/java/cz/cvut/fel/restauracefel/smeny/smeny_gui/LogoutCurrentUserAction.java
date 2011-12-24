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
 * Trida akce pro odhlaseni aktualne prihlaseneho uzivatele ze smeny v kontextove nabidce pro formular OverViewShiftForm.
 * 
 * @author Martin Kosek
 */
public class LogoutCurrentUserAction extends AbstractAction {

    private JTable table = null;
    private OverviewEmployeeShiftForm parent = null;    

    public LogoutCurrentUserAction(OverviewEmployeeShiftForm parent, JTable table) {
        super("Odhlásit");
        this.parent = parent;
        this.table = table;        
    }

    public void actionPerformed(ActionEvent e) {
        int rowNumber = table.getSelectedRow(); //bude slouzit jako index pro datovou strukturu ve ktere bude ulozeno id smeny        
        if (rowNumber > -1) {
            try {
                int idWorkshift = SmenyController.getInstance().getWorkShiftIdFromOverViewTable(rowNumber);
                SmenyController.getInstance().logoutCurrentUserFromWorkShift(idWorkshift);
                parent.reloadTable(parent.getCurrentFilter());
            } catch (FileNotFoundException ex) {                
                Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {                
                Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {                
                Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {                
                Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            SmenyController.getInstance().showMessageDialogInformation("Vyberte řádek", "Informace");
        }
    }
}
