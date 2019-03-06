/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automate;

import java.util.ArrayList;

/**
 * Transition manipulant des ensembles d'états entrants ou sortants.
 * @author Thomas
 */
public class TransitionND {
    private ArrayList<Etat> entree, sortie;
    private char caractere;
    
    public TransitionND(ArrayList<Etat> e, ArrayList<Etat> s, char c) {
        this.entree = e;
        this.sortie = s;
        this.caractere = c;
    }
    
    @Override
    public String toString() {
        return this.entree + " -> " + this.caractere + " -> " + this.sortie;
    }

    public ArrayList<Etat> getEntree() {
        return entree;
    }

    public ArrayList<Etat> getSortie() {
        return sortie;
    }

    public char getCaractere() {
        return caractere;
    }
    
    
}
