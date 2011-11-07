package cz.cvut.fel.restauracefel.smeny.SmenyController;

import cz.cvut.fel.restauracefel.hibernate.Role;
import cz.cvut.fel.restauracefel.hibernate.Template;
import cz.cvut.fel.restauracefel.hibernate.TemplateList;
import cz.cvut.fel.restauracefel.hibernate.Typeworkshift;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.hibernate.Workshift;
import cz.cvut.fel.restauracefel.smeny.smeny_gui.SmenyViewController;
import cz.cvut.fel.restauracefel.smeny.smeny_main.ResultTableModel;
import cz.cvut.fel.restauracefel.smeny_service.ServiceFacade;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 * Kontrolér pro Smeny. Spojuje gui a model.
 * 
 * @author kosekm
 */
public class SmenyController /*implements IModuleInteface */ {

    private static final SmenyController instance = new SmenyController();
    private SmenyViewController view;
    public User user;
    public String[] prava;
    private Object[][] tableData = null;
    private String[] headerNames = new String[]{"Název", "Od", "Do", "Role", "Status"}; //Header of table    
    private ResultTableModel modelTypeWorkShift = null;
    //form CreateTemplateForm
    final int INIT_SIZE = 10;
    final int COUNT_PARAMETERS = 1;
    final int EXTEND_SIZE = 3; //for extension of table
    private Object[][] tableWorkShiftData = new Object[INIT_SIZE][COUNT_PARAMETERS]; //inicializace
    private ResultTableModel modelWorkShift = new ResultTableModel(new String[]{"Směna"}, tableWorkShiftData);
    private DefaultComboBoxModel modelRoles = null;
    private String[] dataList = null; //for ChooseShiftDialog
    private String[] dataListForDelete = null; //for ChooseDeleteShiftDialog
    private String[] dataListTemplates = null; //for ChooseTemplateDialog
    private Object[][] tableTemplateData = null;
    private String[] headerNameTemplate = new String[]{"Šablona"};
    private ResultTableModel modelTemplate = null;
    public static final String ERROR_ENTERED_DATA = "Chybně zadaná data";
    public static final long MAX_LENGTH_DAYS = 90;
    public static final long DAY_IN_MILLISECONDS = 3600 * 1000 * 24; //day in milliseconds

    public SmenyController() {
        view = SmenyViewController.getInstance();
    }

    public static SmenyController getInstance() {
        return instance;
    }

    public void run(User user, String[] prava) {
        this.prava = prava;
        this.user = user;

        view.run();
    }

    public boolean isActive() {
        return view.isActive();
    }

    /**
     * Generate TableDataModel for table of Type of Shifts
     */
    public void generateTableDataTypeShifts() throws RemoteException, FileNotFoundException, NotBoundException {
        List typeWorkshifts = ServiceFacade.getInstance().getTypeWorkShifts();
        List rolesList = ServiceFacade.getInstance().getAllRoles();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        if (typeWorkshifts != null) {
            tableData = new Object[typeWorkshifts.size()][5];
            int i = 0, j = 0;
            for (Object o : typeWorkshifts) {
                Typeworkshift shift = (Typeworkshift) o;
                tableData[i][j++] = shift.getName();
                tableData[i][j++] = sdf.format(shift.getFromTime());
                tableData[i][j++] = sdf.format(shift.getToTime());

                for (Object obj : rolesList) {
                    Role role = (Role) obj;
                    if (role.getRoleId() == shift.getIdWorkshiftRole()) {
                        tableData[i][j++] = role.getName();
                        break;
                    }
                }
                tableData[i][j++] = shift.getStatus();

                System.out.println(shift.getName() + " "
                        + shift.getFromTime() + " "
                        + shift.getToTime() + " "
                        + shift.getIdWorkshiftRole() + " "
                        + shift.getStatus());
                j = 0;

                i++;
            }
        }
        modelTypeWorkShift = new ResultTableModel(this.headerNames, this.tableData);
    }

    /**
     * Generate model for ComboBox for CreateShiftForm
     */
    public void generateComboBoxRoles(List<Role> rolesList) throws FileNotFoundException, NotBoundException, RemoteException {
        String[] roles = new String[rolesList.size()];
        int iter = 0;
        for (Object obj : rolesList) {
            Role role = (Role) obj;
            roles[iter++] = role.getName();
        }
        modelRoles = new javax.swing.DefaultComboBoxModel(roles);
    }

