package cz.cvut.fel.restauracefel.hibernate;

import java.util.List;

/**
 * @author Tomas Hnizdil
 */
public class Right extends DBEntity {

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
    
    public void create() {
        create(this);
    }

    public void delete() {
        delete(this);
    }

    public void update() {
        update(this);
    }

    public static Right findById(Integer id) {
        return (Right) findByIdNotDeleted("Right", "rightId", id, "isDeleted", 0);
    }

    //vraci vsechny role, ktere nejsou oznaceny jako smazane
    public static List findAll(){
        return findAllNotDeleted("Right", "isDeleted", 0);
    }

    public static Right findByName(String name) {
        return (Right) findByStringNameNotDeleted("Right", "name", name, "isDeleted", 0);
    }
}


