/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Workshift;
import cz.cvut.fel.restauracefel.smeny_service.ServiceFacadeSmeny;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Martin
 */
public class WorkShiftController {
    
    protected static WorkShiftController instance = null;
    protected Workshift template = null;
    
    private WorkShiftController() {
    }

    public static WorkShiftController getInstance() {
        if (instance == null) {
            instance = new WorkShiftController();
        }
        return instance;
    }
    
    public void createNewWorkshift(Date date, int idTypeWorkShift){
        Workshift workShift = new Workshift();
        workShift.setDateShift(date);
        workShift.setIdTypeWorkshift(idTypeWorkShift);
        workShift.create();
    }
    
    public Workshift getWorkshiftById(int idWorkshift) {
        return Workshift.findByIdNotDeleted(idWorkshift);    
    }
    
    public List getWorkshiftByUserId(int idUser) {
        return new ArrayList(); //TODO - implement
    }
    
    public List getWorkshiftByTypeWorkshiftId(int idTypeWorkshift) {
        return new ArrayList(); //TODO - implement
    }
    
    public List getAllActiveWorkShifts(Date dateFrom) {
        return Workshift.getAllActiveWorkShifts(dateFrom);
    }
}
