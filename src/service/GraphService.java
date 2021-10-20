package service;

import model.Graph;

import java.io.IOException;

public interface GraphService {

    Graph createInstance();

    Graph loadFromListEdges(String name) throws IOException;

    Graph loadFromMatrix(String name) throws IOException;

    void saveAsListEdges(String name) throws IOException;

    void saveAsMatrix(String name) throws IOException;

    boolean addVertex(Integer id);

    boolean removeVertex(Integer id);

    boolean addEdge(Integer id, Integer id2, Float weight);

    boolean removeEdge(Integer id, Integer id2);

    Integer getCountVertices();

    Integer getCountEdges();

    boolean isAdjacency(Integer id, Integer id2);

    Float getWeight(Integer id, Integer id2);
}
