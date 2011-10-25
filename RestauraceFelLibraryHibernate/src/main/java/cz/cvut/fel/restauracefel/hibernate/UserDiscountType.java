package cz.cvut.fel.restauracefel.hibernate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Tomas Hnizdil
 */
public class UserDiscountType implements Serializable {

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

}


