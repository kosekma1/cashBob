package cz.cvut.fel.restauracefel.storage.sklad_gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
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
 * Trida reprezentujici GUI pro editaci druhu materialu.
 *
 * @author Jarda
 */
public class MaterialTypeDialog extends AbstractDialog {

    private JScrollPane paneMt = null;
    private JScrollPane paneTable = null;
    private JTable table = null;
    private MaterialTypeForm mt = null;
    private int row = -1;

    /**
     * Konstruktor tridy MaterialTypeDialog.
     *
     * @param parent instance tridy JFrame jenz vytvorila tento formular
     * @param modal urcuje, zda bude dialog modalni
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public MaterialTypeDialog(JFrame parent, boolean modal) throws RemoteException, NotBoundException, FileNotFoundException {
        super(parent, modal);

        LocalizationManager.getInstance().localizeDialog(StorageController.DEFAULT_BUNDLE_BASE_NAME, "MaterialTypesDialog", this);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int y = (int) ((dim.getHeight() - 350) / 2);
        int x = (int) ((dim.getWidth() - 400) / 2);
        setBounds(x, y, 400, 350);
        initComponents();
        initTable();
    }

    /**
     * Metoda pro inicializaci komponent dialogu.
     */
    protected void initComponents() {
        paneMt = new JScrollPane();
        paneTable = new JScrollPane();
        table = new JTable();
        mt = new MaterialTypeForm();
        paneMt.getViewport().add(mt);
        paneTable.getViewport().add(table);
        Container con = getContentPane();
        con.setLayout(new BorderLayout());
        con.add(paneMt, BorderLayout.NORTH);
        con.add(paneTable, BorderLayout.CENTER);

        mt.getInsertButton().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    insertAction();
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(null, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mt.getDeleteButton().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    deleteAction();
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(null, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Metoda pro vytvoreni noveho druhu materialu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    private void insertAction() throws NotBoundException, RemoteException, FileNotFoundException {
        int i = isValidInput();
        if (i == 0) {
            String name = mt.getNameTextField().getText();
            String note = mt.getNoteTextArea().getText();
            boolean isOK;
            if (row == -1) {
                isOK = ServiceFacade.getInstance().createMaterialType(name, note);
                if (!isOK) {
                    JOptionPane.showMessageDialog(this, "Záznam nemohl být uložen, protože záznam se stejným názvem druhu suroviny je již uložen.", "Druh suroviny", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            } else {
                int materialTypeId = (Integer) table.getModel().getValueAt(row, 0);
                isOK = ServiceFacade.getInstance().updateMaterialType(materialTypeId, name, note);
                if (!isOK) {
                    JOptionPane.showMessageDialog(this, "Záznam nemohl být aktualizován, protože záznam se stejným názvem druhu suroviny je již uložen.", "Druh suroviny", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            mt.clearFields();
            refresh();
        } else if (i == 1) {
            JOptionPane.showMessageDialog(this, "Musí být uveden název nového druhu suroviny.", "Druh suroviny", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Metoda provadi smazani vybraneho druhu materialu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    private void deleteAction() throws NotBoundException, RemoteException, FileNotFoundException {
        row = table.getSelectedRow();
        int materialTypeId = (Integer) table.getModel().getValueAt(row, 0);
        if (!ServiceFacade.getInstance().isDeletableMaterialType(materialTypeId)) {
            JOptionPane.showMessageDialog(this, "Druh suroviny nemůže být smazán, protože se na něho odkazují existující suroviny.", "Druh suroviny", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        boolean isOK = ServiceFacade.getInstance().deleteMaterialType(materialTypeId);
        if (!isOK) {
            JOptionPane.showMessageDialog(this, "Záznam nebyl smazán.", "Druh suroviny", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        refresh();
    }

    /**
     * Metoda kontrolujici spravnost zadanych informaci ve vstupnich komponentach.
     *
     * @return 0 v pripade, ze jsou vsechny vstupy OK; jinak navraci cislo
     * vstupni komponenty
     */
    protected int isValidInput() {
        if (!Validator.isText(mt.getNameTextField())) {
            return 1;
        }
        return 0;
    }

    /**
     * Metoda inicializujici tabulku.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    protected void initTable() throws RemoteException, NotBoundException, FileNotFoundException {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                row = table.getSelectedRow();
                if (row == -1) {
                    mt.clearFields();
                    mt.getDeleteButton().setEnabled(false);
                    mt.getInsertButton().setText("Vložit záznam");
                    mt.getInsertButton().setToolTipText("Vložit nový záznam");
                } else {
                    mt.getNameTextField().setText((String) table.getValueAt(row, 1));
                    mt.getNoteTextArea().setText((String) table.getValueAt(row, 2));
                    mt.getDeleteButton().setEnabled(true);
                    mt.getInsertButton().setText("Aktualizovat záznam");
                    mt.getInsertButton().setToolTipText("Smazat vybraný záznam");
                }
            }
        });
        refresh();
    }

    /**
     * Metoda aktualizujici tabulku.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    protected void refresh() throws NotBoundException, RemoteException, FileNotFoundException {
        ResultTableModel rtm = new ResultTableModel();
        rtm.setColumnData(ResultTableModel.namesMatType);
        Object[][] matTypes = ServiceFacade.getInstance().getMaterialTypes();
        rtm.setTableData(matTypes);
        table.setModel(rtm);
    }
}
