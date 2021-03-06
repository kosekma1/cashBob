package cz.cvut.fel.restauracefel.storage.sklad_gui;

import cz.cvut.fel.restauracefel.library.service.LocalizationManager;
import cz.cvut.fel.restauracefel.storage.storageController.StorageController;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * Trida vytvarejici dialog pro nacteni caroveho kodu.
 *
 * @author Jarda
 */
public class BarcodeDialog extends javax.swing.JDialog {

    //Minimalni delka caroveho kodu
    private final int MIN_LEN = 5;
    //Maximalni delka caroveho kodu
    private final int MAX_LEN = 13;
    //Carovy kod
    private String barcode = null;

    //Rozsireni - metoda, ktera bude validovat nacteny carovy kod
    /**
     * Konstruktor tridy BarcodeDialog.
     *
     * @param parent instance tridy JFrame jenz vytvorila tento dialog
     * @param modal urcuje, zda bude dialog modalni
     */
    public BarcodeDialog(JFrame parent, boolean modal) {
        super(parent, modal);

        super.setTitle("Čárový kód");
        initComponents();

        LocalizationManager manager = LocalizationManager.getInstance();

        manager.localizeDialog(StorageController.DEFAULT_BUNDLE_BASE_NAME, "BarCodeDialog", this);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "BarCodeInformationLabel", jLabelInfoText);
        manager.localizeLabel(StorageController.DEFAULT_BUNDLE_BASE_NAME, "BarCodeLabel", jLabelBarcode);
        manager.localizeButton(StorageController.DEFAULT_BUNDLE_BASE_NAME, "ScanAgainButton", jButtonAgain);

        jButtonAgain.setVisible(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int y = (int) ((dim.getHeight() - 150) / 2);
        int x = (int) ((dim.getWidth() - 300) / 2);
        setLocation(x, y);
        setCloseOperation();
        readBardode();
    }

    /**
     * Metoda, ktera nacita carovy kod.
     */
    private void readBardode() {
        jLabelInfoText.setText("Načtěte čárový kód");
        jTextFieldBarcode.setEditable(true);
        jButtonAgain.setVisible(false);
        jTextFieldBarcode.addKeyListener(new KeyAdapter() {
            /*@Override
            public void keyTyped(KeyEvent e) {
            barcode = jTextFieldBarcode.getText();
            jTextFieldBarcode.setEditable(false);
            if (barcode.length() < MIN_LEN || barcode.length() > MAX_LEN){
            barcode = null;
            jTextFieldBarcode.setText("");
            jLabelInfoText.setText("Neplatný vstup");
            jButtonAgain.setVisible(true);
            } else {
            jLabelInfoText.setText("ENTER pro potvrzení");
            }
            }*/

            @Override
            public void keyPressed(KeyEvent e) {
                barcode = jTextFieldBarcode.getText();
                //System.out.println(barcode);
            }
        });
        jTextFieldBarcode.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (barcode != null) {
                    dispose();
                }
            }
        });
        jButtonAgain.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                readBardode();
            }
        });
    }

    /**
     * Prepisuje standartni close operaci. Pri zavreni okna dojde k zneplatneni
     * nacteneho caroveho kodu.
     */
    private void setCloseOperation() {
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                barcode = null;
            }
        });
    }

    /**
     * Metoda navracejici nacteny carovy kod.
     * @return Nacteny carovy kod
     */
    public String getBarcode() {
        return barcode;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        commReader1 = new cz.cvut.fel.restauracefel.sklad_service.CommReader();
        jTextFieldBarcode = new javax.swing.JTextField();
        jLabelBarcode = new javax.swing.JLabel();
        jLabelInfoText = new javax.swing.JLabel();
        jButtonAgain = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelBarcode.setText("Čárový kód");

        jLabelInfoText.setFont(new java.awt.Font("Tahoma", 1, 16));
        jLabelInfoText.setText("Načtěte čárový kód");

        jButtonAgain.setText("Načíst kód znova");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabelInfoText))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabelBarcode)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonAgain)
                            .addComponent(jTextFieldBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabelInfoText)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBarcode)
                    .addComponent(jTextFieldBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(jButtonAgain)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private cz.cvut.fel.restauracefel.sklad_service.CommReader commReader1;
    private javax.swing.JButton jButtonAgain;
    private javax.swing.JLabel jLabelBarcode;
    private javax.swing.JLabel jLabelInfoText;
    private javax.swing.JTextField jTextFieldBarcode;
    // End of variables declaration//GEN-END:variables
}
