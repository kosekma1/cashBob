package cz.cvut.fel.restauracefel.smeny.SmenyController;

import cz.cvut.fel.restauracefel.hibernate.Attendance;
import cz.cvut.fel.restauracefel.hibernate.Role;
import cz.cvut.fel.restauracefel.hibernate.Template;
import cz.cvut.fel.restauracefel.hibernate.TemplateList;
import cz.cvut.fel.restauracefel.hibernate.Typeworkshift;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.hibernate.UserRole;
import cz.cvut.fel.restauracefel.hibernate.Workshift;
import cz.cvut.fel.restauracefel.smeny.smeny_gui.SmenyViewController;
import cz.cvut.fel.restauracefel.smeny.smeny_main.ResultTableModel;
import cz.cvut.fel.restauracefel.smeny_service.ServiceFacade;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 * Kontroler pro vsechny formulare v modulu pro planovani pracovnich smen. 
 * 
 * @author Martin Kosek
 */
public class SmenyController {

    private static final SmenyController instance = new SmenyController();
    private SmenyViewController view;
    public User user;
    public String[] prava;
    private Object[][] tableData = null;
    private String[] headerNames = new String[]{"Název", "Od", "Do", "Role", "Status"}; //hlavicka tabulky
    private ResultTableModel modelTypeWorkShift = null;
    //Formular CreateTemplateForm
    final int INIT_SIZE = 10;
    final int COUNT_PARAMETERS = 1;
    final int EXTEND_SIZE = 3; //tabulku rozsiruje o 3
    private Object[][] tableWorkShiftData = new Object[INIT_SIZE][COUNT_PARAMETERS]; //inicializace
    private ResultTableModel modelWorkShift = new ResultTableModel(new String[]{"Směna"}, tableWorkShiftData);
    private DefaultComboBoxModel modelRoles = null;
    private String[] dataList = null; //pro ChooseShiftDialog
    private String[] dataListForDelete = null; //pro ChooseDeleteShiftDialog
    private String[] dataListTemplates = null; //pro ChooseTemplateDialog
    private Object[][] tableTemplateData = null;
    private String[] headerNameTemplate = new String[]{"Šablona"};
    private ResultTableModel modelTemplate = null;
    public static final String ERROR_ENTERED_DATA = "Chybně zadaná data";
    public static final long MAX_LENGTH_DAYS = 90;
    public static final long DAY_IN_MILLISECONDS = 3600 * 1000 * 24; //den v milisekundach
    //Formular planned workshifts
    private Object[][] tablePlannedWorkShift = null;
    private ResultTableModel modelPlannedWorkShift = null;
    //Users
    private String[] dataListEmployees = null; //pro ChooseEmployeeDialog
    private int[] userIds = null;
    //Leader and employee overview
    private String[] headerOverView = new String[]{"Datum a čas", "Typ směny", "Přihlášení", "Obsazení", "Potvrzení"};
    private Object[][] tableWorkShiftOverview = null;
    private ResultTableModel modelOverviewWorkShift = null;
    private int[] workShiftIds = null; //slouzi pro ulozeni id pracovnich smen, ktere se zobrazuji v tabulce
    private String[] datalListLoginUsers = null; //pro chooseEmployeeDialog
    private int[] usersAttendaceIds = null; //pro chooseEmployeeDialog - evidence id
    private Date dateFrom = null;
    private Date dateTo = null;
    private int week = 0;
    private Locale currentLocale = new Locale("cs", "CZ");
    //Texty pro dialogova okna
    public static final String INFORMATION_TEXT = "Informace";
    public static final String ERROR_TEXT = "Chyba";

    //Konstanty pro filtry
    public enum WorkShiftFilter {

        ALL, LOGIN, LOGIN_USER, OCCUPATION, UNOCCUPATION, UNCONFIRMED, CONFIRMED, REQUEST_CANCEL, OCCUPATION_USER, ROLE_USER, CONFIRMED_USER,
        LOGIN_OR_OCCUPATION_USER
    }

    public enum DateFilter {

