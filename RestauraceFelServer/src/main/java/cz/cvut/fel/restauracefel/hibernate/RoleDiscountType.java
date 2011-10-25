package cz.cvut.fel.restauracefel.hibernate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @author Tomas Hnizdil
 */
public class RoleDiscountType extends DBEntity {

    private static final long serialVersionUID = -3485621218713955474L;
    
    private Integer roleDiscountTypeId;
    private Role role;
    private DiscountType discountType;
    private int isDeleted;

    public RoleDiscountType() {
    }

    public RoleDiscountType(Role role, DiscountType discountType, int isDeleted) {
        this.role = role;
        this.discountType = discountType;
        this.isDeleted = isDeleted;
    }

    //tato metoda zde musi byt, a to z duvodu, aby mohla probehnout spravne serializace
    //zapis objektu do proudu bajtu
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeInt(roleDiscountTypeId);

        Role r = new Role(role.getName(), role.getIsDeleted());
        r.setRoleId(role.getRoleId());
        stream.writeObject(r);

        stream.writeInt(isDeleted);
    }

    //tato metoda zde musi byt, a to z duvodu, aby mohla probehnout spravne serializace
    //cteni a rekonstrukce objektu z proudu bajtu
    private void readObject(ObjectInputStream stream) throws IOException {
        try {
            roleDiscountTypeId = stream.readInt();
            role = (Role) stream.readObject();
            discountType = (DiscountType) stream.readObject();
            isDeleted = stream.readInt();
        } catch (Exception e) {
        }
    }

    public Integer getRoleDiscountTypeId() {
        return this.roleDiscountTypeId;
    }

    public void setRoleDiscountTypeId(Integer roleDiscountTypeId) {
        this.roleDiscountTypeId = roleDiscountTypeId;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public DiscountType getDiscountType() {
        return this.discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    public void create() {
        create(this);
    }

    public void delete() {
        delete(this);
    }

    public void update() {
        update(this);
    }

    public static RoleDiscountType findById(Integer id) {
        //return (UserRole) findById("UserRole", "userRoleId", id);
        return (RoleDiscountType) findByIdNotDeleted("RoleDiscountType", "roleDiscountTypeId", id, "isDeleted", 0);
    }

    public static List findByRole(Integer roleId) {
        String query = "from RoleDiscountType rdt where rdt.role.roleId = :id1 and rdt.isDeleted = :id2";
        String[] paramNames = new String[]{"id1", "id2"};
        String[] paramTypes = new String[]{"Integer", "Integer"};
        Integer[] paramValues = new Integer[]{roleId, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty()) {
            return null;
        }
        return res;
    }

    public static RoleDiscountType findByRoleAndDiscountType(Integer roleId, Integer discountTypeId) {
        String query = "from RoleDiscountType rdt where rdt.role.roleId = :roleId and rdt.discountType.discountTypeId = :discountTypeId and rdt.isDeleted = :id3";
        String[] paramNames = new String[]{"roleId", "discountTypeId", "id3"};
        String[] paramTypes = new String[]{"Integer", "Integer", "Integer"};
        Integer[] paramValues = new Integer[]{roleId, discountTypeId, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty()) {
            return null;
        }
        return (RoleDiscountType) res.get(0);
    }
}


