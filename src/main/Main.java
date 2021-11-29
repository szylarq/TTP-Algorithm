package main;

import model.AdjacencyMatrix;
import model.Item;
import model.Population;
import model.Vertex;
import utils.FileHandler;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Main {

    private final static int NUMBER_OF_ALGORITHM_INVOCATIONS = 10;
    private final static int TOURNAMENT_SIZE = 1;
    private final static int MAX_GENERATION_LEVEL = 100;
    private final static double CROSSOVER_PROBABILITY = 0.7;
    private final static double MUTATION_PROBABILITY = 0.01;
    private final static String DEFAULT_DIRECTORY = "E:\\Documents\\Dokumenty\\Java Projekty\\TTP Algorithm\\data\\";

    public static void main(String [] args) throws FileNotFoundException {

        String fileReadExtension = ".ttp";
        String fileWriteExtension = ".csv";

        String[] fileNames = new String[17];
        fileNames[0] = "trivial_0";
        fileNames[1] = "trivial_1";
        fileNames[2] = "easy_0";
        fileNames[3] = "easy_1";
        fileNames[4] = "easy_2";
        fileNames[5] = "easy_3";
        fileNames[6] = "easy_4";
        fileNames[7] = "medium_0";
        fileNames[8] = "medium_1";
        fileNames[9] = "medium_2";
        fileNames[10] = "medium_3";
        fileNames[11] = "medium_4";
        fileNames[12] = "hard_0";
        fileNames[13] = "hard_1";
        fileNames[14] = "hard_2";
        fileNames[15] = "hard_3";
        fileNames[16] = "hard_4";

        String currentFileName = fileNames[1];

        String filePathRead = DEFAULT_DIRECTORY + currentFileName + fileReadExtension;
        String filePathWrite = DEFAULT_DIRECTORY + currentFileName + fileWriteExtension;

        List<Vertex> listOfVertices = FileHandler.extractVertices(filePathRead);
        AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(listOfVertices);
        Map<Integer, List<Item>> mapOfItems = FileHandler.extractItems(filePathRead);
        Map<String, String> mapOfFeatures = FileHandler.extractFeatures(filePathRead);

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(filePathWrite, true)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        int knapsackCapacity = Integer.parseInt(mapOfFeatures.get("CAPACITY OF KNAPSACK")
                .replaceAll("\\s",""));
        double minSpeed = Double.parseDouble(mapOfFeatures.get("MIN SPEED")
                .replaceAll("\\s",""));
        double maxSpeed = Double.parseDouble(mapOfFeatures.get("MAX SPEED")
                .replaceAll("\\s",""));

        for(int i = 0; i < NUMBER_OF_ALGORITHM_INVOCATIONS; i++) {

            Algorithm alg = new Algorithm();
            Population finalPopulation = alg.run(writer, TOURNAMENT_SIZE, listOfVertices, CROSSOVER_PROBABILITY, mapOfItems,
                    knapsackCapacity, adjacencyMatrix, minSpeed, maxSpeed, MUTATION_PROBABILITY, MAX_GENERATION_LEVEL);


//            System.out.println(finalPopulation);
        System.out.println(finalPopulation.getPopulationQuality());
//            System.out.println(finalPopulation.getListOfOrganisms().get(0).getQualityOfAnOrganism());
//            System.out.println(finalPopulation.getListOfOrganisms().get(0).getListOfItems());
//            System.out.println(main.Algorithm.countValuesOfAllItems(finalPopulation.getListOfOrganisms().get(0).getListOfItems()));


        }

        writer.close();
    }
}
