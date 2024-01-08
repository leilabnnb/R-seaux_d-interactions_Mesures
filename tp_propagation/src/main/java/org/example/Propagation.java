package org.example;

import org.graphstream.graph.Edge;
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
     * @param voisin noeud à infecter sous certaines conditions
     * @param infections nombre de noeuds inféctés
     * @return nombre de noeuds inféctés
     */
    public static int infecte(Node voisin, int infections) {
        double probaDInfection = Math.random();
        double taux_infection = 1.0 / 7.0;
        if (probaDInfection < taux_infection && !voisin.hasAttribute("immunisé")) {
            if (!voisin.hasAttribute("infecté")) {
                // donc on est sur une personne qui n'a pas été immunisée et qui n'est pas infectée actuellement
                voisin.setAttribute("infecté", true);
                infections++;
            }
        }
        return infections;
    }

    /**
     * Fonction permettant de simuler le processus d'immunisation d'un noeud passé en paramètre
     * @param voisin noeud à infecter sous certaines conditions
     * @param infections nombre de noeuds inféctés
     * @return nombre de noeuds inféctés
     */
    public static int immunise(Node voisin, int infections){
        double probaDeGuérison = Math.random();
        double taux_guérison = 2.0/30.0;
        if(probaDeGuérison < taux_guérison){
            if(voisin.hasAttribute("infecté")){
                voisin.removeAttribute("infecté");
                infections--;
            }
        }
        return infections;
    }


    public static void scénario1(Graph g){
        Node patient0 = g.getNode(0);
        patient0.setAttribute("infecté", true);
        int infections = 1;
        for(int i = 0; i<90; i++){
            for(Node n : g){
                if(n.hasAttribute("infecté")){ // patient0 sera le premier à vérifier la condition donc premier à contaminer tous ses collègues
                    for(Edge e : n){ // accéder à tous les voisins
                        infections = infecte(e.getOpposite(n), infections);
                    }
                }
                infections = immunise(n, infections); // utilisation de l'anti virus si proba assez élevée
            }
        }
    }

    public static void scénario2(Graph g) {

    }



}