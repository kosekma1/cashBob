package cz.cvut.fel.restauracefel.smeny.smeny_gui;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import cz.cvut.fel.restauracefel.smeny.SmenyController.SmenyController;
import cz.cvut.fel.restauracefel.smeny.SmenyController.SmenyController.WorkShiftFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPopupMenu;

/**
 * Trida reprezentujici GUI formular pro spravu smen zamestnancem.
 * Zamestnanec se muze prihlasovat, potvrzovat a odhlasovat ze smeny.
 *  
 * @author Martin Kosek
 */
public class OverviewEmployeeShiftForm extends AbstractForm {

    private StatusBar statusBar = null;
    private MainFrame parent = null;
    private Point point = new Point(550, 210);
    private JPopupMenu contextMenu = null;
    //private OverviewEmployeeShiftForm osf = null;
    private OverviewEmployeeShiftForm osf = this;
    private int x = 0;
    private int y = 0;
    private Locale locale = new Locale("cs", "CZ");
    String[] comboBoxItems = new String[]{"Vše", "Moje role", "Moje přihlášené", "Mnou obsazené"};

    /**
     * Konstruktor tridy CreateShiftForm.
     *
     * @param parent
     * @param bar
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public OverviewEmployeeShiftForm(MainFrame parent, StatusBar bar) throws FileNotFoundException, NotBoundException, RemoteException {
        //osf = this;
        this.parent = parent;
        this.statusBar = bar;
        initAllData();
        initComponents();
        initMyComponents();
        setDateFromToWeek();
        refresh();        
    }

    private void initAllData() throws FileNotFoundException, RemoteException, NotBoundException {
        SmenyController.getInstance().initRangeDate(locale);
        SmenyController.getInstance().generateTableWorkShiftsOverview(SmenyController.WorkShiftFilter.ALL);
    }

    protected void reloadTable(WorkShiftFilter filter) {
        try {
            SmenyController.getInstance().generateTableWorkShiftsOverview(filter);
            jTableWorkShiftOverview.setModel(SmenyController.getInstance().getModelOverviewWorkShift());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setDateFromToWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String firstDate = sdf.format(SmenyController.getInstance().getDateFrom());
        String lastDate = sdf.format(SmenyController.getInstance().getDateTo());
        int week = SmenyController.getInstance().getWeek();
        jTextFieldWeek.setText(String.valueOf(week));
        jTextFieldWeekRange.setText(firstDate + " - " + lastDate);
    }

    private void initMyComponents() {

        contextMenu = new JPopupMenu();
        contextMenu.add(new LoginCurrentUserAction(this, this.jTableWorkShiftOverview));
        contextMenu.add(new LogoutCurrentUserAction(this, this.jTableWorkShiftOverview));
        contextMenu.add(new ApproveOccupyAction(this, this.jTableWorkShiftOverview));
        contextMenu.add(new RequestCancelOccupationAction(this, this.jTableWorkShiftOverview));
        contextMenu.addSeparator();
        contextMenu.add("Konec");

        this.addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent me) {
                //System.out.println(me);
                x = me.getX();
                y = me.getY();
                //refresh();
            }
        });

        jTableWorkShiftOverview.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent me) {
                //System.out.println(me);
                int button = me.getButton();
                x = me.getX();
                y = me.getY();
                if (button == me.BUTTON3) {                    
                    contextMenu.show(jTableWorkShiftOverview, x, y);
                }
                osf.repaint(); //always redisplay screen                
            }
        });

        jComboBoxFilter.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent me) { //TODO - solve repaint after action in JComboBox1           
                osf.repaint(); //always redisplay screen
            }
        });

    }

    /**
     * Metoda prenastavuje statusBar.
     */
    @Override
    protected void refresh() {
        statusBar.setMessage("Tento formulář slouží k přihlašování a odhlašování na směny.");
        //tatusBar.setMessage("x: " + this.x + " y: " + this.y);        
    }
   

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTitle = new javax.swing.JLabel();
        jLabelOverviewWorkShift = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButtonPreviousWeek = new javax.swing.JButton();
        jButtonNextWeek = new javax.swing.JButton();
        jTextFieldWeekRange = new javax.swing.JTextField();
        jLabelWeek = new javax.swing.JLabel();
        jTextFieldWeek = new javax.swing.JTextField();
        jComboBoxFilter = new javax.swing.JComboBox();
        refreshTableButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableWorkShiftOverview = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButtonLoginUser = new javax.swing.JButton();
        jButtonLogoutUser = new javax.swing.JButton();
        jButtonApprovedOccupy = new javax.swing.JButton();
        jButtonRequestCancel = new javax.swing.JButton();

        setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBox.light"));
        setPreferredSize(new java.awt.Dimension(948, 577));

