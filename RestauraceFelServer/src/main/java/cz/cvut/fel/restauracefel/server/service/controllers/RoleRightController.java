package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Right;
import cz.cvut.fel.restauracefel.hibernate.Role;
import cz.cvut.fel.restauracefel.hibernate.RoleRight;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Tomas Hnizdil
 */
public class RoleRightController {

    protected static RoleRightController instance = null;
    protected RoleRight roleRight = null;
    protected Role role = null;
    protected Right right = null;

    private RoleRightController() {
    }

    public static RoleRightController getInstance() {
        if (instance == null) {
            instance = new RoleRightController();
        }
        return instance;
    }

    //vytvari novy objekt tridy RoleRight
    public boolean createRoleRight(int roleId, int rightId) {
        roleRight = RoleRight.findByRoleAndRight(roleId, rightId);
        if (roleRight == null) {
            roleRight = new RoleRight();
            role = Role.findById(roleId);
            right = Right.findById(rightId);
            if (right == null || role == null) {
                return false;
            }
            roleRight.setRole(role);
            roleRight.setRight(right);
            roleRight.create();
            return true;
        } else {
            return false;
        }
    }

    //maze uzivatelskou roli dle daneho uzivatele a dane role
    public boolean deleteRoleRight(int roleId, int rightId) {
        roleRight = RoleRight.findByRoleAndRight(roleId, rightId);
        if (roleRight == null) {
            return false;
        }
        roleRight.setIsDeleted(1);
        return true;
    }

    public RoleRight getRoleRightById(int roleRightId) {
        return RoleRight.findById(roleRightId);
    }

    //navraci uzivatelksou roli pro daneho uzivatele a danou roli
    public RoleRight getRoleRightByRoleAndRight(int roleId, int rightId) {
        return RoleRight.findByRoleAndRight(roleId, rightId);
    }

    //vraci pole Stringu obsahujici jmena vsech discountTypu podle uzivatelske role
    public String[] getRightNamesByRole(int roleId) {
        List<RoleRight> list = getRoleRightsByRoleId(roleId);
        if (list == null || list.isEmpty())
            return null;
        String rightsName[] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            rightsName[i] = ((RoleRight) it.next()).getRight().getName();
            i++;
        }
        return rightsName;
    }

    //pro daneho uzivatele vrati seznam jeho roli
    public List getRoleRightsByRoleId(int roleId) {
        return RoleRight.findByRole(roleId);
    }
}
