package utils;

import model.Item;
import model.Vertex;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class FileHandler {

    public static Map<String, String> extractFeatures(String filePath) throws FileNotFoundException {

        String line;
        Map<String, String> mapOfFeatures = new HashMap<>();
        String[] tempStrings;

        Scanner scanner = new Scanner(new File(filePath));

        line = scanner.nextLine();

        while (!line.contains("NODE")) {

            tempStrings = line.split(":");
            mapOfFeatures.put(tempStrings[0], tempStrings[1]);
            line = scanner.nextLine();
        }

        return mapOfFeatures;
    }

    public static List<Vertex> extractVertices(String filePath) throws FileNotFoundException{

        String line = "";
        StringTokenizer tokenizer;
        List <Vertex> listOfVertices = new ArrayList<>();

        Scanner scanner = new Scanner(new File(filePath));

        while (!line.contains("NODE")) {

            line = scanner.nextLine();
        }

        line = scanner.nextLine();

        while(!line.contains("ITEMS")){

            tokenizer = new StringTokenizer(line);

            listOfVertices.add(new Vertex(Integer.parseInt(tokenizer.nextToken()),
                    Float.parseFloat(tokenizer.nextToken()), Float.parseFloat(tokenizer.nextToken())));

            line = scanner.nextLine();
        }

        return listOfVertices;
    }

    public static Map<Integer, List<Item>> extractItems(String filePath) throws FileNotFoundException {

        String line = "";
        StringTokenizer tokenizer;
        int key;
        Map<Integer, List<Item>> mapOfItems = new HashMap<>();

        Scanner scanner = new Scanner(new File(filePath));

        while(!line.contains("ITEMS SECTION")){

            line = scanner.nextLine();
        }

        while(scanner.hasNextLine()){

            line = scanner.nextLine();
            tokenizer = new StringTokenizer(line);

            Item item = new Item(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()),
                    Integer.parseInt(tokenizer.nextToken()));

            List<Item> listOfItems = new ArrayList<>();
            listOfItems.add(item);

            key = Integer.parseInt(tokenizer.nextToken());

            if(!mapOfItems.containsKey(key)) {

                mapOfItems.put(key, listOfItems);
            }
            else{

                mapOfItems.get(key).add(item);
            }
        }

        return mapOfItems;
    }
}
