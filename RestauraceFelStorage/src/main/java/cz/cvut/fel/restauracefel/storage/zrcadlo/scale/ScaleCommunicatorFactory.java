package cz.cvut.fel.restauracefel.storage.zrcadlo.scale;

import java.io.FileNotFoundException;

/**
 *
 * @author Vojta
 */
public class ScaleCommunicatorFactory {

    public static ScaleCommunicator getScaleCommunicator(EnumScaleCommunicatorType t) throws FileNotFoundException {
        switch (t) {
            case COM:
                return new ComScaleCommunicator();
            default:
                return null;
        }

    }
}
