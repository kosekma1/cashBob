package cz.cvut.fel.restauracefel.manager.manager_gui;

import cz.cvut.fel.restauracefel.manager.ManagerController.MenuResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.manager_service.ResultTableModel;
import cz.cvut.fel.restauracefel.manager_service.ServiceFacade;
import cz.cvut.fel.restauracefel.library.service.Validator;

/**
 * Trida reprezentujici GUI pro okno, kde se vytvareji menu.
 *
 * @author Jarda
 */
public class MenuForm extends AbstractForm {
        
    private JTable table = null;
    private JFrame parent = null;
    private StatusBar statusBar = null;
    private int row = -1;

    private ManagerViewController view;

    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    /**
     * Konstruktor tridy MenuForm.
     *
     * @param parent instance tridy JFrame jenz vytvorila tento formular
     * @param bar statusBar, do ktereho budou vypisovany popisky
     * @throws java.io.FileNotFoundException
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     */
    public MenuForm(JFrame parent, StatusBar bar) {
        view = ManagerViewController.getInstance();

        this.parent = parent;
        this.statusBar = bar;
        initComponents();
        initTable();        
        refresh();
        clearFields();
    }

    /**
     * Metoda provadi aktualizaci vsech comboBoxu a aktualizaci tabulky.
     * Zaroven prenastavuje statusBar.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    @Override
    public void refresh() {
        setComboBoxModel(view.getUsernames(), jComboBoxUser);
        refreshTable();
        statusBar.setMessage("Tento formulář slouží k editaci a úpravě záznamů o menu.");
    }

    /**
     * Metoda inicializujici tabulku.
     */
    @Override
    protected void initTable() {
        table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                row = table.getSelectedRow();
                if (row == -1) {
                    clearFields();
                    jButtonDelete.setEnabled(false);
                    jButtonMenuItems.setEnabled(false);
                    jButtonInsert.setText("Vložit záznam");
                    jButtonInsert.setToolTipText("Vložit nový záznam menu");
                    jButtonPrint.setEnabled(false);
                    statusBar.setMessage("Založit nové menu.");
                } else {
                    jTextFieldName.setText((String) table.getModel().getValueAt(row, 1));
                    jTextFieldDate.setText((String) table.getModel().getValueAt(row, 2));
                    jComboBoxUser.setSelectedItem((String) table.getModel().getValueAt(row, 3));
                    jButtonDelete.setEnabled(true);
                    jButtonMenuItems.setEnabled(true);
                    jButtonPrint.setEnabled(true);
                    jButtonInsert.setText("Aktualizovat záznam");
                    jButtonInsert.setToolTipText("Aktualizovat vybraný záznam menu");
                    statusBar.setMessage("Menu ID: "+table.getModel().getValueAt(row, 0)+", \""+table.getModel().getValueAt(row, 1)+"\"");
                }
            }
        });
        //refreshTable();
    }

    /**
     * Metoda aktualizuje tabulku.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    @Override
    protected void refreshTable() {
        ResultTableModel rtm = new ResultTableModel();
        rtm.setColumnData(ResultTableModel.namesMenu);
        Object[][] menus = view.getMenus();
        rtm.setTableData(menus);
        table.setModel(rtm);
    }

    /**
     * Metoda navraci tabulku (instanci tridy JTable).
     *
     * @return instance tridy JTable
     */
    public JTable getTable() {
        return table;
    }

    /**
     * Metoda cisti vstupni pole na tomto formulari.
     */
    @Override
    protected void clearFields() {
        Validator.clearTextField(jTextFieldName);
        Validator.clearDateField(jTextFieldDate);
        Validator.clearComboBox(jComboBoxUser);

        jButtonDelete.setEnabled(false);
        jButtonMenuItems.setEnabled(false);
        jButtonPrint.setEnabled(false);

        table.clearSelection();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelName = new javax.swing.JLabel();
        jLabelDate = new javax.swing.JLabel();
        jLabelUser = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldDate = new javax.swing.JTextField();
        jComboBoxUser = new javax.swing.JComboBox();
        jButtonInsert = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();
        jButtonMenuItems = new javax.swing.JButton();
        jButtonPrint = new javax.swing.JButton();

        jLabelName.setText("Název menu");

        jLabelDate.setText("Datum vytvoření menu");

        jLabelUser.setText("Vytvořil");

        jComboBoxUser.setToolTipText("Uživatel, který vytvořil toto menu.");

        jButtonInsert.setText("Vytvořit záznam");
        jButtonInsert.setToolTipText("Vložit nový záznam Menu");
        jButtonInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertActionPerformed(evt);
            }
        });

        jButtonDelete.setText("Smazat záznam");
        jButtonDelete.setToolTipText("Smazat vybraný záznam Menu");
        jButtonDelete.setEnabled(false);
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonClear.setText("Vyčistit formulář");
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        jButtonMenuItems.setText("Nastavit položky menu");
        jButtonMenuItems.setToolTipText("Přiřadit označenému menu položky menu.");
        jButtonMenuItems.setEnabled(false);
        jButtonMenuItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMenuItemsActionPerformed(evt);
            }
        });

        jButtonPrint.setText("Vytisknout menu");
        jButtonPrint.setToolTipText("Tisk označeného menu.");
        jButtonPrint.setEnabled(false);
        jButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonPrint)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDate)
                            .addComponent(jLabelName)
                            .addComponent(jLabelUser))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxUser, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldDate)
                            .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonInsert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jButtonMenuItems)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelName)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonInsert))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDate)
                    .addComponent(jTextFieldDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUser)
                    .addComponent(jComboBoxUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonMenuItems)
                    .addComponent(jButtonPrint))
                .addContainerGap(19, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        String name = jTextFieldName.getText();
        Date date = Validator.isValidDate(jTextFieldDate.getText());
        String username = (String) jComboBoxUser.getSelectedItem();
        int menuId = -1;

        if(row != -1){
            menuId = (Integer)table.getModel().getValueAt(row, 0);
        }

        if(view.insertMenu(name, date, username, menuId) == MenuResult.Succesful){
            refreshTable();
            clearFields();
        }
    }//GEN-LAST:event_jButtonInsertActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        row = table.getSelectedRow();
        int menuId = (Integer) table.getModel().getValueAt(row, 0);

        view.deleteMenu(menuId);

        refreshTable();
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        clearFields();
    }//GEN-LAST:event_jButtonClearActionPerformed

    private void jButtonMenuItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMenuItemsActionPerformed
        int menuId = (Integer)table.getModel().getValueAt(row, 0);

        MenuMenuItemDialog mmd = new MenuMenuItemDialog(parent, true, menuId);
        mmd.setVisible(true);
    }//GEN-LAST:event_jButtonMenuItemsActionPerformed

    private void jButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintActionPerformed
        String title = (String) table.getModel().getValueAt(row, 1);
        int menuId = (Integer) table.getModel().getValueAt(row, 0);
        
        view.printMenu(title, menuId);
}//GEN-LAST:event_jButtonPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonInsert;
    private javax.swing.JButton jButtonMenuItems;
    private javax.swing.JButton jButtonPrint;
    private javax.swing.JComboBox jComboBoxUser;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelUser;
    private javax.swing.JTextField jTextFieldDate;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables
    

}
