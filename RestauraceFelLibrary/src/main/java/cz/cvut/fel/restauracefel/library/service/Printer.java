package cz.cvut.fel.restauracefel.library.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Class for printig from list and table model to printer, PDF and CSV
 *
 * @author Lukas Kotrba <lukas.kotrba@hotmail.com>
 */
public class Printer {

    /**
     * Constructor
     */
    private Printer() {
    }

    /**
     * Save given Collection data into PDF
     *
     * @param params Print parameters
     * @param data Collection with data
     * @param pressKit File with Jasper press kit (*.jasper)
     * @param destinationPath Destination file path
     * @throws net.sf.jasperreports.engine.JRException
     */
    public static void printFromListToPDF(Map<String, Object> params, Collection data, File pressKit, String destinationPath) throws JRException {
        JasperReport jr = (JasperReport) JRLoader.loadObject(pressKit);
        params = localizeParameters(params, jr);
        JasperPrint jp = JasperFillManager.fillReport(jr, params, new JRBeanCollectionDataSource(data));
        JasperExportManager.exportReportToPdfFile(jp, destinationPath);
    }

    /**
     * Prints given Collection data out on printer
     *
     * @param params Print parameters
     * @param data Collection data to be printed
     * @param pressKit File with Jasper press kit (*.jasper)
     * @throws net.sf.jasperreports.engine.JRException
     */
    public static void printFromListToPrinter(Map<String, Object> params, Collection data, File pressKit) throws JRException {
        JasperReport jr = (JasperReport) JRLoader.loadObject(pressKit);
        params = localizeParameters(params, jr);
        JasperPrint jp = JasperFillManager.fillReport(jr, params, new JRBeanCollectionDataSource(data));
        JasperPrintManager.printReport(jp, true);
    }

    /**
     * Save given TableModel data into specified file
     * 
     * @param data Data to be printed
     * @param destinationFileName Destination file path
     */
    public static void printFromTableToCSV(TableModel data, String path) {
        String output = "";
        for (int i = 0; i < data.getColumnCount(); i++) {
            output += prepareForCSV(data.getColumnName(i)) + ";";
        }
        output += "\n";

        for (int i = 0; i < data.getRowCount(); i++) {
            for (int j = 0; j < data.getColumnCount(); j++) {
                output += prepareForCSV(data.getValueAt(i, j).toString()) + ";";
            }
            output += "\n";
        }

        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fWriter = new FileWriter(file);
            fWriter.write(output);
            fWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Save given TableModel data into PDF file
     *
     * @param params Print parameters
     * @param data Data to be printed
     * @param pressKit File with Jasper press kit (*.jasper)
     * @param destinationPath Destination file path
     * @throws net.sf.jasperreports.engine.JRException
     */
    public static void printFromTableToPDF(Map<String, Object> params, TableModel data, File pressKit, String destinationPath) throws JRException {
        JasperReport jr = (JasperReport) JRLoader.loadObject(pressKit);
        data = replaceHeadersByFieldsKey(data, jr);
        params = localizeParameters(params, jr);
        JasperPrint jp = JasperFillManager.fillReport(jr, params, new JRTableModelDataSource(data));
        JasperExportManager.exportReportToPdfFile(jp, destinationPath);
    }

    /**
     * Prints given data out on printer
     *
     * @param params Print parameters
     * @param data Data to be printed
     * @param pressKit File with Jasper press kit (*.jasper)
     * @throws net.sf.jasperreports.engine.JRException
     */
    public static void printFromTableToPrinter(Map<String, Object> params, TableModel data, File pressKit) throws JRException {
        JasperReport jr = (JasperReport) JRLoader.loadObject(pressKit);
        data = replaceHeadersByFieldsKey(data, jr);
        params = localizeParameters(params, jr);
        JasperPrint jp = JasperFillManager.fillReport(jr, params, new JRTableModelDataSource(data));
        JasperPrintManager.printReport(jp, true);
    }

    /**
     * Gets parameters from JasperReport press kit and return their localised version
     * 
     * @param initialParams Into this Map parameters will be added
     * @param jReport JasperReports with requested parameters to localize
     * @return Map with localized parameters
     */
    private static Map<String, Object> localizeParameters(Map<String, Object> initialParams, JasperReport jReport) {
        JRParameter[] jrPars = jReport.getParameters();
        String name;
        ResourceBundle rb = LocalizationManager.getInstance().getResourceBundle("cz.cvut.fel.restauracefel.localization.restaurace_fel_bundle");
        for (JRParameter param : jrPars) {
            name = param.getName();
            // JReports has built-in parameters, which are all in capitals and these parameters should not be localised.
            // For us, user defined parameters are named with camel casing convetion - that's the reason why I check only the first letter for capitals
            if (Character.isUpperCase(name.charAt(0)) || initialParams.containsKey(name)) {
                continue;
            }

            initialParams.put(name, rb.getString(name));
        }

        return initialParams;
    }

    /**
     * Prepares given text for including to CSV, by adding necessary escape sequences
     * 
     * @param text Text for prepare
     * @return CSV safe text
     */
    private static String prepareForCSV(String text) {
        if (text.indexOf('"') != -1) {
            text = text.replaceAll("\"", "\"\"");
            text = "\"" + text + "\"";
        } else if (text.indexOf(';') != -1 || text.startsWith(" ") || text.endsWith(" ")) {
            text = "\"" + text + "\"";
        }

        return text;
    }

    /**
     * Replaces appropriate headers by fields key
     * 
     * @param table Table
     * @param jReport JasperReports with fields
     * @return TableModel with headers replaced by appropriate fields key
     */
    private static TableModel replaceHeadersByFieldsKey(TableModel table, JasperReport jReport) {

        //<editor-fold defaultstate="collapsed" desc="Get headers from table">
        String[] headers = new String[table.getColumnCount()];
        for (int i = 0; i < table.getColumnCount(); i++) {
            headers[i] = table.getColumnName(i);
            //System.out.println(headers[i]);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Get data from table">
        Object[][] data = new Object[(int) table.getRowCount()][(int) table.getColumnCount()];
        for (int row = 0; row < table.getRowCount(); row++) {
            for (int col = 0; col < table.getColumnCount(); col++) {
                data[row][col] = table.getValueAt(row, col);
            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Find and replace headers by key">
        ResourceBundle rb = LocalizationManager.getInstance().getResourceBundle("cz.cvut.fel.restauracefel.localization.restaurace_fel_bundle");
        JRField[] fields = jReport.getFields();
        String loc;
        for (JRField field : fields) {
            loc = rb.getString(field.getName());

            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equals(loc)) {
                    headers[i] = field.getName();
                    break;
                }
            }
        }
        //</editor-fold>

        return new DefaultTableModel(data, headers);
    }
}
