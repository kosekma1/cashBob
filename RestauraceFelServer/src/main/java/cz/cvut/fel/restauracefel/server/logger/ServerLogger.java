package cz.cvut.fel.restauracefel.server.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Jarda
 */
public class ServerLogger {

    private static final String LOG_FILE_NAME = "exceptions.log";
    private static ServerLogger instance = null;
    private Logger logger = null;
    private Handler handler = null;

    private ServerLogger(){
        try {
            logger = Logger.getLogger("exceptions");
            handler = new FileHandler(LOG_FILE_NAME, true);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
            logger.setLevel(Level.ALL);
        } catch (IOException ioe){
            System.out.println("Logger: IO exception");
            ioe.printStackTrace();
        } catch (SecurityException se){
            System.out.println("Logger: Security exception");
            se.printStackTrace();
        }
    }

    public static ServerLogger getInstance(){
        if (instance == null){
            instance = new ServerLogger();
        }
        return instance;
    }

    public void closeLogger(){
        handler.close();
    }

    public void setLoggingLevel(Level level){
        logger.setLevel(level);
    }

    public void writeLogMessage(Level level, String message){
        logger.log(level, message);
    }

    public void writeLogMessageExcp(Level level, String message, Throwable thrown){
        logger.log(level, message, thrown);
    }  

    public void writeThrowingException(String sourceClass, String sourceMethod, Throwable thrown){
        logger.throwing(sourceClass, sourceMethod, thrown);
    }

}
