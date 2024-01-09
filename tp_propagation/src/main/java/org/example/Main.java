package org.example;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceEdge;
import java.io.IOException;

import static org.example.Propagation.*;

public class Main {
    public static void main(String[] args) {


        // Construction du réseau de collaboration
        Graph g = new DefaultGraph("g");
        FileSource fs = new FileSourceEdge();

        fs.addSink(g);

        try {
            fs.readAll("/home/leila/RI/TP_mesures/DGS/com-dblp.ungraph.txt");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fs.removeSink(g);
        }

        // informations sur le réseau de collaboration
        double taux_transmi = 1.0/7.0, taux_guérison = 2.0/30.0;
        double taux_propag = taux_transmi/taux_guérison;

        System.out.println("Taux de propagation du virus dans le réseau de collaboration scientifique: "+ taux_propag);
        System.out.println("Seuil épidemique du réseau de collaboration "+ seuilEpidRéel(g));
        System.out.println("Seuil épidemique théorique d'un réseau aléatoire de même degré moyen "+ seuilEpidAlea(g));

        scénario1(g);
        scénario2(g);
        scénario3(g);



    }
}