        ALL_DAYS, COMMON_DAYS, WEEKENDS
    }

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
     * Generuje TableDataModel pro tabulku typu smen.     
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
     * Generuje model pro ComboBox pro formulare CreateShiftForm     
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
     * Generuje tabulku se jmeny smen pro dialog ChooseShiftDialo     
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
     * Generuje tabulku se jmeno smen pro dialog DeleteShiftDialog     
     */
    public void generateDataListForDelete() {
        dataListForDelete = new String[tableWorkShiftData.length];
        int j = 0;
        for (int i = 0; i < dataListForDelete.length; i++) {
            dataListForDelete[i] = (String) tableWorkShiftData[i][j];
        }
    }

    /**
     * Generuje seznam zamestnancu pro formular OverviewLeaderShiftForm     .
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
    public void generateDataListEmployees() throws FileNotFoundException, NotBoundException, RemoteException {
        List usersList = ServiceFacade.getInstance().getAllUsers();
        List userRoleList = null;

        if (usersList == null || usersList.isEmpty()) {
            dataListEmployees = new String[1];
            dataListEmployees[1] = "";
        } else {
            dataListEmployees = new String[usersList.size()];
            userIds = new int[usersList.size()];
            User userTemp = null;
            String userRolesText = "(";
            Role role = null;
            int i = 0, j = 0;
            for (Object o : usersList) {
                userTemp = (User) o;
                userRoleList = ServiceFacade.getInstance().getUserRoleByUserId(userTemp.getUserId());
                for (Object obUserRole : userRoleList) {
                    role = ((UserRole) obUserRole).getRole();
                    userRolesText += role.getName() + ",";
                }
                userRolesText = userRolesText.substring(0, userRolesText.length() - 1);//remove ending comma
                dataListEmployees[i++] = userTemp.getFirstName() + " " + userTemp.getLastName() + " " + userRolesText + ")";
                userIds[j++] = userTemp.getUserId();
                userRolesText = "(";
            }
        }
    }

    public String[] getDataListEmployees() {
        return dataListEmployees;
    }

    /**
     * Vklada smenu do tabulky se smenami pro vytvoreni sablony ve formulari CreateTemplateForm     
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
        if (!changed) { //automaticke rozsireni tabulky                  
            Object[][] newTable = new Object[tableWorkShiftData.length + EXTEND_SIZE][COUNT_PARAMETERS];
            System.arraycopy(tableWorkShiftData, 0, newTable, 0, tableWorkShiftData.length);
            tableWorkShiftData = newTable;
            addWorkShift(nameWorkShift);
            modelWorkShift = new ResultTableModel(new String[]{"Směna"}, tableWorkShiftData);
        }
    }

    /**
     * Vklada do tabulky smeny ktere jsou ulozeny v sablone. Pouzito ve formulari CreateTemplateForm.           
     * @param templateName  Nazev smeny
     * @throws FileNotFoundException
     * @throws RemoteException
     * @throws NotBoundException 
     */
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
     * Smaze smenu ze seznamu v dialogu ChooseDeleteShiftDialog     
     * @param index Index na kterem se smena nachazi
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
     * Smaze tabulku se smenami, ktera se zobrazuje ve formulari CreateTemplateForm     
     */
    public void clearTableWorkShiftData() {
        int j = 0;
        for (int i = 0; i < tableWorkShiftData.length; i++) {
            tableWorkShiftData[i][j] = null;
        }
    }

    /**
     * 
     * @return Tabulka se smenami
     */
    public Object[][] getTableWorkShiftData() {
        return this.tableWorkShiftData;
    }

    /**
     * Vytiskne obsah tabulky(dvourozmerneho pole) smen     
     * Pouze pro testovaci ucely
     */
    public void printTestTableWorkShiftData() {
        int j = 0;
        for (int i = 0; i < tableWorkShiftData.length; i++) {
            System.out.println(tableWorkShiftData[i][j]);
        }
    }

