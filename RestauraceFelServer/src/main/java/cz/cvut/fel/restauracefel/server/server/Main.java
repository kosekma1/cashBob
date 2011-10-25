package cz.cvut.fel.restauracefel.server.server;

import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import cz.cvut.fel.restauracefel.server.logger.ServerLogger;
import cz.cvut.fel.restauracefel.service.ServiceFacadeAll;
import cz.cvut.fel.restauracefel.pokladna_service.ServiceFacadeCash;
import cz.cvut.fel.restauracefel.manager_service.ServiceFacadeManager;
import cz.cvut.fel.restauracefel.sklad_service.ServiceFacadeStorage;
import cz.cvut.fel.restauracefel.service.ServiceFacadePDA;
import cz.cvut.fel.restauracefel.smeny_service.ServiceFacadeSmeny;
import java.net.InetAddress;


/**
 *
 * @author Jarda
 */
public class Main {

    public static void main(String[] args) {

        ServerLogger log = ServerLogger.getInstance();

        //inicializace Java RMI
        try {            
            log.writeLogMessage(Level.INFO, "Server spusten");
            ServiceFacadeAll facadeAll = ServiceFacadeAll.getInstance();
            ServiceFacadeManager facadeManager = ServiceFacadeManager.getInstance();
            ServiceFacadeStorage facadeStorage = ServiceFacadeStorage.getInstance();
            ServiceFacadeCash facadeCash = ServiceFacadeCash.getInstance();
            ServiceFacadePDA facadePDA = ServiceFacadePDA.getInstance();
            ServiceFacadeSmeny facadeSmeny = ServiceFacadeSmeny.getInstance();
            //Vsechny fasady jsou registrovany a pristupny pres port 1099 (jmenna sluzba)
            
            ConfigParser config = new ConfigParser();
            InetAddress inetAddress = InetAddress.getByName(config.getServerIP());
            
            Registry reg = LocateRegistry.createRegistry(1099, null, new AnchorSocketFactory(inetAddress));
            //Registry reg = LocateRegistry.createRegistry(1099);
            facadeAll.initServiceFacadeRMI(reg);
            facadeManager.initServiceFacadeRMI(reg);
            facadeStorage.initServiceFacadeRMI(reg);
            facadeCash.initServiceFacadeRMI(reg);
            facadePDA.initServiceFacadeRMI(reg);
            facadeSmeny.initServiceFacadeRMI(reg);
        } catch (RemoteException re) {
            log.writeThrowingException("ServiceFacadeAll", "getInstance", re);
            log.writeThrowingException("ServiceFacadeManager", "getInstance", re);
            log.writeThrowingException("ServiceFacadeStorage", "getInstance", re);
            log.writeThrowingException("ServiceFacadeCash", "getInstance", re);
            log.writeThrowingException("ServiceFacadePDA", "getInstance", re);
            log.writeThrowingException("ServiceFacadeAll", "initServiceFacadeRMI", re);
            log.writeThrowingException("ServiceFacadeManager", "initServiceFacadeRMI", re);
            log.writeThrowingException("ServiceFacadeStorage", "initServiceFacadeRMI", re);
            log.writeThrowingException("ServiceFacadeCash", "initServiceFacadeRMI", re);
            log.writeThrowingException("ServiceFacadePDA", "initServiceFacadeRMI", re);
            log.writeThrowingException("ServiceFacadeSmeny", "initServiceFacadeSmeny", re);
            System.exit(-1);
        } catch (UnknownHostException uhe){
            log.writeThrowingException("ServiceFacadeAll", "getInstance", uhe);
            log.writeThrowingException("ServiceFacadeManager", "getInstance", uhe);
            log.writeThrowingException("ServiceFacadeStorage", "getInstance", uhe);
            log.writeThrowingException("ServiceFacadeCash", "getInstance", uhe);
            log.writeThrowingException("ServiceFacadePDA", "getInstance", uhe);
            log.writeThrowingException("ServiceFacadeAll", "initServiceFacadeRMI", uhe);
            log.writeThrowingException("ServiceFacadeManager", "initServiceFacadeRMI", uhe);
            log.writeThrowingException("ServiceFacadeStorage", "initServiceFacadeRMI", uhe);
            log.writeThrowingException("ServiceFacadeCash", "initServiceFacadeRMI", uhe);
            log.writeThrowingException("ServiceFacadePDA", "initServiceFacadeRMI", uhe);
            log.writeThrowingException("ServiceFacadeSmeny", "initServiceFacadeSmeny", uhe);
            System.exit(-1);
        } catch (Exception ex){
            log.writeThrowingException("ServiceFacadeAll", "getInstance", ex);
            log.writeThrowingException("ServiceFacadeManager", "getInstance", ex);
            log.writeThrowingException("ServiceFacadeStorage", "getInstance", ex);
            log.writeThrowingException("ServiceFacadeCash", "getInstance", ex);
            log.writeThrowingException("ServiceFacadePDA", "getInstance", ex);
            log.writeThrowingException("ServiceFacadeAll", "initServiceFacadeRMI", ex);
            log.writeThrowingException("ServiceFacadeManager", "initServiceFacadeRMI", ex);
            log.writeThrowingException("ServiceFacadeStorage", "initServiceFacadeRMI", ex);
            log.writeThrowingException("ServiceFacadeCash", "initServiceFacadeRMI", ex);
            log.writeThrowingException("ServiceFacadePDA", "initServiceFacadeRMI", ex);
            log.writeThrowingException("ServiceFacadeSmeny", "initServiceFacadeSmeny", ex);
            ex.printStackTrace();
            System.exit(-1);
        }        
               
    }
}
