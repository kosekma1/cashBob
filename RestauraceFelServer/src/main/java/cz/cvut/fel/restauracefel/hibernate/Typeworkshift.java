package cz.cvut.fel.restauracefel.hibernate;
// Generated 17.7.2011 22:33:15 by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.List;

/**
 * Typeworkshift generated by hbm2java
 */
public class Typeworkshift extends DBEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private int idTypeWorkshift;
    private String name;
    private Date fromTime;
    private Date toTime;
    private int status;
    private int idWorkshiftRole;
    private int isDeleted;

    public Typeworkshift() {
    }

    public Typeworkshift(int idTypeWorkshift, String name, Date fromTime, Date toTime, int status, int idWorkshiftRole, int isDeleted) {
        this.idTypeWorkshift = idTypeWorkshift;
        this.name = name;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.status = status;
        this.idWorkshiftRole = idWorkshiftRole;
        this.isDeleted = isDeleted;
    }

    public int getIdTypeWorkshift() {
        return this.idTypeWorkshift;
    }

    public void setIdTypeWorkshift(int idTypeWorkshift) {
        this.idTypeWorkshift = idTypeWorkshift;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFromTime() {
        return this.fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return this.toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdWorkshiftRole() {
        return this.idWorkshiftRole;
    }

    public void setIdWorkshiftRole(int idWorkshiftRole) {
        this.idWorkshiftRole = idWorkshiftRole;
    }

    /**
     * @return the isDeleted
     */
    public int getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public static List findAll() {
        return findAllNotDeleted("Typeworkshift", "isDeleted", 0);
    }

    public static Typeworkshift findByName(String name) {
        return (Typeworkshift) findByStringNameNotDeleted("Typeworkshift", "name", name, "isDeleted", 0);

    }

    public void create() {
        create(this);
    }
    
    public void delete() {
        delete(this);
    }
}
