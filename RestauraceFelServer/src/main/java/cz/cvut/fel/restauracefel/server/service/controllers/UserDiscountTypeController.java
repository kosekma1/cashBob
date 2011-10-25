package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.DiscountType;
import cz.cvut.fel.restauracefel.hibernate.RoleDiscountType;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.hibernate.UserDiscountType;
import cz.cvut.fel.restauracefel.hibernate.UserRole;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Tomas Hnizdil
 */
public class UserDiscountTypeController {

    protected static UserDiscountTypeController instance = null;
    protected UserDiscountType userDiscountType = null;
    protected User user = null;
    protected DiscountType discountType = null;

    private UserDiscountTypeController() {
    }

    public static UserDiscountTypeController getInstance() {
        if (instance == null) {
            instance = new UserDiscountTypeController();
        }
        return instance;
    }

    //vytvari novy objekt tridy UserDiscountType
    public boolean createUserDiscountType(int userId, int discountTypeId) {
        userDiscountType = UserDiscountType.findByUserAndDiscountType(userId, discountTypeId);
        if (userDiscountType == null) {
            userDiscountType = new UserDiscountType();
            user = User.findById(userId);
            discountType = DiscountType.findById(discountTypeId);
            if (discountType == null || user == null) {
                return false;
            }
            userDiscountType.setUser(user);
            userDiscountType.setDiscountType(discountType);
            userDiscountType.create();
            return true;
        } else {
            return false;
        }
    }

    //maze uzivatelskou roli dle daneho uzivatele a dane role
    public boolean deleteUserDiscountType(int userId, int discountTypeId) {
        userDiscountType = UserDiscountType.findByUserAndDiscountType(userId, discountTypeId);
        if (userDiscountType == null) {
            return false;
        }
        userDiscountType.setIsDeleted(1);
        return true;
    }

    public UserDiscountType getUserDiscountTypeById(int userDiscountTypeId) {
        return UserDiscountType.findById(userDiscountTypeId);
    }

    //navraci uzivatelksou roli pro daneho uzivatele a danou roli
    public UserDiscountType getUserDiscountTypeByUserAndDiscountType(int userId, int discountTypeId) {
        return UserDiscountType.findByUserAndDiscountType(userId, discountTypeId);
    }

    //vraci pole Stringu obsahujici jmena vsech discountTypu podle uzivatele
    public String[] getDiscountTypeNamesByUser(int userId) {
        List<UserDiscountType> list = getUserDiscountTypesByUserId(0);
        if (list == null) list = new ArrayList<UserDiscountType>();
        if (userId>0) {
            List<UserDiscountType> userList = getUserDiscountTypesByUserId(userId);
            if (userList != null && !userList.isEmpty()) {
                for(int i=0; i<userList.size(); i++) {
                    if (!list.contains(userList.get(i))) list.add(userList.get(i));
                }
            }
        }
        List<RoleDiscountType> list2 = new ArrayList<RoleDiscountType>();
        List<UserRole> roles = UserRole.findByUser(userId);
        if (roles !=null && !roles.isEmpty()) {
            Iterator itRoles = roles.iterator();
            while (itRoles.hasNext()) {
                int roleId = ((UserRole)itRoles.next()).getRole().getRoleId();
                List <RoleDiscountType> tmp = RoleDiscountType.findByRole(roleId);
                if (tmp != null && !tmp.isEmpty()) list2.addAll(tmp);
            }
        }

        if ((list == null || list.isEmpty()) && (list2 == null || list2.isEmpty()))
            return null;
        List<String> names = new ArrayList<String>();
        for (int i=0; i<list.size(); i++) names.add(list.get(i).getDiscountType().getName());
        for (int j=0; j<list2.size(); j++) {
            String name = list2.get(j).getDiscountType().getName();
            if (!names.contains(name)) names.add(name);
        }

        String[] discountTypeNames = new String[names.size()];
        for (int i=0; i<discountTypeNames.length; i++) discountTypeNames[i] = names.get(i);
        
        return discountTypeNames;
    }

    //pro daneho uzivatele vrati seznam jeho roli
    public List getUserDiscountTypesByUserId(int userId) {
        return UserDiscountType.findByUser(userId);
    }
}
