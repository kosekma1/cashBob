package cz.cvut.fel.restauracefel.storage.zrcadlo.controller;

/**
 *
 * @author Vojta
 */
public class ValidateInputException extends Exception {

    /**
     * Creates a new instance of <code>ValidateInputException</code> without detail message.
     */
    public ValidateInputException() {
    }


    /**
     * Constructs an instance of <code>ValidateInputException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ValidateInputException(String msg) {
        super(msg);
    }
}
