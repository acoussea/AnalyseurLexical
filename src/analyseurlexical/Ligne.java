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
    
    
    
    
}