    /**
     * Generuje data pro tabulku sablon pro formular CreateTemplateForm     
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
            tableTemplateData[0][0] = null; //prazdna tabulka
        }

        modelTemplate = new ResultTableModel(headerNameTemplate, tableTemplateData);
    }

    /**
     * Generuje seznam sablon pro ChooseTemplateDialog
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
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

    /**
     * Generuje tabulku a nastavuje model planovanych smen od aktualniho data
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
    public void generateTableDataPlannedWorkShifts() throws FileNotFoundException, NotBoundException, RemoteException {

        Date actualDate = new Date();
        List workShifts = ServiceFacade.getInstance().getAllActiveWorkShifts(actualDate);
        int columns = 2;

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        if (workShifts == null || workShifts.isEmpty()) {
            tablePlannedWorkShift = new Object[1][columns];
            tablePlannedWorkShift[0][0] = "";
            tablePlannedWorkShift[0][1] = "";

        } else {
            tablePlannedWorkShift = new Object[workShifts.size()][2];
            Workshift workShift = null;
            int i = 0, j = 0;
            List typeWorkshifts = ServiceFacade.getInstance().getTypeWorkShifts();

            for (Object o : workShifts) { //set Date of planned workshift
                workShift = (Workshift) o;
                tablePlannedWorkShift[i][j++] = sdf.format(workShift.getDateShift());

                Typeworkshift typeWorkShift = null;
                int idTypeWorkShift = workShift.getIdTypeWorkshift();
                for (Object ot : typeWorkshifts) { //set name of Workshift
                    typeWorkShift = (Typeworkshift) ot;
                    if (typeWorkShift.getIdTypeWorkshift() == idTypeWorkShift) {
                        tablePlannedWorkShift[i][j++] = typeWorkShift.getName();
                        break;
                    }

                }
                j = 0;
                i++;
            }
        }
        modelPlannedWorkShift = new ResultTableModel(new String[]{"Datum", "Směna"}, tablePlannedWorkShift);
    }

    public List filterWorkShifts(WorkShiftFilter filter, List workShifts) throws FileNotFoundException, NotBoundException, RemoteException {

        Workshift ws = null;
        ListIterator iter = workShifts.listIterator();
        switch (filter) {
            case ALL: //do nothing eg. without filter - show everything
                break;
            case ROLE_USER:
                List userRoles = ServiceFacade.getInstance().getUserRoleByUserId(user.getUserId());
                UserRole userRole = null;
                int roleIdUser = 0;
                int roleIdWorkShift = 0;
                Typeworkshift tws = null;
                List tempWorkShifts = new ArrayList();
                while (iter.hasNext()) {
                    ws = (Workshift) iter.next();
                    for (Object o : userRoles) {
                        userRole = (UserRole) o;
                        roleIdUser = userRole.getRole().getRoleId();
                        tws = ServiceFacade.getInstance().getTypeWorkShiftById(ws.getIdTypeWorkshift());
                        roleIdWorkShift = tws.getIdWorkshiftRole();
                        if (roleIdUser == roleIdWorkShift) {
                            tempWorkShifts.add(ws);
                            break;
                        }
                    }
                }
                workShifts = tempWorkShifts;
                break;
            case OCCUPATION_USER: //remove all workshifts that are not occupied with current user

                while (iter.hasNext()) {
                    ws = (Workshift) iter.next();
                    if (ws.getIdUser() == null || !(ws.getIdUser().equals(user.getUserId()))) {
                        iter.remove();
                    }
                }
                break;
            case LOGIN_USER:
                List attendances = null;
                int userId = user.getUserId();
                List tempWorkShifts1 = new ArrayList();
                while (iter.hasNext()) {
                    ws = (Workshift) iter.next();
                    attendances = ServiceFacade.getInstance().getAttendaceByWorkShiftId(ws.getIdWorkshift());
                    if (attendances != null) {
                        for (Object o : attendances) {
                            Attendance att = (Attendance) o;
                            if (att.getIdUser() == userId) {
                                tempWorkShifts1.add(ws);
                                break;
                            }
                        }
                    }
                }
                workShifts = tempWorkShifts1;
                break;
            case OCCUPATION: //select only occupied workshifts               
                while (iter.hasNext()) {
                    ws = (Workshift) iter.next();
                    if (ws.getIdUser() == null) {
                        iter.remove();
                    }
                }
                break;
            case UNOCCUPATION: //select only unoccupied workshifts               
                while (iter.hasNext()) {
                    ws = (Workshift) iter.next();
                    if (ws.getIdUser() != null) {
                        iter.remove();
                    }
                }
                break;
            case CONFIRMED: //select only confirmed workshifts               
                while (iter.hasNext()) {
                    ws = (Workshift) iter.next();
                    if (ws.getUserSubmit() == null || !ws.getUserSubmit().equals("Potvrzeno")) {
                        iter.remove();
                    }
                }
                break;
            case UNCONFIRMED: //select only unconfirmed or request cancel workshifts               
                while (iter.hasNext()) {
                    ws = (Workshift) iter.next();
                    if (ws.getUserSubmit() != null) {
                        if (ws.getUserSubmit().equals("Potvrzeno")) {
                            iter.remove();
                        }
                    }
                }
                break;
            case REQUEST_CANCEL: //select only requests for cancel workshifts               
                while (iter.hasNext()) {
                    ws = (Workshift) iter.next();
                    if (ws.getUserSubmit() == null || !ws.getUserSubmit().equals("Zažádáno o zrušení")) {
                        iter.remove();
                    }
                }
                break;
            case CONFIRMED_USER:
                while (iter.hasNext()) {
                    ws = (Workshift) iter.next();
                    if (ws.getIdUser() != user.getUserId() && (ws.getUserSubmit() == null || !ws.getUserSubmit().equals("Potvrzeno"))) {
                        iter.remove();
                    }
                }
                break;
            case LOGIN_OR_OCCUPATION_USER:
                List attendanceList = null;
                int user_Id = user.getUserId();
                List temp_WorkShifts = new ArrayList();
                while (iter.hasNext()) {
                    ws = (Workshift) iter.next();
                    if (ws.getIdUser() != null && user_Id == ws.getIdUser()) { //idUser can be null
                        temp_WorkShifts.add(ws);
                    } else {
                        attendanceList = ServiceFacade.getInstance().getAttendaceByWorkShiftId(ws.getIdWorkshift());
                        if (attendanceList != null) {
                            for (Object o : attendanceList) {
                                Attendance att = (Attendance) o;
                                if (att.getIdUser() == user_Id) {
                                    temp_WorkShifts.add(ws);
                                    break;
                                }
                            }
                        }
                    }
                }
                workShifts = temp_WorkShifts;
                break;
            default:
                break;
        }
        return workShifts;
    }

    /**
     * Generuje data pro tabulku, kterea se zobrazuje ve formularich 
     * OverviewLeaderShiftForm a OverviewEmployeeShiftForm      
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
    public void generateTableWorkShiftsOverview(WorkShiftFilter filter) throws FileNotFoundException, NotBoundException, RemoteException {
        List workShifts = ServiceFacade.getInstance().getWorkshiftsFromTo(this.dateFrom, this.dateTo); //get all planned workshift from today, not history                           
        workShifts = filterWorkShifts(filter, workShifts);

        int columns = 5; //table has 5 columns

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy", currentLocale);
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm", currentLocale);
        if (workShifts == null || workShifts.isEmpty()) {
            tableWorkShiftOverview = new Object[1][columns];
            for (int i = 0; i < 5; i++) {
                tableWorkShiftOverview[0][i] = null;
            }
            showMessageDialogInformation("Žádné směny.", INFORMATION_TEXT);
        } else {
            tableWorkShiftOverview = new Object[workShifts.size()][columns];
            Workshift workShift = null;
            workShiftIds = new int[workShifts.size()];

            int i = 0, j = 0, k = 0;
            List typeWorkshifts = ServiceFacade.getInstance().getTypeWorkShifts();
            Typeworkshift typeWorkShift = null;
            for (Object o : workShifts) { //set Date of planned workshift
                workShift = (Workshift) o;
                int idTypeWorkShift = workShift.getIdTypeWorkshift();
                for (Object ot : typeWorkshifts) { //set date and name of Workshift
                    typeWorkShift = (Typeworkshift) ot;
                    if (typeWorkShift.getIdTypeWorkshift() == idTypeWorkShift) {
                        tableWorkShiftOverview[i][j++] = sdfDate.format(workShift.getDateShift()) + " " + sdfTime.format(typeWorkShift.getFromTime()) + "-" + sdfTime.format(typeWorkShift.getToTime());
                        tableWorkShiftOverview[i][j++] = typeWorkShift.getName();
                        break;
                    }
                }
                List attendanceList = ServiceFacade.getInstance().getAttendaceByWorkShiftId(workShift.getIdWorkshift());
                if (attendanceList == null) {
                    tableWorkShiftOverview[i][j++] = "Nikdo není přihlášen";
                } else { //read users from Attendance and add to table
                    StringBuilder sb = new StringBuilder();
                    User userTemp = null;
                    for (Object att : attendanceList) {
                        int idUser = ((Attendance) att).getIdUser();
                        userTemp = ServiceFacade.getInstance().getUserById(idUser);
                        sb.append(userTemp.getFirstName());
                        sb.append(" ");
                        sb.append(userTemp.getLastName());
                        sb.append(",");
                    }
                    tableWorkShiftOverview[i][j++] = (sb.toString()).substring(0, sb.toString().length() - 1);//remove last comma
                }

                //occupy user
                StringBuilder sb = new StringBuilder();
                if (workShift.getIdUser() == null) {
                    tableWorkShiftOverview[i][j++] = "Neobsazeno";
                } else { //read user in workshit and add to table his/her full name
                    User userOccupy = ServiceFacade.getInstance().getUserById(workShift.getIdUser());
                    sb.append(userOccupy.getFirstName());
                    sb.append(" ");
                    sb.append(userOccupy.getLastName());
                    tableWorkShiftOverview[i][j++] = sb.toString();
                }

                tableWorkShiftOverview[i][j++] = workShift.getUserSubmit() == null ? "Nepotvrzeno" : workShift.getUserSubmit();

                workShiftIds[k++] = workShift.getIdWorkshift();
                j = 0;
                i++;
            }
        }
        modelOverviewWorkShift = new ResultTableModel(this.headerOverView, tableWorkShiftOverview);

    }

    /**
     * Ulozi uzivatele do tabulky prihlasenych na smenu (tabulka Attendace)
     * @param userId    id prihalsovaneho uzivatele
     * @param workShiftId   id smeny na kterou se prihlasuje
     */
    public void saveUserToWorkShift(int userIndexId, int workShiftIndexId) throws FileNotFoundException, NotBoundException, RemoteException {
        int userId = userIds[userIndexId];
        int workShiftId = this.workShiftIds[workShiftIndexId];
        Attendance att = ServiceFacade.getInstance().getAttendaceByWorkShiftAndUser(workShiftId, userId);
        if (att == null) {
            loginUserToWorkShifts(userId, workShiftId);
        } else {
            this.showErrorMessage("Uživatel je již přihlášen.", ERROR_TEXT);
        }
    }

