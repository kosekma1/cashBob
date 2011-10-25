package cz.cvut.fel.restauracefel.storage.zrcadlo.controller;

import cz.cvut.fel.restauracefel.storage.zrcadlo.scale.ScaleCommunicatorThread;
import cz.cvut.fel.restauracefel.storage.zrcadlo.scale.ScaleCommunicatorException;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import cz.cvut.fel.restauracefel.hibernate.Kontrola;
import cz.cvut.fel.restauracefel.hibernate.Material;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.hibernate.Uzaverka;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.table.TableModel;
import cz.cvut.fel.restauracefel.storage.sklad_gui.NewUzaverkaForm;
import cz.cvut.fel.restauracefel.sklad_service.ResultTableModel;
import cz.cvut.fel.restauracefel.sklad_service.ServiceFacade;
import cz.cvut.fel.restauracefel.sklad_service.SurovinyModel;

/**
 * Trida UzaverkaKontroler ridi zobrazovani uzaverek a prubeh zadavani novych
 * kontrol.
 * Trida vyuziva navrhoveho vzoru singleton.
 * @author Vojtech Ruschka
 */
public class UzaverkaController {

    public final int NOTSELECTED = -1;
    private NewUzaverkaForm form;
    private Uzaverka uzaverka;
    private User loggedUser;
    private SurovinyModel listSurovin;
    private TableModel listKontrol;
    private int indexSurovina;
    private Material vybranaSurovina;
    private double hmotnostNaVaze;
    private ScaleCommunicatorThread sct;
    private Thread vaha;
    private boolean naslouchatVaze;
    private static UzaverkaController instance;

    public static UzaverkaController getInstance() {
        if (instance == null) {
            instance = new UzaverkaController();
        }
        return instance;
    }

    private UzaverkaController() {
    }

    /**
     * Metoda je vyuzivana v konstruktoru tridy UzaverkaForm.
     * Inicializuje kontroler do konzistentniho stavu pro uzaverku,
     * ktera byla vybrana v UzaverkaList.
     * Metoda nacte list provedenych kontrol, list nezkontrolovanych surovin
     * a inicializuje ScaleCommunicatorThread.
     * @param form UzaverkaForm, ze ktereho metodu volame.
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException
     * @throws NoSuchPortException
     * @throws PortInUseException
     * @throws UnsupportedCommOperationException
     * @throws ScaleCommunicatorException
     * @throws IOException
     */
    public void initByUzaverkaForm(NewUzaverkaForm form) throws FileNotFoundException, NotBoundException, RemoteException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException, ScaleCommunicatorException, IOException {
        this.form = form;
        this.listKontrol = nactiKontroly();
        this.listSurovin = (SurovinyModel) nactiListSurovin();
        this.indexSurovina = NOTSELECTED;
        this.naslouchatVaze = false;
        initScaleCommunicatorThread();
    }

