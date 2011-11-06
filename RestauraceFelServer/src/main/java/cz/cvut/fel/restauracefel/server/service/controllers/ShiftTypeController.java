package cz.cvut.fel.restauracefel.server.service.controllers;


import cz.cvut.fel.restauracefel.hibernate.Typeworkshift;
import java.util.List;

/**
 * Kontroler pro práci se směnami.
 * @author kosekm
 */
public class ShiftTypeController {
    
    protected static ShiftTypeController instance = null;
    protected Typeworkshift shiftType = null;
    
    private ShiftTypeController(){
        
    }
    
    public static ShiftTypeController getInstance() {
        if (instance == null) {
            instance = new ShiftTypeController();
        }
        return instance;
    }
    
     public List getTypeWorkShifts()  {
        return Typeworkshift.findAll();        
    }
    
     public void createWorkshiftType(Typeworkshift typeWorkshift){
         typeWorkshift.create();
     }
     
     public Typeworkshift findTypeworkshiftByName(String name){
         return Typeworkshift.findByName(name);
     }
     
     public Typeworkshift getTypeWorkShiftById(int idTypeWorkshift){
         return (Typeworkshift)Typeworkshift.findById("Typeworkshift", "idTypeWorkshift", idTypeWorkshift);
     }
    
 
}
