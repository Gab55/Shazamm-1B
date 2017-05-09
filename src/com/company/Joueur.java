package com.company;

import java.util.Scanner;



// CLASSE MERE DE LA CLASSE HUMAIN ET IA

public class Joueur {
    protected String nomJoueur;
    protected int numJoueur;
    protected int pointMana=50;

    ///////////////////// CONSTRUCTEUR /////////////////////////////////

    public Joueur(String nomJoueur, int numJoueur, int pointMana) {
        this.nomJoueur = nomJoueur;
        this.numJoueur = numJoueur;
        this.pointMana = pointMana;
    }

    //////////////////// GETTER ET SETTER ///////////////////////////////

    public String getNomJoueur() {
        return nomJoueur;
    }

    public void setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }

    public int getNumJoueur() {
        return numJoueur;
    }

    public void setNumJoueur(int numJoueur) {
        this.numJoueur = numJoueur;
    }

    public int getPointMana(){return pointMana;}

    public void setPointMana(int pointMana){this.pointMana=pointMana;}

}
