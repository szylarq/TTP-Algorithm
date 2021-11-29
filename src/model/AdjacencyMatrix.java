package model;

import model.Vertex;
import utils.Utils;

import java.util.List;

public class AdjacencyMatrix {

    private double[][] adjacencyMatrix;

    public AdjacencyMatrix(List<Vertex> listOfVertices){

        int dimension = listOfVertices.size();
        adjacencyMatrix = new double[dimension][dimension];
        fill(listOfVertices);
    }

    private void fill(List<Vertex> listOfVertices){

        for (int i = 0; i < adjacencyMatrix.length; i++){
            for (int j = 0; j < adjacencyMatrix.length; j++) {

                adjacencyMatrix[i][j] = Utils.countDistance(listOfVertices.get(i), listOfVertices.get(j));
            }
        }
    }

    public double[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    @Override
    public String toString() {
        StringBuilder matrixToString = new StringBuilder();

        for (int i = 0; i < adjacencyMatrix.length; i++){
            for (int j = 0; j < adjacencyMatrix.length; j++) {

                matrixToString.append(adjacencyMatrix[i][j]);
                matrixToString.append(" ");
            }

            matrixToString.append("\n");
        }
        return matrixToString.toString();
    }
}
