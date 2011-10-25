package cz.cvut.fel.restauracefel.manager.manager_gui;

import cz.cvut.fel.restauracefel.manager.ManagerController.MenuItemTypeResult;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import cz.cvut.fel.restauracefel.manager_service.ResultTableModel;

/**
 * Trida reprezentujici dialog pro praci s kategoriemi polozek menu (MenuItemType).
 *
 * @author Jarda
 */
public class MenuItemTypeDialog extends AbstractDialog {

    private JScrollPane paneMenuItemType = null;
    private JScrollPane paneTable = null;
    private JTable table = null;
    private MenuItemTypeForm menuItemTypeForm = null;
    private int row = -1;

    private ManagerViewController view;

    /**
     * Konstruktor tridy MenuItemTypeDialog.
     *
     * @param parent instance tridy JFrame jenz vytvorila tento formular
     * @param modal urcuje, zda bude okno modalni     
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public MenuItemTypeDialog(JFrame parent, boolean modal) {
        super(parent, modal);
        super.setTitle("Kategorie položek menu");

        view = ManagerViewController.getInstance();
      
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int y = (int) ((dim.getHeight() - 550) / 2);
        int x = (int) ((dim.getWidth() - 450) / 2);
        setBounds(x, y, 450, 550);
        initComponents();        
        initTable();
        refresh();
    }

    /**
     * Metoda inicializuje komponenty na JDialogu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    @Override
    protected void initComponents() {
        paneMenuItemType = new JScrollPane();
        paneTable = new JScrollPane();
        table = new JTable();
        menuItemTypeForm = new MenuItemTypeForm();
        paneMenuItemType.getViewport().add(menuItemTypeForm);
        paneTable.getViewport().add(table);
        Container con = getContentPane();
        con.setLayout(new BorderLayout());
        con.add(paneMenuItemType, BorderLayout.NORTH);
        con.add(paneTable, BorderLayout.CENTER);

        menuItemTypeForm.getInsertButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertAction();
            }
        });

        menuItemTypeForm.getDeleteButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteAction();
            }
        });
    }

    /**
     * Metoda pro vkladani novych kategorii polozek menu do systemu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public void insertAction() {
        String name = menuItemTypeForm.getTextFieldName().getText();
        int menuItemTypeId = -1;

        if(row != -1){
            menuItemTypeId = (Integer) table.getModel().getValueAt(row, 0);
        }

        if(view.insertMenuItemType(name, menuItemTypeId) == MenuItemTypeResult.Succesful){
            menuItemTypeForm.clearFields();
            refresh();
        }
    }

    /**
     * Metoda pro mazani kategorii polozek menu ze systemu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public void deleteAction() {
        row = table.getSelectedRow();
        int menuItemTypeId = (Integer) table.getModel().getValueAt(row, 0);

        view.deleteMenuItemType(menuItemTypeId);

        refresh();
    }

    /**
     * Metoda inicializujici tabulku na danem JDialogu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    @Override
    protected void initTable() {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                row = table.getSelectedRow();
                if (row == -1) {
                    menuItemTypeForm.clearFields();
                    menuItemTypeForm.getInsertButton().setEnabled(true);
                    menuItemTypeForm.getDeleteButton().setEnabled(false);
                    menuItemTypeForm.getInsertButton().setText("Vytvořit kategorii");
                    menuItemTypeForm.getInsertButton().setToolTipText("Vytvořit novou kategorii položek menu");
                } else {
                    menuItemTypeForm.getTextFieldName().setText((String)table.getModel().getValueAt(row, 1));
                    menuItemTypeForm.getDeleteButton().setEnabled(true);
                    menuItemTypeForm.getInsertButton().setText("Aktualizovat");
                }
            }
        });
        //refresh();
    }

    /**
     * Metoda aktualizujici zaznamy v dane tabulce na tomto JDialogu.
     *
     * @throws java.io.FileNotFoundException
     * @throws java.rmi.NotBoundException
     * @throws java.rmi.RemoteException
     */
    @Override
    protected void refresh() {
        ResultTableModel rtm = new ResultTableModel();
        Object[][] menuMenus = view.getMenuItemTypes();
        rtm.setColumnData(ResultTableModel.namesMenuItemType);
        rtm.setTableData(menuMenus);
        table.setModel(rtm);
    }
}
