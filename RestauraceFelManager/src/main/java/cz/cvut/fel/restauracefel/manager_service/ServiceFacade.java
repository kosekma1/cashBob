package cz.cvut.fel.restauracefel.manager_service;

import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Trida reprezentujici servisni facadu na klientske strane. Tato trida slouzi
 * k navazovani spojeni se serverem.
 *
 * @author Jarda
 */
public class ServiceFacade {

    private static IServiceFacadeManager facade = null;

    /**
     * Soukromy konstruktor tridy ServiceFacade
     */
    private ServiceFacade() {
    }

    /**
     * Tato metoda slozi k ziskani reference na serverovou fasadu. Klient pote
     * muze volat metody deklarovane ve vzdalenem rozhrani (jak na serveru tak
     * na klientu) a definovane na serveru.
     * Server se pokusi pripojit k primarnimu serveru a pokud to selze, tak se
     * zkousi pripojit k serveru sekundarnimu.
     *
     * @return vzdalene rozhrani
     * @throws java.rmi.RemoteException
     * @throws java.io.FileNotFoundException
     * @throws java.rmi.NotBoundException
     */
    public static IServiceFacadeManager getInstance() throws RemoteException, FileNotFoundException, NotBoundException {
        if (facade == null) {
            ConfigParser config = null;
            config = new ConfigParser();
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
            }
            String name = "ServiceFacadeManager";
            facade = null;
            Registry reg = null;
            try {
                reg = LocateRegistry.getRegistry(config.getPrimaryServerIP(), 1099);
                facade = (IServiceFacadeManager) reg.lookup(name);
            } catch (Exception e){
                try {
                    reg = LocateRegistry.getRegistry(config.getSecondaryServerIP(), 1099);
                    facade = (IServiceFacadeManager) reg.lookup(name);
                } catch (Exception ex){
                    facade = null;
                    throw new RemoteException();
                }
            }
        }
        return facade;
    }
}

