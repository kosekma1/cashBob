package cz.cvut.fel.restauracefel.pokladna.pokladna_gui;

import cz.cvut.fel.restauracefel.hibernate.Account;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.pokladna_service.ServiceFacade;

/**
 * Trida reprezentujici GUI formular pro vytvareni objednavek.
 *
 * @author Tomas Hnizdil
 */
public class CreateOrderForm extends AbstractForm {

    private Account account;
    private MainFrame parent;
    private ConfigParser config = null;
    private JButton[] jButtons = null;
    private Object[][] orders = null;
    private Map<Integer, Object[]> newOrders = new HashMap<Integer, Object[]>();
    private int loggedUserId;
    private StatusBar statusBar = null;
    private int okno = 0; // okno zobrazovanych menu

    /**
     * Konstruktor tridy CreateOrderDialog.
     *
     * @param parent
     * @param modal
     * @param accountId id uctu, na ktery se bude objednavat
     * @param loggedUserId id prihlaseneho uzivatele
     * @throws java.rmi.RemoteException 
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public CreateOrderForm(MainFrame parent, StatusBar bar, int accountId, int loggedUserId) throws FileNotFoundException, NotBoundException, RemoteException {
        this.parent = parent;
        this.statusBar = bar;
        this.loggedUserId = loggedUserId;
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

                JLabel pocet = new JLabel(orders[i][6].toString() + "x ");
                JLabel polozka = new JLabel(orders[i][2].toString());
                JLabel cena = new JLabel(singleValue + ",- ");

                pocet.setBounds(0, i * 35 + 10, 20, 35);
                polozka.setBounds(25, i * 35 + 10, 115, 35);
                cena.setBounds(145, i * 35 + 10, 40, 35);
                cena.setAlignmentX(RIGHT_ALIGNMENT);

                final String name = orders[i][2].toString();
                final Integer id = (Integer) orders[i][0];

                polozka.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        orderMenuItem(id, name);
                    }
                });

                JButton add = new JButton();
                add.setText("+");
                add.setName(orders[i][0].toString());
                add.setFont(new Font("Verdana", Font.BOLD, 14));
                add.setFocusPainted(false);
                add.setBounds(190, i * 35 + 10, 35, 32);

                add.addActionListener(new java.awt.event.ActionListener() {

                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        orderMenuItem(id, name);
                    }
                });

                jPanelOrders.add(pocet);
                jPanelOrders.add(polozka);
                jPanelOrders.add(cena);
                jPanelOrders.add(add);
            }
        }
        jLabelSum.setText("  Celkem: " + sum + ",- " + config.getMoney());

        jScrollPaneBill.getViewport().removeAll();
        jScrollPaneBill.getViewport().add(jPanelOrders);

        jPanelOrders.setSize(225, i * 35 + 15);
        jPanelOrders.setPreferredSize(new Dimension(225, i * 35 + 15));
        jPanelOrders.revalidate();
        jPanelOrders.repaint();
    }

    /**
     * Metoda provadi aktualizaci nove objednavky zobrazene vedle uctu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    private void refreshOrder() throws FileNotFoundException, NotBoundException, RemoteException {
        JPanel jPanelNewOrders = new JPanel();
        jPanelNewOrders.setOpaque(false);
        jPanelNewOrders.setLayout(new GroupLayout(jPanelNewOrders));

        int i = 0;
        Iterator it = newOrders.keySet().iterator();
        while (it.hasNext()) {
            final Integer menuItemId = (Integer) it.next();
            Object[] order = newOrders.get(menuItemId);
            JLabel pocet = new JLabel(order[1].toString() + "x ");
            JLabel polozka = new JLabel(order[0].toString());
            pocet.setBounds(0, i * 35 + 10, 20, 30);
            polozka.setBounds(25, i * 35 + 10, 160, 30);

            polozka.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if ((Integer) (newOrders.get(menuItemId)[1]) <= 1) {
                        newOrders.remove(menuItemId);

                    } else {
                        newOrders.get(menuItemId)[1] = (Integer) newOrders.get(menuItemId)[1] - 1;

                    }
                    refresh();
                }
            });

            JButton add = new JButton();
            add.setText("-");
            add.setName(menuItemId.toString());
            add.setFont(new Font("Verdana", Font.BOLD, 14));
            add.setFocusPainted(false);
            add.setBounds(190, i * 35 + 10, 35, 32);
            add.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    Integer id = Integer.parseInt(((JButton) evt.getSource()).getName());
                    if ((Integer) (newOrders.get(id)[1]) <= 1) {
                        newOrders.remove(id);

                    } else {
                        newOrders.get(id)[1] = (Integer) newOrders.get(id)[1] - 1;

                    }
                    refresh();
                }
            });

            jPanelNewOrders.add(pocet);
            jPanelNewOrders.add(polozka);
            jPanelNewOrders.add(add);
            i++;
        }

        jScrollPaneNewOrders.getViewport().removeAll();
        jScrollPaneNewOrders.getViewport().add(jPanelNewOrders);

        jPanelNewOrders.setSize(225, i * 35 + 15);
        jPanelNewOrders.setPreferredSize(new Dimension(225, i * 35 + 15));
        jPanelNewOrders.revalidate();
        jPanelNewOrders.repaint();
    }

    /**
     * Metoda vklada id, nazev a pocet nove objednanych polozek do mapy newOrders.
     *
     * @param menuItemId id objednane polozky
     * @param name nazev objednane polozky
     */
    protected void orderMenuItem(Integer menuItemId, String name) {
        if (newOrders.containsKey(menuItemId)) {
            newOrders.get(menuItemId)[1] = (Integer) newOrders.get(menuItemId)[1] + 1;
        } else {
            Object[] o = {name, 1};
            newOrders.put(menuItemId, o);
        }
        refresh();
    }

