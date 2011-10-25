package cz.cvut.fel.restauracefel.hibernate;

import java.util.List;

/**
 *
 * @author Lukas Camra
 */
public class Kontrola extends DBEntity {

    private static final long serialVersionUID = -3485621318713955438L;
    private double noveMnozstvi;
    private double stareMnozstvi;
    private double prodanoVahou;
    private Material surovina;
    private Integer measurementId;
    private Uzaverka uzaverka;
    private double prodanoPokladnou;
    private double rozdil;
    private boolean valid;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    
    

    public Uzaverka getUzaverka() {
        return uzaverka;
    }

    public void setUzaverka(Uzaverka uzaverka) {
        this.uzaverka = uzaverka;
    }

    public Integer getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(Integer measurementId) {
        this.measurementId = measurementId;
    }

    public Material getSurovina() {
        return surovina;
    }

    public void setSurovina(Material surovina) {
        this.surovina = surovina;
    }

    public double getNoveMnozstvi() {
        return noveMnozstvi;
    }

    public void setNoveMnozstvi(double noveMnozstvi) {
        this.noveMnozstvi = noveMnozstvi;
    }

    public double getStareMnozstvi() {
        return stareMnozstvi;
    }

    public void setStareMnozstvi(double stareMnozstvi) {
        this.stareMnozstvi = stareMnozstvi;
    }

    public void create() {
        create(this);
    }

    public void delete() {
        delete(this);
    }

    public void update() {
        update(this);
    }

    public static Kontrola findById(Integer id) {
        return (Kontrola) findByIdNotDeleted("Kontrola", "measurementId", id, "isDeleted", 0);
    }

    public static List<Kontrola> findByUzaverka(Integer uzaverkaId) {
        String query = "SELECT k FROM Kontrola k WHERE k.uzaverka.id = :id";
        String[] paramNames = new String[]{"id"};
        String[] paramTypes = new String[]{"Integer"};
        Integer[] paramValues = new Integer[]{uzaverkaId};

        List<Kontrola> res = executeQuery(query, paramNames, paramTypes, paramValues);
        if (res == null || res.isEmpty()) {
            return null;
        }
        return res;
    }

    public double getProdanoPokladnou() {
        return prodanoPokladnou;
    }

    public void setProdanoPokladnou(double prodanoPokladnou) {
        this.prodanoPokladnou = prodanoPokladnou;
    }

    public double getProdanoVahou() {
        return prodanoVahou;
    }

    public void setProdanoVahou(double prodanoVahou) {
        this.prodanoVahou = stareMnozstvi - noveMnozstvi;
    }

    public double getRozdil() {
        this.rozdil = this.prodanoVahou - this.prodanoPokladnou;
        return rozdil;
    }

    public void setRozdil(double rozdil) {
        this.rozdil = rozdil;
    }

    public Material findMaterialByKontrola() {
        String query = "SELECT m FROM Kontrola k JOIN k.surovina m";
        String[] paramNames = new String[]{};
        String[] paramTypes = new String[]{};
        String[] paramValues = new String[]{};
        List<Material> res = executeQuery(query, paramNames, paramTypes, paramValues);
        return res.get(0);
    }
}
