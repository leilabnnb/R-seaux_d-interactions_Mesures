package org.example;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceEdge;

import java.io.IOException;
import java.util.Arrays;

public class Mesures {

    public static boolean randIsConnexe(Graph g){
        double avgDegree = Toolkit.averageDegree(g);
        int order = g.getNodeCount();
        if(avgDegree > Math.log(order)) return true;
        else return false;
    }
    public static void main(String[] args) {
        Graph g = new DefaultGraph("g");
        FileSource fs = new FileSourceEdge();

        fs.addSink(g);

        try {
            fs.readAll("/home/etudiant/BS204037/RI/tp1_mesures/data/com-dblp.ungraph.txt");
        } catch( IOException e) {
            e.printStackTrace();
        } finally {
            fs.removeSink(g);
        }

    // Q2 Mesures de base
        System.out.println("Question 2");

        int order = g.getNodeCount();
        int size = g.getEdgeCount();
        double avgDegree = Toolkit.averageDegree(g);
        double clusteringCoeffG = Toolkit.averageClusteringCoefficient(g);
        System.out.println("Nombre de noeuds " + order + "\n"+
                            "Nombre de liens " + size + "\n"+
                            "Degré moyen " + avgDegree + "\n"+
                            "Coefficient de clustering " + clusteringCoeffG + "\n"
        );
        System.out.println();


    // Q3 Connéxité
        System.out.println("Question 3");

        // Le réseau est-il connexe
        if(Toolkit.isConnected(g))
            System.out.println("Le graphe est connexe");
        else
            System.out.println("Le graphe n'est pas connexe");

        // Un réseau al&atoire de même degré moyen et taille serait-il connexe?
        if (randIsConnexe(g)) System.out.println("Le graphe aléatoire de même degré moyen et avec le même nombre de noeuds est connexe.");
        else System.out.println("Le graphe aléatoire de même degré moyen et avec le même nombre de noeuds n'est pas connexe.");





    }






}