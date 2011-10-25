package cz.cvut.fel.restauracefel.manager.manager_gui;

import cz.cvut.fel.restauracefel.manager.ManagerController.UserFormResult;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import cz.cvut.fel.restauracefel.manager_service.ResultTableModel;
import cz.cvut.fel.restauracefel.library.service.Validator;

/**
 * Trida reprezentujici GUI formular pro editaci uzivatelu systemu.
 *
 * @author Jarda
 */
public class UserForm extends AbstractForm {

    private ManagerViewController view;

    private JTable table = null;
    private UserRoleDialog userRoleDialog = null;
    private StatusBar statusBar = null;
    private int row = -1;
    private JFrame parent = null;

    /**
     * Konstruktor tridy UserForm.
     *
     * @param parent
     * @param bar
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public UserForm(JFrame parent, StatusBar bar) {
        this.parent = parent;
        this.statusBar = bar;

        view = ManagerViewController.getInstance();
        
        initComponents();        
        initTable();
        refresh();
        clearFields();
    }

    /**
     * Metoda provadi aktualizaci tabulky.
     * Zaroven prenastavuje statusBar.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public void refresh() {
        refreshTable();
        statusBar.setMessage("Tento formulář slouží k editaci a úpravě záznamů o zaměstnancích.");
    }                

    /**
     * Metoda inicializujici tabulku.          
     */
    protected void initTable(){
        table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e) {
                row = table.getSelectedRow();
                if (row == -1){
                    clearFields();
                } else {
                    jTextFieldName.setText((String)table.getModel().getValueAt(row, 1));
                    jTextFieldSurname.setText((String)table.getModel().getValueAt(row, 2));
                    jTextFieldRoles.setText((String)table.getModel().getValueAt(row, 3));
                    jTextFieldPIN.setText((String)table.getModel().getValueAt(row, 4));
                    jTextFieldLogin.setText((String)table.getModel().getValueAt(row, 5));
                    jTextFieldOwnPasswd.setText((String)table.getModel().getValueAt(row, 6));
                    jButtonDelete.setEnabled(true);
                    jButtonRole.setEnabled(true);
                    jButtonInsert.setText("Změnit záznam o zaměstnanci");
                    statusBar.setMessage("Zaměstnanec ID: "+table.getValueAt(row, 0)+", \""+table.getValueAt(row, 2)+", "+table.getModel().getValueAt(row, 1)+"\"");
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
    protected void refreshTable() {
        ResultTableModel rtm = new ResultTableModel();        
        Object[][] users = view.getUsers();
        rtm.setColumnData(ResultTableModel.namesUser);
        rtm.setTableData(users);
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
     * Metoda cisti vsechny vstupni formulare, formular pro datum nastavuje na
     * aktualni datum a u comboBoxu nastavuje aktualni vybranou polozku na
     * prvni polozku daneho comboBoxu.
     */
    protected void clearFields(){
        Validator.clearTextField(jTextFieldName);
        Validator.clearTextField(jTextFieldSurname);
        Validator.clearTextField(jTextFieldPIN);
        Validator.clearTextField(jTextFieldLogin);
        jTextFieldPasswd.setText(view.getDefaultPassword());
        Validator.clearTextField(jTextFieldRoles);

        jButtonDelete.setEnabled(false);
        jButtonRole.setEnabled(false);
        jButtonInsert.setText("Vložit záznam o zaměstnanci");
        statusBar.setMessage("Vložit nový záznam o zaměstnanci.");

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
        jLabelSurname = new javax.swing.JLabel();
        jLabelPIN = new javax.swing.JLabel();
        jLabelLogin = new javax.swing.JLabel();
        jLabelPasswd = new javax.swing.JLabel();
        jTextFieldLogin = new javax.swing.JTextField();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldSurname = new javax.swing.JTextField();
        jTextFieldPIN = new javax.swing.JTextField();
        jButtonInsert = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jLabelRoles = new javax.swing.JLabel();
        jTextFieldRoles = new javax.swing.JTextField();
        jButtonRole = new javax.swing.JButton();
        jTextFieldPasswd = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldOwnPasswd = new javax.swing.JTextField();

        jLabelName.setText("Jméno");

        jLabelSurname.setText("Příjmení");

        jLabelPIN.setText("PIN");

        jLabelLogin.setText("Uživatelské jméno");

        jLabelPasswd.setText("Výchozí heslo");
        jLabelPasswd.setToolTipText("Výchozí heslo, které se uživateli nastaví\n při vytváření jeho uživatelského záznamu.");

        jButtonInsert.setText("Vložit záznam o zaměstnanci");
        jButtonInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertActionPerformed(evt);
            }
        });

        jButtonClear.setText("Vyčistit formulář");
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        jButtonDelete.setText("Smazat záznam");
        jButtonDelete.setEnabled(false);
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jLabelRoles.setText("Uživatelské role");

        jTextFieldRoles.setEditable(false);

        jButtonRole.setText("Upravit uživatelské role");
        jButtonRole.setEnabled(false);
        jButtonRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRoleActionPerformed(evt);
            }
        });

        jTextFieldPasswd.setEditable(false);
        jTextFieldPasswd.setText("1234");
        jTextFieldPasswd.setToolTipText("Výchozí heslo, které se uživateli nastaví  při vytváření jeho uživatelského záznamu.");

        jLabel1.setText("Vlastní heslo");
        jLabel1.setToolTipText("Změnil si již daný uživatel výchozí heslo na svoje tajné heslo?");

        jTextFieldOwnPasswd.setEditable(false);
        jTextFieldOwnPasswd.setToolTipText("Změnil si již daný uživatel výchozí heslo na svoje tajné heslo?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLogin)
                    .addComponent(jLabelSurname)
                    .addComponent(jLabelPIN)
                    .addComponent(jLabelPasswd)
                    .addComponent(jLabelName)
                    .addComponent(jLabelRoles))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldPasswd, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldRoles, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                    .addComponent(jTextFieldSurname, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                    .addComponent(jTextFieldPIN, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                    .addComponent(jTextFieldLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldOwnPasswd))
                    .addComponent(jButtonDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                    .addComponent(jButtonClear, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                    .addComponent(jButtonInsert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonRole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelName)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonInsert))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSurname)
                    .addComponent(jTextFieldSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPIN)
                    .addComponent(jTextFieldPIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLogin)
                    .addComponent(jTextFieldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPasswd)
                    .addComponent(jTextFieldPasswd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldOwnPasswd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRoles)
                    .addComponent(jTextFieldRoles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRole))
                .addGap(39, 39, 39))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        clearFields();
}//GEN-LAST:event_jButtonClearActionPerformed

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        String name = jTextFieldName.getText();
        String surname = jTextFieldSurname.getText();
        String pin = jTextFieldPIN.getText();
        String username = jTextFieldLogin.getText();
        String passwd = jTextFieldPasswd.getText();
        int userId = -1;

        if(row != -1){
            userId = (Integer)table.getModel().getValueAt(row, 0);
        }

        if(view.insertUser(name, surname, pin, username, passwd, userId) == UserFormResult.Succesful){
            refreshTable();
            clearFields();
        }
    }//GEN-LAST:event_jButtonInsertActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        row = table.getSelectedRow();
        int userId = (Integer)table.getModel().getValueAt(row, 0);

        view.deleteUser(userId);

        refreshTable();
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRoleActionPerformed
        int userId = (Integer)table.getModel().getValueAt(row, 0);

        userRoleDialog = new UserRoleDialog(parent, true, userId);
        userRoleDialog.setVisible(true);
        refreshTable();
    }//GEN-LAST:event_jButtonRoleActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonInsert;
    private javax.swing.JButton jButtonRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelLogin;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelPIN;
    private javax.swing.JLabel jLabelPasswd;
    private javax.swing.JLabel jLabelRoles;
    private javax.swing.JLabel jLabelSurname;
    private javax.swing.JTextField jTextFieldLogin;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldOwnPasswd;
    private javax.swing.JTextField jTextFieldPIN;
    private javax.swing.JTextField jTextFieldPasswd;
    private javax.swing.JTextField jTextFieldRoles;
    private javax.swing.JTextField jTextFieldSurname;
    // End of variables declaration//GEN-END:variables

}
