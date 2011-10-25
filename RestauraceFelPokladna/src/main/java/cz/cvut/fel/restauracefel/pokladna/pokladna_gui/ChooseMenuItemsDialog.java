package cz.cvut.fel.restauracefel.pokladna.pokladna_gui;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.pokladna_service.ServiceFacade;

/**
 * Trida vytvarejici dialog pro vyber polozek menu, na ktere se bude vztahovat sleva.
 *
 * @author Tomas Hnizdil
 */
public class ChooseMenuItemsDialog extends AbstractDialog {

    // Odkaz na frame volajici tento dialog
    private AssignDiscountForm caller = null;
    private int okno = 0;
    private int[] selectedIndices = null;

    /**
     * Konstruktor tridy ChooseMenuItemsDialog
     *
     * @param parent instance tridy MainFrame jenz vytvorila tento formular
     * @param modal
     * @param caller AssignDiscountForm, ktery tento dialog zavolal a do ktereho se zapise zvoleny vysledek
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public ChooseMenuItemsDialog(MainFrame parent, boolean modal, AssignDiscountForm caller) throws RemoteException, NotBoundException, FileNotFoundException {
        super(parent, modal);
        super.setTitle("Přiřazení položek menu ke slevě");
        this.caller = caller;
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        refresh();
    }

    /**
     * Metoda vola inicializaci menu a prekresluje cely formular
     */
    @Override
    protected void refresh() {
        try {
            initMenuButtonGroup();
            this.validate();
            this.repaint();
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metoda provadi aktualizaci seznamu polozek podle vybraneho menu
     *
     * @param menuId id menu, z nehoz se maji vzbirat jednotlive polozky
     */
    protected void refresh(int menuId) {
        try {
            Object[][] menuItems;
            if (menuId==0) menuItems = ServiceFacade.getInstance().getMenuItems();
            else menuItems = ServiceFacade.getInstance().getMenuItemsByMenu(menuId);
            if (menuItems == null) {
                jList1.setListData(new String[0]);
                return;
            }
            String[] menuItemNames = new String[menuItems.length];
            for (int i = 0; i < menuItemNames.length; i++) {
                menuItemNames[i] = menuItems[i][2].toString();
            }
            jList1.setListData(menuItemNames);
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metoda provadi inicializaci panelu tlacitek s vyberem jednotlivych menu
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    protected void initMenuButtonGroup() throws FileNotFoundException, NotBoundException, RemoteException {
        jPanelMenus.removeAll();

        final Object[][] menus = ServiceFacade.getInstance().getMenus();

        if (menus == null) {
            refresh(0);
            return;
        }

        for (int i = 0; i < 4; i++) {
            final int j = okno + i;
            if (j>=menus.length) break;
            JButton menu = new JButton(menus[j][1].toString());
            menu.setBounds(i * 105, 5, 100, 50);
            menu.setFocusPainted(false);
            menu.setFont(new Font("Verdana", Font.BOLD, 14));
            menu.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    refresh((Integer) menus[j][0]);
                }
            });
            jPanelMenus.add(menu);
        }

        if (menus.length > 4) {
            JButton back = new JButton();
            back.setBounds(425, 5, 40, 50);
            back.setIcon(new ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left.png")));
            back.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    okno--;
                    refresh();
                }
            });
            if (okno<=0) back.setVisible(false);
            JButton forward = new JButton();
            forward.setBounds(470, 5, 40, 50);
            forward.setIcon(new ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/right.png")));
            forward.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    okno++;
                    refresh();
                }
            });
            if (okno>=menus.length-4) forward.setVisible(false);
            jPanelMenus.add(back);
            jPanelMenus.add(forward);
        }
        
        refresh((Integer) menus[0][0]);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButtonBack = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonOK = new javax.swing.JButton();
        jPanelMenus = new javax.swing.JPanel();
        jButtonDeselectAll = new javax.swing.JButton();
        jButtonSelectAll = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jList1.setFont(new java.awt.Font("Tahoma", 1, 18));
        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setAlignmentX(2.0F);
        jList1.setFixedCellHeight(50);
        jList1.setFixedCellWidth(200);
        jList1.setFocusCycleRoot(true);
        jList1.setSelectionBackground(javax.swing.UIManager.getDefaults().getColor("InternalFrame.inactiveTitleGradient"));
        jList1.setVisibleRowCount(-1);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jButtonBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/ko.png"))); // NOI18N
        jButtonBack.setText(" Zavřít");
        jButtonBack.setToolTipText("Vymazat pole stůl");
        jButtonBack.setBorder(null);
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel1.setText("Přiřadit položky menu");

        jLabel2.setText("Vyberte položky menu, na které se má sleva vztahovat");

        jButtonOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/ok.png"))); // NOI18N
        jButtonOK.setText(" OK");
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });

        jPanelMenus.setOpaque(false);
        jPanelMenus.setPreferredSize(new java.awt.Dimension(700, 58));

        javax.swing.GroupLayout jPanelMenusLayout = new javax.swing.GroupLayout(jPanelMenus);
        jPanelMenus.setLayout(jPanelMenusLayout);
        jPanelMenusLayout.setHorizontalGroup(
            jPanelMenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 510, Short.MAX_VALUE)
        );
        jPanelMenusLayout.setVerticalGroup(
            jPanelMenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 58, Short.MAX_VALUE)
        );

        jButtonDeselectAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/right.png"))); // NOI18N
        jButtonDeselectAll.setText(" Zrušit výběr");
        jButtonDeselectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeselectAllActionPerformed(evt);
            }
        });

        jButtonSelectAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left.png"))); // NOI18N
        jButtonSelectAll.setText(" Vybrat vše");
        jButtonSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelMenus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButtonSelectAll)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonDeselectAll)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonOK, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelMenus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonOK, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonDeselectAll, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                        .addComponent(jButtonSelectAll, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                    .addComponent(jButtonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonBackActionPerformed

    /**
     * Metoda pro hledani hodnoty v poli selectedIndices (pole indexu vybranych prvku v komponente jList)
     *
     * @param index hledana hodnota
     */
    private int contains(int index) {
        for (int i=0; i<selectedIndices.length; i++) {
            if (selectedIndices[i]==index) return i;
        }
        return -1;
    }

    private void clicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clicked
        if (selectedIndices == null) { // prvni vyber
            selectedIndices = jList1.getSelectedIndices();
            return;
        }
        int index = jList1.getSelectedIndex();
        int i = contains(index);
        int[] newIndices;
        if (i==-1) {
            newIndices = new int[selectedIndices.length+1];
            for (int j=0; j<selectedIndices.length; j++) newIndices[j] = selectedIndices[j];
            newIndices[selectedIndices.length] = jList1.getSelectedIndex();
        } else {
            newIndices = new int[selectedIndices.length-1];
            for (int j=0; j<newIndices.length; j++) newIndices[j] = selectedIndices[j];
            if (i<newIndices.length) newIndices[i] = selectedIndices[selectedIndices.length-1];
        }

        jList1.setSelectedIndices(newIndices);
        selectedIndices = newIndices;
    }//GEN-LAST:event_clicked

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed
        HashSet<String> hs = new HashSet<String>();
        for (int i = 0; i < jList1.getSelectedValues().length; i++) {
            hs.add(jList1.getSelectedValues()[i].toString());
        }
        caller.fillMenuItems(hs);
        dispose();
    }//GEN-LAST:event_jButtonOKActionPerformed

    private void jButtonSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectAllActionPerformed
        jList1.setSelectionInterval(0, jList1.getModel().getSize() - 1);
        selectedIndices = jList1.getSelectedIndices();
}//GEN-LAST:event_jButtonSelectAllActionPerformed

    private void jButtonDeselectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeselectAllActionPerformed
        jList1.clearSelection();
        selectedIndices = null;
}//GEN-LAST:event_jButtonDeselectAllActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonDeselectAll;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JButton jButtonSelectAll;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelMenus;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
