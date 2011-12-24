package cz.cvut.fel.restauracefel.smeny.smeny_gui;

import cz.cvut.fel.restauracefel.library.service.EmptyListException;
import cz.cvut.fel.restauracefel.smeny.SmenyController.SmenyController;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JTable;

/**
 * Trida akce pro prihlaseni uzivatele na smenu z kontextove nabidky ve formulari OverViewShiftForm.
 * 
 * @author Martin Kosek
 */
public class LoginEmployeeAction extends AbstractAction {

    private Point point = new Point(550, 210);
    private ChooseEmployeeDialog chooseEmployeeDialog = null;
    private JTable table = null;
    private MainFrame mainFrame = null;
    private OverviewLeaderShiftForm parent = null;

    public LoginEmployeeAction(MainFrame mainFrame, OverviewLeaderShiftForm parent, JTable table) {
        super("Přihlásit zaměstnance");
        this.mainFrame = mainFrame;
        this.parent = parent;
        this.table = table;
    }

    public void actionPerformed(ActionEvent e) {
       int rowNumber = table.getSelectedRow(); //bude slouzit jako index pro datovou strukturu ve ktere bude ulozeno id smeny        
        if (rowNumber > -1) {
            try {
                chooseEmployeeDialog = new ChooseEmployeeDialog(mainFrame, true, rowNumber, parent);
                chooseEmployeeDialog.setLocation(point);
                chooseEmployeeDialog.setVisible(true);
            } catch (EmptyListException ex) {                
                Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {                
                Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {                
                Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {                
                Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {                
                Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {            
            SmenyController.getInstance().showMessageDialogInformation("Vyberte řádek", "Informace");
        }
    }
}
