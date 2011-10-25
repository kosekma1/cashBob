package cz.cvut.fel.restauracefel.storage.sklad_gui;

import cz.cvut.fel.restauracefel.hibernate.Material;
import cz.cvut.fel.restauracefel.hibernate.User;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
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
 * Trida reprezentujici GUI formular pro vydej surovin.
 * Zaroven zajistuje vytvoreni a naplneni tabulky, vcetne zajisteni aktualizace.
 * Take se stara o zapisovani popisku do statusBaru.
 *
 * @author Jarda
 */
public class ExpenditureForm extends AbstractForm {

    private JTable table = null;
    private JFrame parent = null;
    private StatusBar statusBar = null;
    private int row = -1;

    /**
     * Konstruktor tridy ExpenditureForm
     *
     * @param parent instance tridy JFrame jenz vytvorila tento formular
     * @param bar statusBar, do ktereho budou vypisovany popisky
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public ExpenditureForm(JFrame parent, StatusBar bar) throws RemoteException, NotBoundException, FileNotFoundException {
        this.parent = parent;
        this.statusBar = bar;
        initComponents();
        initTable();

        LocalizationManager manager = LocalizationManager.getInstance();

        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "TypeLabel", jLabelType);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "MaterialLabel", jLabelMaterial);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "QuantityLabel", jLabelQuantity);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "NoteLabel", jLabelNote);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "DateLabel", jLabelDate);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "PersonLabel", jLabelPerson);

        manager.localizeButton(StorageController.DEFAULT_BUNDLE_BASE_NAME, "InsertButton", jButtonInsert);
        manager.localizeButton(StorageController.DEFAULT_BUNDLE_BASE_NAME, "ClearButton", jButtonClear);
        manager.localizeButton(StorageController.DEFAULT_BUNDLE_BASE_NAME, "DeleteButton", jButtonDelete);

        setComboBoxesListeners(jComboBoxMaterialType, jComboBoxMaterial, jComboBoxUnit);
        refresh();
        clearFields();
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
        statusBar.setMessage("Tento formulář slouží k editaci a úpravě záznamů o výdejích surovin ze skladu.");
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
        if (Validator.isValidDate(jTextFieldDate.getText()) == null) {
            return 3;
        }
        if (!Validator.isSelectedItem(jComboBoxUser)) {
            return 4;
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
                    jButtonInsert.setText("Vložit záznam");
                    statusBar.setMessage("Vložit nový záznam o skladovém výdeji.");
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

                        User u = ServiceFacade.getInstance().getUserByUsername((String) table.getModel().getValueAt(row, 5));
                        if (u == null) {
                            jComboBoxUser.setSelectedItem(null);
                        } else {
                            jComboBoxUser.setSelectedItem(u.getUsername());
                        }
                    } catch (Exception ex) {
                    }
                    jTextFieldQuantity.setText(String.valueOf(table.getModel().getValueAt(row, 3)));
                    jComboBoxUnit.setSelectedItem((String) table.getModel().getValueAt(row, 4));
                    jTextArea1.setText((String) table.getModel().getValueAt(row, 6));
                    jTextFieldDate.setText((String) table.getModel().getValueAt(row, 1));
                    jButtonDelete.setEnabled(true);
                    jButtonInsert.setText("Upravit vybraný záznam");
                    statusBar.setMessage("Skladový výdej ID: " + table.getValueAt(row, 0) + ", \"" + table.getValueAt(row, 2) + ", " + table.getModel().getValueAt(row, 3) + " " + table.getModel().getValueAt(row, 4) + "\"");
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
        Object[][] expenditures = ServiceFacade.getInstance().getExpenditures();
        rtm.setColumnData(ResultTableModel.namesExpenditure);
        rtm.setTableData(expenditures);
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
        jButtonInsert = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jComboBoxUnit = new javax.swing.JComboBox();

        setToolTipText("Formulář pro evidování skladových výdejů.");

        jLabelMaterial.setText("Surovina");

        jLabelQuantity.setText("Množství");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelType)
                    .addComponent(jLabelMaterial)
                    .addComponent(jLabelQuantity)
                    .addComponent(jLabelNote))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxMaterialType, 0, 188, Short.MAX_VALUE)
                    .addComponent(jTextFieldQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(jComboBoxMaterial, javax.swing.GroupLayout.Alignment.TRAILING, 0, 188, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPerson)
                            .addComponent(jLabelDate)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldDate, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(jComboBoxUser, 0, 142, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonDelete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, Short.MAX_VALUE)
                            .addComponent(jButtonInsert, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                            .addComponent(jButtonClear, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, Short.MAX_VALUE))
                        .addGap(5, 5, 5)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
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
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelDate)
                            .addComponent(jTextFieldDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPerson)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabelNote))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        int i = isValidInput();
        if (i == 0) {
            double q = Double.parseDouble((String) jTextFieldQuantity.getText());
            Date date = Validator.isValidDate(jTextFieldDate.getText());
            String note = jTextArea1.getText();
            int materialId, userReportId, fromUnitTypeId, toUnitTypeId;
            double quantity, current;
            try {
                materialId = ServiceFacade.getInstance().getMaterialByName((String) jComboBoxMaterial.getSelectedItem()).getMaterialId();
                userReportId = ServiceFacade.getInstance().getUserByUsername((String) jComboBoxUser.getSelectedItem()).getUserId();
                fromUnitTypeId = ServiceFacade.getInstance().getUnitTypeByAbbr((String) jComboBoxUnit.getSelectedItem()).getUnitTypeId();
                toUnitTypeId = ServiceFacade.getInstance().getMaterialByID(materialId).getUnitType().getUnitTypeId();
                quantity = CodeList.transfer(fromUnitTypeId, toUnitTypeId, q);
                current = ServiceFacade.getInstance().getMaterialByID(materialId).getCurrentQuantity();
                boolean isOK;
                if (row == -1) {
                    if (current < quantity) {
                        JOptionPane.showMessageDialog(this, "Na skladě není dostateční množství suroviny.", "Výdej suroviny", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    isOK = ServiceFacade.getInstance().createExpenditure(date, materialId, quantity, userReportId, note);
                    if (!isOK) {
                        JOptionPane.showMessageDialog(this, "Záznam nemůže být uložen.", "Výdej suroviny", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                } else {
                    int expenditureId = (Integer) table.getModel().getValueAt(row, 0);
                    current = current + (Double) table.getModel().getValueAt(row, 3);
                    if (current < quantity) {
                        JOptionPane.showMessageDialog(this, "Na skladě není dostateční množství suroviny.", "Odpis suroviny", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    isOK = ServiceFacade.getInstance().updateExpenditure(expenditureId, date, materialId, quantity, userReportId, note);
                    if (!isOK) {
                        JOptionPane.showMessageDialog(this, "Záznam nemůže být aktualizován.", "Výdej suroviny", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                if (ServiceFacade.getInstance().getMaterialByID(materialId).getCurrentQuantity()
                        <= ServiceFacade.getInstance().getMaterialByID(materialId).getMinimal()) {
                    JOptionPane.showMessageDialog(this, "Bylo dosaženo stanoveného minimálního množství.", "Výdej suroviny", JOptionPane.INFORMATION_MESSAGE);
                }
                refreshTable();
            } catch (FileNotFoundException fnfe) {
                JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
            }
            clearFields();
        } else if (i == 1) {
            JOptionPane.showMessageDialog(this, "Musí být vybrána surovina.", "Výdej suroviny", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 2) {
            JOptionPane.showMessageDialog(this, "Musí být uveden údaj o množství vydané suroviny, a to ve formátu kladného čísla.", "Výdej suroviny", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 3) {
            JOptionPane.showMessageDialog(this, "Musí být uvedeno datum vydání suroviny.", "Výdej suroviny", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 4) {
            JOptionPane.showMessageDialog(this, "Musí být uvedena osoba, která zaevidovala výdej suroviny.", "Výdej suroviny", JOptionPane.INFORMATION_MESSAGE);
        }
}//GEN-LAST:event_jButtonInsertActionPerformed

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        clearFields();
}//GEN-LAST:event_jButtonClearActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        row = table.getSelectedRow();
        int expenditurId = (Integer) table.getModel().getValueAt(row, 0);
        try {
            boolean ok = ServiceFacade.getInstance().deleteExpenditure(expenditurId);
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Záznam nebyl smazán.", "Výdej suroviny", JOptionPane.INFORMATION_MESSAGE);
            }
            refreshTable();
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonInsert;
    private javax.swing.JComboBox jComboBoxMaterial;
    private javax.swing.JComboBox jComboBoxMaterialType;
    private javax.swing.JComboBox jComboBoxUnit;
    private javax.swing.JComboBox jComboBoxUser;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelMaterial;
    private javax.swing.JLabel jLabelNote;
    private javax.swing.JLabel jLabelPerson;
    private javax.swing.JLabel jLabelQuantity;
    private javax.swing.JLabel jLabelType;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFieldDate;
    private javax.swing.JTextField jTextFieldQuantity;
    // End of variables declaration//GEN-END:variables
}
