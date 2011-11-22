package cz.cvut.fel.restauracefel.smeny.smeny_gui;

import com.toedter.calendar.JDateChooser;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import cz.cvut.fel.restauracefel.library.service.EmptyListException;
import cz.cvut.fel.restauracefel.smeny.SmenyController.SmenyController;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Trida reprezentujici GUI formular pro vytvareni noveho uctu.
 *  
 * @author Tomas Hnizdil
 */
public class WorkShiftPlanForm extends AbstractForm {

    private ChooseShiftDialog chooseShiftDialog = null;
    private ChooseDeleteShiftDialog chooseDeleteShiftDialog = null;
    private ChooseTemplateDialog chooseTemplateDialog = null;
    private StatusBar statusBar = null;
    private MainFrame parent = null;
    private Point point = new Point(550, 210);
    private JDateChooser dateChooserFrom;
    private JDateChooser dateChooserTo;

    /**
     * Konstruktor tridy CreateShiftForm.
     *
     * @param parent
     * @param bar
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public WorkShiftPlanForm(MainFrame parent, StatusBar bar) throws FileNotFoundException, NotBoundException, RemoteException {
        this.parent = parent;
        this.statusBar = bar;
        loadAllData();
        initComponents();
        initCalendars();        
        refresh();
        clearFields();
    }

    private void initCalendars() {
        
        Locale czechLocale = new Locale("cs", "CZ");
        
        dateChooserFrom = new JDateChooser();
        dateChooserFrom.setMinSelectableDate(Calendar.getInstance().getTime());
        dateChooserFrom.setBounds(0, 0, 218, 43);
        dateChooserFrom.setLocale(czechLocale);
        jPanelDateChooserFrom.add(dateChooserFrom, null);

        dateChooserTo = new JDateChooser();
        dateChooserTo.setMinSelectableDate(Calendar.getInstance().getTime());
        dateChooserTo.setBounds(0, 0, 218, 43);
        dateChooserTo.setLocale(czechLocale);
        jPanelDateChooserTo.add(dateChooserTo, null);

    }
    
    private void loadAllData(){
        try {
            SmenyController.getInstance().generateTableDataPlannedWorkShifts();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WorkShiftPlanForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(WorkShiftPlanForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(WorkShiftPlanForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metoda prenastavuje statusBar.
     */
    @Override
    protected void refresh() {
        statusBar.setMessage("Tento formulář slouží k plánování směn.");
    }

    /**
     * Metoda kontrolujici spravnost vyplnenych udaju.
     *
     * @return Vraci index urcujici vstupni komponentu, ktera obsahuje
     * neplatny vstup. Pokud je vse vporadku tak navraci 0.
     */
    @Override
    /* protected EnumSpravnost isValidInput() {
    if (!Validator.isText(jTextFieldName)) {
    return EnumSpravnost.NeniToSpravne;
    }
    return EnumSpravnost.JeToSpravne;
    } */
    /**
     * Metoda cisti vsechny vstupni pole formulare.
     */
    //@Override
    protected void clearFields() {
        //Validator.clearTextField(jTextFieldName);
        //Validator.clearTextField(jTextFieldTable);
        //Validator.clearTextField(jTextFieldPerson);
        //Validator.clearTextField(jTextFieldDiscountType);
        //Validator.clearTextField(jTextFieldAccountCategory);
        //Validator.clearTextField(jTextFieldNote);
    }

