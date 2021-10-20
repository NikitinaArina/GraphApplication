import service.GraphService;
import service.GraphServiceImpl;

import java.io.IOException;
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
            int option = in.nextInt();

            switch (option) {
                case 1:
                    graphService.createInstance();
                    break;
                case 2:
                    System.out.println("Загрузка графа из файла в виде матрицы смежности");
                    System.out.println("Введите название файла:");
                    nameOfFile = in.next();
                    graphService.loadFromMatrix(nameOfFile);
                    break;
                case 3:
                    System.out.println("Загрузка графа из файла в виде списка ребер");
                    System.out.println("Введите название файла:");
                    nameOfFile = in.next();
                    graphService.loadFromListEdges(nameOfFile);
                    break;
                case 4:
                    System.out.println("Сохранение графа в файл в виде матрицы смежности");
                    System.out.println("Введите название файла:");
                    nameOfFile = in.next();
                    graphService.saveAsMatrix(nameOfFile);
                    break;
                case 5:
                    System.out.println("Сохранение графа в файл в виде списка ребер");
                    System.out.println("Введите название файла:");
                    nameOfFile = in.next();
                    graphService.saveAsListEdges(nameOfFile);
                    break;
                case 6:
                    System.out.println("Введите id вершины:");
                    id = in.nextInt();
                    graphService.addVertex(id);
                    break;
                case 7:
                    System.out.println("Введите id вершины начала:");
                    id = in.nextInt();
                    System.out.println("Введите id вершины конца:");
                    id2 = in.nextInt();
                    System.out.println("Введите вес ребра");
                    weight = in.nextFloat();
                    graphService.addEdge(id, id2, weight);
                    break;
                case 8:
                    System.out.println("Введите id вершины:");
                    id = in.nextInt();
                    graphService.removeVertex(id);
                    break;
                case 9:
                    System.out.println("Введите id вершины начала:");
                    id = in.nextInt();
                    System.out.println("Введите id вершины конца:");
                    id2 = in.nextInt();
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
                    id = in.nextInt();
                    System.out.println("Введите id второй вершины:");
                    id2 = in.nextInt();
                    graphService.isAdjacency(id, id2);
                    break;
                case 13:
                    System.out.println("Введите id вершины начала:");
                    id = in.nextInt();
                    System.out.println("Введите id вершины конца:");
                    id2 = in.nextInt();
                    graphService.getWeight(id, id2);
                    break;
                case 14:
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
}
