/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automate;

/**
 * Représentation d'une transition ->> EtatEntree + EtatSortie + MotEntree + Mot sortie
 * ==> Transition d'un état à un autre suite à l'entrée d'un mot, retournant un mot de sortie
 * @author Axel Cousseau
 */
public class Transition {
    private Etat etatEntree;
    private Etat etatSortie;
    private char entree;
    private char sortie;

    /**
     * Constructeur
     * @param etatEntree Etat initial de la transition
     * @param etatSortie Etat final de la transition
     * @param entree le mot passé en entrée
     * @param sortie le mot retourné
     * @author Axel Cousseau
     */
    public Transition(Etat etatEntree, Etat etatSortie, char entree, char sortie) {
        this.etatEntree = etatEntree;
        this.etatSortie = etatSortie;
        this.entree = entree;
        this.sortie = sortie;
    }

    /**
     * Constructeur
     * @param etatEntree Etat initial de la transition
     * @param etatSortie Etat final de la transition
     * @author Axel Cousseau
     */
    public Transition(Etat etatEntree, Etat etatSortie) {
        this.etatEntree = etatEntree;
        this.etatSortie = etatSortie;
    }

    /**
     * 
     * @param etatEntree Etat initial de la transition
     * @param etatSortie Etat final de la transition
     * @param entree le mot passé en entrée
     * @author Axel Cousseau
     */
    public Transition(Etat etatEntree, Etat etatSortie, char entree) {
        this.etatEntree = etatEntree;
        this.etatSortie = etatSortie;
        this.entree = entree;
        this.sortie ='#';
    }

    /**
     * Getter etat d'entrée
     * @return Etat initial de la transition
     * @author Axel Cousseau
     */
    public Etat getEtatEntree() {
        return etatEntree;
    }

    /**
     * Setter etat d'entée
     * @param etatEntree Etat initial de la transition
     * @author Axel Cousseau
     */
    public void setEtatEntree(Etat etatEntree) {
        this.etatEntree = etatEntree;
    }

    /**
     * Getter etat de sortie
     * @return Etat final de la transition
     * @author Axel Cousseau
     */
    public Etat getEtatSortie() {
        return etatSortie;
    }

    /**
     * Setter etat de sortie
     * @param etatSortie Etat final de la transition
     * @author Axel Cousseau
     */
    public void setEtatSortie(Etat etatSortie) {
        this.etatSortie = etatSortie;
    }

    /**
     * Getter mot d'entrée
     * @return le mot passé en entrée
     * @author Axel Cousseau
     */
    public char getEntree() {
        return entree;
    }

    /**
     * Setter mot d'entée
     * @param entree le mot en entrée, lu
     * @author Axel Cousseau
     */
    public void setEntree(char entree) {
        this.entree = entree;
    }

    /**
     * Getter sortie
     * @return le mot retourné
     * @author Axel Cousseau
     */
    public char getSortie() {
        return sortie;
    }

    /**
     * Setter sortie
     * @param sortie le mot retourné
     * @author Axel Cousseau
     */
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
