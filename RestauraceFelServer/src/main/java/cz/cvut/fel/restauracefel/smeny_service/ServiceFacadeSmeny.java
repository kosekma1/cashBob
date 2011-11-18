package cz.cvut.fel.restauracefel.smeny_service;

import cz.cvut.fel.restauracefel.hibernate.Attendance;
import cz.cvut.fel.restauracefel.hibernate.Typeworkshift;
import cz.cvut.fel.restauracefel.hibernate.Workshift;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.hibernate.Role;
import cz.cvut.fel.restauracefel.hibernate.Template;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.hibernate.UserRole;
import cz.cvut.fel.restauracefel.server.service.controllers.AttendanceController;
import cz.cvut.fel.restauracefel.server.service.controllers.RoleController;
import cz.cvut.fel.restauracefel.server.service.controllers.ShiftTypeController;
import cz.cvut.fel.restauracefel.server.service.controllers.TemplateController;
import cz.cvut.fel.restauracefel.server.service.controllers.UserController;
import cz.cvut.fel.restauracefel.server.service.controllers.UserRoleController;
import cz.cvut.fel.restauracefel.server.service.controllers.WorkShiftController;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

/**
 * Implementation of all methods declared in interface for RMI communication.
 * 
 * @author Martin Kosek
 */
public class ServiceFacadeSmeny extends UnicastRemoteObject implements IServiceFacadeSmeny {

    //singleton
    protected static ServiceFacadeSmeny instance = null;

    public ServiceFacadeSmeny() throws RemoteException {
        super();
    }

    public void initServiceFacadeRMI(Registry reg) throws java.net.UnknownHostException, RemoteException, FileNotFoundException {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        String name = "ServiceFacadeSmeny";
        ConfigParser config = new ConfigParser();
        InetAddress inetAddress = InetAddress.getByName(config.getServerIP());

        IServiceFacadeSmeny facade = ServiceFacadeSmeny.getInstance();
        reg.rebind(name, facade);
        System.out.println("Servisni fasada pro modul SMENY zaregistrovana pod jmenem \"ServiceFacadeSmeny\"");
        System.out.println("Pripojeni pres adresu:" + inetAddress.toString() + "\n\n");

    }

    public static ServiceFacadeSmeny getInstance() throws RemoteException {
        if (instance == null) {
            instance = new ServiceFacadeSmeny();
        }
        return instance;
    }

    //TYPEWORKSHIFT
    @Override
    public List getTypeWorkShifts() /* throws RemoteException */ {
        return ShiftTypeController.getInstance().getTypeWorkShifts();

    }

    @Override
    public void createNewTypewWorkShift(String name, Date fromTime, Date toTime, int status, int idWorkshiftRole, int isDeleted) throws RemoteException {
        //SmenyController.getInstance().createNewTypeWorkShift(idTypeWorkshift, name, fromTime, toTime, status, idWorkshiftRole, isDeleted);
    }

    @Override
    public void createNewTypewWorkShift(Typeworkshift typeWorkshift) {
        ShiftTypeController.getInstance().createWorkshiftType(typeWorkshift);
    }

    @Override
    public Typeworkshift findTypeworkshiftByName(String name) throws RemoteException {
        return ShiftTypeController.getInstance().findTypeworkshiftByName(name);
    }

    @Override
    public Typeworkshift getTypeWorkShiftById(int idTypeWorkshift) throws RemoteException {
        return ShiftTypeController.getInstance().getTypeWorkShiftById(idTypeWorkshift);
    }

    //TEMPLATES
    @Override
    public void creatNewTemplate(Template template) throws RemoteException {
        TemplateController.getInstance().createTemplate(template);
    }

    @Override
    public Template findTemplateByName(String name) throws RemoteException {
        return TemplateController.getInstance().findTemplateByName(name);
    }

    @Override
    public void createNewTemplateList(int idTemplate, int idTypeWorkShift) throws RemoteException {
        TemplateController.getInstance().createNewTemplateList(idTemplate, idTypeWorkShift);
    }

    @Override
    public List getTemplateListByTemplateId(int idTemplate) throws RemoteException {
        return TemplateController.getInstance().getTemplateListByTemplateId(idTemplate);
    }

    @Override
    public List getTemplates() throws RemoteException {
        return TemplateController.getInstance().getTemplates();
    }

    //WORKSHIFTS
    @Override
    public void createNewWorkshift(Date date, int idTypeWorkShift) throws RemoteException {
        WorkShiftController.getInstance().createNewWorkshift(date, idTypeWorkShift);
    }

    @Override
    public Workshift getWorkshiftById(int idWorkshift) throws RemoteException {
        return WorkShiftController.getInstance().getWorkshiftById(idWorkshift);
    }

    @Override
    public List getWorkshiftByUserId(int idUser) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List getWorkshiftByTypeWorkshiftId(int idTypeWorkshift) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List getAllActiveWorkShifts(Date dateFrom) throws RemoteException {
        return WorkShiftController.getInstance().getAllActiveWorkShifts(dateFrom);
    }

    @Override
    public boolean updateWorkshift(int workShiftId, Integer userId) throws RemoteException {
        return WorkShiftController.getInstance().updateWorkshift(workShiftId, userId);
    }

