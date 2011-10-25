package cz.cvut.fel.restauracefel.pokladna.pokladna_gui;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * Trida reprezentujici toolbar pro ovladani aplikace
 *
 * @author RealNeo
 * @author Tomas Hnizdil
 */
public class ToolBar extends JToolBar {

    public JButton createAccount = new JButton(new ImageIcon("images/new-bill-normal.png"));
    public JButton accountList = new JButton(new ImageIcon("images/accounts-normal.png"));
    public JButton payAccount = new JButton(new ImageIcon("images/count-bill-normal.png"));
    public JButton createOrder = new JButton(new ImageIcon("images/browse-bills.png"));
    public JButton moveMenuItems = new JButton(new ImageIcon("images/move-menu-items.png"));
    public JButton createCustomer = new JButton(new ImageIcon("images/new-client-normal.png"));
    public JButton createDiscount = new JButton(new ImageIcon("images/discount-normal.png"));
    public JButton grantDiscount = new JButton(new ImageIcon("images/grant-discount-normal.png"));
    public JButton createAccountCategory = new JButton(new ImageIcon("images/new-order-normal.png"));
    public JButton close = new JButton(new ImageIcon("images/exit.png"));
    private JPanel panel = new JPanel();

    /**
     * Konstruktor tridy ToolBar
     */
    public ToolBar() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        this.setLayout(new BorderLayout());
        createAccount.setFocusPainted(false);
        accountList.setFocusPainted(false);
        payAccount.setFocusPainted(false);
        createOrder.setFocusPainted(false);
        moveMenuItems.setFocusPainted(false);
        createCustomer.setFocusPainted(false);
        createDiscount.setFocusPainted(false);
        grantDiscount.setFocusPainted(false);
        createAccountCategory.setFocusPainted(false);
        close.setFocusPainted(false);

        panel.add(createAccount);
        panel.add(accountList);
        panel.add(createOrder);
        panel.add(payAccount);
        panel.add(moveMenuItems);
        //this.addSeparator(new Dimension(20, 20));
        panel.add(createCustomer);
        panel.add(createDiscount);
        panel.add(grantDiscount);
        panel.add(createAccountCategory);
        //  this.addSeparator();
        this.add(panel, BorderLayout.WEST);
        //this.add(close, BorderLayout.EAST);
    }
}
