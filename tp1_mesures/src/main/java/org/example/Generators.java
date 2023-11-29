package org.example;

import org.graphstream.graph.Graph;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;

public class Generators {

    public static void randomNetwork(Graph graph, int order, double avgDegree) {
        Generator gen;
        gen = new RandomGenerator(avgDegree, false, false);
        gen.addSink(graph);
        gen.begin();
        while (graph.getNodeCount() < order && gen.nextEvents());
        gen.end();
    }

}
