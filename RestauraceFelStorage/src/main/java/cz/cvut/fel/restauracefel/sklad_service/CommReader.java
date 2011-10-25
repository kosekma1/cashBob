package cz.cvut.fel.restauracefel.sklad_service;

//import gnu.io.CommPort;
//import gnu.io.CommPortIdentifier;
//import gnu.io.SerialPort;
//import sklad_gui.UzaverkaForm;
import cz.cvut.fel.restauracefel.library.service.ConfigParser;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Lukas Camra
 */
public class CommReader {
    //private static CommPort commPort;

    public static boolean isRun=true;
    void connect(String portName) throws Exception {
        /*CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Error: Port is currently in use");
        } else {
            CommReader.commPort = portIdentifier.open(this.getClass().getName(), 2000 );

            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                serialPort.setRTS(true);
                InputStream in = serialPort.getInputStream();
//                OutputStream out = serialPort.getOutputStream();

                (new Thread(new SerialReader(in))).start();
//                (new Thread(new SerialWriter(out))).start();

            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }*/
    }

    public static void close(){
        //commPort.close();
    }

    /** */
    public static class SerialReader implements Runnable {

        InputStream in;

        public SerialReader(InputStream in) {
            this.in = in;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int len = -1;
            try {
                boolean wasLF=false;
                StringBuilder sb=new StringBuilder();
                while ( ( len = this.in.read(buffer)) > -1 && isRun ) {
                    if(wasLF){
                        sb=new StringBuilder();
                        wasLF=false;
                    }
                    String s=new String(buffer,0,len);
                    if(s.length()>0 && s.charAt(s.length()-1)==10){
                        sb.append(s.substring(0,s.length()-1));
                        wasLF=true;
                        sendWeight(sb.toString());
                    }
                    else
                        sb.append(s);
                }
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendWeight(String t){
        try{
        int len=t.length();
        String result=t.substring(len-7,len-5)+"."+t.substring(len-5);
        double resultDouble=Double.parseDouble(result);
        resultDouble*=1000;
        //zaokrouhleni
        int intResult= (int) (resultDouble*10);
        resultDouble=((double) intResult) / 10;
        System.out.println(resultDouble);
//        UzaverkaForm.setWeight(resultDouble);
        }
        catch(Exception ex){
            System.out.println("Reading error..."+t+"; "+ex.getClass().toString());

        }
    }
    

    /** */
//    public static class SerialWriter implements Runnable {
//
//        OutputStream out;
//
//        public SerialWriter(OutputStream out) {
//            this.out = out;
//        }
//
//        public void run() {
//            try {
//                int c = 0;
//                while ((c = System.in.read()) > -1) {
//                    this.out.write(c);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static void start() throws Exception {
        CommReader.isRun=true;
        CommReader reader=(new CommReader());
        ConfigParser config=new ConfigParser();
        reader.connect(config.getCom());
    }
}
