package com.company;

import java.awt.*;
import java.util.*;

/**
 * Created by Gaby on 24/02/2017.
 */
public class Jeu {

        private ArrayList<Joueur> listJoueur;
        private ArrayList<Humain> listHumain; // liste de joueurs physique
        private ArrayList<IA> listIA; // liste de joueurs IA
        private int nbTour=1;
        private int nbManches=1;
        private ArrayList <Carte> listCarte; // liste des cartes du joueur physique
        private ArrayList <Carte> listCarte2; // liste des cartes du joueur IA

        // on initialise le jeu ou on donne les variable

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
        Humain humain=null;
        IA ia=null;

        // on va renseigner le nom du joueur physique
        for (int i =1; i<2; i++){
            Scanner sc= new Scanner(System.in); // scanner qui permet à l'utilisateur de renter le nom qu'il veut
            System.out.println("Veuillez renseigner le nom du joueur ");
            nomJoueur = sc.nextLine();
            j = new Joueur(nomJoueur,numJoueur,pointMana);
            humain= new Humain(nomJoueur,numJoueur,pointMana); // création de l'objet humain qui sera un joueur physique
            ia= new IA(nomJoueur,numJoueur,pointMana);  // création de l'objet humain qui sera une IA
            listJoueur.add(j); // on ajoute un joueur à la liste listJoueur
            listHumain.add(humain); // on ajoute un objet humain dans la liste listHumain
            listIA.add(ia);// on ajoute un objet IA dans la liste listIA
            numJoueur+=1;
        }


        System.out.println("Liste joueur Humain "+listHumain+" ");
        Plateau p= new Plateau(); // on initialise un objet plateau (le plateau de jeu)

        init(); // appel de la méthode init
        superPaquet(); // appel de la méthode superPaquet

