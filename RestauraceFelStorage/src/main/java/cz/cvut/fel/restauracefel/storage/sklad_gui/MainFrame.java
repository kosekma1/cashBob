package cz.cvut.fel.restauracefel.storage.sklad_gui;

import cz.cvut.fel.restauracefel.hibernate.User;
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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import cz.cvut.fel.restauracefel.library.service.Backup;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.library.service.LocalizationManager;
import cz.cvut.fel.restauracefel.library.view.CommonViewController;
import cz.cvut.fel.restauracefel.storage.storageController.StorageController;

/**
 * Hlavni trida grafickeho uzivatelskeho rozhrani. Stara se o vytvareni ostatnich
 * trid (lazy inicialization), predavani rizeni temto tridam atd.
 *
 * @author Jarda
 */
public class MainFrame extends JFrame {

    private LocalizationManager manager = LocalizationManager.getInstance();
    private Container con = null;
    private JMenuBar menuBar = null;
    private JMenu menuFile = null;
    private JMenu menuStore = null;
    private JMenu zrcadlo = null;
    private JMenu zalohovani = null;
    private JMenu menuHelp = null;
    private JMenuItem close = null;
    private JMenuItem uzaverka = null;
    private JMenuItem uzaverkaPrehled = null;
    private JMenuItem newMaterial = null;
    private JMenuItem insertMat = null;
    private JMenuItem exportMat = null;
    private JMenuItem deprecMat = null;
    private JMenuItem matTypes = null;
    private JMenuItem reasonTypes = null;
    private JMenuItem about = null;
    private JMenuItem vytvoritZalohu = null;
    private JScrollPane panel = null;
    private JScrollPane panelTable = null;
    private static AbstractForm form = null;
    private static MaterialForm materialForm = null;
    private static NewUzaverkaForm uzaverkaForm = null;
    private static NewUzaverkaList uzaverkaFormList = null;
    private static IncomeForm insertMaterialForm = null;
    private static ExpenditureForm expenditureForm = null;
    private static DepreciationForm depreciationForm = null;
    private static MaterialTypeDialog mtd = null;
    private static ReasonTypeDialog rtd = null;
    private static StatusBar statusBar = new StatusBar();
    private static User loggedUser = null;
    private final CommonViewController commonViewController;
    private final MainFrame mainFrame;
    public String[] rights = null;

