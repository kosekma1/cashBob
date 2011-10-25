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
import cz.cvut.fel.restauracefel.sklad_service.ResultTableModel;
import cz.cvut.fel.restauracefel.sklad_service.ServiceFacade;
import cz.cvut.fel.restauracefel.library.service.Validator;

/**
 * Trida GUI pro editaci duvodu odpisu.
 *
 * @author Jarda
 */
public class ReasonTypeDialog extends AbstractDialog {

    private JScrollPane paneRt = null;
    private JScrollPane paneTable = null;
    private JTable table = null;
    private ReasonTypeForm rt = null;
    private JFrame parent = null;
    private int row = -1;

    /**
     * Konstruktor tridy ReasonTypeDialog.
     *
     * @param parent instance tridy JFrame jenz vytvorila tento formular
     * @param modal urcuje, zda bude dialog modalni
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public ReasonTypeDialog(JFrame parent, boolean modal) throws RemoteException, NotBoundException, FileNotFoundException {
        super(parent, modal);
        super.setTitle("Důvody odpisů surovin");
        this.parent = parent;
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
        paneRt = new JScrollPane();
        paneTable = new JScrollPane();
        table = new JTable();
        rt = new ReasonTypeForm();
        paneRt.getViewport().add(rt);
        paneTable.getViewport().add(table);
        Container con = getContentPane();
        con.setLayout(new BorderLayout());
        con.add(paneRt, BorderLayout.NORTH);
        con.add(paneTable, BorderLayout.CENTER);

        rt.getInsertButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    insertAction();
                } catch (FileNotFoundException fnfe){
                    JOptionPane.showMessageDialog(null, "Konfigurační soubor \""+ConfigParser.getConfigFile()+"\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        rt.getDeleteButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteAction();
                } catch (FileNotFoundException fnfe){
                    JOptionPane.showMessageDialog(null, "Konfigurační soubor \""+ConfigParser.getConfigFile()+"\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Metoda pro vytvoreni noveho zaznamu o duvodu odpisu dle vstupnich informaci.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    private void insertAction() throws NotBoundException, RemoteException, FileNotFoundException {
        int i = isValidInput();
        if (i == 0) {
            String name = rt.getNameTextField().getText();
            String note = rt.getNoteTextArea().getText();
            boolean isOK;
            if (row == -1){
                isOK = ServiceFacade.getInstance().createReasonType(name, note);
            } else {
                int reasonTypeId = (Integer) table.getModel().getValueAt(row, 0);
                isOK = ServiceFacade.getInstance().updateReasonType(reasonTypeId, name, note);
            }
            if (!isOK) {
                JOptionPane.showMessageDialog(this, "Záznam nemohl být uložen, protože záznam se stejným názvem odpisu suroviny je již uložen.", "Důvod odpisu", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            rt.clearFields();
            refresh();
        } else if (i == 1) {
            JOptionPane.showMessageDialog(this, "Musí být uveden název nového odpisu suroviny.", "Důvod odpisu", JOptionPane.INFORMATION_MESSAGE);
        }        
    }

    /**
     * Metoda provadi smazani vybraneho duvodu odpisu.
     * 
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    private void deleteAction() throws NotBoundException, RemoteException, FileNotFoundException{
        row = table.getSelectedRow();
        int reasonTypeId = (Integer) table.getModel().getValueAt(row, 0);
        if (!ServiceFacade.getInstance().isDeletableReasonType(reasonTypeId)){
            JOptionPane.showMessageDialog(this, "Důvod odpisu suroviny nemůže být smazán, protože se na něho odkazují existující záznamy o odpisech.", "Důvod odpisu", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        boolean isOK = ServiceFacade.getInstance().deleteReasonType(reasonTypeId);
        if (!isOK){
            JOptionPane.showMessageDialog(this, "Záznam nebyl smazán.", "Důvod odpisu", JOptionPane.INFORMATION_MESSAGE);
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
        if (!Validator.isText(rt.getNameTextField())) return 1;
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
                    rt.clearFields();
                    rt.getDeleteButton().setEnabled(false);
                    rt.getInsertButton().setText("Vložit záznam");
                    rt.getInsertButton().setToolTipText("Vložit nový záznam");
                } else {
                    rt.getNameTextField().setText((String) table.getValueAt(row, 1));
                    rt.getNoteTextArea().setText((String) table.getValueAt(row, 2));
                    rt.getDeleteButton().setEnabled(true);
                    rt.getInsertButton().setText("Aktualizovat záznam");
                    rt.getInsertButton().setToolTipText("Smazat vybraný záznam");
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
        rtm.setColumnData(ResultTableModel.namesReasonType);
        Object[][] reasonTypes = ServiceFacade.getInstance().getReasonTypes();
        rtm.setTableData(reasonTypes);
        table.setModel(rtm);
    }
}
