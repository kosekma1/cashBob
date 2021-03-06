/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LocalizationDialog.java
 *
 * Created on 15.3.2011, 21:38:06
 */
package cz.cvut.fel.restauracefel.restauracefel.gui;

import cz.cvut.fel.restauracefel.library.service.LocalizationManager;
import cz.cvut.fel.restauracefel.restauracefel.main.Main;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

/**
 * Dialog for choosing localization of application. All changes will be visible
 * after restart!
 *
 * @author Štěpán
 */
public class LocalizationDialog extends javax.swing.JDialog {

    private Locale[] supportedLocales;

    /** Creates new form LocalizationDialog */
    public LocalizationDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        LocalizationManager manager = LocalizationManager.getInstance();
        manager.localizeLabel(Main.DEFAULT_BUNDLE_BASE_NAME, "LocalizationLabel", localizationLabel);
        manager.localizeLabel(Main.DEFAULT_BUNDLE_BASE_NAME, "LocalizationNoteLabel", localizationNoteLabel);
        manager.localizeButton(Main.DEFAULT_BUNDLE_BASE_NAME, "OkButton", okButton);

        supportedLocales = manager.getSupportedLocales();
        String[] locales = new String[supportedLocales.length];
        for (int i = 0; i < locales.length; i++) {
            locales[i] = supportedLocales[i].getDisplayName();
        }
        localizationComboBox.setModel(new DefaultComboBoxModel(locales));

        manager.localizeDialog(Main.DEFAULT_BUNDLE_BASE_NAME, "LocalizationDialog", this);
        pack();
        setResizable(false);
        this.setLocation(parent.getX() + (parent.getWidth() / 2) - this.getWidth(), parent.getY() + (parent.getHeight() / 2) - this.getHeight());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        localizationLabel = new javax.swing.JLabel();
        localizationComboBox = new javax.swing.JComboBox();
        localizationNoteLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        localizationLabel.setText("Locale:");

        localizationNoteLabel.setText("All changes will be visible after restart!");

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(localizationNoteLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(localizationLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(localizationComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(okButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(localizationLabel)
                    .addComponent(localizationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(localizationNoteLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(okButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        try {
            LocalizationManager.getInstance().setDefaultLocale(supportedLocales[localizationComboBox.getSelectedIndex()]);
        } catch (IOException ex) {
            LocalizationManager.getInstance().showMessage(LocalizationManager.MessageType.ERROR, Main.MESSAGE_BUNDLE_BASE_NAME, "CannotSetLocalization");
        }
        setVisible(false);
    }//GEN-LAST:event_okButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox localizationComboBox;
    private javax.swing.JLabel localizationLabel;
    private javax.swing.JLabel localizationNoteLabel;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables
}
