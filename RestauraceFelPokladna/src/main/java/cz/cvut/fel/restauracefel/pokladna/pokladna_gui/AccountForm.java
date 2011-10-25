package cz.cvut.fel.restauracefel.pokladna.pokladna_gui;

import cz.cvut.fel.restauracefel.hibernate.Account;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.pokladna_service.ServiceFacade;

/**
 * Trida reprezentujici GUI formular zobrazujici vybrany ucet.
 *
 * @author Tomas Hnizdil
 */
public class AccountForm extends AbstractForm {

    private Account account;
    private MainFrame parent;
    private ConfigParser config = null;
    private StatusBar statusBar = null;

    /**
     * Konstruktor tridy AccountForm.
     *
     * @param parent
     * @param bar
     * @param accountId
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public AccountForm(MainFrame parent, StatusBar bar, int accountId) throws FileNotFoundException, NotBoundException, RemoteException {
        this.parent = parent;
        this.statusBar = bar;
        this.account = ServiceFacade.getInstance().getAccountById(accountId);
        config = new ConfigParser();
        initComponents();
        jLabelTitle.setText("Účet \"" + account.getName() + "\"");
        refresh();
    }

    /**
     * Metoda provadi inicializaci uctu.
     *
     * @param unpaidOnly priznak, zda se jedna o ucet se vsemi polozkami, nebo ucet s nezaplacenymi polozkami
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    protected void initBill(boolean unpaidOnly) throws FileNotFoundException, NotBoundException, RemoteException {
        Object[][] orders = null;
        JScrollPane jScrollPane;
        JLabel jLabelSleva, jLabelSuma;

        JPanel jPanelObjednavky = new JPanel();
        jPanelObjednavky.setOpaque(false);
        jPanelObjednavky.setLayout(new GroupLayout(jPanelObjednavky));

        if (unpaidOnly) {
            orders = ServiceFacade.getInstance().getMenuItemsByAccount(account.getAccountId());
            jScrollPane = jScrollPaneUnpaidOrders;
            jLabelSleva = jLabelDiscountType1;
            jLabelSuma = jLabelUnpaidSum;
        } else {
            orders = ServiceFacade.getInstance().getAllMenuItemsByAccount(account.getAccountId());
            jScrollPane = jScrollPaneOrders;
            jLabelSleva = jLabelDiscountType;
            jLabelSuma = jLabelSum;
        }

        double sum = 0;
        int i = 0;
        if (account.getDiscountType() != null) {
            jLabelSleva.setText("Sleva: " + account.getDiscountType().getName());
        } else jLabelSleva.setVisible(false);

        if (orders != null) {
            for (i = 0; i < orders.length; i++) {
                double singleValue = (Double) orders[i][4] * (Double) orders[i][3] - (Double) orders[i][5];
                double value = Integer.valueOf(orders[i][6].toString()) * singleValue;
                sum += value;

                JLabel pocet = new JLabel(orders[i][6].toString() + "x ");
                JLabel polozka = new JLabel(orders[i][2].toString());
                JLabel cena = new JLabel(singleValue + ",- ");

                pocet.setBounds(0, i * 30 + 5, 20, 30);
                polozka.setBounds(25, i * 30 + 5, 130, 30);
                cena.setBounds(160, i * 30 + 5, 40, 30);
                cena.setAlignmentX(RIGHT_ALIGNMENT);

                jPanelObjednavky.add(pocet);
                jPanelObjednavky.add(polozka);
                jPanelObjednavky.add(cena);
            }
        }
        jLabelSuma.setText("  Celkem: " + sum + ",- " + config.getMoney());

        jScrollPane.getViewport().removeAll();
        jScrollPane.getViewport().add(jPanelObjednavky);

        jPanelObjednavky.setSize(225, i * 30 + 10);
        jPanelObjednavky.setPreferredSize(new Dimension(225, i * 30 + 10));
        jPanelObjednavky.revalidate();
        jPanelObjednavky.repaint();
    }

    /**
     * Metoda provadi aktualizaci obou uctu, nastavuje vlastnost enabled u tlacitek a prenastavuje statusBar.
     */
    @Override
    protected void refresh() {
        try {
            initBill(true);
            initBill(false);
            if (account.getAccountStatusType().getName().equals("uzavřen")) {
                jButtonOrder.setEnabled(false);
                jButtonMove.setEnabled(false);
                jButtonPay.setEnabled(false);
            }
            statusBar.setMessage("Tento formulář slouží k zobrazení vybraného účtu.");
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
        jPanel1 = new javax.swing.JPanel();
        jPanelBill = new BackgroundPanel();
        jLabelSum = new javax.swing.JLabel();
        jLabelAccountTitle = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPaneOrders = new javax.swing.JScrollPane();
        jLabelDiscountType = new javax.swing.JLabel();
        jPanelBill1 = new BackgroundPanel();
        jLabelUnpaidSum = new javax.swing.JLabel();
        jLabelAccountTitle1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPaneUnpaidOrders = new javax.swing.JScrollPane();
        jLabelDiscountType1 = new javax.swing.JLabel();
        jButtonBack = new javax.swing.JButton();
        jButtonOrder = new javax.swing.JButton();
        jButtonMove = new javax.swing.JButton();
        jButtonPay = new javax.swing.JButton();

        jLabelTitle.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setFont(new java.awt.Font("Tahoma", 0, 18));
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("Účet");
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

        jLabelUnpaidSum.setText("   CELKEM:");
        jLabelUnpaidSum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelAccountTitle1.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabelAccountTitle1.setText("Nezaplacené položky");

        jLabel12.setFont(new java.awt.Font("Tahoma", 2, 14));

        jScrollPaneUnpaidOrders.setBorder(null);
        jScrollPaneUnpaidOrders.setOpaque(false);

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
                            .addComponent(jLabelUnpaidSum, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(jPanelBill1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelBill1Layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(jScrollPaneUnpaidOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jLabelUnpaidSum, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
            .addGroup(jPanelBill1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelBill1Layout.createSequentialGroup()
                    .addGap(113, 113, 113)
                    .addComponent(jScrollPaneUnpaidOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(141, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelBill, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelBill1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jButtonBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/ko.png"))); // NOI18N
        jButtonBack.setText(" Zavřít");
        jButtonBack.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jButtonOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/right.png"))); // NOI18N
        jButtonOrder.setText(" Objednat");
        jButtonOrder.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOrderActionPerformed(evt);
            }
        });

        jButtonMove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/move.png"))); // NOI18N
        jButtonMove.setText(" Přesunout");
        jButtonMove.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMoveActionPerformed(evt);
            }
        });

        jButtonPay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/money.png"))); // NOI18N
        jButtonPay.setText(" Zaplatit");
        jButtonPay.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 966, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(836, Short.MAX_VALUE)
                .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(836, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonOrder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonMove, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(836, Short.MAX_VALUE)
                .addComponent(jButtonPay, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 771, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(185, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonMove, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonPay, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(186, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(42, 42, 42)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        try {
            parent.accountListForm = new AccountListForm(parent, statusBar);
            parent.panel.getViewport().add(parent.accountListForm);
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
        try {
            CreateOrderForm createOrderForm = new CreateOrderForm(parent, statusBar, account.getAccountId(), parent.getLoggedUser().getUserId());
            parent.panel.getViewport().add(createOrderForm);
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
    }//GEN-LAST:event_jButtonOrderActionPerformed

    private void jButtonMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMoveActionPerformed
        try {
            MoveOrderForm moveOrderForm = new MoveOrderForm(parent, statusBar, account.getAccountId());
            parent.panel.getViewport().add(moveOrderForm);
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
    }//GEN-LAST:event_jButtonMoveActionPerformed

    private void jButtonPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPayActionPerformed
        try {
            PayOrderForm payOrderForm = new PayOrderForm(parent, statusBar, account.getAccountId());
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
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonPayActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonMove;
    private javax.swing.JButton jButtonOrder;
    private javax.swing.JButton jButtonPay;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabelAccountTitle;
    private javax.swing.JLabel jLabelAccountTitle1;
    private javax.swing.JLabel jLabelDiscountType;
    private javax.swing.JLabel jLabelDiscountType1;
    private javax.swing.JLabel jLabelSum;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelUnpaidSum;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelBill;
    private javax.swing.JPanel jPanelBill1;
    private javax.swing.JScrollPane jScrollPaneOrders;
    private javax.swing.JScrollPane jScrollPaneUnpaidOrders;
    // End of variables declaration//GEN-END:variables
}
