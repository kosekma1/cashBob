package cz.cvut.fel.restauracefel.smeny.smeny_gui;

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
 * Action for make occupation of the workshift for context menu in OverViewShiftForm.
 * 
 * @author Martin Kosek
 */
public class OccupyAction extends AbstractAction {

    private Point point = new Point(550, 210);
    private JTable table = null;
    private MainFrame parent = null;
    ChooseOcuppyEmployeeDialog chooseOcuppyEmployeeDialog = null;

    public OccupyAction(MainFrame parent, JTable table) {
        super("Obsadit zaměstnance");
        this.table = table;
    }

    public void actionPerformed(ActionEvent e) {
        int rowNumber = table.getSelectedRow();
        if (rowNumber > -1) {
            try {
                chooseOcuppyEmployeeDialog = new ChooseOcuppyEmployeeDialog(parent, true, rowNumber, table);
                chooseOcuppyEmployeeDialog.setLocation(point);
                chooseOcuppyEmployeeDialog.setVisible(true);
            } catch (RemoteException ex) {
                Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
            }
        } else {
            parent.showMessageDialogInformation("Vyberte řádek", "Informace");
        }
    }
}
