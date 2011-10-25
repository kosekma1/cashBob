package cz.cvut.fel.restauracefel.hibernate;

import java.io.Serializable;

/**
 * @author Tomas Hnizdil
 */
public class Role implements Serializable, IRole {

    private static final long serialVersionUID = 3562373636307929417L;

    private Integer roleId;
    private String name;
    private int isDeleted;

    public Role() {
    }

    public Role(String name, int isDeleted) {
        this.name = name;
        this.isDeleted = isDeleted;
    }

    @Override
    public Integer getRoleId() {
        return this.roleId;
    }

    @Override
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

}


