/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automate;

/**
 * Représentation d'un état d'un automate
 * @author Axel Cousseau
 */
public class Etat {
    private int numero;
    private boolean isInit;
    private boolean isFinal;

    /**
     * Constructeur de base
     * @param numero le numero associé à l'état
     * @author Axel Cousseau
     */
    public Etat(int numero) {
        this.numero = numero;
    }

    /**
     * Constructeur spécifique
     * @param numero le numéro associé à l'état
     * @param isInit si l'état est un état initial ou non
     * @param isFinal si l'état est final ou non
     * @author Axel Cousseau
     */
    public Etat(int numero, boolean isInit, boolean isFinal) {
        this.numero = numero;
        this.isInit = isInit;
        this.isFinal = isFinal;
    }

    /**
     * Constructeur par défaut
     * @author Axel Cousseau
     */
    public Etat() { //defaut
        this.numero=0;
        this.isInit=true;
    }

    /**
     * Getter numéro d'état
     * @return le numéro associé à l'état
     * @author Axel Cousseau
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Getter init
     * @return etat initial ou non
     * @author Axel Cousseau
     */
    public boolean isIsInit() {
        return isInit;
    }

    /**
     * Getter final
     * @return etat final ou non
     * @author Axel Cousseau
     */
    public boolean isIsFinal() {
        return isFinal;
    }

    /**
     * Setter numero de l'état
     * @param numero le numéro associé à l'état
     * @author Axel Cousseau
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Setter final
     * @param isInit etat initial ou non
     * @author Axel Cousseau
     */
    public void setIsInit(boolean isInit) {
        this.isInit = isInit;
    }

    /**
     * Setter final
     * @param isFinal etat final ou non
     * @author Axel Cousseau
     */
    public void setIsFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    /**
     * Methode Equals etat
     * @param obj l'état à tester
     * @return boolean true si l'état est égal à celui en param
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        else if(obj instanceof Etat){
            return this.numero == ((Etat) obj).numero;
        }
        return false; //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public String toString() {
        return "" + this.numero;
    }
    
    
}
