package cz.cvut.fel.restauracefel.smeny.smeny_gui;

import java.awt.Insets;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import cz.cvut.fel.restauracefel.library.service.EmptyListException;
import cz.cvut.fel.restauracefel.library.service.Validator;
import cz.cvut.fel.restauracefel.smeny.SmenyController.SmenyController;

/**
 * Trida reprezentujici formular pro vytvareni nove sablony typu smen.s
 *  
 * @author Martin Kosek
 */
public class CreateTemplateForm extends AbstractForm {

    private ChooseShiftDialog chooseShiftDialog = null;
    private ChooseDeleteShiftDialog chooseDeleteShiftDialog = null;    
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
        initAllData();
        initComponents();
        refresh();
        clearFields();
    }

    /**
     * Metoda prenastavuje statusBar
     */
    @Override
    protected void refresh() {
        statusBar.setMessage("Tento formulář slouží k vytvoření nové šablony.");        
    }

    /**
     * Metoda cisti vsechny vstupni pole formulare vcetne tabulky se smenanmi.
     */
    @Override
    protected void clearFields() {
        Validator.clearTextField(templateNameTextField);
        SmenyController.getInstance().clearTableWorkShiftData();
    }    

    private void reloadTableTemplates() throws FileNotFoundException, NotBoundException, RemoteException {
        SmenyController.getInstance().generateTableTemplateData();
        jTableTemplates.setModel(SmenyController.getInstance().getModelTemplate());
    }
    
    private void reloadTableWorkShifts(){
        jTableWorkShifts.setModel(SmenyController.getInstance().getModelWorkShift());
    }

    private void initAllData() throws FileNotFoundException, NotBoundException, RemoteException {
        SmenyController.getInstance().generateTableTemplateData();        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        newTemplatePanel = new BackgroundPanel();
        templateNameTextField = new javax.swing.JTextField();
        jLabelNewTemplate = new javax.swing.JLabel();
        jLabelNameTemplate = new javax.swing.JLabel();
        jPanelNewTemplateButtons = new javax.swing.JPanel();
        jButtonSaveTemplate = new javax.swing.JButton();
        jButtonAddWorkShift = new javax.swing.JButton();
        jButtonRemoveWorkShift = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableWorkShifts = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTemplates = new javax.swing.JTable();
        jLabelSavedTemplates = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonEditTemplate = new javax.swing.JButton();
        jButtonDeleteTemplate = new javax.swing.JButton();
        jLabelTitleCreateDeleteTemplate = new javax.swing.JLabel();

        setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBox.light"));
        setPreferredSize(new java.awt.Dimension(1032, 622));

        newTemplatePanel.setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBox.light"));
        newTemplatePanel.setOpaque(false);

        templateNameTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        templateNameTextField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        templateNameTextField.setMargin(new Insets(10, 10, 10, 10));

        jLabelNewTemplate.setFont(new java.awt.Font("Calibri", 1, 22));
        jLabelNewTemplate.setText("Nová šablona");

        jLabelNameTemplate.setFont(new java.awt.Font("Calibri", 1, 14));
        jLabelNameTemplate.setForeground(new java.awt.Color(0, 102, 102));
        jLabelNameTemplate.setText("Název šablony:");

        jPanelNewTemplateButtons.setOpaque(false);

        jButtonSaveTemplate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonSaveTemplate.setText("Uložit");
        jButtonSaveTemplate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonSaveTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveTemplateActionPerformed(evt);
            }
        });

        jButtonAddWorkShift.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonAddWorkShift.setText("Přidat směnu");
        jButtonAddWorkShift.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonAddWorkShift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddWorkShiftActionPerformed(evt);
            }
        });

        jButtonRemoveWorkShift.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonRemoveWorkShift.setText("Odebrat směnu");
        jButtonRemoveWorkShift.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonRemoveWorkShift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveWorkShiftActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelNewTemplateButtonsLayout = new javax.swing.GroupLayout(jPanelNewTemplateButtons);
        jPanelNewTemplateButtons.setLayout(jPanelNewTemplateButtonsLayout);
        jPanelNewTemplateButtonsLayout.setHorizontalGroup(
            jPanelNewTemplateButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNewTemplateButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNewTemplateButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonAddWorkShift, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                    .addComponent(jButtonRemoveWorkShift, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                    .addComponent(jButtonSaveTemplate, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelNewTemplateButtonsLayout.setVerticalGroup(
            jPanelNewTemplateButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNewTemplateButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonAddWorkShift, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonRemoveWorkShift, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(jButtonSaveTemplate, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jButtonSaveTemplate.getAccessibleContext().setAccessibleName("Přidat směnu");

        jTableWorkShifts.setFont(new java.awt.Font("Calibri", 0, 14));
        jTableWorkShifts.setModel(SmenyController.getInstance().getModelWorkShift());
        jScrollPane2.setViewportView(jTableWorkShifts);

        jTableTemplates.setFont(new java.awt.Font("Calibri", 0, 14));
        jTableTemplates.setModel(SmenyController.getInstance().getModelTemplate());
        jScrollPane1.setViewportView(jTableTemplates);

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

        jLabelSavedTemplates.setFont(new java.awt.Font("Calibri", 1, 22));
        jLabelSavedTemplates.setText("Uložené šablony");

        jPanel2.setOpaque(false);

        jButtonEditTemplate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonEditTemplate.setText("Upravit");
        jButtonEditTemplate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonEditTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditTemplateActionPerformed(evt);
            }
        });

        jButtonDeleteTemplate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonDeleteTemplate.setText("Smazat");
        jButtonDeleteTemplate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonDeleteTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteTemplateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonEditTemplate, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(jButtonDeleteTemplate, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonEditTemplate, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonDeleteTemplate, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout newTemplatePanelLayout = new javax.swing.GroupLayout(newTemplatePanel);
        newTemplatePanel.setLayout(newTemplatePanelLayout);
        newTemplatePanelLayout.setHorizontalGroup(
            newTemplatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newTemplatePanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(newTemplatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newTemplatePanelLayout.createSequentialGroup()
                        .addGroup(newTemplatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelNewTemplate)
                            .addComponent(jLabelNameTemplate))
                        .addGap(63, 63, 63))
                    .addComponent(templateNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
                .addGap(42, 42, 42)
                .addComponent(jPanelNewTemplateButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(newTemplatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newTemplatePanelLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelSavedTemplates))
                .addContainerGap())
        );
        newTemplatePanelLayout.setVerticalGroup(
            newTemplatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newTemplatePanelLayout.createSequentialGroup()
                .addGroup(newTemplatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newTemplatePanelLayout.createSequentialGroup()
                        .addComponent(jLabelSavedTemplates)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(newTemplatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newTemplatePanelLayout.createSequentialGroup()
                            .addGap(75, 75, 75)
                            .addComponent(jPanelNewTemplateButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, newTemplatePanelLayout.createSequentialGroup()
                            .addGap(27, 27, 27)
                            .addComponent(jLabelNewTemplate)
                            .addGap(11, 11, 11)
                            .addComponent(jLabelNameTemplate)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(templateNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(newTemplatePanelLayout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jLabelTitleCreateDeleteTemplate.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTitleCreateDeleteTemplate.setFont(new java.awt.Font("Tahoma", 0, 18));
        jLabelTitleCreateDeleteTemplate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitleCreateDeleteTemplate.setText("Správa šablon");
        jLabelTitleCreateDeleteTemplate.setOpaque(true);
        jLabelTitleCreateDeleteTemplate.setPreferredSize(new java.awt.Dimension(193, 22));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitleCreateDeleteTemplate, javax.swing.GroupLayout.DEFAULT_SIZE, 1146, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(newTemplatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelTitleCreateDeleteTemplate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(newTemplatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSaveTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveTemplateActionPerformed
        try {
            saveTemplate();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(CreateShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(CreateShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*
        KeyboardDialog keyboard = new KeyboardDialog(parent, true);
        keyboard.setLocation(point);
        keyboard.setTextField(jTextFieldName);
        keyboard.setVisible(true);
         *
         */
    }//GEN-LAST:event_jButtonSaveTemplateActionPerformed

    private void saveTemplate() throws FileNotFoundException, NotBoundException, RemoteException {
        String templateName = templateNameTextField.getText();
        boolean result = SmenyController.getInstance().saveTemplate(templateName);
        if (result) {
            clearFields();
            reloadTableTemplates();            
            reloadTableWorkShifts();
            this.repaint();
        }
    }

    private void jButtonAddWorkShiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddWorkShiftActionPerformed
        try {
            chooseShiftDialog = new ChooseShiftDialog(parent, true, jTableWorkShifts);
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
    }//GEN-LAST:event_jButtonAddWorkShiftActionPerformed

private void jButtonRemoveWorkShiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveWorkShiftActionPerformed
    try {
        chooseDeleteShiftDialog = new ChooseDeleteShiftDialog(parent, true, jTableWorkShifts);
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
}//GEN-LAST:event_jButtonRemoveWorkShiftActionPerformed

private void jButtonEditTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditTemplateActionPerformed
    //TODO - implementovat editaci sablon    
}//GEN-LAST:event_jButtonEditTemplateActionPerformed

/**
 * Smazani vybrane sablony.
 * @param evt 
 */
private void jButtonDeleteTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteTemplateActionPerformed
    int row = this.jTableTemplates.getSelectedRow();
    int column = this.jTableTemplates.getSelectedColumn();
    if (row == -1) {
        SmenyController.getInstance().showErrorMessage("Vyberte řádek.", "Chyba");
    } else {
        int result = SmenyController.getInstance().showConfirmDialogStandard("Opravdu smazat šablonu?", "Dotaz");
        if (result == 0) {
            try {
                String templateName = (String) jTableTemplates.getValueAt(row, column);
                SmenyController.getInstance().deleteTemplateByName(templateName);
                reloadTableTemplates();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


}//GEN-LAST:event_jButtonDeleteTemplateActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddWorkShift;
    private javax.swing.JButton jButtonDeleteTemplate;
    private javax.swing.JButton jButtonEditTemplate;
    private javax.swing.JButton jButtonRemoveWorkShift;
    private javax.swing.JButton jButtonSaveTemplate;
    private javax.swing.JLabel jLabelNameTemplate;
    private javax.swing.JLabel jLabelNewTemplate;
    private javax.swing.JLabel jLabelSavedTemplates;
    private javax.swing.JLabel jLabelTitleCreateDeleteTemplate;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelNewTemplateButtons;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableTemplates;
    private javax.swing.JTable jTableWorkShifts;
    private javax.swing.JPanel newTemplatePanel;
    private javax.swing.JTextField templateNameTextField;
    // End of variables declaration//GEN-END:variables
}
