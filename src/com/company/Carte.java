package com.company;

import com.sun.org.apache.xpath.internal.SourceTree;
import jdk.nashorn.internal.scripts.JO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by Gaby on 24/02/2017.
 */

// CLASSE OU ON VA FAIRE GERER LES CARTES ET LEURS COMPORTEMENTS
public class Carte {

    private int numCarte;
    private String nomCarte;

    ///////////////////// CONSTRUCTEUR /////////////////////////////////


    public Carte(int numCarte, String nomCarte) {
        this.numCarte = numCarte;
        this.nomCarte = nomCarte;
    }

    public String toString() {
        if (numCarte == 1) {
            return "carte Mutisme";
        } else if (numCarte == 2) {
            return "carte Clone";
        } else if (numCarte == 3) {
            return "carte Larcin";
        } else if (numCarte == 4) {
            return "carte Fin de manche";
        } else if (numCarte == 5) {
            return "carte Milieu";
        } else if (numCarte == 6) {
            return "carte Recyclage";
        } else if (numCarte == 7) {
            return "carte Boost attaque";
        } else if (numCarte == 8) {
            return "carte Double dose";
        } else if (numCarte == 9) {
            return "carte Qui perd gagne";
        } else if (numCarte == 10) {
            return "carte Brasier";
        } else if (numCarte == 11) {
            return "carte Résistance";
        } else if (numCarte == 12) {
            return "carte Harpagon";
        } else if (numCarte == 13) {
            return "carte Boost réserve";
        } else if (numCarte == 14) {
            return "carte Aspiration";
        }
        return ""+numCarte;
    }

    // Méthode pour donner les effets à chaque cartes pour le paquets de cartes pour le joueur humain
    public void effetCarte(int nb, Humain humain,Plateau plateau, Jeu jeu) {
        for (int i = 0; i < jeu.getListJoueur().size(); i++) {
            numCarte = nb;
            if (numCarte == 1) {
                System.out.println("Mutisme");
                jeu.getListHumain().get(i).setPuissanceCoupHumain(0);
                System.out.println(humain.getPuissanceCoupHumain());
                break;
            } else if (numCarte == 2) {
                System.out.println("carte Clone");
                break;
            } else if (numCarte == 3) {
                System.out.println("carte Larcin");
                break;
            } else if (numCarte == 4) {
                System.out.println("carte Fin de manche");
                jeu.FinManche(plateau);
                break;
            } else if (numCarte == 5) {
                System.out.println("carte Milieu");
                plateau.setPlaceMur(10);
                break;
            } else if (numCarte == 6) {
                System.out.println("carte Recyclage");
                Scanner sc2 = new Scanner(System.in);
                System.out.println("Vous avez le droit à 5 points en dessous ou au dessus de votre mise ? ");
                int choix2 = sc2.nextInt();
                if ((choix2 > 5) || (choix2 < 0)) {
                    System.out.println(" Impossible");
                } else {
                    jeu.getListHumain().get(i).setPuissanceCoupHumain(choix2);
                }
                break;
            } else if (numCarte == 7) {
                System.out.println("carte Boost attaque");
                jeu.getListHumain().get(i).setPuissanceCoupHumain(jeu.getListHumain().get(i).getPuissanceCoupHumain() + 7);
                break;
            } else if (numCarte == 8) {
                System.out.println("carte Double dose");
                jeu.getListHumain().get(i).setPuissanceCoupHumain(jeu.getListHumain().get(i).getPuissanceCoupHumain() * 2);
            } else if (numCarte == 9) {
                System.out.println("carte Qui perd gagne");
                if (jeu.getListHumain().get(i) == jeu.getListHumain().get(0)) {
                    plateau.setPlaceMur(plateau.getPlaceMur() - 2);
                } else if (jeu.getListHumain().get(i) == jeu.getListHumain().get(1)) {
                    plateau.setPlaceMur(plateau.getPlaceMur() + 2);
                }
            } else if (numCarte == 10) {
                System.out.println("carte Brasier");
                if (jeu.getListHumain().get(i) == jeu.getListHumain().get(0)) {
                    plateau.setPlaceMur(plateau.getPlaceMur() + 2);
                } else if (jeu.getListHumain().get(i) == jeu.getListHumain().get(1)) {
                    plateau.setPlaceMur(plateau.getPlaceMur() - 2);
                }

            } else if (numCarte == 11) {
                System.out.println("carte Résistance");
                if (jeu.getListHumain().get(i) == jeu.getListHumain().get(0)) {
                    plateau.setPlaceMur(plateau.getPlaceMur() - 1);
                } else if (jeu.getListHumain().get(i) == jeu.getListHumain().get(1)) {
                    plateau.setPlaceMur(plateau.getPlaceMur() + 1);
                }
            } else if (numCarte == 12) {
                System.out.println("carte Harpagon");
            } else if (numCarte == 13) {
                System.out.println("carte Boost réserve");
                jeu.getListHumain().get(i).setPointMana(jeu.getListHumain().get(i).getPointMana() + 13);
            } else if (numCarte == 14) {
                System.out.println("carte Aspiration");
                if (jeu.getListHumain().get(i) == jeu.getListHumain().get(0)) {
                    jeu.getListHumain().get(0).setPointMana(jeu.getListHumain().get(1).getPuissanceCoupHumain());
                } else if (jeu.getListHumain().get(i) == jeu.getListHumain().get(1)) {
                    jeu.getListHumain().get(1).setPointMana(jeu.getListHumain().get(0).getPuissanceCoupHumain());

                }
            }
        }
    }

