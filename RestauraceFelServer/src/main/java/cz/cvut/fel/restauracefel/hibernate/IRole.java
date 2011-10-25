/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.restauracefel.hibernate;

/**
 * Rozhrani definujici metody, ktere musi implementovat trida, ktera ma
 * reprezentovat uzivatelskou roli.
 *
 * @author Jarda
 */
public interface IRole {

    /**
     * Metoda vraci ID dane uzivatelkse role.
     *
     * @return ID dane role
     */
    public Integer getRoleId();

    /**
     * Metoda nastavujici ID pro danou uzivatelskou roli.
     *
     * @param roleId ID role
     */
    public void setRoleId(Integer roleId);

    /**
     * Metoda navracejici nazev dane uzivatelske role.
     *
     * @return nazev dane role
     */
    public String getName();

    /**
     * Metoda nastavujici nazev pro danou uzivatelskou roli.
     *
     * @param name nazev dane role
     */
    public void setName(String name);

}