    /**
     * Metoda inicializuje panel tlacitek s jednotlivymi menu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    protected void initMenuButtonGroup() throws FileNotFoundException, NotBoundException, RemoteException {
        jPanelMenus.removeAll();
        final Object[][] menus = ServiceFacade.getInstance().getMenus();

        if (menus==null) {
            initButtonGroup(0);
            return;
        }

        for (int i = 0; i < 3; i++) {
            final int j = okno + i;
            if (j>=menus.length) break;
            JButton menu = new JButton(menus[j][1].toString());
            menu.setBounds(i * 105, 5, 100, 50);
            menu.setFocusPainted(false);
            menu.setFont(new Font("Verdana", Font.BOLD, 14));
            menu.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    initButtonGroup((Integer) menus[j][0]);
                }
            });
            jPanelMenus.add(menu);
        }

        if (menus.length > 3) {
            JButton back = new JButton();
            back.setBounds(315, 5, 40, 50);
            back.setIcon(new ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left.png")));
            back.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    okno--;
                    refresh();
                }
            });
            if (okno<=0) back.setVisible(false);
            JButton forward = new JButton();
            forward.setBounds(360, 5, 40, 50);
            forward.setIcon(new ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/right.png")));
            forward.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    okno++;
                    refresh();
                }
            });
            if (okno>=menus.length-3) forward.setVisible(false);
            jPanelMenus.add(back);
            jPanelMenus.add(forward);
        }

    }

    /**
     * Metoda vytvari skupinu tlacitek (pro kazdou polozku menu 1 JButton) pro objednavani.
     *
     */
    protected void initButtonGroup(int menuId) {
        Font btnFont = new Font("Verdana", Font.PLAIN, 14);

        jPanel.removeAll();
        Object[][] menuItems = null;
        try {
            if (menuId==0) menuItems = ServiceFacade.getInstance().getMenuItems();
            else menuItems = ServiceFacade.getInstance().getMenuItemsByMenu(menuId);
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }

        if (menuItems != null) {
            jButtons = new JButton[menuItems.length];
            jPanel.setLayout(new GridLayout(6, 5));
            for (int i = 0; i < menuItems.length; i++) {
                jButtons[i] = new JButton();
                jButtons[i].setText(menuItems[i][2].toString());
                jButtons[i].setFont(btnFont);
                jButtons[i].setFocusPainted(false);

                final int id = (Integer) menuItems[i][0];
                final String name = menuItems[i][2].toString();
                jButtons[i].addActionListener(new java.awt.event.ActionListener() {

                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        orderMenuItem(id, name);
                    }
                });
                jPanel.add(jButtons[i]);
            }
        }

