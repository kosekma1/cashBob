package cz.cvut.fel.restauracefel.pokladna.pokladna_gui;

import java.awt.Insets;
import java.awt.Point;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.pokladna_service.ServiceFacade;
import cz.cvut.fel.restauracefel.library.service.Validator;

/**
 * Trida reprezentujici GUI formular pro vytvareni noveho zakaznika.
 *
 * @author Tomas Hnizdil  
 */
public class CreateCustomerForm extends AbstractForm {

    private StatusBar statusBar = null;
    private MainFrame parent = null;
    private InsertCreditForm caller = null;
    private Point point = new Point(550, 210);
    enum Jmena{Spravne, Uzivatelske,Krestni,Prijmeni;};

    /**
     * Konstruktor tridy CreateCustomerForm.
     *
     * @param parent
     * @param bar
     * @param caller odkaz na InsertCreditForm, ktery tento formular zavolal
     */
    public CreateCustomerForm(MainFrame parent, StatusBar bar, InsertCreditForm caller) {
        this.parent = parent;
        this.statusBar = bar;
        this.caller = caller;
        initComponents();
        refresh();
        clearFields();
    }

    /**
     * Metoda prenastavuje statusBar.
     */
    @Override
    protected void refresh() {
        statusBar.setMessage("Tento formulář slouží k vytváření nového zákazníka.");
    }

    /**
     * Metoda kontrolujici spravnost vyplnenych udaju.
     *
     * @return Vraci index urcujici vstupni komponentu, ktera obsahuje
     * neplatny vstup. Pokud je vse vporadku tak navraci 0.
     */
    private Jmena isValidInputComplex() {
        if (!Validator.isText(jTextFieldUsername)) {
            return Jmena.Uzivatelske;
        }
        if (!Validator.isText(jTextFieldName)) {
            return Jmena.Krestni;
        }
        if (!Validator.isText(jTextFieldSurname)) {
            return Jmena.Prijmeni;
        }
        return Jmena.Spravne;
    }

