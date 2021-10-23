import service.GraphService;
import service.GraphServiceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ApplicationStart {
    private static Scanner in = new Scanner(System.in).useDelimiter("\n");

    public static void main(String[] args) throws IOException {
        String menu = "Функционал:\n" +
                "1 - Создать пустой граф\n" +
                "2 - Загрузить из файла (матрица смежности)\n" +
                "3 - Загрузить из файла (списки ребер)\n" +
                "4 - Сохранить в файл (матрица смежности)\n" +
                "5 - Сохранить в файл (списки ребер)\n" +
                "6 - Добавить вершину\n" +
                "7 - Добавить ребро\n" +
                "8 - Удалить вершину\n" +
                "9 - Удалить ребро\n" +
                "10 - Количество вершин\n" +
                "11 - Количество ребер\n" +
                "12 - Проверить смежность\n" +
                "13 - Узнать вес ребра\n" +
                "14 - Трехвершинные полные подграфы графа\n" +
                "15 - Выйти\n";
        boolean flag = true;
        GraphService graphService = new GraphServiceImpl();
        while (flag) {
            System.out.println(menu);
            String nameOfFile;
            int id = 0;
            int id2 = 0;
            float weight = 0F;
            int option = inputInt();

            switch (option) {
                case 1:
                    graphService.createInstance();
                    break;
                case 2:
                    System.out.println("Загрузка графа из файла в виде матрицы смежности");
                    System.out.println("Введите название файла:");
                    nameOfFile = inputString();
                    try {
                        graphService.loadFromMatrix(nameOfFile);
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    break;
                case 3:
                    System.out.println("Загрузка графа из файла в виде списка ребер");
                    System.out.println("Введите название файла:");
                    nameOfFile = inputString();
                    try {
                        graphService.loadFromListEdges(nameOfFile);
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    break;
                case 4:
                    System.out.println("Сохранение графа в файл в виде матрицы смежности");
                    System.out.println("Введите название файла:");
                    nameOfFile = inputString();
                    graphService.saveAsMatrix(nameOfFile);
                    break;
                case 5:
                    System.out.println("Сохранение графа в файл в виде списка ребер");
                    System.out.println("Введите название файла:");
                    nameOfFile = inputString();
                    graphService.saveAsListEdges(nameOfFile);
                    break;
                case 6:
                    System.out.println("Введите id вершины:");
                    id = inputInt();
                    graphService.addVertex(id);
                    break;
                case 7:
                    System.out.println("Введите id вершины начала:");
                    id = inputInt();
                    System.out.println("Введите id вершины конца:");
                    id2 = inputInt();
                    System.out.println("Введите вес ребра");
                    weight = inputFloat();
                    graphService.addEdge(id, id2, weight);
                    break;
                case 8:
                    System.out.println("Введите id вершины:");
                    id = inputInt();
                    graphService.removeVertex(id);
                    break;
                case 9:
                    System.out.println("Введите id вершины начала:");
                    id = inputInt();
                    System.out.println("Введите id вершины конца:");
                    id2 = inputInt();
                    graphService.removeEdge(id, id2);
                    break;
                case 10:
                    System.out.println("Количество вершин графа: " + graphService.getCountVertices());
                    break;
                case 11:
                    System.out.println("Количество ребер графа: " + graphService.getCountEdges());
                    break;
                case 12:
                    System.out.println("Введите id первой вершины:");
                    id = inputInt();
                    System.out.println("Введите id второй вершины:");
                    id2 = inputInt();
                    graphService.isAdjacency(id, id2);
                    break;
                case 13:
                    System.out.println("Введите id вершины начала:");
                    id = inputInt();
                    System.out.println("Введите id вершины конца:");
                    id2 = inputInt();
                    graphService.getWeight(id, id2);
                    break;
                case 14:
                    List<List<Integer>> threeSubgraphsGraph = graphService.getThreeSubgraphsGraph();
                    System.out.println(report());
                    graphService.toMatrix(null, false);
                    System.out.println("Количество трехвершиных подграфов графа: " + threeSubgraphsGraph.get(threeSubgraphsGraph.size() - 1));
                    threeSubgraphsGraph.remove(threeSubgraphsGraph.size() - 1);
                    System.out.println(threeSubgraphsGraph);
                    break;
                case 15:
                    flag = false;
                    in.close();
                    break;
                default:
                    System.out.println("Неверный пункт меню\n");
                    break;
            }
        }
    }

    private static int inputInt() {
        int res = -1;
        boolean flag = true;
        do {
            if (in.hasNextInt()) {
                res = in.nextInt();
                flag = false;
            } else {
                in.nextLine();
                System.out.println("Требуется int значение, попробуйте снова");
            }
        } while (flag);
        return res;
    }

    private static float inputFloat() {
        float res = -1;
        boolean flag = true;
        do {
            if (in.hasNextFloat()) {
                res = in.nextFloat();
                flag = false;
            } else {
                in.nextLine();
                System.out.println("Требуется float значение, попробуйте снова");
            }
        } while (flag);
        return res;
    }

    private static String inputString() {
        String res = "";
        boolean flag = true;
        do {
            if (in.hasNext()) {
                res = in.next();
                flag = false;
            } else {
                in.nextLine();
                System.out.println("Требуется String значение, попробуйте снова");
            }
        } while (flag);
        return res;
    }

    private static String report() {
        StringBuilder stringBuilder = new StringBuilder();
        LocalDateTime time = LocalDateTime.now();
        stringBuilder
                .append("Результаты работы алгоритма «Вывести все трехвершинные полные подграфы графа.»\n")
                .append("Студент: Никитина Арина Сергеевна\n")
                .append("Группа: Б-ПИНФ-31\n")
                .append("Дата: " + time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm")));
        return String.valueOf(stringBuilder);
    }
}