        jLabelTitle.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setFont(new java.awt.Font("Tahoma", 0, 18));
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("Přehled směn - zaměstnanec");
        jLabelTitle.setOpaque(true);

        jLabelOverviewWorkShift.setFont(new java.awt.Font("Calibri", 1, 22));
        jLabelOverviewWorkShift.setText("Přehled směn");

        jPanel1.setOpaque(false);

        jButtonPreviousWeek.setText("<");
        jButtonPreviousWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviousWeekActionPerformed(evt);
            }
        });

        jButtonNextWeek.setText(">");
        jButtonNextWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextWeekActionPerformed(evt);
            }
        });

        jTextFieldWeekRange.setEditable(false);
        jTextFieldWeekRange.setFont(new java.awt.Font("Calibri", 0, 14));
        jTextFieldWeekRange.setText("9.5.2011-14.5.2011");

        jLabelWeek.setFont(new java.awt.Font("Calibri", 1, 14));
        jLabelWeek.setText("Týden:");

        jTextFieldWeek.setEditable(false);
        jTextFieldWeek.setFont(new java.awt.Font("Calibri", 0, 14));
        jTextFieldWeek.setText("14");

        jComboBoxFilter.setFont(new java.awt.Font("Calibri", 0, 14));
        jComboBoxFilter.setModel(new javax.swing.DefaultComboBoxModel(comboBoxItems));
        jComboBoxFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFilterActionPerformed(evt);
            }
        });

        refreshTableButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/refresh.png"))); // NOI18N
        refreshTableButton.setToolTipText("Aktualizovat tabulku");
        refreshTableButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        refreshTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshTableButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBoxFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(refreshTableButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jLabelWeek)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldWeekRange, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonPreviousWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonNextWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBoxFilter, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addComponent(refreshTableButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldWeekRange, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jTextFieldWeek, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonPreviousWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonNextWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelWeek)))
                .addContainerGap())
        );

        jPanel2.setPreferredSize(new java.awt.Dimension(620, 371));

        jTableWorkShiftOverview.setFont(new java.awt.Font("Calibri", 0, 14));
        jTableWorkShiftOverview.setModel(SmenyController.getInstance().getModelOverviewWorkShift());
        jTableWorkShiftOverview.setRowHeight(25);
        jScrollPane1.setViewportView(jTableWorkShiftOverview);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setOpaque(false);

        jButtonLoginUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonLoginUser.setText("Přihlásit");
        jButtonLoginUser.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonLoginUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginUserActionPerformed(evt);
            }
        });

        jButtonLogoutUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonLogoutUser.setText("Odhlásit");
        jButtonLogoutUser.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonLogoutUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutUserActionPerformed(evt);
            }
        });

        jButtonApprovedOccupy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonApprovedOccupy.setText("Potvrdit obsazení");
        jButtonApprovedOccupy.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonApprovedOccupy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApprovedOccupyActionPerformed(evt);
            }
        });

        jButtonRequestCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cz/cvut/fel/restauracefel/buttons/left-red.png"))); // NOI18N
        jButtonRequestCancel.setText("Žádost o zrušení obsazení");
        jButtonRequestCancel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonRequestCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRequestCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonLoginUser, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .addComponent(jButtonLogoutUser, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .addComponent(jButtonApprovedOccupy, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .addComponent(jButtonRequestCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jButtonLoginUser, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonLogoutUser, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonApprovedOccupy, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonRequestCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 948, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelOverviewWorkShift)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 497, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelOverviewWorkShift, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(61, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void logError(Exception ex) {
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
    }

    private void jButtonNextWeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextWeekActionPerformed
        setDaysInWeek(7, 13);
        reloadTable(getCurrentFilter());
    }//GEN-LAST:event_jButtonNextWeekActionPerformed

private void jButtonLoginUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginUserActionPerformed

    int rowNumber = this.jTableWorkShiftOverview.getSelectedRow(); //bude slouzit jako index pro datovou strukturu ve ktere bude ulozeno id smeny                
    if (rowNumber > -1) {
        try {
            int workShiftId = SmenyController.getInstance().getWorkShiftIdFromOverViewTable(rowNumber);
            SmenyController.getInstance().saveCurrentUserToWorkShift(workShiftId);
            reloadTable(getCurrentFilter());
        } catch (FileNotFoundException ex) {
            logError(ex);
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            logError(ex);
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            logError(ex);
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            logError(ex);
        }
    } else {
        parent.showMessageDialogInformation("Vyberte řádek", "Informace");
    }


}//GEN-LAST:event_jButtonLoginUserActionPerformed

private void jButtonLogoutUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutUserActionPerformed
    int rowNumber = this.jTableWorkShiftOverview.getSelectedRow(); //bude slouzit jako index pro datovou strukturu ve ktere bude ulozeno id smeny        
    if (rowNumber > -1) {
        try {
            int idWorkshift = SmenyController.getInstance().getWorkShiftIdFromOverViewTable(rowNumber);
            SmenyController.getInstance().logoutCurrentUserFromWorkShift(idWorkshift);
            reloadTable(getCurrentFilter());
        } catch (FileNotFoundException ex) {
            logError(ex);
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            logError(ex);
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            logError(ex);
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            logError(ex);
        }
    } else {
        parent.showMessageDialogInformation("Vyberte řádek", "Informace");
    }
}//GEN-LAST:event_jButtonLogoutUserActionPerformed

