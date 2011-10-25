package cz.cvut.fel.restauracefel.manager.manager_gui;

import cz.cvut.fel.restauracefel.hibernate.User;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JFrame;

/**
 * Trida reprezentujici GUI dialog pro urcovani uzivatelskych roli.
 *
 * @author Jarda
 */
public class UserRoleDialog extends javax.swing.JDialog {

    private int userId;

    private ManagerViewController view;

    /**
     * Konstruktor tridy UserRoleDialog.
     *
     * @param parent instance tridy JFrame jenz vytvorila tento formular
     * @param modal urcuje, zda bude dialog modalni
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public UserRoleDialog(JFrame parent, boolean modal, int userId) {
        super(parent, modal);
        setTitle("Nastavení uživatelských rolí");        

        view = ManagerViewController.getInstance();

        this.userId = userId;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int y = (int) ((dim.getHeight() - 150) / 2);
        int x = (int) ((dim.getWidth() - 300) / 2);
        setLocation(x, y);
        initComponents();
        setPerson();
        setCheckBoxes();
    }

    /**
     * Metoda nastavujici do jTextFieldu "person" informace o danem uzivateli.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    private void setPerson() {
        User user = null;        
        user = view.getUserById(userId);

        if(user != null){
            jTextFieldPerson.setText(user.getLastName() + ", " + user.getFirstName());
        }
    }

    /**
     * Metoda nastavujici checkBoxy dle stavajicich pridelenych roli.          
     */
    private void setCheckBoxes(){
        boolean[] currentRoles = view.getUserRoles(userId);

        if (currentRoles[0] == true){
            jCheckBoxManager.setSelected(true);
        }
        if (currentRoles[1] == true){
            jCheckBoxWaiter.setSelected(true);
        }
        if (currentRoles[2] == true){
            jCheckBoxCook.setSelected(true);
        }
    }

    /**
     * Metoda vraci pole oznacujici nove role. Pokud ma dany uzivatel danou roli,
     * tak je na odpovidajicim miste pole true; jinak false.
     *
     * @return pole oznacujici nove pridelene uzivatelske role danemu uzivateli
     */
    private boolean [] getNewRoles(){
        boolean newRoles [] = {false, false, false};

        if (jCheckBoxManager.isSelected()){
            newRoles[0] = true;
        }
        if (jCheckBoxWaiter.isSelected()){
            newRoles[1] = true;
        }
        if (jCheckBoxCook.isSelected()){
            newRoles[2] = true;
        }

        return newRoles;
    }

    /**
     * Metoda odstranujici "zaskrtnuti" checkBoxu.
     */
    public void clearCheckBoxes(){
        jCheckBoxManager.setSelected(false);
        jCheckBoxWaiter.setSelected(false);
        jCheckBoxCook.setSelected(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelUser = new javax.swing.JLabel();
        jTextFieldPerson = new javax.swing.JTextField();
        jLabelRole = new javax.swing.JLabel();
        jButtonInsert = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jCheckBoxManager = new javax.swing.JCheckBox();
        jCheckBoxWaiter = new javax.swing.JCheckBox();
        jCheckBoxCook = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jLabelUser.setText("Osoba");

        jTextFieldPerson.setEditable(false);

        jLabelRole.setText("Role");

        jButtonInsert.setText("Změnit role");
        jButtonInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Zpět");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jCheckBoxManager.setText("Manager");

        jCheckBoxWaiter.setText("Waiter");

        jCheckBoxCook.setText("Cook");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelUser)
                    .addComponent(jLabelRole))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxWaiter)
                    .addComponent(jCheckBoxCook)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldPerson, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBoxManager))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonInsert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUser)
                    .addComponent(jTextFieldPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonInsert))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxManager)
                    .addComponent(jButtonCancel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRole)
                    .addComponent(jCheckBoxWaiter))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxCook)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        boolean newRoles [] = getNewRoles();

        view.updateUserRoles(userId, newRoles);
        
        dispose();
    }//GEN-LAST:event_jButtonInsertActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonInsert;
    private javax.swing.JCheckBox jCheckBoxCook;
    private javax.swing.JCheckBox jCheckBoxManager;
    private javax.swing.JCheckBox jCheckBoxWaiter;
    private javax.swing.JLabel jLabelRole;
    private javax.swing.JLabel jLabelUser;
    private javax.swing.JTextField jTextFieldPerson;
    // End of variables declaration//GEN-END:variables

}
