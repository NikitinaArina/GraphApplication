package model;

import java.util.Objects;

public class Edge {
    private Vertex vertices1;
    private Vertex vertices2;
    private Float weight;

    public Edge(Vertex vertices1, Vertex vertices2, Float weight) {
        this.vertices1 = vertices1;
        this.vertices2 = vertices2;
        this.weight = weight;
    }

    public Vertex getVertices1() {
        return vertices1;
    }

    public void setVertices1(Vertex vertices1) {
        this.vertices1 = vertices1;
    }

    public Vertex getVertices2() {
        return vertices2;
    }

    public void setVertices2(Vertex vertices2) {
        this.vertices2 = vertices2;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return vertices1.getId().equals(edge.getVertices1().getId()) &&
                vertices2.getId().equals(edge.getVertices2().getId());
    }

    @Override
    public int hashCode() {
        return 31 * (vertices1.hashCode() + vertices2.hashCode() + weight.hashCode());
    }

    @Override
    public String toString() {
        return vertices1 +
                "-" + vertices2 +
                " weight=" + weight;
    }
}
