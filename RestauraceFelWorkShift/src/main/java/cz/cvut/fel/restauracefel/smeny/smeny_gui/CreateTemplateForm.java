package cz.cvut.fel.restauracefel.smeny.smeny_gui;

import cz.cvut.fel.restauracefel.hibernate.User;
import java.awt.Insets;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.library.service.EmptyListException;
//import cz.cvut.fel.restauracefel.pokladna_service.ServiceFacade;
import cz.cvut.fel.restauracefel.library.service.Validator;
import cz.cvut.fel.restauracefel.smeny.smeny_gui.ChooseDeleteTemplateDialog;

/**
 * Trida reprezentujici GUI formular pro vytvareni noveho uctu.
 *  
 * @author Tomas Hnizdil
 */
public class CreateTemplateForm extends AbstractForm {

    private ChooseShiftDialog chooseShiftDialog = null;
    private ChooseDeleteShiftDialog chooseDeleteShiftDialog = null;
    private ChooseDeleteTemplateDialog chooseDeleteTemplateDialog = null;
    //private ChoosePersonDialog choosePersonDialog = null;
    //private ChooseDiscountTypeDialog chooseDiscountTypeDialog = null;
    //private ChooseAccountCategoryDialog chooseAccountCategoryDialog = null;*/
    private StatusBar statusBar = null;
    private MainFrame parent = null;
    private Point point = new Point(550, 210);

    /**
     * Konstruktor tridy CreateShiftForm.
     *
     * @param parent
     * @param bar
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public CreateTemplateForm(MainFrame parent, StatusBar bar) throws FileNotFoundException, NotBoundException, RemoteException {
        this.parent = parent;
        this.statusBar = bar;
        initComponents();
        refresh();
        clearFields();
    }

    /**
     * Metoda prenastavuje statusBar.
     */
    @Override
    protected void refresh() {
        statusBar.setMessage("Tento formulář slouží k vytváření nového účtu.");
    }

    /**
     * Metoda kontrolujici spravnost vyplnenych udaju.
     *
     * @return Vraci index urcujici vstupni komponentu, ktera obsahuje
     * neplatny vstup. Pokud je vse vporadku tak navraci 0.
     */
    @Override
    /* protected EnumSpravnost isValidInput() {
    if (!Validator.isText(jTextFieldName)) {
    return EnumSpravnost.NeniToSpravne;
    }
    return EnumSpravnost.JeToSpravne;
    } */
    /**
     * Metoda cisti vsechny vstupni pole formulare.
     */
    //@Override
    protected void clearFields() {
        Validator.clearTextField(jTextFieldName);
        //Validator.clearTextField(jTextFieldTable);
        //Validator.clearTextField(jTextFieldPerson);
        //Validator.clearTextField(jTextFieldDiscountType);
        //Validator.clearTextField(jTextFieldAccountCategory);
        //Validator.clearTextField(jTextFieldNote);
    }

