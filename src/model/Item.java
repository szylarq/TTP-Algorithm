package model;

public class Item {

    private int id;
    private int profit;
    private int weight;
    private double profitability;

    public Item (int id, int profit, int weight){

        this.id = id;
        this.profit = profit;
        this.weight = weight;

        profitability = (double)profit/weight;
    }

    public int getId() {
        return id;
    }

    public int getProfit() {
        return profit;
    }

    public int getWeight() {
        return weight;
    }

    public double getProfitability() {
        return profitability;
    }

    @Override
    public String toString() {
        return getId() + "";
    }
}
