import java.util.*;

public class Population {

    private static final int SIZE_OF_POPULATION = 100;
    private List<Organism> listOfOrganisms;

    public Population(List<Organism> listOfOrganisms){

        this.listOfOrganisms = listOfOrganisms;
    }

    public List<Organism> getListOfOrganisms() {
        return listOfOrganisms;
    }

    public static Population createBasicPopulation(List<Vertex> listOfVertices, Map<Integer, List<Item>> mapOfItems,
                                                    int knapsackCapacity, AdjacencyMatrix adjacencyMatrix,
                                                   double minSpeed, double maxSpeed){

        List<Organism> newPopulationList = new ArrayList<>();

        for(int i = 0; i < SIZE_OF_POPULATION; i++){

            newPopulationList.add(Organism.createRandomOrganism(listOfVertices));
        }
        Population newPopulation = new Population(newPopulationList);
        newPopulation.initialize(mapOfItems, knapsackCapacity, adjacencyMatrix, minSpeed, maxSpeed);

        return newPopulation;
    }

    public List<Organism> tournament(int tournamentSize){

        List<Organism> tournamentWinners = new ArrayList<>();
        List<Organism> tempList;

        int listSize = listOfOrganisms.size();
        int i = 0;
        double maxOrganismQuality;
        double currentOrganismQuality;
        Organism tournamentWinner;

        while(i != listSize){

            tempList = new ArrayList<>(tournamentSize);

            for(int j = 0; j < tournamentSize; j++){

                tempList.add(listOfOrganisms.get(i + j));
            }

            i += tournamentSize;

            tournamentWinner = tempList.get(0);
            maxOrganismQuality = tournamentWinner.getQualityOfAnOrganism();


            for(int k = 1; k < tournamentSize; k++){

                currentOrganismQuality = tempList.get(k).getQualityOfAnOrganism();

                if (currentOrganismQuality > maxOrganismQuality){

                    maxOrganismQuality = currentOrganismQuality;
                    tournamentWinner = tempList.get(k);
                }
            }

            tournamentWinners.add(tournamentWinner);
        }

        return tournamentWinners;
    }

    private void initialize(Map<Integer, List<Item>> mapOfItems, int knapsackCapacity, AdjacencyMatrix adjacencyMatrix,
                           double minSpeed, double maxSpeed){

        for(Organism organism : listOfOrganisms){

            organism.initialize(mapOfItems, knapsackCapacity, adjacencyMatrix, minSpeed, maxSpeed);
        }
    }

    public Population crossover(Population oldPopulation, List<Organism> parents, double crossoverProbability, Map<Integer, List<Item>> mapOfItems,
                                int knapsackCapacity, AdjacencyMatrix adjacencyMatrix, double minSpeed, double maxSpeed){

        int parentsListSize = parents.size();
        int numberOfNewSuccessors = 0;
        Organism newSuccessor;
        Random random = new Random();
        List<Organism> oldPopulationList = oldPopulation.getListOfOrganisms();
        List<Organism> newGenerationList = new ArrayList<>();

        int i = 0;
        int j;

        while(numberOfNewSuccessors < SIZE_OF_POPULATION){

            j = random.nextInt(SIZE_OF_POPULATION);

            if(Math.random() < crossoverProbability && i != j){

                newSuccessor = createNewSuccessor(parents.get(i), oldPopulationList.get(j), mapOfItems, knapsackCapacity,
                            adjacencyMatrix, minSpeed, maxSpeed);

                newGenerationList.add(newSuccessor);
                numberOfNewSuccessors++;
            }

            i++;

            if(i == parentsListSize - 1){

                i = 0;
            }

        }
        return new Population(newGenerationList);
    }

