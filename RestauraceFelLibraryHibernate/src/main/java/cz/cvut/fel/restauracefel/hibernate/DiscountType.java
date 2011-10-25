package cz.cvut.fel.restauracefel.hibernate;

import java.io.Serializable;

/**
 * @author Tomas Hnizdil
 */
public class DiscountType implements Serializable {

     private static final long serialVersionUID = 4753600958074874267L;

     private Integer discountTypeId;
     private String name;
     private int isDeleted;

    public DiscountType() {
    }

	
    public DiscountType(String name, int isDeleted) {
        this.name = name;
        this.isDeleted = isDeleted;
    }
    
    public Integer getDiscountTypeId() {
        return this.discountTypeId;
    }
    
    public void setDiscountTypeId(Integer discountTypeId) {
        this.discountTypeId = discountTypeId;
    }
    public String getName() {
        return this.name;
    }
    
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


