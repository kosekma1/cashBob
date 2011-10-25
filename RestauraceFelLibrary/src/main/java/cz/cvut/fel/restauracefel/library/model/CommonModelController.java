/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.library.model;

import cz.cvut.fel.restauracefel.hibernate.User;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Lukáš Viezán
 */
public class CommonModelController {

    private static final CommonModelController instance = new CommonModelController( );

    private User loggedUser = null;
    private ArrayList< String > rights = null;
    private boolean aditionalUserAccessable = false;

    private CommonModelController( ) {
        rights = new ArrayList<String>( );
    }

    public static CommonModelController getInstance( ) {
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

    public boolean isAditionalUserAccessable() {
        return aditionalUserAccessable;
    }

    public void setAditionalUserAccessable(boolean aditionalUserAccessable) {
        this.aditionalUserAccessable = aditionalUserAccessable;
    }

    
}
