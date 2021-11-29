import java.util.*;

public class Organism {

    private List<Vertex> listOfVertices;
    private List<Item> listOfItems = new ArrayList<>();
    private double qualityOfAnOrganism;

    public Organism(List<Vertex> listOfVertices){

        this.listOfVertices = listOfVertices;
    }

    public List<Vertex> getListOfVertices() {
        return listOfVertices;
    }

    public List<Item> getListOfItems() {
        return listOfItems;
    }

    public double getQualityOfAnOrganism() {
        return qualityOfAnOrganism;
    }

    public static Organism createRandomOrganism(List<Vertex> listOfVertices){

        List <Vertex> tempList = new ArrayList<>(listOfVertices);
        Collections.shuffle(tempList);

        return new Organism(tempList);
    }

    private void countQuality(AdjacencyMatrix adjacencyMatrix, int knapsackCapacity, double minSpeed, double maxSpeed){

        int itemsActualWeight = 0;
        Item currentItem;
        int listSize = listOfVertices.size();

        for(int i = 0; i < listSize - 1; i++){

            currentItem = listOfItems.get(i);

            if(currentItem != null){

                itemsActualWeight += currentItem.getWeight();
            }

            qualityOfAnOrganism -= (adjacencyMatrix.getAdjacencyMatrix()
                    [listOfVertices.get(i).getId() - 1][listOfVertices.get(i + 1).getId() - 1])
                            / Algorithm.getActualSpeed(minSpeed, maxSpeed, knapsackCapacity, itemsActualWeight);
        }

        qualityOfAnOrganism -= adjacencyMatrix.getAdjacencyMatrix()
                [listOfVertices.get(listSize - 1).getId() - 1][listOfVertices.get(0).getId() - 1]
                        / Algorithm.getActualSpeed(minSpeed, maxSpeed, knapsackCapacity, itemsActualWeight);

        qualityOfAnOrganism += Algorithm.countValuesOfAllItems(listOfItems);
    }

    private void createListOfItems(Map<Integer, List<Item>> mapOfItems, int knapsackCapacity){

        int knapsackFreeSpace = knapsackCapacity;
        Item mostProfitableItem;
        int mostProfitableItemWeight;
        int vertexId;

        for(Vertex vertex : listOfVertices){

            vertexId = vertex.getId();

            if(vertexId != 1) {
                mostProfitableItem = Algorithm.getMostProfitableItem(mapOfItems.get(vertexId));
                mostProfitableItemWeight = mostProfitableItem.getWeight();

                if (knapsackFreeSpace >= mostProfitableItemWeight) {

                    listOfItems.add(mostProfitableItem);
                    knapsackFreeSpace -= mostProfitableItemWeight;
                } else {

                    listOfItems.add(null);
                }
            }
            else{

                listOfItems.add(null);
            }
        }
    }

    public void initialize(Map<Integer, List<Item>> mapOfItems, int knapsackCapacity, AdjacencyMatrix adjacencyMatrix,
                           double minSpeed, double maxSpeed){

        createListOfItems(mapOfItems, knapsackCapacity);
        countQuality(adjacencyMatrix, knapsackCapacity, minSpeed, maxSpeed);
    }

    public Organism mutate(){

        Random random = new Random();
        List<Vertex> mutatingOrganismList = listOfVertices;
        int listSize = mutatingOrganismList.size();
        int firstVertexIndex = random.nextInt(listSize);
        int secondVertexIndex = firstVertexIndex;

        while(secondVertexIndex == firstVertexIndex){

            secondVertexIndex = random.nextInt(listSize);
        }
        Vertex helperVertex = mutatingOrganismList.get(firstVertexIndex);

        mutatingOrganismList.set(firstVertexIndex, mutatingOrganismList.get(secondVertexIndex));
        mutatingOrganismList.set(secondVertexIndex, helperVertex);

        return new Organism(mutatingOrganismList);
    }

    @Override
    public String toString() {
        return getListOfVertices().toString();
    }
}
