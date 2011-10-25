package cz.cvut.fel.restauracefel.service;

import cz.cvut.fel.restauracefel.hibernate.Right;
import cz.cvut.fel.restauracefel.hibernate.Role;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import cz.cvut.fel.restauracefel.server.service.controllers.RightController;
import cz.cvut.fel.restauracefel.server.service.controllers.RoleController;
import cz.cvut.fel.restauracefel.server.service.controllers.RoleRightController;
import cz.cvut.fel.restauracefel.server.service.controllers.UserController;

/**
 *
 * @author Tomas Hnizdil
 */
public class ServiceFacadeAll extends UnicastRemoteObject implements IServiceFacadeAll {

    //singleton
    protected static ServiceFacadeAll instance = null;

    public ServiceFacadeAll() throws RemoteException {
        super();
    }

    public void initServiceFacadeRMI(Registry reg) throws java.net.UnknownHostException, RemoteException, FileNotFoundException {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        
        String name = "ServiceFacadeAll";
        
        ConfigParser config = new ConfigParser();
        InetAddress inetAddress = InetAddress.getByName(config.getServerIP());
        //Stub
        IServiceFacadeAll facade = ServiceFacadeAll.getInstance();
        reg.rebind(name, facade);
        System.out.println("Servisni fasada pro spolecne rozhranni zaregistrovana pod jmenem \"ServiceFacadeAll\"");
        System.out.println("Pripojeni pres adresu:" + inetAddress.toString() + "\n\n");

    }

    //Vraci instanci tridy ServiceFacadeManager
    public static ServiceFacadeAll getInstance() throws RemoteException {
        if (instance == null) {
            instance = new ServiceFacadeAll();
        }
        return instance;
    }


    /*
     *
     * Zde bude implementace metod deklarovanych v rozhrani IServiceFacadeCash
     *
     * metody pro modul POKLADNA
     */
    
    //USER
    @Override
    public boolean isValidUser(String username, String passwd) throws RemoteException {
        return UserController.getInstance().isValidUser(username, passwd);
    }

    @Override
    public User getUserByUsername(String userName) throws RemoteException {
        return UserController.getInstance().getUserByUsername(userName);
    }

    @Override
    public User getUserById(int id) throws RemoteException {
        return UserController.getInstance().getUserById(id);
    }

    //ROLE
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

    //RIGHT methods
    @Override
    public String[] getRightNames() throws RemoteException {
        return RightController.getInstance().getRightNames();
    }

    @Override
    public Right getRightByID(int id) throws RemoteException {
        return RightController.getInstance().getRightByID(id);
    }

    @Override
    public Right getRightByName(String name) throws RemoteException {
        return RightController.getInstance().getRightByName(name);
    }

    @Override
    public String[] getRightsByUser(int userId) throws RemoteException {
        return RightController.getInstance().getRightsByUser(userId);
    }

    //ROLERIGHT methods
    @Override
    public boolean createRoleRight(int roleId, int rightId) throws RemoteException {
        return RoleRightController.getInstance().createRoleRight(roleId, rightId);
    }

    @Override
    public boolean deleteRoleRight(int roleId, int rightId) throws RemoteException {
        return RoleRightController.getInstance().deleteRoleRight(roleId, rightId);
    }

    @Override
    public String[] getRightNamesByRole(int roleId) throws RemoteException {
        return RoleRightController.getInstance().getRightNamesByRole(roleId);
    }
}
