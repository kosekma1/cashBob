package cz.cvut.fel.restauracefel.manager.manager_gui;

import cz.cvut.fel.restauracefel.manager.ManagerController.TableDialogResult;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import cz.cvut.fel.restauracefel.manager_service.ResultTableModel;
import cz.cvut.fel.restauracefel.library.service.Validator;

/**
 * Trida reprezentujici dialog pro evidenci stolu.
 *
 * @author Jarda
 */
public class TableDialog extends JDialog {

    private JScrollPane paneXTable = null;
    private JScrollPane paneTable = null;
    private JTable table = null;
    private TableForm tableForm = null;
    private int row = -1;

    private ManagerViewController view;

    /**
     * Konstruktor tridy TableDialog.
     *
     * @param parent instance tridy JFrame jenz vytvorila tento dialog
     * @param modal urcuje, zda bude dialog modalni
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public TableDialog(JFrame parent, boolean modal) {
        super(parent, modal);
        super.setTitle("Evidence stolů");

        view = ManagerViewController.getInstance();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int y = (int) ((dim.getHeight() - 350) / 2);
        int x = (int) ((dim.getWidth() - 400) / 2);
        setBounds(x, y, 400, 350);
        initComponents();
        initTable();
    }

    /**
     * Inicializuje graficke komponenty, nastavuje akce pro tlacitka.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    private void initComponents() {
        paneXTable = new JScrollPane();
        paneTable = new JScrollPane();
        table = new JTable();
        tableForm = new TableForm();
        paneXTable.getViewport().add(tableForm);
        paneTable.getViewport().add(table);
        Container con = getContentPane();
        con.setLayout(new BorderLayout());
        con.add(paneXTable, BorderLayout.NORTH);
        con.add(paneTable, BorderLayout.CENTER);

        tableForm.getInsertButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertAction();
            }
        });

        tableForm.getDeleteButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteAction();
            }
        });        
    }

    /**
     * Vklada zaznam o novem stolu do databaze.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    private void insertAction() {
        String tableN = tableForm.getTableNumberTextField().getText();
        String places = tableForm.getPlacesTextField().getText();
        int tableId = -1;

        if(row != -1){
            tableId = (Integer) table.getModel().getValueAt(row, 0);
        }

        if(view.insertTable(tableN, places, tableId) == TableDialogResult.Succesful){
            tableForm.clearFields();
            refresh();
        }
    }

    /**
     * Maze zaznam o stolu z databaze.
     *
     * @throws java.io.FileNotFoundException
     * @throws java.rmi.NotBoundException
     * @throws java.rmi.RemoteException
     */
    private void deleteAction() {
        row = table.getSelectedRow();        
        int tableId = (Integer) table.getModel().getValueAt(row, 0);

        view.deleteTable(tableId);

        refresh();
    }

    /**
     * Metoda inicializujici tabulku.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    private void initTable() {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                row = table.getSelectedRow();
                if (row == -1) {
                    tableForm.clearFields();
                    tableForm.getDeleteButton().setEnabled(false);
                    tableForm.getInsertButton().setText("Vložit záznam");
                    tableForm.getInsertButton().setToolTipText("Vložit nový záznam Menu");
                } else {
                    tableForm.getTableNumberTextField().setText(String.valueOf(table.getModel().getValueAt(row, 1)));
                    tableForm.getPlacesTextField().setText(String.valueOf(table.getModel().getValueAt(row, 2)));
                    tableForm.getDeleteButton().setEnabled(true);
                    tableForm.getInsertButton().setText("Aktualizovat záznam");
                    tableForm.getInsertButton().setToolTipText("Smazat vybraný záznam Menu");
                }
            }
        });
        refresh();
    }

    /**
     * Metoda aktualizujici tabulku.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    private void refresh() {
        ResultTableModel rtm = new ResultTableModel();
        rtm.setColumnData(ResultTableModel.namesTable);
        Object[][] tables = view.getTables();
        rtm.setTableData(tables);
        table.setModel(rtm);
    }
}