    /**
     * Ulozi aktualne prihlasneho uzivatele (prihlasneho do aplikace) 
     * do tabulky prihlasenych na smenu (tabulka Attendace)
     * @param workShiftId   id smeny na kterou se uzivatel hlasi
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
    public void saveCurrentUserToWorkShift(int workShiftId) throws FileNotFoundException, NotBoundException, RemoteException {
        Attendance att = ServiceFacade.getInstance().getAttendaceByWorkShiftAndUser(workShiftId, user.getUserId());
        if (att == null) {
            loginUserToWorkShifts(user.getUserId(), workShiftId);
            showMessageDialogInformation("Uživatel je úspěšně přihlášen.", INFORMATION_TEXT);
        } else {
            this.showErrorMessage("Uživatel je již přihlášen", ERROR_TEXT);
        }
    }

    //Save user with userId to all workshifts with the same type workshift at the same date.
    /**
     * Ulozi/prihlasi uzivatele do vsech stejnych typu smen, urcenych dle smeny s workShiftId
     * v danem dni.
     * @param userId    id prihlasovaneho uzivatele
     * @param workShiftId   id vzchozi smeny na kterou se hlasi
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
    public void loginUserToWorkShifts(int userId, int workShiftId) throws FileNotFoundException, NotBoundException, RemoteException {
        Workshift ws = ServiceFacade.getInstance().getWorkshiftById(workShiftId);
        int workShiftTypeId = ws.getIdTypeWorkshift();
        List listWorkshifts = ServiceFacade.getInstance().getWorkshiftsFromTo(ws.getDateShift(), ws.getDateShift());

        Workshift workShift = null;

        //kontrola jestli neni obsazen ve smenach stejneho typu v dany den
        boolean isFree = true;
        for (Object o : listWorkshifts) {
            workShift = (Workshift) o;
            if (workShift.getIdUser() != null
                    && workShift.getIdUser() == userId
                    && workShift.getIdTypeWorkshift() == workShiftTypeId) {
                isFree = false;
                break;
            }
        }

        if (isFree) {
            for (Object o : listWorkshifts) {
                workShift = (Workshift) o;
                if (workShift.getIdTypeWorkshift() == workShiftTypeId) {
                    ServiceFacade.getInstance().createNewAttendance(userId, workShift.getIdWorkshift());
                }
            }
        } else {
            this.showErrorMessage("Uživatel je již obsazen na směnu stejného typu.", ERROR_TEXT);
        }
    }

    /**
     * Generuje seznam prihlasenych uzivatelu na smenu s workShiftId pro dialog ChooseOcuppyEmployeeDialog    
     * @param workShiftId   id smeny
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
    public void generateDataListLoginUsers(int workShiftId) throws FileNotFoundException, NotBoundException, RemoteException {
        List attendanceList = ServiceFacade.getInstance().getAttendaceByWorkShiftId(workShiftId);
        if (attendanceList == null || attendanceList.isEmpty()) {
            this.showErrorMessage("Nikdo není přihlášen.", ERROR_TEXT);
            usersAttendaceIds = null;
            datalListLoginUsers = new String[0];
        } else {
            Attendance attendance = null;
            int userId = 0;
            int i = 0;
            StringBuilder sb = new StringBuilder();
            User userTemp = null;
            datalListLoginUsers = new String[attendanceList.size()];
            usersAttendaceIds = new int[attendanceList.size()];
            for (Object item : attendanceList) {
                attendance = (Attendance) item;
                userId = attendance.getIdUser();
                userTemp = (User) ServiceFacade.getInstance().getUserById(userId);
                sb.append(userTemp.getFirstName());
                sb.append(" ");
                sb.append(userTemp.getLastName());
                datalListLoginUsers[i] = sb.toString();
                sb.delete(0, sb.length());
                usersAttendaceIds[i] = userTemp.getUserId();
                i++;
            }
        }
    }

    /**
     * Prihlaseny uzivatel se ulozi jako obsazeny uzivatel na konkretni smenu.
     * Bude smazan z prihlasenych.     
     * @param userId    id uzivatele
     * @param workShiftId   id pracovni smeny
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
    public void saveOccupyUser(int userId, int workShiftId) throws FileNotFoundException, NotBoundException, RemoteException {
        Workshift ws = ServiceFacade.getInstance().getWorkshiftById(workShiftId);
        if (ws.getIdUser() != null) {
            this.showErrorMessage("Směna je již obsazena.", ERROR_TEXT);
        } else {
            boolean result = ServiceFacade.getInstance().updateWorkshiftLogin(workShiftId, userId);
            if (result) {
                ServiceFacade.getInstance().deleteAttendences(ws.getDateShift(), ws.getIdTypeWorkshift(), userId);
            }
        }

    }

    /**
     * Zrusi obsazeni smeny danym uzivatelem.     
     * @param workShiftId   id uzivatele
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
    public void cancelOccupationWorkshift(JTable table) throws FileNotFoundException, NotBoundException, RemoteException {
        int rowNumber = table.getSelectedRow();
        boolean resultUpdate = false;
        int workShiftId = -1;
        if (rowNumber > -1) {
            int resultUI = showConfirmDialogStandard("Opravdu zrušit obsazení?", "Dotaz");
            if (resultUI == 0) {
                workShiftId = getWorkShiftIdFromOverViewTable(rowNumber);
                resultUpdate = ServiceFacade.getInstance().updateWorkshiftLogin(workShiftId, null);
                if (resultUpdate) {
                    updateOccupationMessage(workShiftId, "Nepotvrzeno");
                    showMessageDialogInformation("Obsazení směny bylo uvolněno.", INFORMATION_TEXT);
                } else {
                    this.showErrorMessage("Nepodařilo se zrušit obsazení směny.", ERROR_TEXT);
                }
            }
        } else {
            showMessageDialogInformation("Vyberte řádek", INFORMATION_TEXT);
        }
    }

    /**
     * Odhlasi aktualne prihlaseneho uzivatele ze smeny na kterou je prihlasen.     
     * 
     * @param workShiftId   id smeny
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
    public void logoutCurrentUserFromWorkShift(int workShiftId) throws FileNotFoundException, NotBoundException, RemoteException {
        Attendance att = ServiceFacade.getInstance().getAttendaceByWorkShiftAndUser(workShiftId, user.getUserId());
        if (att == null) {
            this.showErrorMessage("Na tuto směnu nejste přihlášen/a.", ERROR_TEXT);
        } else {
            Workshift ws = ServiceFacade.getInstance().getWorkshiftById(workShiftId);
            ServiceFacade.getInstance().deleteAttendences(ws.getDateShift(), ws.getIdTypeWorkshift(), this.user.getUserId());
            this.showMessageDialogInformation("Byl/a jste úspěšně odhlášen ze směny.", INFORMATION_TEXT);
        }
    }

    /**
     * Aktualizuje textovou informaci o stavu smeny pro aktualne prihlaseneho uzivatele
     * @param idWorkshift   id smeny
     * @param message   informace
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
    public void updateOccupationMessageUser(int idWorkshift, String message) throws FileNotFoundException, NotBoundException, RemoteException {
        Workshift ws = ServiceFacade.getInstance().getWorkshiftById(idWorkshift);
        if (ws.getIdUser().equals(user.getUserId())) {
            ServiceFacade.getInstance().updateWorkshiftOccupation(ws.getIdWorkshift(), message);
            this.showMessageDialogInformation("Akce úspěšně provedena.", INFORMATION_TEXT);
        } else {
            this.showErrorMessage("Akce se nezdařila. Nejste obsazen/a do vybrané směny.", ERROR_TEXT);
        }
    }

    /**
     * Aktualizuje textovou informaci o stavu smeny.
     * @param idWorkshift
     * @param message
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
    public void updateOccupationMessage(int idWorkshift, String message) throws FileNotFoundException, NotBoundException, RemoteException {
        ServiceFacade.getInstance().updateWorkshiftOccupation(idWorkshift, message);
    }

    public String[] getDataListLoginUsers() {
        return this.datalListLoginUsers;
    }

    public int getWorkShiftIdFromOverViewTable(int row) {
        return this.workShiftIds[row];
    }

    public ResultTableModel getModelOverviewWorkShift() {
        return this.modelOverviewWorkShift;
    }

    public String[] getDataListTemplates() {
        return this.dataListTemplates;
    }

    public ResultTableModel getModelTypeWorkShift() {
        return modelTypeWorkShift;
    }

    public ResultTableModel getModelWorkShift() {
        return modelWorkShift;
    }

    public DefaultComboBoxModel getModelRoles() {
        return modelRoles;
    }

    public ResultTableModel getModelTemplate() {
        return modelTemplate;
    }

    public ResultTableModel getModelPlannedWorkShift() {
        return this.modelPlannedWorkShift;
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
     * Ulozi sablonu daneho nazvu.    
     * 
     * @param templateName  nazev sablony
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
            showMessageDialogInformation("Šablona uložena.", "Úspěšné uložení.");
        }
        return process;
    }

    /**
     * Smaze natrvalo sablonu.
    
     * @param templateName  nazev sablony
     * @throws FileNotFoundException
     * @throws RemoteException
     * @throws NotBoundException 
     */
    public void deleteTemplateByName(String templateName) throws FileNotFoundException, RemoteException, NotBoundException {
        if (templateName == null || templateName.trim().equals("")) {
            this.showErrorMessage("Šablona nebyla vybrána.", ERROR_TEXT);
        } else {
            ServiceFacade.getInstance().deleteTemplateByName(templateName);
            this.showMessageDialogInformation("Šablona úspěšně smazána.", INFORMATION_TEXT);
        }
    }

