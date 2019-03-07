/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyseurlexical;

import automate.Automate;
import automate.Etat;
import automate.Transition;
import automate.TransitionND;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe AEFND ou Automate à Etats Finis Non Déterministe.
 * Reprend toutes les méthodes nécessaires à la génération, l'exportation et l'affichage
 * d'un automate non déterministe vers un automate déterministe.
 * @author Thomas
 */
public class AEFND {
    
    /**
     * Récupère la liste de tous les voisins F de la liste d'entrée T ayant comme transition
     * le caractère placé en paramètre a.
     * @param T Ensemble d'états
     * @param a caractère transitoire
     * @param aut Automate
     * @return liste d'Etats
     */
    public ArrayList<Etat> transiter(ArrayList<Etat> T, char a, Automate aut) {
        ArrayList<Etat> F = new ArrayList<>();          // Liste de sortie
        for(Etat e : T) {
            List<Transition> voisins = getTransitionsof(e, aut.getTransitions());
            for (Transition v : voisins) {
                if(v.getEntree() == a) {
                    Etat u = v.getEtatSortie();
                    if(!F.contains(u)) {
                        F.add(u);
                    }
                }
            }
        }
        
        return F;
    }
    
    /*
    public void afficher(Automate a) {
        System.out.println("NON DETERMINISTE --> DETERMINISTE : ");
        for (Etat e : a.getEtats()) {
            System.out.println(e.getNumero() + (e.isIsInit() ? "(init)" : ""));
        }
        
        for (Transition t : a.getTransitions()) {
            System.out.println("Transition : " + t.getEtatEntree().getNumero() + " -> " + t.getEtatSortie().getNumero()
            + "(" + t.getEntree() + "/" + t.getSortie() + ")");
        }
    }
    */

    /**
     * Récupère la liste d'états F rassemblant tous les états non déterministes
     * qui réalisent la lambda-fermeture des états qui composent la liste T.
     * @param T Ensemble d'états
     * @param a Automate
     * @return liste d'etats
     */
    public ArrayList<Etat> lambda_fermeture(List<Etat> T, Automate a) {
        List<Transition> V = a.getTransitions();        // Liste des transitions
        ArrayList<Etat> F = new ArrayList<>();          // Liste de sortie
        ArrayList<Etat> P = new ArrayList<>();          // Liste manipulée pendant l'opération
        for (Etat e : T) {
            P.add(e);
        }
        
        while(!P.isEmpty()) {
            Etat t = P.get(0);
            if(!F.contains(t)) {
                F.add(t);
                ArrayList<Transition> trans_t = getTransitionsof(t, V);
                for( Transition tra : trans_t) {
                    if(tra.getEntree() == a.getMeta()) {
                        P.add(tra.getEtatSortie());
                    }
                }     
            }
            P.remove(t);
        }
        
        return F;
    }
    
    /**
     * Récupère toutes les transitions dont l'état e est l'état entrant.
     * @param e Etat
     * @param trans Liste des transitions
     * @return Liste de transitions
     */
    public ArrayList<Transition> getTransitionsof(Etat e, List<Transition> trans) {
        ArrayList<Transition> tr = new ArrayList<>();           // Liste des transitions (sortie de la méhode)
        for (Transition t : trans) {
            if(t.getEtatEntree().equals(e))
                tr.add(t);
        }
        
        return tr;
    }
    
