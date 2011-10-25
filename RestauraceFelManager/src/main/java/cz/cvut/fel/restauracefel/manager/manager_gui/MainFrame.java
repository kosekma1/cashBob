package cz.cvut.fel.restauracefel.manager.manager_gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import cz.cvut.fel.restauracefel.library.service.Backup;
import cz.cvut.fel.restauracefel.library.view.CommonViewController;

/**
 * Hlavni trida grafickeho uzivatelskeho rozhrani. Stara se o vytvareni ostatnich
 * trid (lazy inicialization), predavani rizeni temto tridam atd.
 *
 * @author Jarda
 * @author Tomas Hnizdil
 */
public class MainFrame extends JFrame {

    private Container con = null;
    private JMenuBar menuBar = null;
    private JMenu menuFile = null;
    private JMenu menuEmployers = null;
    private JMenu menuMenu = null;
    private JMenu zalohovani = null;
    private JMenu menuHelp = null;
    private JMenuItem close = null;
    private JMenuItem insertEmp = null;
    private JMenuItem changePasswd = null;
    private JMenuItem createMenu = null;
    private JMenuItem createMenuItem = null;
    private JMenuItem createTable = null;
    private JMenuItem about = null;
    private JMenuItem vytvoritZalohu = null;
    private JScrollPane panel = null;
    private JScrollPane panelTable = null;
    private static AbstractForm form = null;
    private static UserForm userForm = null;
    private static MenuItemForm menuItemForm = null;
    private static MenuForm menuForm = null;
    private static TableDialog td = null;
    private static StatusBar statusBar = new StatusBar();
    private static ManagerViewController view;
    private final CommonViewController commonViewController;
    private final MainFrame mainFrame;

    /**
     * Konstruktor tridy MainFrame.
     */
    public MainFrame() { //User user, String[] prava
        view = ManagerViewController.getInstance();
        commonViewController = CommonViewController.getInstance();

        if (view.getLoggedUser() == null) {
            this.dispose();
        }

        this.setTitle("Restaurace FEL - Manažerský modul, přihlášený uživatel: " + view.getLoggedUser().getUsername());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        mainFrame = this;
    }

    /**
     * Metoda navraci referenci na instanci pro kterou byla zavolana.
     *
     * @return instance tridy JFrame
     */
    private JFrame getThis() {
        return this;
    }

    /**
     * Metoda pro ukonceni cele aplikace.
     */
    public void close() {
        this.dispose();
    }

