package cz.cvut.fel.restauracefel.storage.sklad_gui;

import cz.cvut.fel.restauracefel.hibernate.Material;
import cz.cvut.fel.restauracefel.hibernate.User;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import cz.cvut.fel.restauracefel.library.service.CodeList;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.library.service.LocalizationManager;
import cz.cvut.fel.restauracefel.sklad_service.ResultTableModel;
import cz.cvut.fel.restauracefel.sklad_service.ServiceFacade;
import cz.cvut.fel.restauracefel.library.service.Validator;
import cz.cvut.fel.restauracefel.storage.storageController.StorageController;

/**
 * Trida reprezentujici GUI formular pro prijem surovin na sklad.
 * Zaroven zajistuje vytvoreni a naplneni tabulky, vcetne zajisteni aktualizace.
 * Take se stara o zapisovani popisku do statusBaru.
 *
 * @author Jarda
 */
public class IncomeForm extends AbstractForm {

    private JTable table = null;
    private JFrame parent = null;
    private StatusBar statusBar = null;
    private int row = -1;
    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    private ConfigParser config = null;

    /**
     * Konstruktor tridy incomeForm
     *
     * @param parent instance tridy JFrame jenz vytvorila tento formular
     * @param bar statusBar, do ktereho budou vypisovany popisky
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public IncomeForm(JFrame parent, StatusBar bar) throws FileNotFoundException, RemoteException, NotBoundException {
        this.parent = parent;
        this.statusBar = bar;
        initComponents();
        initTable();

        LocalizationManager manager = LocalizationManager.getInstance();

        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "LoadMaterialLabel", jLabel1);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "TypeLabel", jLabelType);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "MaterialLabel", jLabelMaterial);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "QuantityLabel", jLabelQuantity);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "NoteLabel", jLabelNote);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "DateLabel", jLabelDate);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "PersonLabel", jLabelPerson);

        manager.localizeButton(StorageController.DEFAULT_BUNDLE_BASE_NAME, "InsertButton", jButtonInsert);
        manager.localizeButton(StorageController.DEFAULT_BUNDLE_BASE_NAME, "ClearButton", jButtonClear);
        manager.localizeButton(StorageController.DEFAULT_BUNDLE_BASE_NAME, "DeleteButton", jButtonDelete);

        config = new ConfigParser();
        refreshSettings();
        setComboBoxesListeners(jComboBoxMaterialType, jComboBoxMaterial, jComboBoxUnit);
        refresh();
        clearFields();
    }

    /**
     * Metoda aktualizuje formular dle udaju ulozenych v config. souboru.
     *
     * @throws java.io.FileNotFoundException
     */
    public void refreshSettings() throws FileNotFoundException {
        String money = " v " + config.getMoney();
        jLabelMoney.setText(money);
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
        setComboBoxModel(ServiceFacade.getInstance().getMaterialTypeNames(), jComboBoxMaterialType);
        setComboBoxModel(ServiceFacade.getInstance().getUserUsernames(), jComboBoxUser);
        refreshTable();
        statusBar.setMessage("Tento formulář slouží k editaci a úpravě záznamů o skladových příjmech.");
    }

