package com.company;

/**
 * Created by RobertPastiss on 28/04/2017.
 */
public class IA extends Joueur {
    private int puissanceCoupIA;

    ///////////////////// CONSTRUCTEUR /////////////////////////////////

    // on va se servir ici de la classe mere
    public IA(String nomIA, int numIA, int pointManaIA){
        super(nomIA,numIA, pointManaIA);
    }

    //////////////////// GETTER ET SETTER ///////////////////////////////

    public int getPuissanceCoupIA() {
        return puissanceCoupIA;
    }

    public void setPuissanceCoupIA(int puissanceCoupIA) {
        this.puissanceCoupIA = puissanceCoupIA;
    }
}
