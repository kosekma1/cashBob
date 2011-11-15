/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Attendance;
import java.util.List;

/**
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
        return Attendance.findById(attendanceId);
    }
    
    public void deleteById(int attendanceId){
        Attendance att = Attendance.findById(attendanceId);
        att.delete();        
    }
}
