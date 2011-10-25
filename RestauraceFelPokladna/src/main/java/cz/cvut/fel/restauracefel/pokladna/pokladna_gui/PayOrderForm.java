package cz.cvut.fel.restauracefel.pokladna.pokladna_gui;

import cz.cvut.fel.restauracefel.pokladna.PokladnaController.MenuItemObject;
import cz.cvut.fel.restauracefel.pokladna.PokladnaController.MenuItemsObjects;
import cz.cvut.fel.restauracefel.pokladna.PokladnaController.PokladnaController;
import cz.cvut.fel.restauracefel.pokladna.PokladnaInterfaces.InterfacePressed;
import cz.cvut.fel.restauracefel.hibernate.Account;
import cz.cvut.fel.restauracefel.library.library_gui.PrintDialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.pokladna_service.ResultTableModel;
import cz.cvut.fel.restauracefel.pokladna_service.ServiceFacade;

/**
 * Trida reprezentujici GUI formular pro placeni uctu.
 *
 * @author Tomas Hnizdil
 */
public class PayOrderForm extends AbstractForm implements InterfacePressed {

    private Account account;
    private MainFrame parent;
    private ConfigParser config = null;
    private Object[][] orders = null;
    private MenuItemsObjects paidOrders = new MenuItemsObjects();
    private StatusBar statusBar = null;
    public boolean closed = false;

