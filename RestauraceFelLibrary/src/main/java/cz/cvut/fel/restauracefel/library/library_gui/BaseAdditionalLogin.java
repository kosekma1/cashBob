package cz.cvut.fel.restauracefel.library.library_gui;

import cz.cvut.fel.restauracefel.library.Enums.EnumLoginResult;
import cz.cvut.fel.restauracefel.library.controller.CommonController;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 * Trida reprezentujici prihlasovaci dialog.
 *
 * @author Jarda
 */
public class BaseAdditionalLogin extends javax.swing.JDialog {
    
    private String requestRight;

    private CommonController controller;

    /** Creates new form AdditionalLoginDialog */
    public BaseAdditionalLogin( java.awt.Frame parent, boolean modal, String requestRight) {
        super( parent, modal );
        super.setTitle("Přihlášení do systému RestauraceFEL");

        this.controller = CommonController.getInstance();
        this.requestRight = requestRight;
        
        //setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.setLocation(parent.getX() + (parent.getWidth() / 2) - 300, parent.getY() + (parent.getHeight() / 2) - 150);

        initComponents( );
        //setCloseOperation();

        this.setVisible( true );
    }
    
    

    /**
     * Nastaveni vlastni close operace.
     */
    /*private void setCloseOperation() {
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }*/

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelUsername = new javax.swing.JLabel();
        jLabelPassword = new javax.swing.JLabel();
        jTextFieldUsername = new javax.swing.JTextField();
        jButtonLog = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jPasswordField = new javax.swing.JPasswordField();
        jLabelInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelUsername.setText("Uživatelské jméno");

        jLabelPassword.setText("Heslo");

        jButtonLog.setText("Přihlásit se");
        jButtonLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogActionPerformed(evt);
            }
        });

        jButtonClose.setText("Konec");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordFieldKeyPressed(evt);
            }
        });

        jLabelInfo.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabelInfo.setText("Přihlášení do systému RestauraceFEL");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelInfo)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelUsername)
                                    .addComponent(jLabelPassword))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPasswordField)
                                    .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonLog, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                .addGap(21, 21, 21)
                                .addComponent(jButtonClose, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)))
                        .addGap(35, 35, 35))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabelInfo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUsername)
                    .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPassword)
                    .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClose)
                    .addComponent(jButtonLog))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogActionPerformed
        String username = jTextFieldUsername.getText( );
        String password = String.copyValueOf( jPasswordField.getPassword( ) );

        EnumLoginResult result = controller.additionalLogin( username, password, requestRight );
        switch ( result ) {
            case LoginSuccesful:
                controller.setAdditionalUserAccessable( true );
                setVisible( false );
                break;

            case WrongLoginData:
                controller.setAdditionalUserAccessable( false );
                jTextFieldUsername.setText( "" );
                jPasswordField.setText( "" );
                JOptionPane.showMessageDialog( this, "Nesprávné uživatelské jméno nebo heslo", "Chyba", JOptionPane.ERROR_MESSAGE );
                break;

            case ScantRights:
                controller.setAdditionalUserAccessable( false );
                JOptionPane.showMessageDialog( this, "Nedostatečná práva", "Chyba", JOptionPane.ERROR_MESSAGE );
                break;
        }
    }//GEN-LAST:event_jButtonLogActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        this.setVisible( false );
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jPasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordFieldKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jButtonLogActionPerformed(null);
        }
    }//GEN-LAST:event_jPasswordFieldKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonLog;
    private javax.swing.JLabel jLabelInfo;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelUsername;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JTextField jTextFieldUsername;
    // End of variables declaration//GEN-END:variables

}
