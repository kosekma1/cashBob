package cz.cvut.fel.restauracefel.storage.zrcadlo.scale;

/**
 *
 * @author Vojta
 */
public class ScaleCommunicatorException extends Exception {

    /**
     * Creates a new instance of <code>ScaleCommunicatorException</code> without detail message.
     */
    public ScaleCommunicatorException() {
    }


    /**
     * Constructs an instance of <code>ScaleCommunicatorException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ScaleCommunicatorException(String msg) {
        super(msg);
    }
}
