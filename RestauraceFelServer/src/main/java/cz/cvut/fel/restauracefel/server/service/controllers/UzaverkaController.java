package cz.cvut.fel.restauracefel.server.service.controllers;

import cz.cvut.fel.restauracefel.hibernate.Kontrola;
import cz.cvut.fel.restauracefel.hibernate.Material;
import cz.cvut.fel.restauracefel.hibernate.OrderMenuItem;
import cz.cvut.fel.restauracefel.hibernate.UsedMaterial;
import cz.cvut.fel.restauracefel.hibernate.User;
import cz.cvut.fel.restauracefel.hibernate.Uzaverka;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lukas Camra
 */
public class UzaverkaController {

    protected static UzaverkaController instance = null;
    protected Uzaverka uzaverka;
    protected Kontrola kontrola;

    public void setUzaverka(Uzaverka uzaverka) {
        this.uzaverka = uzaverka;
    }

    public static UzaverkaController getInstance() {
        if (UzaverkaController.instance == null) {
            UzaverkaController.instance = new UzaverkaController();
        }
        return instance;
    }

    //vytvari novou uzaverku
    public Uzaverka createUzaverka(int userId, Date date) {
        User user = User.findById(userId);
        if (user == null) {
            return null;
        }
        uzaverka = new Uzaverka();
        uzaverka.setUser(user);
        uzaverka.setDate(date);
        uzaverka.create();
        return uzaverka;
    }

    public Uzaverka createUzaverka(Uzaverka u) {
        uzaverka = u;
        uzaverka.create();
        return uzaverka;
    }

    public Uzaverka updateUzaverka(Uzaverka u) {
        uzaverka = Uzaverka.findById(u.getId());
        uzaverka.setClosed(u.isClosed());
        uzaverka.setDate(u.getDate());
        uzaverka.setUser(u.getUser());
        uzaverka.update();
        return uzaverka;
    }

    //vraci vsechny kontroly uzaverky
    public Object[][] getKontroly() {
        List<Kontrola> kontroly = Kontrola.findByUzaverka(uzaverka.getId());
        if (uzaverka == null || kontroly == null) {
            return null;
        }
        Object[][] result = new Object[kontroly.size()][7];
        int i = 0;
        for (Kontrola k : kontroly) {
            result[i][0] = k.getSurovina().getName();
            result[i][1] = k.getStareMnozstvi();
            result[i][2] = k.getNoveMnozstvi();
            result[i][3] = k.getProdanoVahou();
            result[i][4] = k.getProdanoPokladnou();
            result[i][5] = k.getRozdil();
            if (k.isValid()) {
                result[i][6] = "ano";
            } else {
                result[i][6] = "ne";
            }
            i++;
        }
        return result;

    }
    //pridava kontrolu

    public void addKontrola(double noveMnozstvi, double stareMnozstvi, double prodanoVahou, double rozdil, int surovinaId, int uzaverkaId, boolean valid) {
        Kontrola k = new Kontrola();
        k.setNoveMnozstvi(noveMnozstvi);
        k.setStareMnozstvi(stareMnozstvi);
        k.setProdanoVahou(prodanoVahou);
        k.setRozdil(rozdil);
        Material surovina = Material.findById(surovinaId);
        k.setSurovina(surovina);
        uzaverka = Uzaverka.findById(uzaverkaId);
        k.setUzaverka(uzaverka);
        k.setValid(valid);

        List<Uzaverka> uzaverky = Uzaverka.findAll("Uzaverka");
        Uzaverka previous = null;
        if (uzaverky.size() - 2 >= 0) {
            previous = uzaverky.get(uzaverky.size() - 2);
        }
        Date date = null;
        if (previous == null) {
            date = new Date(0);
        } else {
            date = previous.getDate();
        }
        double[] result = UsedMaterial.findQuantityOfUsedMaterialUsedAfterInOrders(date, k.getSurovina());
        k.setProdanoPokladnou(result[0]);
        k.create();
    }

