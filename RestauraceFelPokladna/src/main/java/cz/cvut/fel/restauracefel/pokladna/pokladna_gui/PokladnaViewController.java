/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.pokladna.pokladna_gui;

import cz.cvut.fel.restauracefel.library.library_gui.AdditionalLogin;
import cz.cvut.fel.restauracefel.pokladna.PokladnaController.MenuItemObject;
import cz.cvut.fel.restauracefel.pokladna.PokladnaController.MenuItemsObjects;
import cz.cvut.fel.restauracefel.pokladna.PokladnaController.PokladnaController;
import cz.cvut.fel.restauracefel.pokladna.PokladnaInterfaces.InterfacePressed;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *
 * @author Lukáš Viezán  
 */
public class PokladnaViewController {

    private static final PokladnaViewController instance = new PokladnaViewController();
    private MainFrame mainFrame = null;

    private PokladnaViewController() {
    }

    public static PokladnaViewController getInstance() {
        return instance;
    }

    public void run() {
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    public boolean isActive() {
        if (mainFrame == null) {
            return false;
        }

        return mainFrame.isVisible();
    }

    public int showConfirmDialogStandard(String text, String title) {
        return mainFrame.showConfirmDialogStandard(text, title);
    }

    /**
     * Zobrazi polozky menu
     * @param menuItems polozky menu
     */
    public void zobrazPolozky(List<MenuItemObject> menuItems, JComponent whereToAdd, final InterfacePressed interfacePressed, final EnumMenuItem akce, JLabel jLabelSum, String money, MenuItemsObjects paidOrders) {
        JPanel jPanelOrders = new JPanel();
        jPanelOrders.setOpaque(false);
        jPanelOrders.setLayout(new GroupLayout(jPanelOrders));

        for (int i = 0; i < menuItems.size(); i++) {
            MenuItemObject menuItem = menuItems.get(i);

            JLabel pocet = new JLabel(menuItem.getCount() + "x ");
            JLabel polozka = new JLabel(menuItem.getName());
            JLabel cena = new JLabel(menuItem.singleValue() + ",- ");

            pocet.setBounds(0, i * 35 + 10, 20, 35);
            polozka.setBounds(25, i * 35 + 10, 115, 35);
            cena.setBounds(145, i * 35 + 10, 40, 35);
            cena.setAlignmentX(Component.RIGHT_ALIGNMENT);

            final MenuItemObject temp = menuItem;

            JButton add = new JButton();
            if (akce == EnumMenuItem.PRIDEJ) {
                add = new JButton("+");
            }
            if (akce == EnumMenuItem.ODEBER) {
                add = new JButton("-");
            }
            add.setName(Integer.toString(menuItem.getID()));
            add.setFont(new Font("Verdana", Font.BOLD, 14));
            add.setFocusPainted(false);
            add.setBounds(190, i * 35 + 10, 35, 32);

            add.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    interfacePressed.useMenuItem(temp, akce);
                }
            });

            jPanelOrders.add(pocet);
            jPanelOrders.add(polozka);
            jPanelOrders.add(cena);

            if (akce == EnumMenuItem.PRIDEJ) {
                if (paidOrders.findByID(menuItem.getID()) == null || paidOrders.findByID(menuItem.getID()).getCount() < menuItem.getCount()) {
                    jPanelOrders.add(add);
                }
            }
            if (akce == EnumMenuItem.ODEBER) {
                jPanelOrders.add(add);
            }
        }
        jLabelSum.setText("  Celkem: " + PokladnaController.menuItemsSum(menuItems) + ",- " + money);

        whereToAdd.removeAll();
        whereToAdd.add(jPanelOrders);

        jPanelOrders.setSize(225, menuItems.size() * 35 + 15);
        jPanelOrders.setPreferredSize(new Dimension(225, menuItems.size() * 35 + 15));
        jPanelOrders.revalidate();
        jPanelOrders.repaint();
    }

    public void showMessageDialogInformation(String message, String title){
        mainFrame.showMessageDialogInformation(message, title);
    }

}
