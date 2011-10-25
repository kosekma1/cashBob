package cz.cvut.fel.restauracefel.storage.sklad_gui;

import cz.cvut.fel.restauracefel.library.library_gui.PrintDialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.library.service.LocalizationManager;
import cz.cvut.fel.restauracefel.sklad_service.ResultTableModel;
import cz.cvut.fel.restauracefel.sklad_service.ServiceFacade;
import cz.cvut.fel.restauracefel.library.service.Validator;
import cz.cvut.fel.restauracefel.storage.storageController.StorageController;

/**
 * Trida reprezentujici GUI formular pro evidovani skladovych karet.
 * Zaroven zajistuje vytvoreni a naplneni tabulky, vcetne zajisteni aktualizace.
 * Take se stara o zapisovani popisku do statusBaru.
 *
 * @author Jarda
 */
public class MaterialForm extends AbstractForm {

    private JTable table = null;
    private JFrame parent = null;
    private StatusBar statusBar = null;
    private BarcodeDialog barcodeDialog = null;
    private int row = -1;

    /**
     * Konstruktor tridy MaterialForm.
     *
     * @param parent instance tridy JFrame jenz vytvorila tento formular
     * @param bar statusBar, do ktereho budou vypisovany popisky
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public MaterialForm(JFrame parent, StatusBar bar) throws RemoteException, NotBoundException, FileNotFoundException {
        this.parent = parent;
        this.statusBar = bar;
        initComponents();
        initTable();

        LocalizationManager manager = LocalizationManager.getInstance();

        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "MaterialNameLabel", jLabelName);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "TypeLabel", jLabelType);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "UnitLabel", jLabelUnit);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "MinimumQuantityLabel", jLabelMinimum);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "MinimumQuantityInformationLabel", jLabelMinInfo);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "BarCodeLabel", jLabelBarcode);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "IntensityLabel", jLabelHustota);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "EmptyWeightLabel", jLabelEmptyWeight);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "CapacityLabel", jLabelCapacity);

        manager.localizeButton(StorageController.DEFAULT_BUNDLE_BASE_NAME, "InsertButton", jButtonInsert);
        manager.localizeButton(StorageController.DEFAULT_BUNDLE_BASE_NAME, "ClearButton", jButtonClear);
        manager.localizeButton(StorageController.DEFAULT_BUNDLE_BASE_NAME, "DeleteButton", jButtonDelete);
        manager.localizeButton(StorageController.DEFAULT_BUNDLE_BASE_NAME, "BarCodeButton", jButtonBarcode);
        manager.localizeButton(StorageController.DEFAULT_BUNDLE_BASE_NAME, "EditBarCodeButton", jButtonEditBarcode);
        manager.localizeButton(StorageController.DEFAULT_BUNDLE_BASE_NAME, "PrintButton", jButtonPrint);

        refresh();
    }

    /**
     * Metoda provadi aktualizaci vsech comboBoxu a aktualizaci tabulky.
     * Zaroven prenastavuje statusBar.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public void refresh() throws RemoteException, NotBoundException, FileNotFoundException {
        setComboBoxModel(ServiceFacade.getInstance().getMaterialTypeNames(), jComboBoxMaterial);
        setComboBoxModel(ServiceFacade.getInstance().getUnitTypeNames(), jComboBoxUnit);
        refreshTable();
        statusBar.setMessage("Tento formulář slouží k editaci a úpravě skladových záznamů o surovinách.");
    }

    /**
     * Metoda kontrolujici spravnost vyplnenych udaju.
     *
     * @return Vraci index urcujici vstupni komponentu, ktera obsahuje
     * neplatny vstup. Pokud je vse vporadku tak navraci 0.
     */
    protected int isValidInput() {
        if (!Validator.isText(jTextFieldName)) {
            return 1;
        }
        if (!Validator.isText(jTextFieldBarcode)) {
            return 2;
        }
        if (!Validator.isPositiveDouble(jTextFieldMinimum)) {
            jTextFieldMinimum.setText("0");
        }
        if (!Validator.isSelectedItem(jComboBoxMaterial)) {
            return 4;
        }
        if (!Validator.isSelectedItem(jComboBoxUnit)) {
            return 5;
        }
        if (!Validator.isDouble(jTextDensity)) {
            return 6;
        }
        if (!Validator.isDouble(jTextCapacityPackage)) {
            return 7;
        }
        if (!Validator.isDouble(jTextEmptyWeight)) {
            return 8;
        }
        return 0;
    }

