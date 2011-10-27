package cz.cvut.fel.restauracefel.hibernate;
// Generated 16.3.2009 21:36:54 by Hibernate Tools 3.2.1.GA

import java.util.List;

/**
 * DiscountType generated by hbm2java
 */
public class DiscountType extends DBEntity {

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

    public void create() {
        create(this);
    }

    public void delete() {
        delete(this);
    }

    public void update() {
        update(this);
    }

    public static DiscountType findById(Integer id) {
        return (DiscountType) findByIdNotDeleted("DiscountType", "discountTypeId", id, "isDeleted", 0);
    }

    public static DiscountType findByName(String name) {
        return (DiscountType) findByStringNameNotDeleted("DiscountType", "name", name, "isDeleted", 0);
    }

    public static List findAll() {
        return findAllNotDeleted("DiscountType", "isDeleted", 0);
    }
}

