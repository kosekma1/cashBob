package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.UnitType;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jarda
 */
public class UnitTypeController {
    
    protected static UnitTypeController instance = null;
    protected UnitType ut = null;

    private UnitTypeController() {
    }

    public static UnitTypeController getInstance() {
        if (instance == null){
            instance = new UnitTypeController();
        }
        return instance;
    }

    public boolean createUnitType(String name, String abbreviation, int typeId){
        if(name == null || name.isEmpty() || abbreviation == null || abbreviation.isEmpty()){
            return false;
        }

        if (!isExistedUnitType(name, abbreviation)){
            ut = new UnitType(name, abbreviation, typeId, 0);
            ut.create();
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteUnitType(int unitTypeId){
        ut = UnitType.findById(unitTypeId);
        if (ut == null){
            return false;
        } else {
            //ut.delete();
            ut.setIsDeleted(1);
            return true;
        }
    }

    public boolean updateUnitType(int unitTypeId, String name, String abbreviation, int typeId){
        if(name == null || name.isEmpty() || abbreviation == null || abbreviation.isEmpty()){
            return false;
        }
        
        ut = UnitType.findById(unitTypeId);
        if (ut == null){
            return false;
        }
        UnitType u = null;
        u = UnitType.findByName(name);
        if (u != null && u != ut){
            return false;
        }
        u = UnitType.findByAbbreviation(abbreviation);
        if (u != null && u != ut){
            return false;
        }
        ut.setName(name);
        ut.setAbbreviation(abbreviation);
        ut.setTypeId(typeId);
        ut.update();
        return true;
    }

    public boolean isExistedUnitType(String name, String abbreviation){
        ut = UnitType.findByName(name);
        if (ut != null){
            return true;
        }
        ut = UnitType.findByAbbreviation(abbreviation);
        if (ut != null){
            return true;
        }
        return false;
    }

    //vraci List vsech instanci UnitType
    public List getAllUnitTypes() {
        List allUnitTypes = UnitType.findAll();
        return allUnitTypes;
    }

    //vraci poli Stringu se zkratkami vsech UnitType
    public String[] getUnitTypeAbbrs() {
        List<UnitType> list = getAllUnitTypes();
        if (list == null || list.isEmpty())
            return null;
        String unitTypeAbbr[] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            unitTypeAbbr[i] = ((UnitType) it.next()).getAbbreviation();
            i++;
        }
        return unitTypeAbbr;
    }

    //vraci pole Stringu se jmeny vsech UnitType
    public String[] getUnitTypeNames() {
        List<UnitType> list = getAllUnitTypes();
        if (list == null || list.isEmpty())
            return null;
        String unitTypeNames[] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            unitTypeNames[i] = ((UnitType) it.next()).getName();
            i++;
        }
        return unitTypeNames;
    }

    public String [] getUnitTypeNamesByTypeId(int typeId){
        List<UnitType> list = UnitType.findByTypeId(typeId);
        if (list == null || list.isEmpty()){
            return null;
        }
        String unitTypeNames [] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()){
            unitTypeNames[i] = ((UnitType) it.next()).getName();
            i++;
        }
        return unitTypeNames;
    }

    public String [] getUnitTypeAbbrsByTypeId(int typeId){
        List<UnitType> list = UnitType.findByTypeId(typeId);
        if (list == null || list.isEmpty()){
            return null;
        }
        String unitTypeNames [] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()){
            unitTypeNames[i] = ((UnitType) it.next()).getAbbreviation();
            i++;
        }
        return unitTypeNames;
    }

    //vraci UnitType dle jeho Id
    public UnitType getUnitTypeByID(int id) {
        return UnitType.findById(id);
    }

    //vraci UnitType dle jeho jmena
    public UnitType getUnitTypeByName(String name) {
        return UnitType.findByName(name);
    }

    //vraci UnitType dle jeho zkratky
    public UnitType getUnitTypeByAbbr(String abbr){
        return UnitType.findByAbbreviation(abbr);
    }
    
}
