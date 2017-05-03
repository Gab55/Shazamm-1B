package com.company;

import java.awt.*;
import java.util.*;

/**
 * Created by Gaby on 24/02/2017.
 */
public class Jeu {

    private ArrayList<Joueur> listJoueur;
    private ArrayList<Humain> listHumain;
    private ArrayList<IA> listIA;
    private int nbManches=1;
    private ArrayList <Carte> listCarte;
    private ArrayList <Carte> listCarte2;


    public void debutJeu(){
        this.listCarte=new ArrayList<Carte>();
        this.listCarte2=new ArrayList<Carte>();

        this.listJoueur= new ArrayList<Joueur>();
        this.listHumain= new ArrayList<Humain>() ;
        this.listIA= new ArrayList<IA>() ;
        int numJoueur=1;
        int pointMana=50;
        String nomJoueur;
        System.out.println("Bienvenue dans le jeu Shazamm !");
        Joueur j= null;
        for (int i =1; i<2; i++){

            Scanner sc= new Scanner(System.in);
            System.out.println("Veuillez renseigner le nom du joueur ");
            nomJoueur = sc.nextLine();
            j = new Joueur(nomJoueur,numJoueur,pointMana);
            Humain humain= new Humain(nomJoueur,numJoueur,pointMana);
            IA ia= new IA(nomJoueur,numJoueur,pointMana);
            listJoueur.add(j);
            listHumain.add(humain);
            listIA.add(ia);
            numJoueur+=1;
        }
        System.out.println("Liste joueur Humain "+listHumain+" Liste joueur IA "+listIA);
        Plateau p= new Plateau();

        init();
        superPaquet();

        while (p.getPlaceJ2()<p.getTailleTab()&&(p.getPlaceJ1()>0)) {
            attaquer(p,j);
        }


    }

    public void init() {
        for (int i =1; i<6;i++){
            Carte c =new Carte(i," ");
            this.listCarte.add(c);
            melanger();
        }for (int j =1; j<6;j++){
            Carte c =new Carte(j," ");
            this.listCarte2.add(c);
            melanger();
        }
    }


    public void choixPuissanceHumain(Plateau plateau, Joueur joueur){
        System.out.println(" le nombre de cases est de "+plateau.getTailleTab());
        for (int i=0; i<listHumain.size();i++) {
            superPaquet();
            choixCarte(plateau,joueur);
            if (listHumain.get(0).getPointMana()==0){
                FinManche(plateau);
                System.out.println(" Joueur "+listHumain.get(0).getNomJoueur()+" a plus de mana, fin du tour");
            }

            System.out.println("Joueur " + listHumain.get(0).getNomJoueur() + " à vous !");
            Scanner sc = new Scanner(System.in);
            System.out.println("Saisissez un entier : ");
            int puissance = sc.nextInt();

            if ((listHumain.get(0).getPointMana()<=0)){ // Condition si J1 a plus de points de mana
                FinManche(plateau);
                System.out.println(" Joueur HUMAIN "+listHumain.get(0).getNomJoueur()+" a perdu la manche");
                System.out.println("Nouveau tour");
                break;
            } else if (puissance > listHumain.get(0).getPointMana()) {
                System.out.println("Pas possible recommencez");
                System.out.println("Points de mana Joueur "+listHumain.get(0).getPointMana());
                choixPuissanceHumain(plateau,joueur);
                break;
            }
            else {
                listHumain.get(0).setPuissanceCoup(puissance);
                listHumain.get(0).setPointMana(listHumain.get(0).getPointMana() - puissance);
                System.out.println(" Puissance du coup " + puissance);

                System.out.println(" la puissance du coup est "+listHumain.get(0).getPuissanceCoup());
                System.out.println("il reste " + listHumain.get(0).getPointMana() + " points de Mana");
                System.out.println("");

            }

        }


    }

    // Méthode de création du comportement de l'IA qui aura un comportement maximisant pour elle et minimisant pour le joueur "Humain"