    /**
     * Metoda kontrolujici spravnost vyplnenych udaju.
     *
     * @return Vraci index urcujici vstupni komponentu, ktera obsahuje
     * neplatny vstup.
     */
    protected int isValidInput() {
        if (!Validator.isSelectedItem(jComboBoxMaterial)) {
            return 1;
        }
        if (!Validator.isPositiveDouble(jTextFieldQuantity)) {
            return 2;
        }
        if (!Validator.isPositiveDouble(jTextFieldPrice)) {
            jTextFieldPrice.setText("0");
        }
        if (Validator.isValidDate(jTextFieldDate.getText()) == null) {
            return 4;
        }
        if (!Validator.isSelectedItem(jComboBoxUser)) {
            return 5;
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
                    jButtonInsert.setText("Vložit položku");
                    statusBar.setMessage("Vložit nový skladový příjem.");
                } else {
                    String material = (String) table.getModel().getValueAt(row, 2);
                    try {
                        Material m = ServiceFacade.getInstance().getMaterialByName(material);
                        if (m == null) {
                            jComboBoxMaterialType.setSelectedItem(null);
                        } else {
                            jComboBoxMaterialType.setSelectedItem(m.getMaterialType().getName());
                            jComboBoxMaterial.setSelectedItem(m.getName());
                        }

                        User u = ServiceFacade.getInstance().getUserByUsername((String) table.getModel().getValueAt(row, 6));
                        if (u == null) {
                            jComboBoxUser.setSelectedItem(null);
                        } else {
                            jComboBoxUser.setSelectedItem(u.getUsername());
                        }
                    } catch (Exception ex) {
                    }
                    jTextFieldQuantity.setText(String.valueOf(table.getModel().getValueAt(row, 3)));
                    jTextFieldPrice.setText(String.valueOf(table.getModel().getValueAt(row, 5)));
                    jTextArea1.setText((String) table.getModel().getValueAt(row, 7));
                    jComboBoxUnit.setSelectedItem((String) table.getModel().getValueAt(row, 4));
                    jTextFieldDate.setText((String) table.getModel().getValueAt(row, 1));
                    jButtonDelete.setEnabled(true);
                    jButtonInsert.setText("Upravit vybraný záznam");
                    statusBar.setMessage("Skladový příjem ID: " + table.getValueAt(row, 0) + ", \"" + table.getValueAt(row, 2) + ", " + table.getModel().getValueAt(row, 3) + " " + table.getModel().getValueAt(row, 4) + "\"");
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
        Object[][] incomes = ServiceFacade.getInstance().getIncomes();
        rtm.setColumnData(ResultTableModel.namesIncome);
        rtm.setTableData(incomes);
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
    protected void clearFields() {
        Validator.clearComboBox(jComboBoxMaterialType);
        Validator.clearTextField(jTextFieldQuantity);
        Validator.clearTextField(jTextFieldPrice);
        Validator.clearTextField(jTextArea1);
        Validator.clearDateField(jTextFieldDate);
        Validator.clearComboBox(jComboBoxUser);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelMaterial = new javax.swing.JLabel();
        jLabelQuantity = new javax.swing.JLabel();
        jLabelPrice = new javax.swing.JLabel();
        jLabelPerson = new javax.swing.JLabel();
        jLabelDate = new javax.swing.JLabel();
        jLabelNote = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jComboBoxUser = new javax.swing.JComboBox();
        jTextFieldDate = new javax.swing.JTextField();
        jLabelType = new javax.swing.JLabel();
        jComboBoxMaterialType = new javax.swing.JComboBox();
        jComboBoxMaterial = new javax.swing.JComboBox();
        jTextFieldQuantity = new javax.swing.JTextField();
        jTextFieldPrice = new javax.swing.JTextField();
        jButtonInsert = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jComboBoxUnit = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        barcodeText = new javax.swing.JTextField();
        jLabelMoney = new javax.swing.JLabel();

        setToolTipText("Formulář pro evidování skladových příjmů.");

        jLabelMaterial.setText("Surovina");

        jLabelQuantity.setText("Množství");

        jLabelPrice.setText("Cena");

        jLabelPerson.setText("Zaevidoval");

        jLabelDate.setText("Datum");

        jLabelNote.setText("Poznámka");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabelType.setText("Druh suroviny");

        jButtonInsert.setText("Vložit záznam");
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

        jButtonDelete.setText("Odstranit záznam");
        jButtonDelete.setEnabled(false);
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jLabel1.setText("Načíst surovinu");

        barcodeText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                barcodeTextKeyPressed(evt);
            }
        });