    //vraci vsechny uzaverky
    public Object[][] getAllUzaverky() {
        List<Uzaverka> uzaverky = Uzaverka.findAllOrderedDescendingByDate();
        if (uzaverky == null || uzaverky.isEmpty()) {
            return new Object[0][0];
        }
        Object[][] result = new Object[uzaverky.size()][5];
        int i = 0;
        for (Uzaverka uzav : uzaverky) {
            result[i][0] = uzav.getId();
            SimpleDateFormat dateFormater = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
            result[i][1] = dateFormater.format(uzav.getDate());
            result[i][3] = uzav.getUser().getLastName();
            List<Kontrola> kontroly = Kontrola.findByUzaverka(uzav.getId());
            if (kontroly != null) {
                result[i][2] = kontroly.size();
            } else {
                result[i][2] = 0;
            }
            if (uzav.isClosed()) {
                result[i][4] = "ano";
            } else {
                result[i][4] = "ne";
            }
            i++;
        }
        return result;
    }

    //vraci seznam kontrol uzaverky
    public List<Kontrola> getAllKontrolyList() {
        List<Kontrola> kontroly = Kontrola.findByUzaverka(uzaverka.getId());
        return kontroly;
    }

    //scita jednotlive uzaverky reprezentovany celym cislem v pole ids
    public List<Object[]> getStatistikaFromUzaverky(int[] ids) {
        List<Object[]> result = new ArrayList<Object[]>();
        for (int i : ids) {
            uzaverka = ((Uzaverka) Uzaverka.findById("Uzaverka", "id", i));
            List<Kontrola> k = getAllKontrolyList();
            if (k == null) {
                continue;
            }
            for (Kontrola temp : k) {
                if (!temp.isValid()) {
                    continue;
                }
                boolean found = false;
                for (Object[] temp2 : result) {
                    if (temp.getSurovina().getName().equals(temp2[0])) {
                        temp2[2] = ((Double) temp2[2]) + temp.getProdanoVahou();
                        found = true;
                    }
                }
                if (!found) {
                    result.add(new Object[]{temp.getSurovina().getName(), temp.getStareMnozstvi(), temp.getProdanoVahou()});
                }
            }

        }
        return result;
    }

    /**
     * Metoda pocita statistiku uzaverky.
     * @param uzaverkaId
     * @return
     */
    public Object[][] getStatistikaFromUzaverka(int uzaverkaId) {
        final int POCET_SLOUPCU = 10;
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        uzaverka = Uzaverka.findById(uzaverkaId);
        List<Kontrola> kontroly = Kontrola.findByUzaverka(uzaverkaId);
        List<Uzaverka> previousUzaverky = Uzaverka.findPreviousUzaverky(uzaverka);
        if (kontroly == null || previousUzaverky == null) {
            //neexituji zadne kontroly pro uzaverku nebo neexistuji zadne predchazejici uzaverky
            return new Object[0][POCET_SLOUPCU];
        }
        Object[][] result = new Object[kontroly.size()][POCET_SLOUPCU];
        int i = 0;
        for (Kontrola k : kontroly) {
            result[i][0] = k.getSurovina().getName();
            Kontrola previousKontrola = findPreviousKontrola(previousUzaverky, k);
            if (previousKontrola == null) {
                //zadna predchozi uzaverka pro surovinu neexistuje
                result[i][1] = "Zadna predchozi kontrola suroviny.";
                setFieldsOnZero(result, i, 2, POCET_SLOUPCU);
                continue;
            }
            Uzaverka previousUzaverka = Uzaverka.findById(previousKontrola.getUzaverka().getId());
            result[i][1] = dateFormater.format(previousUzaverka.getDate());
            result[i][2] = dateFormater.format(uzaverka.getDate());
            List<UsedMaterial> receptury = UsedMaterial.findByMaterial(k.getSurovina().getMaterialId());
            if (receptury == null || receptury.isEmpty()) {
                //nejsou nastaveny receptury
                result[i][3] = "Zadne receptury.";
                setFieldsOnZero(result, i, 4, POCET_SLOUPCU);
                continue;
            }
            double prodanyObjem = 0;
            for (UsedMaterial um : receptury) {
                int pocetObjednavek = OrderMenuItem.countOrdersWithMenuItemInInterval(um.getMenuItem().getMenuItemId(), previousUzaverka.getDate(), uzaverka.getDate());
                prodanyObjem += pocetObjednavek * um.getQuantity();
            }
            result[i][3] = decimalFormat.format(prodanyObjem);
            double pocetBaleni = prodanyObjem / k.getSurovina().getPackageCapacity();
            result[i][4] = decimalFormat.format(pocetBaleni);
            result[i][5] = decimalFormat.format(previousKontrola.getProdanoVahou());
            result[i][6] = decimalFormat.format(k.getProdanoVahou());
            double objemZmerenyVahou = spocitejZvazenyObjemSurovinyZKontrol(previousKontrola, k, (int) pocetBaleni);
            result[i][7] = decimalFormat.format(objemZmerenyVahou);
            result[i][8] = decimalFormat.format(prodanyObjem - objemZmerenyVahou);
            result[i][9] = decimalFormat.format(spocitejProcentualniRozdil(prodanyObjem, objemZmerenyVahou));
            i++;
        }
        return result;
    }

