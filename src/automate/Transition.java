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
public class Transition {
    private Etat etatEntree;
    private Etat etatSortie;
    private char entree;
    private char sortie;

    public Transition(Etat etatEntree, Etat etatSortie, char entree, char sortie) {
        this.etatEntree = etatEntree;
        this.etatSortie = etatSortie;
        this.entree = entree;
        this.sortie = sortie;
    }

    public Transition(Etat etatEntree, Etat etatSortie) {
        this.etatEntree = etatEntree;
        this.etatSortie = etatSortie;
    }

    public Transition(Etat etatEntree, Etat etatSortie, char entree) {
        this.etatEntree = etatEntree;
        this.etatSortie = etatSortie;
        this.entree = entree;
        this.sortie ='#';
    }

    public Etat getEtatEntree() {
        return etatEntree;
    }

    public void setEtatEntree(Etat etatEntree) {
        this.etatEntree = etatEntree;
    }

    public Etat getEtatSortie() {
        return etatSortie;
    }

    public void setEtatSortie(Etat etatSortie) {
        this.etatSortie = etatSortie;
    }

    public char getEntree() {
        return entree;
    }

    public void setEntree(char entree) {
        this.entree = entree;
    }

    public char getSortie() {
        return sortie;
    }

    public void setSortie(char sortie) {
        this.sortie = sortie;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }else if(obj instanceof Transition) {
            return etatEntree.equals(((Transition) obj).etatEntree) && 
                    etatSortie.equals(((Transition) obj).etatSortie);
        }
        return false;
    }
    
    
}
