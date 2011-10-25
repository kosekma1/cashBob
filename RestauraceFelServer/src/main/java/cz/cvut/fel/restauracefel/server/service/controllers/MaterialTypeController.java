package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Material;
import cz.cvut.fel.restauracefel.hibernate.MaterialType;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jarda
 */
public class MaterialTypeController {

    protected MaterialType matType = null;
    protected static MaterialTypeController instance = null;

    private MaterialTypeController() {
    }

    public static MaterialTypeController getInstance() {
        if (instance == null){
            instance = new MaterialTypeController();
        }
        return instance;
    }

    //vytvari novy druh materialu (nesmi mit stejne jmeno jako jiz vytvoreny)
    public boolean createMaterialType(String name, String note){
        matType = MaterialType.findByName(name);
        if (matType == null){
            matType = new MaterialType(name, note, 0);
            matType.create();
            return true;
        } else {
            return false;
        }
    }

    //maze druh materialu s danym Id
    public boolean deleteMaterialType(int materialTypeId){
        matType = MaterialType.findById(materialTypeId);
        if (matType == null){
            return false;
        } else {
            //matType.delete();
            matType.setIsDeleted(1);
            return true;
        }
    }

    //updatuje druh materialu s danym Id
    public boolean updateMaterialType(int materialTypeId, String name, String note){
        matType = MaterialType.findById(materialTypeId);
        if (matType == null){
            return false;
        } else {
            //kdyz je jiz dane jmeno obsazeno
            MaterialType mt = MaterialType.findByName(name);
            if (mt != null && mt != matType){
                return false;
            }
            matType.setName(name);
            matType.setNote(note);
            matType.update();
            return true;
        }
    }

    //vraci List vsech druhu materialu (vsech nesmazanych)
    public List getAllMaterialTypes() {
        return MaterialType.findAll();
    }

    //navraci druh materialu s danym Id
    public MaterialType getMaterialTypeByID(int id) {
        return MaterialType.findById(id);
    }

    //navraci druh materialu dle jeho jmena
    public MaterialType getMaterialTypeByName(String name) {
        return MaterialType.findByName(name);
    }

    //navraci String pole jmen vsech druhu materialu
    public String[] getMaterialTypeNames() {
        List list = MaterialType.findAll();
        if (list == null || list.isEmpty())
            return null;
        String materialTypes[] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            matType = (MaterialType) it.next();            
            materialTypes[i] = matType.getName();
            i++;
        }
        return materialTypes;
    }

    public Object [][] getMaterialTypes(){
        List<MaterialType> list = MaterialType.findAll();
        if (list == null || list.isEmpty()){
            return null;
        }
        Object array [][] = new Object [list.size()][3];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()){
            matType = (MaterialType) (it.next());
            array [i][0] = matType.getMaterialTypeId();
            array [i][1] = matType.getName();
            array [i][2] = matType.getNote();
            i++;
        }
        return array;
    }

    public boolean isDeletableMaterialType(int materialTypeId){
        List<Material> list = Material.findByMaterialType(materialTypeId);
        if (list == null || list.isEmpty()){
            return true;
        } else {
            return false;
        }
    }
    
}
