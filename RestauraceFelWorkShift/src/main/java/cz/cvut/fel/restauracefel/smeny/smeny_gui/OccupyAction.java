package cz.cvut.fel.restauracefel.smeny.smeny_gui;

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
 * Action for make occupation of the workshift for context menu in OverViewShiftForm.
 * 
 * @author Martin Kosek
 */
public class OccupyAction extends AbstractAction {

    private Point point = new Point(550, 210);
    private JTable table = null;
    private OverviewLeaderShiftForm parent = null;
    private MainFrame mainFrame = null;
    ChooseOcuppyEmployeeDialog chooseOcuppyEmployeeDialog = null;

    public OccupyAction(MainFrame mainFrame, OverviewLeaderShiftForm parent, JTable table) {
        super("Obsadit zaměstnance");
        this.mainFrame = mainFrame;
        this.parent = parent;
        this.table = table;
    }

    public void actionPerformed(ActionEvent e) {
        int rowNumber = table.getSelectedRow();
        if (rowNumber > -1) {
            try {
                chooseOcuppyEmployeeDialog = new ChooseOcuppyEmployeeDialog(mainFrame, true, rowNumber, parent);
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
            SmenyController.getInstance().showMessageDialogInformation("Vyberte řádek", "Informace");
        }
    }
}
