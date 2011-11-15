package cz.cvut.fel.restauracefel.smeny_service;

import cz.cvut.fel.restauracefel.hibernate.Attendance;
import cz.cvut.fel.restauracefel.hibernate.Role;
import cz.cvut.fel.restauracefel.hibernate.Template;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.hibernate.UserRole;
import cz.cvut.fel.restauracefel.hibernate.Typeworkshift; //TODO vlozit tridu do balicku
import cz.cvut.fel.restauracefel.hibernate.Workshift;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * Hlavni interface pro RMI komunikaci se seznamem metod implementovanych na strane serveru
 * pro modul Workshift/Smeny
 *
 * @author Martin Kosek
 */

public interface IServiceFacadeSmeny extends Remote  {
    
    //TYPEWORKSHIFT
    public List getTypeWorkShifts() throws RemoteException;
    public Typeworkshift findTypeworkshiftByName(String name) throws RemoteException;                
    public Typeworkshift getTypeWorkShiftById(int idTypeWorkshift) throws RemoteException;
    public void createNewTypewWorkShift(String name, Date fromTime, Date toTime, int status, int idWorkshiftRole, int isDeleted) throws RemoteException;     
    public void createNewTypewWorkShift(Typeworkshift typeWorkshift) throws RemoteException;
    
    //TEMPLATES
    public void creatNewTemplate(Template template)throws RemoteException;
    public Template findTemplateByName(String name) throws RemoteException;
    public void createNewTemplateList(int idTemplate, int idTypeWorkShift) throws RemoteException;
    public List getTemplateListByTemplateId(int idTemplate) throws RemoteException;
    public List getTemplates() throws RemoteException;  
    
    //WORKSHIFTS
    //public void createNewWorkshift(Workshift workshift) throws RemoteException;
    public void createNewWorkshift(Date date, int idTypeWorkShift) throws RemoteException;
    public Workshift getWorkshiftById(int idWorkshift) throws RemoteException;
    public List getWorkshiftByUserId(int idUser) throws RemoteException;
    public List getWorkshiftByTypeWorkshiftId(int idTypeWorkshift) throws RemoteException;
    public List getAllActiveWorkShifts(Date dateFrom) throws RemoteException;
    
    public void createNewAttendance(int userId, int workShiftId) throws RemoteException;
    public List getAttendaceByWorkShiftId(int workShiftId) throws RemoteException;
    public Attendance getAttendanceById(int attendanceId)throws RemoteException;
    public void deleteAttendanceById(int attendanceId)throws RemoteException;
    
    //USER methods
    public List getAllUsers() throws RemoteException;
    public boolean createUser(String name, String surname, String pid, String username, String passwd) throws RemoteException;
    public boolean createUser(String name, String surname, String username) throws RemoteException;
    public boolean isValidUser(String username, String passwd) throws RemoteException;
    public User getUserByPID(String pid) throws RemoteException;
    public User getUserByUsername(String userName) throws RemoteException;
    public User getUserById(int id) throws RemoteException;
    public boolean deleteUser(int userId) throws RemoteException;
    public String[] getUserNames() throws RemoteException;
    public String[] getUserUsernames() throws RemoteException;
    public Object [][] getUsers() throws RemoteException;
    public boolean updateUser(Integer userId, String name, String surname, String pid, String username) throws RemoteException;
    public boolean updateUser(Integer userId, double credit) throws RemoteException;
    public String getDefaultPasswd() throws RemoteException;
    public boolean updateUserPassword(Integer userId, String newPassword) throws RemoteException;
    public boolean isValidOldPasswd(Integer userId, String passwd) throws RemoteException;
    public double getUserCredit(int userId) throws RemoteException;

    //ROLE methods
    public List getAllRoles() throws RemoteException;
    public String[] getRoleNames() throws RemoteException;
    public Role getRoleByID(int id) throws RemoteException;
    public Role getRoleByName(String name) throws RemoteException;
    
     //USERROLE methods
    public boolean createUserRole(int userId, int roleId) throws RemoteException;
    public void deleteUserRole(int userId, int roleId) throws RemoteException;
    public UserRole getUserRoleById(int userRoleId) throws RemoteException;
    //public UserRole getUserRoleByUserAndRole(int userId, int roleId) throws RemoteException;
    public List getUserRoleByUserId(int user) throws RemoteException;
    public boolean isExistedUserRole(int userId, int roleId) throws RemoteException;
    public boolean [] getUserRoles(int userId) throws RemoteException;
    public boolean isUserRole(int userId, int roleId) throws RemoteException;

}

