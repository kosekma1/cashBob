package cz.cvut.fel.restauracefel.hibernate;

import java.io.Serializable;

/**
 * @author Tomas Hnizdil
 */
public class Right implements Serializable {

    private static final long serialVersionUID = 3562373636307929489L;
    
    private Integer rightId;
    private String name;
    private int isDeleted;

    public Right() {
    }

    public Right(String name, int isDeleted) {
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public Integer getRightId() {
        return this.rightId;
    }

    public void setRightId(Integer rightId) {
        this.rightId = rightId;
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


