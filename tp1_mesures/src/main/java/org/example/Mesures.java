package org.example;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceEdge;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Locale;
import java.io.IOException;
import java.util.Random;
import org.graphstream.stream.file.FileSinkDGS;
import static org.graphstream.algorithm.Toolkit.*;


public class Mesures {

    public static boolean randIsConnexe(Graph g){
        double avgDegree = averageDegree(g);
        int order = g.getNodeCount();
        if(avgDegree > Math.log(order)) return true;
        else return false;
    }

    public static void generateFileDegreeDist(String filename, Graph g){

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


    /**
     * Permet de trouver la distance moyenne du graph passé en paramètre
     * @param g
     * @return la distance moyenne
     */
    public static double avgDistance(Graph g){
        Node node;
        Random random = new Random();
        double distanceTotale = 0.0;

        try {
            String path = System.getProperty(("user.dir")) + File.separator +"tp1_mesures/data/distances.dat" ;
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);

            HashMap<Integer, Integer> frequencyMap = new HashMap<>();

            for (int i = 0; i < 1000; i++) {
                node = randomNode(g, random);
                BreadthFirstIterator bfs = new BreadthFirstIterator(node);
                while (bfs.hasNext()) {
                    Node next = bfs.next();
                    distanceTotale += bfs.getDepthOf(next);
                    if (frequencyMap.containsKey(bfs.getDepthOf(next))) {
                        // Si la distance est déjà dans la map, incrémenter sa fréquence.
                        frequencyMap.put(bfs.getDepthOf(next), frequencyMap.get(bfs.getDepthOf(next)) + 1);
                    } else {
                        // Sinon, ajouter la distance à la map avec une fréquence de 1.
                        frequencyMap.put(bfs.getDepthOf(next), 1);
                    }
                }

            }
            for (Integer distance : frequencyMap.keySet()) {
                bw.write(distance +"\t"+ frequencyMap.get(distance) +"\n" );
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        double distanceMoyenne = distanceTotale/ (1000 * (g.getNodeCount() -1));

        return distanceMoyenne;
    }

     /*
    Génération de graphs
     */

    // Q6
    public static void genRandomNetwork (int n, double avgDegree){

        Graph g = new SingleGraph("réseau aléatoire");
        RandomGenerator gen = new RandomGenerator(avgDegree);
        gen.addSink(g);
        gen.begin();
        for (int i = 0; i < n; i++){
            gen.nextEvents();
        }
        gen.end();

        FileSinkDGS fileSinkDGS = new FileSinkDGS();
        try {
            fileSinkDGS.writeAll(g, "/home/leila/RI/TP_mesures/tp1_mesures/graph/randomNetwork.dgs");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void genBarabasiNetwork (int n, double avgDegree){

        Graph g = new SingleGraph("réseau Barabàsi-Albert");
        RandomGenerator gen = new RandomGenerator(avgDegree);
        gen.addSink(g);
        gen.begin();
        gen.end();

        FileSinkDGS fileSinkDGS = new FileSinkDGS();
        try {
            fileSinkDGS.writeAll(g, "/home/leila/RI/TP_mesures/tp1_mesures/graph/randomNetwork.dgs");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mesuresDeBases(Graph g ){
        // mesures
        System.out.println("Nombre de noeuds " + g.getNodeCount() + "\n" +
                "Nombre de liens " + g.getEdgeCount() + "\n" +
                "Coefficient de clustering " + averageClusteringCoefficient(g) + "\n"
        );
        double distanceTotale = 0;
        int nbr=0;
        for(int i = 0 ; i < 1000 ; i++){
            Node depart = randomNode(g);
            BreadthFirstIterator it = new BreadthFirstIterator(depart);
            while(it.hasNext()){
                Node next = it.next();
                distanceTotale += it.getDepthOf(next);
            }
        }
        double distanceMoyenne = distanceTotale /(1000 * (g.getNodeCount() -1));
        System.out.println("Distance moyenne dans le réseau: " + distanceMoyenne);
    }


    public static void main(String[] args) {
        Graph g = new DefaultGraph("g");
        FileSource fs = new FileSourceEdge();

        fs.addSink(g);

        try {
            fs.readAll("/home/leila/RI/TP_mesures/tp1_mesures/data/com-dblp.ungraph.txt");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fs.removeSink(g);
        }

        // Q2 Mesures de base
        System.out.println("Question 2");

        int order = g.getNodeCount();
        int size = g.getEdgeCount();
        double avgDegree = averageDegree(g);
        double clusteringCoeffG = averageClusteringCoefficient(g);
        System.out.println("Nombre de noeuds " + order + "\n" +
                "Nombre de liens " + size + "\n" +
                "Degré moyen " + avgDegree + "\n" +
                "Coefficient de clustering " + clusteringCoeffG + "\n"
        );
        System.out.println();


        // Q3 Connéxité
        System.out.println("Question 3");

        // Le réseau est-il connexe
        if (Toolkit.isConnected(g))
            System.out.println("Le réseau est connexe");
        else
            System.out.println("Le réseau n'est pas connexe");

        // Un réseau al&atoire de même degré moyen et taille serait-il connexe?
        if (randIsConnexe(g))
            System.out.println("Le réseau aléatoire de même degré moyen et avec le même nombre de noeuds est connexe.");
        else
            System.out.println("Le réseau aléatoire de même degré moyen et avec le même nombre de noeuds n'est pas connexe.");

        // A partir de quel degré moyen un réseau aléatoire de même taille serait connexe
        System.out.println("Le degré moyen pour qu'un réseau aléatoire de taille " + order + " soit connexe doit être supérieur à " + Math.log(order));

        // Q4
        /*int[] degreeDist = Toolkit.degreeDistribution(g);
        for (int k = 0; k < degreeDist.length; k++) {
            if (degreeDist[k] != 0) {
                System.out.printf(Locale.US, "%6d%20.8f%n", k, (double)degreeDist[k] / g.getNodeCount());
            }
        }*/

        //generateFileDegreeDist("tp1_mesures/data/distribution.dat", g);

        // Q5
        /// estimer distance moyenne
        System.out.println("Question 5\nDistance moyenne obtenue par echantillonage : " + avgDistance(g));

        // Q6
        // test des 3 méthodes ...

    }



}