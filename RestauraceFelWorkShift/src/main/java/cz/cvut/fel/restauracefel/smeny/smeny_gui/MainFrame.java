package cz.cvut.fel.restauracefel.smeny.smeny_gui;

import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.library.view.CommonViewController;
import cz.cvut.fel.restauracefel.smeny.SmenyController.SmenyController;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Hlavní okno aplikace.
 * 
 * @author Martin
 */
public class MainFrame extends JFrame {

    public static User loggedUser = null;
    public static String[] rights = null;
    private Container con = null;
    private ToolBar toolBar = new ToolBar();
    public BackroundPane panel = null;
    private static StatusBar statusBar = new StatusBar();
    private String title = null;
    protected static CreateShiftForm createShiftForm = null;
    protected static CreateTemplateForm createTemplateForm = null;
    protected static WorkShiftPlanForm workShiftPlanForm = null;
    protected static StatisticsForm statisticsForm = null;        
    protected static OverviewLeaderShiftForm overViewShiftForm = null;
    protected static InformationForm informationForm = null;
    protected static OverviewEmployeeShiftForm overViewEmplForm = null;
    private SmenyViewController view = null;
    private CommonViewController commonViewController;

    public MainFrame( //User user, String[] prava
            ) {

        loggedUser = SmenyController.getInstance().user;
        rights = SmenyController.getInstance().prava;
        if (loggedUser == null) {
            System.exit(0);
        }
                
        String userName = loggedUser.getUsername();
        this.title = "Restaurace FEL - Modul směn, přihlášený uživatel: " + userName;
        this.setTitle(title);
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        view = SmenyViewController.getInstance();
        commonViewController = CommonViewController.getInstance();

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
        setRights();
    }

    /**
     * Metoda pro prirazeni listeneru jednotlivym tlacitkam v toolbaru
     */
    private void createToolBar() {

        toolBar.close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                closeAction();
            }
        });

        toolBar.addTypeWorkShift.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                loadForm(EnumChooseForm.ADD_TYPE_WORKSHIFT);
            }
        });

        toolBar.addTemplate.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                loadForm(EnumChooseForm.ADD_TEMPLATE);
            }
        });

        toolBar.planOfShifts.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                loadForm(EnumChooseForm.PLAN_OF_SHIFTS);
            }
        });

        toolBar.statistics.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                loadForm(EnumChooseForm.STATISTICS_FORM);
            }
        });

        toolBar.information.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                loadForm(EnumChooseForm.INFORMATION);
            }
        });

        toolBar.overviewEmpShift.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                loadForm(EnumChooseForm.OVERVIEW_EMPLOYEE_SHIFT);
            }
        });
       
        toolBar.overviewShift.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                loadForm(EnumChooseForm.OVERVIEW_LEADER_SHIFT);
            }
        });

        refreshWindowLayout();

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
     * Metoda pro prijeti nebo odmitnuti pristupu k danym funkcim
     * Pokud mnozina pristupovych prav neobsahuje odpovidajici retezec nastavi tlacitko pro danou funkci na disabled.
     */
    public void setRights() {
        /*
        if (!contains("Tvorba nového účtu")) toolBar.createAccount.setEnabled(false);
        if (!contains("Přehled účtů")) toolBar.accountList.setEnabled(false);
        if (!contains("Tvorba nové objednávky")) toolBar.createOrder.setEnabled(false);
        if (!contains("Placení objednávek")) toolBar.payAccount.setEnabled(false);
        if (!contains("Přesun objednávek")) toolBar.moveMenuItems.setEnabled(false);
        if (!contains("Tvorba nového zákazníka")) toolBar.createCustomer.setEnabled(false);
        if (!contains("Tvorba nové slevy")) toolBar.createDiscount.setEnabled(false);
        if (!contains("Přiřazení slevy osobám a rolím")) toolBar.grantDiscount.setEnabled(false);
        if (!contains("Tvorba nové kategorie účtů")) toolBar.createAccountCategory.setEnabled(false);
         */
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
     * Akce ukonci beh programu
     */
    private void closeAction() {
        int x = JOptionPane.showConfirmDialog(null, "Opravdu ukončit modul směn?", "Konec", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (x == 0) {
            this.dispose();
        }
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
                case ADD_TYPE_WORKSHIFT:
                    //TODO testovat prava                    
                    /*if (!contains("Tvorba nového typu směny")) {
                    if (!commonViewController.performBaseAdditionalLogin(this, "Tvorba nového typu směny")) {
                    return;
                    }
                    }*/
                    af = createShiftForm;
                    if (af == null) {
                        af = new CreateShiftForm(this, statusBar);
                    } else {
                        af.refresh();
                    }
                    break;
                case ADD_TEMPLATE:
                    if (!contains("Tvorba nového templatu")) {
                        if (!commonViewController.performBaseAdditionalLogin(this, "Tvorba nového typu směny")) {
                            return;
                        }
                    }
                    af = createTemplateForm;
                    if (af == null) {
                        af = new CreateTemplateForm(this, statusBar);
                    } else {
                        af.refresh();
                    }
                    break;
                case PLAN_OF_SHIFTS:
                    if (!contains("Tvorba plánu směn")) {
                        if (!commonViewController.performBaseAdditionalLogin(this, "Tvorba plánu směn")) {
                            return;
                        }
                    }
                    af = workShiftPlanForm;
                    af = new WorkShiftPlanForm(this, statusBar);
                    //af = new WorkShiftPlanForm(this, statusBar, loggedUser.getUserAttendanceId(), EnumOrder.CREATE_ORDER);
                    break;
                case STATISTICS_FORM:
                    af = statisticsForm;
                    af = new StatisticsForm(this, statusBar);
                    //af = new WorkShiftPlanForm(this, statusBar, loggedUser.getUserAttendanceId(), EnumOrder.CREATE_ORDER);
                    break;                
                case OVERVIEW_LEADER_SHIFT:
                    if (!contains("Přehled směn - vedoucí")) {
                        if (!commonViewController.performBaseAdditionalLogin(this, "Přehled směn - vedoucí")) {
                            return;
                        }
                    }
                    af = overViewShiftForm;
                    if (af == null) {
                        af = new OverviewLeaderShiftForm(this, statusBar);
                    } else {
                        af.refresh();
                    }
                    break;
                case INFORMATION:
                    af = informationForm;
                    if (af == null) {
                        af = new InformationForm(this, statusBar);
                    } else {
                        af.refresh();
                    }
                    break;
                case OVERVIEW_EMPLOYEE_SHIFT:
                    if (!contains("Přehled směn zaměstnanec")) {
                        if (!commonViewController.performBaseAdditionalLogin(this, "Přehled směn zaměstnanec")) {
                            return;
                        }
                    }
                    af = overViewEmplForm;
                    if (af == null) {
                        af = new OverviewEmployeeShiftForm(this, statusBar);
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
            ex.printStackTrace();
            try {
                
                PrintWriter vystup = new PrintWriter(new FileWriter("log-chyb.txt"));
                StackTraceElement[] el = ex.getStackTrace();
                for (StackTraceElement els : el) {
                    vystup.println(els.toString());
                }
                vystup.println(ex.getMessage());
                vystup.close();                  
                
            } catch (IOException ex1) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex1);
            }

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
