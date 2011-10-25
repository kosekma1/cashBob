package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Expenditure;
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
public class ExpenditureController {

    private static ExpenditureController instance = null;
    private Expenditure expenditure = null;


    private ExpenditureController(){
    }

    public static ExpenditureController getInstance(){
        if (instance == null){
            instance = new ExpenditureController();
        }
        return instance;
    }

    //vytvari novy objekt tridy Expenditure
    public boolean createExpenditure(Date date, int materialId, double quantity, int userId, String note){
        expenditure = new Expenditure();
        Material mat = Material.findById(materialId);
        if (mat == null){
            return false;
        }
        if (mat.getCurrentQuantity() < quantity){
            return false;
        }
        mat.setCurrentQuantity(mat.getCurrentQuantity() - quantity);
        mat.update();
        expenditure.setDate(date);
        expenditure.setMaterial(mat);
        expenditure.setQuantity(quantity);
        User user = User.findById(userId);
        if (user == null){
            return false;
        }
        expenditure.setUser(user);
        expenditure.setNote(note);
        expenditure.setIsDeleted(0);
        expenditure.create();
        return true;
    }

    //aktualizuje objekt tridy Expenditure s danym Id
    public boolean updateExpenditure(int expenditureId, Date date, int materialId, double quantity, int userId, String note){
        expenditure = Expenditure.findById(expenditureId);
        if (expenditure == null){
            return false;
        }
        Material mat = Material.findById(materialId);
        if (mat == null || mat.getCurrentQuantity() < quantity){
            return false;
        }
        if (mat == expenditure.getMaterial()){
            mat.setCurrentQuantity(mat.getCurrentQuantity() + expenditure.getQuantity() - quantity);
        } else {
            Material mOld = expenditure.getMaterial();
            mOld.setCurrentQuantity(mOld.getCurrentQuantity() + expenditure.getQuantity());
            mOld.update();
            mat.setCurrentQuantity(mat.getCurrentQuantity() - quantity);
        }
        mat.update();
        expenditure.setDate(date);
        expenditure.setMaterial(mat);
        expenditure.setQuantity(quantity);
        User u = User.findById(userId);
        if (u == null){
            return false;
        }
        expenditure.setUser(u);
        expenditure.setNote(note);
        expenditure.update();
        return true;
    }

    //maze objekt tridy Expenditure s danym Id
    public boolean deleteExpenditure(int expenditureId){
        expenditure = Expenditure.findById(expenditureId);
        if (expenditure == null){
            return false;
        }
        Material mat = expenditure.getMaterial();        
        mat.setCurrentQuantity(mat.getCurrentQuantity() + expenditure.getQuantity());
        mat.update();
        //expenditure.delete();
        expenditure.setIsDeleted(1);
        return true;
    }

    //navraci objekt tridy Expenditure s danym Id
    public Expenditure getExpenditureById(int expenditureId){
        return Expenditure.findById(expenditureId);
    }

    //v podobe dvojrozmerneho pole typu Object navraci objekty tridy Expenditure
    public Object [][] getExpenditures(){
        List<Expenditure> list = Expenditure.findAll();
        if (list == null || list.isEmpty()){
            return null;
        }
        Object [][] array = new Object [list.size()][7];
        Iterator it = list.iterator();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        int i = 0;
        while (it.hasNext()){
            expenditure = (Expenditure)it.next();
            array [i][0] = expenditure.getExpenditureId();
            array [i][1] = sdf.format(expenditure.getDate());
            array [i][2] = expenditure.getMaterial().getName();
            array [i][3] = expenditure.getQuantity();
            array [i][4] = expenditure.getMaterial().getUnitType().getAbbreviation();                        
            if (expenditure.getUser().getIsDeleted() == 1){
                array [i][5] = "Uživatel smazán";
            } else {
                array [i][5] = expenditure.getUser().getUsername();
            }
            array [i][6] = expenditure.getNote();
            i++;
        }
        return array;
    }

}
