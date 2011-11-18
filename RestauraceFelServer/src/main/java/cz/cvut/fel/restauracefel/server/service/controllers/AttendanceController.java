package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Attendance;
import java.util.List;

/**
 * Controller for work with Attendance hibernate entity. It is used by
 * ServiceFacade.
 * 
 * @author Martin
 */
public class AttendanceController {
    
    protected static AttendanceController instance = null;
    protected Attendance attendance = null;
    
    public static AttendanceController getInstance() {
        if (instance == null) {
            instance = new AttendanceController();
        }
        return instance;
    }
    
    public void createNewAttendance(int userId, int workShiftId){
        Attendance newAttendance = new Attendance();
        newAttendance.setIdUser(userId);
        newAttendance.setIdWorkshift(workShiftId);
        newAttendance.create();
    }
    
    public List findByWorkShiftId(int workShiftId){
      return Attendance.findByWorkShiftId(workShiftId);   
    }
    
    public Attendance findById(int attendanceId){
        return (Attendance)Attendance.findById("Attendance", "idAttendance", attendanceId);
    }
    
    public void deleteById(int attendanceId){
        Attendance att = findById(attendanceId);        
        att.delete();        
    }
    
    public Attendance findByWorkShiftAndUser(int workShiftId, int userId)  {
        return Attendance.findByWorkShiftAndUser(workShiftId, userId);
    }
}
