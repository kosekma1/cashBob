package cz.cvut.fel.restauracefel.hibernate;
// Generated 16.3.2009 21:36:54 by Hibernate Tools 3.2.1.GA

import java.util.List;


/**
 * Table generated by hbm2java
 */
public class Table extends DBEntity {

    private static final long serialVersionUID = 2722674881805881779L;

    private Integer tableId;
    private int tableNumber;
    private int numberOfPlaces;
    private int isDeleted;

    public Table() {
    }

    public Table(int tableNumber, int numberOfPlaces, int isDeleted) {
        this.tableNumber = tableNumber;
        this.numberOfPlaces = numberOfPlaces;
        this.isDeleted = isDeleted;
    }

    public Integer getTableId() {
        return this.tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public int getTableNumber() {
        return this.tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getNumberOfPlaces() {
        return this.numberOfPlaces;
    }

    public void setNumberOfPlaces(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
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

    public void update(){
        update(this);
    }

    public static Table findById(Integer id) {
        //return (Table) findById("Table", "tableId", id);
        return (Table) findByIdNotDeleted("Table", "tableId", id, "isDeleted", 0);
    }

    public static Table findByTableNumer(int tableNumber){
        //return (Table) findById("Table", "tableNumber", tableNumber);
        return (Table) findByIdNotDeleted("Table", "tableNumber", tableNumber, "isDeleted", 0);
    }

    public static List findByNumberOfPlaces(int places){
        String query = "from Table ta where ta.numberOfPlaces = :places and ta.isDeleted = :id2";
        String[] paramNames = new String[] {"places", "id2"};
		String[] paramTypes = new String[] {"Integer", "Integer"};
		Integer[] paramValues = new Integer[] {places, 0};

        List res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty())
            return null;
		return res;
    }

    //vraci vsechny stoly, ktere nejsou oznaceny jako smazane
    public static List findAll(){
        return findAllNotDeleted("Table", "isDeleted", 0);
    }
}


