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
 *
 * @author Thomas
 */
public class AEFND {
    public ArrayList<Etat> transiter(ArrayList<Etat> T, char a, Automate aut) {
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
                    if(tra.getEntree() == a.getMeta()) {
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
        ArrayList<Character> voc = a.getVoc();
        for (char v : voc ) {
            System.out.println(v);
        }
        voc.remove(voc.size()-1);
        
        while(!P.isEmpty()) {
            ArrayList<Etat> T = P.pop();
            //if(!T.isEmpty()) {
                if(!L.contains(T)) {
                    L.add(T);
                    for(char c : voc) {
                        ArrayList<Etat> U = lambda_fermeture(transiter(T, c, a), a);
                        //if(!U.isEmpty()) {
                            D.add(new TransitionND(T, U, c));
                            P.push(U);
                        //}
                    }
                }
            //}
        }
        
        System.out.println("Etats : ");
        for(ArrayList<Etat> e : L) {
            System.out.println(e);
        }
        
        System.out.println("Transitions : ");
        for(TransitionND t : D) {
            System.out.println(t);
        }
        
        return toAutomate(L, D, a.getVoc(), a.getMeta());
    }
    
    public Automate toAutomate(ArrayList<ArrayList<Etat>> L, ArrayList<TransitionND> D, ArrayList<Character> voc, char meta) {
        ArrayList<Etat> etats = new ArrayList<>();
        ArrayList<Transition> transitions = new ArrayList<>();
        for (ArrayList<Etat> e : L) {
            etats.add(new Etat(L.indexOf(e), false, false));
        }
        
        etats.get(0).setIsInit(true);
        
        for (TransitionND t : D) {
            Etat e = etats.get(L.indexOf(t.getEntree()));
            
            for(Etat a : t.getEntree()) {
                if(a.isIsFinal())
                    e.setIsFinal(true);
            }
            
            Etat s = etats.get(L.indexOf(t.getSortie()));
            
            for(Etat b : t.getSortie()) {
                if(b.isIsFinal())
                    e.setIsFinal(true);
            }
            
            transitions.add(new Transition(e, s, t.getCaractere()));
        }
        
        return new Automate(etats, transitions, voc, meta);
    }
    
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