    /**
     * Tato metoda se stara o inicializaci a vytvoreni hlavniho menu (instance
     * tridy JMenuBar). Pro jednotlive polozky menu (JMenuItem) jsou zde definovane
     * posluchace (ActionListener).
     */
    private void createMenu() {
        menuBar = new JMenuBar();
        menuFile = new JMenu("Soubor");
        menuEmployers = new JMenu("Zaměstnanci");
        menuMenu = new JMenu("Menu");
        menuHelp = new JMenu("Nápověda");
        zalohovani = new JMenu("Zálohování");

        close = new JMenuItem("Konec aplikace");
        insertEmp = new JMenuItem("Evidence zaměstnanců");
        changePasswd = new JMenuItem("Změnit heslo přihlášeného uživatele");
        createMenu = new JMenuItem("Evidence menu");
        createMenuItem = new JMenuItem("Evidence položek menu");
        createTable = new JMenuItem("Evidence stolů");
        vytvoritZalohu = new JMenuItem("Vytvořit zálohu dat");
        about = new JMenuItem("O aplikaci");

        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK));
        close.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int x = JOptionPane.showConfirmDialog(null, "Opravdu chcete ukončit běh programu?", "Konec", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (x == 0) {
                    close();
                }
            }
        });

        insertEmp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        insertEmp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!contains("Editace zaměstnanců")) {
                    if (!commonViewController.performBaseAdditionalLogin(mainFrame, "Editace zaměstnanců")) {
                        return;
                    }
                }

                if (userForm == null) {
                    userForm = new UserForm(getThis(), statusBar);
                } else {
                    userForm.refresh();
                }

                form = userForm;

                con.removeAll();
                panel.getViewport().add(userForm);
                con.add(panel, BorderLayout.NORTH);

                panelTable.getViewport().add(userForm.getTable());
                con.add(panelTable, BorderLayout.CENTER);

                con.add(statusBar, BorderLayout.SOUTH);
                setVisible(true);
            }
        });

        changePasswd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        changePasswd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!contains("Změna uživatelských hesel")) {
                    if (!commonViewController.performBaseAdditionalLogin(mainFrame, "Změna uživatelských hesel")) {
                        return;
                    }
                }
                
                ChangePasswordDialog chpd = new ChangePasswordDialog(getThis(), true);
                chpd.setVisible(true);
                if (form != null) {
                    form.refresh();
                }
            }
        });

        createMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        createMenu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!contains("Tvorba menu")) {
                    if (!commonViewController.performBaseAdditionalLogin(mainFrame, "Tvorba menu")) {
                        return;
                    }
                }
                
                if (menuForm == null) {
                    menuForm = new MenuForm(getThis(), statusBar);
                } else {
                    menuForm.refresh();
                }

                form = menuForm;

                con.removeAll();
                panel.getViewport().add(menuForm);
                con.add(panel, BorderLayout.NORTH);

                panelTable.getViewport().add(menuForm.getTable());
                con.add(panelTable, BorderLayout.CENTER);

                con.add(statusBar, BorderLayout.SOUTH);
                setVisible(true);
            }
        });

        createMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        createMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!contains("Tvorba položek menu")) {
                    if (!commonViewController.performBaseAdditionalLogin(mainFrame, "Tvorba položek menu")) {
                        return;
                    }
                }
                
                if (menuItemForm == null) {
                    menuItemForm = new MenuItemForm(getThis(), statusBar);
                } else {
                    menuItemForm.refresh();
                }

                form = menuItemForm;

                con.removeAll();
                panel.getViewport().add(menuItemForm);
                con.add(panel, BorderLayout.NORTH);

                panelTable.getViewport().add(menuItemForm.getTable());
                con.add(panelTable, BorderLayout.CENTER);

                con.add(statusBar, BorderLayout.SOUTH);
                setVisible(true);
            }
        });

        createTable.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        createTable.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!contains("Editace stolů")) {
                    if (!commonViewController.performBaseAdditionalLogin(mainFrame, "Editace stolů")) {
                        return;
                    }
                }
                
                td = new TableDialog(getThis(), true);
                td.setVisible(true);
            }
        });

        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        about.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(getThis(), "Aplikace RestauraceFEL - Manažerský modul.", "O aplikaci", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        vytvoritZalohu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!contains("Zálohování dat")) {
                    if (!commonViewController.performBaseAdditionalLogin(mainFrame, "Zálohování dat")) {
                        return;
                    }
                }
                
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                try {
                    fileChooser.setCurrentDirectory(new File(".").getCanonicalFile());
                    File backingUp = new File("../Server/db_rest_fel");
                    int val = fileChooser.showDialog(getThis(), "Vyber");
                    if (val == JFileChooser.APPROVE_OPTION) {
                        File where = fileChooser.getSelectedFile();
                        File dst = Backup.createRootFolder(backingUp, where);
                        Backup.copyDirectory(backingUp, dst);
                        JOptionPane.showMessageDialog(null, "Záloha byla vytvořena", "Záloha vytvořena", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(null, fnfe.getMessage(), "Chyba", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });



        menuFile.add(close);
        menuEmployers.add(insertEmp);
        menuEmployers.add(changePasswd);
        menuMenu.add(createMenu);
        menuMenu.add(createMenuItem);
        menuMenu.add(new JSeparator());
        menuMenu.add(createTable);
        menuHelp.add(about);
        zalohovani.add(vytvoritZalohu);

        menuBar.add(menuFile);
        menuBar.add(menuEmployers);
        menuBar.add(menuMenu);
        menuBar.add(zalohovani);
        menuBar.add(menuHelp);

        this.setJMenuBar(menuBar);

        //setRights();
    }

    private boolean contains(String name) {
        String[] rights = view.getRights();

        if (rights == null) {
            return false;
        }
        for (int i = 0; i < rights.length; i++) {
            if (rights[i].equals(name)) {
                return true;
            }
        }
        return false;
    }

    /*private void setRights() {
    if (!contains("Editace zaměstnanců")) insertEmp.setEnabled(false);
    if (!contains("Změna uživatelských hesel")) changePasswd.setEnabled(false);
    if (!contains("Tvorba menu")) createMenu.setEnabled(false);
    if (!contains("Tvorba položek menu")) createMenuItem.setEnabled(false);
    if (!contains("Editace stolů")) createTable.setEnabled(false);
    if (!contains("Zálohování dat")) vytvoritZalohu.setEnabled(false);
    }*/
    /**
     * Metoda inicializuje okno a jeho komponenty. Vola metodu pro vytvoreni
     * hlavniho menu.
     */
    private void initComponents() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = 650;
        int h = 550;
        int y = (int) ((dim.getHeight() - h) / 2);
        int x = (int) ((dim.getWidth() - w) / 2);
        this.setBounds(x, y, w, h);
        this.setVisible(true);
        this.setMinimumSize(new Dimension(400, 400));

        con = this.getContentPane();
        con.setLayout(new BorderLayout());

        panel = new JScrollPane();
        panelTable = new JScrollPane();

        this.createMenu();

    }

    public void showErrorMessage(String text, String title) {
        JOptionPane.showMessageDialog(this, text, title, JOptionPane.ERROR_MESSAGE);
    }
}
