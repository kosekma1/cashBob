package cz.cvut.fel.restauracefel.pokladna.pokladna_main;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel;

/**
 * Hlavni trida modulu Pokladna.
 *
 * @author Tomas Hnizdil
 */
public class Main {

    public static void main(final String[] args) {

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
                                //mainFrame.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
               // mainFrame.setCursor(invisible);
            }
        });

    }
}
