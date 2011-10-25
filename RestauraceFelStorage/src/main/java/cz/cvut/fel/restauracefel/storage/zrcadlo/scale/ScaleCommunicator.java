
package cz.cvut.fel.restauracefel.storage.zrcadlo.scale;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;

/**
 *
 * @author Vojta
 */
public abstract class ScaleCommunicator {

    public abstract void connect() throws NoSuchPortException, ScaleCommunicatorException, PortInUseException, UnsupportedCommOperationException, IOException;
    public abstract double weigh() throws ScaleCommunicatorException, IOException;

}