    /**
     * Metoda cisti vsechny vstupni pole formulare.
     */
    @Override
    protected void clearFields() {
        Validator.clearTextField(jTextFieldUsername);
        Validator.clearTextField(jTextFieldName);
        Validator.clearTextField(jTextFieldSurname);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        billPanel = new BackgroundPanel();
        jTextFieldUsername = new javax.swing.JTextField();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldSurname = new javax.swing.JTextField();
        jLabelInfoText = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonCreateName = new javax.swing.JButton();
        jButtonCreateSurname = new javax.swing.JButton();
        jButtonCreateUsername = new javax.swing.JButton();
        jLabelTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButtonBack = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonOK = new javax.swing.JButton();

        setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBox.light"));

        billPanel.setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBox.light"));
        billPanel.setOpaque(false);
        billPanel.setPreferredSize(new java.awt.Dimension(531, 470));

        jTextFieldUsername.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jTextFieldUsername.setMargin(new Insets(10, 10, 10, 10));

        jTextFieldName.setEditable(false);
        jTextFieldName.setBorder(null);

        jTextFieldSurname.setEditable(false);
        jTextFieldSurname.setBorder(null);

        jLabelInfoText.setFont(new java.awt.Font("Calibri", 1, 22));
        jLabelInfoText.setText("Nový zákazník");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Jméno:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("Příjmení:");

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 14));
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Uživatelské jméno:");

        jPanel2.setOpaque(false);

        jButtonCreateName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left.png"))); // NOI18N
        jButtonCreateName.setText("Jméno");
        jButtonCreateName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonCreateName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateNameActionPerformed(evt);
            }
        });

        jButtonCreateSurname.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left.png"))); // NOI18N
        jButtonCreateSurname.setText("Příjmení");
        jButtonCreateSurname.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonCreateSurname.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonCreateSurname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateSurnameActionPerformed(evt);
            }
        });

        jButtonCreateUsername.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonCreateUsername.setText("Username");
        jButtonCreateUsername.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonCreateUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateUsernameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonCreateName, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jButtonCreateSurname, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jButtonCreateUsername, 0, 0, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonCreateUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCreateName, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCreateSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout billPanelLayout = new javax.swing.GroupLayout(billPanel);
        billPanel.setLayout(billPanelLayout);
        billPanelLayout.setHorizontalGroup(
            billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billPanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(billPanelLayout.createSequentialGroup()
                        .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(billPanelLayout.createSequentialGroup()
                                .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(33, 33, 33)
                                .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldSurname)
                                    .addComponent(jTextFieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66))
                    .addGroup(billPanelLayout.createSequentialGroup()
                        .addComponent(jLabelInfoText)
                        .addContainerGap(366, Short.MAX_VALUE))))
        );
        billPanelLayout.setVerticalGroup(
            billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billPanelLayout.createSequentialGroup()
                .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(billPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabelInfoText)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(4, 4, 4)
                        .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(billPanelLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(191, Short.MAX_VALUE))
        );

        jLabelTitle.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setFont(new java.awt.Font("Tahoma", 0, 18));
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("Vytvořit nového zákazníka");
        jLabelTitle.setOpaque(true);

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(149, 167));

        jButtonBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left.png"))); // NOI18N
        jButtonBack.setText("  Zpět");
        jButtonBack.setToolTipText("Vložit účet");
        jButtonBack.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonBack.setMargin(new java.awt.Insets(2, 0, 2, 0));
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jButtonDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/ko.png"))); // NOI18N
        jButtonDelete.setText("  Vymazat");
        jButtonDelete.setToolTipText("Vymazat pole účtu");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/ok.png"))); // NOI18N
        jButtonOK.setText("  OK");
        jButtonOK.setToolTipText("Vložit účet");
        jButtonOK.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonOK.setMargin(new java.awt.Insets(2, 0, 2, 0));
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonOK, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonOK, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(billPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(billPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCreateSurnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateSurnameActionPerformed
        KeyboardDialog keyboard = new KeyboardDialog(parent, true);
        keyboard.setLocation(point);
        keyboard.setTextField(jTextFieldSurname);
        keyboard.setVisible(true);
}//GEN-LAST:event_jButtonCreateSurnameActionPerformed

    private void jButtonCreateNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateNameActionPerformed
        KeyboardDialog keyboard = new KeyboardDialog(parent, true);
        keyboard.setLocation(point);
        keyboard.setTextField(jTextFieldName);
        keyboard.setVisible(true);
    }//GEN-LAST:event_jButtonCreateNameActionPerformed

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed
        Jmena i = isValidInputComplex();
        switch (i) {
            case Spravne:
                String username = jTextFieldUsername.getText();
                String name = jTextFieldName.getText();
                String surname = jTextFieldSurname.getText();
                try {
                    boolean isOK;
                    isOK = ServiceFacade.getInstance().createUser(name, surname, username);
                    if (!isOK) {
                        JOptionPane.showMessageDialog(this, "Nový uživatel nemohl být vytvořen, protože zvolené uživatelské jméno už je obsazeno.", "Nový zákazník", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    int userId = ServiceFacade.getInstance().getUserByUsername(username).getUserId();
                    //int roleId = ServiceFacade.getInstance().getRoleByName("customer").getRoleId();
                    int roleId = 4;
                    isOK = ServiceFacade.getInstance().createUserRole(userId, roleId);
                    if (!isOK) {
                        //JOptionPane.showMessageDialog(this, "Záznam nemohl být uložen, protože záznam se stejným uživatelským jménem nebo PID číslem je již uložen.", "Zaměstnanci", JOptionPane.INFORMATION_MESSAGE);
                        //return;
                    }
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
                }
                clearFields();
                break;
            case Uzivatelske:
                JOptionPane.showMessageDialog(this, "Musí být uvedeno uživatelské jméno nového zákazníka.", "Nový zákazník", JOptionPane.INFORMATION_MESSAGE);
                break;
            case Krestni:
                JOptionPane.showMessageDialog(this, "Musí být uvedeno křestní jméno nového zákazníka.", "Nový zákazník", JOptionPane.INFORMATION_MESSAGE);
                break;
            case Prijmeni:
                JOptionPane.showMessageDialog(this, "Musí být uvedeno příjmení nového zákazníka.", "Nový zákazník", JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                break;
        }
}//GEN-LAST:event_jButtonOKActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        clearFields();
}//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonCreateUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateUsernameActionPerformed
        KeyboardDialog keyboard = new KeyboardDialog(parent, true);
        keyboard.setLocation(point);
        keyboard.setTextField(jTextFieldUsername);
        keyboard.setVisible(true);
    }//GEN-LAST:event_jButtonCreateUsernameActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        parent.panel.getViewport().add(caller);
        parent.panel.repaint();
        parent.panel.validate();
        parent.refreshWindowLayout();
        refresh();
    }//GEN-LAST:event_jButtonBackActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel billPanel;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonCreateName;
    private javax.swing.JButton jButtonCreateSurname;
    private javax.swing.JButton jButtonCreateUsername;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelInfoText;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldSurname;
    private javax.swing.JTextField jTextFieldUsername;
    // End of variables declaration//GEN-END:variables

}