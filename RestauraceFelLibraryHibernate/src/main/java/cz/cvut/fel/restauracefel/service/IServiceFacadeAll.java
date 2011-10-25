package cz.cvut.fel.restauracefel.service;

import cz.cvut.fel.restauracefel.hibernate.Right;
import cz.cvut.fel.restauracefel.hibernate.Role;
import cz.cvut.fel.restauracefel.hibernate.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Hlavni interface pro RMI komunikaci se seznamem metod implementovanych na strane serveru
 *
 * @author Tomas Hnizdil
 */
public interface IServiceFacadeAll extends Remote {

    //USER methods
    public boolean isValidUser(String username, String passwd) throws RemoteException;
    public User getUserByUsername(String userName) throws RemoteException;
    public User getUserById(int id) throws RemoteException;

    //ROLE methods
    public String[] getRoleNames() throws RemoteException;
    public Role getRoleByID(int id) throws RemoteException;
    public Role getRoleByName(String name) throws RemoteException;

    //RIGHT methods
    public String[] getRightNames() throws RemoteException;
    public Right getRightByID(int id) throws RemoteException;
    public Right getRightByName(String name) throws RemoteException;
    public String[] getRightsByUser(int userId) throws RemoteException;

    //ROLERIGHT methods
    public boolean createRoleRight(int roleId, int rightId) throws RemoteException;
    public boolean deleteRoleRight(int roleId, int rightId) throws RemoteException;
    public String[] getRightNamesByRole(int roleId) throws RemoteException;
}