    /**
     * Metoda vytvari a zobrazuje formular pro objednani polozek na ucet.
     *
     * @param accountId id uctu, na ktery se bude objednavat
     */
    public void loadCreateOrderForm(int accountId) {
        /*try {
        CreateOrderForm createOrderForm = new CreateOrderForm(parent, statusBar, accountId, MainFrame.loggedUser.getUserId());
        parent.panel.getViewport().add(createOrderForm);
        parent.panel.validate();
        parent.panel.repaint();
        parent.refreshWindowLayout();
        refresh();
        } catch (FileNotFoundException fnfe) {
        JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }
         */
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
        jTextFieldName = new javax.swing.JTextField();
        jLabelInfoText = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonCreateName = new javax.swing.JButton();
        jButtonCreateName1 = new javax.swing.JButton();
        jButtonCreateName2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabelInfoText1 = new javax.swing.JLabel();
        jButtonCreateName3 = new javax.swing.JButton();
        jLabelTitle = new javax.swing.JLabel();

        setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBox.light"));
        setPreferredSize(new java.awt.Dimension(1032, 622));

        billPanel.setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBox.light"));
        billPanel.setOpaque(false);

        jTextFieldName.setFont(new java.awt.Font("Tahoma", 0, 12));
        jTextFieldName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jTextFieldName.setMargin(new Insets(10, 10, 10, 10));
        jTextFieldName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNameActionPerformed(evt);
            }
        });

        jLabelInfoText.setFont(new java.awt.Font("Calibri", 1, 22));
        jLabelInfoText.setText("Nová šablona");

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 14));
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Název šablony:");

        jPanel2.setOpaque(false);

        jButtonCreateName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonCreateName.setText("Uložit");
        jButtonCreateName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonCreateName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateNameActionPerformed(evt);
            }
        });

        jButtonCreateName1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonCreateName1.setText("Přidat směnu");
        jButtonCreateName1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonCreateName1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateName1ActionPerformed(evt);
            }
        });

        jButtonCreateName2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonCreateName2.setText("Odebrat směnu");
        jButtonCreateName2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonCreateName2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateName2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonCreateName1, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                    .addComponent(jButtonCreateName2, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                    .addComponent(jButtonCreateName, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonCreateName1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonCreateName2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(jButtonCreateName, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jButtonCreateName.getAccessibleContext().setAccessibleName("Přidat směnu");

        jTable2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Směna 1"},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Typ směny"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jTable1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Název šablony"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelInfoText1.setFont(new java.awt.Font("Calibri", 1, 22));
        jLabelInfoText1.setText("Uložené šablony");

        jButtonCreateName3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonCreateName3.setText("Smazat šablonu");
        jButtonCreateName3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonCreateName3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateName3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout billPanelLayout = new javax.swing.GroupLayout(billPanel);
        billPanel.setLayout(billPanelLayout);
        billPanelLayout.setHorizontalGroup(
            billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, billPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, billPanelLayout.createSequentialGroup()
                        .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelInfoText)
                            .addComponent(jLabel3))
                        .addGap(63, 63, 63))
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
                .addGap(42, 42, 42)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(billPanelLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonCreateName3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabelInfoText1))
                .addContainerGap())
        );
        billPanelLayout.setVerticalGroup(
            billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billPanelLayout.createSequentialGroup()
                .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(billPanelLayout.createSequentialGroup()
                        .addComponent(jLabelInfoText1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, billPanelLayout.createSequentialGroup()
                            .addGap(75, 75, 75)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, billPanelLayout.createSequentialGroup()
                            .addGap(27, 27, 27)
                            .addComponent(jLabelInfoText)
                            .addGap(11, 11, 11)
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(billPanelLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jButtonCreateName3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jLabelTitle.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setFont(new java.awt.Font("Tahoma", 0, 18));
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("Vytvořit/Smazat šablonu");
        jLabelTitle.setOpaque(true);
        jLabelTitle.setPreferredSize(new java.awt.Dimension(193, 22));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1117, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(billPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(billPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCreateNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateNameActionPerformed
        /*
        KeyboardDialog keyboard = new KeyboardDialog(parent, true);
        keyboard.setLocation(point);
        keyboard.setTextField(jTextFieldName);
        keyboard.setVisible(true);
         *
         */
    }//GEN-LAST:event_jButtonCreateNameActionPerformed

    private void jButtonCreateName1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateName1ActionPerformed
        try {
            chooseShiftDialog = new ChooseShiftDialog(parent, true, jTextFieldName);
            chooseShiftDialog.setLocation(point);
            chooseShiftDialog.setVisible(true);
        } catch (EmptyListException ex) {
            Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonCreateName1ActionPerformed

private void jButtonCreateName2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateName2ActionPerformed
    try {
        chooseDeleteShiftDialog = new ChooseDeleteShiftDialog(parent, true, jTextFieldName);
        chooseDeleteShiftDialog.setLocation(point);
        chooseDeleteShiftDialog.setVisible(true);
    } catch (EmptyListException ex) {
        Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
    } catch (RemoteException ex) {
        Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
    } catch (NotBoundException ex) {
        Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
    } catch (FileNotFoundException ex) {
        Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_jButtonCreateName2ActionPerformed

private void jButtonCreateName3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateName3ActionPerformed
        try {
            ChooseDeleteTemplateDialog chooseDeleteTemplateDialog = new ChooseDeleteTemplateDialog(parent, true, jTextFieldName);
            chooseDeleteTemplateDialog.setLocation(point);
            chooseDeleteTemplateDialog.setVisible(true);
        } catch (EmptyListException ex) {
            Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
        }

}//GEN-LAST:event_jButtonCreateName3ActionPerformed

private void jTextFieldNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNameActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_jTextFieldNameActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel billPanel;
    private javax.swing.JButton jButtonCreateName;
    private javax.swing.JButton jButtonCreateName1;
    private javax.swing.JButton jButtonCreateName2;
    private javax.swing.JButton jButtonCreateName3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelInfoText;
    private javax.swing.JLabel jLabelInfoText1;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables
}
