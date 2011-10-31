package cz.cvut.fel.restauracefel.smeny.SmenyController;

import cz.cvut.fel.restauracefel.hibernate.Role;
import cz.cvut.fel.restauracefel.hibernate.Template;
import cz.cvut.fel.restauracefel.hibernate.Typeworkshift;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.smeny.smeny_gui.SmenyViewController;
import cz.cvut.fel.restauracefel.smeny.smeny_main.ResultTableModel;
import cz.cvut.fel.restauracefel.smeny_service.ServiceFacade;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
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
    
    private Object[][] tableTemplateData = null;
    private String [] headerNameTemplate = new String[]{"Šablona"};
    private ResultTableModel modelTemplate = null;            
    
    
    
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
    public void generateDataList() throws FileNotFoundException, RemoteException, NotBoundException {
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
     * Add workshift to table with Workshifts
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
    
    /**
     * Delete workshift from list.
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
    
    public void clearTableWorkShiftData(){
        int j = 0;                
        for (int i = 0; i < tableWorkShiftData.length; i++) {
            tableWorkShiftData[i][j] = null;            
        }
    }
    
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
    
    public void generateTableTemplateData() throws FileNotFoundException, NotBoundException, RemoteException{
        List templates = ServiceFacade.getInstance().getTemplates();
        tableTemplateData = new String[templates.size()][COUNT_PARAMETERS];
        int j=0;
        for(int i=0;i<tableTemplateData.length;i++){
            tableTemplateData[i][j] = ((Template)templates.get(i)).getName();
        }
        modelTemplate = new ResultTableModel(new String[]{"Šablona"}, tableTemplateData);        
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

    public String[] getDataList() {
        return this.dataList;
    }

    public String[] getDataListForDelete() {
        return this.dataListForDelete;
    }
}
