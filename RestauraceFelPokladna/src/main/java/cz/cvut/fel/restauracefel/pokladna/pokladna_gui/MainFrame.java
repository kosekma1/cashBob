package cz.cvut.fel.restauracefel.pokladna.pokladna_gui;

import cz.cvut.fel.restauracefel.hibernate.User;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.library.view.CommonViewController;

/**
 * Hlavni trida grafickeho uzivatelskeho rozhrani modulu Pokladna. Stara se o vytvareni ostatnich
 * trid (lazy inicialization), predavani rizeni temto tridam atd.
 *
 * @author Tomas Hnizdil
 */
public class MainFrame extends JFrame {

    private Container con = null;
    private ToolBar toolBar = new ToolBar();
    public BackroundPane panel = null;
    protected static ChooseAccountForm chooseAccountForm = null;
    protected static CreateAccountForm createAccountForm = null;
    protected static AccountListForm accountListForm = null;
    protected static InsertCreditForm insertCreditForm = null;
    protected static AssignDiscountForm createDiscountForm = null;
    protected static GrantDiscountForm grantDiscountForm = null;
    protected static CreateAccountCategoryForm createAccountCategoryForm = null;
    protected static CreateOrderForm createOrderForm = null;
    private static StatusBar statusBar = new StatusBar();
    public static User loggedUser = null;
    public static String[] rights = null;
    //final static int CREATE_ORDER = 1;
    //final static int PAY_ORDER = 2;
    //final static int MOVE_ORDER = 3;
    public static javax.swing.JPanel PanelVyberu = new JPanel();
    private PokladnaViewController view;
    private CommonViewController commonViewController;

    /**
     * Konstruktor tridy MainFrame.
     *
     * @param user prihlaseny uzivatel
     * @param prava seznam pristupovych prav prihlaseneho uzivatele
     */
    public MainFrame() { //User user, String[] prava
//        loggedUser = user;
//        rights = prava;
        loggedUser = cz.cvut.fel.restauracefel.pokladna.PokladnaController.PokladnaController.getInstance().user;
        rights = cz.cvut.fel.restauracefel.pokladna.PokladnaController.PokladnaController.getInstance().prava;
        if (loggedUser == null) {
            System.exit(0);
        }
        this.setTitle("Restaurace FEL - Pokladní modul, přihlášený uživatel: " + loggedUser.getUsername());
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        view = PokladnaViewController.getInstance();
        commonViewController = CommonViewController.getInstance();
    }

