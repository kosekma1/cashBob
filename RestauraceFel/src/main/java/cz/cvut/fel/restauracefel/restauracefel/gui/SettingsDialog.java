package cz.cvut.fel.restauracefel.restauracefel.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.library.library_gui.RegexFormatter;
import cz.cvut.fel.restauracefel.library.service.LocalizationManager;
import cz.cvut.fel.restauracefel.restauracefel.main.Main;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.MaskFormatter;

/**
 * GUI dialog pro nastavovani parametru aplikace.
 *
 * @author Jarda
 */
public class SettingsDialog extends javax.swing.JDialog {

    private JFrame parent = null;
    private ConfigParser config = null;
    private LocalizationManager manager;

    /**
     * Konstruktor tridy SettingsDialog.
     *
     * @param parent instance tridy JFrame jenz vytvorila tento formular
     * @param modal urcuje, zda bude dialog modalni
     * @throws java.io.FileNotFoundException
     */
    public SettingsDialog(JFrame parent, boolean modal) throws FileNotFoundException {
        super(parent, modal);
        manager = LocalizationManager.getInstance();

        manager.localizeDialog(Main.DEFAULT_BUNDLE_BASE_NAME, "SettingsDialog", this);

        this.parent = parent;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        int y = (int) ((dim.getHeight() - 350) / 2);
        int x = (int) ((dim.getWidth() - 400) / 2);
        setBounds(x, y, 400, 350);

        initComponents();

        manager.localizeLabel(Main.DEFAULT_BUNDLE_BASE_NAME, "PrimaryIPAddressLabel", jLabelPrimaryIP);
        manager.localizeLabel(Main.DEFAULT_BUNDLE_BASE_NAME, "SecondaryIPAddressLabel", jLabelSecondaryIP);
        manager.localizeLabel(Main.DEFAULT_BUNDLE_BASE_NAME, "ConfigurationFileLabel", jLabelFilePath);
        manager.localizeLabel(Main.DEFAULT_BUNDLE_BASE_NAME, "PrintingDirectoryLabel", jLabelPritingDir);
        manager.localizeLabel(Main.DEFAULT_BUNDLE_BASE_NAME, "MoneyLabel", jLabelMoney);
        manager.localizeLabel(Main.DEFAULT_BUNDLE_BASE_NAME, "CommunicationPortLabel", jLabelCommPort);

        manager.localizeButton(Main.DEFAULT_BUNDLE_BASE_NAME, "SaveSettingsButton", jButtonInsert);
        manager.localizeButton(Main.DEFAULT_BUNDLE_BASE_NAME, "CloseSettingsButton", jButtonClose);
        manager.localizeButton(Main.DEFAULT_BUNDLE_BASE_NAME, "PrintingSettingsButton", jButtonPrint);

        config = new ConfigParser();
        jTextFieldFilePath.setText(config.getAbsoluteConfigFilePath());
        readSettings();
    }

