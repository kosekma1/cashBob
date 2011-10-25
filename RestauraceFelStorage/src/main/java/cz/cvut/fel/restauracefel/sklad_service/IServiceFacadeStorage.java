package cz.cvut.fel.restauracefel.sklad_service;

import cz.cvut.fel.restauracefel.hibernate.Uzaverka;
import cz.cvut.fel.restauracefel.hibernate.Depreciation;
import cz.cvut.fel.restauracefel.hibernate.Expenditure;
import cz.cvut.fel.restauracefel.hibernate.Income;
import cz.cvut.fel.restauracefel.hibernate.Kontrola;
import cz.cvut.fel.restauracefel.hibernate.Material;
import cz.cvut.fel.restauracefel.hibernate.MaterialType;
import cz.cvut.fel.restauracefel.hibernate.ReasonType;
import cz.cvut.fel.restauracefel.hibernate.Role;
import cz.cvut.fel.restauracefel.hibernate.UnitType;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.hibernate.UserRole;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * Hlavni interface pro RMI komunikaci se seznamem metod implementovanych na strane serveru
 *
 * @author Jarda
 * @author Tomas Hnizdil
 */
public interface IServiceFacadeStorage extends Remote {

    //USER methods
    public List getAllUsers() throws RemoteException;
    public boolean createUser(String name, String surname, String pid, String username, String passwd) throws RemoteException;
    public boolean isValidUser(String username, String passwd) throws RemoteException;
    public User getUserByPID(String pid) throws RemoteException;
    public User getUserByUsername(String userName) throws RemoteException;
    public User getUserById(int id) throws RemoteException;
    public boolean deleteUser(int userId) throws RemoteException;
    public String[] getUserNames() throws RemoteException;
    public String[] getUserUsernames() throws RemoteException;
    public Object [][] getUsers() throws RemoteException;
    public boolean updateUser(Integer userId, String name, String surname, String pid, String username) throws RemoteException;
    public String getDefaultPasswd() throws RemoteException;
    public boolean updateUserPassword(Integer userId, String newPassword) throws RemoteException;
    public boolean isValidOldPasswd(Integer userId, String passwd) throws RemoteException;

    //ROLE methods
    public List getAllRoles() throws RemoteException;
    public String[] getRoleNames() throws RemoteException;
    public Role getRoleByID(int id) throws RemoteException;
    public Role getRoleByName(String name) throws RemoteException;

    //MATERIAL methods
    public boolean createMaterial(String name, int idMaterialType, int idUnitType, String barcode, double minimal,double density, double emptyPackageWeight, double packageCapacity) throws RemoteException;
    public List getAllMaterials() throws RemoteException;
    public Material getMaterialByID(int id) throws RemoteException;
    public Material getMaterialByName(String name) throws RemoteException;
    public String[] getMaterialNames() throws RemoteException;
    public Object [][] getMaterials() throws RemoteException;
    public String[] getMaterialNamesByMaterialType(int materialTypeId) throws RemoteException;
    public boolean deleteMaterial(int materialId) throws RemoteException;
    public boolean updateMaterial(Integer materialId, String name, int idMaterialType, int idUnitType, String barcode, double minimal,double density, double emptyPackageWeight, double packageCapacity) throws RemoteException;
    public boolean isDeletableMaterial(int materialId) throws RemoteException;

    //MATERIALTYPE methods
    public boolean createMaterialType(String name, String note) throws RemoteException;
    public boolean deleteMaterialType(int materialTypeId) throws RemoteException;
    public boolean updateMaterialType(int materialTypeId, String name, String note) throws RemoteException;
    public List getAllMaterialTypes() throws RemoteException;
    public MaterialType getMaterialTypeByID(int id) throws RemoteException;
    public MaterialType getMaterialTypeByName(String name) throws RemoteException;
    public String[] getMaterialTypeNames() throws RemoteException;
    public Object [][] getMaterialTypes() throws RemoteException;
    public boolean isDeletableMaterialType(int materialTypeId) throws RemoteException;

