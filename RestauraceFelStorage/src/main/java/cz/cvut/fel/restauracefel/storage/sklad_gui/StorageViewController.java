/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.storage.sklad_gui;

/**
 *
 * @author Lukáš Viezán
 */
public class StorageViewController {

    private static final StorageViewController instance = new StorageViewController( );

    private MainFrame mainFrame = null;

    private StorageViewController( ) { }

    public static StorageViewController getInstance( ) {
        return instance;
    }

    public void run( ) {
        mainFrame = new MainFrame( );
        mainFrame.setVisible( true );
    }

    public boolean isActive( ) {
        if ( mainFrame == null ) {
            return false;
        }

        return mainFrame.isVisible( );
    }
}