        this.validate();
        this.repaint();
    }

    /**
     * Metoda provadi aktualizaci uctu, objednavky, prenastavuje statusBar a
     * prekresluje cely formular.
     */
    @Override
    protected void refresh() {
        try {
            orders = ServiceFacade.getInstance().getMenuItemsByAccount(account.getAccountId());
            initMenuButtonGroup();
            refreshBill();
            refreshOrder();
            statusBar.setMessage("Tento formulář slouží k vytváření nových objednávek.");
            this.validate();
            this.repaint();
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
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
        jButtonClear = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();
        jButtonOrder = new javax.swing.JButton();
        jPanelBill1 = new BackgroundPanel();
        jLabelAccountTitle1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPaneNewOrders = new javax.swing.JScrollPane();
        jPanelBill = new BackgroundPanel();
        jLabelSum = new javax.swing.JLabel();
        jLabelAccountTitle = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPaneBill = new javax.swing.JScrollPane();
        jLabelDiscountType = new javax.swing.JLabel();
        jPanel = new javax.swing.JPanel();
        jPanelMenus = new javax.swing.JPanel();

        jLabelTitle.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setFont(new java.awt.Font("Tahoma", 0, 18));
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("Vytvořit novou objednávku");
        jLabelTitle.setOpaque(true);

        jButtonClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonClear.setText(" Vymazat");
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

        jButtonOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/ok.png"))); // NOI18N
        jButtonOrder.setText(" Objednat");
        jButtonOrder.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOrderActionPerformed(evt);
            }
        });

        jPanelBill1.setOpaque(false);
        jPanelBill1.setPreferredSize(new java.awt.Dimension(301, 470));

        jLabelAccountTitle1.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabelAccountTitle1.setText("OBJEDNÁVKA");

        jLabel12.setFont(new java.awt.Font("Tahoma", 2, 14));

        jScrollPaneNewOrders.setBorder(null);
        jScrollPaneNewOrders.setOpaque(false);

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
                        .addComponent(jLabelAccountTitle1)))
                .addContainerGap(69, Short.MAX_VALUE))
            .addGroup(jPanelBill1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelBill1Layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(jScrollPaneNewOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(26, Short.MAX_VALUE)))
        );
        jPanelBill1Layout.setVerticalGroup(
            jPanelBill1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBill1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jLabelAccountTitle1)
                .addGap(431, 431, 431))
            .addGroup(jPanelBill1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelBill1Layout.createSequentialGroup()
                    .addGap(113, 113, 113)
                    .addComponent(jScrollPaneNewOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(119, Short.MAX_VALUE)))
        );

        jPanelBill.setOpaque(false);
        jPanelBill.setPreferredSize(new java.awt.Dimension(301, 470));

        jLabelSum.setText("   CELKEM:");
        jLabelSum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelAccountTitle.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabelAccountTitle.setText("Všechny položky");

        jLabel11.setFont(new java.awt.Font("Tahoma", 2, 14));

        jScrollPaneBill.setBorder(null);
        jScrollPaneBill.setOpaque(false);

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
                    .addComponent(jScrollPaneBill, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jScrollPaneBill, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(119, Short.MAX_VALUE)))
        );

        jPanel.setOpaque(false);

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );

        jPanelMenus.setOpaque(false);
        jPanelMenus.setPreferredSize(new java.awt.Dimension(410, 61));

        javax.swing.GroupLayout jPanelMenusLayout = new javax.swing.GroupLayout(jPanelMenus);
        jPanelMenus.setLayout(jPanelMenusLayout);
        jPanelMenusLayout.setHorizontalGroup(
            jPanelMenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
        );
        jPanelMenusLayout.setVerticalGroup(
            jPanelMenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 61, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1231, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanelBill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelBill1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelMenus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBack, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonOrder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jPanelBill1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanelBill, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanelMenus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        newOrders.clear();
        refresh();
}//GEN-LAST:event_jButtonClearActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        try {
            parent.chooseAccountForm = new ChooseAccountForm(parent, statusBar, MainFrame.loggedUser.getUserId(), EnumOrder.CREATE_ORDER);
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

    private void jButtonOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOrderActionPerformed
        boolean isOK;
        try {
            Iterator it = newOrders.keySet().iterator();
            while (it.hasNext()) {
                Integer menuItemId = (Integer) it.next();
                int pocet = (Integer) newOrders.get(menuItemId)[1];

                for (int i = 0; i < pocet; i++) {
                    isOK = ServiceFacade.getInstance().createOrder(0, new Date(), account.getAccountId(), loggedUserId);
                    if (!isOK) {
                        JOptionPane.showMessageDialog(this, "Objednávka nemohla být přijata.", "Objednávka", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    Object[][] orderList = ServiceFacade.getInstance().getOrdersByAccount(account.getAccountId());
                    isOK = ServiceFacade.getInstance().createOrderMenuItem(menuItemId, (Integer) orderList[orderList.length - 1][0]);
                    if (!isOK) {
                        JOptionPane.showMessageDialog(this, "Objednávka nemohla být přijata.", "Objednávka", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
            }
            newOrders.clear();
            refresh();
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonOrderActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonOrder;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabelAccountTitle;
    private javax.swing.JLabel jLabelAccountTitle1;
    private javax.swing.JLabel jLabelDiscountType;
    private javax.swing.JLabel jLabelSum;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel;
    private javax.swing.JPanel jPanelBill;
    private javax.swing.JPanel jPanelBill1;
    private javax.swing.JPanel jPanelMenus;
    private javax.swing.JScrollPane jScrollPaneBill;
    private javax.swing.JScrollPane jScrollPaneNewOrders;
    // End of variables declaration//GEN-END:variables
}
