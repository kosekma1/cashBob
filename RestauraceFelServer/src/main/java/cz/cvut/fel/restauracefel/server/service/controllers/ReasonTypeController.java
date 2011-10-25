package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Depreciation;
import cz.cvut.fel.restauracefel.hibernate.ReasonType;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jarda
 */
public class ReasonTypeController {
    
    private static ReasonTypeController instance = null;
    private ReasonType reasonType = null;

    private ReasonTypeController(){
    }

    public static ReasonTypeController getInstance(){
        if (instance == null){
            instance = new ReasonTypeController();
        }
        return instance;
    }

    public boolean createReasonType(String name, String note){
        reasonType = ReasonType.findByName(name);
        if (reasonType == null){
            reasonType = new ReasonType(name, note, 0);
            reasonType.create();
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteReasonType(int reasonTypeId){
        reasonType = ReasonType.findById(reasonTypeId);
        if (reasonType == null){
            return false;
        }
        //reasonType.delete();
        reasonType.setIsDeleted(1);
        return true;
    }

    public boolean updateReasonType(int reasonTypeId, String name, String note){
        reasonType = ReasonType.findById(reasonTypeId);
        if (reasonType == null){
            return false;
        }
        ReasonType rt = ReasonType.findByName(name);
        if (rt != null && rt != reasonType){
            return false;
        }
        reasonType.setName(name);
        reasonType.setNote(note);
        reasonType.update();
        return true;
    }

    public ReasonType getReasonTypeById(int reasonTypeId){
        return ReasonType.findById(reasonTypeId);
    }

    public ReasonType getReasonTypeByName(String name){
        return ReasonType.findByName(name);
    }

    public Object [][] getReasonTypes(){
        List<ReasonType> list = ReasonType.findAll();
        if (list == null || list.isEmpty()){
            return null;
        }
        Object array [][] = new Object [list.size()][3];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()){
            reasonType = (ReasonType)it.next();
            array [i][0] = reasonType.getReasonTypeId();
            array [i][1] = reasonType.getName();
            array [i][2] = reasonType.getNote();
            i++;
        }
        return array;
    }

    public String [] getReasonTypeNames(){
        List<ReasonType> list = ReasonType.findAll();
        if (list == null || list.isEmpty()){
            return null;
        }
        String names [] = new String [list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()){
            reasonType = (ReasonType)it.next();
            names [i] = reasonType.getName();
            i++;
        }
        return names;
    }

    public boolean isDeletableReasonType(int reasonTypeId){
        List<Depreciation> deps = Depreciation.findByReasonType(reasonTypeId);
        if (deps == null || deps.isEmpty()){
            return true;
        }
        return false;
    }
}
