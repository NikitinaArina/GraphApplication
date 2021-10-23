package service;

import model.Edge;
import model.Graph;
import model.Vertex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.logging.Logger;

public class GraphServiceImpl implements GraphService {
    private static final Logger logger = Logger.getLogger(GraphService.class.getName());
    private static final Path PATH = Path.of("/Users/arnikitina/IdeaProjects/GraphApp/files");
    private Graph graph;

    public GraphServiceImpl() {
        createInstance();
    }

    public Graph createInstance() {
        this.graph = new Graph();
        logger.info("The graph created");
        return graph;
    }

    @Override
    public Graph loadFromMatrix(String name) throws IOException {
        Path path = Path.of(PATH + "/" + name + ".txt");
        List<String> lines = getData(path);

        int amountOfVertices = 0;
        int temp = 1;

        clearGraph();

        String[] vertices = lines.get(0).split("\s+");

        if (vertices.length == 1) amountOfVertices = Integer.parseInt(vertices[0]);
        else {
            logger.warning("Not correct values in file");
            return new Graph();
        }

        for (String line : lines) {
            String[] arrayOfValues = line.split("\s+");

            if (arrayOfValues.length == amountOfVertices) {
                Vertex vertex = new Vertex(temp);

                for (int i = 0; i < amountOfVertices; i++) {
                    Vertex vertex2 = new Vertex(i + 1);

                    if (Float.parseFloat(arrayOfValues[i]) != 0) {
                        Edge edge = new Edge(vertex, vertex2, Float.parseFloat(arrayOfValues[i]));

                        addVertex(vertex);
                        addVertex(vertex2);
                        addEdge(edge);
                    }
                }
                temp++;
            }
        }
        return graph;
    }

    public Graph loadFromListEdges(String name) throws IOException {
        Path path = Path.of(PATH + "/" + name + ".txt");

        List<String> lines = getData(path);

        clearGraph();

        for (String line : lines) {
            String[] arrayOfValues = line.split("\s+");

            if (arrayOfValues.length == 3) {
                Vertex vertex = new Vertex(Integer.parseInt(arrayOfValues[0]));
                Vertex vertex2 = new Vertex(Integer.parseInt(arrayOfValues[1]));

                addVertex(vertex);
                addVertex(vertex2);

                Edge edge = new Edge(vertex, vertex2, Float.parseFloat(arrayOfValues[2]));
                addEdge(edge);
            }
        }
        return graph;
    }

    @Override
    public void saveAsListEdges(String name) throws IOException {
        Path path = Path.of(PATH + "/" + name + ".txt");

        isCreatedFile(path);

        Files.write(path, Collections.singleton(getCountVertices().toString()), StandardCharsets.UTF_8);

        for (Edge edge : graph.getEdges()) {
            Files.write(path,
                    Collections.singleton(edge.getVertices1().getId() +
                            " " + edge.getVertices2().getId() +
                            " " + edge.getWeight()),
                    StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        }
    }

    @Override
    public void saveAsMatrix(String name) throws IOException {
        Path path = Path.of(PATH + "/" + name + ".txt");

        isCreatedFile(path);

        Files.write(path, Collections.singleton(getCountVertices().toString()), StandardCharsets.UTF_8);

        toMatrix(path, true);
    }

    @Override
    public boolean addVertex(Integer id) {
        Set<Vertex> vertices = graph.getVertices();
        Vertex vertex = new Vertex(id);

        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            graph.setVertices(vertices);

            logger.info(String.format("Vertex (%s) was add successfully", id));
            return true;
        } else {
            logger.warning(String.format("Vertex (%s) was not add, looks like you have the same vertex in the graph", id));
            return false;
        }
    }

    public void addVertex(Vertex vertex) {
        addVertex(vertex.getId());
    }

    @Override
    public boolean removeVertex(Integer id) {
        Set<Vertex> vertices = graph.getVertices();
        Vertex vertex = new Vertex(id);

        if (vertices.contains(vertex)) {
            vertices.remove(vertex);

            Set<Edge> edges = graph.getEdges();

            for (Edge edge : edges) {
                if (edge.getVertices1().equals(vertex)) {
                    edges.remove(edge);
                } else if (edge.getVertices2().equals(vertex)) {
                    edges.remove(edge);
                }
            }
            logger.info(String.format("Vertex (%s) was delete successfully", id));
            return true;
        } else {
            logger.warning(String.format("Vertex (%s) was not delete, looks like you have the same vertex in the graph", id));
            return false;
        }
    }

