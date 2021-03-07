package com.team.projectcatalina.clases;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;


public class Vert implements Comparable<Vert>, Serializable {

    private boolean visited;
    private String name;
    private ArrayList<Edge> List;
    private double dist = Double.MAX_VALUE;
    private Vert pr;

    //Nombre para la parada
    public Vert(String name) {
        this.name = name;
        this.List = new ArrayList<>();
    }

    //Lista de paradas adyadences
    public ArrayList<Edge> getList() {
        return List;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setList(ArrayList<Edge> List) {
        this.List = List;
    }

    public void addNeighbour(Edge edge) {
        this.List.add(edge);
    }

    public boolean Visited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    //Pr parada predecesora
    public Vert getPr() {
        return pr;
    }

    public void setPr(Vert pr) {
        this.pr = pr;
    }

    //Distància entre paradas
    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Vert otherV) {
        return Double.compare(this.dist, otherV.getDist());
    }

    public static void showverts(ArrayList<Vert> lista){
        for (int i=0;i<lista.size();i++){
            lista.get(i).toString();
            Log.i("logTest","log de vert "+ lista.get(i).toString());
        }
    }

    public void showwdges(){
        for (int i=0;i<getList().size();i++){
            //getList().get(i);
            Log.i("logTest","log de edge "+ getList().get(i).toString());
        }
    }
}
