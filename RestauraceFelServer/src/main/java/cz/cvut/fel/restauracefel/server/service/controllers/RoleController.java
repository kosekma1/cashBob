package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Role;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jarda
 */
public class RoleController {

    protected static RoleController instance = null;
    protected Role role = null;

    private RoleController() {
    }

    public static RoleController getInstance() {
        if (instance == null){
            instance = new RoleController();
        }
        return instance;
    }

    //vraci List vsech uzivatelskych roli
    public List getAllRoles() {
        List allRoles = Role.findAll();
        return allRoles;
    }

    //vraci pole Stringu obsahujici jmena vsech roli
    public String[] getRoleNames() {
        List<Role> list = getAllRoles();
        if (list == null || list.isEmpty())
            return null;
        String rolesName[] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            rolesName[i] = ((Role) it.next()).getName();
            i++;
        }
        return rolesName;
    }

    //vraci roli dle jejiho Id
    public Role getRoleByID(int id) {
        return Role.findById(id);
    }

    //vraci roli dle jejiho jmena
    public Role getRoleByName(String name) {
        return Role.findByName(name);
    }
}
