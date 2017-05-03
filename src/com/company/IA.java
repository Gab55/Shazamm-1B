package com.company;

/**
 * Created by RobertPastiss on 28/04/2017.
 */
public class IA extends Joueur {
    private int puissanceCoupIA;

    public IA(String nomIA, int numIA, int pointManaIA){
        super(nomIA,numIA, pointManaIA);
    }

    public int getPuissanceCoupIA() {
        return puissanceCoupIA;
    }

    public void setPuissanceCoupIA(int puissanceCoupIA) {
        this.puissanceCoupIA = puissanceCoupIA;
    }
}
