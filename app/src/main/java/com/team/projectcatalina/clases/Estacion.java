package com.team.projectcatalina.clases;

public class Estacion {
    private String Name;
    private String Estado;

    public Estacion() {
        //constructor
    }

    public Estacion(String nombre, String estado) {
        this.Name = nombre;
        this.Estado = estado;
    }

    public String getName() {
        return Name;
    }
    public String getEstado() {
        return Estado;
    }
}


