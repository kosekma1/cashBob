package cz.cvut.fel.restauracefel.smeny.smeny_gui;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import cz.cvut.fel.restauracefel.smeny.SmenyController.SmenyController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPopupMenu;

/**
 * Trida reprezentujici GUI formular pro vytvareni noveho uctu.
 *  
 * @author Martin Kosek
 */
public class OverviewEmployeeShiftForm extends AbstractForm {

    /*private ChooseTableDialog chooseTableDialog = null;
    private ChoosePersonDialog choosePersonDialog = null;
    private ChooseDiscountTypeDialog chooseDiscountTypeDialog = null;
    private ChooseAccountCategoryDialog chooseAccountCategoryDialog = null;*/
    private StatusBar statusBar = null;
    private MainFrame parent = null;
    private Point point = new Point(550, 210);
    private JPopupMenu contextMenu = null;
    private OverviewEmployeeShiftForm osf = null;
    private int x = 0;
    private int y = 0;

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
        osf = this;
        this.parent = parent;
        this.statusBar = bar;
        loadAllData();
        initComponents();
        initMyComponents();
        //refresh();
        clearFields();
    }

    private void loadAllData() throws FileNotFoundException, RemoteException, NotBoundException {
        SmenyController.getInstance().generateTableOverviewLeader();
    }

    private void initMyComponents() {

        contextMenu = new JPopupMenu();
        contextMenu.add("Přihlásit"); //TODO priradit akce               
        contextMenu.add("Odhlásit"); //TODO priradit akce - presun do prihlasenych                              
        contextMenu.add("Potvrdit obsazení"); //TODO priradit akce - presun do prihlasenych                              
        contextMenu.add("Žádost o zrušení"); //TODO priradit akce - presun do prihlasenych                                             
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
                    ///System.exit(1);
                    //Component comp = SwingUtilities.getDeepestComponentAt(me.getComponent(), me.getX(), me.getY()); 
                    //JTextComponent tc = (JTextComponent)comp;                
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
        try {
            loadAllData();
            jTableWorkShiftOverview.setModel(SmenyController.getInstance().getModelOverviewWorkShift());
        } catch (FileNotFoundException ex) {
            logError(ex);
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            logError(ex);
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            logError(ex);
            Logger.getLogger(OverviewEmployeeShiftForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            logError(ex);
        }
    }

    /**
     * Metoda cisti vsechny vstupni pole formulare.
     */
    @Override
    protected void clearFields() {
        //Validator.clearTextField(jTextFieldName);

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
        jTextFieldDateRange = new javax.swing.JTextField();
        jLabelWeek = new javax.swing.JLabel();
        jTextFieldWeek = new javax.swing.JTextField();
        jComboBoxFilter = new javax.swing.JComboBox();
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

        jButtonNextWeek.setText(">");
        jButtonNextWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextWeekActionPerformed(evt);
            }
        });

        jTextFieldDateRange.setFont(new java.awt.Font("Calibri", 0, 14));
        jTextFieldDateRange.setText("9.5.2011-14.5.2011");
        jTextFieldDateRange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDateRangeActionPerformed(evt);
            }
        });

        jLabelWeek.setFont(new java.awt.Font("Calibri", 1, 14));
        jLabelWeek.setText("Týden:");

        jTextFieldWeek.setFont(new java.awt.Font("Calibri", 0, 14));
        jTextFieldWeek.setText("14");

        jComboBoxFilter.setFont(new java.awt.Font("Calibri", 0, 14));
        jComboBoxFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vše", "Moje role", "Přihlášené" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBoxFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(jLabelWeek)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldDateRange, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBoxFilter, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonPreviousWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonNextWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldDateRange, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelWeek)))
                .addContainerGap())
        );

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
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
                .addComponent(jLabelOverviewWorkShift)
                .addContainerGap(812, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(131, 131, 131))
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldDateRangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDateRangeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDateRangeActionPerformed

    private void jButtonNextWeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextWeekActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonNextWeekActionPerformed

private void jButtonLoginUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginUserActionPerformed

    int rowNumber = this.jTableWorkShiftOverview.getSelectedRow(); //bude slouzit jako index pro datovou strukturu ve ktere bude ulozeno id smeny                
    if (rowNumber > -1) {
        try {
            int workShiftId = SmenyController.getInstance().getWorkShiftIdFromOverViewTable(rowNumber);
            SmenyController.getInstance().saveCurrentUserToWorkShift(workShiftId);
            refresh();
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
            refresh();
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
            refresh();
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

private void jButtonRequestCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRequestCancelActionPerformed
int rowNumber = this.jTableWorkShiftOverview.getSelectedRow(); //bude slouzit jako index pro datovou strukturu ve ktere bude ulozeno id smeny        
    String message = "Zažádáno o zrušení";
    if (rowNumber > -1) {
        try {
            int idWorkshift = SmenyController.getInstance().getWorkShiftIdFromOverViewTable(rowNumber);
            SmenyController.getInstance().updateOccupationMessageUser(idWorkshift, message);
            refresh();
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
    private javax.swing.JTextField jTextFieldDateRange;
    private javax.swing.JTextField jTextFieldWeek;
    // End of variables declaration//GEN-END:variables
}
