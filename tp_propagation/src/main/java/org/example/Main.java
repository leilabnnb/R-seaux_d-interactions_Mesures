package org.example;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceEdge;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.example.Propagation.*;

public class Main {

    public static void main(String[] args) {

        // pour récupérer quelques informations sur les scénarios 2 et 3
        ArrayList<Double> getGroupesAvgDegree = new ArrayList<>();
        double seuilScénario2 = 0.0;

        Graph g = builtGraph();

        // informations sur le réseau de collaboration
        double taux_transmi = 1.0 / 7.0, taux_guérison = 2.0 / 30.0;
        double taux_propag = taux_transmi / taux_guérison;

        System.out.println("Taux de propagation du virus dans le réseau de collaboration scientifique: " + taux_propag);
        System.out.println("Seuil épidemique du réseau de collaboration " + seuilEpidRéel(g, 0));
        System.out.println("Seuil épidemique théorique d'un réseau aléatoire de même degré moyen " + seuilEpidAlea(g));

        for (int i = 0; i<5; i++) {
            Graph g1 = builtGraph();
            //scénario1(g1, "tp_propagation/dataPropagation/scenario1_"+i+".dat");

            Graph g2 = builtGraph();
            seuilScénario2 = scénario2(g2, "tp_propagation/dataPropagation/scenario2_"+i+".dat");

            Graph g3 = builtGraph();
            getGroupesAvgDegree = scénario3(g3, "tp_propagation/dataPropagation/scenario3_"+i+".dat");
        }

        System.out.println("Mesures scénario 2");
        System.out.println("Seuil épidémique du réseau modifié suite à l'immunisation aléatoire: "+ seuilScénario2);



        System.out.println("Mesures scénario 3");
        System.out.println("Degré moyen du groupe 0: "+ getGroupesAvgDegree.get(0));
        System.out.println("Degré moyen du groupe 1: "+ getGroupesAvgDegree.get(1));
        System.out.println("Seuil épidémique du réseau modifié suite à l'immunisation séléctive: "+ getGroupesAvgDegree.get(2));


    }

}