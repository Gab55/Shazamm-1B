package com.company;

import java.awt.*;
import java.util.*;



// CLASSE OU TOUTES LES ACTIONS VONT SE FAIRE

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
        //Joueur j= null;
        Humain humain=null;
        IA ia=null;
        String host = "jdbc:mysql://localhost:3306/shazamm";
        String username = "root";
        String password = "";
        BDD bdd = new BDD(host, username, password);

        // on va renseigner le nom du joueur physique
        for (int i =1; i<2; i++){
            Scanner sc= new Scanner(System.in); // scanner qui permet à l'utilisateur de renter le nom qu'il veut
            System.out.println("Veuillez renseigner le nom du joueur ");
            nomJoueur = sc.nextLine();
            //j = new Joueur(nomJoueur,numJoueur,pointMana);
            humain= new Humain(nomJoueur,numJoueur,pointMana); // création de l'objet humain qui sera un joueur physique
            ia= new IA(nomJoueur,numJoueur,pointMana);  // création de l'objet humain qui sera une IA
          //  listJoueur.add(j); // on ajoute un joueur à la liste listJoueur
            listHumain.add(humain); // on ajoute un objet humain dans la liste listHumain
            listIA.add(ia);// on ajoute un objet IA dans la liste listIA
            numJoueur+=1;
        }

        infoBDD(bdd);
        System.out.println("Liste joueur Humain "+listHumain+" ");
        Plateau p= new Plateau(); // on initialise un objet plateau (le plateau de jeu)
        init(); // appel de la méthode init
        superPaquet(); // appel de la méthode superPaquet

        while (p.getPlaceJ2()<p.getTailleTab()&&(p.getPlaceJ1()>0)) { // condition d'arret du jeu ou du programme
            attaquer(p,humain,ia); // appel de la méthode attaquer
        }


    }