    /**
     * Metoda inicializujici tabulku.     
     */
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
                    jButtonInsert.setText("Vložit skladový záznam");
                    jButtonEditBarcode.setText("Editovat čárový kód");
                    jTextFieldBarcode.setEditable(false);
                    jComboBoxUnit.setEnabled(true);
                    statusBar.setMessage("Vložit nový skladový záznam.");
                } else {
                    jTextFieldName.setText((String) table.getModel().getValueAt(row, 1));
                    jTextFieldMinimum.setText(String.valueOf((Double) table.getModel().getValueAt(row, 6)));
                    jTextFieldBarcode.setText((String) table.getModel().getValueAt(row, 5));
                    jComboBoxMaterial.setSelectedItem((String) table.getModel().getValueAt(row, 3));
                    jComboBoxUnit.setSelectedItem((String) table.getModel().getValueAt(row, 4));
                    jTextDensity.setText(table.getModel().getValueAt(row, 7).toString());
                    jTextEmptyWeight.setText(table.getModel().getValueAt(row, 8).toString());
                    jTextCapacityPackage.setText(table.getModel().getValueAt(row, 9).toString());
                    jComboBoxUnit.setEnabled(false);
                    jButtonDelete.setEnabled(true);
                    jButtonInsert.setText("Upravit stávající záznam");
                    jButtonEditBarcode.setText("Editovat čárový kód");
                    jTextFieldBarcode.setEditable(false);
                    statusBar.setMessage("Vybrán záznamu ID: " + table.getValueAt(row, 0) + ", \"" + table.getValueAt(row, 1) + "\"");
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
    protected void refreshTable() throws FileNotFoundException, NotBoundException, RemoteException {
        ResultTableModel rtm = new ResultTableModel();
        Object[][] materials = ServiceFacade.getInstance().getMaterials();
        rtm.setColumnData(ResultTableModel.namesMaterial);
        rtm.setTableData(materials);
        table.setModel(rtm);
        table.getColumnModel().getColumn(1).setMinWidth(150);
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
    protected void clearFields() {
        Validator.clearComboBox(jComboBoxMaterial);
        Validator.clearComboBox(jComboBoxUnit);
        Validator.clearTextField(jTextFieldName);
        Validator.clearTextField(jTextFieldMinimum);
        Validator.clearTextField(jTextFieldBarcode);
        Validator.clearTextField(jTextCapacityPackage);
        Validator.clearTextField(jTextDensity);
        Validator.clearTextField(jTextEmptyWeight);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldName = new javax.swing.JTextField();
        jLabelName = new javax.swing.JLabel();
        jLabelType = new javax.swing.JLabel();
        jLabelUnit = new javax.swing.JLabel();
        jLabelMinimum = new javax.swing.JLabel();
        jLabelBarcode = new javax.swing.JLabel();
        jTextFieldMinimum = new javax.swing.JTextField();
        jLabelMinInfo = new javax.swing.JLabel();
        jTextFieldBarcode = new javax.swing.JTextField();
        jButtonBarcode = new javax.swing.JButton();
        jButtonInsert = new javax.swing.JButton();
        jButtonEditBarcode = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();
        jComboBoxMaterial = new javax.swing.JComboBox();
        jComboBoxUnit = new javax.swing.JComboBox();
        jButtonDelete = new javax.swing.JButton();
        jButtonPrint = new javax.swing.JButton();
        jLabelHustota = new javax.swing.JLabel();
        jLabelEmptyWeight = new javax.swing.JLabel();
        jLabelCapacity = new javax.swing.JLabel();
        jTextDensity = new javax.swing.JTextField();
        jTextEmptyWeight = new javax.swing.JTextField();
        jTextCapacityPackage = new javax.swing.JTextField();

        setToolTipText("Formulář slouží k evidování skladových záznamů o surovinách.");

        jLabelName.setText("Název suroviny");
        jLabelName.setToolTipText("Název dané suroviny.");

        jLabelType.setText("Typ suroviny");

        jLabelUnit.setText("Jednotka");
        jLabelUnit.setToolTipText("Určuje měrnou jednotku, ve které se budou zadávat příjmy dané suroviny na sklad.");

        jLabelMinimum.setText("Minimální normativní množství");
        jLabelMinimum.setToolTipText("Určuje stanovené minimální množství dané suroviny. Při dosažení této nebo nižší hodnoty systém o tomto stavu informuje uživatele.");

        jLabelBarcode.setText("Čárový kód");

        jLabelMinInfo.setText("[v dané jednotce]");

        jTextFieldBarcode.setEditable(false);

        jButtonBarcode.setText("Načíst čárový kód");
        jButtonBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBarcodeActionPerformed(evt);
            }
        });

        jButtonInsert.setText("Vložit skladový záznam");
        jButtonInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertActionPerformed(evt);
            }
        });

        jButtonEditBarcode.setText("Editovat čárový kód");
        jButtonEditBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditBarcodeActionPerformed(evt);
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

        jButtonPrint.setText("Vytisknout skladové záznamy");
        jButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintActionPerformed(evt);
            }
        });

        jLabelHustota.setText("Hustota:");

        jLabelEmptyWeight.setText("Váha prázdného balení:");

        jLabelCapacity.setText("Objem balení:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelMinimum)
                    .addComponent(jLabelType)
                    .addComponent(jLabelUnit)
                    .addComponent(jLabelBarcode)
                    .addComponent(jLabelName)
                    .addComponent(jLabelEmptyWeight)
                    .addComponent(jLabelCapacity)
                    .addComponent(jLabelHustota))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonPrint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextCapacityPackage)
                    .addComponent(jTextEmptyWeight)
                    .addComponent(jTextDensity)
                    .addComponent(jTextFieldBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextFieldMinimum, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelMinInfo))
                    .addComponent(jComboBoxUnit, 0, 174, Short.MAX_VALUE)
                    .addComponent(jComboBoxMaterial, 0, 174, Short.MAX_VALUE)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonInsert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 158, Short.MAX_VALUE)
                            .addComponent(jButtonClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                            .addComponent(jButtonDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonEditBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addComponent(jButtonBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonInsert)
                    .addComponent(jLabelName))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonClear)
                    .addComponent(jLabelType))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDelete)
                    .addComponent(jLabelUnit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldMinimum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMinimum)
                    .addComponent(jLabelMinInfo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBarcode)
                    .addComponent(jButtonBarcode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelHustota)
                    .addComponent(jTextDensity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEditBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEmptyWeight)
                    .addComponent(jTextEmptyWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCapacity)
                    .addComponent(jTextCapacityPackage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPrint)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        clearFields();
        jTextFieldBarcode.setEditable(false);
    }//GEN-LAST:event_jButtonClearActionPerformed

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        int i = isValidInput();
        if (i == 0) {
            String name = jTextFieldName.getText();
            String barcode = jTextFieldBarcode.getText();
            double density = Double.parseDouble(jTextDensity.getText());
            double emptyPackageWeight = Double.parseDouble(jTextEmptyWeight.getText());
            double packageCapacity = Double.parseDouble(jTextCapacityPackage.getText());
            double minimal = Double.parseDouble(jTextFieldMinimum.getText());
            int idMatType, idUnitType;
            try {
                idMatType = ServiceFacade.getInstance().getMaterialTypeByName((String) jComboBoxMaterial.getSelectedItem()).getMaterialTypeId();
                idUnitType = ServiceFacade.getInstance().getUnitTypeByName((String) jComboBoxUnit.getSelectedItem()).getUnitTypeId();
                boolean isOK;
                if (row == -1) {
                    isOK = ServiceFacade.getInstance().createMaterial(name, idMatType, idUnitType, barcode, minimal, density, emptyPackageWeight, packageCapacity);
                    if (!isOK) {
                        JOptionPane.showMessageDialog(this, "Záznam nemůže být uložen, protože záznam se stejným jménem nebo čárovým kódem je již uložen.", "Surovina", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                } else {
                    int materialId = (Integer) table.getModel().getValueAt(row, 0);
                    isOK = ServiceFacade.getInstance().updateMaterial(materialId, name, idMatType, idUnitType, barcode, minimal, density, emptyPackageWeight, packageCapacity);
                    if (!isOK) {
                        JOptionPane.showMessageDialog(this, "Záznam nemůže být aktualizován, protože záznam se stejným jménem nebo čárovým kódem je již uložen.", "Surovina", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                refreshTable();
            } catch (FileNotFoundException fnfe) {
                JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
            }
            clearFields();
        } else if (i == 1) {
            JOptionPane.showMessageDialog(this, "Údaj o názvu suroviny musí být vyplněn.", "Nová surovina", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 2) {
            JOptionPane.showMessageDialog(this, "Údaj o čárovém kódu musí být vyplněn.", "Nová surovina", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 3) {
            JOptionPane.showMessageDialog(this, "Minimální normativní množství - musí být kladné číslo.", "Nová surovina", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 4) {
            JOptionPane.showMessageDialog(this, "Musí být vybrán druh suroviny, do které daná surovina spadá..", "Nová surovina", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 5) {
            JOptionPane.showMessageDialog(this, "Musí být vybrána základní měrná jednotka.", "Nová surovina", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 6) {
            JOptionPane.showMessageDialog(this, "Hustota - musí být číslo.", "Nová surovina", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 7) {
            JOptionPane.showMessageDialog(this, "Objem balení - musí být číslo.", "Nová surovina", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 8) {
            JOptionPane.showMessageDialog(this, "Váha prázdného balení - musí být číslo.", "Nová surovina", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 9) {
            JOptionPane.showMessageDialog(this, "Obsah používaného balení - musí být číslo.", "Nová surovina", JOptionPane.INFORMATION_MESSAGE);
        }
        jTextFieldBarcode.setEditable(false);
}//GEN-LAST:event_jButtonInsertActionPerformed

    private void jButtonEditBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditBarcodeActionPerformed
        if (!jTextFieldBarcode.isEditable()) {
            jButtonEditBarcode.setText("Zamknout čárový kód");
            jTextFieldBarcode.setEditable(true);
        } else {
            jButtonEditBarcode.setText("Editovat čárový kód");
            jTextFieldBarcode.setEditable(false);
        }
    }//GEN-LAST:event_jButtonEditBarcodeActionPerformed

    private void jButtonBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBarcodeActionPerformed
        barcodeDialog = new BarcodeDialog(parent, true);
        barcodeDialog.setVisible(true);
        String barcode = barcodeDialog.getBarcode();
        if (barcode != null) {
            jTextFieldBarcode.setText(barcode);
        }
}//GEN-LAST:event_jButtonBarcodeActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        row = table.getSelectedRow();
        int materialId = (Integer) table.getModel().getValueAt(row, 0);
        try {
            if (!ServiceFacade.getInstance().isDeletableMaterial(materialId)) {
                JOptionPane.showMessageDialog(this, "Surovina nemůže být smazána, protože se na ni odkazují existující záznamy.", "Surovina", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                boolean ok = ServiceFacade.getInstance().deleteMaterial(materialId);
                if (!ok) {
                    JOptionPane.showMessageDialog(this, "Záznam nebyl smazán.", "Surovina", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            refreshTable();
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        jTextFieldBarcode.setEditable(false);
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintActionPerformed
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", "Suroviny");
        try {
            String template = "material.jasper";
            ConfigParser config = new ConfigParser();
            String printingFilePath = config.getTemplatesDir() + System.getProperty("file.separator") + template;
            File printFile = new File(printingFilePath);
            if (!printFile.exists()) {
                JOptionPane.showMessageDialog(this, "Šablona \"" + template + "\" nebyla v adresáři:\n\n "
                        + config.getTemplatesDir() + "\n\n nelezena. Pokud se šablony nachází v jiném adresáři, "
                        + "nastavte cestu k tomuto adresáři v nastavení.", "Tisková sestava nenalezena", JOptionPane.ERROR_MESSAGE);
                return;
            }
            PrintDialog printDialog = new PrintDialog(parent, true, params, printFile);
            printDialog.setTable(table);
            printDialog.setVisible(true);
            //Printer.compileReport(printFile, config.getTemplatesDir() + System.getProperty("file.separator") + "Material.jasper");
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        }
}//GEN-LAST:event_jButtonPrintActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBarcode;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonEditBarcode;
    private javax.swing.JButton jButtonInsert;
    private javax.swing.JButton jButtonPrint;
    private javax.swing.JComboBox jComboBoxMaterial;
    private javax.swing.JComboBox jComboBoxUnit;
    private javax.swing.JLabel jLabelBarcode;
    private javax.swing.JLabel jLabelCapacity;
    private javax.swing.JLabel jLabelEmptyWeight;
    private javax.swing.JLabel jLabelHustota;
    private javax.swing.JLabel jLabelMinInfo;
    private javax.swing.JLabel jLabelMinimum;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelType;
    private javax.swing.JLabel jLabelUnit;
    private javax.swing.JTextField jTextCapacityPackage;
    private javax.swing.JTextField jTextDensity;
    private javax.swing.JTextField jTextEmptyWeight;
    private javax.swing.JTextField jTextFieldBarcode;
    private javax.swing.JTextField jTextFieldMinimum;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables
}
