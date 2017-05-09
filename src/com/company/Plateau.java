package com.company;

import java.util.Hashtable;



// CLASSE QUI VA PERMETTRE LE GESTION DU PLATEAU DE JEU

public class Plateau {
    private int tailleTab=19; // taille total du plateau
    private int tabLave=19;
    Hashtable plateauBase = new Hashtable(tailleTab);
    private int placeMur = tailleTab/2+1; // opération qui va permettre de connaitre la place du mur même après modification du plateau
    private int placeJ1 = placeMur-3; // opération qui va permettre de connaitre la place du J1 même après modification du plateau
    private int placeJ2= placeMur+3; // opération qui va permettre de connaitre la place du J1 même après modification du plateau

    public Plateau() {

    }

    /////////////////////////// Getter et Setter //////////////////////////////


    public Hashtable getPlateauBase() {
        return plateauBase;
    }

    public void setPlateauBase(Hashtable plateauBase) {
        this.plateauBase = plateauBase;
    }

    public int getTailleTab() {
        return tailleTab;
    }

    public void setTailleTab(int tailleTab) {
        this.tailleTab = tailleTab;
    }

    public int getPlaceMur() {
        return placeMur;
    }

    public void setPlaceMur(int placeMur) {
        this.placeMur = placeMur;
    }

    public int getPlaceJ1() {
        return placeJ1;
    }

    public void setPlaceJ1(int placeJ1) {
        this.placeJ1 = placeJ1;
    }

    public int getPlaceJ2() {
        return placeJ2;
    }

    public void setPlaceJ2(int placeJ2) {
        this.placeJ2 = placeJ2;
    }

    public int getTabLave() {
        return tabLave;
    }

    public void setTabLave(int tabLave) {
        this.tabLave = tabLave;
    }
}
