package main;

import model.AdjacencyMatrix;
import model.Item;
import model.Population;
import model.Vertex;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class Algorithm {

    public static Item getMostProfitableItem(/*int itemId, Map<Integer, */List<Item> listOfItems){

        double maxProfitability = 0.0;
        double currentProfitability;
        Item mostProfitableItem = null;

        for(Item item : listOfItems){

            currentProfitability = item.getProfitability();

           if(maxProfitability < currentProfitability) {

               maxProfitability = currentProfitability;
               mostProfitableItem = item;
           }
        }
        return mostProfitableItem;
    }

    public static double getActualSpeed(double minSpeed, double maxSpeed, int knapsackCapacity, int itemsActualWeight){

        return maxSpeed - (itemsActualWeight * (maxSpeed - minSpeed) / knapsackCapacity);
    }

    public static int countValuesOfAllItems(List<Item> listOfItems){

        int value = 0;

        for(Item item : listOfItems){

            if(item != null){

                value += item.getProfit();
            }
        }
        return value;
    }

    private static Population createNewGeneration(Population oldPopulation, int tournamentSize,
                                                  double crossoverProbability, Map<Integer, List<Item>> mapOfItems,
                                                  int knapsackCapacity, AdjacencyMatrix adjacencyMatrix,
                                                  double minSpeed, double maxSpeed, double mutationProbability){

        Population newGeneration = oldPopulation.crossover(oldPopulation, oldPopulation.tournament(tournamentSize),
                crossoverProbability, mapOfItems, knapsackCapacity, adjacencyMatrix, minSpeed, maxSpeed);

        newGeneration.mutation(mutationProbability, mapOfItems, knapsackCapacity, adjacencyMatrix, minSpeed, maxSpeed);

        return newGeneration;
    }

    public Population run(PrintWriter writer, int tournamentSize, List<Vertex> listOfVertices, double crossoverProbability,
                          Map<Integer, List<Item>> mapOfItems, int knapsackCapacity, AdjacencyMatrix adjacencyMatrix,
                          double minSpeed, double maxSpeed, double mutationProbability, int maxGenerationLevel){

        Population helperPopulation = Population.createBasicPopulation(listOfVertices, mapOfItems, knapsackCapacity,
                adjacencyMatrix, minSpeed, maxSpeed);

        writer.write(0 + ";");
        writer.write(helperPopulation.getBestQuality() + ";");
        writer.write(helperPopulation.getMediumQuality() + ";");
        writer.write(helperPopulation.getWorstQuality() + "\n");

        Population newGeneration;

        for(int i = 1; i < maxGenerationLevel; i++){

            newGeneration = createNewGeneration(helperPopulation, tournamentSize, crossoverProbability, mapOfItems,
                    knapsackCapacity, adjacencyMatrix, minSpeed, maxSpeed, mutationProbability);

            writer.write(i + ";");
            writer.write(helperPopulation.getBestQuality() + ";");
            writer.write(helperPopulation.getMediumQuality() + ";");
            writer.write(helperPopulation.getWorstQuality() + "\n");

            helperPopulation = newGeneration;
        }

        return helperPopulation;
    }
}
