/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analyseurlexical;

import automate.Automate;
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
import java.util.Scanner;
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
        
        //AnalyseurLexical al = new AnalyseurLexical();
        //al.afficheDescrLignes();
        //al.descrToDot();
        /*al.traitementEntree("000\n"
                + "101\n"
                + "110\n"
                + "###");*/
        
        
        /*
            PARTIE NON-DETERMINISTE
        */
        //AEFND afnd = new AEFND();
        
        /*
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
        
        System.out.println(" -- Vocabulaire -- ");
        for (char c : al.getAutomate().getVoc()) {
            System.out.print(c + " ");
        }
        */
        //System.out.println("    --  DETERMINISATION --  ");
        //Automate NDtoD = afnd.dertiminiser(al.getAutomate());
        
        //afnd.descrToDot(NDtoD, "NDD01");
        
        boolean menu1=true,menu2=true,menu3=true;
        
        while(menu1){
            System.out.println("----------SELECTION----------");
            System.out.println("1 - Analyse automate non-deterministe");
            System.out.println("2 - Analyse automate deterministe");
            System.out.println("3 - Quitter ");
            Scanner sc1 = new Scanner(System.in);
            String s = sc1.nextLine();
            switch(s){
                case "1" :
                    System.out.println("Automate non-determinisé :");
                    AnalyseurLexical al1 = new AnalyseurLexical();
                    al1.afficheDescrLignes();
                    AEFND nonDet = new AEFND();
                    nonDet.genereDescr(nonDet.dertiminiser(al1.getAutomate()), al1.getDescr().getName());
                    System.out.println("Automate determinisé : ");
                    AnalyseurLexical aldet = new AnalyseurLexical(new File(al1.getDescr().getName()+".descr"));
                    aldet.afficheDescrLignes();
                    if(!al1.isAutomateFalse()){
                        while(menu2){
                            System.out.println("-----Analyse Automate non-Deterministe-----");
                            System.out.println("1 - Analyse d'entrée ");
                            System.out.println("2 - Exporter l'automate determinisé en .dot ");
                            System.out.println("3 - Retour ");
                            Scanner sc3 = new Scanner(System.in);
                            String s3 = sc3.nextLine();
                            switch(s3){
                                case "1" :
                                    boolean saisie=true;
                                    System.out.println("Entrez les mots à analyser, séparés par un retour à la ligne");
                                    System.out.println("Pour signifier la fin de la saisie, entrez ###");
                                    Scanner scEntree2 = new Scanner(System.in);
                                    String strEntree="";
                                    while(saisie){
                                        System.out.print(">");
                                        String str2 = scEntree2.nextLine();
                                        strEntree += str2+'\n';
                                        if(str2.equals("###")){
                                            saisie=false;
                                        }
                                    }
                                    aldet.traitementEntree(strEntree);
                                    break;
                                case "2" :
                                    System.out.println("Export du fichier .descr en fichier .dot");
                                    aldet.descrToDot();
                                    break;
                                case "3" :
                                    menu2=false;
                                    break;
                                default :
                                    System.out.println("Invalide, tapez 1, 2 ou 3 pour signifier votre choix");
                                    break;
                            }
                        }
                    }
                    break;
                case "2" :
                    AnalyseurLexical al2 = new AnalyseurLexical();
                    al2.afficheDescrLignes();
                    if(!al2.isAutomateFalse()){
                        while(menu3){
                            System.out.println("-----Analyse Automate Deterministe-----");
                            System.out.println("1 - Analyse d'entrée ");
                            System.out.println("2 - Exporter l'automate en .dot ");
                            System.out.println("3 - Retour ");
                            Scanner sc2 = new Scanner(System.in);
                            String s2 = sc2.nextLine();
                            switch(s2){
                                case "1" :
                                    boolean saisie=true;
                                    System.out.println("Entrez les mots à analyser, séparés par un retour à la ligne");
                                    System.out.println("Pour signifier la fin de la saisie, entrez ###");
                                    Scanner scEntree2 = new Scanner(System.in);
                                    String strEntree="";
                                    while(saisie){
                                        System.out.print(">");
                                        String str2 = scEntree2.nextLine();
                                        strEntree += str2+'\n';
                                        if(str2.equals("###")){
                                            saisie=false;
                                        }
                                    }
                                    al2.traitementEntree(strEntree);
                                    break;
                                case "2" :
                                    System.out.println("Export du fichier .descr en fichier .dot");
                                    al2.descrToDot();
                                    break;
                                case "3" : 
                                    menu3=false;
                                    break;
                                default :
                                    System.out.println("Invalide, tapez 1, 2 ou 3 pour signifier votre choix");
                                    break;
                            }
                        }
                    }
                    break;
                case "3" :
                    menu1=false;
                    break;
                default :
                    System.out.println("Invalide, tapez 1, 2 ou 3 pour signifier votre choix");
                    break;
            }
        }
        
    }
    
}
