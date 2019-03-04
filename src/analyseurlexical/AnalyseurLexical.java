
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyseurlexical;

import automate.Automate;
import automate.Etat;
import automate.Transition;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;

/**
 *
 * @author couss
 */
public class AnalyseurLexical {
    private ArrayList<Ligne> lignes;
    private File descr;
    private Automate automate;
    
    public AnalyseurLexical() {
        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = dialog.getDirectory()+dialog.getFile();
        System.out.println(file);
        this.descr = new File(file);
        this.lignes = new ArrayList<>();
        this.initAnalyse();
        dialog.dispose();
    }

    public AnalyseurLexical(File descr) {
        this.descr = descr;
        this.lignes = new ArrayList<>();
        this.initAnalyse();
    }

    public void initAnalyse(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.descr));
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    this.ajoutLigne(line);
                }
            } catch (IOException ex) {
                Logger.getLogger(AnalyseurLexical.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AnalyseurLexical.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ArrayList<Etat> etats = new ArrayList<>();
        ArrayList<Transition> trans = new ArrayList<>();
        ArrayList<Character> voc = new ArrayList<>();
        for(Ligne l : lignes){
            switch(l.getType()){
                case 'E' :
                    for(int i=0;i<Integer.parseInt(l.getLexemes().get(0));i++){
                    etats.add(new Etat(i));
                    }
                    break;
                case 'I' :
                    for(int i=0;i<l.getLexemes().size();i++){
                        for(Etat e : etats){
                            if(e.getNumero()==Integer.parseInt(l.getLexemes().get(i))){
                                e.setIsInit(true);
                            }
                        }
                    }
                    
                    break;
                case 'F' :
                    boolean existeEntree = false;
                    for(Etat e : etats){
                        if(e.isIsFinal()){
                            existeEntree=true;
                        }
                    }
                    if(!existeEntree){//entree par def = 0
                        etats.get(0).setIsInit(true);
                    }
                    //------
                    for(int i=0;i<l.getLexemes().size();i++){
                        for(Etat e : etats){
                            if(e.getNumero()==Integer.parseInt(l.getLexemes().get(i))){
                                e.setIsFinal(true);
                            }
                        }
                    }
                    break;
                case 'T' :
                    Etat entree=null,sortie=null;
                    if(l.getLexemes().size()>3){
                        for(Etat e : etats){
                            if(e.getNumero()==Integer.parseInt(l.getLexemes().get(0))){
                                entree = e;
                            }
                            if(e.getNumero()==Integer.parseInt(l.getLexemes().get(2))){
                                sortie = e;
                            }
                        }
                        trans.add(new Transition(entree, sortie, l.getLexemes().get(1).charAt(1), l.getLexemes().get(3).charAt(1)));
                    }else{
                        for(Etat e : etats){
                            if(e.getNumero()==Integer.parseInt(l.getLexemes().get(0))){
                                entree = e;
                            }
                            if(e.getNumero()==Integer.parseInt(l.getLexemes().get(2))){
                                sortie = e;
                            }
                        }
                        trans.add(new Transition(entree, sortie, l.getLexemes().get(1).charAt(1)));
                    }
                    break;
                case 'V' : 
                    for (String str : l.getLexemes()) {
                        voc.add(str.charAt(0));
                    }
            }
        }
        
        this.automate = new Automate(etats, trans, voc);
    }
    
    public void traitementEntree(String entree){
        System.out.println("Traitement des phrases lues :");
        System.out.println("");
        Etat init = this.automate.getInit();
        String[] motsEntree = entree.split("\n");
        Etat courant;
        for(int i=0;i<motsEntree.length;i++){
            boolean entreeTraitee = false;//true une fois que le premier carac de l'entrée est passé
            courant = init;
            String phraseSortie="";
            boolean sortie = false;
            if(!motsEntree[i].equals("###")){
                String mot = motsEntree[i];
                for(Transition t : this.automate.getTransitions()){
                        if(t.getEtatEntree().getNumero()==init.getNumero() && !entreeTraitee){
                            if(t.getEntree()==mot.charAt(0)){
                                entreeTraitee=true;
                                courant = t.getEtatSortie();
                                if(t.getSortie()!='#'){
                                    System.out.println("Etat courant : " + init.getNumero() + ",Entrée : " + mot.charAt(0) + ",Sortie : "+ t.getSortie() + ", Transition trouvée");
                                    phraseSortie+=t.getSortie();
                                }else{
                                    System.out.println("Etat courant : " + init.getNumero() + ",Entrée : " + mot.charAt(0) + ", Transition trouvée");
                                }
                            }
                        }
                    }
                for(int j=1;j<mot.length();j++){
                    boolean transFind = false; //Pour ne pas lire toutes les transitions dans la boucle...
                    for(Transition t : this.automate.getTransitions()){
                        if(entreeTraitee && !sortie){
                            if((t.getEtatEntree().getNumero() == courant.getNumero()) && (t.getEntree() == mot.charAt(j)) && !transFind){
                                transFind=true;
                                if(t.getSortie()!='#'){
                                    System.out.println("Etat courant : " + courant.getNumero() + ",Entrée : " + mot.charAt(j) + ",Sortie : "+ t.getSortie() + ", Transition trouvée");
                                    phraseSortie+=t.getSortie();
                                }else{
                                    System.out.println("Etat courant : " + courant.getNumero() + ",Entrée : " + mot.charAt(j) + ", Transition trouvée");
                                }
                                courant = t.getEtatSortie();
                                if(j==mot.length()-1 && !sortie ){//dernier caractère, fin de chaine
                                    sortie=true;
                                    System.out.println("Etat courant : " + courant.getNumero() + " Fin de chaîne");
                                    if(courant.isIsFinal()){
                                        System.out.println("Entrée acceptante");
                                    }else{
                                        System.out.println("Entrée non-acceptante");
                                    }
                                    System.out.println("La sortie de cette phrase est : " + phraseSortie);
                                    System.out.println("---------------------------");
                                }
                            }
                        }else if(!sortie){
                            System.out.println("Etat Courant : " + init.getNumero() +", Entrée : " + mot.charAt(j) +", Aucune transition");
                        }
                    }
                }
            }
        }
    }
    
    public void descrToDot(){
        String fileDot = descr.getName()+".dot";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileDot));
            String init = Integer.toString(this.automate.getInit().getNumero());
            writer.write("digraph G {\n");
            writer.write("\t\"\" [shape=none]\n");
            for(Etat e : this.automate.getFinaux()){
                    writer.write('\t'+Integer.toString(e.getNumero()) + " [shape=doublecircle]\n");
            }
            for(Etat e : this.automate.getInitiaux()){
                writer.write("\t\"\" ->"+Integer.toString(e.getNumero())+"\n");
            }
            for(Transition t : this.automate.getTransitions()){
                String entree = Integer.toString(t.getEtatEntree().getNumero());
                String sortie = Integer.toString(t.getEtatSortie().getNumero());
                String motEntree =String.valueOf(t.getEntree());
                String motSortie = String.valueOf(t.getSortie());
                writer.write('\t'+entree + " -> " + sortie +"[label=\""+motEntree+""+"/"+motSortie+"\"];\n");
            }
            writer.write("}");
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(AnalyseurLexical.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void afficheDescrLignes(){
        System.out.println("---------------------------");
        for(Ligne l : this.getLignes()){
            System.out.println(l.toString());
        }
        System.out.println("---------------------------");
        System.out.println("");
    }
    
    public ArrayList<Ligne> getLignes() {
        return lignes;
    }

    public void setLignes(ArrayList<Ligne> lignes) {
        this.lignes = lignes;
    }
    
    public void ajoutLigne(String content){
        this.lignes.add(new Ligne(content));
    }
    
    public void ajoutLigne(Ligne ligne){
        this.lignes.add(ligne);
    }
    
    public Automate getAutomate() {
        return automate;
    }

    public File getDescr() {
        return descr;
    }
    
    
    
}