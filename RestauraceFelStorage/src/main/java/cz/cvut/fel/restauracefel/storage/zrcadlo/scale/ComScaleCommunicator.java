package cz.cvut.fel.restauracefel.storage.zrcadlo.scale;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;

/**
 *
 * @author Vojta
 */
public class ComScaleCommunicator extends ScaleCommunicator {

    InputStream in;
    ConfigParser cp;

    public ComScaleCommunicator() throws FileNotFoundException {
        cp = new ConfigParser();
    }
    
    /**
     * Metoda se pokusí připojit k váze.
     * @param portName Jméno portu (např. "COM1")
     * @throws Exception
     */
    @Override
    public void connect() throws NoSuchPortException, ScaleCommunicatorException, PortInUseException, UnsupportedCommOperationException, IOException {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(cp.getCom());
        if (portIdentifier.isCurrentlyOwned()) {
            throw new ScaleCommunicatorException("Port je právě využíván.");
        } else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(Integer.parseInt(cp.getBaudrate()), Integer.parseInt(cp.getDatabits()), Integer.parseInt(cp.getStopbits()), Integer.parseInt(cp.getParitybits()));

                in = serialPort.getInputStream();

            } else {
                throw new ScaleCommunicatorException("Zadaný port není sériový.");
            }
        }
    }

    /**
     * Metoda vraci udaj o hmotnosti z vahy.
     * @return Udaj o hmotnosti z vahy.
     */
    @Override
    public double weigh() throws ScaleCommunicatorException, IOException {
        String weight = read();
        if ((weight = parseNumber(weight)) == null) {
            throw new ScaleCommunicatorException("Špatný údaj z váhy.");
        }
        return Double.parseDouble(weight);
    }

    /**
     * Metoda ceka, dokud z vahy neprijde udaj o hmotnosti. Cteni konci
     * prectenim konce radku, ktery vaha posila po odeslani hmotnosti.
     * @return Precteny radek s udajem o hmotnosti.
     * @throws IOException
     */
    private String read() throws IOException {
        String s = "";
        int i = 0, previous = 0;

        while (true) {
            i = in.read();
            // pokud je prijat konec radku, cteni konci
            if (i == '\n' && previous == '\r') {
                break;
            }
            previous = i;
            if (i == '\r') {
                continue;
            }
            // znak pridame k vystupu
            if (i != -1) {
                s += (char) i;
            }
        }
        in.close();
        return s;
    }

    /**
     * Metoda vraci String obsahujici pouze udaj o hmotnosti.
     * @param s Parsovany retezec.
     * @return String obsahujici pouze udaj o hmotnosti. Pokud retezec neobsahuje zadne cislo, vraci null.
     */
    private String parseNumber(String s) {
        int start = 0;
        int end = s.length() - 1;
        for (; start <= s.length(); start++) {
            if (start == s.length()) {
                return null;
            }
            if (Character.isDigit(s.charAt(start))) {
                break;
            }
        }
        for (; end >= -1; end--) {
            if (Character.isDigit(s.charAt(end))) {
                break;
            }
        }
        return s.substring(start, end + 1);

    }
}
