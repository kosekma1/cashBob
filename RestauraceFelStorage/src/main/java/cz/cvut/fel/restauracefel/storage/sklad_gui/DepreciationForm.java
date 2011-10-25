package cz.cvut.fel.restauracefel.storage.sklad_gui;

import cz.cvut.fel.restauracefel.hibernate.Material;
import cz.cvut.fel.restauracefel.hibernate.User;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
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
import java.awt.Stroke;

/**
 * Trida reprezentujici GUI formular pro odepisovani surovin.
 * Zaroven zajistuje vytvoreni a naplneni tabulky, vcetne zajisteni aktualizace.
 * Take se stara o zapisovani popisku do statusBaru.
 *
 * @author Jarda
 */
public class DepreciationForm extends AbstractForm {

    private JTable table = null;
    private JFrame parent = null;
    private StatusBar statusBar = null;
    private int row = -1;
    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    /**
     * Konstruktor tridy DepreciationForm.
     *
     * @param parent instance tridy JFrame jenz vytvorila tento formular
     * @param bar statusBar, do ktereho budou vypisovany popisky
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public DepreciationForm(JFrame parent, StatusBar bar) throws RemoteException, NotBoundException, FileNotFoundException {
        this.parent = parent;
        this.statusBar = bar;
        initComponents();
        initTable();

        LocalizationManager manager = LocalizationManager.getInstance();

        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "TypeLabel", jLabelType);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "MaterialLabel", jLabelMaterial);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "QuantityLabel", jLabelQuantity);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "ReasonLabel", jLabelReason);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "NoteLabel", jLabelNote);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "DateLabel", jLabelDate);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "CausedPersonLabel", jLabelCausedPerson);
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
        setComboBoxModel(ServiceFacade.getInstance().getReasonTypeNames(), jComboBoxReason);
        setComboBoxModel(ServiceFacade.getInstance().getUserUsernames(), jComboBoxPerson);
        setComboBoxModel(ServiceFacade.getInstance().getUserUsernames(), jComboBoxCausedPerson);
        refreshTable();
        statusBar.setMessage("Tento formulář slouží k editaci a úpravě údajů o odpisech surovin.");
    }

    /**
     * Metoda kontrolujici spravnost vyplnenych udaju.
     *
     * @return Vraci index urcujici vstupni komponentu, ktera obsahuje
     * neplatny vstup.
     */
    protected int isValidInput() {
        if (!Validator.isSelectedItem(jComboBoxMaterial)) return 1;
        if (!Validator.isPositiveDouble(jTextFieldQuantity)) return 2;
        if (!Validator.isSelectedItem(jComboBoxReason)) return 3;
        if (Validator.isValidDate(jTextFieldDate.getText()) == null) return 4;
        if (!Validator.isSelectedItem(jComboBoxCausedPerson)) return 5;
        if (!Validator.isSelectedItem(jComboBoxPerson)) return 6;        
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
                    statusBar.setMessage("Vložit nový záznam o odpisu ze skladu.");
                } else {
                    String material = (String) table.getModel().getValueAt(row, 2);                    
                    try {
                        Material m = ServiceFacade.getInstance().getMaterialByName(material);
                        if (m == null){
                            jComboBoxMaterialType.setSelectedItem(null);
                        } else {
                            jComboBoxMaterialType.setSelectedItem(m.getMaterialType().getName());
                            jComboBoxMaterial.setSelectedItem(m.getName());
                        }
                        
                        User userCaused = ServiceFacade.getInstance().getUserByUsername((String) table.getModel().getValueAt(row, 6));
                        if (userCaused == null){
                            jComboBoxCausedPerson.setSelectedItem(null);
                        } else {
                            jComboBoxCausedPerson.setSelectedItem(userCaused.getUsername());
                        }
                        User userPerson = ServiceFacade.getInstance().getUserByUsername((String) table.getModel().getValueAt(row, 7));
                        if (userPerson == null){
                            jComboBoxPerson.setSelectedItem(null);
                        } else {
                            jComboBoxPerson.setSelectedItem(userCaused.getUsername());
                        }
                    } catch (Exception ex){                        
                    }
                    jTextFieldQuantity.setText(String.valueOf(table.getModel().getValueAt(row, 3)));
                    jComboBoxUnit.setSelectedItem((String) table.getModel().getValueAt(row, 4));
                    jComboBoxCausedPerson.setSelectedItem((String) table.getModel().getValueAt(row, 5));
                    jTextArea1.setText((String) table.getModel().getValueAt(row, 8));                    
                    jTextFieldDate.setText((String) table.getModel().getValueAt(row, 1));
                    jButtonDelete.setEnabled(true);
                    jButtonInsert.setText("Upravit vybraný záznam");
                    statusBar.setMessage("Skladový odpis ID: "+table.getModel().getValueAt(row, 0)+", \""+table.getValueAt(row, 2)+", "+table.getModel().getValueAt(row, 3)+" "+table.getModel().getValueAt(row, 4)+", "+table.getModel().getValueAt(row, 5)+"\"");
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
        Object[][] depreciation = ServiceFacade.getInstance().getDepreciations();
        rtm.setColumnData(ResultTableModel.namesDepreciation);
        rtm.setTableData(depreciation);
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
        Validator.clearComboBox(jComboBoxReason);
        Validator.clearComboBox(jComboBoxCausedPerson);
        Validator.clearComboBox(jComboBoxPerson);
        Validator.clearTextField(jTextFieldQuantity);
        Validator.clearTextField(jTextArea1);
        Validator.clearDateField(jTextFieldDate);        
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
        jLabelCausedPerson = new javax.swing.JLabel();
        jLabelNote = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jComboBoxPerson = new javax.swing.JComboBox();
        jTextFieldDate = new javax.swing.JTextField();
        jLabelType = new javax.swing.JLabel();
        jComboBoxMaterialType = new javax.swing.JComboBox();
        jComboBoxMaterial = new javax.swing.JComboBox();
        jTextFieldQuantity = new javax.swing.JTextField();
        jButtonInsert = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jLabelDate = new javax.swing.JLabel();
        jComboBoxCausedPerson = new javax.swing.JComboBox();
        jLabelReason = new javax.swing.JLabel();
        jComboBoxReason = new javax.swing.JComboBox();
        jComboBoxUnit = new javax.swing.JComboBox();

        setToolTipText("Tento formulář slouží k odepisování \nskladových surovin ze skladu.");

        jLabelMaterial.setText("Surovina");

        jLabelQuantity.setText("Množství");

        jLabelPerson.setText("Zaevidoval");

        jLabelCausedPerson.setText("Zapříčinil");

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

        jLabelDate.setText("Datum");

        jLabelReason.setText("Důvod");

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
                    .addComponent(jLabelNote)
                    .addComponent(jLabelReason))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxMaterialType, 0, 188, Short.MAX_VALUE)
                    .addComponent(jTextFieldQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(jComboBoxMaterial, javax.swing.GroupLayout.Alignment.TRAILING, 0, 188, Short.MAX_VALUE)
                    .addComponent(jComboBoxReason, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPerson)
                            .addComponent(jLabelCausedPerson)
                            .addComponent(jLabelDate)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButtonClear, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(jButtonInsert, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(jComboBoxCausedPerson, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldDate, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxPerson, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jComboBoxMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jLabelQuantity)
                    .addComponent(jTextFieldQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDelete)
                    .addComponent(jComboBoxUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelReason)
                    .addComponent(jComboBoxReason, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabelNote))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelDate))
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelCausedPerson)
                                    .addComponent(jComboBoxCausedPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBoxPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelPerson)))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        int i = isValidInput();
        if (i == 0){
            double q = Double.parseDouble((String)jTextFieldQuantity.getText());
            Date date = Validator.isValidDate(jTextFieldDate.getText());
            String note = jTextArea1.getText();
            int  materialId, userReportId, userOffenId, reasonTypeId, fromUnitTypeId, toUnitTypeId;
            double quantity, current;
            try {
                materialId = ServiceFacade.getInstance().getMaterialByName((String)jComboBoxMaterial.getSelectedItem()).getMaterialId();
                userReportId = ServiceFacade.getInstance().getUserByUsername((String)jComboBoxPerson.getSelectedItem()).getUserId();
                userOffenId = ServiceFacade.getInstance().getUserByUsername((String)jComboBoxCausedPerson.getSelectedItem()).getUserId();
                reasonTypeId = ServiceFacade.getInstance().getReasonTypeByName((String)jComboBoxReason.getSelectedItem()).getReasonTypeId();
                fromUnitTypeId = ServiceFacade.getInstance().getUnitTypeByAbbr((String)jComboBoxUnit.getSelectedItem()).getUnitTypeId();
                toUnitTypeId = ServiceFacade.getInstance().getMaterialByID(materialId).getUnitType().getUnitTypeId();
                quantity = CodeList.transfer(fromUnitTypeId, toUnitTypeId, q);
                current = ServiceFacade.getInstance().getMaterialByID(materialId).getCurrentQuantity();
                boolean isOK;                
                if (row == -1){
                    if (current < quantity){
                        JOptionPane.showMessageDialog(this, "Na skladě není dostateční množství suroviny.", "Odpis suroviny", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    isOK = ServiceFacade.getInstance().createDepreciation(userReportId, userOffenId, materialId, quantity, date, reasonTypeId, note);
                    if (!isOK){                        
                        JOptionPane.showMessageDialog(this, "Záznam nemůže být uložen.", "Odpis suroviny", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                } else {
                    int depreciationId = (Integer)table.getModel().getValueAt(row, 0);
                    current = current + (Double)table.getModel().getValueAt(row, 3);
                    if (current < quantity){
                        JOptionPane.showMessageDialog(this, "Na skladě není dostateční množství suroviny.", "Odpis suroviny", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    isOK = ServiceFacade.getInstance().updateDepreciation(depreciationId, userReportId, userOffenId, materialId, quantity, date, reasonTypeId, note);
                    if (!isOK){
                        JOptionPane.showMessageDialog(this, "Záznam nemůže být aktualizován.", "Odpis suroviny", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                if (ServiceFacade.getInstance().getMaterialByID(materialId).getCurrentQuantity() <=
                        ServiceFacade.getInstance().getMaterialByID(materialId).getMinimal()){
                        JOptionPane.showMessageDialog(this, "Bylo dosaženo stanoveného minimálního množství.", "Výdej suroviny", JOptionPane.INFORMATION_MESSAGE);
                }
                refreshTable();
            } catch (FileNotFoundException fnfe){
                JOptionPane.showMessageDialog(this, "Konfigurační soubor \""+ConfigParser.getConfigFile()+"\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
            }
            clearFields();
        } else if (i == 1){
            JOptionPane.showMessageDialog(this, "Musí být vybrána surovina.", "Odpis suroviny", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 2){
            JOptionPane.showMessageDialog(this, "Musí být uveden údaj o množství odepisované suroviny, a to ve formátu kladného čísla.", "Odpis suroviny", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 3){
            JOptionPane.showMessageDialog(this, "Musí být uveden důvod odpisu suroviny.", "Odpis suroviny", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 4){
            JOptionPane.showMessageDialog(this, "Musí být uvedeno datum provedení odpisu suroviny.", "Odpis suroviny", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 5){
            JOptionPane.showMessageDialog(this, "Musí být uvedena odpovědná osoba za odpis suroviny.", "Odpis suroviny", JOptionPane.INFORMATION_MESSAGE);
        } else if (i == 6){
            JOptionPane.showMessageDialog(this, "Musí být uvedena osoba, která zaevidovala odpis.", "Odpis suroviny", JOptionPane.INFORMATION_MESSAGE);
        } 
}//GEN-LAST:event_jButtonInsertActionPerformed

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        clearFields();
}//GEN-LAST:event_jButtonClearActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        row = table.getSelectedRow();
        int depreciationId = (Integer)table.getModel().getValueAt(row, 0);
        try {
            boolean ok = ServiceFacade.getInstance().deleteDepreciation(depreciationId);
            if (!ok){
                JOptionPane.showMessageDialog(this, "Záznam nebyl smazán.", "Odpis suroviny", JOptionPane.INFORMATION_MESSAGE);
            }
            refreshTable();
        } catch (FileNotFoundException fnfe){
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \""+ConfigParser.getConfigFile()+"\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex){
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonInsert;
    private javax.swing.JComboBox jComboBoxCausedPerson;
    private javax.swing.JComboBox jComboBoxMaterial;
    private javax.swing.JComboBox jComboBoxMaterialType;
    private javax.swing.JComboBox jComboBoxPerson;
    private javax.swing.JComboBox jComboBoxReason;
    private javax.swing.JComboBox jComboBoxUnit;
    private javax.swing.JLabel jLabelCausedPerson;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelMaterial;
    private javax.swing.JLabel jLabelNote;
    private javax.swing.JLabel jLabelPerson;
    private javax.swing.JLabel jLabelQuantity;
    private javax.swing.JLabel jLabelReason;
    private javax.swing.JLabel jLabelType;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFieldDate;
    private javax.swing.JTextField jTextFieldQuantity;
    // End of variables declaration//GEN-END:variables
}
