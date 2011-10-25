package cz.cvut.fel.restauracefel.hibernate;
// Generated 16.3.2009 21:36:54 by Hibernate Tools 3.2.1.GA

import java.io.Serializable;
import java.util.Date;

/**
 * Order generated by hbm2java
 */
public class Order implements Serializable {

    private Integer orderId;
    private int isPaid;
    private Date time;
    private Account account;
    private User user;
    private int isDeleted;

    public Order() {
    }

    public Order(int isPaid, Date time, int isDeleted) {
        this.isPaid = isPaid;
        this.time = time;
        this.isDeleted = isDeleted;
    }

    public Order(int isPaid, Date time, Account account, User user, int isDeleted) {
        this.isPaid = isPaid;
        this.time = time;
        this.account = account;
        this.user = user;
        this.isDeleted = isDeleted;
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public int getIsPaid() {
        return this.isPaid;
    }

    public void setIsPaid(int isPaid) {
        this.isPaid = isPaid;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

}