    /**
     * Metoda je vyuzivana v konstruktoru tridy UzaverkaList.
     * @param loggedUser Prihlaseny uzivatel.
     */
    public void initByUzaverkaList(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    /**
     * Metoda zajisti, aby fungovalo spojeni s vahou.
     * @throws FileNotFoundException
     * @throws NoSuchPortException
     * @throws PortInUseException
     * @throws UnsupportedCommOperationException
     * @throws ScaleCommunicatorException
     * @throws IOException
     */
    private void initScaleCommunicatorThread() throws FileNotFoundException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException, ScaleCommunicatorException, IOException {
        if (sct == null) {
            sct = new ScaleCommunicatorThread(this);
        }
        if (vaha == null || !vaha.isAlive()) {
            vaha = new Thread(sct);
            vaha.start();
        }
    }

    /**
     * Metoda zalozi novou uzaverku. Datum je nastaveno na aktualni.
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException
     */
    public void zalozNovouUzaverku() throws FileNotFoundException, NotBoundException, RemoteException {
        uzaverka = new Uzaverka();
        uzaverka.setDate(new Date());
        uzaverka.setUser(loggedUser);
        uzaverka = ulozUzaverku(uzaverka);
    }

    /**
     * Metoda nacte vybranou uzaverku. Pokud je uzaverka uzavrena, vyhodi
     * vyjimku.
     * @param uzaverkaId
     * @throws FileNotFoundException
     * @throws RemoteException
     * @throws NotBoundException
     * @throws ValidateInputException
     */
    public void nactiUzaverku(int uzaverkaId) throws FileNotFoundException, RemoteException, NotBoundException, ValidateInputException {
        uzaverka = getUzaverkaById(uzaverkaId);
        if (uzaverka.isClosed()) {
            uzaverka = null;
            throw new ValidateInputException("Uzávěrka je uzavřená.");
        }
    }

    /**
     * Vraci Model vsech uzaverek.
     * Poradi atributu je: id uzaverky; datum uzaverky; pocet kontrol; user, ktery uzaverku provedl; closed (ano, ne)
     * @return
     * @throws FileNotFoundException
     * @throws RemoteException
     * @throws NotBoundException
     */
    public ResultTableModel nactiUzaverky() throws FileNotFoundException, RemoteException, NotBoundException {
        return new ResultTableModel(ResultTableModel.namesUzaverka, ServiceFacade.getInstance().getUzaverky());
    }

    /**
     * Metoda nacte statistiky dane uzaverky.
     * @param uzaverkaId
     * @return
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException
     */
    public Object[][] getStatistikaFromUzaverka(int uzaverkaId) throws FileNotFoundException, NotBoundException, RemoteException {
        return ServiceFacade.getInstance().getStatistikaFromUzaverka(uzaverkaId);
    }

    /**
     * Metoda nacte surovinu podle caroveho kodu.
     * @param kod
     * @return
     * @throws FileNotFoundException
     */
    public int najdiSurovinuPodleCarovehoKodu(String kod) throws FileNotFoundException {
        indexSurovina = 0;
        boolean nalezeno = false;
        for (Material m : listSurovin.getSuroviny()) {
            if (m.getBarcode().equals(kod)) {
                vybranaSurovina = m;
                nalezeno = true;
                break;
            }
            indexSurovina++;
        }
        if (!nalezeno) {
            indexSurovina = NOTSELECTED;
        } else {
            setNaslouchatVaze(true);
        }
        return indexSurovina;
    }

    /**
     * Metoda zvaliduje udaje pred ulozenim kontroly a pokud je vse v poradku,
     * ulozi ji.
     * @param pocetBaleni Pocet baleni na vaze.
     * @throws ValidateInputException
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException
     */
    public void zvalidujAUlozKontrolu(String pocetBaleni) throws ValidateInputException, FileNotFoundException, NotBoundException, RemoteException {
        zvalidujVstupyProKontrolu(pocetBaleni);
        int pocetBaleniInt = Integer.parseInt(pocetBaleni);
        spocitejKontrolu(pocetBaleniInt);
        this.listSurovin = (SurovinyModel) nactiListSurovin();
        this.listKontrol = nactiKontroly();
    }

    /**
     * Metoda spocita ze vstupu kontrolu a ulozi ji.
     * @param pocetBaleni Pocet baleni na vaze.
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException
     */
    private void spocitejKontrolu(int pocetBaleni) throws FileNotFoundException, NotBoundException, RemoteException {
        Kontrola k = new Kontrola();
        k.setValid(true);
        k.setUzaverka(uzaverka);
        k.setSurovina(vybranaSurovina);
        k.setProdanoVahou(Service.spocitejObjem(spocitejHmotnostKapaliny(pocetBaleni), vybranaSurovina.getDensity()));
        k.setNoveMnozstvi(0);
        k.setProdanoPokladnou(0);
        k.setRozdil(0);
        k.setStareMnozstvi(0);
        ulozKontrolu(k);
    }

    /**
     * Zvaliduje vstupy pred ulozenim kontroly.
     * @param pocetBaleni
     * @throws ValidateInputException
     */
    private void zvalidujVstupyProKontrolu(String pocetBaleni) throws ValidateInputException {
        if (uzaverka.isClosed()) {
            throw new ValidateInputException("Uzávěrka je uzavřená.");
        }
        if (indexSurovina == NOTSELECTED) {
            throw new ValidateInputException("Není vybraná žádná surovina.");
        }
        if (hmotnostNaVaze <= 0) {
            throw new ValidateInputException("Hmotnost musí být větší než 0 gramů.");
        }
        int pb;
        try {
            pb = Integer.parseInt(pocetBaleni);
        } catch (NumberFormatException ex) {
            throw new ValidateInputException("Počet balení musí být číslo.");
        }
        if (pb < 1) {
            throw new ValidateInputException("Počet balení musí být větší nebo roven 1.");
        }
    }

    /**
     * Uzavre vybranou uzaverku.
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException
     */
    public void closeUzaverka() throws FileNotFoundException, NotBoundException, RemoteException {
        uzaverka.setClosed(true);
        uzaverka = updateUzaverka(uzaverka);
    }

    /**
     * Vraci hmotnost kapaliny v lahvich.
     * @return
     */
    private double spocitejHmotnostKapaliny(int pocetBaleni) {
        return hmotnostNaVaze - (((double) pocetBaleni) * vybranaSurovina.getEmptyPackageWeight());
    }

    /**
     * Ulozi uzaverku.
     * @param u
     * @return Ulozena uzaverka.
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException
     */
    private Uzaverka ulozUzaverku(Uzaverka u) throws FileNotFoundException, NotBoundException, RemoteException {
        return ServiceFacade.getInstance().createUzaverka(u);
    }

    /**
     * Updatuje uzaverku v databazi.
     * @param u
     * @return Updatovana uzaverka.
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException
     */
    private Uzaverka updateUzaverka(Uzaverka u) throws FileNotFoundException, NotBoundException, RemoteException {
        return ServiceFacade.getInstance().updateUzaverka(u);
    }

    /**
     * Ulozi kontrolu v databazi.
     * @param k
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException
     */
    private void ulozKontrolu(Kontrola k) throws FileNotFoundException, NotBoundException, RemoteException {
        ServiceFacade.getInstance().addKontrolaToUzaverka(k);
    }

    /**
     * Vraci uzaverku podle id.
     * @param uzaverkaId
     * @return
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException
     */
    private Uzaverka getUzaverkaById(int uzaverkaId) throws FileNotFoundException, NotBoundException, RemoteException {
        return ServiceFacade.getInstance().getUzaverkaByID(uzaverkaId);
    }

    /**
     * Nacte list nezkontrolovanych surovin.
     * @return
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException
     */
    private ListModel nactiListSurovin() throws FileNotFoundException, NotBoundException, RemoteException {
        List<Material> materials = ServiceFacade.getInstance().getAllMaterials();
        List<Material> checkedMaterials = ServiceFacade.getInstance().getCheckedMaterialsByUzaverka(uzaverka);
        for (Material mc : checkedMaterials) {
            for (int index = 0; index < materials.size(); index++) {
                if (materials.get(index).getMaterialId() == mc.getMaterialId()) {
                    materials.remove(index);
                    break;
                }
            }
        }
        return new SurovinyModel(materials);
    }

    /**
     * Nacte kontroly pro uzaverku.
     * @return
     * @throws FileNotFoundException
     * @throws NotBoundException
     * @throws RemoteException
     */
    private TableModel nactiKontroly() throws FileNotFoundException, NotBoundException, RemoteException {
        List<Kontrola> kontroly = ServiceFacade.getInstance().getKontrolyFromUzaverka(uzaverka.getId());
        return new ResultTableModel(ResultTableModel.namesKontroly, listKontrolaToArray(kontroly));
    }

    private Object[][] listKontrolaToArray(List<Kontrola> list) {
        if (list == null) {
            return new Object[0][2];
        }
        Object[][] pole = new Object[list.size()][2];
        int index = 0;
        for (Kontrola k : list) {
            pole[index][0] = k.getSurovina().getName();
            pole[index][1] = k.getProdanoVahou();
            index++;
        }
        return pole;
    }

    /**
     * Metoda je volana z ScaleCommunicatorThread v pripade, ze je z váhy
     * odeslan udaj o hmotnosti. Metoda podle situace rozhodne, zda se udaj
     * zpracuje, nebo zahodi.
     * @param m
     */
    public void updateHmotnost(double m) {
        if (isNaslouchatVaze() && vybranaSurovina != null) {
            hmotnostNaVaze = m;
            form.updateHmotnostInput(hmotnostNaVaze);
        }
        setNaslouchatVaze(false);
    }

    public void setNaslouchatVaze(boolean naslouchatVaze) {
        this.naslouchatVaze = naslouchatVaze;
    }

    public boolean isNaslouchatVaze() {
        return naslouchatVaze;
    }

    public ListModel getListSurovin() {
        return listSurovin;
    }

    public void setListSurovin(ListModel listSurovin) {
        this.listSurovin = (SurovinyModel) listSurovin;
    }

    public TableModel getListKontrol() {
        return listKontrol;
    }

    public void setListKontrol(TableModel listKontrol) {
        this.listKontrol = listKontrol;
    }


}