private void jButtonApprovedOccupyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApprovedOccupyActionPerformed

    int rowNumber = this.jTableWorkShiftOverview.getSelectedRow(); //bude slouzit jako index pro datovou strukturu ve ktere bude ulozeno id smeny        
    String message = "Potvrzeno";
    if (rowNumber > -1) {
        try {
            int idWorkshift = SmenyController.getInstance().getWorkShiftIdFromOverViewTable(rowNumber);
            SmenyController.getInstance().updateOccupationMessageUser(idWorkshift, message);
            reloadTable(getCurrentFilter());
        } catch (FileNotFoundException ex) {
            logError(ex);
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            logError(ex);
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            logError(ex);
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        parent.showMessageDialogInformation("Vyberte řádek", "Informace");
    }
}//GEN-LAST:event_jButtonApprovedOccupyActionPerformed

private void jButtonRequestCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRequestCancelActionPerformed
    int rowNumber = this.jTableWorkShiftOverview.getSelectedRow(); //bude slouzit jako index pro datovou strukturu ve ktere bude ulozeno id smeny        
    String message = "Zažádáno o zrušení";

    if (rowNumber > -1) {
        try {
            int idWorkshift = SmenyController.getInstance().getWorkShiftIdFromOverViewTable(rowNumber);
            SmenyController.getInstance().updateOccupationMessageUser(idWorkshift, message);
            reloadTable(getCurrentFilter());
        } catch (FileNotFoundException ex) {
            logError(ex);
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            logError(ex);
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            logError(ex);
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        parent.showMessageDialogInformation("Vyberte řádek", "Informace");
    }
}//GEN-LAST:event_jButtonRequestCancelActionPerformed
    /**
     * Add or subtract number of the days from first day of thce current week
     * and set new week dates.
     * @param from
     * @param to 
     */
    private void setDaysInWeek(int from, int to) {
        Date date = SmenyController.getInstance().getDateFrom();
        Calendar cal = Calendar.getInstance(locale);

        cal.setTime(date);
        cal.add(Calendar.DAY_OF_WEEK, from);//first date of the week
        SmenyController.getInstance().setDateFrom(cal.getTime());

        cal.setTime(date);
        cal.add(Calendar.DAY_OF_WEEK, to);//last date of the week
        SmenyController.getInstance().setDateTo(cal.getTime());

        SmenyController.getInstance().setWeek(cal.get(Calendar.WEEK_OF_YEAR));

        setDateFromToWeek();
    }

    private void jButtonPreviousWeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviousWeekActionPerformed
        setDaysInWeek(-7, -1);
        reloadTable(getCurrentFilter());
    }//GEN-LAST:event_jButtonPreviousWeekActionPerformed

    /**
     * Returns current filter selected in ComboBox with filters.
     * @return 
     */
    protected SmenyController.WorkShiftFilter getCurrentFilter() {
        int index = jComboBoxFilter.getSelectedIndex();
        switch (index) {
            case 0:
                return WorkShiftFilter.ALL;
            case 1:
                return WorkShiftFilter.ROLE_USER;
            case 2:
                return WorkShiftFilter.LOGIN_USER;
            case 3:
                return WorkShiftFilter.OCCUPATION_USER;
        }
        return WorkShiftFilter.ALL;
    }

    private void jComboBoxFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFilterActionPerformed
        reloadTable(getCurrentFilter());
        this.repaint();
    }//GEN-LAST:event_jComboBoxFilterActionPerformed

    private void refreshTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshTableButtonActionPerformed
        reloadTable(getCurrentFilter());        
    }//GEN-LAST:event_refreshTableButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonApprovedOccupy;
    private javax.swing.JButton jButtonLoginUser;
    private javax.swing.JButton jButtonLogoutUser;
    private javax.swing.JButton jButtonNextWeek;
    private javax.swing.JButton jButtonPreviousWeek;
    private javax.swing.JButton jButtonRequestCancel;
    private javax.swing.JComboBox jComboBoxFilter;
    private javax.swing.JLabel jLabelOverviewWorkShift;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelWeek;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableWorkShiftOverview;
    private javax.swing.JTextField jTextFieldWeek;
    private javax.swing.JTextField jTextFieldWeekRange;
    private javax.swing.JButton refreshTableButton;
    // End of variables declaration//GEN-END:variables
}
