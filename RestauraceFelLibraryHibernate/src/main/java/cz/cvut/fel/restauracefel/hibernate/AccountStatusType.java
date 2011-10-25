package cz.cvut.fel.restauracefel.hibernate;

import java.io.Serializable;


/**
 * @author Tomas Hnizdil
 */
public class AccountStatusType implements Serializable {

    private static final long serialVersionUID = -345056626509032698L;
    private Integer accountStatusTypeId;
    private String name;
    private String note;
    private int isDeleted;

    public AccountStatusType() {
    }

    public AccountStatusType(String name, int isDeleted) {
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public AccountStatusType(String name, String note, int isDeleted) {
        this.name = name;
        this.note = note;
        this.isDeleted = isDeleted;
    }

    public Integer getAccountStatusTypeId() {
        return this.accountStatusTypeId;
    }

    public void setAccountStatusTypeId(Integer accountStatusTypeId) {
        this.accountStatusTypeId = accountStatusTypeId;
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