package cz.cvut.fel.restauracefel.hibernate;
// Generated 17.7.2011 22:33:15 by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * Requestcancelled generated by hbm2java
 */
public class RequestCancelled extends DBEntity implements java.io.Serializable {

     private static final long serialVersionUID = 1L;
     private int idRequestCancelled;
     private int result;
     private int idWorkshift;
     private Date dateCancelled;
     private String reason;

    public RequestCancelled() {
    }

	
    public RequestCancelled(int idRequestCancelled, int result, int idWorkshift) {
        this.idRequestCancelled = idRequestCancelled;
        this.result = result;
        this.idWorkshift = idWorkshift;
    }
    public RequestCancelled(int idRequestCancelled, int result, int idWorkshift, Date dateCancelled, String reason) {
       this.idRequestCancelled = idRequestCancelled;
       this.result = result;
       this.idWorkshift = idWorkshift;
       this.dateCancelled = dateCancelled;
       this.reason = reason;
    }
   
    public int getIdRequestCancelled() {
        return this.idRequestCancelled;
    }
    
    public void setIdRequestCancelled(int idRequestCancelled) {
        this.idRequestCancelled = idRequestCancelled;
    }
    public int getResult() {
        return this.result;
    }
    
    public void setResult(int result) {
        this.result = result;
    }
    public int getIdWorkshift() {
        return this.idWorkshift;
    }
    
    public void setIdWorkshift(int idWorkshift) {
        this.idWorkshift = idWorkshift;
    }
    public Date getDateCancelled() {
        return this.dateCancelled;
    }
    
    public void setDateCancelled(Date dateCancelled) {
        this.dateCancelled = dateCancelled;
    }
    public String getReason() {
        return this.reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }




}


