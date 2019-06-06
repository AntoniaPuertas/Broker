package com.example.broker.Datos;

import java.util.HashMap;

public class Jugador {

    private String nombre;
    private double  capital;
    private int numeroAcciones;

    public Jugador(String nombre, double capital){
        this.nombre = nombre;
        this.capital = capital;
        this.numeroAcciones = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public int getNumeroAcciones() {
        return numeroAcciones;
    }

    public void setNumeroAcciones(int numeroAcciones) {
        this.numeroAcciones = numeroAcciones;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                ", capital=" + capital +
                '}';
    }
}
