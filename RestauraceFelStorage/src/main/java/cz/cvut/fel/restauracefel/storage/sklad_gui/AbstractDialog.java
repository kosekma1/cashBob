package cz.cvut.fel.restauracefel.storage.sklad_gui;

import cz.cvut.fel.restauracefel.hibernate.Material;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import cz.cvut.fel.restauracefel.sklad_service.ServiceFacade;

/**
 * Abstraktni trida, ze ktere dedi klientske JDialog tridy.
 *
 * @author Jarda
 */
public abstract class AbstractDialog extends JDialog {

    /**
     * Konstruktor tridy AbstractDialog.
     */
    public AbstractDialog(JFrame parent, boolean modal) {
        super(parent, modal);
    }

    /**
     * Metoda pro nastaveni datoveho modelu pro urceny JComboBox. String retezce
     * obsazene v poli namse budou nastaveny jako polozky JComboBoxu.
     *
     * @param names retezce, ktere budou nastaveny jako polozky daneho JComboBoxu
     * @param box JComboBox, pro ktery budou nastaveny polozky
     */
    protected void setComboBoxModel(String names[], JComboBox box){
        box.removeAllItems();
        if (names == null || names.length == 0) {
            return;
        }
        for (int i = 0; i < names.length; i++) {
            box.addItem(names[i]);
        }
        box.setSelectedIndex(0);
    }

    /**
     * Metoda urcena pro nastaveni comboBoxu obsahujicich udaje o druhu materialu
     * (materialTypeBox), materialu (materialBox) a druhu jednotky (unitTypeBox).
     * Metoda nastavuje pro materialTypeBox a materialBox listenery. Ty zajistuji
     * selekci pripustnych hodnot v ostatnich comboBoxech.
     *
     * @param materialTypeBox obsahuje zaznamy o druzich materialu
     * @param materialBox obsahuje zaznamy o materialech
     * @param unitBox obsahuje zaznamy o jednotkach
     */
    protected void setComboBoxesListeners(final JComboBox materialTypeBox, final JComboBox materialBox, final JComboBox unitBox) {
        materialTypeBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                try {
                    setMaterialModel(materialTypeBox, materialBox);
                } catch (Exception ex){}
            }
        });

        materialBox.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                try {
                    setUnitTypeModel(materialBox, unitBox);
                } catch (Exception ex){}
            }
        });
    }

    /**
     * Metoda nastavujici polozky comboBoxu material. Polozky jsou nastaveny dle
     * aktualne vybrane polozky comboBoxu materialType.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    private void setMaterialModel(JComboBox materialTypeBox, JComboBox materialBox) throws RemoteException, NotBoundException, FileNotFoundException {
        if (materialTypeBox.getSelectedIndex() == -1) {
            materialBox.setSelectedItem(null);
            return;
        }
        int materialTypeId = ServiceFacade.getInstance().getMaterialTypeByName((String) materialTypeBox.getSelectedItem()).getMaterialTypeId();
        setComboBoxModel(ServiceFacade.getInstance().getMaterialNamesByMaterialType(materialTypeId), materialBox);
    }

    /**
     * Metoda nastavujici polozky comboBoxu unitType. Polozky jsou nastaveny dle
     * aktualne vybrane polozky comboBoxu material.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    private void setUnitTypeModel(JComboBox materialBox, JComboBox unitBox) throws RemoteException, NotBoundException, FileNotFoundException {
        if (materialBox.getSelectedIndex() == -1) {
            unitBox.removeAllItems();
            return;
        }
        Material mat = ServiceFacade.getInstance().getMaterialByName((String) materialBox.getSelectedItem());
        int typeId = mat.getUnitType().getTypeId();
        setComboBoxModel(ServiceFacade.getInstance().getUnitTypeAbbrsByTypeId(typeId), unitBox);
        unitBox.setSelectedItem(mat.getUnitType().getAbbreviation());
    }

    //ABSTRACT methods

    /**
     * Metoda inicializuje komponenty na danem JDialogu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    protected abstract void initComponents() throws RemoteException, NotBoundException, FileNotFoundException;

    /**
     * Metoda kontrolujici spravnost vstupupnich poli na formulari.
     *
     * @return Vraci index urcujici vstupni komponentu, ktera obsahuje
     * neplatny vstup.
     */
    protected abstract int isValidInput();

    /**
     * Metoda inicializuje tabulku na danem JDialogu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    protected abstract void initTable() throws RemoteException, NotBoundException, FileNotFoundException;

    /**
     * Metoda provadi aktualizaci vsech comboBoxu a aktualizaci tabulky na danem
     * JDialogu.
     *
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     * @throws java.io.FileNotFoundException
     */
    protected abstract void refresh() throws RemoteException, NotBoundException, FileNotFoundException;
}