public void infoBDD(BDD bdd) {
    ArrayList<String> l = bdd.getTuples("SELECT nomHumain FROM shazamm");
    System.out.println("Liste des joueurs deja presents dans la base :");
    System.out.println(l);

    for (int j = 0; j < getListHumain().size(); j++) { //inserer les internautes du fichier .csv dans la base
        //voir cours 13 pour la syntaxe des requêtes SQL d'insertion de tuples : INSERT INTO
        String query = "INSERT INTO shazamm(nomHumain)" + "VALUES ('"+getListHumain().get(0).getNomJoueur()+"')";
        bdd.insertTuples(query);
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
                listHumain.get(0).setPuissanceCoupHumain(puissance); // on modifie la variable du joueur humain PuissanceCoup est qui va prendre la valeur d'entrée du scanner
                listHumain.get(0).setPointMana(listHumain.get(0).getPointMana() - puissance); //  on modifie la variable des points de mana (ou d'action) l'opération est = > Point de Mana - puissance entrée par le joueur par le scanner
                listHumain.get(0).setTotalPuissanceCoupHumain(listHumain.get(0).getPuissanceCoupHumain()+listHumain.get(0).getTotalPuissanceCoupHumain()); // total de tout les coups du joueur physique, cela va nous permettre de faire "apprendre l'IA"
                // pour le calcul d'une meilleurs probablité d'intervale de coups
                choixCarte(plateau,humain); // on appel la méthode choixCarte qui va permettre au joueur de choisir une carte
                System.out.println(" Puissance du coup " + puissance); // on affiche la puissance du coup à l'écran
                System.out.println(" Puissance TOTAL des coups " + listHumain.get(0).getTotalPuissanceCoupHumain()); // on affiche la puissanceTotal des coups à l'écran
                System.out.println("il reste " + listHumain.get(0).getPointMana() + " points de Mana"); // on affiche points de mana qui reste à l'écran
                System.out.println("");
                nbTour+=1; // on ajoute +1 à la variable nbTour cela va nous permettre de faire apprendre l'IA pour le calcul d'une meilleurs probablité d'intervale de coups

            }

        }


    }

    // Méthode de création du comportement de l'IA qui aura un comportement maximisant pour l'IA et minimisant pour le joueur "Humain"
    // Elle se base sur les coups de joueurs
    // dans un premier temps elle va se baser sur un unique coup pour lui permettre de jouer en fonction du coup mais aussi en fonction de la mana qui reste au joueur
    // puis par la suite elle sera capable de caculer un intervalle maximisant pour elle

    public void choixPuissanceIA(Plateau plateau, IA ia){
        int puissanceH = listHumain.get(0).getPuissanceCoupHumain(); // on part de coup du joueurs "humain"
        int puissanceMiniIA =1; // la mise minimum de l'IA
        int fonctionIA = puissanceMiniIA + (int) (Math.random()*(puissanceH - puissanceMiniIA) +5);
        int probaCoupHumain = listHumain.get(0).getTotalPuissanceCoupHumain()/nbTour; // a pour but de calculer un intervale pour permettre potentiellement à l'IA de connaitre un meilleurs intervalle de meilleurs coups

        // on va donner une chance au joueur humain, bien entendu l'IA cherche toujour à maximiser ces coups
        if (nbTour==1) { // si le nombre de tour est égale à 1
            // on ne connait pas le encore plusieurs coups fait par le joueur alors on garde comme intervalle puissanceMiniIA et puissanceH (qui correspond au coup du joueur)
            if (listHumain.get(0).getPointMana() <= 40) {
                fonctionIA = puissanceMiniIA + (int) (Math.random() * (puissanceH - puissanceMiniIA) + 4);

            } else if (listHumain.get(0).getPointMana() <= 30) {
                fonctionIA = puissanceMiniIA + (int) (Math.random() * (puissanceH - puissanceMiniIA) + 3);

            } else if (listHumain.get(0).getPointMana() <= 20) {
                fonctionIA = puissanceMiniIA + (int) (Math.random() * (puissanceH - puissanceMiniIA) + 2);

            } else if (listHumain.get(0).getPointMana() <= 10) {
                fonctionIA = puissanceMiniIA + (int) (Math.random() * (puissanceH - puissanceMiniIA) + 1);
            }
        }else if (nbTour>2){ // dés que l'on connait plusieurs coups du joueur alors on va modifier la méthode de calcule de l'IA pour essayer de deviner un intervalle le plus probable grace à une moyenne qui va donc permettre
            // à l'IA d'apprendre en fonction des coups du joueur
            //nouvelle méthode de calcule d'un intervalle grace à une moyenne des différents coups
            fonctionIA = probaCoupHumain + (int) (Math.random() * (puissanceH - probaCoupHumain) + 3);
            System.out.println("probaCoupHumain " +fonctionIA);
        }

        // ici il s'agit des mêmes conditions de jeu pour l'IA que pour le joueur en effet aucune triche, même pour l'IA

        System.out.println("IA à vous !");
        for (int i=0; i<listIA.size();i++) { // parcours de la liste de l'IA
            // condition si l'IA na plus de points de mana
            if (listIA.get(0).getPointMana()==0){
                FinManche(plateau);
                System.out.println(" l'IA "+listIA.get(0).getNomJoueur()+" a plus de mana, fin du tour");
            }
            //condition si pointMana est <= à 0
            if ((listIA.get(0).getPointMana()<=0)){ // Condition si IA a plus de points de mana
                FinManche(plateau);
                System.out.println(" Joueur "+listIA.get(i).getNomJoueur()+" a perdu la manche");
                System.out.println("Nouveau tour");
                break;
                //condition si l'ia mise plus que le nombre de point mana
            } else if (fonctionIA > listIA.get(i).getPointMana()) {
                choixPuissanceIA(plateau,ia);
                break;
            }
            // si aucunes autres conditions est vérifiées et DONC que le déroulement est bon
            else {

                listIA.get(0).setPuissanceCoupIA(fonctionIA);
                listIA.get(0).setPointMana(listIA.get(0).getPointMana() - fonctionIA);
                choixCarteIA(plateau, ia);
                System.out.println(" Puissance du coup " + fonctionIA);
                System.out.println(" la puissance du coup est l'IA "+listIA.get(0).getPuissanceCoupIA());
                System.out.println("il reste " + listIA.get(0).getPointMana() + " points de Mana à l'IA");
                System.out.println("");


            }
        }
    }


    // cette méthode va permettre de modifier le tableau de jeu en fonction des résultats entre l'ia et le joueurs

    public void attaquer(Plateau plateau,  Humain humain, IA ia) {
        // on appel les méthodes pour choisir la mise des coups par le joueur et l'IA
        this.choixPuissanceHumain(plateau, humain);
        this.choixPuissanceIA(plateau, ia);
        // on va comparer les puissances de l'ia et du joueur
        if (listHumain.get(0).getPuissanceCoupHumain() < listIA.get(0).getPuissanceCoupIA()) {
            System.out.println("Pas assez fort HUMAIN l'IA gagne le tour (J2) "); //
            plateau.setPlaceMur(plateau.getPlaceMur() - 1); // on déplace le mur en fonction du gagnant
            plateau.plateauBase.put("m", plateau.getPlaceMur());

            if (plateau.getPlaceJ1() >= plateau.getPlaceMur()) { // condition pour gagner la manche en fonction ou se trouve le mur
                System.out.println("Bien joué IA");
                System.out.println("FIN DE LA MANCHE !!!! ");
                System.out.println("l'IA gagne la manche (IA) J2 ");
                System.out.println("Nombre de manches " + nbManches);
                System.out.println("Taille tab " + plateau.getTailleTab());
                FinManche(plateau); // on appel la méthode FIN de manche
                nbManches+=1;
            }
            // condition si le joueurs tombe dans la lave
            if (plateau.getPlaceJ1() == 0) {     // Condition si J1 est à 0
                System.out.println("Fin du game");

            }

            //Attaque IA pas assez forte
            // Même condition que pour l'IA mais cette fois-ci pour le joueur
        } else if (listHumain.get(0).getPuissanceCoupHumain() > listIA.get(0).getPuissanceCoupIA()) {
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
            //condition si l'IA tombe à l'eau
            if (plateau.getPlaceJ2() >= plateau.getTailleTab()) {     // Condition si J2 est au bout du plateau droit
                System.out.println("Fin du game");

            }
            // condition si l'IA et le joueur on la même puissance d'attaque
        } else {

            System.out.println(" Même puissance pour les deux joueurs");
        }
        // on affiche le plateau de jeu
        Enumeration enumeration2 = plateau.plateauBase.elements();
        while (enumeration2.hasMoreElements()) {
            System.out.println("Résultat de la fin du tour " + enumeration2.nextElement());

        }
    }

    // méthode qui va permettre de choisir une carte pour le joueur
    public void choixCarte(Plateau plateau,  Humain humain){
        Scanner sc = new Scanner(System.in);  // scanner qui permet une entrée
        System.out.println("Saisissez une carte entre 0 et 4 : ");
        int choix = sc.nextInt();
        if (choix>4){ // l'entrée ne peut pas etre plus grande que 4 (le nombre de carte est de 5)
            System.out.println("Choix invalide recommencez");
            this.choixCarte(plateau, humain); // on relance la méthode
        }
        // on vérifie que la liste contient l'entrée ET si oui alors on appel la méthode pouvoirCarte
        if (listCarte.contains(listCarte.get(choix))) {
            this.pouvoirCarte(plateau, listCarte.get(choix) ,listCarte.get(choix).getNumCarte(),humain);

        }

    }

    //méthode qui permet à une carte d'avoir un effet pour le joueur humain
    public void pouvoirCarte(Plateau plateau, Carte carte,int idCarte, Humain humain) {
        carte.effetCarte(idCarte,humain, plateau,this);
    }

    // meme chose que pour le joueur humain SAUF que le choix de la carte va se faire de manière aléatoire
    public void choixCarteIA(Plateau plateau, IA ia){
        Random random = new Random();
        int min = 0;
        int max = 4;
        int choixAleatoire = random.nextInt(max-min +1)+min;
        // condition si le choix est supérieur à 4
        if (choixAleatoire>4){
            System.out.println("Choix invalide recommencez");
            this.choixCarteIA(plateau, ia);
        }
        // on vérifie que la liste contient l'entrée ET si oui alors on appel la méthode pouvoirCarte
        if (listCarte2.contains(listCarte2.get(choixAleatoire))) {
            this.pouvoirCarteIA(plateau, listCarte2.get(choixAleatoire) ,listCarte2.get(choixAleatoire).getNumCarte(),ia);
            System.out.println("Carte IA: " +choixAleatoire +"" );

        }

    }
    //méthode qui permet à une carte d'avoir un effet pour le joueur IA
    public void pouvoirCarteIA(Plateau plateau, Carte carte,int idCarte, IA ia) {
        carte.effetCarteIA(idCarte,ia, plateau,this);
    }

    // permet d'afficher les cartes
    public void superPaquet(){

        for (int i = 0; i < 1; i++) {
            System.out.print(" Cartes Humain [" + i + "]=" + listCarte + " ");
        }

        for (int i = 1; i < 1; i++) {
            System.out.print(" Cartes IA [" + i + "]=" + listCarte2 + " ");
        }

    }

    // permet de mélanger les cartes dans les listes
    public void melanger() {
        for (int i = 0; i < 50; i++) {
            Collections.shuffle(listCarte);
        }for (int i = 0; i < 60; i++) {
            Collections.shuffle(listCarte2);
        }
    }

