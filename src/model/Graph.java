package model;

import java.util.HashSet;
import java.util.Set;

public class Graph {
    private Set<Vertex> vertices;
    private Set<Edge> edges;

    public Graph() {
    }

    public Graph(HashSet<Vertex> vertices, HashSet<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public Set<Vertex> getVertices() {
        if (vertices == null) {
            return new HashSet<>();
        }
        return vertices;
    }

    public void setVertices(Set<Vertex> vertices) {
        this.vertices = vertices;
    }

    public Set<Edge> getEdges() {
        if (edges == null) {
            return new HashSet<>();
        }
        return edges;
    }

    public void setEdges(Set<Edge> edges) {
        this.edges = edges;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "vertices=" + vertices +
                ", edges=" + edges +
                '}';
    }
}
