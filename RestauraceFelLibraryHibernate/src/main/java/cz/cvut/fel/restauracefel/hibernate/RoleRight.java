package cz.cvut.fel.restauracefel.hibernate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Tomas Hnizdil
 */
public class RoleRight implements Serializable {

    private static final long serialVersionUID = -3485621218713955411L;
    
    private Integer roleRightId;
    private Role role;
    private Right right;
    private int isDeleted;

    public RoleRight() {
    }

    public RoleRight(Role role, Right right, int isDeleted) {
        this.role = role;
        this.right = right;
        this.isDeleted = isDeleted;
    }

    //tato metoda zde musi byt, a to z duvodu, aby mohla probehnout spravne serializace
    //zapis objektu do proudu bajtu
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeInt(roleRightId);

        Role r = new Role(role.getName(), role.getIsDeleted());
        r.setRoleId(role.getRoleId());
        stream.writeObject(r);

        stream.writeInt(isDeleted);
    }

    //tato metoda zde musi byt, a to z duvodu, aby mohla probehnout spravne serializace
    //cteni a rekonstrukce objektu z proudu bajtu
    private void readObject(ObjectInputStream stream) throws IOException {
        try {
            roleRightId = stream.readInt();
            role = (Role) stream.readObject();
            right = (Right) stream.readObject();
            isDeleted = stream.readInt();
        } catch (Exception e) {
        }
    }

    public Integer getRoleRightId() {
        return this.roleRightId;
    }

    public void setRoleRightId(Integer roleRightId) {
        this.roleRightId = roleRightId;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public Right getRight() {
        return this.right;
    }

    public void setRight(Right right) {
        this.right = right;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}


