package cz.cvut.fel.restauracefel.hibernate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Trida zajistujici komunikace domenovych trid s databazi. Konkretni domenove
 * tridy dedi od teto tridy.
 *
 * @author Jarda
 */
public class DBEntity implements Serializable {

    protected static Session session = HibernateSessionFactory.getSession();

    /**
     * Ulozi objekt do databaze.
     *
     * @param object objekt ktery se ma ulozit do databaze
     */
    protected void create(Object object) {
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(object);
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        }
    }

    /**
     * Vymaze objekt z databaze.
     *
     * @param object objekt ktery ma byt smazan z databaze
     */
    protected void delete(Object object) {
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.delete(object);
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        }
    }

    /**
     * Aktualizuje objekt v databazi.
     *
     * @param object object ktery chceme aktualizovat
     */
    protected void update(Object object) {
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(object);
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        }
    }

    /**
     * Vykona dotaz, ktery dostane jako parametr a jako vysledek vrati seznam objektu
     * nebo null, pokud se neco pokazi.
     *
     * @param queryText samotny text dotazu
     * @param parameterNames nazvy parametru
     * @param parameterTypes datove typy parametru
     * @param parameterValues hodnoty parametru
     */
    protected static List executeQuery(String queryText, String[] parameterNames, String[] parameterTypes, Object[] parameterValues) {
        if (parameterNames.length != parameterValues.length ||
                parameterTypes.length != parameterNames.length) {
            return null;
        }

        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            Query query = session.createQuery(queryText);

            for (int i = 0; i < parameterNames.length; i++) {
                String parameterType = parameterTypes[i].toLowerCase();

                if (parameterType.equals("string")) {
                    query.setString(parameterNames[i], (String) parameterValues[i]);
                } else if (parameterType.equals("int") || parameterType.equals("integer")) {
                    query.setInteger(parameterNames[i], (Integer) parameterValues[i]);
                } else if (parameterType.equals("date")) {
                    query.setDate(parameterNames[i], (Date) parameterValues[i]);
                } else if (parameterType.equals("double")) {
                    query.setDouble(parameterNames[i], (Double) parameterValues[i]);
                }
            }

            List result = query.list();

            tx.commit();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            return null;
        }
    }

    /**
     * Metoda najde datovy zaznam z databaze odpovidajici zadanym parametrum.
     *
     * @param className nazev tridy, pro kterou hledame zaznam
     * @param column nazev promenne, reprezentujici sloupec v databazi
     * @param id hodnota, podle ktere se bude vyhledavat
     *
     * @return odpovidajici databazovy zaznam
     */
    public static DBEntity findById(String className, String column, Integer id) {
        String[] paramNames = {"id"};
        String[] paramTypes = {"Integer"};
        Object[] paramValues = {id};

        //String idParamName = className.substring(0, 1).toLowerCase() + className.substring(1) + "ID";
        String idParamName = column;

        // POZOR, tady parametrem neni nazev tabulky a sloupecku, ale nazev tridy a jejich atributu
        List res = executeQuery("from " + className + " x where x." + idParamName + " = :id", paramNames, paramTypes, paramValues);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return (DBEntity) res.get(0);
    }

    /**
     * Metoda navraci odpovidajici instanci potomka tridy DBEntity. Vraci pouze
     * nesmazane zaznamy.
     *
     * @param className nazev tridy, pro kterou hledame zaznam
     * @param columnId oznacuje nazev sloupce, kde je uvedeno ID
     * @param id id hodnota, podle ktere se bude vyhledavat
     * @param columnDel nazev sloupce, kde je uvedena hodnota, podle ktere se
     * rozhoduje zda je dany zaznam smazan ci nikoliv
     * @param del ID hodnota, ktera oznacuje nesmazany zaznam
     * @return odpovidajici instance potomka tridy DBEntity
     */
    public static DBEntity findByIdNotDeleted(String className, String columnId, Integer id, String columnDel, Integer del) {
        String[] paramNames = {"id", "del"};
        String[] paramTypes = {"Integer", "Integer"};
        Object[] paramValues = {id, del};

        // POZOR, tady parametrem neni nazev tabulky a sloupecku, ale nazev tridy a jejich atributu
        String query = "from " + className + " x where x." + columnId + " = :id and x." + columnDel + " = :del";
        List res = executeQuery(query, paramNames, paramTypes, paramValues);

        if (res == null || res.isEmpty()) {
            return null;
        }
        return (DBEntity) res.get(0);
    }

    /**
     * Vrati vsechny zaznamy pro danou tridu.
     *
     * @param className nazev tridy
     * @return List odpovodajicich instanci dane tridy.
     */
    public static List findAll(String className) {
        List res = executeQuery("from " + className + " x", new String[0], new String[0], new Object[0]);
        if (res == null || res.isEmpty()) {
            return null;
        }

        return res;
    }

    /**
     * Metoda navraci seznam vsech databazovych zaznamu tridy "className", ktere
     * nejsou smazany (flag "isDeleted" je nastaven na 0).
     *
     * @param className nazev tridy
     * @param columnDeleted nazev sloupce, kde je uvedena hodnota, podle ktere
     * se rozhoduje zda je dany zaznam smazan ci nikoliv
     * @param del ID hodnota, ktera oznacuje nesmazany zaznam
     * @return list odpovodajicich instanci dane tridy
     */
    public static List findAllNotDeleted(String className, String columnDeleted, Integer del) {
        String[] paramNames = {"del"};
        String[] paramTypes = {"Integer"};
        Object[] paramValues = {del};

        String query = "from " + className + " x where x." + columnDeleted + " = :del";
        List res = executeQuery(query, paramNames, paramTypes, paramValues);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res;
    }

    /**
     * Metoda najde zaznam, jenz odpovida specifikovanym parametrum. Vraci pouze
     * nesmazane zaznamy.
     *
     * @param className nazev tridy, jejiz zaznam hledame
     * @param columnName nazev promenne (sloupce v databazi) pro ktery hledame odpovidajici hodnotu
     * @param name hodnota, podle ktere se vyhledava odpovidajici zaznam
     * @param columnDel nazev sloupce, kde je uvedena hodnota, podle ktere se
     * rozhoduje zda je dany zaznam smazan ci nikoliv
     * @param del ID hodnota, ktera oznacuje nesmazany zaznam
     * @return instance daneho potomku tridy DBEntity
     */
    public static DBEntity findByStringNameNotDeleted(String className, String columnName, String name, String columnDel, Integer del) {
        String[] paramNames = {"name", "del"};
        String[] paramTypes = {"String", "Integer"};
        Object[] paramValues = {name, del};

        String query = "from " + className + " x where x." + columnName + " = :name and x." + columnDel + " = :del";
        List res = executeQuery(query, paramNames, paramTypes, paramValues);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return (DBEntity) res.get(0);
    }

    /**
     * Najde zaznam, jenz odpovida specifikovanym parametrum.
     *
     * @param className nazev tridy, jejiz zaznam hledame
     * @param column nazev promenne (sloupce v databazi) pro ktery hledame odpovidajici hodnotu
     * @param name hodnota, podle ktere se vyhledava odpovidajici zaznam
     * @return
     */
    public static DBEntity findByStringName(String className, String column, String name) {
        String[] paramNames = {"name"};
        String[] paramTypes = {"String"};
        Object[] paramValues = {name};

        String idParamName = column;

        List res = executeQuery("from " + className + " x where x." + idParamName + " = :name", paramNames, paramTypes, paramValues);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return (DBEntity) res.get(0);
    }
}
