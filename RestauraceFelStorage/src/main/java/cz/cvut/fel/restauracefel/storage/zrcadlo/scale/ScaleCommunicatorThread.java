package cz.cvut.fel.restauracefel.storage.zrcadlo.scale;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import cz.cvut.fel.restauracefel.storage.zrcadlo.controller.UzaverkaController;

/**
 *
 * @author Vojta
 */
public class ScaleCommunicatorThread implements Runnable {

    ScaleCommunicator sc;
    UzaverkaController kontroler;
    ConfigParser cp;

    public ScaleCommunicatorThread(UzaverkaController kontroler) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, ScaleCommunicatorException, IOException {
        this.kontroler = kontroler;
        cp = new ConfigParser();
        sc = ScaleCommunicatorFactory.getScaleCommunicator(EnumScaleCommunicatorType.valueOf(cp.getScaleType()));
        sc.connect();
    }

    @Override
    public void run() {
        try {
            while (true) {
                kontroler.updateHmotnost(sc.weigh());
            }
        } catch (ScaleCommunicatorException ex) {
            Logger.getLogger(ScaleCommunicatorThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ScaleCommunicatorThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
