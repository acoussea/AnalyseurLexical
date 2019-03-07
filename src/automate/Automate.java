/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automate;

import java.util.ArrayList;

/**
 * Structure de données permettant de stocker les automates lus
 * @author Axel Cousseau
 */
public class Automate {
    private ArrayList<Etat> etats;
    private ArrayList<Transition> transitions;
    private ArrayList<Character> voc; //Vocabulaire d'entrée ex : "01" ou "abcd"
    private char meta; //Le méta-caractère de l'automate (lambda, par defaut à la création '#')

    /**
     * Constructeur simple
     * @param etats Liste des états de l'automate
     * @param transitions Liste des transitions de l'automate = (EtatEntree, EtatSortie, motEntree, motSortie)
     * @author Axel Cousseau
     */
    public Automate(ArrayList<Etat> etats, ArrayList<Transition> transitions) {
        this.etats = etats;
        this.transitions = transitions;
    }

    
    /**
     * Constructeur 
     * @param etats Liste des états de l'automate
     * @author Axel Cousseau
     */
    public Automate(ArrayList<Etat> etats) {
        this.etats = etats;
    }
    
    /**
     * Constructeur 
     * @param etats Liste des états de l'automate
     * @param transitions Liste des transitions de l'automate = (EtatEntree, EtatSortie, motEntree, motSortie)
     * @param voc Liste des caractères acceptés en entrée (vocabulaire d'entrée)
     * @author Axel Cousseau
     */
    public Automate(ArrayList<Etat> etats, ArrayList<Transition> transitions, ArrayList<Character> voc) {
        this.etats = etats;
        this.transitions = transitions;
        this.voc = voc;
    }
    
    /**
     * @param etats Liste des états de l'automate
     * @param transitions Liste des transitions de l'automate = (EtatEntree, EtatSortie, motEntree, motSortie)
     * @param voc Liste des caractères acceptés en entrée (vocabulaire d'entrée)
     * @param meta le méta-caractère = mot vide
     * @author Axel Cousseau
     */
    public Automate(ArrayList<Etat> etats, ArrayList<Transition> transitions, ArrayList<Character> voc, char meta) {
        this.etats = etats;
        this.transitions = transitions;
        this.voc = voc;
        this.meta = meta;
    }

    /**
     * Getter Etats
     * @return la liste d'états de l'automate
     * @author Axel Cousseau
     */
    public ArrayList<Etat> getEtats() {
        return etats;
    }

    /**
     * Setter Etats
     * @param etats la liste d'états de l'automate
     * @author Axel Cousseau
     */
    public void setEtats(ArrayList<Etat> etats) {
        this.etats = etats;
    }

    /**
     * Getter Transitions
     * @return la liste des transitions de l'automate
     * @author Axel Cousseau
     */
    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    /**
     * Setter transitions
     * @param transitions  la liste des transitions de l'automate
     * @author Axel Cousseau
     */
    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }
    
    /**
     * Getter EtatInitial
     * @return l'état initial de l'automate
     * @author Axel Cousseau
     */
    public Etat getInit(){
        Etat init = null;
        for(Etat e : etats){
            if(e.isIsInit()){
                init = e;
            }
        }
        return init;
    }
    
    /**
     * Getter états initiaux
     * @return La liste des états initiaux, s'il y en a plusieurs (non-deterministe)
     * @author Axel Cousseau
     */
    public ArrayList<Etat> getInitiaux(){
        ArrayList<Etat> init = new ArrayList<>();
        for(Etat e : etats){
            if(e.isIsInit()){
                init.add(e);
            }
        }
        return init;
    }
    
    /**
     * Getter états finaux
     * @return La liste des états finaux de l'automate
     * @author Axel Cousseau
     */
    public ArrayList<Etat> getFinaux(){
        ArrayList<Etat> finaux = new ArrayList<>();
        for(Etat e : etats){
            if(e.isIsFinal()){
                finaux.add(e);
            }
        }
        return finaux;
    }

    /**
     * Getter vocabulaire d'entrée
     * @return la liste des mots acceptés en entrée
     * @author Thomas Poyault
     */
    public ArrayList<Character> getVoc() {
        return voc;
    }

    /**
     * Getter méta caractère
     * @return le méta caractère
     * @author Thomas Poyault
     */
    public char getMeta() {
        return meta;
    }
    
    
}
