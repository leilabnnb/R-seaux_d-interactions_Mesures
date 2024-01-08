package org.example;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import static org.graphstream.algorithm.Toolkit.averageDegree;

public class Propagation {


    public static double averageCarres(Graph g) {
        double sommeCarres = 0.0;
        for (Node node : g) {
            int degree = node.getDegree();
            sommeCarres += degree * degree;
        }

        return sommeCarres / g.getNodeCount();
    }

    public static double seuilEpidRéel(Graph g) {
        return averageDegree(g) / averageCarres(g);
    }

    public static double seuilEpidAlea(Graph g) {
        return 1 / (averageDegree(g) + 1);
    }

    /**
     * Fonction permettant de simuler le processus d'infection d'un noeud passé en paramètre
     *
     * @param n noeud à infecter sous certaines conditions
     * @return le nombre d'infectés
     */
    public static void infecte(Node n) {
        double probaDInfection = Math.random();
        double taux_infection = 1.0 / 7.0;
        if (probaDInfection < taux_infection && !n.hasAttribute("immunisé")) {
            if (!n.hasAttribute("infecté")) {
                // donc on est sur une personne qui n'a pas été immunisée et qui n'est pas infectée actuellement
                n.setAttribute("infecté", true);
            }
        }
    }

}