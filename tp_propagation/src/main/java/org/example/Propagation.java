package org.example;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDGS;
import org.graphstream.stream.file.FileSourceEdge;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static org.graphstream.algorithm.Toolkit.averageDegree;
import static org.graphstream.algorithm.Toolkit.randomNode;

public class Propagation {


    public static Graph builtGraph() {
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
        return g;
    }

    public static Graph builtGraphAlea(String filenameRand) {
        Graph gRan = new SingleGraph("gRan");
        FileSourceDGS fsRan = new FileSourceDGS();

        fsRan.addSink(gRan);

        try {
            fsRan.readAll(filenameRand);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fsRan.removeSink(gRan);
        }

        return gRan;
    }

    public static Graph builtGraphBarabasi(String filenameBarabasi) {
        Graph gBarabasi = new SingleGraph("gBarabasi");
        FileSourceDGS fsBarabasi = new FileSourceDGS();
        fsBarabasi.addSink(gBarabasi);
        try {
            fsBarabasi.readAll(filenameBarabasi);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fsBarabasi.removeSink(gBarabasi);
        }

        return gBarabasi;
    }

    public static double averageCarres(Graph g, int deleted) {
        double sommeCarres = 0.0;
        for (Node node : g) {
            int degree = node.getDegree();
            sommeCarres += degree * degree;
        }

        return sommeCarres / (g.getNodeCount() - deleted );
    }

    public static double seuilEpidRéel(Graph g, int deleted) {
        return avgDegreeModif(g, deleted) / averageCarres(g, deleted );
    }
    public static double seuilEpidAlea(Graph g) {
        return 1 / (averageDegree(g) + 1);
    }

    public static double avgDegreeModif(Graph g, int deleted){
        double somme = 0.0;
        for (Node node : g) {
            if (!node.hasAttribute("immunisé"))
                somme += node.getDegree();
        }
        return  somme / (g.getNodeCount() - deleted);
    }

    public static double averageCarresModif(Graph g, int deleted) {
        double sommeCarres = 0.0;
        for (Node node : g) {
            if (!node.hasAttribute("immunisé")) {
                int degree = node.getDegree();
                sommeCarres += degree * degree;
            }
        }

        return sommeCarres / (g.getNodeCount() - deleted );
    }

    public static double seuilEpidRéelModif(Graph g, int deleted) {
        return avgDegreeModif(g, deleted) / averageCarresModif(g, deleted );
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


    public static void scénario1(Graph g, String filename){
        String line = "";
        StringBuilder fileContent = new StringBuilder();
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
            line = i+1 + " "+ infections + "\n";
            fileContent.append(line);
        }
        generateFile(fileContent, filename);

    }

    public static double scénario2(Graph g, String filename) {
        // On tire au hasard 50% des noeuds et les immunise
        int immunisations = 0;
        ArrayList<Node> listeAImmuniser = new ArrayList<Node>();
        for(int i=0; i< g.getNodeCount()/2; i++) {
            listeAImmuniser.add(randomNode(g));
        }
        for (Node n : listeAImmuniser) {
            n.setAttribute("immunisé", true);
            immunisations ++;
        }
        String line ="";
        StringBuilder fileContent = new StringBuilder();
        Node patient0 = g.getNode(0);
        patient0.setAttribute("infecté", true);
        int infections = 1;
        for(int i = 0; i<90; i++){
            for(Node n : g){
                if(n.hasAttribute("infecté")){ // patient0 sera le premier à vérifier la condition donc premier à contaminer tous ses collègues
                    for(Edge e : n){
                        infections = infecte(e.getOpposite(n), infections);
                    }
                }
                infections = immunise(n, infections); // utilisation de l'anti virus si proba assez élevée
            }
            line = i+1 + " "+ infections + "\n";
            fileContent.append(line);
        }
        generateFile(fileContent, filename);
        return seuilEpidRéelModif(g, immunisations);
    }

    public static ArrayList<Double> scénario3(Graph g, String filename){
        // On tire au hasard 50% des noeuds et immunise un de leur voisin
        int immunisations = 0;
        double avgDegreeG0 = 0.0;
        double avgDegreeG1 = 0.0;
        ArrayList<Double> degresEtSeuil = new ArrayList<>();
        ArrayList<Node> aSelectionner= new ArrayList<Node>();
        for(int i=0; i< g.getNodeCount()/2; i++) {
            aSelectionner.add(randomNode(g));
        }
        for (Node n: aSelectionner) {
            if (n.edges().count() != 0) {
                int aImmuniser = (int) (Math.random() * n.edges().count());
                n.getEdge(aImmuniser).getOpposite(n).setAttribute("immunisé", true);
                immunisations++;
                avgDegreeG0 += n.getDegree();
                avgDegreeG1 += n.getEdge(aImmuniser).getOpposite(n).getDegree();
            }
        }
        String line = "";
        StringBuilder fileContent = new StringBuilder();
        Node patient0 = g.getNode(0);
        patient0.setAttribute("infecté", true);
        int infections = 1;
        for(int i = 0; i<90; i++){
            for(Node n : g){
                if(n.hasAttribute("infecté")){ // patient0 sera le premier à vérifier la condition donc premier à contaminer tous ses collègues
                    for(Edge e : n){
                        infections = infecte(e.getOpposite(n), infections);
                    }
                }
                infections = immunise(n, infections); // utilisation de l'anti virus si proba assez élevée
            }
            line = i+1 + " "+ infections + "\n";
            fileContent.append(line);

        }
        generateFile(fileContent, filename);

        // Q3 calcul des degrés moyens des groupes 0 et 1
        avgDegreeG0 = avgDegreeG0/immunisations;
        avgDegreeG1 = avgDegreeG1/immunisations;
        degresEtSeuil.add(avgDegreeG0);
        degresEtSeuil.add(avgDegreeG1);
        degresEtSeuil.add(seuilEpidRéelModif(g, immunisations));

        return degresEtSeuil;
    }

    public static void generateFile(StringBuilder fileContent, String filename){
        try {
            String path = System.getProperty(("user.dir")) + File.separator + filename ;
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.valueOf(fileContent));
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }


}