    private Organism createNewSuccessor(Organism mother, Organism father, Map<Integer, List<Item>> mapOfItems,
                                       int knapsackCapacity, AdjacencyMatrix adjacencyMatrix, double minSpeed,
                                       double maxSpeed){

        int listSize = mother.getListOfVertices().size();
        Random random = new Random();
        int intersection = random.nextInt(listSize - 1);
        Organism newSuccessor;

        List<Vertex> newSuccessorList = new ArrayList<>();

        for(int i = 0; i < intersection; i++){

            newSuccessorList.add(mother.getListOfVertices().get(i));
        }
        for(int i = intersection; i < listSize; i++){

            newSuccessorList.add(father.getListOfVertices().get(i));
        }

        if(Population.isVerticesListValid(newSuccessorList)){

            newSuccessor = new Organism(newSuccessorList);
        }
        else{

            newSuccessor = new Organism(Population.repairOrganism(newSuccessorList, father));
        }

        newSuccessor.initialize(mapOfItems, knapsackCapacity, adjacencyMatrix, minSpeed, maxSpeed);

        return newSuccessor;
    }

    private static boolean isVerticesListValid(List<Vertex> listOfVertices){

        Set<Integer> helperSet = new HashSet<>();

        for(Vertex vertex : listOfVertices){

            if(!helperSet.add(vertex.getId())){

                return false;
            }
            else{

                helperSet.add(vertex.getId());
            }
        }

        return true;
    }

    private static List<Vertex> repairOrganism(List<Vertex> listOfVertices, Organism father){

        List<Vertex> missingVertices = new ArrayList<>();
        List<Vertex> firstOccurrences = new ArrayList<>();
        Map<Vertex, Integer> duplicatedVertices = new HashMap<>();

        for(Vertex vertex : listOfVertices) {

            if (firstOccurrences.contains(vertex)) {

                duplicatedVertices.put(vertex, listOfVertices.lastIndexOf(vertex));
            }
            else {

                firstOccurrences.add(vertex);
            }
        }

        for(Vertex vertex : father.getListOfVertices()){

            if(!duplicatedVertices.containsValue(vertex) && !firstOccurrences.contains(vertex)){

                missingVertices.add(vertex);
            }
        }

        Set<Vertex> keys = duplicatedVertices.keySet();

        int i = 0;

        for(Vertex vertex : keys){

            listOfVertices.set(duplicatedVertices.get(vertex), missingVertices.get(i));
            i++;
        }

        return listOfVertices;
    }

    public boolean mutation(double mutationProbability, Map<Integer, List<Item>> mapOfItems, int knapsackCapacity,
                         AdjacencyMatrix adjacencyMatrix, double minSpeed, double maxSpeed){

        Organism mutatedOrganism;
        boolean didAnyoneMutate = false;

        for(Organism organism : listOfOrganisms){

            if(Math.random() < mutationProbability){

                mutatedOrganism = organism.mutate();
                mutatedOrganism.initialize(mapOfItems, knapsackCapacity, adjacencyMatrix, minSpeed, maxSpeed);

                if(!listOfOrganisms.contains(organism)){

                    listOfOrganisms.set(listOfOrganisms.indexOf(organism), mutatedOrganism);
                    didAnyoneMutate = true;
                }
            }
        }

        return didAnyoneMutate;
    }

    public double getPopulationQuality(){

        double sum = 0.0;

        for(Organism organism : listOfOrganisms){

            sum += organism.getQualityOfAnOrganism();
        }

        return sum;
    }

    public double getBestQuality(){

        double bestOrganismQuality = listOfOrganisms.get(0).getQualityOfAnOrganism();
        double currentOrganismQuality;

        for (Organism organism : listOfOrganisms){

            currentOrganismQuality = organism.getQualityOfAnOrganism();

            if(currentOrganismQuality > bestOrganismQuality){

                bestOrganismQuality = currentOrganismQuality;
            }
        }

        return bestOrganismQuality;
    }

    public double getWorstQuality(){

        double worstOrganismQuality = listOfOrganisms.get(0).getQualityOfAnOrganism();
        double currentOrganismQuality;

        for (Organism organism : listOfOrganisms){

            currentOrganismQuality = organism.getQualityOfAnOrganism();

            if(currentOrganismQuality < worstOrganismQuality){

                worstOrganismQuality = currentOrganismQuality;
            }
        }

        return worstOrganismQuality;
    }

    public double getMediumQuality(){

        return getPopulationQuality() / SIZE_OF_POPULATION;
    }

    @Override
    public String toString(){

        StringBuilder populationToString = new StringBuilder();

        for (Organism listOfOrganism : listOfOrganisms) {

            populationToString.append(listOfOrganism);
            populationToString.append("\n");
        }

        return populationToString.toString();
    }
}