    /**
     * Metoda obnovujici design. Pridava do JFrame jednotlive panely.
     */
    public void refreshWindowLayout() {
        con.add(toolBar, BorderLayout.NORTH);
        con.add(panel, BorderLayout.CENTER);
        con.add(statusBar, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Metoda pro zobrazeni vybraneho formulare v hlavnim JFrame.
     *
     * @param form cislo zvoleneho formulare
     */
    public void loadForm(EnumChooseForm form) {
        AbstractForm af = null;

        try {
            switch (form) {
                case createAccountForm:
                    if (!contains("Tvorba nového účtu")) {
                        if (!commonViewController.performAdditionalLogin(this, "Tvorba nového účtu")) {
                            return;
                        }
                    }

                    af = createAccountForm;
                    if (af == null) {
                        af = new CreateAccountForm(this, statusBar);
                    } else {
                        af.refresh();
                    }
                    break;
                case accountListForm:
                    if (!contains("Přehled účtů")) {
                        if (!commonViewController.performAdditionalLogin(this, "Přehled účtů")) {
                            return;
                        }
                    }

                    af = accountListForm;
                    if (af == null) {
                        af = new AccountListForm(this, statusBar);
                    } else {
                        af.refresh();
                    }
                    break;
                case chooseAccountFormCreate:
                    if (!contains("Tvorba nové objednávky")) {
                        if (!commonViewController.performAdditionalLogin(this, "Tvorba nové objednávky")) {
                            return;
                        }
                    }

                    af = chooseAccountForm;
                    af = new ChooseAccountForm(this, statusBar, loggedUser.getUserId(), EnumOrder.CREATE_ORDER);
                    break;
                case chooseAccountFormPay:
                    if (!contains("Placení objednávek")) {
                        if (!commonViewController.performAdditionalLogin(this, "Placení objednávek")) {
                            return;
                        }
                    }

                    af = chooseAccountForm;
                    af = new ChooseAccountForm(this, statusBar, loggedUser.getUserId(), EnumOrder.PAY_ORDER);
                    break;
                case chooseAccountFormMove:
                    if (!contains("Přesun objednávek")) {
                        if (!commonViewController.performAdditionalLogin(this, "Přesun objednávek")) {
                            return;
                        }
                    }

                    af = chooseAccountForm;
                    af = new ChooseAccountForm(this, statusBar, loggedUser.getUserId(), EnumOrder.MOVE_ORDER);
                    break;
                case insertCreditForm:
                    if (!contains("Tvorba nového zákazníka")) {
                        if (!commonViewController.performAdditionalLogin(this, "Tvorba nového zákazníka")) {
                            return;
                        }
                    }

                    af = insertCreditForm;
                    if (af == null) {
                        af = new InsertCreditForm(this, statusBar);
                    } else {
                        af.refresh();
                    }
                    break;
                case createDiscountForm:
                    if (!contains("Tvorba nové slevy")) {
                        if (!commonViewController.performAdditionalLogin(this, "Tvorba nové slevy")) {
                            return;
                        }
                    }

                    af = createDiscountForm;
                    if (af == null) {
                        af = new AssignDiscountForm(this, statusBar);
                    } else {
                        af.refresh();
                    }
                    break;
                case grantDiscountForm:
                    if (!contains("Přiřazení slevy osobám a rolím")) {
                        if (!commonViewController.performAdditionalLogin(this, "Přiřazení slevy osobám a rolím")) {
                            return;
                        }
                    }

                    af = grantDiscountForm;
                    if (af == null) {
                        af = new GrantDiscountForm(this, statusBar);
                    } else {
                        af.refresh();
                    }
                    break;
                case createAccountCategoryForm:
                    if (!contains("Tvorba nové kategorie účtů")) {
                        if (!commonViewController.performAdditionalLogin(this, "Tvorba nové kategorie účtů")) {
                            return;
                        }
                    }

                    af = createAccountCategoryForm;
                    if (af == null) {
                        af = new CreateAccountCategoryForm(this, statusBar);
                    } else {
                        af.refresh();
                    }
                    break;
                default:
                    return;
            }
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(null, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // panel.removeAll();

        panel.getViewport().add(af);
        panel.repaint();
        panel.validate();
        refreshWindowLayout();
    }

    /**
     * Akce ukonci beh programu
     */
    private void closeAction() {
        //  int x = JOptionPane.showConfirmDialog(null, "Opravdu chcete ukončit běh programu?", "Konec", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        // if (x == 0) {
        this.dispose();
        // }
    }

    /**
     * Metoda pro prirazeni listeneru jednotlivym tlacitkam v toolbaru
     */
    private void createToolBar() {

        toolBar.createAccount.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadForm(EnumChooseForm.createAccountForm);
            }
        });

        toolBar.close.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                closeAction();
            }
        });

        toolBar.accountList.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadForm(EnumChooseForm.accountListForm);
            }
        });

        toolBar.createOrder.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadForm(EnumChooseForm.chooseAccountFormCreate);
            }
        });

        toolBar.payAccount.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadForm(EnumChooseForm.chooseAccountFormPay);
            }
        });

        toolBar.moveMenuItems.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadForm(EnumChooseForm.chooseAccountFormMove);
            }
        });

        toolBar.createCustomer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadForm(EnumChooseForm.insertCreditForm);
            }
        });

        toolBar.createDiscount.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadForm(EnumChooseForm.createDiscountForm);
            }
        });

        toolBar.grantDiscount.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadForm(EnumChooseForm.grantDiscountForm);
            }
        });

        toolBar.createAccountCategory.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadForm(EnumChooseForm.createAccountCategoryForm);
            }
        });

        refreshWindowLayout();

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
        this.setVisible(true);
        this.setMinimumSize(new Dimension(1280, 720));
        //this.setSize(new Dimension(1280, 720));

        con = this.getContentPane();
        con.setLayout(new BorderLayout());

        panel = new BackroundPane();

        this.createToolBar();
        //setRights();
    }

    /**
     * Metoda pro hledani retezce v poli nazvu pristupovych prav
     *
     * @param name hledany retezec
     */
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

    /**
     * Metoda pro prijeti nebo odmitnuti pristupu k danym funkcim
     * Pokud mnozina pristupovych prav neobsahuje odpovidajici retezec nastavi tlacitko pro danou funkci na disabled.
     */
    /*public void setRights() {
    if (!contains("Tvorba nového účtu")) {
    toolBar.createAccount.setEnabled(false);
    }
    if (!contains("Přehled účtů")) {
    toolBar.accountList.setEnabled(false);
    }
    if (!contains("Tvorba nové objednávky")) {
    toolBar.createOrder.setEnabled(false);
    }
    if (!contains("Placení objednávek")) {
    toolBar.payAccount.setEnabled(false);
    }
    if (!contains("Přesun objednávek")) {
    toolBar.moveMenuItems.setEnabled(false);
    }
    if (!contains("Tvorba nového zákazníka")) {
    toolBar.createCustomer.setEnabled(false);
    }
    if (!contains("Tvorba nové slevy")) {
    toolBar.createDiscount.setEnabled(false);
    }
    if (!contains("Přiřazení slevy osobám a rolím")) {
    toolBar.grantDiscount.setEnabled(false);
    }
    if (!contains("Tvorba nové kategorie účtů")) {
    toolBar.createAccountCategory.setEnabled(false);
    }
    }*/
    /**
     * Metoda navraci instanci tridy User prave prihlaseneho uzivatele.
     *
     * @return prihlaseny uzivatel
     */
    public static User getLoggedUser() {
        return loggedUser;
    }

    /**
     * Klasicke Ano/Ne potvrzovací okno
     * @param text Popis
     * @param title
     * @return 0, pokud klikne na ano
     */
    public int showConfirmDialogStandard(String text, String title) {
        return JOptionPane.showConfirmDialog(this, text, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    public void showMessageDialogInformation(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
