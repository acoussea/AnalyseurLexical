/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyseurlexical;

import automate.Etat;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author couss
 */
public class Moteur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        AnalyseurLexical al = new AnalyseurLexical();
        al.afficheDescrLignes();
        //al.descrToDot();
        al.traitementEntree("000\n"
                + "101\n"
                + "110\n"
                + "###");
        
        AEFND afnd = new AEFND();
        List<Etat> T = new ArrayList<>();
        T.add(new Etat(0));
        ArrayList<Etat> res = afnd.lambda_fermeture(T, al.getAutomate());
        
        System.out.println("Lambda fermeture : ");
        for(Etat e : res) {
            System.out.print(e.getNumero() + " ");
        }
        System.out.println();
        
        System.out.println("Transiter : ");
        ArrayList<Etat> res2 = afnd.transiter(res, 'a', al.getAutomate());
        for(Etat e : res2) {
            System.out.print(e.getNumero() + " ");
        }
        System.out.println();
    }
    
}
