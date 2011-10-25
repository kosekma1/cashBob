/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.manager.managerModel;

import cz.cvut.fel.restauracefel.hibernate.User;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author honza
 */
public class ManagerModelController {

    private static final ManagerModelController instance = new ManagerModelController( );

    private User loggedUser = null;
    private ArrayList< String > rights = null;

    private ManagerModelController( ) {
        rights = new ArrayList<String>( );
    }

    public static ManagerModelController getInstance( ) {
        return instance;
    }

    public User getLoggedUser( ) {
        return loggedUser;
    }

    public void setLoggedUser( User loggedUser ) {
        this.loggedUser = loggedUser;
    }

    public String[ ] getRights( ) {
        String [ ] temp = new String[ rights.size( ) ];
        return rights.toArray( temp );
    }

    public void setRights( String[ ] rights ) {
        this.rights.addAll( Arrays.asList( rights ) );
    }

    public boolean hasRights( String right ) {
        return rights.contains( right );
    }
}