    public void choixPuissanceIA(Plateau plateau){
        int puissanceH = listHumain.get(0).getPuissanceCoup(); // on part de coup du joueurs "humain"
        int puissanceMiniIA =1; // la mise minimum de l'IA
        int fonctionIA = puissanceMiniIA + (int) (Math.random()*(puissanceH - puissanceMiniIA) +5);

        // on va donner une chance au joueur humain, bien entendu l'IA cherche toujour à maximiser ces coups
        if (listHumain.get(0).getPointMana()<=40){
            fonctionIA = puissanceMiniIA + (int) (Math.random()*(puissanceH - puissanceMiniIA) +4);

        }else if (listHumain.get(0).getPointMana()<=30){
            fonctionIA = puissanceMiniIA + (int) (Math.random()*(puissanceH - puissanceMiniIA) +3);

        }else if (listHumain.get(0).getPointMana()<=20){
            fonctionIA = puissanceMiniIA + (int) (Math.random()*(puissanceH - puissanceMiniIA) +2);

        }else if (listHumain.get(0).getPointMana()<=10){
            fonctionIA = puissanceMiniIA + (int) (Math.random()*(puissanceH - puissanceMiniIA) +1);
        }
        System.out.println("IA à vous !");
        for (int i=0; i<listIA.size();i++) {
            if (listIA.get(0).getPointMana()==0){
                FinManche(plateau);
                System.out.println(" l'IA "+listIA.get(0).getNomJoueur()+" a plus de mana, fin du tour");
            }

            if ((listIA.get(0).getPointMana()<=0)){ // Condition si IA a plus de points de mana
                FinManche(plateau);
                System.out.println(" Joueur "+listIA.get(i).getNomJoueur()+" a perdu la manche");
                System.out.println("Nouveau tour");
                break;
            } else if (fonctionIA > listIA.get(i).getPointMana()) {
                choixPuissanceIA(plateau);
                break;
            }
            else {
                listIA.get(0).setPuissanceCoup(fonctionIA);
                listIA.get(0).setPointMana(listIA.get(0).getPointMana() - fonctionIA);
                System.out.println(" Puissance du coup " + fonctionIA);
                System.out.println(" la puissance du coup est l'IA "+listIA.get(0).getPuissanceCoup());
                System.out.println("il reste " + listIA.get(0).getPointMana() + " points de Mana à l'IA");
                System.out.println("");

            }
        }
    }


    public void attaquer(Plateau plateau, Joueur joueur) {

        this.choixPuissanceHumain(plateau, joueur);
        this.choixPuissanceIA(plateau);
        if (listHumain.get(0).getPuissanceCoup() < listIA.get(0).getPuissanceCoup()) {
            System.out.println("Pas assez fort HUMAIN le " + listIA.get(0).getNomJoueur() + " gagne le tour (IA) J2 ");
            plateau.setPlaceMur(plateau.getPlaceMur() - 1);
            plateau.plateauBase.put("m", plateau.getPlaceMur());

            if (plateau.getPlaceJ1() >= plateau.getPlaceMur()) {
                System.out.println("Bien joué IA");
                System.out.println("FIN DE LA MANCHE !!!! ");
                System.out.println(listIA.get(0).getNomJoueur() + " gagne la manche (IA) J2 ");
                System.out.println("Nombre de manches " + nbManches);
                System.out.println("Taille tab " + plateau.getTailleTab());
                FinManche(plateau);
                nbManches+=1;
            }

            if (plateau.getPlaceJ1() == 0) {          // Condition si J1 est à 0
                System.out.println("Fin du game");
                //Attaque IA pas assez forte
            }

        } else if (listHumain.get(0).getPuissanceCoup() > listIA.get(0).getPuissanceCoup()) {
            System.out.println("Pas assez fort IA le " + listHumain.get(0).getNomJoueur() + " gagne le tour (HUMAIN) J1 ");
            plateau.setPlaceMur(plateau.getPlaceMur() + 1);
            plateau.plateauBase.put("m", plateau.getPlaceMur());
            if (plateau.getPlaceMur() >= plateau.getPlaceJ2()) {
                System.out.println(" Bien joué Humain ");
                System.out.println("FIN DE LA MANCHE !!!! ");
                System.out.println(listHumain.get(0).getNomJoueur() + " gagne la manche (HUMAIN) J1");
                System.out.println("Taille tab " + plateau.getTailleTab());
                FinManche(plateau);
                nbManches+=1;
            }
            if (plateau.getPlaceJ2() == plateau.getTailleTab()) {          // Condition si J2 est au bout du plateau droit
                System.out.println("Fin du game");

            }

        } else { //if (listJoueur.get(0).getPuissanceCoup() == listJoueur.get(1).getPuissanceCoup())

            System.out.println(" Même puissance pour les deux joueurs");
        }
        Enumeration enumeration2 = plateau.plateauBase.elements();
        while (enumeration2.hasMoreElements()) {
            System.out.println("Résultat de la fin du tour " + enumeration2.nextElement());

        }
    }


