/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automate;

/**
 *
 * @author couss
 */
public class Etat {
    private int numero;
    private boolean isInit;
    private boolean isFinal;

    public Etat(int numero) {
        this.numero = numero;
    }

    public Etat(int numero, boolean isInit, boolean isFinal) {
        this.numero = numero;
        this.isInit = isInit;
        this.isFinal = isFinal;
    }

    public Etat() { //defaut
        this.numero=0;
        this.isInit=true;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isIsInit() {
        return isInit;
    }

    public boolean isIsFinal() {
        return isFinal;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setIsInit(boolean isInit) {
        this.isInit = isInit;
    }

    public void setIsFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

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
