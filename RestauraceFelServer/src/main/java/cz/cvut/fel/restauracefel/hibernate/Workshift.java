package cz.cvut.fel.restauracefel.hibernate;
// Generated 17.7.2011 22:33:15 by Hibernate Tools 3.2.1.GA

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 * Workshift generated by hbm2java
 */
public class Workshift extends DBEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private int idWorkshift;
    private int idTypeWorkshift;
    private Integer idUser;
    private Byte leaderSubmit;
    private String userSubmit;
    private String cancelled;
    private Integer idTemplate;
    private Date dateShift;
    private int status;
    private int isDeleted;

    public Workshift() {
    }

    public Workshift(int idWorkshift, int idTypeWorkshift, Date dateShift, int status) {
        this.idWorkshift = idWorkshift;
        this.idTypeWorkshift = idTypeWorkshift;
        this.dateShift = dateShift;
        this.status = status;
    }

    public Workshift(int idWorkshift, int idTypeWorkshift, Integer idUser, Byte leaderSubmit, String userSubmit, String cancelled, int idTemplate, Date dateShift, int status, int isDeleted) {
        this.idWorkshift = idWorkshift;
        this.idTypeWorkshift = idTypeWorkshift;
        this.idUser = idUser;
        this.leaderSubmit = leaderSubmit;
        this.userSubmit = userSubmit;
        this.cancelled = cancelled;
        this.idTemplate = idTemplate;
        this.dateShift = dateShift;
        this.status = status;
        this.isDeleted = isDeleted;
    }

    public int getIdWorkshift() {
        return this.idWorkshift;
    }

    public void setIdWorkshift(int idWorkshift) {
        this.idWorkshift = idWorkshift;
    }

    public int getIdTypeWorkshift() {
        return this.idTypeWorkshift;
    }

    public void setIdTypeWorkshift(int idTypeWorkshift) {
        this.idTypeWorkshift = idTypeWorkshift;
    }

    public Integer getIdUser() {
        return this.idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Byte getLeaderSubmit() {
        return this.leaderSubmit;
    }

    public void setLeaderSubmit(Byte leaderSubmit) {
        this.leaderSubmit = leaderSubmit;
    }

    public String getUserSubmit() {
        return this.userSubmit;
    }

    public void setUserSubmit(String userSubmit) {
        this.userSubmit = userSubmit;
    }

    public String getCancelled() {
        return this.cancelled;
    }

    public void setCancelled(String cancelled) {
        this.cancelled = cancelled;
    }

    public Integer getIdTemplate() {
        return this.idTemplate;
    }

    public void setIdTemplate(Integer idTemplate) {
        this.idTemplate = idTemplate;
    }

    public Date getDateShift() {
        return this.dateShift;
    }

    public void setDateShift(Date dateShift) {
        this.dateShift = dateShift;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public static Workshift findByName(String name) {
        return (Workshift) findByStringNameNotDeleted("Workshift", "name", name, "isDeleted", 0);

    }

    public static Workshift findByIdNotDeleted(int id) {
        return (Workshift) findByIdNotDeleted("Workshift", "idWorkshift", id, "isDeleted", 0);
    }

    /**
     * Return all workshifts from specified date.
     * @param dateFrom (only date such 2011-12-21)
     * @return 
     */
    public static List getAllActiveWorkShifts(Date dateFrom) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //make right format for comparing        
        String parameterValue = sdf.format(dateFrom);

        Transaction tx = null;
        String className = "Workshift";
        String parameterName = "dateShift";

        try {
            String hibernateQuery = "from " + className + " w where w."
                    + parameterName + " >= '" + parameterValue + "'";//apostrophes near parameterValue are necessary
            tx = session.beginTransaction();
            Query query = session.createQuery(hibernateQuery);
            List result = query.list();

            tx.commit();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            return null;
        }
    }

    public void create() {
        create(this);
    }
}