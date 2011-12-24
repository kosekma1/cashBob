/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.smeny.smeny_main;

import javax.swing.JFrame;
import javax.swing.UIManager;
import org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel;

/**
 *
 * @author Martin
 */
public class Main {

    /**
     * Tato trida jiz nema vyuziti.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    UIManager.setLookAndFeel(new SubstanceOfficeSilver2007LookAndFeel());
                    UIManager.put("ScrollBar.width", 32);
                } catch (Exception e) {
                    System.out.println("Substance Raven Graphite failed to initialize");
                }
                //new NewApplication().setVisible(true);
                //Toolkit tk = Toolkit.getDefaultToolkit();
                //Cursor invisible = tk.createCustomCursor(tk.createImage(""), new Point(), null);

                //System.out.println(args[0]);
                try {

                    //MainFrame mainFrame = new MainFrame(ServiceFacade.getInstance().getUserById(1), null);
                    /* String[] rights = new String[]{"Tvorba nového typu směny", "Tvorba nového templatu",
                        "Tvorba plánu směn","Přehled směn - vedoucí","Přehled směn zaměstnanec"};
                    SmenyController.getInstance().run(new User(), rights); */
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                // mainFrame.setCursor(invisible);
            }
        });
    }
}
