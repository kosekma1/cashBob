package cz.cvut.fel.restauracefel.pokladna.pokladna_gui;

import cz.cvut.fel.restauracefel.hibernate.Account;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.pokladna_service.ServiceFacade;

/**
 * Trida reprezentujici GUI formular pro presun polozek mezi ucty.
 *
 * @author Tomas Hnizdil
 */
public class MoveOrderForm extends AbstractForm {

    private Account account;
    private MainFrame parent;
    private ConfigParser config = null;
    private Object[][] orders = null;
    private Map<Integer, Object[]> movedOrders = new HashMap<Integer, Object[]>();
    private StatusBar statusBar = null;

    /**
     * Konstruktor tridy MoveOrderForm
     *
     * @param parent instance tridy MainFrame jenz vytvorila tento formular
     * @param bar statusBar, do ktereho budou vypisovany popisky 
     * @param accountId id uctu, ze ktereho se bude presouvat
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public MoveOrderForm(MainFrame parent, StatusBar bar, int accountId) throws FileNotFoundException, NotBoundException, RemoteException {
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
        JPanel jPanelOrders = new JPanel();
        jPanelOrders.setOpaque(false);
        jPanelOrders.setLayout(new GroupLayout(jPanelOrders));
        
        double sum = 0;
        int i = 0;
        jLabelAccountTitle.setText(account.getName());
        if (account.getDiscountType() != null) {
            jLabelDiscountType.setText("Sleva: " + account.getDiscountType().getName());
        } else jLabelDiscountType.setVisible(false);
        
        if (orders != null) {
            for (i = 0; i < orders.length; i++) {
                double singleValue = (Double) orders[i][4] * (Double) orders[i][3] - (Double) orders[i][5];
                double value = Integer.valueOf(orders[i][6].toString()) * singleValue;
                sum += value;
                Integer cislo = Integer.valueOf(orders[i][6].toString());

                JLabel pocet = new JLabel(cislo + "x ");
                JLabel polozka = new JLabel(orders[i][2].toString());
                JLabel cena = new JLabel(singleValue + ",-");

                pocet.setBounds(0, i * 35 + 10, 20, 35);
                polozka.setBounds(25, i * 35 + 10, 115, 35);
                cena.setBounds(145, i * 35 + 10, 40, 35);
                cena.setAlignmentX(RIGHT_ALIGNMENT);

                final String name = orders[i][2].toString();
                final Integer id = (Integer) orders[i][0];
                final Double price = singleValue;

                if (movedOrders.get(id) == null || (Integer) movedOrders.get(id)[2] < cislo) {
                    polozka.addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            moveMenuItem(id, name, price);
                        }
                    });
                }

                JButton add = new JButton("+");
                add.setName(orders[i][0].toString());
                add.setFont(new Font("Verdana", Font.BOLD, 14));
                add.setFocusPainted(false);
                add.setBounds(190, i * 35 + 10, 35, 32);

                add.addActionListener(new java.awt.event.ActionListener() {

                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        moveMenuItem(id, name, price);
                    }
                });

                jPanelOrders.add(pocet);
                jPanelOrders.add(polozka);
                jPanelOrders.add(cena);
                if (movedOrders.get(id) == null || (Integer) movedOrders.get(id)[2] < cislo) {
                    jPanelOrders.add(add);
                }
            }
        }
        jLabelSum.setText("Celkem: " + sum + ",- " + config.getMoney());

        jScrollPaneOrders.getViewport().removeAll();
        jScrollPaneOrders.getViewport().add(jPanelOrders);

        jPanelOrders.setSize(225, i*35+15);
        jPanelOrders.setPreferredSize(new Dimension(225, i*35+15));
        jPanelOrders.revalidate();
        jPanelOrders.repaint();
    }

    /**
     * Metoda provadi aktualizaci seznamu presouvanych polozek zobrazeneho vedle uctu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    protected void refreshMovedBill() throws FileNotFoundException, NotBoundException, RemoteException {
        JPanel jPanelMovedOrders = new JPanel();
        jPanelMovedOrders.setOpaque(false);
        jPanelMovedOrders.setLayout(new GroupLayout(jPanelMovedOrders));

        int i = 0;
        double sum = 0;
        Iterator it = movedOrders.keySet().iterator();
        while (it.hasNext()) {
            final Integer menuItemId = (Integer) it.next();
            Object[] order = movedOrders.get(menuItemId);
            double value = (Integer) order[2] * (Double) order[1];
            sum += value;

            JLabel pocet = new JLabel(order[2].toString() + "x ");
            JLabel polozka = new JLabel(order[0].toString());
            JLabel cena = new JLabel(order[1].toString() + ",- ");

            pocet.setBounds(0, i * 35 + 10, 20, 35);
            polozka.setBounds(25, i * 35 + 10, 115, 35);
            cena.setBounds(145, i * 35 + 10, 40, 35);
            cena.setAlignmentX(RIGHT_ALIGNMENT);

            polozka.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if ((Integer) (movedOrders.get(menuItemId)[2]) <= 1) {
                        movedOrders.remove(menuItemId);

                    } else {
                        movedOrders.get(menuItemId)[2] = (Integer) movedOrders.get(menuItemId)[2] - 1;

                    }
                    refresh();
                }
            });

            JButton add = new JButton("-");
            add.setName(menuItemId.toString());
            add.setFont(new Font("Verdana", Font.BOLD, 14));
            add.setFocusPainted(false);
            add.setBounds(190, i * 35 + 10, 35, 32);
            add.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    Integer id = Integer.parseInt(((JButton) evt.getSource()).getName());
                    if ((Integer) (movedOrders.get(id)[2]) <= 1) {
                        movedOrders.remove(id);

                    } else {
                        movedOrders.get(id)[2] = (Integer) movedOrders.get(id)[2] - 1;

                    }
                    refresh();
                }
            });

            jPanelMovedOrders.add(pocet);
            jPanelMovedOrders.add(polozka);
            jPanelMovedOrders.add(cena);
            jPanelMovedOrders.add(add);
            i++;
        }
        jLabelMovedSum.setText("Celkem: " + sum + ",- " + config.getMoney());

        jScrollPaneMovedOrders.getViewport().removeAll();
        jScrollPaneMovedOrders.getViewport().add(jPanelMovedOrders);

        jPanelMovedOrders.setSize(225, i*35+15);
        jPanelMovedOrders.setPreferredSize(new Dimension(225, i*35+15));
        jPanelMovedOrders.revalidate();
        jPanelMovedOrders.repaint();
    }

    /**
     * Metoda vklada id, nazev, cenu a pocet presouvanych polozek do mapy movedOrders.
     *
     * @param menuItemId id presouvane polozky
     * @param name nazev presouvane polozky
     * @param price cena presouvane polozky
     */
    protected void moveMenuItem(int menuItemId, String name, double price) {
        if (movedOrders.containsKey(menuItemId)) {
            movedOrders.get(menuItemId)[2] = (Integer) movedOrders.get(menuItemId)[2] + 1;
        } else {
            Object[] o = {name, price, 1};
            movedOrders.put(menuItemId, o);
        }
        refresh();
    }