    public void choixCarte(Plateau plateau, Joueur joueur){
        Scanner sc = new Scanner(System.in);
        System.out.println("Saisissez une carte entre 1 et 5 : ");
        int choix = sc.nextInt();
        if (listCarte.contains(listCarte.get(choix))) {
            this.pouvoirCarte(plateau, listCarte.get(choix) ,listCarte.get(choix).getNumCarte(),joueur);
        }

    }

    public void pouvoirCarte(Plateau plateau, Carte carte,int idCarte, Joueur joueur) {

        carte.effetCarte(idCarte,joueur, plateau,this);
    }





    public void superPaquet(){

        for (int i = 0; i < 1; i++) {
            System.out.print(" Cartes Humain [" + i + "]=" + listCarte + " ");
        }

        for (int i = 1; i < 1; i++) {
            System.out.print(" Cartes IA [" + i + "]=" + listCarte2 + " ");
        }

    }


    public void melanger() {
        for (int i = 0; i < 50; i++) {
            Collections.shuffle(listCarte);
        }for (int i = 0; i < 60; i++) {
            Collections.shuffle(listCarte2);
        }
    }

    public void couleurJoueur(){
        Random ra = new Random();
        Boolean coulVert= false;
        Boolean coulRouge= false;
        int fonction = ra.nextInt(2);
        if (fonction == 1) {
            coulRouge=true;
            System.out.println("Sorcier " + listJoueur.get(0).getNomJoueur() + " est le sorcier rouge");
            if (coulRouge==true){
                System.out.println("Sorcier " + listJoueur.get(1).getNomJoueur() + " est le sorcier vert");

            }
        } else {
            coulVert=true;
            System.out.println("Sorcier " + listJoueur.get(0).getNomJoueur() + " est le sorcier vert");
            System.out.println("Sorcier " + listJoueur.get(1).getNomJoueur() + " est le sorcier rouge");
        }
    }


    public void FinManche(Plateau plateau){
        listHumain.get(0).setPointMana(50);
        System.out.println("Joueur "+listHumain.get(0).getNomJoueur()+" a "+listHumain.get(0).getPointMana()+" points de mana");
        listIA.get(0).setPointMana(50);
        System.out.println("Joueur "+listIA.get(0).getNomJoueur()+" a "+listIA.get(0).getPointMana()+" points de mana");
        int a=((plateau.getTailleTab()+1)/2)-plateau.getPlaceMur();
        System.out.println("a:   "+a);
        if (nbManches>1) {
            plateau.setTailleTab(plateau.getTailleTab() - 2);
            System.out.println("avant :     " + plateau.getPlaceMur());
            plateau.setPlaceMur((plateau.getTailleTab() / 2 + 1) - a);
            System.out.println("apres :     " + plateau.getPlaceMur());
            plateau.setPlaceJ1(plateau.getPlaceMur() - 3);
            plateau.setPlaceJ2(plateau.getPlaceMur() + 3);
            plateau.plateauBase.put("J1", plateau.getPlaceJ1());
            plateau.plateauBase.put("m", plateau.getPlaceMur());
            plateau.plateauBase.put("J2", plateau.getPlaceJ2());
//            plateau.plateauBase.put("J1", plateau.getPlaceJ1()+3);
//            plateau.plateauBase.put("m", plateau.getPlaceMur());
//            plateau.plateauBase.put("J2", plateau.getPlaceJ2()+3);
//            nbManches+=1;
        }

    }


    public ArrayList<Joueur> getListJoueur() {
        return listJoueur;
    }

    public void setListJoueur(ArrayList<Joueur> listJoueur) {
        this.listJoueur = listJoueur;
    }

    public ArrayList<Humain> getListHumain() {
        return listHumain;
    }

    public void setListHumain(ArrayList<Humain> listHumain) {
        this.listHumain = listHumain;
    }

    public ArrayList<IA> getListIA() {
        return listIA;
    }

    public void setListIA(ArrayList<IA> listIA) {
        this.listIA = listIA;
    }

    public ArrayList<Carte> getListCarte() {
        return listCarte;
    }

    public void setListCarte(ArrayList<Carte> listCarte) {
        this.listCarte = listCarte;
    }

    public ArrayList<Carte> getListCarte2() {
        return listCarte2;
    }

    public void setListCarte2(ArrayList<Carte> listCarte2) {
        this.listCarte2 = listCarte2;
    }
}