    /**
     * Konstruktor tridy PayOrderForm
     *
     * @param parent instance tridy MainFrame jenz vytvorila tento formular
     * @param bar statusBar, do ktereho budou vypisovany popisky
     * @param accountId id uctu, ze ktereho se bude platit
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public PayOrderForm(MainFrame parent, StatusBar bar, int accountId) throws FileNotFoundException, RemoteException, NotBoundException {
        this.parent = parent;
        this.statusBar = bar;
        this.account = ServiceFacade.getInstance().getAccountById(accountId);
        config = new ConfigParser();
        initComponents();
        refresh();
    }

    /**
     * Metoda provadi aktualizaci uctu zobrazeneho v leve casti formulare.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    protected void refreshBill() throws FileNotFoundException, NotBoundException, RemoteException {
        List<MenuItemObject> menuItems = PokladnaController.menuItems(orders);

        jLabelAccountTitle.setText(account.getName());
        if (account.getDiscountType() != null) {
            jLabelDiscountType.setText("Sleva: " + account.getDiscountType().getName());
        } else {
            jLabelDiscountType.setVisible(false);
        }

        PokladnaViewController.getInstance().zobrazPolozky(menuItems, jScrollPaneOrders.getViewport(), this, EnumMenuItem.PRIDEJ, jLabelSum, config.getMoney(), paidOrders);
        closed = PokladnaController.getInstance().uzavritUcet(menuItems, account);
    }

    /**
     * Metoda provadi aktualizaci seznamu placenych polozek zobrazeneho vedle uctu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    protected void refreshPaidBill() throws FileNotFoundException, NotBoundException, RemoteException {
        if (account.getDiscountType() != null) {
            jLabelDiscountType1.setText("Sleva: " + account.getDiscountType().getName());
        } else {
            jLabelDiscountType1.setVisible(false);
        }

        PokladnaViewController.getInstance().zobrazPolozky(paidOrders.getSeznam(), jScrollPanePaidOrders.getViewport(), this, EnumMenuItem.ODEBER, jLabelPaidSum, config.getMoney(), null);
    }

    /**
     * Metoda provadi aktualizaci uctu, seznamu placenych objednavek, prenastavuje statusBar a
     * prekresluje cely formular.
     */
    @Override
    protected void refresh() {
        try {
            orders = ServiceFacade.getInstance().getMenuItemsByAccount(account.getAccountId());
            refreshBill();
            refreshPaidBill();
            if (account.getUser() == null) {
                jButtonPay1.setEnabled(false);
            }
            statusBar.setMessage("Tento formulář slouží k placení položek z účtu.");
            this.validate();
            this.repaint();
            if (closed) {
                ChooseAccountForm chooseAccountForm = new ChooseAccountForm(parent, statusBar, MainFrame.loggedUser.getUserId(), EnumOrder.PAY_ORDER);
                parent.panel.getViewport().add(chooseAccountForm);
                parent.panel.validate();
                parent.panel.repaint();
                parent.refreshWindowLayout();
            }
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected int isValidInputComplex() {
        if (orders == null) {
            return 1;
        }
        if (paidOrders == null || paidOrders.getSeznam().isEmpty()) {
            return 2;
        }
        return 0;
    }

    /**
     * Metoda inicializuje pole dat v potrebnem formatu pro tisk uctenky.
     *
     * @throws java.io.FileNotFoundException
     */
    private Object[][] initBill() throws FileNotFoundException {
        //Object[][] printData = new Object[10][3];
        Object[][] printData = new Object[paidOrders.getSeznam().size() + 1][3];
        double sum = 0;
        MenuItemObject item;
        int i = 0;
        for (i = 0; i < paidOrders.getSeznam().size(); i++) {
            item = paidOrders.getSeznam().get(i);
            printData[i][0] = (int) item.getCount();
            printData[i][1] = (String) item.getName();
            printData[i][2] = (double) item.getPrice();
            sum += item.getPrice();
        }
        printData[i][0] = (int) 0;
        printData[i][1] = "Celkem:";
        printData[i][2] = sum;


        return printData;
    }

    /**
     * Metoda tiskne uctenku.
     */
    private void print() {
        try {
            String template = "bill.jasper";
            String printingFilePath = config.getTemplatesDir() + System.getProperty("file.separator") + template;
            File printFile = new File(printingFilePath);

            if (!printFile.exists()) {
                JOptionPane.showMessageDialog(null, "Šablona \"" + template + "\" nebyla v adresáři:\n\n "
                        + config.getTemplatesDir() + "\n\n nelezena. Pokud se šablony nachází v jiném adresáři, "
                        + "nastavte cestu k tomuto adresáři v nastavení.", "Tisková sestava nenalezena", JOptionPane.ERROR_MESSAGE);
                return;
            }
            HashMap<String, Object> prop = new HashMap<String, Object>();
            SimpleDateFormat dateFormater = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
            prop.put("companyName", "Mlýnská kavárna");
            PrintDialog print = new PrintDialog(parent, true, prop, printFile);
            JTable table = new JTable();
            Object[][] printData = initBill();
            ResultTableModel model = new ResultTableModel();
            model.setColumnData(ResultTableModel.namesBill);
            model.setTableData(printData);
            table.setModel(model);
            print.setTable(table);
            print.setVisible(true);
            this.repaint();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
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

        jLabelTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanelBill = new BackgroundPanel();
        jLabelSum = new javax.swing.JLabel();
        jLabelAccountTitle = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPaneOrders = new javax.swing.JScrollPane();
        jLabelDiscountType = new javax.swing.JLabel();
        jPanelBill1 = new BackgroundPanel();
        jLabelPaidSum = new javax.swing.JLabel();
        jLabelAccountTitle1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPanePaidOrders = new javax.swing.JScrollPane();
        jLabelDiscountType1 = new javax.swing.JLabel();
        jButtonSelectAll = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();
        jButtonPay = new javax.swing.JButton();
        jButtonPay1 = new javax.swing.JButton();
        jButtonPrint = new javax.swing.JButton();

        jLabelTitle.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setFont(new java.awt.Font("Tahoma", 0, 18));
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("Zaplatit účet");
        jLabelTitle.setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1388, 567));

        jPanelBill.setOpaque(false);
        jPanelBill.setPreferredSize(new java.awt.Dimension(301, 470));

        jLabelSum.setText("   CELKEM:");
        jLabelSum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelAccountTitle.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabelAccountTitle.setText("Všechny položky");

        jLabel11.setFont(new java.awt.Font("Tahoma", 2, 14));

        jScrollPaneOrders.setBorder(null);
        jScrollPaneOrders.setOpaque(false);

        jLabelDiscountType.setFont(new java.awt.Font("Tahoma", 2, 14));
        jLabelDiscountType.setText("jLabel1");

        jLabel11.setLocation(200, 200);

        javax.swing.GroupLayout jPanelBillLayout = new javax.swing.GroupLayout(jPanelBill);
        jPanelBill.setLayout(jPanelBillLayout);
        jPanelBillLayout.setHorizontalGroup(
            jPanelBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBillLayout.createSequentialGroup()
                .addGroup(jPanelBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBillLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelBillLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabelAccountTitle))
                    .addGroup(jPanelBillLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanelBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDiscountType)
                            .addComponent(jLabelSum, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(jPanelBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelBillLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(jScrollPaneOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(26, Short.MAX_VALUE)))
        );
        jPanelBillLayout.setVerticalGroup(
            jPanelBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBillLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jLabelAccountTitle)
                .addGap(18, 18, 18)
                .addComponent(jLabelDiscountType)
                .addGap(314, 314, 314)
                .addComponent(jLabelSum, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
            .addGroup(jPanelBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelBillLayout.createSequentialGroup()
                    .addGap(113, 113, 113)
                    .addComponent(jScrollPaneOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(119, Short.MAX_VALUE)))
        );

        jPanelBill1.setOpaque(false);
        jPanelBill1.setPreferredSize(new java.awt.Dimension(301, 470));

        jLabelPaidSum.setText("   CELKEM:");
        jLabelPaidSum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelAccountTitle1.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabelAccountTitle1.setText("K PLACENÍ");

        jLabel12.setFont(new java.awt.Font("Tahoma", 2, 14));

        jScrollPanePaidOrders.setBorder(null);
        jScrollPanePaidOrders.setOpaque(false);

        jLabelDiscountType1.setFont(new java.awt.Font("Tahoma", 2, 14));
        jLabelDiscountType1.setText("jLabel1");

        jLabel11.setLocation(200, 200);

        javax.swing.GroupLayout jPanelBill1Layout = new javax.swing.GroupLayout(jPanelBill1);
        jPanelBill1.setLayout(jPanelBill1Layout);
        jPanelBill1Layout.setHorizontalGroup(
            jPanelBill1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBill1Layout.createSequentialGroup()
                .addGroup(jPanelBill1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBill1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelBill1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabelAccountTitle1))
                    .addGroup(jPanelBill1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanelBill1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDiscountType1)
                            .addComponent(jLabelPaidSum, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(jPanelBill1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelBill1Layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(jScrollPanePaidOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(26, Short.MAX_VALUE)))
        );
        jPanelBill1Layout.setVerticalGroup(
            jPanelBill1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBill1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jLabelAccountTitle1)
                .addGap(18, 18, 18)
                .addComponent(jLabelDiscountType1)
                .addGap(314, 314, 314)
                .addComponent(jLabelPaidSum, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
            .addGroup(jPanelBill1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelBill1Layout.createSequentialGroup()
                    .addGap(113, 113, 113)
                    .addComponent(jScrollPanePaidOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(119, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelBill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelBill1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(218, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelBill, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelBill1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonSelectAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left.png"))); // NOI18N
        jButtonSelectAll.setText("    Vybrat vše");
        jButtonSelectAll.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectAllActionPerformed(evt);
            }
        });

        jButtonClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonClear.setText("       Reset");
        jButtonClear.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        jButtonBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/ko.png"))); // NOI18N
        jButtonBack.setText("       Zavřít");
        jButtonBack.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jButtonPay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/ok.png"))); // NOI18N
        jButtonPay.setText("  Zaplatit hotově");
        jButtonPay.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPayActionPerformed(evt);
            }
        });

        jButtonPay1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/ok.png"))); // NOI18N
        jButtonPay1.setText(" Zaplatit z kreditu");
        jButtonPay1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonPay1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPay1ActionPerformed(evt);
            }
        });

        jButtonPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/printer.png"))); // NOI18N
        jButtonPrint.setText(" Vytisknout účet");
        jButtonPrint.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1063, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButtonPrint, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonPay1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonPay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonSelectAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jButtonSelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonPay, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonPay1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectAllActionPerformed
        List<MenuItemObject> menuItems = PokladnaController.menuItems(orders);
        paidOrders.clear();
        for (int i = 0; i < orders.length; i++) {
            int count = Integer.valueOf(orders[i][6].toString());
            for (int j = 0; j < count; j++) {
                paidOrders.add(menuItems.get(i));
            }
        }
        refresh();
}//GEN-LAST:event_jButtonSelectAllActionPerformed

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        paidOrders.clear();
        refresh();
    }//GEN-LAST:event_jButtonClearActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        try {
            parent.chooseAccountForm = new ChooseAccountForm(parent, statusBar, MainFrame.loggedUser.getUserId(), EnumOrder.PAY_ORDER);
            parent.panel.getViewport().add(parent.chooseAccountForm);
            parent.panel.repaint();
            parent.panel.validate();
            parent.refreshWindowLayout();
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jButtonPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPayActionPerformed
        int i = isValidInputComplex();
        switch (i) {
            case 0:
                try {
                    for (int j = 0; j < paidOrders.getSeznam().size(); j++) {
                        MenuItemObject menuItem = paidOrders.getSeznam().get(j);
                        Integer menuItemId = menuItem.getID();
                        int number = (int) paidOrders.findByID(menuItemId).getCount();
                        boolean isOK = ServiceFacade.getInstance().payNMenuItemsByAccount(number, menuItemId, account.getAccountId());
                        if (!isOK) {
                            JOptionPane.showMessageDialog(this, "Položka menu nemohla být zaplacena.", "Placení položky", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    int response = JOptionPane.showConfirmDialog(this, "Vybrané položky byly zaplaceny. Chcete vytisknout účtenku?", "Tisk účtenky", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == 0) {
                        print();
                    }
                    paidOrders.clear();
                    refresh();
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case 1:
                JOptionPane.showMessageDialog(this, "Na účtu nejsou žádné položky k zaplacení.", "Placení účtu", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(this, "Nejsou vybrány žádné položky k zaplacení.", "Placení účtu", JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButtonPayActionPerformed

    private void jButtonPay1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPay1ActionPerformed
        int i = isValidInputComplex();
        switch (i) {
            case 0:
                try {
                    double sum = 0;
                    sum = PokladnaController.menuItemsSum(paidOrders.getSeznam());
                    int userId = ServiceFacade.getInstance().getAccountById(account.getAccountId()).getUser().getUserId();
                    double credit = ServiceFacade.getInstance().getUserCredit(userId);
                    if (credit < sum) {
                        JOptionPane.showMessageDialog(this, "Zakaznikův kredit není dostatečný pro platbu.", "Placení položky", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (PokladnaController.payNMenuItemsByAccount(paidOrders.getSeznam(), account.getAccountId()) == false) {
                        return;
                    }
                    ServiceFacade.getInstance().updateUser(userId, -sum);
                    if (credit < 100) {
                        JOptionPane.showMessageDialog(this, "Zákazníkův kredit je nyní pouze " + credit + ",-" + config.getMoney() + ". Doporučujeme dobýt kredit.", "Placení položky", JOptionPane.INFORMATION_MESSAGE);
                    }
                    int response = JOptionPane.showConfirmDialog(this, "Vybrané položky byly zaplaceny. Chcete vytisknout účtenku?", "Tisk účtenky", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == 0) {
                        print();
                    }
                    paidOrders.clear();
                    refresh();
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case 1:
                JOptionPane.showMessageDialog(this, "Na účtu nejsou žádné položky k zaplacení.", "Placení účtu", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(this, "Nejsou vybrány žádné položky k zaplacení.", "Placení účtu", JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButtonPay1ActionPerformed

    private void jButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintActionPerformed
        if (paidOrders != null && !paidOrders.isEmpty()) {
            print();
        } else {
            JOptionPane.showMessageDialog(this, "Nejsou vybrány žádné položky k zaplacení.", "Placení účtu", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButtonPrintActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonPay;
    private javax.swing.JButton jButtonPay1;
    private javax.swing.JButton jButtonPrint;
    private javax.swing.JButton jButtonSelectAll;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabelAccountTitle;
    private javax.swing.JLabel jLabelAccountTitle1;
    private javax.swing.JLabel jLabelDiscountType;
    private javax.swing.JLabel jLabelDiscountType1;
    private javax.swing.JLabel jLabelPaidSum;
    private javax.swing.JLabel jLabelSum;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelBill;
    private javax.swing.JPanel jPanelBill1;
    private javax.swing.JScrollPane jScrollPaneOrders;
    private javax.swing.JScrollPane jScrollPanePaidOrders;
    // End of variables declaration//GEN-END:variables

    public void useMenuItem(MenuItemObject menuItem, EnumMenuItem akce) {
        if (akce == EnumMenuItem.PRIDEJ) {
            paidOrders.add(menuItem);
        }
        if (akce == EnumMenuItem.ODEBER) {
            paidOrders.remove(menuItem);
        }
        refresh();
    }
}
