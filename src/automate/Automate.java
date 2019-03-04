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
public class Automate { //DETERMINISTE
    private ArrayList<Etat> etats;
    private ArrayList<Transition> transitions;
    private ArrayList<Character> voc;

    public Automate(ArrayList<Etat> etats, ArrayList<Transition> transitions) {
        this.etats = etats;
        this.transitions = transitions;
    }

    public Automate(ArrayList<Etat> etats) {
        this.etats = etats;
    }
    
    public Automate(ArrayList<Etat> etats, ArrayList<Transition> transitions, ArrayList<Character> voc) {
        this.etats = etats;
        this.transitions = transitions;
        this.voc = voc;
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
    
    public Etat getInit(){
        Etat init = null;
        for(Etat e : etats){
            if(e.isIsInit()){
                init = e;
            }
        }
        return init;
    }
    
    public ArrayList<Etat> getInitiaux(){
        ArrayList<Etat> init = new ArrayList<>();
        for(Etat e : etats){
            if(e.isIsInit()){
                init.add(e);
            }
        }
        return init;
    }
    
    public ArrayList<Etat> getFinaux(){
        ArrayList<Etat> finaux = new ArrayList<>();
        for(Etat e : etats){
            if(e.isIsFinal()){
                finaux.add(e);
            }
        }
        return finaux;
    }

    public ArrayList<Character> getVoc() {
        return voc;
    }
    
    
}
