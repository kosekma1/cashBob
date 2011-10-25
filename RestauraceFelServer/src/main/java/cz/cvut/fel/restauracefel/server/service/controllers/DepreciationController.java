package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Depreciation;
import cz.cvut.fel.restauracefel.hibernate.Material;
import cz.cvut.fel.restauracefel.hibernate.ReasonType;
import cz.cvut.fel.restauracefel.hibernate.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Trida reprezentujici controller pro praci s tridou Depreciation.
 *
 * @author Jarda
 */
public class DepreciationController {

    private static DepreciationController instance = null;
    private Depreciation depreciation = null;

    private DepreciationController(){
    }

    /**
     * Staticka metoda, navraci referenci na instanci tridy DepreciationController.
     *
     * @return objekt tridy DepreciationController
     */
    public static DepreciationController getInstance(){
        if (instance == null){
            instance = new DepreciationController();
        }
        return instance;
    }

    /**
     * Vytvari zaznam o provedeni odpisu (depreciation).
     *
     * @param userReporterId ID osoby, ktera odpis zaznamenala
     * @param userOffenderId ID osoby, ktera zapricinila duvod pro odpis
     * @param materialId ID odepisovaneho materialu
     * @param quantity mnozstvi odepisovaneho materialu
     * @param date datum odpisu
     * @param reasonTypeId ID duvodu odpisu
     * @param note poznamka
     * @return true, pokud se podarilo zaznamenat odpis; jinak false
     */
    public boolean createDepreciation(int userReporterId, int userOffenderId, int materialId, double quantity, Date date, int reasonTypeId, String note){
        User uReport = User.findById(userReporterId);
        if (uReport == null){
            return false;
        }
        User uOffend = User.findById(userOffenderId);
        if (uOffend == null){
            return false;
        }
        Material m = Material.findById(materialId);
        if (m == null){
            return false;
        }
        if (m.getCurrentQuantity() < quantity){
            return false;
        }
        m.setCurrentQuantity(m.getCurrentQuantity() - quantity);
        m.update();
        depreciation = new Depreciation();
        depreciation.setUserReporter(uReport);
        depreciation.setUserOffender(uOffend);
        depreciation.setMaterial(m);
        depreciation.setQuantity(quantity);
        depreciation.setDate(date);        
        ReasonType rt = ReasonType.findById(reasonTypeId);
        if (rt == null){
            return false;
        }
        depreciation.setReasonType(rt);
        depreciation.setNote(note);
        depreciation.setIsDeleted(0);
        depreciation.create();
        return true;
    }

    /**
     * Metoda maze zaznam o odpisu dle jeho ID.
     *
     * @param depreciationId ID ospisu, ktery chceme odstranit z evidence
     * @return true, pokud byl zaznam odstranenl jinak false
     */
    public boolean deleteDepreciation(int depreciationId){
        depreciation = Depreciation.findById(depreciationId);
        if (depreciation == null){
            return false;
        }
        Material mat = depreciation.getMaterial();
        mat.setCurrentQuantity(mat.getCurrentQuantity() + depreciation.getQuantity());
        mat.update();
        //depreciation.delete();
        depreciation.setIsDeleted(1);
        return true;
    }

    /**
     * Metoda pro aktualizaci zaznamu o provedeni odpisu.
     *
     * @param depreciationId ID zaznamu o odpisu, ktery chceme aktualizovat
     * @param userReporterId ID osoby, ktera odpis zaznamenala
     * @param userOffenderId ID osoby, ktera zapricinila duvod pro odpis
     * @param materialId ID odepisovaneho materialu
     * @param quantity mnozstvi odepisovaneho materialu
     * @param date datum odpisu
     * @param reasonTypeId ID duvodu odpisu
     * @param note poznamka
     * @return true, pokud byl zaznam aktualizovan; jinak false
     */
    public boolean updateDepreciation(int depreciationId, int userReporterId, int userOffenderId, int materialId, double quantity, Date date, int reasonTypeId, String note){
        depreciation = Depreciation.findById(depreciationId);
        if (depreciation == null){
            return false;
        }
        User uReport = User.findById(userReporterId);
        if (uReport == null){
            return false;
        }
        User uOffend = User.findById(userOffenderId);
        if (uOffend == null){
            return false;
        }
        Material mat = Material.findById(materialId);
        if (mat == null){
            return false;
        }
        if (mat == depreciation.getMaterial()){
            mat.setCurrentQuantity(mat.getCurrentQuantity() + depreciation.getQuantity() - quantity);
        } else {
            Material mOld = depreciation.getMaterial();
            mOld.setCurrentQuantity(mOld.getCurrentQuantity() + depreciation.getQuantity());
            mOld.update();
            mat.setCurrentQuantity(mat.getCurrentQuantity() - quantity);
        }
        mat.update();
        ReasonType rt = ReasonType.findById(reasonTypeId);
        if (rt == null){
            return false;
        }
        depreciation.setUserReporter(uReport);
        depreciation.setUserOffender(uOffend);
        depreciation.setMaterial(mat);
        depreciation.setQuantity(quantity);
        depreciation.setDate(date);
        depreciation.setReasonType(rt);
        depreciation.setNote(note);
        depreciation.update();
        return true;
    }

    /**
     * Metoda navraci zaznam o odpisu s danym depreciationID.
     *
     * @param depreciationId ID odpisu
     * @return odpis s danym depreciationID
     */
    public Depreciation getDepreciationById(int depreciationId){
        return Depreciation.findById(depreciationId);
    }

    /**
     * Metoda navraci zaznamy o odpisech reprezentovany v 2D poli objektu.
     *
     * @return pole tridy Object
     */
    public Object [][] getDepreciations(){
        List<Depreciation> list = Depreciation.findAll();
        if (list == null || list.isEmpty()){
            return null;
        }
        Object array [][] = new Object [list.size()][9];
        Iterator it = list.iterator();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        int i = 0;
        while (it.hasNext()){
            depreciation = (Depreciation)it.next();
            array [i][0] = depreciation.getDepreciationId();
            array [i][1] = sdf.format(depreciation.getDate());
            array [i][2] = depreciation.getMaterial().getName();
            array [i][3] = depreciation.getQuantity();
            array [i][4] = depreciation.getMaterial().getUnitType().getAbbreviation();
            array [i][5] = depreciation.getReasonType().getName();            
            if (depreciation.getUserOffender().getIsDeleted() == 1){
                array [i][6] = "Uživatel smazán";
            } else {
                array [i][6] = depreciation.getUserOffender().getUsername();
            }
            if (depreciation.getUserReporter().getIsDeleted() == 1){
                array [i][7] = "Uživatel smazán";
            } else {
                array [i][7] = depreciation.getUserReporter().getUsername();
            }
            array [i][8] = depreciation.getNote();
            i++;
        }
        return array;
    }

}
