public class Vertex {

    private int id;
    private float xCoordinate;
    private float yCoordinate;

    public Vertex(int id, float xCoordinate, float yCoordinate){

        this.id = id;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getId() {
        return id;
    }

    public float getxCoordinate() {
        return xCoordinate;
    }

    public float getyCoordinate() {
        return yCoordinate;
    }

    @Override
    public String toString() {
        return getId() + "" /*+ getxCoordinate() + " " + getyCoordinate()*/;
    }
}