    /**
     * Ulozi typ pracovni smeny do databaze. Pouzito ve formulari CreateShiftForm     
     * @param shiftName nazev smeny
     * @param roleName  nazev role
     * @param dateFrom  cas trvani smeny "od"
     * @param dateTo    cas trvani smeny "do"
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
            ServiceFacade.getInstance().createNewTypewWorkShift(tw);

            showMessageDialogInformation("Typ směny byl uložen.", INFORMATION_TEXT);
        }

        return process;
    }

    /**
     * Spocita pocet dni z milisekund.     
     * @param time cas v milisekundach
     * @return days pocet dni
     */
    private long getDays(long time) {
        return time / 1000 / 3600 / 24;
    }

    /**
     * Ulozi pracovni smenu do dnu v rozmezi zadanych datumu.     
     * @param dateFrom  datum "od"
     * @param dateTo    datum "do"
     * @param filter    filtr dle ktereho se smeny ukladaji
     * @return
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException 
     */
    public boolean saveWorkShifts(Date dateFrom, Date dateTo, DateFilter filter) throws FileNotFoundException, NotBoundException, RemoteException {
        boolean process = true;
        if (dateFrom == null || dateTo == null) {
            showErrorMessage("Zadejte obě data.", SmenyController.ERROR_ENTERED_DATA);
            process = false;
        }
        if (dateFrom.after(dateTo)) {
            showErrorMessage("Datum do musí být větší než datum od.", SmenyController.ERROR_ENTERED_DATA);
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

            Calendar cal = Calendar.getInstance(currentLocale);
            boolean isToSave = false;
            for (int i = 0; i < table.length; i++) {
                if (table[i][j] != null) {
                    tws = ServiceFacade.getInstance().findTypeworkshiftByName((String) table[i][j]);
                    idTypeWorkShift = tws.getIdTypeWorkshift();
                    //save workshift to each day
                    do {
                        tempDate = new Date(dateFromMills);
                        cal.setTime(tempDate);

                        switch (filter) {
                            case ALL_DAYS:
                                isToSave = true;
                                break;
                            case COMMON_DAYS: //czech week - common days 2-6
                                if (cal.get(Calendar.DAY_OF_WEEK) >= Calendar.MONDAY
                                        && cal.get(Calendar.DAY_OF_WEEK) <= Calendar.FRIDAY) {
                                    isToSave = true;
                                }
                                break;
                            case WEEKENDS: //czech weekends 7 (Saturaday), 1 (Sunday)
                                if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                                        || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                                    isToSave = true;
                                }
                                break;
                            default:
                                isToSave = true;
                                break;
                        }
                        if (isToSave) {
                            ServiceFacade.getInstance().createNewWorkshift(tempDate, idTypeWorkShift);
                            isToSave = false;
                        }

                        dateFromMills += DAY_IN_MILLISECONDS; //add one day
                    } while (dateFromMills <= dateToMills);
                    dateFromMills = dateFrom.getTime();
                }
            }
            showMessageDialogInformation("Pracovní směny uloženy.", "Úspěšné uložení dat");
        }

