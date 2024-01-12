package org.example;

import org.graphstream.graph.Graph;

import java.util.ArrayList;

import static org.example.Propagation.*;

public class Main {

    public static void main(String[] args) {

        // pour récupérer quelques informations sur les scénarios 2 et 3
        ArrayList<Double> réelGetGroupesAvgDegree = new ArrayList<>();
        double réelSeuilScénario2 = 0.0;

        ArrayList<Double> aléaGetGroupesAvgDegree = new ArrayList<>();
        double aléaSeuilScénario2 = 0.0;

        ArrayList<Double> barabasiGetGroupesAvgDegree = new ArrayList<>();
        double barabasiSeuilScénario2 = 0.0;

        String filenameBarabasi = "/home/leila/RI/TP_mesures/tp1_mesures/graph/barabasiNetwork2.dgs";
        String filenameRand = "/home/leila/RI/TP_mesures/tp1_mesures/graph/randomNetwork2.dgs";




        // informations sur le réseau de collaboration
        double taux_transmi = 1.0 / 7.0, taux_guérison = 2.0 / 30.0;
        double taux_propag = taux_transmi / taux_guérison;
/*
        Graph g = builtGraph();

        System.out.println("Taux de propagation du virus dans le réseau de collaboration scientifique: " + taux_propag);
        System.out.println("Seuil épidemique du réseau de collaboration " + seuilEpidRéel(g, 0));
        System.out.println("Seuil épidemique théorique d'un réseau aléatoire de même degré moyen " + seuilEpidAlea(g));

        for (int i = 0; i<5; i++) {
            Graph g1 = builtGraph();
            scénario1(g1, "tp_propagation/dataPropagation/scenario1_"+i+".dat");

            Graph g2 = builtGraph();
            réelSeuilScénario2 = scénario2(g2, "tp_propagation/dataPropagation/scenario2_"+i+".dat");

            Graph g3 = builtGraph();
            réelGetGroupesAvgDegree = scénario3(g3, "tp_propagation/dataPropagation/scenario3_"+i+".dat");
        }

        System.out.println("Simulations réseau de collaboration");
        System.out.println("Mesures scénario 2");
        System.out.println("Seuil épidémique du réseau modifié suite à l'immunisation aléatoire: "+ réelSeuilScénario2);


        System.out.println("Mesures scénario 3");
        System.out.println("Degré moyen du groupe 0: "+ réelGetGroupesAvgDegree.get(0));
        System.out.println("Degré moyen du groupe 1: "+ réelGetGroupesAvgDegree.get(1));

        System.out.println("Seuil épidémique du réseau modifié suite à l'immunisation séléctive: "+ réelGetGroupesAvgDegree.get(2));
*/

        //Q5 tests sur réseau aléatoire et Barabasi

        for (int i = 0; i<5; i++) {
            Graph gAlea1 = builtGraphAlea(filenameRand);
            scénario1(gAlea1, "tp_propagation/dataPropagation/aléatoire/alea_scenario1_"+i+".dat");

            Graph gAlea2 = builtGraphAlea(filenameRand);
            aléaSeuilScénario2 = scénario2(gAlea2, "tp_propagation/dataPropagation/aléatoire/alea_scenario2_"+i+".dat");

            Graph gAlea3 = builtGraphAlea(filenameRand);
            aléaGetGroupesAvgDegree = scénario3(gAlea3, "tp_propagation/dataPropagation/aléatoire/alea_scenario3_"+i+".dat");
        }

        System.out.println("");
        System.out.println("Simulations réseau aléatoire");
        System.out.println("Mesures scénario 2");
        System.out.println("Seuil épidémique du réseau modifié suite à l'immunisation aléatoire: "+ aléaSeuilScénario2);

        System.out.println("Mesures scénario 3");
        System.out.println("Degré moyen du groupe 0: "+ aléaGetGroupesAvgDegree.get(0));
        System.out.println("Degré moyen du groupe 1: "+ aléaGetGroupesAvgDegree.get(1));

        System.out.println("Seuil épidémique du réseau modifié suite à l'immunisation séléctive: "+ aléaGetGroupesAvgDegree.get(2));


/*
        for (int i = 0; i<5; i++) {
            Graph gBarabasi1 = builtGraphBarabasi(filenameBarabasi);
            scénario1(gBarabasi1, "tp_propagation/dataPropagation/barabasi/barabasi_scenario1_"+i+".dat");

            Graph gBarabasi2 = builtGraphBarabasi(filenameBarabasi);
            barabasiSeuilScénario2 = scénario2(gBarabasi2, "tp_propagation/dataPropagation/barabasi/barabasi_scenario2_"+i+".dat");

            Graph gBarabasi3 = builtGraphBarabasi(filenameBarabasi);
            barabasiGetGroupesAvgDegree = scénario3(gBarabasi3, "tp_propagation/dataPropagation/barabasi/barabasi_scenario3_"+i+".dat");
        }

        System.out.println("");
        System.out.println("Simulations réseau Barabasi");

        System.out.println("Mesures scénario 2");
        System.out.println("Seuil épidémique du réseau modifié suite à l'immunisation aléatoire: "+ barabasiSeuilScénario2);

        System.out.println("Mesures scénario 3");
        System.out.println("Degré moyen du groupe 0: "+ barabasiGetGroupesAvgDegree.get(0));
        System.out.println("Degré moyen du groupe 1: "+ barabasiGetGroupesAvgDegree.get(1));

        System.out.println("Seuil épidémique du réseau modifié suite à l'immunisation séléctive: "+ barabasiGetGroupesAvgDegree.get(2));

*/




    }

}