        while (p.getPlaceJ2()<p.getTailleTab()&&(p.getPlaceJ1()>0)) { // condition d'arret du jeu ou du programme
            attaquer(p,humain,ia); // appel de la méthode attaquer
        }


    }

    // grace à la méthode init nous allons créer des jeux de cartes, un pour le joueur humain et l'autre pour l'IA
    // Les deux joueurs auront des paquets différent
    public void init() {
        for (int i =1; i<14;i++){               // boucle qui permet d'aller jusqu'a 14 qui est le nombre de cartes total par joueur
            Carte c =new Carte(i," "); // création du l'objet carte qui va prendre un numéro différent en fonction de la variable i qui se trouve dans la boucle for
            this.listCarte.add(c);              // on ajoute les objets cartes dans la liste qui va correspondre à la liste des cartes du joueur
            melanger();                         // on fait appel à la la méthode mélanger
        }
                                            // Même instruction que la première boucle, mais le lieu de stockage sera différent
        for (int j =1; j<14;j++){
            Carte c =new Carte(j," ");
            this.listCarte2.add(c);         // on ajoute les objets cartes dans la liste qui va correspondre à la liste des cartes de l'IA
            melanger();                     // on fait appel à la la méthode mélanger
        }
    }

    // méthode qui va permetre au joueus physique de donner la mise qui va donc correspondre à la puissance de son attaque

    public void choixPuissanceHumain(Plateau plateau,  Humain humain){
        System.out.println(" le nombre de cases est de "+plateau.getTailleTab());
        for (int i=0; i<listHumain.size();i++) { // on parcours la liste de joueurs humain
            superPaquet(); // on affiche le paquet avec les cartes disponible pour le joueur
            // à partir d'ici les conditions vont faire en sorte que le joueur ne puisse pas tricher
            if (listHumain.get(0).getPointMana()==0){   // Condition pour vérifier que le joueur à toujours des points d'action (ici de points de mana) de disponible
                FinManche(plateau); //si la condition est vrai alors on fait appel à la méthode Fin de manche
                System.out.println(" Joueur "+listHumain.get(0).getNomJoueur()+" a plus de mana, fin du tour"); // on affiche le résultat
            }
            // on informe que au joueur qu'il s'agit de son tour
            System.out.println("Joueur " + listHumain.get(0).getNomJoueur() + " à vous !");
            Scanner sc = new Scanner(System.in);  // Nouveau scanner qui permet au joueur de donner la puissance voulut à son sort
            System.out.println("Saisissez un entier : ");
            int puissance = sc.nextInt(); // entrée du scanner qui sera donc un int

            if ((listHumain.get(0).getPointMana()<=0)){ // Condition si le joueur n'a plus de points de mana
                FinManche(plateau); // on appel la méthode FinManche
                System.out.println(" Joueur HUMAIN "+listHumain.get(0).getNomJoueur()+" a perdu la manche"); // on affiche le résultat à l'écran
                System.out.println("Nouveau tour"); // on affiche le résultat à l'écran
                break; // on arréte la boucle si vérifié
            } else if (puissance > listHumain.get(0).getPointMana()) { // condition si la puissance est supérieur aux points de mana (ou d'action) disponible
                System.out.println("Pas possible recommencez");  // on affiche le résultat à l'écran
                System.out.println("Points de mana Joueur "+listHumain.get(0).getPointMana());  // on affiche le résultat à l'écran (point de mana disponible)
                choixPuissanceHumain(plateau,humain); // on rappel la méthode choixPuissanceHumain pour que le joueur puisse recommencer la mise
                break;
            }
            else { // si aucunes des conditions n'est validées et DONC que le jeu se déroule normalement
                listHumain.get(0).setPuissanceCoup(puissance); // on modifie la variable du joueur humain PuissanceCoup est qui va prendre la valeur d'entrée du scanner
                listHumain.get(0).setPointMana(listHumain.get(0).getPointMana() - puissance); //  on modifie la variable des points de mana (ou d'action) l'opération est = > Point de Mana - puissance entrée par le joueur par le scanner
                listHumain.get(0).setTotalPuissanceCoupHumain(listHumain.get(0).getPuissanceCoup()+listHumain.get(0).getTotalPuissanceCoupHumain()); // total de tout les coups du joueur physique, cela va nous permettre de faire "apprendre l'IA"
                choixCarte(plateau,humain); // on appel la méthode choixCarte qui va permettre au joueur de choisir une carte
                System.out.println(" Puissance du coup " + puissance); // on affiche la puissance du coup à l'écran
                System.out.println(" Puissance TOTAL des coups " + listHumain.get(0).getTotalPuissanceCoupHumain()); // on affiche la puissanceTotal des coups à l'écran
                System.out.println("il reste " + listHumain.get(0).getPointMana() + " points de Mana"); // on affiche points de mana qui reste à l'écran
                System.out.println("");
                nbTour+=1;

            }

        }


    }

    // Méthode de création du comportement de l'IA qui aura un comportement maximisant pour l'IA et minimisant pour le joueur "Humain"

    public void choixPuissanceIA(Plateau plateau, IA ia){
        int puissanceH = listHumain.get(0).getPuissanceCoup(); // on part de coup du joueurs "humain"
        int puissanceMiniIA =1; // la mise minimum de l'IA
        int fonctionIA = puissanceMiniIA + (int) (Math.random()*(puissanceH - puissanceMiniIA) +5);
        int probaCoupHumain = listHumain.get(0).getTotalPuissanceCoupHumain()/nbTour;

        // on va donner une chance au joueur humain, bien entendu l'IA cherche toujour à maximiser ces coups
        if (nbTour==1) {
            if (listHumain.get(0).getPointMana() <= 40) {
                fonctionIA = puissanceMiniIA + (int) (Math.random() * (puissanceH - puissanceMiniIA) + 4);

            } else if (listHumain.get(0).getPointMana() <= 30) {
                fonctionIA = puissanceMiniIA + (int) (Math.random() * (puissanceH - puissanceMiniIA) + 3);

            } else if (listHumain.get(0).getPointMana() <= 20) {
                fonctionIA = puissanceMiniIA + (int) (Math.random() * (puissanceH - puissanceMiniIA) + 2);

            } else if (listHumain.get(0).getPointMana() <= 10) {
                fonctionIA = puissanceMiniIA + (int) (Math.random() * (puissanceH - puissanceMiniIA) + 1);
            }
        }else if (nbTour>2){
            fonctionIA = probaCoupHumain + (int) (Math.random() * (puissanceH - probaCoupHumain) + 3);
            System.out.println("probaCoupHumain " +fonctionIA);
            System.out.println("fonctionIA = probaCoupHumain + (int) (Math.random() * (puissanceH - probaCoupHumain) + 3);");
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
                choixPuissanceIA(plateau,ia);
                break;
            }
            else {
                listIA.get(0).setPuissanceCoup(fonctionIA);
                listIA.get(0).setPointMana(listIA.get(0).getPointMana() - fonctionIA);
                choixCarteIA(plateau, ia);
                System.out.println(" Puissance du coup " + fonctionIA);
                System.out.println(" la puissance du coup est l'IA "+listIA.get(0).getPuissanceCoup());
                System.out.println("il reste " + listIA.get(0).getPointMana() + " points de Mana à l'IA");
                System.out.println("");


            }
        }
    }


    public void attaquer(Plateau plateau,  Humain humain, IA ia) {
        this.choixPuissanceHumain(plateau, humain);
        this.choixPuissanceIA(plateau, ia);
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

            if (plateau.getPlaceJ1() == 0) {     // Condition si J1 est à 0
                System.out.println("Fin du game");

            }

            //Attaque IA pas assez forte
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
            if (plateau.getPlaceJ2() == plateau.getTailleTab()) {     // Condition si J2 est au bout du plateau droit
                System.out.println("Fin du game");

            }

        } else {

            System.out.println(" Même puissance pour les deux joueurs");
        }
        Enumeration enumeration2 = plateau.plateauBase.elements();
        while (enumeration2.hasMoreElements()) {
            System.out.println("Résultat de la fin du tour " + enumeration2.nextElement());

        }
    }


    public void choixCarte(Plateau plateau,  Humain humain){
        Scanner sc = new Scanner(System.in);
        System.out.println("Saisissez une carte entre 0 et 4 : ");
        int choix = sc.nextInt();
        if (choix>4){
            System.out.println("Choix invalide recommencez");
            this.choixCarte(plateau, humain);
        }
        if (listCarte.contains(listCarte.get(choix))) {
            this.pouvoirCarte(plateau, listCarte.get(choix) ,listCarte.get(choix).getNumCarte(),humain);

        }

    }

    public void pouvoirCarte(Plateau plateau, Carte carte,int idCarte, Humain humain) {

        carte.effetCarte(idCarte,humain, plateau,this);
    }


    public void choixCarteIA(Plateau plateau, IA ia){
        Random random = new Random();
        int min = 0;
        int max = 4;
        int choixAleatoire = random.nextInt(max-min +1)+min;
        if (choixAleatoire>4){
            System.out.println("Choix invalide recommencez");
            this.choixCarteIA(plateau, ia);
        }
        if (listCarte2.contains(listCarte2.get(choixAleatoire))) {
            this.pouvoirCarteIA(plateau, listCarte2.get(choixAleatoire) ,listCarte2.get(choixAleatoire).getNumCarte(),ia);
            System.out.println("Carte IA: " +choixAleatoire +"" );

        }

    }

    public void pouvoirCarteIA(Plateau plateau, Carte carte,int idCarte, IA ia) {

        carte.effetCarteIA(idCarte,ia, plateau,this);
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
            listHumain.get(0).setTotalPuissanceCoupHumain(0);
            nbTour=1;
            melanger();
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



