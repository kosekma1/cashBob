package cz.cvut.fel.restauracefel.hibernate;

import java.io.Serializable;

/**
 * @author Tomas Hnizdil
 */
public class AccountCategory implements Serializable {

    private static final long serialVersionUID = -345056626509032598L;
    private Integer accountCategoryId;
    private String name;
    private String note;
    private int isDeleted;

    public AccountCategory() {
    }

    public AccountCategory(String name, int isDeleted) {
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public AccountCategory(String name, String note, int isDeleted) {
        this.name = name;
        this.note = note;
        this.isDeleted = isDeleted;
    }

    public Integer getAccountCategoryId() {
        return this.accountCategoryId;
    }

    public void setAccountCategoryId(Integer accountCategoryId) {
        this.accountCategoryId = accountCategoryId;
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
}


