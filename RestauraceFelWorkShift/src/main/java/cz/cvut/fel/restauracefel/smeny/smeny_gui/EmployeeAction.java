package cz.cvut.fel.restauracefel.smeny.smeny_gui;

import cz.cvut.fel.restauracefel.library.service.EmptyListException;
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
 * Action for login User to workshift for context menu in OverViewShiftForm.
 * 
 * @author Martin
 */
public class EmployeeAction extends AbstractAction {

    private Point point = new Point(550, 210);
    private ChooseEmployeeDialog chooseEmployeeDialog = null;
    private JTable table = null;
    private MainFrame parent = null;

    public EmployeeAction(MainFrame parent, JTable table) {
        super("Přihlásit zaměstnance");
        this.parent = parent;
        this.table = table;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            int row = table.getSelectedRow();
            chooseEmployeeDialog = new ChooseEmployeeDialog(parent, true, row, table);
            chooseEmployeeDialog.setLocation(point);
            chooseEmployeeDialog.setVisible(true);
        } catch (EmptyListException ex) {
            Logger.getLogger(EmployeeAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(EmployeeAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(EmployeeAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmployeeAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
