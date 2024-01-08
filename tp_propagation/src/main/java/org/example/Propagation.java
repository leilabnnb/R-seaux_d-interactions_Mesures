package org.example;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import static org.graphstream.algorithm.Toolkit.averageDegree;

public class Propagation {


    public static double averageCarres(Graph g){
        double sommeCarres = 0.0;
        for (Node node : g) {
            int degree = node.getDegree();
            sommeCarres+= degree * degree;
        }

        return sommeCarres / g.getNodeCount();
    }
    public static double seuilEpidRéel(Graph g){
        return averageDegree(g) / averageCarres(g);
    }




}
