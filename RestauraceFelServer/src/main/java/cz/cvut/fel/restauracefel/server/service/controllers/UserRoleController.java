package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Role;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.hibernate.UserRole;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jarda
 */
public class UserRoleController {

    protected static UserRoleController instance = null;
	protected UserRole userRole = null;
    protected User user = null;
    protected Role role = null;

	private UserRoleController(){}

	public static UserRoleController getInstance(){
		if (instance == null){
            instance = new UserRoleController();
        }
        return instance;
	}

    //vytvari novy objekt tridy UserRole
	public boolean createUserRole(int userId, int roleId){
        userRole = UserRole.findByUserAndRole(userId, roleId);
		if (userRole == null){
			userRole = new UserRole();
            user = User.findById(userId);
            role = Role.findById(roleId);
            if (user == null || role == null){
                return false;
            }
			userRole.setUser(user);
			userRole.setRole(role);
			userRole.create();			
			return true;
		} else {			
			return false;
		}
	}

    //maze uzivatelskou roli dle daneho uzivatele a dane role
    public boolean deleteUserRole(int userId, int roleId){
        userRole = UserRole.findByUserAndRole(userId, roleId);
        if (userRole == null){
            return false;
        }
        //userRole.delete();
        userRole.setIsDeleted(1);
        return true;
    }

    public UserRole getUserRoleById(int userRoleId){
        return UserRole.findById(userRoleId);
    }

    //navraci uzivatelksou roli pro daneho uzivatele a danou roli
    public UserRole getUserRoleByUserAndRole(int userId, int roleId){
        return UserRole.findByUserAndRole(userId, roleId);
    }

	//pro daneho uzivatele vrati seznam jeho roli
	public List getUserRoleByUserId(int userId){
		return UserRole.findByUser(userId);
	}

    //navraci pole Id roli pro daneho uzivatele s danym userId
    public boolean [] getUserRoles(int userId){
        List<UserRole> list = UserRole.findByUser(userId);               
        boolean roles [] = {false, false, false};
        if (list == null || list.isEmpty()){
            return roles;
        }
        Iterator it = list.iterator();         
        int i = 0;
        while (it.hasNext()){
            int roleId = ((UserRole)it.next()).getRole().getRoleId();
            if (roleId == 1){
                roles [0] = true;
            } else if (roleId == 2){
                roles [1] = true;
            } else if (roleId == 3){
                roles [2] = true;
            }
            
            i++;
        }
        return roles;
    }

    public boolean isUserRole(int userId, int roleId){
        UserRole ur = UserRoleController.getInstance().getUserRoleByUserAndRole(userId, roleId);
        if (ur == null){
            return false;
        }
        return true;
    }
}
