package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.DiscountType;
import cz.cvut.fel.restauracefel.hibernate.Role;
import cz.cvut.fel.restauracefel.hibernate.RoleDiscountType;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Tomas Hnizdil
 */
public class RoleDiscountTypeController {

    protected static RoleDiscountTypeController instance = null;
    protected RoleDiscountType roleDiscountType = null;
    protected Role role = null;
    protected DiscountType discountType = null;

    private RoleDiscountTypeController() {
    }

    public static RoleDiscountTypeController getInstance() {
        if (instance == null) {
            instance = new RoleDiscountTypeController();
        }
        return instance;
    }

    //vytvari novy objekt tridy RoleDiscountType
    public boolean createRoleDiscountType(int roleId, int discountTypeId) {
        roleDiscountType = RoleDiscountType.findByRoleAndDiscountType(roleId, discountTypeId);
        if (roleDiscountType == null) {
            roleDiscountType = new RoleDiscountType();
            role = Role.findById(roleId);
            discountType = DiscountType.findById(discountTypeId);
            if (discountType == null || role == null) {
                return false;
            }
            roleDiscountType.setRole(role);
            roleDiscountType.setDiscountType(discountType);
            roleDiscountType.create();
            return true;
        } else {
            return false;
        }
    }

    //maze uzivatelskou roli dle daneho uzivatele a dane role
    public boolean deleteRoleDiscountType(int roleId, int discountTypeId) {
        roleDiscountType = RoleDiscountType.findByRoleAndDiscountType(roleId, discountTypeId);
        if (roleDiscountType == null) {
            return false;
        }
        //userRole.delete();
        roleDiscountType.setIsDeleted(1);
        return true;
    }

    public RoleDiscountType getRoleDiscountTypeById(int roleDiscountTypeId) {
        return RoleDiscountType.findById(roleDiscountTypeId);
    }

    //navraci uzivatelksou roli pro daneho uzivatele a danou roli
    public RoleDiscountType getRoleDiscountTypeByRoleAndDiscountType(int roleId, int discountTypeId) {
        return RoleDiscountType.findByRoleAndDiscountType(roleId, discountTypeId);
    }

    //vraci pole Stringu obsahujici jmena vsech discountTypu podle uzivatelske role
    public String[] getDiscountTypeNamesByRole(int roleId) {
        List<RoleDiscountType> list = getRoleDiscountTypesByRoleId(roleId);
        if (list == null || list.isEmpty())
            return null;
        String discountTypesName[] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            discountTypesName[i] = ((RoleDiscountType) it.next()).getDiscountType().getName();
            i++;
        }
        return discountTypesName;
    }

    //pro daneho uzivatele vrati seznam jeho roli
    public List getRoleDiscountTypesByRoleId(int roleId) {
        return RoleDiscountType.findByRole(roleId);
    }
}
