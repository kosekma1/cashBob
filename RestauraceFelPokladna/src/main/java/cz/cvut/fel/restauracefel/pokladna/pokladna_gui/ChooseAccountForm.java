package cz.cvut.fel.restauracefel.pokladna.pokladna_gui;

import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.pokladna_service.ServiceFacade;

/**
 * Trida reprezentujici GUI formular pro vyber uctu pro novou objednavku, placeni, nebo presouvani polozek.
 *
 * @author Tomas Hnizdil
 */
public class ChooseAccountForm extends AbstractForm {

    private MainFrame parent = null;
    private StatusBar statusBar = null;
    private CreateOrderForm createOrderForm = null;
    private PayOrderForm payOrderForm = null;
    private MoveOrderForm moveOrderForm = null;
    private EnumOrder action;
    private ImageIcon money = new ImageIcon("images/money.png");
    private ImageIcon pencil = new ImageIcon("images/pencil.png");
    private ImageIcon move = new ImageIcon("images/move.png");
    private ImageIcon icon = null;


    /**
     * Konstruktor tridy ChooseAccountForm
     *
     * @param parent instance tridy MainFrame jenz vytvorila tento formular
     * @param bar statusBar, do ktereho budou vypisovany popisky
     * @param action priznak, zda se jedna o objednavani, placeni, nebo presouvani polozek
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public ChooseAccountForm(MainFrame parent, StatusBar bar, int loggedUserId, EnumOrder action) throws FileNotFoundException, RemoteException, NotBoundException {
        this.parent = parent;
        this.statusBar = bar;
        this.action = action;
        initComponents();
        switch (action) {
            case CREATE_ORDER:
                jLabelTitle.setText("Vytvořit novou objednávku");
                this.icon = pencil;
                this.repaint();
                break;
            case PAY_ORDER:
                jLabelTitle.setText("Zaplatit účet");
                this.icon = money;
                this.repaint();
                break;
            case MOVE_ORDER:
                jLabelTitle.setText("Přesunout položky z účtu");
                this.icon = move;
                this.repaint();
                break;
            default:
                break;
        }
        refresh();
        clearFields();
    }

    /**
     * Metoda vola inicializaci tlacitek s ucty a prenastavuje statusBar.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    @Override
    protected void refresh() throws RemoteException, NotBoundException, FileNotFoundException {
        initButtonGroup();
        statusBar.setMessage("Tento formulář slouží k vybrání účtu.");
    }

    /**
     * Metoda vytvari a zobrazuje novy formular pro placeni uctu.
     *
     * @param accountId Id uctu, ze ktereho se bude platit
     *
     */
    protected void payOrder(int accountId) {
        try {
            payOrderForm = new PayOrderForm(parent, statusBar, accountId);
            if (!payOrderForm.closed) {
                parent.panel.getViewport().add(payOrderForm);
                parent.panel.repaint();
                parent.panel.validate();
                parent.refreshWindowLayout();
            }
            refresh();
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metoda vytvari a zobrazuje novy formular pro novou objednavku.
     *
     * @param accountId Id uctu, na ktery se bude objednavat nova polozka
     *
     */
    protected void createOrder(int accountId) {
        try {
            createOrderForm = new CreateOrderForm(parent, statusBar, accountId, MainFrame.loggedUser.getUserId());
            parent.panel.getViewport().add(createOrderForm);
            parent.panel.repaint();
            parent.panel.validate();
            parent.refreshWindowLayout();
            refresh();
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metoda vytvari a zobrazuje novy formular pro presouvani polozek.
     *
     * @param accountId Id uctu, ze ktereho se bude presouvat
     *
     */
    protected void moveOrder(int accountId) {
        try {
            moveOrderForm = new MoveOrderForm(parent, statusBar, accountId);
            parent.panel.getViewport().add(moveOrderForm);
            parent.panel.repaint();
            parent.panel.validate();
            parent.refreshWindowLayout();
            refresh();
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metoda inicializuje pole tlacitek jButton (1 pro kazdy ucet) pro vyber uctu.
     *
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException
     *
     */
    public void initButtonGroup() throws FileNotFoundException, NotBoundException, RemoteException {
        Object[][] accounts = ServiceFacade.getInstance().getAccountsByAccountStatusType(1); // AccountStatusType 1 = ucet neuzavren
        if (accounts == null) {
            JOptionPane.showMessageDialog(this, "Momentálně nejsou evidovány žádné neuzavřené účty.", "Žádné účty", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JButton[] buttonArray = new JButton[accounts.length];
        jPanel.removeAll();
        jPanel.validate();
        int pocetSloupcu = 8;
        int j = 0;
        for (int i = 0; i < accounts.length; i++) {
            buttonArray[i] = new JButton();
            buttonArray[i].setText(accounts[i][1].toString());
            buttonArray[i].setVisible(true);
            final int accountId = Integer.parseInt(accounts[i][0].toString());
            buttonArray[i].addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    switch (action) {
                        case CREATE_ORDER:
                            createOrder(accountId);
                            break;
                        case PAY_ORDER:
                            payOrder(accountId);
                            break;
                        case MOVE_ORDER:
                            moveOrder(accountId);
                            break;
                        default:
                            break;
                    }
                }
            });
            jPanel.add(buttonArray[i]);
            jPanel.validate();
            buttonArray[i].setBounds((i % pocetSloupcu) * 150, j * 95, 140, 80);
            buttonArray[i].setIcon(icon);
            buttonArray[i].setHorizontalAlignment(SwingConstants.LEFT);
            if (i % pocetSloupcu == pocetSloupcu - 1) {
                j++;
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();

        jPanel.setToolTipText("");
        jPanel.setOpaque(false);

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 677, Short.MAX_VALUE)
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 433, Short.MAX_VALUE)
        );

        jLabelTitle.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setFont(new java.awt.Font("Tahoma", 0, 18));
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("jLabel1");
        jLabelTitle.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel;
    // End of variables declaration//GEN-END:variables
}
