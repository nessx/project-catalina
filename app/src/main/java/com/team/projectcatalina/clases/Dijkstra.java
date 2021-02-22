package com.team.projectcatalina.clases;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {

    public static void ShortestP(Vert vert){
        //Asigna la distáncia de la parada a la misma parada a 0
        vert.setDist(0);

        //Cola de nodos
        PriorityQueue<Vert> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(vert);

        //Asignamos la parada como visitada
        vert.setVisited(true);

        //Mientras haya paradas a analizar...
        while( !priorityQueue.isEmpty() ){
            //Cogemos la primera parada de la lista
            Vert actualVertex = priorityQueue.poll();

            //Para cada camino que tenga la parada
            for(Edge edge : actualVertex.getList()){

                Log.i("logTest", edge.getStart().getName() + " - " + edge.getEnd().getName());
                Vert v = edge.getEnd();

                if(!v.Visited()){
                    double newDistance = actualVertex.getDist() + edge.getWeight();
                    if( newDistance < v.getDist() ){
                        priorityQueue.remove(v);
                        v.setDist(newDistance);
                        v.setPr(actualVertex);
                        priorityQueue.add(v);
                    }
                }
            }
            Log.i("logTest", "---");
            actualVertex.setVisited(true);
        }
    }

    public static ArrayList<Vert> getShortestP(Vert end){
        ArrayList<Vert> path = new ArrayList<>();
        for(Vert vertex = end; vertex!=null; vertex=vertex.getPr()){
            path.add(vertex);
        }

        Collections.reverse(path);
        return path;
    }

}