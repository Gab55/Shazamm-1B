package com.company;

import java.util.ArrayList;

/**
 * Created by RobertPastiss on 28/04/2017.
 */

// CLASSE FILLE HUMAIN QUI VA HERITER DE LA CLASSE JOUEUR

public class Humain extends Joueur {
    private int puissanceCoupHumain;
    private int totalPuissanceCoupHumain;

    ///////////////////// CONSTRUCTEUR /////////////////////////////////

    // on va se servir ici de la classe mere
    public Humain(String nomHumain, int numHumain, int pointManaHumain) {
        super(nomHumain, numHumain, pointManaHumain);
    }

    //////////////////// GETTER ET SETTER ///////////////////////////////

    public int getPuissanceCoupHumain() {
        return puissanceCoupHumain;
    }

    public void setPuissanceCoupHumain(int puissanceCoupHumain) {
        this.puissanceCoupHumain = puissanceCoupHumain;
    }

    public int getTotalPuissanceCoupHumain() {
        return totalPuissanceCoupHumain;
    }

    public void setTotalPuissanceCoupHumain(int totalPuissanceCoupHumain) {
        this.totalPuissanceCoupHumain = totalPuissanceCoupHumain;
    }
}
