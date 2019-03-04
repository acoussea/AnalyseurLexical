/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automate;

import java.util.ArrayList;

/**
 *
 * @author couss
 */
public class Automate {
    private ArrayList<Etat> etats;
    private ArrayList<Transition> transitions;

    public Automate(ArrayList<Etat> etats, ArrayList<Transition> transitions) {
        this.etats = etats;
        this.transitions = transitions;
    }

    public Automate(ArrayList<Etat> etats) {
        this.etats = etats;
    }

    public ArrayList<Etat> getEtats() {
        return etats;
    }

    public void setEtats(ArrayList<Etat> etats) {
        this.etats = etats;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }
    
    
    
}