    /**
     * Konstruktor tridy MainFrame.
     */
    public MainFrame() { //User user, String[] prava
        commonViewController = CommonViewController.getInstance();
        loggedUser = StorageController.getInstance().user; // TODO
        rights = StorageController.getInstance().prava; // TODO
        if (loggedUser == null) {
            this.dispose();
        }
        manager.localizeFrame(StorageController.DEFAULT_BUNDLE_BASE_NAME, "MainFrame", this);
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
     * Tato metoda vznikla kvuli zobrazovani UzaverkaForm, ktere se nekona v teto tride.
     * Tuto metodu by mely (po upravach) vyuzivat i ostatni panely v teto tride. Dojde tim k vyraznemu zkraceni tridy.
     * Vojtech Ruschka
     * @param p
     */
    public void addPanel(JPanel p) {
        con.removeAll();
        panel.getViewport().add(p);
        con.add(panel, BorderLayout.NORTH);


        //panelTable.getViewport().add(uzaverkaForm.getTable());
        //con.add(panelTable, BorderLayout.CENTER);

        con.add(statusBar, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Tato metoda se stara o inicializaci a vytvoreni hlavniho menu (instance
     * tridy JMenuBar). Pro jednotlive polozky menu (JMenuItem) jsou zde definovane
     * posluchace (ActionListener).
     */
    private void createMenu() {
        menuBar = new JMenuBar();
        menuFile = new JMenu("Soubor");
        manager.localizeMenu(StorageController.DEFAULT_BUNDLE_BASE_NAME, "FileMenu", menuFile);

        menuStore = new JMenu("Sklad");
        manager.localizeMenu(StorageController.DEFAULT_BUNDLE_BASE_NAME, "StoreMenu", menuStore);

        zrcadlo = new JMenu("Zrcadlo");
        manager.localizeMenu(StorageController.DEFAULT_BUNDLE_BASE_NAME, "MirrorMenu", zrcadlo);

        menuHelp = new JMenu("Nápověda");
        manager.localizeMenu(StorageController.DEFAULT_BUNDLE_BASE_NAME, "HelpMenu", menuHelp);

        zalohovani = new JMenu("Zálohování");
        manager.localizeMenu(StorageController.DEFAULT_BUNDLE_BASE_NAME, "BackingMenu", zalohovani);

        close = new JMenuItem("Konec aplikace");
        manager.localizeMenuItem(StorageController.DEFAULT_BUNDLE_BASE_NAME, "CloseMenuItem", close);

        newMaterial = new JMenuItem("Skladové záznamy");
        manager.localizeMenuItem(StorageController.DEFAULT_BUNDLE_BASE_NAME, "NewMaterialMenuItem", newMaterial);

        insertMat = new JMenuItem("Příjem surovin na sklad");
        manager.localizeMenuItem(StorageController.DEFAULT_BUNDLE_BASE_NAME, "InsertMaterialMenuItem", insertMat);

        exportMat = new JMenuItem("Výdej surovin ze skladu");
        manager.localizeMenuItem(StorageController.DEFAULT_BUNDLE_BASE_NAME, "ExportMaterialMenuItem", exportMat);

        deprecMat = new JMenuItem("Odpis surovin ze skladu");
        manager.localizeMenuItem(StorageController.DEFAULT_BUNDLE_BASE_NAME, "DepreciateMaterialMenuItem", deprecMat);

        matTypes = new JMenuItem("Druhy surovin");
        manager.localizeMenuItem(StorageController.DEFAULT_BUNDLE_BASE_NAME, "MaterialTypesMenuItem", matTypes);

        reasonTypes = new JMenuItem("Důvody odpisů surovin");
        manager.localizeMenuItem(StorageController.DEFAULT_BUNDLE_BASE_NAME, "ReasonTypesMenuItem", reasonTypes);

        uzaverkaPrehled = new JMenuItem("Uzávěrky");
        manager.localizeMenuItem(StorageController.DEFAULT_BUNDLE_BASE_NAME, "BalancingMenuItem", uzaverkaPrehled);

        vytvoritZalohu = new JMenuItem("Vytvořit zálohu dat");
        manager.localizeMenuItem(StorageController.DEFAULT_BUNDLE_BASE_NAME, "BackupMenuItem", vytvoritZalohu);

        about = new JMenuItem("O aplikaci");
        manager.localizeMenuItem(StorageController.DEFAULT_BUNDLE_BASE_NAME, "AboutMenuItem", about);

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

        newMaterial.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        newMaterial.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!contains("Editace surovin")) {
                    if (!commonViewController.performBaseAdditionalLogin(mainFrame, "Editace surovin")) {
                        return;
                    }
                }
                
                try {
                    if (materialForm == null) {
                        materialForm = new MaterialForm(getThis(), statusBar);
                    } else {
                        materialForm.refresh();
                    }
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(null, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                form = materialForm;

                con.removeAll();
                panel.getViewport().add(materialForm);
                con.add(panel, BorderLayout.NORTH);


                panelTable.getViewport().add(materialForm.getTable());
                con.add(panelTable, BorderLayout.CENTER);

                con.add(statusBar, BorderLayout.SOUTH);
                setVisible(true);
            }
        });

        insertMat.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
        insertMat.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!contains("Příjem surovin")) {
                    if (!commonViewController.performBaseAdditionalLogin(mainFrame, "Příjem surovin")) {
                        return;
                    }
                }
                
                try {
                    if (insertMaterialForm == null) {
                        insertMaterialForm = new IncomeForm(getThis(), statusBar);
                    } else {
                        insertMaterialForm.refresh();
                    }
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(null, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                form = insertMaterialForm;

                con.removeAll();
                panel.getViewport().add(insertMaterialForm);
                con.add(panel, BorderLayout.NORTH);

                panelTable.getViewport().add(insertMaterialForm.getTable());
                con.add(panelTable, BorderLayout.CENTER);

                con.add(statusBar, BorderLayout.SOUTH);
                setVisible(true);
            }
        });

