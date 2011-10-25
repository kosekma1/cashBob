package cz.cvut.fel.restauracefel.restauracefel.gui;

import cz.cvut.fel.restauracefel.library.service.LocalizationManager;
import cz.cvut.fel.restauracefel.library.view.CommonViewController;
import cz.cvut.fel.restauracefel.restauracefel.main.Main;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Hlavni trida grafickeho uzivatelskeho rozhrani. Stara se o vytvareni ostatnich
 * trid (lazy inicialization), predavani rizeni temto tridam atd.
 *
 * @author Tomas Hnizdil
 */
public class MainFrame extends JFrame {

    private Container con = null;
    private ToolBar toolBar = null;
    private BackgroundPanel panel = null;
    private static LogingForm lf = null;
    private static SettingsDialog sd = null;
    private static StatusBar statusBar = new StatusBar();
    private ViewController view;

    /**
     * Konstruktor tridy MainFrame.
     */
    public MainFrame() {
        LocalizationManager.getInstance().localizeFrame(Main.DEFAULT_BUNDLE_BASE_NAME, "MainFrame", this);

        initComponents();

        setVisible(true);

        view = ViewController.getInstance();
    }

    private void showLogingForm() {
        this.setTitle("Přihlášení do systému Restaurace FEL");
        AbstractForm af = new LogingForm(this, statusBar);
        changeForm(af, false);
    }

    private void showRightForm() {

        if (!view.hasRights("Změna přístupových práv")) {
            if (!CommonViewController.getInstance().performAdditionalLogin(this, "Změna přístupových práv")) {
                return;
            }
        }

        try {
            sd = new SettingsDialog(this, true);
        } catch (FileNotFoundException ex) {
            view.configFileError();
        }

        AbstractForm af = new AssignRightForm(this, statusBar);
        changeForm(af, true);
    }

    private void showLocalization() {
        new LocalizationDialog(this, true).setVisible(true);
    }

    private void showSettings() {

        if (!view.hasRights("Nastavení klienta")) {
            if (!CommonViewController.getInstance().performAdditionalLogin(this, "Nastavení klienta")) {
                return;
            }
        }

        try {
            sd = new SettingsDialog(this, true);
        } catch (FileNotFoundException ex) {
            view.configFileError();
        }

        sd.setVisible(true);
        changeForm(null, true);
    }

    private void changeForm(AbstractForm af, boolean refresh) {
        con.removeAll();
        panel.getViewport().add(af);
        panel.repaint();
        panel.validate();
        con.validate();
        con.repaint();
        refreshWindowLayout(refresh);
    }

    /**
     * Metoda pro prirazeni listeneru jednotlivym tlacitkam v toolbaru
     */
    private void createToolBar() {

        toolBar.logout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.logout();
                showLogingForm();
            }
        });

        toolBar.changeRights.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showRightForm();
            }
        });

        toolBar.settings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showSettings();
            }
        });

        toolBar.localization.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showLocalization();
            }
        });

        toolBar.cashModule.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.showPokladna();
            }
        });

        toolBar.managerAndStorageModule.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.showManager();
            }
        });

        toolBar.storageModule.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.showStorage();
            }
        });
        
        toolBar.smenyModule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.showSmeny();
            }
        });

        refreshWindowLayout(true);

    }

    /**
     * Metoda obnovujici design. Pridava do JFrame jednotlive panely.
     *
     * @param tb ma-li se zobrazit toolbar (pri logovani se nezobrazuje)
     */
    private void refreshWindowLayout(boolean tb) {
        if (tb) {
            con.add(toolBar, BorderLayout.NORTH);
        }
        con.add(panel, BorderLayout.CENTER);
        con.add(statusBar, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Metoda pro ukonceni cele aplikace.
     */
    public void close() {
        System.exit(0);
    }

    /**
     * Metoda inicializuje okno a jeho komponenty. Vola metodu pro vytvoreni
     * hlavniho menu.
     */
    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int) dim.getWidth();
        int h = (int) dim.getHeight();
        int y = (int) ((dim.getHeight() - h) / 2);
        int x = (int) ((dim.getWidth() - w) / 2);

        this.setBounds(x, y, w, h);
        this.setMinimumSize(new Dimension(1280, 720));

        con = this.getContentPane();
        con.setLayout(new BorderLayout());

        panel = new BackgroundPanel();

        lf = new LogingForm(this, null);

        con.removeAll();
        panel.getViewport().add(lf);
        con.add(panel, BorderLayout.NORTH);

        con.add(statusBar, BorderLayout.SOUTH);
    }

    /**
     * Metoda volana pri uspesnem prihlaseni z LogingForm. Zobrazuje hlavní nabýdku přihlášeného uživatele
     *
     * @param username uživatelské jméno přihlášeného uivatele
     */
    public void showUserGate(String username) {
        toolBar = new ToolBar();
        this.setTitle("Restaurace FEL - Přihlášený uživatel: " + username);
        this.createToolBar();
    }

    public void showErrorMessage(String text, String title) {
        JOptionPane.showMessageDialog(this, text, title, JOptionPane.ERROR_MESSAGE);
    }
}