    /**
     * Déterminise un automate non-déterministe.
     * @param a Automate
     * @return Automate
     */
    public Automate dertiminiser(Automate a) {
        Stack<ArrayList<Etat>> P = new Stack<>();           // Pile manipulée pendant l'opération
        P.push(lambda_fermeture(a.getInitiaux(), a));       // Initialise la pile avec les lambda-fermetures des états initiaux
        ArrayList<ArrayList<Etat>> L = new ArrayList<>();   // Liste des ensembles détats générés
        ArrayList<TransitionND> D = new ArrayList<>();      // Liste des transitions générées
        ArrayList<Character> voc = a.getVoc();              // Vocabulaire d'entrée
        for (char v : voc ) {
            System.out.println(v);
        }
        voc.remove(voc.size()-1);
        
        while(!P.isEmpty()) {
            ArrayList<Etat> T = P.pop();                    // Extraction du premier élement de la pile
                if(!L.contains(T)) {                        // s'il n'est pas contenu dans la liste de fin
                    L.add(T);                               // Ajout de l'élement dans L
                    for(char c : voc) {                     // Parcours du vocabulaire d'entrée
                        ArrayList<Etat> U = lambda_fermeture(transiter(T, c, a), a);    // Récupération de l'ensemble "Etat sortant"
                            D.add(new TransitionND(T, U, c));       // Ajout de la transition T vers U passant par c
                            P.push(U);                      // Ajout de U dans P
                    }
                }
        }
        
        System.out.println("Etats : ");         // Affichage des états (débuguage)
        for(ArrayList<Etat> e : L) {
            System.out.println(e);
        }
        
        System.out.println("Transitions : ");   // Affichage des transitions (débuguage)
        for(TransitionND t : D) {
            System.out.println(t);
        }
        
        return toAutomate(L, D, a.getVoc(), a.getMeta());
    }
    
    /**
     * Renvoie un automate généré en fonction des listes d'états L et transitions D déterminisés,
     * du vocabulaire d'entrée voc et du méta-caractère meta : cette méthode est privée et utilisée
     * en interne par déterminiser.
     * @param L Liste des états déterministes(Ensembles d'états)
     * @param D Liste des transitions déterministes
     * @param voc Liste du vocabulaire d'entrée
     * @param meta Méta-caractère
     * @return Automate
     */
    private Automate toAutomate(ArrayList<ArrayList<Etat>> L, ArrayList<TransitionND> D, ArrayList<Character> voc, char meta) {
        ArrayList<Etat> etats = new ArrayList<>();
        ArrayList<Transition> transitions = new ArrayList<>();
        for (ArrayList<Etat> e : L) {           // Création d'un état pour chaque ensemble d'états,
                                                // ayant pour numéro la position de cet ensemble d'états dans la liste L
            etats.add(new Etat(L.indexOf(e), false, false));
        }
        
        // Etat initial
        etats.get(0).setIsInit(true);
        
        for (TransitionND t : D) { // Transition d'ensembles d'états vers Transition d'états
            Etat e = etats.get(L.indexOf(t.getEntree()));
            
            // Recherche des états finaux
            for(Etat a : t.getEntree()) {
                if(a.isIsFinal())
                    e.setIsFinal(true);
            }
            
            Etat s = etats.get(L.indexOf(t.getSortie()));
            
            transitions.add(new Transition(e, s, t.getCaractere()));
        }
        
        return new Automate(etats, transitions, voc, meta);
    }
    
    /**
     * Génère un fichier .descr a partir d'un automate a.
     * @param a Automate
     * @param name Nom de fichier
     */
    public void genereDescr(Automate a, String name){
        String fileDot = name+".descr";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileDot));
            
            writer.write("M '" + String.valueOf(a.getMeta()) + "'\n");
            
            String voc = "\"";
            for (char c : a.getVoc()) {
                voc+=c;
            }
            voc+="\"";
            writer.write("V " + voc + "\n");
            writer.write("E " + a.getEtats().size() + "\n");
            
            String F = "";
            for (Etat e : a.getEtats()) {
                if(e.isIsFinal()) {
                    F+=e.getNumero() + " ";
                }
            }
            writer.write("F " + F + "\n");
            
            for (Transition t : a.getTransitions()) {
                writer.write("T " + t.getEtatEntree().getNumero() + " '" + t.getEntree() + "' " + t.getEtatSortie().getNumero() + "\n"); 
            }
            
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(AnalyseurLexical.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
