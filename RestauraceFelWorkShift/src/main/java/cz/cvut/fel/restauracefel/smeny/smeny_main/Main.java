/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.smeny.smeny_main;

import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.smeny.SmenyController.SmenyController;
import cz.cvut.fel.restauracefel.smeny.smeny_gui.MainFrame;
import cz.cvut.fel.restauracefel.smeny.smeny_gui.SmenyViewController;
import javax.swing.JFrame;
import javax.swing.UIManager;
import org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel;
import cz.cvut.fel.restauracefel.smeny_service.ServiceFacade;

/**
 *
 * @author Martin
 */
public class Main {

    /**
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
