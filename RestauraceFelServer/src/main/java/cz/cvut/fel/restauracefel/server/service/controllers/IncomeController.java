package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Income;
import cz.cvut.fel.restauracefel.hibernate.Material;
import cz.cvut.fel.restauracefel.hibernate.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jarda
 */

public class IncomeController {

    private static IncomeController instance = null;
    private Income income = null;

    private IncomeController(){
    }

    public static IncomeController getInstance(){
        if (instance == null){
            instance = new IncomeController();
        }
        return instance;
    }

    //vytvari zaznam o prijmu dane suroviny na sklad
    public boolean createIncome(Date date, int materialId, double quantity, double price, int userId, String note){        
        income = new Income();
        Material mat = Material.findById(materialId);
        if (mat == null){
            return false;
        }
        mat.setCurrentQuantity(mat.getCurrentQuantity() + quantity);
        mat.update();
        income.setDate(date);
        income.setMaterial(mat);
        income.setQuantity(quantity);
        income.setPrice(price);
        User u = User.findById(userId);
        if (u == null){
            return false;
        }
        income.setUser(u);
        income.setNote(note);
        income.setIsDeleted(0);
        income.create();
        return true;
    }

    //maze zaznam o prijmu suroviny na sklad, dle daneho Id, ze seznamu prijmu
    public boolean deleteIncome(int incomeId){
        income = Income.findById(incomeId);
        if (income == null){
            return false;
        }
        Material mat = income.getMaterial();
        mat.setCurrentQuantity(mat.getCurrentQuantity() - income.getQuantity());
        mat.update();
        //income.delete();
        income.setIsDeleted(1);
        return true;
    }

    //updatuje zaznam o prijmu suroviny na sklad, dle daneho Id, ze seznamu prijmu
    public boolean updateIncome(int incomeId, Date date, int materialId, double quantity, double price, int userId, String note){
        income = Income.findById(incomeId);
        if (income == null){
            return false;
        }
        Material mat = Material.findById(materialId);
        if (mat == null){
            return false;
        }
        if (mat == income.getMaterial()){
            mat.setCurrentQuantity(mat.getCurrentQuantity() - income.getQuantity() + quantity);
        } else {
            Material mOld = income.getMaterial();
            mOld.setCurrentQuantity(mOld.getCurrentQuantity() - income.getQuantity());
            mOld.update();
            mat.setCurrentQuantity(mat.getCurrentQuantity() + quantity);
        }
        mat.update();
        income.setDate(date);
        income.setMaterial(mat);
        income.setQuantity(quantity);
        income.setPrice(price);
        User u = User.findById(userId);
        if (u == null){
            return false;
        }
        income.setUser(u);
        income.setNote(note);
        income.update();
        return true;
    }

    //vraci pole Objectu reprezentujici zaznamy o prijmu surovin na sklad
    public Object [][] getIncomes(){        
        List<Income> list = Income.findAll();
        if (list == null){            
            return null;
        }                    
        Object [][] array = new Object [list.size()][8];
        Iterator it = list.iterator();        
        int i = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        while (it.hasNext()){
            income = (Income)it.next();
            array [i][0] = income.getIncomeId();
            array [i][1] = sdf.format(income.getDate());
            array [i][2] = income.getMaterial().getName();
            array [i][3] = income.getQuantity();
            array [i][4] = income.getMaterial().getUnitType().getAbbreviation();
            array [i][5] = income.getPrice();
            if (income.getUser().getIsDeleted() == 1){
                array [i][6] = "Uživatel smazán";
            } else {
                array [i][6] = income.getUser().getUsername();
            }
            
            array [i][7] = income.getNote();
            i++;
        }
        return array;
    }

    //navraci zaznam o prijmu na sklad dle Id
    public Income getIncomeByID(int incomeId){
        return Income.findById(incomeId);
    }
    
}
