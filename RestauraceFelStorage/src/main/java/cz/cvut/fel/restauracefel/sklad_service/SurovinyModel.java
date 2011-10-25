package cz.cvut.fel.restauracefel.sklad_service;

import cz.cvut.fel.restauracefel.hibernate.Material;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;


/**
 * Trida, ktera reprezentuje model vsech surovin pro komponentu JList
 * @author Lukas Camra
 */
public class SurovinyModel extends AbstractListModel {
    List<Material> suroviny=new ArrayList<Material>();

    /**
     * getter pro zobrazene suroviny v JList
     * @return vraci List surovin zobrazenych v JListu
     */
    public List<Material> getSuroviny() {
        return suroviny;
    }


    /**
     * Konstruktor vytvarejici model
     */
    public SurovinyModel() {
        try {
            this.suroviny = ServiceFacade.getInstance().getAllMaterials();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SurovinyModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(SurovinyModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException e){}

    }

    /**
     * Konstruktor vytvarejici model
     * @param suroviny List, ktery ma byt zobrazeny v JList
     */
    public SurovinyModel(List<Material> suroviny){
        if(suroviny!=null)
            this.suroviny=suroviny;
    }




    /**
     * vraci pocet zobrazovanych surovin
     * @return vraci pocet zobrazovanych surovin
     */
    public int getSize() {
        return suroviny.size();
    }

    /**
     * metoda pro nalezeni reprezentovaneho objektu retezcem
     * @param arg0 cislo v seznamu
     * @return vraci nazev Suroviny s indexem arg0 v seznamu
     */
    public Object getElementAt(int arg0) {
        return suroviny.get(arg0).getName();
    }

    /**
     * metoda pro nalezeni reprezentovaneho objektu
     * @param arg cislo v seznamu
     * @return vraci objekt tridy Material s indexem arg v seznamu
     */
    public Object getMaterialAt(int arg)
    {
        return suroviny.get(arg);
    }
    /**
     * Metoda pro odstraneni prvku ze seznamu
     * @param i index, ktery reprezentuje objekt, ktery ma byt ze seznamu vymazan
     */
    public void removeElement(int i){
        suroviny.remove(i);

    }

}
