package com.company;

import java.util.Scanner;

/**
 * Created by Gaby on 22/02/2017.
 */
public class Joueur {
    protected String nomJoueur;
    protected int numJoueur;
    protected int pointMana=50;
    protected int puissanceCoup;






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

    public int getPuissanceCoup() {
        return puissanceCoup;
    }

    public void setPuissanceCoup(int puissanceCoup) {
        this.puissanceCoup = puissanceCoup;
    }
}
