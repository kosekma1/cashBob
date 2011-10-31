package cz.cvut.fel.restauracefel.smeny.SmenyController;

import cz.cvut.fel.restauracefel.hibernate.Role;
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
    private Object[][] tableWorkShiftData = new Object[10][1]; //inicializace
    private ResultTableModel modelWorkShift = new ResultTableModel(new String[]{"Směna"}, tableWorkShiftData);
    
    private DefaultComboBoxModel modelRoles = null;
    

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
     * Add workshift to table with Workshifts
     * @param nameWorkShift 
     */
    public void addWorkShift(String nameWorkShift){
        int j = 0;
        //TODO osetrit preteceni - resp. realokaci  noveho pole
        for(int i=0;i<tableWorkShiftData.length;i++){
            if(tableWorkShiftData[i][j]==null){
                tableWorkShiftData[i][j]=nameWorkShift;
                break;
            }
        }        
    }
    
    /**
     * Print content of array of Work Shifts names. 
     * For testing purposes.
     */
    public void printTestTableWorkShiftData(){
       int j = 0;
        //TODO osetrit preteceni - resp. realokaci  noveho pole
        for(int i=0;i<tableWorkShiftData.length;i++){
            System.out.println(tableWorkShiftData[i][j]);
        }
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
}