    /**
     * Generate table with names of shifts for ChooseShiftDialog
     * @throws FileNotFoundException
     * @throws RemoteException
     * @throws NotBoundException 
     */
    public void generateDataListWorkShifts() throws FileNotFoundException, RemoteException, NotBoundException {
        List listTypeWorkshifts = ServiceFacade.getInstance().getTypeWorkShifts();
        dataList = new String[listTypeWorkshifts.size()];
        for (int i = 0; i < dataList.length; i++) {
            dataList[i] = ((Typeworkshift) (listTypeWorkshifts.get(i))).getName();
        }
    }

    /**
     * Generate table with names of shifts for DeleteShiftDialog
     */
    public void generateDataListForDelete() {
        dataListForDelete = new String[tableWorkShiftData.length];
        int j = 0;
        for (int i = 0; i < dataListForDelete.length; i++) {
            dataListForDelete[i] = (String) tableWorkShiftData[i][j];
        }
    }

    /**
     * Add workshift to table with Workshifts in CreateTemplateForm
     * @param nameWorkShift 
     */
    public void addWorkShift(String nameWorkShift) {
        int j = 0;
        boolean changed = false;
        for (int i = 0; i < tableWorkShiftData.length; i++) {
            if (tableWorkShiftData[i][j] == null) {
                tableWorkShiftData[i][j] = nameWorkShift;
                changed = true;
                break;
            }
        }
        if (!changed) { //resize                        
            Object[][] newTable = new Object[tableWorkShiftData.length + EXTEND_SIZE][COUNT_PARAMETERS];
            System.arraycopy(tableWorkShiftData, 0, newTable, 0, tableWorkShiftData.length);
            tableWorkShiftData = newTable;
            addWorkShift(nameWorkShift);
            modelWorkShift = new ResultTableModel(new String[]{"Směna"}, tableWorkShiftData); //create new model only if table is extended
        }
    }

    public void addWorkShiftFromTemplate(String templateName) throws FileNotFoundException, RemoteException, NotBoundException {
        Template template = ServiceFacade.getInstance().findTemplateByName(templateName);
        int templateId = template.getIdTemplate();
        List templateList = ServiceFacade.getInstance().getTemplateListByTemplateId(templateId);
        TemplateList tl = null;
        Typeworkshift tw = null;
        for (int i = 0; i < templateList.size(); i++) {
            tl = (TemplateList) templateList.get(i);
            tw = ServiceFacade.getInstance().getTypeWorkShiftById(tl.getIdTypeworkshift());
            addWorkShift(tw.getName());
        }
    }

    /**
     * Delete workshift from list in ChooseDeleteShiftDialog
     * @param index 
     */
    public void deleteWorkShift(int index) {
        int j = 0;
        tableWorkShiftData[index][j] = null;
        for (int i = 0; i < tableWorkShiftData.length; i++) {
            if (tableWorkShiftData[i][j] == null) {
                if ((i + 1) < tableWorkShiftData.length) {
                    tableWorkShiftData[i][j] = tableWorkShiftData[i + 1][j];
                    tableWorkShiftData[i + 1][j] = null;
                }
            }
        }
    }

    /**
     * 
     */
    public void clearTableWorkShiftData() {
        int j = 0;
        for (int i = 0; i < tableWorkShiftData.length; i++) {
            tableWorkShiftData[i][j] = null;
        }
    }

    /**
     * 
     * @return 
     */
    public Object[][] getTableWorkShiftData() {
        return this.tableWorkShiftData;
    }

    /**
     * Print content of array of Work Shifts names. 
     * For testing purposes.
     */
    public void printTestTableWorkShiftData() {
        int j = 0;
        //TODO osetrit preteceni - resp. realokaci  noveho pole
        for (int i = 0; i < tableWorkShiftData.length; i++) {
            System.out.println(tableWorkShiftData[i][j]);
        }
    }

    /**
     * Generate data for Table with templates names for CreateTemplateForm
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
    public void generateTableTemplateData() throws FileNotFoundException, NotBoundException, RemoteException {
        List templates = ServiceFacade.getInstance().getTemplates();
        if (templates != null) {
            tableTemplateData = new String[templates.size()][COUNT_PARAMETERS];
            int j = 0;
            for (int i = 0; i < tableTemplateData.length; i++) {
                tableTemplateData[i][j] = ((Template) templates.get(i)).getName();
            }
        } else {
            tableTemplateData = new String[1][1];
            tableTemplateData[0][0] = null; //empty table
        }

        modelTemplate = new ResultTableModel(headerNameTemplate, tableTemplateData);
    }

    public void generateDataListTemplates() throws FileNotFoundException, NotBoundException, RemoteException {
        List templates = ServiceFacade.getInstance().getTemplates();
        if (templates != null) {
            dataListTemplates = new String[templates.size()];
            for (int i = 0; i < dataListTemplates.length; i++) {
                dataListTemplates[i] = ((Template) templates.get(i)).getName();
            }
        } else {
            dataListTemplates = new String[1];
            dataListTemplates[0] = null; //empty table
        }
    }
    
    public void generateTableDataPlannedWorkShifts() throws FileNotFoundException, NotBoundException, RemoteException {        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);        
        
        /*int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
         * 
         */
        