/*
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
*/

    // cette méthode va permettre de faire la gestion de fin de tours (mana, déplacement de joueurs ...)
    public void FinManche(Plateau plateau){
        listHumain.get(0).setPointMana(50); // on donne 50 points de mana au joueur Humain
        System.out.println("Joueur "+listHumain.get(0).getNomJoueur()+" a "+listHumain.get(0).getPointMana()+" points de mana");
        listIA.get(0).setPointMana(50); // on donne 50 points de mana a l'IA
        System.out.println("Joueur "+listIA.get(0).getNomJoueur()+" a "+listIA.get(0).getPointMana()+" points de mana");
        //a partir d'ici on va gerer la déplacement des joueurs mais aussi du mur
        int a=((plateau.getTailleTab()+1)/2)-plateau.getPlaceMur();
        System.out.println("a:   "+a);
        if (nbManches>1) {
            listHumain.get(0).setTotalPuissanceCoupHumain(0); // on remet à 0 la puissance total des coup du joueur humain
            nbTour=1;
            melanger(); // on mélange de nouveau les cartes
            plateau.setTailleTab(plateau.getTailleTab() - 2); // on retire 2 cases au plateau
            System.out.println("avant :     " + plateau.getPlaceMur()); // place du mur avant
            plateau.setPlaceMur((plateau.getTailleTab() / 2 + 1) - a); // on change la place du mur
            System.out.println("apres :     " + plateau.getPlaceMur()); // place du mur après
            plateau.setPlaceJ1(plateau.getPlaceMur() - 3); // on change la place du joueur 1
            plateau.setPlaceJ2(plateau.getPlaceMur() + 3); // on change la place du joueur 2
            // on place les résultat dans notre HashTable
            plateau.plateauBase.put("J1", plateau.getPlaceJ1());
            plateau.plateauBase.put("m", plateau.getPlaceMur());
            plateau.plateauBase.put("J2", plateau.getPlaceJ2());
//            plateau.plateauBase.put("J1", plateau.getPlaceJ1()+3);
//            plateau.plateauBase.put("m", plateau.getPlaceMur());
//            plateau.plateauBase.put("J2", plateau.getPlaceJ2()+3);
//            nbManches+=1;
        }

    }

    ///////////////////////////// Getter Setter /////////////////////////////////


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
