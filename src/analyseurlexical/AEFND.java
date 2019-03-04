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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author Thomas
 */
public class AEFND {
    public ArrayList<Etat> transiter(List<Etat> T, char a, Automate aut) {
        ArrayList<Etat> F = new ArrayList<>();
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
    
    public ArrayList<Etat> lambda_fermeture(List<Etat> T, Automate a) {
        List<Transition> V = a.getTransitions();
        ArrayList<Etat> F = new ArrayList<>();
        ArrayList<Etat> P = new ArrayList<>();
        for (Etat e : T) {
            P.add(e);
        }
        
        while(!P.isEmpty()) {
            Etat t = P.get(0);
            if(!F.contains(t)) {
                F.add(t);
                ArrayList<Transition> trans_t = getTransitionsof(t, V);
                for( Transition tra : trans_t) {
                    if(tra.getEntree() == '#') {
                        P.add(tra.getEtatSortie());
                    }
                }     
            }
            P.remove(t);
        }
        
        return F;
    }
    
    
    public ArrayList<Transition> getTransitionsof(Etat e, List<Transition> trans) {
        ArrayList<Transition> tr = new ArrayList<>();
        for (Transition t : trans) {
            if(t.getEtatEntree().equals(e))
                tr.add(t);
        }
        
        return tr;
    }
    
    public Automate dertiminiser(Automate a) {
        Stack<ArrayList<Etat>> P = new Stack<>();
        P.push(lambda_fermeture(a.getInitiaux(), a));
        ArrayList<ArrayList<Etat>> L = new ArrayList<>();
        ArrayList<TransitionND> D = new ArrayList<>();
        
        while(!P.isEmpty()) {
            ArrayList<Etat> T = P.pop();
            if(!L.contains(T)) {
                L.add(T);
                
            }
        }
        return null;
    }
}