    @Override
    public boolean addEdge(Integer id, Integer id2, Float weight) {
        Vertex vertex = new Vertex(id);
        Vertex vertex2 = new Vertex(id2);

        Set<Vertex> vertices = graph.getVertices();
        Set<Edge> edges = graph.getEdges();

        if (vertices.contains(vertex) && vertices.contains(vertex2)) {
            Edge edge = new Edge(vertex, vertex2, weight);

            if (edges.contains(edge)) {
                edges.remove(edge);
            }

            edges.add(edge);
            graph.setEdges(edges);

            logger.info(String.format("Edge with vertices (%s and %s) was add successfully", id, id2));
            return true;
        } else {
            logger.warning(String.format("Edge with vertices (%s and %s) was not add, looks like you don't have some of the vertices", id, id2));
            return false;
        }
    }

    public void addEdge(Edge edge) {
        addEdge(edge.getVertices1().getId(), edge.getVertices2().getId(), edge.getWeight());
    }

    @Override
    public boolean removeEdge(Integer id, Integer id2) {
        Vertex vertex = new Vertex(id);
        Vertex vertex2 = new Vertex(id2);
        Edge edge = new Edge(vertex, vertex2, 0F);

        Set<Edge> edges = graph.getEdges();
        Set<Vertex> vertices = graph.getVertices();

        if (vertices.contains(vertex) && vertices.contains(vertex2)) {
            edges.remove(edge);
            graph.setEdges(edges);

            logger.info(String.format("Edge with vertices (%s and %s) was delete successfully", id, id2));
            return true;
        }
        logger.info(String.format("Edge with vertices (%s and %s) was not delete, looks like you don't have some of the vertices", id, id2));
        return false;
    }

    @Override
    public Integer getCountVertices() {
        return graph.getVertices().size();
    }

    @Override
    public Integer getCountEdges() {
        return graph.getEdges().size();
    }

    @Override
    public boolean isAdjacency(Integer id, Integer id2) {
        Set<Edge> edges = graph.getEdges();

        Vertex vertex = new Vertex(id);
        Vertex vertex2 = new Vertex(id2);

        for (Edge edge : edges) {
            if (edge.getVertices1().equals(vertex) && edge.getVertices2().equals(vertex2)) {
                logger.info(String.format("The vertices (%s) and (%s) are adjacency", id, id2));
                return true;
            } else if (edge.getVertices2().equals(vertex) && edge.getVertices1().equals(vertex2)) {
                logger.info(String.format("The vertices (%s) and (%s) are adjacency", id, id2));
                return true;
            }
        }
        return false;
    }

    @Override
    public Float getWeight(Integer id, Integer id2) {
        Set<Edge> edges = graph.getEdges();

        Vertex vertex = new Vertex(id);
        Vertex vertex2 = new Vertex(id2);

        return edges.stream()
                .filter(x -> x.getVertices1().equals(vertex) && x.getVertices2().equals(vertex2))
                .findFirst()
                .orElse(new Edge(new Vertex(0), new Vertex(0), 0F))
                .getWeight();
    }

    private void clearGraph() {
        graph.setVertices(new HashSet<>());
        graph.setEdges(new HashSet<>());
        logger.info("Graph cleared");
    }

    private boolean isCreatedFile(Path path) throws IOException {
        boolean exists = Files.exists(path);

        if (!exists) {
            Files.createFile(path);
            logger.info("File created");
            return true;
        } else logger.warning("The file already exist. Data will be rewrite");

        return false;
    }

    private List<String> getData(Path path) throws IOException {
        boolean exists = Files.exists(path);

        if (exists) {
            return Files.readAllLines(path);
        } else throw new FileNotFoundException(String.format("File %s is not found ", path.getFileName()));
    }

    public void toMatrix(Path path, boolean flag) throws IOException {
        String[][] matrix = new String[getCountVertices()][getCountVertices()];

        for (Edge edge : graph.getEdges()) {
            matrix[edge.getVertices1().getId() - 1][edge.getVertices2().getId() - 1] = String.valueOf(edge.getWeight());
        }

        for (int i = 0; i < matrix.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == null) {
                    matrix[i][j] = "0";
                }
                stringBuilder.append(String.format("%-10s", matrix[i][j]));
            }
            if (flag)
                Files.write(path, List.of(stringBuilder.toString()), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            else System.out.println(stringBuilder);
        }
    }

    /*
     * Вывести все трехвершинные полные подграфы графа.
     * То есть, если три вершины 1,5,8 образуют полный подграф, тройку <1,5,8>
     * */

    public List<List<Integer>> getThreeSubgraphsGraph() {
        int count = 0;
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 1; i < getCountVertices(); i++) {
            for (int j = i + 1; j < getCountVertices(); j++) {
                for (int k = j + 1; k <= getCountVertices(); k++) {
                    if (isAdjacency(i, j) && isAdjacency(j, k) && isAdjacency(i, k)) {
                        list.add(Arrays.asList(i, j, k));
                        count++;
                    }
                }
            }
        }
        list.add(Collections.singletonList(count));
        return list;
    }

    @Override
    public String toString() {
        return String.valueOf(graph);
    }
}
