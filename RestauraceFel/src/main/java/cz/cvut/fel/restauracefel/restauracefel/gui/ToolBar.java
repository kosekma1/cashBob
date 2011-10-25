package cz.cvut.fel.restauracefel.restauracefel.gui;

import cz.cvut.fel.restauracefel.library.service.LocalizationManager;
import cz.cvut.fel.restauracefel.restauracefel.main.Main;
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

    public JButton logout = new JButton(new ImageIcon("images/exit.png"));
    public JButton changeRights = new JButton(new ImageIcon("images/change-rights-normal.png"));
    public JButton cashModule = new JButton(new ImageIcon("images/cash.png"));
    public JButton managerAndStorageModule = new JButton(new ImageIcon("images/manager.png"));
    public JButton storageModule = new JButton(new ImageIcon("images/storage.png"));
    public JButton settings = new JButton(new ImageIcon("images/settings-normal.png"));
    public JButton localization = new JButton(new ImageIcon("images/settings-normal.png"));
    public JButton smenyModule = new JButton(new ImageIcon("images/workshift.png")); 
    private JPanel panelWest = new JPanel();
    private JPanel panelEast = new JPanel();
    private ViewController view;

    /**
     * Konstruktor tridy ToolBar
     */
    public ToolBar() {
        view = ViewController.getInstance();

        panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.X_AXIS));
        panelEast.setLayout(new BoxLayout(panelEast, BoxLayout.X_AXIS));
        this.setLayout(new BorderLayout());
        logout.setFocusPainted(false);
        changeRights.setFocusPainted(false);
        settings.setFocusPainted(false);
        cashModule.setFocusPainted(false);
        managerAndStorageModule.setFocusPainted(false);
        storageModule.setFocusPainted(false);
        smenyModule.setFocusPainted(false);
        
        panelWest.add(logout);
        panelWest.add(changeRights);
        panelWest.add(settings);
        panelWest.add(localization);
        panelEast.add(cashModule);
        panelEast.add(managerAndStorageModule);
        panelEast.add(storageModule);
        panelEast.add(smenyModule);
        
        this.add(panelWest, BorderLayout.WEST);
        this.add(panelEast, BorderLayout.EAST);
        setRights();

        LocalizationManager manager = LocalizationManager.getInstance();

        manager.localizeButton(Main.DEFAULT_BUNDLE_BASE_NAME, "LogoutButton", logout);
        manager.localizeButton(Main.DEFAULT_BUNDLE_BASE_NAME, "ChangeRightsButton", changeRights);
        manager.localizeButton(Main.DEFAULT_BUNDLE_BASE_NAME, "SettingsButton", settings);
        manager.localizeButton(Main.DEFAULT_BUNDLE_BASE_NAME, "LocalizationButton", localization);
        manager.localizeButton(Main.DEFAULT_BUNDLE_BASE_NAME, "CashModuleButton", cashModule);
        manager.localizeButton(Main.DEFAULT_BUNDLE_BASE_NAME, "ManagerAndStorageButton", managerAndStorageModule);
        manager.localizeButton(Main.DEFAULT_BUNDLE_BASE_NAME, "StorageModuleButton", storageModule);
    }

    /**
     * Metoda pro prijeti nebo odmitnuti pristupu k danym funkcim
     * Pokud mnozina pristupovych prav neobsahuje odpovidajici retezec nastavi tlacitko pro danou funkci na disabled.
     */
    public void setRights() {
//        if ( ! view.hasRights( "Změna přístupových práv" ) ) {
//            changeRights.setEnabled( false );
//        }
//        if ( ! view.hasRights( "Nastavení klienta" ) ) {
//            settings.setEnabled( false );
//        }
    }
}