    //UNITTYPE methods
    public boolean createUnitType(String name, String abbreviation, int typeId) throws RemoteException;
    public boolean deleteUnitType(int unitTypeId) throws RemoteException;
    public boolean updateUnitType(int unitTypeId, String name, String abbreviation, int typeId) throws RemoteException;
    public List getAllUnitTypes() throws RemoteException;
    public String[] getUnitTypeAbbrs() throws RemoteException;
    public String[] getUnitTypeNames() throws RemoteException;
    public String [] getUnitTypeNamesByTypeId(int typeId) throws RemoteException;
    public String [] getUnitTypeAbbrsByTypeId(int typeId) throws RemoteException;
    public UnitType getUnitTypeByID(int id) throws RemoteException;
    public UnitType getUnitTypeByName(String name) throws RemoteException;
    public UnitType getUnitTypeByAbbr(String abbr) throws RemoteException;

    //INCOME methods
    public boolean createIncome(Date date, int materialId, double quantity, double price, int userId, String note) throws RemoteException;
    public boolean deleteIncome(int incomeId) throws RemoteException;
    public boolean updateIncome(int incomeId, Date date, int materialId, double quantity, double price, int userId, String note) throws RemoteException;
    public Object [][] getIncomes() throws RemoteException;
    public Income getIncomeByID(int incomeId) throws RemoteException;

    //EXPENDITURE methods
    public boolean createExpenditure(Date date, int materialId, double quantity, int userId, String note) throws RemoteException;
    public boolean deleteExpenditure(int expenditureId) throws RemoteException;
    public boolean updateExpenditure(int expenditureId, Date date, int materialId, double quantity, int userId, String note) throws RemoteException;
    public Expenditure getExpenditureById(int expenditureId) throws RemoteException;
    public Object [][] getExpenditures() throws RemoteException;

    //DEPRECIATION methods
    public boolean createDepreciation(int userReporterId, int userOffenderId, int materialId, double quantity, Date date, int reasonTypeId, String note) throws RemoteException;
    public boolean deleteDepreciation(int depreciationId) throws RemoteException;
    public boolean updateDepreciation(int depreciationId, int userReporterId, int userOffenderId, int materialId, double quantity, Date date, int reasonTypeId, String note) throws RemoteException;
    public Depreciation getDepreciationById(int depreciationId) throws RemoteException;
    public Object [][] getDepreciations() throws RemoteException;

    //REASONTYPE methods
    public boolean createReasonType(String name, String note) throws RemoteException;
    public boolean deleteReasonType(int reasonTypeId) throws RemoteException;
    public boolean updateReasonType(int reasonTypeId, String name, String note) throws RemoteException;
    public ReasonType getReasonTypeById(int reasonTypeId) throws RemoteException;
    public ReasonType getReasonTypeByName(String name) throws RemoteException;
    public Object [][] getReasonTypes() throws RemoteException;
    public String [] getReasonTypeNames() throws RemoteException;
    public boolean isDeletableReasonType(int reasonTypeId) throws RemoteException;

    //USERROLE methods
    public boolean createUserRole(int userId, int roleId) throws RemoteException;
    public void deleteUserRole(int userId, int roleId) throws RemoteException;
    public UserRole getUserRoleById(int userRoleId) throws RemoteException;
    public List getUserRoleByUserId(int user) throws RemoteException;
    public boolean isExistedUserRole(int userId, int roleId) throws RemoteException;
    public boolean [] getUserRoles(int userId) throws RemoteException;
    public boolean isUserRole(int userId, int roleId) throws RemoteException;

    //Zrcadlo methods
    public Object[][] getUzaverkaKontroly() throws RemoteException;
    public Object[][] getUzaverkaKontroly(int idUzaverky) throws RemoteException;
    public Uzaverka createUzaverka(Uzaverka u) throws RemoteException;
    public Uzaverka updateUzaverka(Uzaverka u) throws RemoteException;
    public void addKontrolaToUzaverka(double noveMnozstvi, double stareMnozstvi, double prodanoVahou, double rozdil, int surovinaId, int uzaverkaId, boolean valid) throws RemoteException;
    public Object[][] getUzaverky() throws RemoteException;
    public List<Kontrola> getKontrolyFromUzaverka(int id) throws RemoteException;
    public List<Object[]> getStatistikaFromUzaverky(int[] ids) throws RemoteException;
    public Object[][] getStatistikaFromUzaverka(int uzaverkaId) throws RemoteException;
    public double prodanoPokladnou(int surovinaId) throws RemoteException;
    public void addKontrolaToUzaverka(Kontrola k) throws RemoteException;
    public Uzaverka getUzaverkaByID(int uzaverkaId) throws RemoteException;
    public List<Material> getCheckedMaterialsByUzaverka(Uzaverka u) throws RemoteException;

}