    //ATTENDANCE      
    @Override
    public void createNewAttendance(int userId, int workShiftId) throws RemoteException {
        AttendanceController.getInstance().createNewAttendance(userId, workShiftId);
    }

    @Override
    public List getAttendaceByWorkShiftId(int workShiftId) throws RemoteException {
        return AttendanceController.getInstance().findByWorkShiftId(workShiftId);
    }

    @Override
    public Attendance getAttendanceById(int attendanceId) throws RemoteException {
        return AttendanceController.getInstance().findById(attendanceId);
    }

    @Override
    public void deleteAttendanceById(int attendanceId) throws RemoteException {
        AttendanceController.getInstance().deleteById(attendanceId);
    }

    @Override
    public Attendance getAttendaceByWorkShiftAndUser(int workShiftId, int userId) throws RemoteException {
        return AttendanceController.getInstance().findByWorkShiftAndUser(workShiftId, userId);
    }

    //USER
    @Override
    public List getAllUsers() throws RemoteException {
        return UserController.getInstance().getAllUsers();
    }

    @Override
    public boolean createUser(String name, String surname, String pid, String username, String passwd) throws RemoteException {
        return UserController.getInstance().createUser(name, surname, pid, username, passwd);
    }

    @Override
    public boolean createUser(String name, String surname, String username) throws RemoteException {
        return UserController.getInstance().createUser(name, surname, username);
    }

    @Override
    public boolean isValidUser(String username, String passwd) throws RemoteException {
        return UserController.getInstance().isValidUser(username, passwd);
    }

    @Override
    public User getUserByPID(String pid) throws RemoteException {
        return UserController.getInstance().getUserByPID(pid);
    }

    @Override
    public User getUserByUsername(String userName) throws RemoteException {
        return UserController.getInstance().getUserByUsername(userName);
    }

    @Override
    public User getUserById(int id) throws RemoteException {
        return UserController.getInstance().getUserById(id);
    }

    @Override
    public boolean deleteUser(int userId) throws RemoteException {
        return UserController.getInstance().deleteUser(userId);
    }

    @Override
    public String[] getUserNames() throws RemoteException {
        return UserController.getInstance().getUserNames();
    }

    @Override
    public String[] getUserUsernames() throws RemoteException {
        return UserController.getInstance().getUserUsernames();
    }

    @Override
    public Object[][] getUsers() throws RemoteException {
        return UserController.getInstance().getUsers();
    }

    @Override
    public boolean updateUser(Integer userId, String name, String surname, String pid, String username) throws RemoteException {
        return UserController.getInstance().updateUser(userId, name, surname, pid, username);
    }

    @Override
    public boolean updateUser(Integer userId, double credit) throws RemoteException {
        return UserController.getInstance().updateUser(userId, credit);
    }

    @Override
    public String getDefaultPasswd() throws RemoteException {
        return UserController.getInstance().getDefaultPasswd();
    }

    @Override
    public boolean updateUserPassword(Integer userId, String newPassword) throws RemoteException {
        return UserController.getInstance().updateUserPassword(userId, newPassword);
    }

    @Override
    public boolean isValidOldPasswd(Integer userId, String passwd) throws RemoteException {
        return UserController.getInstance().isValidOldPasswd(userId, passwd);
    }

    @Override
    public double getUserCredit(int userId) throws RemoteException {
        return UserController.getInstance().getUserCredit(userId);
    }

    //ROLE
    @Override
    public List getAllRoles() throws RemoteException {
        return RoleController.getInstance().getAllRoles();
    }

    @Override
    public String[] getRoleNames() throws RemoteException {
        return RoleController.getInstance().getRoleNames();
    }

    @Override
    public Role getRoleByID(int id) throws RemoteException {
        return RoleController.getInstance().getRoleByID(id);
    }

    @Override
    public Role getRoleByName(String name) throws RemoteException {
        return RoleController.getInstance().getRoleByName(name);
    }

    //USERROLE
    @Override
    public boolean createUserRole(int userId, int roleId) throws RemoteException {
        return UserRoleController.getInstance().createUserRole(userId, roleId);
    }

    @Override
    public void deleteUserRole(int userId, int roleId) throws RemoteException {
        UserRoleController.getInstance().deleteUserRole(userId, roleId);
    }

    @Override
    public UserRole getUserRoleById(int userRoleId) throws RemoteException {
        return UserRoleController.getInstance().getUserRoleById(userRoleId);
    }

    @Override
    public List getUserRoleByUserId(int userId) throws RemoteException {
        return UserRoleController.getInstance().getUserRoleByUserId(userId);
    }

    @Override
    public boolean isExistedUserRole(int userId, int roleId) throws RemoteException {
        UserRole ur = UserRoleController.getInstance().getUserRoleByUserAndRole(userId, roleId);
        if (ur == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean[] getUserRoles(int userId) throws RemoteException {
        return UserRoleController.getInstance().getUserRoles(userId);
    }

    @Override
    public boolean isUserRole(int userId, int roleId) throws RemoteException {
        return UserRoleController.getInstance().isUserRole(userId, roleId);
    }
}
