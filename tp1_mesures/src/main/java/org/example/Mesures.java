package org.example;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceEdge;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Locale;
import java.io.IOException;


public class Mesures {

    public static boolean randIsConnexe(Graph g){
        double avgDegree = Toolkit.averageDegree(g);
        int order = g.getNodeCount();
        if(avgDegree > Math.log(order)) return true;
        else return false;
    }

    public static void generateFile(String filename, Graph g){

        try {
            String path = System.getProperty(("user.dir")) + File.separator + filename;
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter((fw));
            int[] degreeDist = Toolkit.degreeDistribution(g);
            bw.write("#\t k  \t\t\tp(k)\n");
            for (int k = 0; k < degreeDist.length; k++) {
                if (degreeDist[k] != 0) {
                    String s = String.format(Locale.US, "%6d%20.8f%n", k, (double)degreeDist[k] / g.getNodeCount());
                    bw.write(s);
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


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
            System.out.println("Le réseau est connexe");
        else
            System.out.println("Le réseau n'est pas connexe");

        // Un réseau al&atoire de même degré moyen et taille serait-il connexe?
        if (randIsConnexe(g)) System.out.println("Le réseau aléatoire de même degré moyen et avec le même nombre de noeuds est connexe.");
        else System.out.println("Le réseau aléatoire de même degré moyen et avec le même nombre de noeuds n'est pas connexe.");

        // A partir de quel degré moyen un réseau aléatoire de même taille serait connexe
        System.out.println("Le degré moyen pour qu'un réseau aléatoire de taille " + order + " soit connexe doit être supérieur à " +Math.log(order));

        int[] degreeDist = Toolkit.degreeDistribution(g);
        for (int k = 0; k < degreeDist.length; k++) {
            if (degreeDist[k] != 0) {
                System.out.printf(Locale.US, "%6d%20.8f%n", k, (double)degreeDist[k] / g.getNodeCount());
            }
        }

        generateFile("tp1_mesures/data/distribution.dat", g);


    }






}