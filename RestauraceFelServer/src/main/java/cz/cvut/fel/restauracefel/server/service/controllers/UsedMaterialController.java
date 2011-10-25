package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Material;
import cz.cvut.fel.restauracefel.hibernate.MenuItem;
import cz.cvut.fel.restauracefel.hibernate.UsedMaterial;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jarda
 */
public class UsedMaterialController {

    private static UsedMaterialController instance = null;
    private UsedMaterial usedMaterial = null;

    private UsedMaterialController(){
    }

    public static UsedMaterialController getInstance(){
        if (instance == null){
            instance = new UsedMaterialController();
        }
        return instance;
    }

    //vytvari novy zaznam o pouzitem materialu
    public void createUsedMaterial(int materialId, int menuItemId, double quantity){
        usedMaterial = new UsedMaterial();
        usedMaterial.setMaterial(Material.findById(materialId));
        usedMaterial.setMenuItem(MenuItem.findById(menuItemId));
        usedMaterial.setQuantity(quantity);
        usedMaterial.create();
    }

    //maze zaznam o pouzitem materialu s danym Id
    public boolean deleteUsedMaterial(int usedMaterialId){
        usedMaterial = UsedMaterial.findById(usedMaterialId);
        if (usedMaterial == null){
            return false;
        }
        //usedMaterial.delete();
        usedMaterial.setIsDeleted(1);
        return true;
    }

    //aktualizuje zaznam o pouzitem materialu s danym Id
    public boolean updateUsedMaterial(int usedMaterialId, int materialId, int menuItemId, double quantity){
        usedMaterial = UsedMaterial.findById(usedMaterialId);
        if (usedMaterial == null){
            return false;
        }
        usedMaterial.setMaterial(Material.findById(materialId));
        usedMaterial.setMenuItem(MenuItem.findById(menuItemId));
        usedMaterial.setQuantity(quantity);
        usedMaterial.update();
        return true;
    }

    //vraci pole typu Object naplnene zaznamy o pouzitych materialech
    public Object [][] getUsedMaterials(){
        List<UsedMaterial> list = UsedMaterial.findAll();
        if (list == null || list.isEmpty())
            return null;
        Object [][] array = new Object [list.size()][4];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()){
            usedMaterial = (UsedMaterial)it.next();
            array [i][0] = usedMaterial.getUsedMaterialId();
            array [i][1] = usedMaterial.getMaterial().getName();
            array [i][2] = usedMaterial.getQuantity();
            array [i][3] = usedMaterial.getMaterial().getUnitType().getAbbreviation();
            i++;
        }
        return array;
    }

    public Object [][] getUsedMaterialsByMenuItem(int menuItemId){
        List<UsedMaterial> list = UsedMaterial.findByMenuItem(menuItemId);
        if (list == null || list.isEmpty())
            return null;
        Object [][] array = new Object [list.size()][4];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()){
            usedMaterial = (UsedMaterial)it.next();
            array [i][0] = usedMaterial.getUsedMaterialId();
            array [i][1] = usedMaterial.getMaterial().getName();
            array [i][2] = usedMaterial.getQuantity();
            array [i][3] = usedMaterial.getMaterial().getUnitType().getAbbreviation();
            i++;
        }
        return array;
    }

    //navraci zaznam o pouzitem materialu s danym Id
    public UsedMaterial getUsedMaterialById(int usedMaterialId){
        return UsedMaterial.findById(usedMaterialId);
    }

}
