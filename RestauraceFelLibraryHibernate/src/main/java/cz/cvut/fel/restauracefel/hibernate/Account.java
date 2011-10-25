package cz.cvut.fel.restauracefel.hibernate;

import java.io.Serializable;

/**
 * @author Tomas Hnizdil
 */
public class Account implements Serializable {

    private static final long serialVersionUID = -345056626508032692L;
    private Integer accountId;
    private String name;
    private Table table;
    private User user;
    private AccountStatusType accountStatusType;
    private AccountCategory accountCategory;
    private DiscountType discountType;
    private String note;
    private int isDeleted;

    public Account() {
    }

    public Account(String name, AccountStatusType accountStatusType, AccountCategory accountCategory, Table table, User user, DiscountType discountType, String note, int isDeleted) {
        this.name = name;
        this.accountStatusType = accountStatusType;
        this.accountCategory = accountCategory;
        this.table = table;
        this.user = user;
        this.discountType = discountType;
        this.note = note;
        this.isDeleted = isDeleted;
    }

    public Integer getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountStatusType getAccountStatusType() {
        return this.accountStatusType;
    }

    public void setAccountStatusType(AccountStatusType accountstatustype) {
        this.accountStatusType = accountstatustype;
    }

    public AccountCategory getAccountCategory() {
        return this.accountCategory;
    }

    public void setAccountCategory(AccountCategory accountCategory) {
        this.accountCategory = accountCategory;
    }

    public Table getTable() {
        return this.table;
    }

    public void setTable(Table table) {
        this.table = table;
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
}





