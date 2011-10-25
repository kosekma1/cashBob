package cz.cvut.fel.restauracefel.hibernate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @author Tomas Hnizdil
 */
public class UserDiscountType extends DBEntity {

    private static final long serialVersionUID = -3485621218713955422L;
    
    private Integer userDiscountTypeId;
    private User user;
    private DiscountType discountType;
    private int isDeleted;

    public UserDiscountType() {
    }

    public UserDiscountType(User user, DiscountType discountType, int isDeleted) {
        this.user = user;
        this.discountType = discountType;
        this.isDeleted = isDeleted;
    }

    //tato metoda zde musi byt, a to z duvodu, aby mohla probehnout spravne serializace
    //zapis objektu do proudu bajtu
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeInt(userDiscountTypeId);

        //User u = new User(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getIsDeleted());
        //u.setUserId(user.getUserId());
        //stream.writeObject(u);

        stream.writeInt(isDeleted);
    }

    //tato metoda zde musi byt, a to z duvodu, aby mohla probehnout spravne serializace
    //cteni a rekonstrukce objektu z proudu bajtu
    private void readObject(ObjectInputStream stream) throws IOException {
        try {
            userDiscountTypeId = stream.readInt();
            user = (User) stream.readObject();
            discountType = (DiscountType) stream.readObject();
            isDeleted = stream.readInt();
        } catch (Exception e) {
        }
    }

    public Integer getUserDiscountTypeId() {
        return this.userDiscountTypeId;
    }

    public void setUserDiscountTypeId(Integer userDiscountTypeId) {
        this.userDiscountTypeId = userDiscountTypeId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public static UserDiscountType findById(Integer id) {
        return (UserDiscountType) findByIdNotDeleted("UserDiscountType", "userDiscountTypeId", id, "isDeleted", 0);
    }

    public static List findByUser(Integer userId) {
        String query = "from UserDiscountType udt where udt.user.userId = :id1 and udt.isDeleted = :id2";
        String[] paramNames = new String[]{"id1", "id2"};
        String[] paramTypes = new String[]{"Integer", "Integer"};
        Integer[] paramValues = new Integer[]{userId, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty()) {
            return null;
        }
        return res;
    }

    public static UserDiscountType findByUserAndDiscountType(Integer userId, Integer discountTypeId) {
        String query = "from UserDiscountType udt where udt.user.userId = :userId and udt.discountType.discountTypeId = :discountTypeId and udt.isDeleted = :id3";
        String[] paramNames = new String[]{"userId", "discountTypeId", "id3"};
        String[] paramTypes = new String[]{"Integer", "Integer", "Integer"};
        Integer[] paramValues = new Integer[]{userId, discountTypeId, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty()) {
            return null;
        }
        return (UserDiscountType) res.get(0);
    }
}