    // méthode pour donner les effets des cartes pour le paquet cartes de l'ia
    public void effetCarteIA(int nb, IA ia,Plateau plateau, Jeu jeu) {
        for (int i = 0; i < jeu.getListJoueur().size(); i++) {
            numCarte = nb;
            if (numCarte == 1) {
                System.out.println("Mutisme");
                jeu.getListIA().get(i).setPuissanceCoupIA(0);
                System.out.println(ia.getPuissanceCoupIA());
                break;
            } else if (numCarte == 2) {
                System.out.println("carte Clone");
                break;
            } else if (numCarte == 3) {
                System.out.println("carte Larcin");
                break;
            } else if (numCarte == 4) {
                System.out.println("carte Fin de manche");
                jeu.FinManche(plateau);
                break;
            } else if (numCarte == 5) {
                System.out.println("carte Milieu");
                plateau.setPlaceMur(10);
                break;
            } else if (numCarte == 6) {
                System.out.println("carte Recyclage");
                Scanner sc2 = new Scanner(System.in);
                System.out.println("Vous avez le droit à 5 points en dessous ou au dessus de votre mise ? ");
                int choix2 = sc2.nextInt();
                if ((choix2 > 5) || (choix2 < 0)) {
                    System.out.println(" Impossible");
                } else {
                    jeu.getListIA().get(i).setPuissanceCoupIA(choix2);
                }
                break;
            } else if (numCarte == 7) {
                System.out.println("carte Boost attaque");
                jeu.getListIA().get(i).setPuissanceCoupIA(jeu.getListIA().get(i).getPuissanceCoupIA() + 7);
                break;
            } else if (numCarte == 8) {
                System.out.println("carte Double dose");
                jeu.getListIA().get(i).setPuissanceCoupIA(jeu.getListIA().get(i).getPuissanceCoupIA() * 2);
            } else if (numCarte == 9) {
                System.out.println("carte Qui perd gagne");
                if (jeu.getListIA().get(i) == jeu.getListIA().get(0)) {
                    plateau.setPlaceMur(plateau.getPlaceMur() - 2);
                } else if (jeu.getListIA().get(i) == jeu.getListIA().get(1)) {
                    plateau.setPlaceMur(plateau.getPlaceMur() + 2);
                }
            } else if (numCarte == 10) {
                System.out.println("carte Brasier");
                if (jeu.getListIA().get(i) == jeu.getListIA().get(0)) {
                    plateau.setPlaceMur(plateau.getPlaceMur() + 2);
                } else if (jeu.getListIA().get(i) == jeu.getListIA().get(1)) {
                    plateau.setPlaceMur(plateau.getPlaceMur() - 2);
                }

            } else if (numCarte == 11) {
                System.out.println("carte Résistance");
                if (jeu.getListIA().get(i) == jeu.getListIA().get(0)) {
                    plateau.setPlaceMur(plateau.getPlaceMur() - 1);
                } else if (jeu.getListIA().get(i) == jeu.getListIA().get(1)) {
                    plateau.setPlaceMur(plateau.getPlaceMur() + 1);
                }
            } else if (numCarte == 12) {
                System.out.println("carte Harpagon");
            } else if (numCarte == 13) {
                System.out.println("carte Boost réserve");
                jeu.getListIA().get(i).setPointMana(jeu.getListIA().get(i).getPointMana() + 13);
            } else if (numCarte == 14) {
                System.out.println("carte Aspiration");
                if (jeu.getListIA().get(i) == jeu.getListIA().get(0)) {
                    jeu.getListIA().get(0).setPointMana(jeu.getListIA().get(1).getPuissanceCoupIA());
                } else if (jeu.getListIA().get(i) == jeu.getListIA().get(1)) {
                    jeu.getListIA().get(1).setPointMana(jeu.getListIA().get(0).getPuissanceCoupIA());

                }
            }
        }
    }

    //////////////////// GETTER ET SETTER ///////////////////////////////


    public int getNumCarte() {
        return numCarte;
    }

    public void setNumCarte(int numCarte) {
        this.numCarte = numCarte;
    }

    public String getNomCarte() {
        return nomCarte;
    }

    public void setNomCarte(String nomCarte) {
        this.nomCarte = nomCarte;
    }


}
