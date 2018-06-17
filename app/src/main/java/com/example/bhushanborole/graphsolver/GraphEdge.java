package com.example.bhushanborole.graphsolver;

public class GraphEdge {

    private Integer startNode;
    private Integer endNode;
    private Integer weight;

    public GraphEdge(Integer startNode, Integer endNode, Integer weight){

        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;

    }


    public int getStartNode() {
        return startNode;
    }

    public int getEndNode() {
        return endNode;
    }

    public int getWeight() {
        return weight;
    }
}
