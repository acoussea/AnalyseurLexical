
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
 * La classe AnalyseurLexical lit les fichiers descr, analyse leur contenu et permet
 * de savoir si un fichier est valide ou non.
 * Elle permet également de traiter une entrée pour savoir si une elle est acceptée ou non
 * Elle contient une méthode permettant l'export du fichier lu en .dot
 * @author Axel Cousseau
 */
public class AnalyseurLexical {
    public static final String ANSI_WHITE = "\u001B[37m"; //simple detail visuel
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m"; //permet de modifier la couleur des lignes
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m"; // dans le terminal -> utile pour les erreurs

    private ArrayList<Ligne> lignes;
    private File descr;
    private Automate automate;
    private boolean automateFalse;
    private String meta;
    
    /**
     * Constructeur Analyseur lexical
     * Ouvre une fenêtre de selection de fichier à lire
     * Début de l'analyse
     * @author Axel Cousseau
     */
    public AnalyseurLexical() {
        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true); //Fenêtre de selection de fichier
        String file = dialog.getDirectory()+dialog.getFile();
        System.out.println(file);
        this.descr = new File(file);
        this.lignes = new ArrayList<>();
        this.initAnalyse(); //On débute l'analyse du fichier
        dialog.dispose();
    }

    /**
     * Constructeur Analyseur lexical par fichier
     * Début de l'analyse
     * @param descr Fichier, si passé directement en paramètre
     * @author Axel Cousseau
     */
    public AnalyseurLexical(File descr) {
        this.descr = descr;
        this.lignes = new ArrayList<>();
        this.initAnalyse(); //analyse du fichier
    }

    /**
     * Lecture du fichier et stockage dans des lignes pour analyse lexicale
     * @author Axel Cousseau
     */
    public void initAnalyse(){
        try { //Lecture du fichier
            BufferedReader br = new BufferedReader(new FileReader(this.descr));
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    this.ajoutLigne(line); //stockage de chaque ligne du fichier lue
                }                            //Dans une Ligne
            } catch (IOException ex) {
                Logger.getLogger(AnalyseurLexical.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AnalyseurLexical.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(this.analyseLexicale()){ //On analyse pour determiner la validité d'un fichier
            this.automateFalse=false; //Si valide, on peut créer l'automate
            this.createAutomate();
            System.out.println(ANSI_GREEN_BACKGROUND+"Analyse effectuée"+ANSI_GREEN_BACKGROUND);
        }else{
            this.automateFalse=true; //Sinon, pas d'automate, message d'erreur
            System.out.println(ANSI_RED_BACKGROUND+"Erreur : Analyse impossible"+ANSI_RED_BACKGROUND);
        }
    }
    
    /**
     * Création de l'automate grâce aux lignes
     * @author Axel Cousseau
     */
    public void createAutomate(){
        ArrayList<Etat> etats = new ArrayList<>();
        ArrayList<Transition> trans = new ArrayList<>();
        ArrayList<Character> voc = new ArrayList<>();
        for(Ligne l : lignes){ //On récupère le type de chaque ligne car traitement different
            switch(l.getType()){
                case 'E' :
                    for(int i=0;i<Integer.parseInt(l.getLexemes().get(0));i++){
                        etats.add(new Etat(i)); // Ligne E, seul paramètre = nb d'état, on en crée autant
                    }
                    break;
                case 'I' :
                    for(int i=0;i<l.getLexemes().size();i++){
                        for(Etat e : etats){ //On parcours nos états
                            if(e.getNumero()==Integer.parseInt(l.getLexemes().get(i))){
                                e.setIsInit(true);//Si le num est égal à celui dans la ligne = etat init
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
                    if(!existeEntree){//entree par def = 0, si pas d'entrée à la case au dessus
                        etats.get(0).setIsInit(true);
                    }
                    for(int i=0;i<l.getLexemes().size();i++){
                        for(Etat e : etats){
                            if(e.getNumero()==Integer.parseInt(l.getLexemes().get(i))){
                                e.setIsFinal(true);//pareil que pour init
                            }
                        }
                    }
                    break;
                case 'T' :
                    Etat entree=null,sortie=null;
                    if(l.getLexemes().size()>3){ // = 3 param -> pas de motSortie
                        for(Etat e : etats){ //On recupère les état entrée et sortie avec leur num
                            if(e.getNumero()==Integer.parseInt(l.getLexemes().get(0))){
                                entree = e;
                            }
                            if(e.getNumero()==Integer.parseInt(l.getLexemes().get(2))){
                                sortie = e;
                            }
                        } //On crée une transation avec nos paramètres
                        trans.add(new Transition(entree, sortie, l.getLexemes().get(1).charAt(1), l.getLexemes().get(3).charAt(1)));
                    }else{ //Si 4 param, mot sortie, on fait pareil
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
                case 'V' : //On ajoute nos char à la liste des mots d'entrée acceptés
                    for (String str : l.getLexemes()) {
                        voc.add(str.charAt(0));
                    }
                    break;
            }
        }//Création de l'automate 
        this.automate = new Automate(etats, trans, voc, this.meta.charAt(0));
    }
    
    /**
     * Analyse des lignes du fichier pour determiner sa validité
     * @return true si les lignes sont composées de lexemes valides
     * @author Axel Cousseau
     */
    public boolean analyseLexicale(){
        this.meta ="#"; //par defaut
        int cpt=1; //Pour compter les lignes
        int nbEtats=1000; // init élevé au cas ou problème verif
        boolean fichierOk = true;
        boolean v=false,e=false,f = false; // Lignes obligatoires
        Ligne ligneV=null,ligneO=null; //Pour récup les caractères acceptés entrée/sortie
        for(int i = 0;i<this.lignes.size()-1;i++){ 
            switch(this.lignes.get(i).getType()){//Parcours des lignes et verif pour chaque type
                case 'C' : 
                    break;                 //Si une ligne n'est pas conforme à sa description (annexe), affiche erreur et retourne false
                case 'M' : 
                    meta = this.lignes.get(i).getLexemes().get(0); //On récupère le métacaractère lié au fichier
                    if(this.lignes.get(i).getLexemes().size()!=1){
                        System.out.println(ANSI_RED_BACKGROUND+"Erreur : La ligne M ne respecte pas sa description (1 caractère), ligne "+cpt+ANSI_RED_BACKGROUND);
                        return false;
                    }
                    break;
                case 'E' :
                    try{
                        nbEtats = Integer.parseInt(this.lignes.get(i).getLexemes().get(0)); //Ligne E : doit contenir un nombre, on verifie que s'en est un
                    }catch(Exception exc){
                        System.out.println(ANSI_RED_BACKGROUND+ANSI_WHITE+"Erreur : E ne contient pas un nombre -> "+this.lignes.get(i).getLexemes().get(0)+", ligne "+cpt+ANSI_WHITE+ANSI_RED_BACKGROUND);
                        return false;
                    }
                    e=true; //On notifie que la ligne existe
                    break;
                case 'I' :
                    for(int j=0;j<this.lignes.get(i).getLexemes().size();j++){
                        try{
                            Integer.parseInt(this.lignes.get(i).getLexemes().get(j)); //pareil que ligne E pour la liste I
                        }catch(Exception ex){
                            System.out.println(ANSI_RED_BACKGROUND+"Erreur : I ne contient pas des nombres -> " + this.lignes.get(i).getLexemes().get(j)+", ligne "+cpt+ANSI_RED_BACKGROUND);
                            return false;
                        }
                    }
                    break;
                case 'F' : 
                    f=true;//On notifie que la ligne existe
                    for(int j=0;j<this.lignes.get(i).getLexemes().size();j++){
                        try{
                            Integer.parseInt(this.lignes.get(i).getLexemes().get(j)); //Pareil que ligne E pour la liste F
                        }catch(Exception ex){
                            System.out.println(ANSI_RED_BACKGROUND+"Erreur : F ne contient pas des nombres -> " + this.lignes.get(i).getLexemes().get(j)+", ligne "+cpt+ANSI_RED_BACKGROUND);
                            return false;
                        }
                    }
                    break;
                case 'V' :
                    ligneV = this.lignes.get(i);//On récupère la ligne pour avoir la liste des caractères acceptés
                    if(!ligneV.getLexemes().contains(meta)) {
                        ligneV.getLexemes().add(meta);//On ajoute le meta caractère au vocabulaire
                    }
                    v=true;//On notifie que la ligne existe
                    break;
                case 'O' :
                    ligneO = this.lignes.get(i); //On récupère la ligne pour avoir la liste des caractères acceptés
                    break;
                case 'T' :   //Trop ou pas assez de paramètres
                    if(this.lignes.get(i).getLexemes().size()<3 || this.lignes.get(i).getLexemes().size()>4){
                        System.out.println(ANSI_RED_BACKGROUND+"Erreur : La transition comporte 3 ou 4 elements, ligne "+cpt+ANSI_RED_BACKGROUND);
                        return false;
                    } //Caractère d'entrée inconnu
                    if(!ligneV.getLexemes().contains(this.lignes.get(i).getLexemes().get(1).replace("'", ""))){
                        System.out.println(ANSI_RED_BACKGROUND+"Erreur : Mot inconnu V, ligne "+cpt+ANSI_RED_BACKGROUND);
                        return false;
                    }
                    if(this.lignes.get(i).getLexemes().size()>3){
                        if(ligneO==null){
                            ligneO = new Ligne();//Si pas de ligne 0, par defaut elle contient le métacaractère
                            ligneO.getLexemes().add(meta);
                        }
                        //caractère de sortie inconnu
                        if(!ligneO.getLexemes().contains(this.lignes.get(i).getLexemes().get(3).replace("'", "")) && !this.lignes.get(i).getLexemes().get(3).replace("'", "").equals(meta)){
                            System.out.println(ANSI_RED_BACKGROUND+"Erreur : Mot inconnu O, ligne "+cpt+ANSI_RED_BACKGROUND);
                            return false;
                        }
                    }
                    //Etat dans la transition > au nombre d'état total de l'automate
                    if(Integer.parseInt(this.lignes.get(i).getLexemes().get(0))>=nbEtats || Integer.parseInt(this.lignes.get(i).getLexemes().get(2))>=nbEtats){
                        System.out.println(ANSI_RED_BACKGROUND+"Erreur : Etat superieur au nombre d'états max dans les transitions, ligne "+cpt+ANSI_RED_BACKGROUND);
                        return false;
                    }
                    try{ //Les etats ne sont pas des nombres
                        Integer.parseInt(this.lignes.get(i).getLexemes().get(0));
                        Integer.parseInt(this.lignes.get(i).getLexemes().get(2));
                    }catch(Exception ex){
                        System.out.println(ANSI_RED_BACKGROUND+"Erreur : Les etats doivent être numeriques, ligne " +cpt+ANSI_RED_BACKGROUND);
                        return false;
                    }
                    break;
            }
            cpt++;
        }
        if(!v || !e || !f){ //Si au moins une des 3 lignes obligatoire n'est pas présente
            System.out.println(ANSI_RED_BACKGROUND+"Erreur : LIGNE OBLIGATOIRE NON INITIALISEE"+ANSI_RED_BACKGROUND);
            return false;
        }
        return fichierOk;
    }
    
    
    
    /**
     * Traitement d'une entrée dans l'automate
     * Savoir si une entrée est acceptante ou non
     * @param entree L'entrée 
     * @author Axel Cousseau
     */
    public void traitementEntree(String entree){
        System.out.println("Traitement des phrases lues :");
        System.out.println("");
        Etat init = this.automate.getInit();
        String[] motsEntree = entree.split("\n"); //Chaque mot dans la saisie est séparé par un saut de ligne
        Etat courant;
        for(int i=0;i<motsEntree.length;i++){
            boolean entreeTraitee = false;//true une fois que le premier carac de l'entrée est passé
            courant = init; //Etat courant, au début c'est l'état initial
            String phraseSortie="";//la phrase générée par chaque entrée
            boolean sortie = false;
            if(!motsEntree[i].equals("###")){ //### = indique fin de saisie
                String mot = motsEntree[i];
                if(!this.automate.getVoc().contains(mot.charAt(0))){
                        System.out.println(ANSI_RED_BACKGROUND+"Erreur : Mot d'entrée non inclu dans le vocabulaire"+ANSI_RED_BACKGROUND);
                        System.out.println("---------------------------");    
                }
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
                    if(!this.automate.getVoc().contains(mot.charAt(j))){
                        System.out.println(ANSI_RED_BACKGROUND+"Erreur : Mot d'entrée non inclu dans le vocabulaire"+ANSI_RED_BACKGROUND);
                        System.out.println("---------------------------");
                    }
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
    
    /**
     * Export un fichier .descr en .dot
     * @author Axel Cousseau
     */
    public void descrToDot(){
        String fileDot = descr.getName()+".dot"; //nom du fichier créé
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileDot));
            String init = Integer.toString(this.automate.getInit().getNumero());
            writer.write("digraph G {\n");//première ligne
            writer.write("\t\"\" [shape=none]\n"); //Pour les états initiaux, la flèche simple en entrée
            for(Etat e : this.automate.getFinaux()){
                    writer.write('\t'+Integer.toString(e.getNumero()) + " [shape=doublecircle]\n");//Double cercle pour chaque état final
            }
            for(Etat e : this.automate.getInitiaux()){
                writer.write("\t\"\" ->"+Integer.toString(e.getNumero())+"\n"); //flèche simple vers chaque état initial
            }
            for(Transition t : this.automate.getTransitions()){
                String entree = Integer.toString(t.getEtatEntree().getNumero());
                String sortie = Integer.toString(t.getEtatSortie().getNumero());
                String motEntree =String.valueOf(t.getEntree());
                String motSortie = String.valueOf(t.getSortie());
                writer.write('\t'+entree + " -> " + sortie +"[label=\""+motEntree+""+"/"+motSortie+"\"];\n");
                //Chaque ligne = entrée -> sortie [label="motEntrée/motSortie"] , notation .dot
            }
            writer.write("}");
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(AnalyseurLexical.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * toString pour chaque ligne
     * @author Axel Cousseau
     */
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

    public boolean isAutomateFalse() {
        return automateFalse;
    }
    
    
    
}