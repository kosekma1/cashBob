package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Attendance;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.hibernate.Workshift;
import java.util.Date;
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

    public boolean createNewAttendance(int userId, int workShiftId) {
        User user = UserController.getInstance().getUserById(userId);
        Workshift workShift = WorkShiftController.getInstance().getWorkshiftById(workShiftId);
        if (user != null && workShift != null) {
            Attendance newAttendance = new Attendance();
            newAttendance.setIdUser(userId);
            newAttendance.setIdWorkshift(workShiftId);
            newAttendance.create();
            return true;
        } else {
            return false;
        }

    }

    public List findByWorkShiftId(int workShiftId) {
        return Attendance.findByWorkShiftId(workShiftId);
    }

    public Attendance findById(int attendanceId) {
        return (Attendance) Attendance.findById("Attendance", "idAttendance", attendanceId);
    }

    public boolean deleteById(int attendanceId) {
        Attendance att = findById(attendanceId);
        if (att != null) {
            att.delete();
            return true;
        } else {
            return false;
        }

    }

    /**
     * Delete attendence from all workshifts with the same workShiftType
     * in the same date.
     * @param workShiftId
     * @param userId
     * @return 
     */
    public boolean deleteAllByDateWorkShiftTypeUserId(Date date, int workShifTypeId, int userId) {
        List workShiftList = WorkShiftController.getInstance().getWorkshiftsFromTo(date, date);
        Workshift ws = null;
        Attendance att = null;
        for (Object o : workShiftList) {
            ws = (Workshift) o;
            if (ws.getIdTypeWorkshift() == workShifTypeId) {
                att = this.findByWorkShiftAndUser(ws.getIdWorkshift(), userId);
                if (att != null) {
                    this.deleteById(att.getIdAttendance());
                }
            }
        }
        return true;
    }

    public Attendance findByWorkShiftAndUser(int workShiftId, int userId) {
        return Attendance.findByWorkShiftAndUser(workShiftId, userId);
    }
}
