/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyseurlexical;

import automate.Etat;
import java.util.ArrayList;

/**
 * Stockage d'une ligne lue depuis un fichier .descr
 * @author Axel Cousseau
 */
public class Ligne {
    private String contenu;
    private char type; // C, M, I, F, E, etc...
    private ArrayList<String> lexemes; // Lexeme = les mots indépendants de la ligne

    /**
     * Constructeur
     * @param contenu le contenu string de la ligne lue
     * @author Axel Cousseau
     */
    public Ligne(String contenu) {
        this.contenu = contenu;
        this.lexemes = new ArrayList<>();
        this.initLexemes(); // On initialise la liste des lexemes en analysant la ligne lue
    }

    /**
     * Constructeur par defaut
     * @author Axel Cousseau
     */
    public Ligne() {
        this.lexemes = new ArrayList<>();
    }
    
    
    /**
     * Initialisation des lexemes d'une ligne lue
     * Récupère le contenu en fonction du type de ligne lue
     * @author Axel Cousseau
     */
    public void initLexemes(){
        String[] mots = this.contenu.split(" "); // chaque mot/lexeme est séparé par un espace
        this.type = mots[0].charAt(0); //Le premier mot est le type de la ligne
        switch(this.type){ //en fonction du type :
            case 'C' :
                String com="";
                for(int i=1;i<mots.length;i++){
                    com += mots[i];
                } //Toute la ligne est récupérée dans un seul string, c'est un simple commentaire
                com.replace("'", ""); //son contenu n'est pas important
                this.lexemes.add(com);
                break;
            case 'M' :
                for(int i=1;i<mots.length;i++){
                    for(int j=0;j<mots[i].length();j++){
                    if(mots[i].charAt(j)!='\''){ //on enlève les ' s'il y en a
                        this.lexemes.add(String.valueOf(mots[i].charAt(j)));
                    }
                    }
                }
                break;
            case 'E' :
            case 'I' :
            case 'F' : 
                for(int i=1;i<mots.length;i++){
                    this.lexemes.add(mots[i]); //on récupère toute la ligne
                }
                break;
            case 'V' :
            case 'O' :
                for(int i=0;i<mots[1].length();i++){
                    if(mots[1].charAt(i)!='"'){ //On enlève les guillemets et on récupère chaque caractère 1 par 1
                        this.lexemes.add(String.valueOf(mots[1].charAt(i)));
                    }
                }
                break;
            case 'T' :
                for(int i=1;i<mots.length;i++){
                    this.lexemes.add(mots[i]);
                }
                break;
        }
    }

    /**
     * Getter contenu
     * @return le contenu de la ligne
     * @author Axel Cousseau
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * Setter contenu
     * @param contenu le contenu de la ligne
     * @author Axel Cousseau
     */
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    /**
     * Getter type
     * @return le type de la ligne
     * @author Axel Cousseau
     */
    public char getType() {
        return type;
    }

    /**
     * Setter type
     * @param type le type de la ligne
     * @author Axel Cousseau
     */
    public void setType(char type) {
        this.type = type;
    }

    /**
     * Getter lexemes
     * @return la liste des lexemes de la ligne
     * @author Axel Cousseau
     */
    public ArrayList<String> getLexemes() {
        return lexemes;
    }

    /**
     * Setter lexeme
     * @param lexemes la liste des lexemes d'une ligne
     * @author Axel Cousseau
     */
    public void setLexemes(ArrayList<String> lexemes) {
        this.lexemes = lexemes;
    }
    
    /**
     * toString d'une liste suivant l'affichage du .descr
     * @return représentation String d'une liste
     * @author Axel Cousseau
     */
    @Override
    public String toString() {
        String s = "";
        switch(this.type){
            case 'C' : s = "Ligne commentaire : " + this.lexemes.get(0);
                break;
            case 'M' : s = "Ligne méta-caractère : " + this.lexemes.get(0);
                break;
            case 'E' : 
                String etats ="";
                for(String str : this.lexemes){
                    etats += str +" ";
                }
                s = "Etats : " + etats;
                break;
            case 'F' : 
                String etatsF ="";
                for(String str : this.lexemes){
                    etatsF += str +" ";
                }
                s = "Etats finaux : " + etatsF;
                break;
            case 'I' :
                String etatsI ="";
                for(String str : this.lexemes){
                    etatsI += str +" ";
                }
                s = "Etats Initiaux : " + etatsI;
                break;
            case 'V' :
                String vocE = "\"";
                for(String str : this.lexemes){
                    vocE += str;
                }
                vocE += "\"";
                s = "Vocabulaire d'entrée : " + vocE;
                break;
            case 'O' :
                String vocS = "\"";
                for(String str : this.lexemes){
                    vocS += str;
                }
                vocS += "\"";
                s = "Vocabulaire de sortie : " + vocS;
                break;
            case 'T' :
                String trans="";
                if(this.lexemes.size()>3){
                    trans = lexemes.get(0) + " " + lexemes.get(1) + " " + lexemes.get(2) + " " + lexemes.get(3) +"";
                }else{
                    trans = lexemes.get(0) + " " + lexemes.get(1) + " " + lexemes.get(2);
                }
                s = "Transition : " + trans;
                break;
        }
        return s;
    }
    
    
    
}