        return process;

    }

    public int getUserAttendanceId(int indexUserId) {
        return usersAttendaceIds[indexUserId];
    }

    public User getCurrentUser() {
        return this.user;
    }

    /**
     * Zobrazi chybovou zpravu v samostatnem dialogovem okne.     
     * @param error popis chyby
     * @param title titulek okna
     */
    public void showErrorMessage(String error, String title) {
        JOptionPane.showMessageDialog(null, error, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Zobrazi spravu v samostatnem dialogovem okne.     
     * @param text  text zpravy
     * @param title titulek okna
     */
    public void showMessageDialogInformation(String text, String title) {
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Klasicke Ano/Ne potvrzovací okno
     * @param text Popis
     * @param title titulek okna
     * @return 0, pokud klikne na ano
     */
    public int showConfirmDialogStandard(String text, String title) {
        return JOptionPane.showConfirmDialog(null, text, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * Return the week in wchich is the selected date.
     * @return the week
     */
    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    /**
     * Nastavuje tyden, datum prvniho a posledniho dne v tydnu pro listovani
     * v tabulce planovanych smen.     
     */
    public void initRangeDate(Locale locale) {
        Calendar cal = Calendar.getInstance(locale);
        Date date = new Date();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        int firstDayOfWeek = cal.getFirstDayOfWeek();
        int diff = 0;
        //universal for all locales
        if (day >= firstDayOfWeek) {
            diff = firstDayOfWeek - day;
        } else {
            if (diff == -1) { //sunday
                diff = -6;
            }
            if (diff == -2) { //saturday
                diff = -5;
            }
        }

        setWeek(cal.get(Calendar.WEEK_OF_YEAR));
        cal.add(Calendar.DAY_OF_WEEK, diff);//first date of the week                
        setDateFrom(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 6);//last date of the week                
        setDateTo(cal.getTime());
    }
}