        jLabelMoney.setText("Kč");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelType)
                            .addComponent(jLabelMaterial)
                            .addComponent(jLabelQuantity)
                            .addComponent(jLabelNote)
                            .addComponent(jLabelPrice))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(jComboBoxMaterial, javax.swing.GroupLayout.Alignment.LEADING, 0, 179, Short.MAX_VALUE)
                            .addComponent(jComboBoxMaterialType, javax.swing.GroupLayout.Alignment.LEADING, 0, 179, Short.MAX_VALUE)
                            .addComponent(jTextFieldQuantity, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(barcodeText)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDate)
                            .addComponent(jLabelPerson)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelMoney)
                            .addComponent(jComboBoxUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldDate, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(jComboBoxUser, 0, 142, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonDelete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addComponent(jButtonInsert, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addComponent(jButtonClear, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(barcodeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jLabelType)
                    .addComponent(jComboBoxMaterialType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonInsert))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jLabelMaterial)
                    .addComponent(jButtonClear)
                    .addComponent(jComboBoxMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jLabelQuantity)
                    .addComponent(jTextFieldQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDelete)
                    .addComponent(jComboBoxUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPrice)
                    .addComponent(jTextFieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMoney))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelDate))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBoxUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelPerson)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabelNote))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        int i = isValidInput();
        if (i == 0) {
            double q = Double.parseDouble((String) jTextFieldQuantity.getText());
            Double price = Double.parseDouble((String) jTextFieldPrice.getText());
            Date date = Validator.isValidDate(jTextFieldDate.getText());
            String note = jTextArea1.getText();
            int materialId, userId, fromUnitTypeId, toUnitTypeId;
            double quantity;
            try {
                materialId = ServiceFacade.getInstance().getMaterialByName((String) jComboBoxMaterial.getSelectedItem()).getMaterialId();
                userId = ServiceFacade.getInstance().getUserByUsername((String) jComboBoxUser.getSelectedItem()).getUserId();
                fromUnitTypeId = ServiceFacade.getInstance().getUnitTypeByAbbr((String) jComboBoxUnit.getSelectedItem()).getUnitTypeId();
                toUnitTypeId = ServiceFacade.getInstance().getMaterialByID(materialId).getUnitType().getUnitTypeId();
                quantity = CodeList.transfer(fromUnitTypeId, toUnitTypeId, q);
                boolean isOK;
                if (row == -1) {
                    isOK = ServiceFacade.getInstance().createIncome(date, materialId, quantity, price, userId, note);
                    if (!isOK) {
                        JOptionPane.showMessageDialog(parent, "Záznam nemohl být uložen.", "Vložení záznamu", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    int incomeId = (Integer) table.getModel().getValueAt(row, 0);
                    isOK = ServiceFacade.getInstance().updateIncome(incomeId, date, materialId, quantity, price, userId, note);
                    if (!isOK) {
                        JOptionPane.showMessageDialog(parent, "Záznam nemohl být aktualizován.", "Aktualizace záznamu", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Musí být vybrána surovina.", "Příjem suroviny", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 2) {
            JOptionPane.showMessageDialog(this, "Musí být uveden údaj o množství, a to ve formátu kladného čísla.", "Příjem suroviny", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 3) {
            JOptionPane.showMessageDialog(this, "Musí být uveden údaj o ceně, a to ve formátu kladného čísla.", "Příjem suroviny", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 4) {
            JOptionPane.showMessageDialog(this, "Musí být uvedeno datum skladového příjmu.", "Příjem suroviny", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 5) {
            JOptionPane.showMessageDialog(this, "Musí být uvedena osoba, která zaevidovala příjem suroviny.", "Příjem suroviny", JOptionPane.INFORMATION_MESSAGE);
        }
}//GEN-LAST:event_jButtonInsertActionPerformed

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        clearFields();
}//GEN-LAST:event_jButtonClearActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        row = table.getSelectedRow();
        int incomeId = (Integer) table.getModel().getValueAt(row, 0);
        try {
            boolean ok = ServiceFacade.getInstance().deleteIncome(incomeId);
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Záznam nebyl smazán.", "Záznam nebyl smazán", JOptionPane.INFORMATION_MESSAGE);
            }
            refreshTable();
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void barcodeTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barcodeTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                List<Material> materials = ServiceFacade.getInstance().getAllMaterials();
                Material found = null;
                for (Material mat : materials) {
                    if (mat.getBarcode().equalsIgnoreCase(barcodeText.getText())) {
                        found = mat;
                        break;
                    }
                }
                if (found == null) {
                    JOptionPane.showMessageDialog(this, "Surovina nebyla nalezena.", "Surovina nebyla nalezena.", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                for (int i = 0; i < jComboBoxMaterialType.getItemCount(); i++) {
                    if (jComboBoxMaterialType.getItemAt(i).equals(found.getMaterialType().getName())) {
                        jComboBoxMaterialType.setSelectedIndex(i);
                        break;
                    }
                }
                for (int i = 0; i < jComboBoxMaterial.getItemCount(); i++) {
                    if (jComboBoxMaterial.getItemAt(i).equals(found.getName())) {
                        jComboBoxMaterial.setSelectedIndex(i);
                        break;
                    }
                }
                jTextFieldQuantity.requestFocus();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(IncomeForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(IncomeForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException e) {
                //
            }
        }
    }//GEN-LAST:event_barcodeTextKeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField barcodeText;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonInsert;
    private javax.swing.JComboBox jComboBoxMaterial;
    private javax.swing.JComboBox jComboBoxMaterialType;
    private javax.swing.JComboBox jComboBoxUnit;
    private javax.swing.JComboBox jComboBoxUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelMaterial;
    private javax.swing.JLabel jLabelMoney;
    private javax.swing.JLabel jLabelNote;
    private javax.swing.JLabel jLabelPerson;
    private javax.swing.JLabel jLabelPrice;
    private javax.swing.JLabel jLabelQuantity;
    private javax.swing.JLabel jLabelType;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFieldDate;
    private javax.swing.JTextField jTextFieldPrice;
    private javax.swing.JTextField jTextFieldQuantity;
    // End of variables declaration//GEN-END:variables
}
