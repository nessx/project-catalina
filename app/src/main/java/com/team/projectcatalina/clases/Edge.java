package com.team.projectcatalina.clases;

public class Edge {
    private double weight;
    private Vert start;
    private Vert end;

    public Edge(double weight, Vert start, Vert end) {
        this.weight = weight;
        this.start = start;
        this.end = end;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Vert getStart() {
        return start;
    }

    public void setStart(Vert start) {
        this.start = start;
    }

    public Vert getEnd() {
        return end;
    }

    public void setEnd(Vert end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "weight=" + weight +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}


