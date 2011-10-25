package cz.cvut.fel.restauracefel.manager.manager_gui;

import cz.cvut.fel.restauracefel.manager.ManagerController.RecipeDialogResult;
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
 *
 * @author Jarda
 */
public class RecipeDialog extends AbstractDialog {

    private JScrollPane paneRecipe = null;
    private JScrollPane paneTable = null;
    private JTable table = null;
    private RecipeForm recipeForm = null;
    private int row = -1;
    private int menuItemId;

    private ManagerViewController view;

    /**
     * Konstruktor tridy RecipeDialog.
     *
     * @param parent instance tridy JFrame jenz vytvorila tento formular
     * @param modal urcuje, zda bude dialog modalni
     * @param menuItemId ID polozky menu, pro kterou se ma definovat receptura
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public RecipeDialog(JFrame parent, boolean modal, int menuItemId) {
        super(parent, modal);
        super.setTitle("Tvorba Menu");

        view = ManagerViewController.getInstance();

        this.menuItemId = menuItemId;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int y = (int) ((dim.getHeight() - 550) / 2);
        int x = (int) ((dim.getWidth() - 400) / 2);
        setBounds(x, y, 400, 550);
        initComponents();
        recipeForm.getMenuItemLabel().setText(view.getMenuItemNameById(menuItemId));
        initTable();
        setComboBoxesListeners(recipeForm.getMaterialTypeComboBox(), recipeForm.getMaterialComboBox(), recipeForm.getUnitTypeComboBox());
        refresh();        
    }

    /**
     * Metoda pro inicializaci komponent dialogu.
     */
    protected void initComponents() {
        paneRecipe = new JScrollPane();
        paneTable = new JScrollPane();
        table = new JTable();
        recipeForm = new RecipeForm();
        paneRecipe.getViewport().add(recipeForm);
        paneTable.getViewport().add(table);
        Container con = getContentPane();
        con.setLayout(new BorderLayout());
        con.add(paneRecipe, BorderLayout.NORTH);
        con.add(paneTable, BorderLayout.CENTER);

        recipeForm.getInsertButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertAction();
            }
        });

        recipeForm.getDeleteButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteAction();
            }
        });        
    }    

    /**
     * Metoda pro pridani suroviny do receptury dane polozky menu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    private void insertAction() {
        String quantity = recipeForm.getQuantityTextField().getText();
        String material = (String) recipeForm.getMaterialComboBox().getSelectedItem();
        String fromUnitType = (String) recipeForm.getUnitTypeComboBox().getSelectedItem();
        int usedMaterialId = -1;

        if(row != -1){
            usedMaterialId = (Integer)table.getModel().getValueAt(row, 0);
        }

        if(view.insertUsedMaterial(menuItemId, quantity, material, fromUnitType, usedMaterialId) == RecipeDialogResult.Succesful){
            refreshTable();
            recipeForm.clearFields();
        }
    }

    /**
     * Metoda provadi smazani vybraneho vybrane slozky receptury.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    private void deleteAction() {
        row = table.getSelectedRow();
        int usedMaterialId = (Integer)table.getModel().getValueAt(row, 0);

        view.deleteUsedMaterial(usedMaterialId);

        refreshTable();
    }

    /**
     * Metoda inicializujici tabulku.
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
                    recipeForm.clearFields();
                    recipeForm.getDeleteButton().setEnabled(false);
                    recipeForm.getInsertButton().setText("Vložit do receptury");
                    recipeForm.getInsertButton().setToolTipText("Vložit novou surovinu do receptury");
                } else {
                    String material = (String) table.getModel().getValueAt(row, 1);
                    recipeForm.getMaterialComboBox().setSelectedItem(material);
                    recipeForm.getMaterialTypeComboBox().setSelectedItem(view.getMaterialTypeNameByMaterialName(material));
                    recipeForm.getQuantityTextField().setText(String.valueOf(table.getModel().getValueAt(row, 2)));
                    recipeForm.getUnitTypeComboBox().setSelectedItem((String) table.getModel().getValueAt(row, 3));
                    recipeForm.getDeleteButton().setEnabled(true);
                    recipeForm.getInsertButton().setText("Aktualizovat recepturu");
                    recipeForm.getInsertButton().setToolTipText("Aktualizovat stávající záznam o surovině receptury");
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
        setComboBoxModel(view.getMaterialTypeNames(), recipeForm.getMaterialTypeComboBox());
        setComboBoxModel(view.getUnitTypeAbbrs(), recipeForm.getUnitTypeComboBox());
        refreshTable();
    }    

    /**
     * Metoda pro aktualizaci tabulky daneho JDialogu.
     *
     * @throws java.io.FileNotFoundException
     * @throws java.rmi.NotBoundException
     * @throws java.rmi.RemoteException
     */
    protected void refreshTable() {
        ResultTableModel rtm = new ResultTableModel();
        Object[][] recipes = view.getUsedMaterialsByMenuItem(menuItemId);
        rtm.setColumnData(ResultTableModel.namesRecipe);
        rtm.setTableData(recipes);
        table.setModel(rtm);
    }

}