    /**
     * Metoda vraci posledni kontrolu suroviny,
     * ktera je kontrolovana v kontrole v parametru.
     * @param l List uzaverek, kde chceme kontrolu hledat.
     * @param k1 Kontrola s pozadovanou surovinou.
     * @return
     */
    private Kontrola findPreviousKontrola(List<Uzaverka> l, Kontrola k1) {
        for (Uzaverka u : l) {
            List<Kontrola> kontroly = Kontrola.findByUzaverka(u.getId());
            if (kontroly == null) {
                continue;
            }
            for (Kontrola k2 : kontroly) {
                if (k1.getSurovina().getMaterialId() == k2.getSurovina().getMaterialId()) {
                    return k2;
                }
            }
        }
        return null;
    }

    private void setFieldsOnZero(Object[][] array, int xIndex, int yIndex, int numberOfColumns) {
        for (int i = yIndex; i < numberOfColumns; i++) {
            array[xIndex][i] = 0;
        }
    }

    private double spocitejZvazenyObjemSurovinyZKontrol(Kontrola first, Kontrola second, int countOfPackages) {
        double result = 0;
        result = first.getProdanoVahou() - second.getProdanoVahou() + (countOfPackages * first.getSurovina().getPackageCapacity());
        return result;
    }

    private double spocitejProcentualniRozdil(double zaklad, double pozadavek) {
        return  Math.abs(((100 * pozadavek) / zaklad) - 100);

    }

    /**
     * Metoda vraci mnozstvi suroviny prodane pokladnou od predchozi uzaverky.
     * @param surovinaId
     * @return
     */
    public double prodanoPokladnou(int surovinaId) {
        Material m = Material.findById(surovinaId);
        if (m == null) {
            return 0;
        }
        List<Uzaverka> uzaverky = Uzaverka.findAll("Uzaverka");
        Uzaverka previous = null;
        if (uzaverky != null && uzaverky.size() - 2 >= 0) {
            previous = uzaverky.get(uzaverky.size() - 2);
        }
        Date date = null;
        if (previous == null) {
            date = new Date(0);
        } else {
            date = previous.getDate();
        }
        return UsedMaterial.findQuantityOfUsedMaterialUsedAfterInOrders(date, m)[0];
    }

    public void addKontrola(Kontrola k) {
        this.kontrola = k;
        kontrola.create();
    }

    public Uzaverka getUzaverkaById(int uzaverkaId) {
        return Uzaverka.findById(uzaverkaId);
    }

    /**
     * Metoda vraci zkontrolovane suroviny uzaverky.
     * @param u
     * @return
     */
    public List<Material> getCheckedMaterialsByUzaverka(Uzaverka u) {
        uzaverka = Uzaverka.findById(u.getId());
        List<Kontrola> kontroly = Kontrola.findByUzaverka(uzaverka.getId());
        List<Material> checkedSuroviny = new ArrayList<Material>();
        if (kontroly == null) {
            return checkedSuroviny;
        }
        for (Kontrola k : kontroly) {
            checkedSuroviny.add(k.getSurovina());
        }
        return checkedSuroviny;
    }
}
