package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Right;
import cz.cvut.fel.restauracefel.hibernate.RoleRight;
import cz.cvut.fel.restauracefel.hibernate.UserRole;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Tomas Hnizdil
 */
public class RightController {

    protected static RightController instance = null;
    protected Right right = null;

    private RightController() {
    }

    public static RightController getInstance() {
        if (instance == null){
            instance = new RightController();
        }
        return instance;
    }

    //vraci List vsech uzivatelskych prav
    public List getAllRights() {
        return Right.findAll();
    }

    //vraci pole Stringu obsahujici jmena vsech roli
    public String[] getRightNames() {
        List<Right> list = getAllRights();
        if (list == null || list.isEmpty())
            return null;
        String rightsName[] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            rightsName[i] = ((Right) it.next()).getName();
            i++;
        }
        return rightsName;
    }

    //vraci roli dle jejiho Id
    public Right getRightByID(int id) {
        return Right.findById(id);
    }

    //vraci roli dle jejiho jmena
    public Right getRightByName(String name) {
        return Right.findByName(name);
    }

    public String[] getRightsByUser(int userId) {
        List<UserRole> roles = UserRole.findByUser(userId);
        if (roles == null) return null;
        List<String> res = new ArrayList<String>();
        for (int i=0; i<roles.size(); i++) {
            List<RoleRight> subList = RoleRight.findByRole(roles.get(i).getRole().getRoleId());
            for(int j=0; j<subList.size(); j++) {
                if (!res.contains(subList.get(j).getRight().getName())) res.add(subList.get(j).getRight().getName());
            }
        }
        String[] result = new String[res.size()];
        for (int i=0; i<result.length; i++) {
            result[i] = res.get(i);
        }
        return result;
    }
}
