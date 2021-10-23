package service;

import model.Graph;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

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

    List<List<Integer>> getThreeSubgraphsGraph();

    void toMatrix(Path path, boolean flag) throws IOException;
}
