package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Table;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jarda
 */
public class TableController {
    
    private static TableController instance = null;
    private Table table = null;

    private TableController(){
    }

    public static TableController getInstance(){
        if (instance == null){
            instance = new TableController();
        }
        return instance;
    }

    public boolean createTable(int tableNumber, int numberOfPlaces){
        if (numberOfPlaces==0) return false;
        table = Table.findByTableNumer(tableNumber);
        if (table != null){
            return false;
        }
        table = new Table(tableNumber, numberOfPlaces, 0);
        table.create();
        return true;
    }

    public boolean deleteTable(int tableId){
        table = Table.findById(tableId);
        if (table == null){
            return false;
        }
        //table.delete();
        table.setIsDeleted(1);
        return true;
    }

    public boolean updateTable(int tableId, int tableNumber, int numberOfPlaces){
        table = Table.findById(tableId);
        if (table == null){
            return false;
        }
        Table t = Table.findByTableNumer(tableNumber);
        if (t != null && t != table){
                return false;
        }
        table.setTableNumber(tableNumber);
        table.setNumberOfPlaces(numberOfPlaces);
        table.update();
        return true;
    }

    public Table getTableById(int tableId){
        return Table.findById(tableId);
    }

    public Table getTableByTableNumber(int tableNumber){
        return Table.findByTableNumer(tableNumber);
    }

    public int [] getTableNumbers(){
        List<Table> list = Table.findAll();
        if (list == null || list.isEmpty()){
            return null;
        }
        int tableNumbers [] = new int [list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()){
            tableNumbers [i] = ((Table)it.next()).getTableNumber();
            i++;
        }
        return tableNumbers;
    }

    public String [] getTableNames(){
        List<Table> list = Table.findAll();
        if (list == null || list.isEmpty()){
            return null;
        }
        String tableNames [] = new String [list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()){
            //tableNames [i] = (((Table)it.next()).getTableId().toString());
            tableNames[i] = Integer.toString(((Table)it.next()).getTableNumber());
            i++;
        }
        return tableNames;
    }

    public Object [][] getTables(){
        List<Table> list = Table.findAll();
        if (list == null || list.isEmpty()){
            return null;
        }
        Object tables [][] = new Object [list.size()][3];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()){
            table = (Table) it.next();
            tables [i][0] = table.getTableId();
            tables [i][1] = table.getTableNumber();
            tables [i][2] = table.getNumberOfPlaces();
            i++;
        }
        return tables;
    }
    
}