    /**
     * Metoda aktualizuje seznam dostupnych cilovych uctu.
     *
     * @throws java.io.FileNotFoundException
     * @throws java.rmi.NotBoundException
     * @throws java.rmi.RemoteException
     */
    private void refreshList() throws FileNotFoundException, NotBoundException, RemoteException {
        Object[][] accounts = ServiceFacade.getInstance().getAccountsByAccountStatusType(1);
        String[] accountNames = new String[accounts.length];
        for (int i = 0; i <accountNames.length; i++) {
            accountNames[i] = accounts[i][1].toString();
        }
        jListAccounts.setListData(accountNames);
    }

    /**
     * Metoda provadi aktualizaci uctu, seznamu presouvanych polozek, prenastavuje statusBar a
     * prekresluje cely formular.
     */
    @Override
    protected void refresh() {
        try {
            orders = ServiceFacade.getInstance().getMenuItemsByAccount(account.getAccountId());
            refreshList();
            refreshBill();
            refreshMovedBill();
            statusBar.setMessage("Tento formulář slouží k přesouvání položek mezi účty.");
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected int isValidInputComplex() {
        if (orders == null) {
            return 1;
        }
        if (movedOrders == null || movedOrders.isEmpty()) {
            return 1;
        }
        if (jListAccounts.getSelectedIndex() == -1) {
            return 3;
        }
        return 0;
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
        jButtonSelectAll = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();
        jButtonMove = new javax.swing.JButton();
        jPanelBill = new BackgroundPanel();
        jLabelSum = new javax.swing.JLabel();
        jLabelAccountTitle = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPaneOrders = new javax.swing.JScrollPane();
        jLabelDiscountType = new javax.swing.JLabel();
        jPanelBill1 = new BackgroundPanel();
        jLabelMovedSum = new javax.swing.JLabel();
        jLabelAccountTitle1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPaneMovedOrders = new javax.swing.JScrollPane();
        jPanel6 = new BackgroundPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jListAccounts = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jLabelTitle.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setFont(new java.awt.Font("Tahoma", 0, 18));
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("Přesunout položky z účtu");
        jLabelTitle.setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1388, 567));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 995, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );

        jButtonSelectAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left.png"))); // NOI18N
        jButtonSelectAll.setText(" Vybrat vše");
        jButtonSelectAll.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectAllActionPerformed(evt);
            }
        });

        jButtonClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonClear.setText(" Reset");
        jButtonClear.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        jButtonBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/ko.png"))); // NOI18N
        jButtonBack.setText(" Zavřít");
        jButtonBack.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jButtonMove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/ok.png"))); // NOI18N
        jButtonMove.setText(" Přesunout");
        jButtonMove.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMoveActionPerformed(evt);
            }
        });

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

        jLabelMovedSum.setText("   CELKEM:");
        jLabelMovedSum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelAccountTitle1.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabelAccountTitle1.setText("PŘESOUVANÉ POLOŽKY");

        jLabel12.setFont(new java.awt.Font("Tahoma", 2, 14));

        jScrollPaneMovedOrders.setBorder(null);
        jScrollPaneMovedOrders.setOpaque(false);

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
                        .addComponent(jLabelMovedSum, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(jPanelBill1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelBill1Layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(jScrollPaneMovedOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(26, Short.MAX_VALUE)))
        );
        jPanelBill1Layout.setVerticalGroup(
            jPanelBill1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBill1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jLabelAccountTitle1)
                .addGap(349, 349, 349)
                .addComponent(jLabelMovedSum, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
            .addGroup(jPanelBill1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelBill1Layout.createSequentialGroup()
                    .addGap(113, 113, 113)
                    .addComponent(jScrollPaneMovedOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(119, Short.MAX_VALUE)))
        );

        jPanel6.setOpaque(false);

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane5.setPreferredSize(new java.awt.Dimension(19, 0));

        jListAccounts.setFont(new java.awt.Font("Tahoma", 1, 18));
        jListAccounts.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "1", "2" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListAccounts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListAccounts.setAlignmentX(2.0F);
        jListAccounts.setFixedCellHeight(50);
        jListAccounts.setFixedCellWidth(200);
        jListAccounts.setFocusCycleRoot(true);
        jListAccounts.setSelectedIndex(0);
        jListAccounts.setSelectionBackground(javax.swing.UIManager.getDefaults().getColor("InternalFrame.inactiveTitleGradient"));
        jListAccounts.setVisibleRowCount(-1);
        jScrollPane5.setViewportView(jListAccounts);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel3.setText("Cílový účet");

        jLabel4.setText("... na který se položky přesunou.");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addContainerGap())
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 198, Short.MAX_VALUE)
                            .addGap(93, 93, 93)))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1149, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanelBill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelBill1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonMove, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonBack, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonSelectAll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(144, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jButtonSelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonMove, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelBill, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanelBill1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(42, 42, 42)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectAllActionPerformed
        movedOrders.clear();
        for (int i = 0; i < orders.length; i++) {
            double price = (Double) orders[i][4] * (Double) orders[i][3] - (Double) orders[i][5];
            Object[] o = {orders[i][2].toString(), price, Integer.valueOf(orders[i][6].toString())};
            movedOrders.put((Integer) orders[i][0], o);
        }
        refresh();
}//GEN-LAST:event_jButtonSelectAllActionPerformed

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        movedOrders.clear();
        refresh();
    }//GEN-LAST:event_jButtonClearActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        try {
            parent.chooseAccountForm = new ChooseAccountForm(parent, statusBar, MainFrame.loggedUser.getUserId(), EnumOrder.MOVE_ORDER);
            parent.panel.getViewport().add(parent.chooseAccountForm);
            parent.panel.repaint();
            parent.panel.validate();
            parent.refreshWindowLayout();
            refresh();
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jButtonMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMoveActionPerformed
        int i = isValidInputComplex();
        switch (i) {
            case 0:
                try {
                    int targetId = ServiceFacade.getInstance().getAccountByName(jListAccounts.getSelectedValue().toString()).getAccountId();
                    Iterator it = movedOrders.keySet().iterator();
                    while (it.hasNext()) {
                        Integer menuItemId = (Integer) it.next();
                        int pocet = (Integer) movedOrders.get(menuItemId)[2];

                        boolean isOK = ServiceFacade.getInstance().moveNMenutItemsByAccount(pocet, menuItemId, account.getAccountId(), targetId);
                        if (!isOK) {
                            JOptionPane.showMessageDialog(this, "Položka menu nemohla být přesunuta.", "Přesun položek", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    movedOrders.clear();
                    refresh();
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case 1:
                JOptionPane.showMessageDialog(this, "Na účtu nejsou žádné položky k přesunutí.", "Přesouvání položek", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(this, "Nejsou vybrány žádné položky.", "Přesouvání položek", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 3:
                JOptionPane.showMessageDialog(this, "Musí být vybrán cílový účet.", "Přesouvání položek", JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButtonMoveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonMove;
    private javax.swing.JButton jButtonSelectAll;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelAccountTitle;
    private javax.swing.JLabel jLabelAccountTitle1;
    private javax.swing.JLabel jLabelDiscountType;
    private javax.swing.JLabel jLabelMovedSum;
    private javax.swing.JLabel jLabelSum;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JList jListAccounts;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelBill;
    private javax.swing.JPanel jPanelBill1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPaneMovedOrders;
    private javax.swing.JScrollPane jScrollPaneOrders;
    // End of variables declaration//GEN-END:variables
}
