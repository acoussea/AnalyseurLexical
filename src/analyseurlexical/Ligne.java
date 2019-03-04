/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyseurlexical;

import automate.Etat;
import java.util.ArrayList;

/**
 *
 * @author couss
 */
public class Ligne {
    private String contenu;
    private char type;
    private ArrayList<String> lexemes;

    public Ligne(String contenu) {
        this.contenu = contenu;
        this.lexemes = new ArrayList<>();
        this.initLexemes();
    }
    
    public void initLexemes(){
        String[] mots = this.contenu.split(" ");
        this.type = mots[0].charAt(0);
        switch(this.type){
            case 'C' :
                String com="";
                for(int i=1;i<mots.length;i++){
                    com += mots[i];
                }
                com.replace("'", "");
                this.lexemes.add(com);
                break;
            case 'M' :
                for(int i=0;i<mots[1].length();i++){
                    if(mots[1].charAt(i)!='\''){
                        this.lexemes.add(String.valueOf(mots[1].charAt(i)));
                    }
                }
                break;
            case 'E' :
            case 'I' :
            case 'F' : 
                for(int i=1;i<mots.length;i++){
                    this.lexemes.add(mots[i]);
                }
                break;
            case 'V' :
            case 'O' :
                for(int i=0;i<mots[1].length();i++){
                    if(mots[1].charAt(i)!='"'){
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

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public ArrayList<String> getLexemes() {
        return lexemes;
    }

    public void setLexemes(ArrayList<String> lexemes) {
        this.lexemes = lexemes;
    }
    
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
