package cz.cvut.fel.restauracefel.smeny.smeny_gui;

import cz.cvut.fel.restauracefel.hibernate.User;
import java.awt.Insets;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.library.service.EmptyListException;
//import cz.cvut.fel.restauracefel.pokladna_service.ServiceFacade;
import cz.cvut.fel.restauracefel.library.service.Validator;

/**
 * Trida reprezentujici formular, ktery zobrazi informace o ovladani aplikace.
 * Neni implementovan.
 *  
 * @author Martin Kosek
 */
public class InformationForm extends AbstractForm {

    /*private ChooseTableDialog chooseTableDialog = null;
    private ChoosePersonDialog choosePersonDialog = null;
    private ChooseDiscountTypeDialog chooseDiscountTypeDialog = null;
    private ChooseAccountCategoryDialog chooseAccountCategoryDialog = null;*/
    private StatusBar statusBar = null;
    private MainFrame parent = null;
    private Point point = new Point(550, 210);

    /**
     * Konstruktor tridy CreateShiftForm.
     *
     * @param parent
     * @param bar
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    public InformationForm(MainFrame parent, StatusBar bar) throws FileNotFoundException, NotBoundException, RemoteException {
        this.parent = parent;
        this.statusBar = bar;
        initComponents();
        refresh();
        clearFields();
    }

    /**
     * Metoda prenastavuje statusBar.
     */
    @Override
    protected void refresh() {
        statusBar.setMessage("Tento formulář slouží k vytváření nového účtu.");
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
            CreateOrderForm createOrderForm = new CreateOrderForm(parent, statusBar, accountId, MainFrame.loggedUser.getUserId());
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

        jLabelTitle = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBox.light"));
        setPreferredSize(new java.awt.Dimension(948, 577));

        jLabelTitle.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setFont(new java.awt.Font("Tahoma", 0, 18));
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("Informace");
        jLabelTitle.setOpaque(true);

        jLabel5.setFont(new java.awt.Font("Calibri", 1, 22));
        jLabel5.setText("Návod ");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Testovací text.");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 990, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(411, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(181, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

}
