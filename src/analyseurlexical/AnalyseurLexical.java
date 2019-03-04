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
    }
    
    public void afficheDescrLignes(){
        for(Ligne l : this.getLignes()){
            System.out.println(l.toString());
        }
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
    
}