        exportMat.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        exportMat.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!contains("Výdej surovin")) {
                    if (!commonViewController.performBaseAdditionalLogin(mainFrame, "Výdej surovin")) {
                        return;
                    }
                }
                
                try {
                    if (expenditureForm == null) {
                        expenditureForm = new ExpenditureForm(getThis(), statusBar);
                    } else {
                        expenditureForm.refresh();
                    }
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(null, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                form = expenditureForm;

                con.removeAll();
                panel.getViewport().add(expenditureForm);
                con.add(panel, BorderLayout.NORTH);

                panelTable.getViewport().add(expenditureForm.getTable());
                con.add(panelTable, BorderLayout.CENTER);

                con.add(statusBar, BorderLayout.SOUTH);
                setVisible(true);
            }
        });

        deprecMat.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        deprecMat.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!contains("Odpis surovin")) {
                    if (!commonViewController.performBaseAdditionalLogin(mainFrame, "Odpis surovin")) {
                        return;
                    }
                }
                
                try {
                    if (depreciationForm == null) {
                        depreciationForm = new DepreciationForm(getThis(), statusBar);
                    } else {
                        depreciationForm.refresh();
                    }
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(null, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                form = depreciationForm;

                con.removeAll();
                panel.getViewport().add(depreciationForm);
                con.add(panel, BorderLayout.NORTH);

                panelTable.getViewport().add(depreciationForm.getTable());
                con.add(panelTable, BorderLayout.CENTER);

                con.add(statusBar, BorderLayout.SOUTH);
                setVisible(true);
            }
        });

        matTypes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
        matTypes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!contains("Editace druhů surovin")) {
                    if (!commonViewController.performBaseAdditionalLogin(mainFrame, "Editace druhů surovin")) {
                        return;
                    }
                }
                
                try {
                    mtd = new MaterialTypeDialog(getThis(), true);
                    mtd.setVisible(true);

                    if (form != null) {
                        form.refresh();
                    }
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(null, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        reasonTypes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
        reasonTypes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!contains("Editace důvodů odpisu")) {
                    if (!commonViewController.performBaseAdditionalLogin(mainFrame, "Editace důvodů odpisu")) {
                        return;
                    }
                }
                
                try {
                    rtd = new ReasonTypeDialog(getThis(), true);
                    rtd.setVisible(true);
                    if (form != null) {
                        form.refresh();
                    }
                } catch (FileNotFoundException fnfe) {
                    JOptionPane.showMessageDialog(null, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        uzaverkaPrehled.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        uzaverkaPrehled.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!contains("Přehled uzávěrek")) {
                    if (!commonViewController.performBaseAdditionalLogin(mainFrame, "Přehled uzávěrek")) {
                        return;
                    }
                }
                
                uzaverkaFormList = new NewUzaverkaList(getThis(), getLoggedUser());
                try {
                    uzaverkaFormList.refresh();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NotBoundException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RemoteException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }



                con.removeAll();
                panel.getViewport().add(uzaverkaFormList);
                con.add(panel, BorderLayout.NORTH);


//                panelTable.getViewport().add(uzaverkaFormList.getTable());
                //con.add(panelTable, BorderLayout.CENTER);

                con.add(statusBar, BorderLayout.SOUTH);
                setVisible(true);

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
        menuStore.add(newMaterial);
        menuStore.add(insertMat);
        menuStore.add(exportMat);
        menuStore.add(deprecMat);
        menuStore.add(new JSeparator());
        menuStore.add(matTypes);
        menuStore.add(reasonTypes);
        menuHelp.add(about);
        zrcadlo.add(uzaverkaPrehled);
        zalohovani.add(vytvoritZalohu);

        menuBar.add(menuFile);
        menuBar.add(menuStore);
        menuBar.add(zrcadlo);
        menuBar.add(zalohovani);
        menuBar.add(menuHelp);

        this.setJMenuBar(menuBar);

        //setRights();
    }

    public boolean contains(String name) {
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

    /*public void setRights() {
        if (!contains("Editace surovin")) {
            newMaterial.setEnabled(false);
        }
        if (!contains("Příjem surovin")) {
            insertMat.setEnabled(false);
        }
        if (!contains("Výdej surovin")) {
            exportMat.setEnabled(false);
        }
        if (!contains("Odpis surovin")) {
            deprecMat.setEnabled(false);
        }
        if (!contains("Editace druhů surovin")) {
            matTypes.setEnabled(false);
        }
        if (!contains("Editace důvodů odpisu")) {
            reasonTypes.setEnabled(false);
        }
        if (!contains("Tvorba uzávěrky (zrcadlo)")) {
            //uzaverka.setEnabled(false);
        }
        if (!contains("Přehled uzávěrek")) {
            uzaverkaPrehled.setEnabled(false);
        }
        if (!contains("Zálohování dat")) {
            vytvoritZalohu.setEnabled(false);
        }
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

    /**
     * Metoda navraci instanci tridy User prave prihlaseneho uzivatele.
     *
     * @return prihlaseny uzivatel
     */
    public static User getLoggedUser() {
        return loggedUser;
    }
}
