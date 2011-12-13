package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Workshift;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller for work with Workshift hibernate entity. It is used by
 * ServiceFacade.
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

    public boolean createNewWorkshift(Date date, int idTypeWorkShift) {
        if (date != null && idTypeWorkShift > 0) {
            Workshift workShift = new Workshift();
            workShift.setDateShift(date);
            workShift.setIdTypeWorkshift(idTypeWorkShift);
            workShift.create();
            return true;
        } else {
            return false;
        }

    }

    public Workshift getWorkshiftById(int idWorkshift) {
        return Workshift.findByIdNotDeleted(idWorkshift);
    }

    public List getWorkshiftByUserId(int idUser) {
        return Workshift.getWorkshiftByUserId(idUser);
    }

    public List getWorkshiftByTypeWorkshiftId(int idTypeWorkshift) {
        return Workshift.getWorkshiftByTypeWorkshiftId(idTypeWorkshift);
    }

    public List getAllActiveWorkShifts(Date dateFrom) {
        return Workshift.getAllActiveWorkShifts(dateFrom);
    }

    public boolean updateWorkshiftLogin(int workShiftId, Integer userId) {
        Workshift ws = Workshift.findByIdNotDeleted(workShiftId);
        if (ws == null) {
            return false;
        }
        ws.setIdUser(userId);
        ws.update();
        return true;
    }

    public boolean updateWorkshiftOccupation(int workShiftId, String message) {
        Workshift ws = Workshift.findByIdNotDeleted(workShiftId);
        if (ws == null) {
            return false;
        }
        ws.setUserSubmit(message);
        ws.update();
        return true;
    }

    public List getWorkshiftsFromTo(Date dateFrom, Date dateTo) {
        return Workshift.getWorkshiftsFromTo(dateFrom, dateTo);
    }
    
    public boolean delete(int idWorkShift){
        Workshift ws = Workshift.findByIdNotDeleted(idWorkShift);
        if(ws!=null) {
            ws.delete();
            return true;
        } else {
            return true;
        }
        
    }
}
