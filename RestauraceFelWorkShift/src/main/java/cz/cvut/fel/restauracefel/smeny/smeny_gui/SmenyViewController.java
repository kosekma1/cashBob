package cz.cvut.fel.restauracefel.smeny.smeny_gui;

/**
 * Controller pro zobrazení hlavního okna modulu Směny.
 * @author Martin Kosek
 */
public class SmenyViewController {
    private static final SmenyViewController instance = new SmenyViewController(); 
    private MainFrame mainFrame = null; 
    
    private SmenyViewController() {
    
    } 
    
    public static SmenyViewController getInstance() {
        return instance;
    } 
    
    public void run() {
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
    
    public boolean isActive() {
        if (mainFrame == null) {
            return false;
        }
        return mainFrame.isVisible();
    }

    public int showConfirmDialogStandard(String text, String title) {
        return mainFrame.showConfirmDialogStandard(text, title);
    }
}
