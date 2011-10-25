package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Depreciation;
import cz.cvut.fel.restauracefel.hibernate.Expenditure;
import cz.cvut.fel.restauracefel.hibernate.Income;
import cz.cvut.fel.restauracefel.hibernate.Material;
import cz.cvut.fel.restauracefel.hibernate.MaterialType;
import cz.cvut.fel.restauracefel.hibernate.UnitType;
import cz.cvut.fel.restauracefel.hibernate.UsedMaterial;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jarda
 */
public class MaterialController {

    protected Material material;
    protected static MaterialController instance = null;

    private MaterialController() {
    }

    public static MaterialController getInstance() {
        if (instance == null) {
            instance = new MaterialController();
        }
        return instance;
    }

    //vytvari novy zaznam o materialu
    //navraci true, pokud byl material uspesne vytvoren a ulozen do DB
    //navraci false, pokud zaznam nebyl vytvoren nebo nebyl zapsan do DB
    public boolean createMaterial(String name, int idMaterialType, int idUnitType, String barcode, double minimal, double density, double emptyPackageWeight, double packageCapacity) {
        if (!isExistedMaterial(name, barcode)) {
            material = new Material();
            material.setName(name);
            material.setCurrentQuantity(0.0);
            material.setUnitType(UnitType.findById(idUnitType));
            material.setMaterialType(MaterialType.findById(idMaterialType));
            material.setBarcode(barcode);
            material.setMinimal(minimal);
            material.setIsDeleted(0);
            material.setDensity(density);
            material.setEmptyPackageWeight(emptyPackageWeight);
            material.setPackageCapacity(packageCapacity);
            material.create();
            return true;
        } else {
            return false;
        }
    }

    //pokud material s danym jmenem a car. kodem neexistuje, tak metoda navraci false; pokud existuje, tak navraci true
    public boolean isExistedMaterial(String name, String barcode) {
        material = Material.findByName(name);
        if (material != null) {
            return true;
        }
        material = Material.findByBarcode(barcode);
        if (material != null) {
            return true;
        }
        return false;
    }

    //pokud material s danym barcodem neexistuje, tak metoda vraci false, jinak navraci true
    public boolean isExistedByBarcode(String barcode) {
        material = Material.findByBarcode(barcode);
        if (material == null) {
            return false;
        } else {
            return true;
        }
    }

    //navraci seznam vsech materialu v podobe Listu
    public List getAllMaterials() {
        return Material.findAll();
    }

    //navraci material pro dane materialId
    public Material getMaterialByID(int id) {
        return Material.findById(id);
    }

    public Material getMaterialByName(String name) {
        return Material.findByName(name);
    }

    //navraci Stringovou reprezentaci vsechny objektu tridy Material, ktere jsou daneho typu (materialTypeId)
    public String[] getMaterialNamesByMaterialType(int materialTypeId) {
        List<Material> list = Material.findByMaterialType(materialTypeId);
        if (list == null || list.isEmpty()) {
            return null;
        }
        String array[] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            material = (Material) it.next();
            array[i] = material.getName();
            i++;
        }
        return array;
    }

    //navraci pole Stringu, kde kazdy zaznam obsahuje id materialu a nazev materialu
    public String[] getMaterialNames() {
        List<Material> list = Material.findAll();
        if (list == null || list.isEmpty()) {
            return null;
        }
        String array[] = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            material = (Material) it.next();
            array[i] = material.getName();
            i++;
        }
        return array;
    }

    //navraci pole Objectu, ktere je tvoreno jednotlivymi zaznamy
    public Object[][] getMaterials() {
        List<Material> list = Material.findAll();
        if (list == null || list.isEmpty()) {
            return null;
        }
        Object array[][] = new Object[list.size()][11];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            Material mat = (Material) it.next();
            array[i][0] = mat.getMaterialId();
            array[i][1] = mat.getName();
            array[i][2] = mat.getCurrentQuantity();
            array[i][3] = mat.getMaterialType().getName();
            array[i][4] = mat.getUnitType().getName();
            array[i][5] = mat.getBarcode();
            array[i][6] = mat.getMinimal();
            array[i][7] = mat.getDensity();
            array[i][8] = mat.getEmptyPackageWeight();
            array[i][9] = mat.getPackageCapacity();
            i++;
        }
        return array;
    }

    //maze material dle daneho Id
    public boolean deleteMaterial(int materialId) {
        material = Material.findById(materialId);
        if (material == null) {
            return false;
        }
        //material.delete();
        material.setIsDeleted(1);
        return true;
    }

    //updatuje material dle daneho Id
    public boolean updateMaterial(Integer materialId, String name, int idMaterialType, int idUnitType, String barcode, double minimal, double density, double emptyPackageWeight, double packageCapacity) {
        if(MaterialType.findById(idMaterialType) == null || UnitType.findById(idUnitType) == null){
            return false;
        }

        material = Material.findById(materialId);
        if (material == null) {
            return false;
        }
        Material m = null;
        m = Material.findByName(name);
        if (m != null && m != material) {
            return false;
        }
        m = Material.findByBarcode(barcode);
        if (m != null && m != material) {
            return false;
        }
        material.setName(name);
        material.setMaterialType(MaterialType.findById(idMaterialType));
        material.setUnitType(UnitType.findById(idUnitType));
        material.setBarcode(barcode);
        material.setMinimal(minimal);
        material.setDensity(density);
        material.setEmptyPackageWeight(emptyPackageWeight);
        material.setPackageCapacity(packageCapacity);
        material.update();
        return true;
    }

    public boolean isDeletableMaterial(int materialId) {
        List<Income> incomes = Income.findByMaterial(materialId);
        if (incomes != null) {
            return false;
        }
        List<Expenditure> expends = Expenditure.findByMaterial(materialId);
        if (expends != null) {
            return false;
        }
        List<Depreciation> deps = Depreciation.findByMaterial(materialId);
        if (deps != null) {
            return false;
        }
        List<UsedMaterial> useds = UsedMaterial.findByMaterial(materialId);
        if (useds != null) {
            return false;
        }
        return true;
    }
}