    /**
     * Metoda vytvari a zobrazuje formular pro objednani polozek na ucet.
     *
     * @param accountId id uctu, na ktery se bude objednavat
     */
    public void loadCreateOrderForm(int accountId) {
        /*try {
        CreateOrderForm createOrderForm = new CreateOrderForm(parent, statusBar, accountId, MainFrame.loggedUser.getUserAttendanceId());
        parent.panel.getViewport().add(createOrderForm);
        parent.panel.validate();
        parent.panel.repaint();
        parent.refreshWindowLayout();
        refresh();
        } catch (FileNotFoundException fnfe) {
        JOptionPane.showMessageDialog(this, "Konfigurační soubor \"" + ConfigParser.getConfigFile() + "\" nebyl nalezen.", "Chyba", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Nelze navázat spojení se serverem.", "Chyba komunikace", JOptionPane.ERROR_MESSAGE);
        }
         */
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        billPanel = new BackgroundPanel();
        jLabelInfoText = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanelButtonForPlan = new javax.swing.JPanel();
        jButtonSavePlanWorkShift = new javax.swing.JButton();
        jButtonAddFromTemplate = new javax.swing.JButton();
        jButtonAddWorkShift = new javax.swing.JButton();
        jComboBoxSelectRange = new javax.swing.JComboBox();
        jButtonAddWorkShift1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableWorkShifts = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jPanelDateChooserFrom = new javax.swing.JPanel();
        jPanelDateChooserTo = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableWorkShiftPlan = new javax.swing.JTable();
        jPanelDeleteWorkshift = new javax.swing.JPanel();
        jButtonDeleteWorkShift = new javax.swing.JButton();
        jLabelTitle = new javax.swing.JLabel();

        setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBox.light"));
        setPreferredSize(new java.awt.Dimension(1032, 622));

        billPanel.setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBox.light"));
        billPanel.setOpaque(false);

        jLabelInfoText.setFont(new java.awt.Font("Calibri", 1, 22));
        jLabelInfoText.setText("Plánovat směny");

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 14));
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Datum od:");

        jPanelButtonForPlan.setOpaque(false);

        jButtonSavePlanWorkShift.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonSavePlanWorkShift.setText("Uložit");
        jButtonSavePlanWorkShift.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonSavePlanWorkShift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSavePlanWorkShiftActionPerformed(evt);
            }
        });

        jButtonAddFromTemplate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonAddFromTemplate.setText("Přidat ze šablony");
        jButtonAddFromTemplate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonAddFromTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddFromTemplateActionPerformed(evt);
            }
        });

        jButtonAddWorkShift.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonAddWorkShift.setText("Přidat směnu");
        jButtonAddWorkShift.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonAddWorkShift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddWorkShiftActionPerformed(evt);
            }
        });

        jComboBoxSelectRange.setFont(new java.awt.Font("Tahoma", 0, 12));
        jComboBoxSelectRange.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "všední dny i víkendy", "všední dny", "víkendy" }));

        jButtonAddWorkShift1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonAddWorkShift1.setText("Odebrat směnu");
        jButtonAddWorkShift1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonAddWorkShift1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddWorkShift1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelButtonForPlanLayout = new javax.swing.GroupLayout(jPanelButtonForPlan);
        jPanelButtonForPlan.setLayout(jPanelButtonForPlanLayout);
        jPanelButtonForPlanLayout.setHorizontalGroup(
            jPanelButtonForPlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelButtonForPlanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelButtonForPlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxSelectRange, 0, 163, Short.MAX_VALUE)
                    .addComponent(jButtonAddFromTemplate, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(jButtonAddWorkShift, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(jButtonAddWorkShift1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(jButtonSavePlanWorkShift, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelButtonForPlanLayout.setVerticalGroup(
            jPanelButtonForPlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelButtonForPlanLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jComboBoxSelectRange, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonAddFromTemplate, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonAddWorkShift, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonAddWorkShift1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonSavePlanWorkShift, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonSavePlanWorkShift.getAccessibleContext().setAccessibleName("Přidat směnu");

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 14));
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Datum do:");

        jTableWorkShifts.setFont(new java.awt.Font("Calibri", 0, 14));
        jTableWorkShifts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Směna"
            }
        ));
        jScrollPane3.setViewportView(jTableWorkShifts);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel5.setFont(new java.awt.Font("Calibri", 1, 22));
        jLabel5.setText("Plánované směny (neproběhlé)");

        javax.swing.GroupLayout jPanelDateChooserFromLayout = new javax.swing.GroupLayout(jPanelDateChooserFrom);
        jPanelDateChooserFrom.setLayout(jPanelDateChooserFromLayout);
        jPanelDateChooserFromLayout.setHorizontalGroup(
            jPanelDateChooserFromLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 218, Short.MAX_VALUE)
        );
        jPanelDateChooserFromLayout.setVerticalGroup(
            jPanelDateChooserFromLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        jPanelDateChooserTo.setPreferredSize(new java.awt.Dimension(218, 43));

        javax.swing.GroupLayout jPanelDateChooserToLayout = new javax.swing.GroupLayout(jPanelDateChooserTo);
        jPanelDateChooserTo.setLayout(jPanelDateChooserToLayout);
        jPanelDateChooserToLayout.setHorizontalGroup(
            jPanelDateChooserToLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 218, Short.MAX_VALUE)
        );
        jPanelDateChooserToLayout.setVerticalGroup(
            jPanelDateChooserToLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 43, Short.MAX_VALUE)
        );

        jTableWorkShiftPlan.setFont(new java.awt.Font("Calibri", 0, 14));
        jTableWorkShiftPlan.setModel(SmenyController.getInstance().getModelPlannedWorkShift());
        jScrollPane1.setViewportView(jTableWorkShiftPlan);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelDeleteWorkshift.setOpaque(false);

        jButtonDeleteWorkShift.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonDeleteWorkShift.setText("Odstranit");
        jButtonDeleteWorkShift.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonDeleteWorkShift.setPreferredSize(new java.awt.Dimension(149, 39));
        jButtonDeleteWorkShift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteWorkShiftActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDeleteWorkshiftLayout = new javax.swing.GroupLayout(jPanelDeleteWorkshift);
        jPanelDeleteWorkshift.setLayout(jPanelDeleteWorkshiftLayout);
        jPanelDeleteWorkshiftLayout.setHorizontalGroup(
            jPanelDeleteWorkshiftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDeleteWorkshiftLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonDeleteWorkShift, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelDeleteWorkshiftLayout.setVerticalGroup(
            jPanelDeleteWorkshiftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDeleteWorkshiftLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonDeleteWorkShift, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout billPanelLayout = new javax.swing.GroupLayout(billPanel);
        billPanel.setLayout(billPanelLayout);
        billPanelLayout.setHorizontalGroup(
            billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelDateChooserFrom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelInfoText, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGroup(billPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE))
                    .addComponent(jPanelDateChooserTo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(57, 57, 57)
                .addComponent(jPanelButtonForPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(billPanelLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jPanelDeleteWorkshift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        billPanelLayout.setVerticalGroup(
            billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, billPanelLayout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelDeleteWorkshift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(billPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(billPanelLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jPanelButtonForPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(billPanelLayout.createSequentialGroup()
                        .addGroup(billPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelInfoText)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelDateChooserFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelDateChooserTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jLabelTitle.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setFont(new java.awt.Font("Tahoma", 0, 18));
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("Plánování směn");
        jLabelTitle.setOpaque(true);
        jLabelTitle.setPreferredSize(new java.awt.Dimension(81, 622));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1199, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(billPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(billPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void saveWorkShifts() throws FileNotFoundException, NotBoundException, RemoteException {
        Date dateFrom = dateChooserFrom.getDate();
        Date dateTo = dateChooserTo.getDate();
                
        boolean result = SmenyController.getInstance().saveWorkShifts(dateFrom, dateTo);
        
        if(result){
            clearFields();
            SmenyController.getInstance().clearTableWorkShiftData();
            dateChooserFrom.setDate(null);
            dateChooserTo.setDate(null);
            
            loadAllData(); //aktualizace tabulky sablon                        
            this.jTableWorkShiftPlan.setModel(SmenyController.getInstance().getModelPlannedWorkShift());
            
            this.repaint();
        }
    }

    private void jButtonSavePlanWorkShiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSavePlanWorkShiftActionPerformed
        try {
            saveWorkShifts();                                        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WorkShiftPlanForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(WorkShiftPlanForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(WorkShiftPlanForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        KeyboardDialog keyboard = new KeyboardDialog(parent, true);
        keyboard.setLocation(point);
        keyboard.setTextField(jTextFieldName);
        keyboard.setVisible(true);
         *
         */
        
        
    }//GEN-LAST:event_jButtonSavePlanWorkShiftActionPerformed

private void jButtonAddFromTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddFromTemplateActionPerformed
    try {
        chooseTemplateDialog = new ChooseTemplateDialog(parent, true, jTableWorkShifts);
        chooseTemplateDialog.setLocation(point);
        chooseTemplateDialog.setVisible(true);
    } catch (EmptyListException ex) {
        Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
    } catch (RemoteException ex) {
        Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
    } catch (NotBoundException ex) {
        Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
    } catch (FileNotFoundException ex) {
        Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_jButtonAddFromTemplateActionPerformed

private void jButtonAddWorkShiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddWorkShiftActionPerformed
    try {
        chooseShiftDialog = new ChooseShiftDialog(parent, true, jTableWorkShifts);
        chooseShiftDialog.setLocation(point);
        chooseShiftDialog.setVisible(true);
    } catch (EmptyListException ex) {
        Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
    } catch (RemoteException ex) {
        Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
    } catch (NotBoundException ex) {
        Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
    } catch (FileNotFoundException ex) {
        Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_jButtonAddWorkShiftActionPerformed

private void jButtonDeleteWorkShiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteWorkShiftActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_jButtonDeleteWorkShiftActionPerformed

private void jButtonAddWorkShift1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddWorkShift1ActionPerformed
   try {
        chooseDeleteShiftDialog = new ChooseDeleteShiftDialog(parent, true, jTableWorkShifts);
        chooseDeleteShiftDialog.setLocation(point);
        chooseDeleteShiftDialog.setVisible(true);
    } catch (EmptyListException ex) {
        Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
    } catch (RemoteException ex) {
        Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
    } catch (NotBoundException ex) {
        Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
    } catch (FileNotFoundException ex) {
        Logger.getLogger(CreateTemplateForm.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_jButtonAddWorkShift1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel billPanel;
    private javax.swing.JButton jButtonAddFromTemplate;
    private javax.swing.JButton jButtonAddWorkShift;
    private javax.swing.JButton jButtonAddWorkShift1;
    private javax.swing.JButton jButtonDeleteWorkShift;
    private javax.swing.JButton jButtonSavePlanWorkShift;
    private javax.swing.JComboBox jComboBoxSelectRange;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelInfoText;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelButtonForPlan;
    private javax.swing.JPanel jPanelDateChooserFrom;
    private javax.swing.JPanel jPanelDateChooserTo;
    private javax.swing.JPanel jPanelDeleteWorkshift;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableWorkShiftPlan;
    private javax.swing.JTable jTableWorkShifts;
    // End of variables declaration//GEN-END:variables
}
