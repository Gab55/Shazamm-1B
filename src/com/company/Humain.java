package com.company;

/**
 * Created by RobertPastiss on 28/04/2017.
 */
public class Humain extends Joueur {
    private int puissanceCoupHumain;

    public Humain(String nomHumain, int numHumain, int pointManaHumain) {
        super(nomHumain, numHumain, pointManaHumain);
    }

    public int getPuissanceCoupHumain() {
        return puissanceCoupHumain;
    }

    public void setPuissanceCoupHumain(int puissanceCoupHumain) {
        this.puissanceCoupHumain = puissanceCoupHumain;
    }
}
