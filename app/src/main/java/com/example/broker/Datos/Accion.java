package com.example.broker.Datos;

import java.util.HashMap;

public class Accion {

    private String nombre;
    private double valor;
    private int tendencia; //si es mayor que cero sube y si es menor que cero baja
    private double valorMaximo;
    private double valorMinimo;

    public Accion(String nombre, double valor){
        this.nombre = nombre;
        this.valor = valor;
        this.valorMaximo = valor;
        this.valorMinimo = valor;
        this.tendencia = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValor() {
        return valor;
    }

    public String getStringValor(){
        return String.valueOf(this.valor);
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getTendencia() {
        return tendencia;
    }

    public void setTendencia(int tendencia) {
        this.tendencia = tendencia;
    }

    public double getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(double valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public double getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }


    @Override
    public String toString() {
        return "Accion{" +
                "nombre='" + nombre + '\'' +
                ", valor=" + valor +
                ", tendencia=" + tendencia +
                ", valorMaximo=" + valorMaximo +
                ", valorMinimo=" + valorMinimo +
                '}';
    }
}
