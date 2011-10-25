package cz.cvut.fel.restauracefel.manager.manager_gui;

import cz.cvut.fel.restauracefel.manager.ManagerController.MenuMenuItemResult;
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
import cz.cvut.fel.restauracefel.library.service.Validator;

/**
 * Trida reprezentujici JDialog pro evidenci polozek menu v ramci nektereho
 * menu.
 *
 * @author Jarda
 */
public class MenuMenuItemDialog extends AbstractDialog {

    private JScrollPane paneMenuItem = null;
    private JScrollPane paneTable = null;
    private JTable table = null;
    private MenuMenuItemForm menuMenuItemForm = null;
    private int row = -1;
    private int menuId;

    private ManagerViewController view;

    /**
     * Konstruktor tridy MenuMenuItemDialog.
     *
     * @param parent instance tridy JFrame jenz vytvorila tento formular
     * @param modal urcuje, zda bude okno modalni
     * @param menuId ID menu, pro ktere se maji evidovat polozky
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public MenuMenuItemDialog(JFrame parent, boolean modal, int menuId) {
        super(parent, modal);
        super.setTitle("Přiřazení položek menu");
        this.menuId = menuId;

        view = ManagerViewController.getInstance();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int y = (int) ((dim.getHeight() - 550) / 2);
        int x = (int) ((dim.getWidth() - 450) / 2);
        setBounds(x, y, 450, 550);
        initComponents();
        menuMenuItemForm.getMenuLabel().setText(view.getMenuNameById(menuId));
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
        paneMenuItem = new JScrollPane();
        paneTable = new JScrollPane();
        table = new JTable();
        menuMenuItemForm = new MenuMenuItemForm();
        paneMenuItem.getViewport().add(menuMenuItemForm);
        paneTable.getViewport().add(table);
        Container con = getContentPane();
        con.setLayout(new BorderLayout());
        con.add(paneMenuItem, BorderLayout.NORTH);
        con.add(paneTable, BorderLayout.CENTER);

        menuMenuItemForm.getInsertButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertAction();
            }
        });

        menuMenuItemForm.getDeleteButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteAction();
            }
        });
    }

    /**
     * Metoda pro vytvoreni noveho zaznamu o prirazeni dane polozky k danemu menu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    private void insertAction() {
        String name = (String)menuMenuItemForm.getMenuItemComboBox().getSelectedItem();

        if(view.insertMenuMenuItem(menuId, name) == MenuMenuItemResult.Succesful){
            menuMenuItemForm.clearFields();
            refresh();
        }
    }

    /**
     * Metoda provadi smazani vybrane vazby mezi polozkou menu a menu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    private void deleteAction() {
        row = table.getSelectedRow();
        String menuItemName = String.valueOf(table.getModel().getValueAt(row, 2));

        view.deleteMenuMenuItem(menuId, menuItemName);

        refresh();
    }

    /**
     * Metoda inicializujici tabulku na danem JDialogu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    protected void initTable() {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                row = table.getSelectedRow();
                if (row == -1) {
                    menuMenuItemForm.clearFields();
                    menuMenuItemForm.getInsertButton().setEnabled(true);
                    menuMenuItemForm.getDeleteButton().setEnabled(false);
                    menuMenuItemForm.getInsertButton().setText("Vložit položku menu");
                    menuMenuItemForm.getInsertButton().setToolTipText("Vložit položku do menu.");
                } else {                   
                    menuMenuItemForm.getMenuItemComboBox().setSelectedItem((String) table.getModel().getValueAt(row, 1));
                    menuMenuItemForm.getDeleteButton().setEnabled(true);
                    menuMenuItemForm.getInsertButton().setEnabled(false);
                }
            }
        });
        //refresh();
    }

    /**
     * Metoda provadi aktualizaci vsech comboBoxu a aktualizaci tabulky.
     * Zaroven prenastavuje statusBar.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    protected void refresh() {
        setComboBoxModel(view.getMenuItemNames(), menuMenuItemForm.getMenuItemComboBox());
        refreshTable();
    }

    /**
     * Metoda aktualizujici zaznamy v dane tabulce na tomto JDialogu.
     *
     * @throws java.io.FileNotFoundException
     * @throws java.rmi.NotBoundException
     * @throws java.rmi.RemoteException
     */
    protected void refreshTable() {
        ResultTableModel rtm = new ResultTableModel();
        Object[][] menuMenus = view.getMenuItemsByMenuId(menuId);
        rtm.setColumnData(ResultTableModel.namesMenuMenuItem);
        rtm.setTableData(menuMenus);
        table.setModel(rtm);
    }
}
