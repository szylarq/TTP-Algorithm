package utils;

import model.*;

public class Utils {

    public static double countDistance(Vertex oneVertex, Vertex otherVertex){

        return Math.sqrt(Math.pow(oneVertex.getxCoordinate() - otherVertex.getxCoordinate(), 2) +
                Math.pow(oneVertex.getyCoordinate() - otherVertex.getyCoordinate(), 2));
    }
}