        List workShifts = ServiceFacade.getInstance().getAllActiveWorkShifts(cal.getTime());
        
        
        List typeWorkshifts = ServiceFacade.getInstance().getTypeWorkShifts();
        List rolesList = ServiceFacade.getInstance().getAllRoles();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        if (typeWorkshifts != null) {
            tableData = new Object[typeWorkshifts.size()][5];
            int i = 0, j = 0;
            for (Object o : typeWorkshifts) {
                Typeworkshift shift = (Typeworkshift) o;
                tableData[i][j++] = shift.getName();
                tableData[i][j++] = sdf.format(shift.getFromTime());
                tableData[i][j++] = sdf.format(shift.getToTime());

                for (Object obj : rolesList) {
                    Role role = (Role) obj;
                    if (role.getRoleId() == shift.getIdWorkshiftRole()) {
                        tableData[i][j++] = role.getName();
                        break;
                    }
                }
                tableData[i][j++] = shift.getStatus();

                System.out.println(shift.getName() + " "
                        + shift.getFromTime() + " "
                        + shift.getToTime() + " "
                        + shift.getIdWorkshiftRole() + " "
                        + shift.getStatus());
                j = 0;

                i++;
            }
        }
        modelTypeWorkShift = new ResultTableModel(this.headerNames, this.tableData);
    }
    
    

    public String[] getDataListTemplates() {
        return this.dataListTemplates;
    }

    /**
     * @return the modelTypeWorkShift
     */
    public ResultTableModel getModelTypeWorkShift() {
        return modelTypeWorkShift;
    }

    public ResultTableModel getModelWorkShift() {
        return modelWorkShift;
    }

    /**
     * @return the modelRoles
     */
    public DefaultComboBoxModel getModelRoles() {
        return modelRoles;
    }

    public ResultTableModel getModelTemplate() {
        return modelTemplate;
    }

    public String[] getDataListWorkShifts() {
        return this.dataList;
    }

    public String[] getDataListForDelete() {
        return this.dataListForDelete;
    }

    public boolean isTableEmpty(Object[][] table) {
        int j = 0;
        boolean empty = true;
        for (int i = 0; i < table.length; i++) {
            if (table[i][j] != null) {
                empty = false;
                break;
            }
        }
        return empty;
    }

    /**
     * Save template in CreateTemplateForm
     * @param templateName
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
    public boolean saveTemplate(String templateName) throws FileNotFoundException, NotBoundException, RemoteException {
        boolean process = true;
        if (templateName.trim().equals("")) {
            showErrorMessage("Zadejte název šablony.", SmenyController.ERROR_ENTERED_DATA);
            process = false;
        }
        if (templateName.trim().length() > 50) {
            showErrorMessage("Příliš dlouhý název šablony (max. 50 znaků).", SmenyController.ERROR_ENTERED_DATA);
            process = false;
        }
        if (ServiceFacade.getInstance().findTemplateByName(templateName) != null) {
            showErrorMessage("Šablona stejného názvu již existuje.", SmenyController.ERROR_ENTERED_DATA);
            process = false;
        }

        Object[][] table = SmenyController.getInstance().getTableWorkShiftData();

        if (isTableEmpty(table)) {
            showErrorMessage("Vložte alespoň jednu směnu.", SmenyController.ERROR_ENTERED_DATA);
            process = false;
        }

        if (process) {
            //save name of the template
            Template template = new Template();
            template.setName(templateName);
            ServiceFacade.getInstance().creatNewTemplate(template);

            //save workshifts connected with saved template
            template = ServiceFacade.getInstance().findTemplateByName(templateName);
            int idTemplate = template.getIdTemplate();

            Typeworkshift tws = null;
            int j = 0;
            for (int i = 0; i < table.length; i++) {
                if (table[i][j] != null) {
                    tws = ServiceFacade.getInstance().findTypeworkshiftByName((String) table[i][j]);
                    ServiceFacade.getInstance().createNewTemplateList(idTemplate, tws.getIdTypeWorkshift());
                }
            }
            showInformationMessage("Šablona uložena.", "Úspěšné uložení.");
        }
        return process;
    }

    /**
     * Save Typeworkshift to database entred from CreateShiftForm
     * @param shiftName
     * @param roleName
     * @param dateFrom
     * @param dateTo
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
    public boolean saveTypeWorkshift(String shiftName, String roleName, Date dateFrom, Date dateTo) throws FileNotFoundException, NotBoundException, RemoteException {
        boolean process = true;
        if (shiftName.trim().equals("")) {
            this.showErrorMessage("Zadejte název směny.", SmenyController.ERROR_ENTERED_DATA);
            process = false;
        }
        if (shiftName.trim().length() > 50) {
            showErrorMessage("Příliš dlouhý název směny (max. 50 znaků).", SmenyController.ERROR_ENTERED_DATA);
            process = false;
        }

        if (ServiceFacade.getInstance().findTypeworkshiftByName(shiftName) != null) {
            showErrorMessage("Typ směny stejného názvu již existuje.", SmenyController.ERROR_ENTERED_DATA);
            process = false;
        }

        if (dateFrom.equals(dateTo)) {
            showErrorMessage("Čas \"Od\" musí být různý \"Do.\"", SmenyController.ERROR_ENTERED_DATA);
            process = false;
        }

        if (process) {
            Typeworkshift tw = new Typeworkshift();
            tw.setName(shiftName);

            tw.setFromTime(dateFrom);
            tw.setToTime(dateTo);

            tw.setStatus(1);
            Role role = ServiceFacade.getInstance().getRoleByName(roleName);
            tw.setIdWorkshiftRole(role.getRoleId());
            //System.out.println("TW: " + tw.getName() + " " + tw.getFromTime() + " " + tw.getToTime());
            ServiceFacade.getInstance().createNewTypewWorkShift(tw);

            showInformationMessage("Typ směny byl uložen.", "Úspěšné uložení.");
        }

        return process;
    }

    /**
     * Count days from milliseconds
     * @param time (in milliseconds)
     * @return days
     */
    private long getDays(long time) {
        return time / 1000 / 3600 / 24;
    }

    /**
     * 
     * @param dateFrom
     * @param dateTo
     * @param typeWorkShifts
     * @return
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
    public boolean saveWorkShifts(Date dateFrom, Date dateTo) throws FileNotFoundException, NotBoundException, RemoteException {
        boolean process = true;
        if (dateFrom == null || dateTo == null) {
            showErrorMessage("Zadejte obě data.", SmenyController.ERROR_ENTERED_DATA);
            process = false;
        }
        if (dateFrom.after(dateTo)) {
            showErrorMessage("Datum do musí být větší než datum od", SmenyController.ERROR_ENTERED_DATA);
            process = false;
        }

        long resultDays = getDays(dateTo.getTime() - dateFrom.getTime());
        if (resultDays > MAX_LENGTH_DAYS) {
            showErrorMessage("Maximální doba na plánování jsou 3 měsíce.", SmenyController.ERROR_ENTERED_DATA);
            process = false;
        }

        Object[][] table = SmenyController.getInstance().getTableWorkShiftData();

        if (isTableEmpty(table)) {
            showErrorMessage("Vložte alespoň jednu směnu.", SmenyController.ERROR_ENTERED_DATA);
            process = false;
        }

        if (process) {
            Typeworkshift tws = null;
            int idTypeWorkShift = 0;
            long dateFromMills = dateFrom.getTime();
            long dateToMills = dateTo.getTime();
            Date tempDate = null;
            int j = 0;
            for (int i = 0; i < table.length; i++) {
                if (table[i][j] != null) {
                    tws = ServiceFacade.getInstance().findTypeworkshiftByName((String) table[i][j]);
                    idTypeWorkShift = tws.getIdTypeWorkshift();
                    this.showInformationMessage("Smena: " + tws.getName() + " : ID " + idTypeWorkShift, "TEST");
                    //save workshift to each day
                    do {
                        tempDate = new Date(dateFromMills);
                        showInformationMessage("Datum: " + tempDate, "TEST");
                        ServiceFacade.getInstance().createNewWorkshift(tempDate, idTypeWorkShift);
                        dateFromMills += DAY_IN_MILLISECONDS;
                    } while (dateFromMills <= dateToMills);
                    dateFromMills = dateFrom.getTime();                    
                }
            }
            showInformationMessage("Pracovní směny uloženy.", "Úspěšné uložení dat");
        }

        return process;
    }

    /**
     * Show error message in stand-alone dialog window.
     * @param error
     * @param title 
     */
    public void showErrorMessage(String error, String title) {
        JOptionPane.showMessageDialog(null, error, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Show information message in stand-alone dialog window.
     * @param error
     * @param title 
     */
    public void showInformationMessage(String error, String title) {
        JOptionPane.showMessageDialog(null, error, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
