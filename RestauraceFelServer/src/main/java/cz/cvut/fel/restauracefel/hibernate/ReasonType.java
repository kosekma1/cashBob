package cz.cvut.fel.restauracefel.hibernate;
// Generated 16.3.2009 21:36:54 by Hibernate Tools 3.2.1.GA

import java.util.List;

/**
 * ReasonType generated by hbm2java
 */
public class ReasonType extends DBEntity {

    private static final long serialVersionUID = 7185880464263209602L;
    
    private Integer reasonTypeId;
    private String name;
    private String note;
    private int isDeleted;

    public ReasonType() {
    }

    public ReasonType(String name, int isDeleted) {
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public ReasonType(String name, String note, int isDeleted) {
        this.name = name;
        this.note = note;
        this.isDeleted = isDeleted;
    }

    public Integer getReasonTypeId() {
        return this.reasonTypeId;
    }

    public void setReasonTypeId(Integer reasonTypeId) {
        this.reasonTypeId = reasonTypeId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public static ReasonType findById(Integer id) {
        //return (ReasonType) findById("ReasonType", "reasonTypeId", id);
        return (ReasonType) findByIdNotDeleted("ReasonType", "reasonTypeId", id, "isDeleted", 0);
    }

    //vrati vsechny duvody odpisu, ktere nejsou oznaceny jako smazane
    public static List findAll(){
        return findAllNotDeleted("ReasonType", "isDeleted", 0);
    }

    public static ReasonType findByName(String name) {
        //return (ReasonType) findByStringName("ReasonType", "name", name);
        return (ReasonType) findByStringNameNotDeleted("ReasonType", "name", name, "isDeleted", 0);
    }
    
}