    /**
     * Metoda vytvari formatovaci objekt pro IP adresy.
     *
     * @return instance tridy RegexFormatter
     */
    private MaskFormatter getIPFormat() {
        MaskFormatter maskIP = null;
        try {
            //String _255 = "(?:25[0-5]|2[0-4][0-9]|1[0-9][0-9]|)";
            //Pattern p = Pattern.compile("^(?:" + _255 + "\\.){3}" + _255 + "$");
            //maskIP = new RegexFormatter(p);

            maskIP = new MaskFormatter("###.###.###.###");
            maskIP.setPlaceholder("000.000.000.000");
            maskIP.setValidCharacters("0123456789");
            maskIP.setAllowsInvalid(false);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return maskIP;
    }

    /**
     * Metoda pro nacitani nastaveni.
     *
     * @throws java.io.FileNotFoundException
     */
    private void readSettings() throws FileNotFoundException {
        jTextFieldPrimaryServerIP.setText(config.getPrimaryServerIP());
        jTextFieldSecondaryServerIP.setText(config.getSecondaryServerIP());
        jTextFieldMoney.setText(config.getMoney());
        jTextFieldDirPath.setText(config.getTemplatesDir());
        comInputText.setText(config.getCom());
    }

    /**
     * Metoda pro zapis nastaveni.
     *
     * @throws java.io.FileNotFoundException
     * @throws java.lang.Exception
     */
    private void saveSettings() throws FileNotFoundException, Exception {
        config.setPrimaryServerIP(jTextFieldPrimaryServerIP.getText());
        config.setSecondaryServerIP(jTextFieldSecondaryServerIP.getText());
        config.setMoney(jTextFieldMoney.getText());
        config.setTemplatesDir(jTextFieldDirPath.getText());
        config.setCom(comInputText.getText());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldPrimaryServerIP = new javax.swing.JFormattedTextField(getIPFormat());
        jLabelPrimaryIP = new javax.swing.JLabel();
        jTextFieldSecondaryServerIP = new javax.swing.JFormattedTextField(getIPFormat());
        jLabelSecondaryIP = new javax.swing.JLabel();
        jLabelMoney = new javax.swing.JLabel();
        jTextFieldMoney = new javax.swing.JTextField();
        jButtonInsert = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jLabelFilePath = new javax.swing.JLabel();
        jTextFieldFilePath = new javax.swing.JTextField();
        jLabelPritingDir = new javax.swing.JLabel();
        jTextFieldDirPath = new javax.swing.JTextField();
        jButtonPrint = new javax.swing.JButton();
        jLabelCommPort = new javax.swing.JLabel();
        comInputText = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelPrimaryIP.setText("IP adresa primárního serveru");

        jLabelSecondaryIP.setText("IP adresa sekundárního serveru");

        jLabelMoney.setText("Používaná měna");

        jButtonInsert.setText("Uložit nastavení");
        jButtonInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertActionPerformed(evt);
            }
        });

        jButtonClose.setText("Zpět");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jLabelFilePath.setText("Konfigurační soubor");

        jTextFieldFilePath.setFont(new java.awt.Font("Tahoma", 1, 11));
        jTextFieldFilePath.setEnabled(false);

        jLabelPritingDir.setText("Adresář s tiskovými sestavami");

        jTextFieldDirPath.setFont(new java.awt.Font("Tahoma", 1, 11));
        jTextFieldDirPath.setEnabled(false);

        jButtonPrint.setText("Nastavit adresář s tiskovými sestavami");
        jButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintActionPerformed(evt);
            }
        });

        jLabelCommPort.setText("Comm port");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPrimaryIP)
                            .addComponent(jLabelSecondaryIP)
                            .addComponent(jLabelFilePath))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldSecondaryServerIP, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jTextFieldPrimaryServerIP, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButtonClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonInsert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jTextFieldFilePath, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPritingDir)
                            .addComponent(jLabelMoney)
                            .addComponent(jLabelCommPort))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldMoney, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                            .addComponent(jButtonPrint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                            .addComponent(jTextFieldDirPath, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                            .addComponent(comInputText, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPrimaryServerIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonInsert)
                    .addComponent(jLabelPrimaryIP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSecondaryServerIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonClose)
                    .addComponent(jLabelSecondaryIP))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFilePath)
                    .addComponent(jTextFieldFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPritingDir)
                    .addComponent(jTextFieldDirPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPrint)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMoney)
                    .addComponent(jTextFieldMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCommPort)
                    .addComponent(comInputText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        try {
            saveSettings();
            JOptionPane.showMessageDialog(this, "Nastavení bylo úspěšně uloženo.", "Nastavení klienta", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonInsertActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintActionPerformed
        String curDir;
        try {
            curDir = config.getTemplatesDir();
        } catch (FileNotFoundException fnfe) {
            curDir = System.getProperty("user.dir");
        }
        JFileChooser fileChooser = new JFileChooser(new File(curDir));
        fileChooser.setDialogTitle("Výběr adresáře, kde se nachází tiskové sestavy");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            jTextFieldDirPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
        } else {
            return;
        }
    }//GEN-LAST:event_jButtonPrintActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField comInputText;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonInsert;
    private javax.swing.JButton jButtonPrint;
    private javax.swing.JLabel jLabelCommPort;
    private javax.swing.JLabel jLabelFilePath;
    private javax.swing.JLabel jLabelMoney;
    private javax.swing.JLabel jLabelPrimaryIP;
    private javax.swing.JLabel jLabelPritingDir;
    private javax.swing.JLabel jLabelSecondaryIP;
    private javax.swing.JTextField jTextFieldDirPath;
    private javax.swing.JTextField jTextFieldFilePath;
    private javax.swing.JTextField jTextFieldMoney;
    private javax.swing.JFormattedTextField jTextFieldPrimaryServerIP;
    private javax.swing.JFormattedTextField jTextFieldSecondaryServerIP;
    // End of variables declaration//GEN-END:variables
}
