/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.automatas.mundo;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Peke
 */
public class Nodo {

    /**
     * Estado del Nodo, Indica si es de finalización o no
     */
    private boolean estadoFin;

    /**
     * Array con todos los hijos del
     */
    private Collection<Transicion> transiciones;

    /**
     * Nombre del Nodo
     */
    private String nombre;

    /**
     * Centro del circulo para graficar el nodo
     */
    private Punto centro;

    /**
     * Ancho del circulo
     */
    private int width;

    /**
     * ALto del Circulo
     */
    private int height;

    public boolean isEstadoFin() {
        return estadoFin;
    }

    public void setEstadoFin(boolean estadoFin) {
        this.estadoFin = estadoFin;
    }

    public Collection<Transicion> getTransiciones() {
        return transiciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Punto getCentro() {
        return centro;
    }

    public void setCentro(Punto centro) {
        this.centro = centro;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Constructor vacio de Nodo
     *
     * @param nombre Nombre del nodo
     */
    public Nodo(String nombre) {
        this.nombre = nombre;
        estadoFin = false;
        transiciones = new ArrayList<>();
    }

    /**
     * Constructor Con estado Inicial
     *
     * @param nombre Nombre del Nodo
     * @param estadoFIn Estado Inicial del Nodo
     */
    public Nodo(String nombre, boolean estadoFIn) {
        this.nombre = nombre;
        this.estadoFin = estadoFIn;
        transiciones = new ArrayList<>();
    }

    /**
     * Permite agregar Transiciones al Nodo
     *
     * @param transicion Transicion que se agrega al nodo
     */
    public void addTransicion(Transicion transicion) {
        try {
            transiciones.add(transicion);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public int distanciaX(Nodo comparar) {
        return comparar.getCentro().getX() - this.getCentro().getX();
    }

    public int distanciaY(Nodo comparar) {
        return comparar.getCentro().getY() - this.getCentro().getY();
    }
    
    public boolean existTransition(Transicion t){
        for(Transicion transicion : transiciones){
            if(t.getValor().equals(transicion.getValor())) {
                if(t.getNodo().getNombre().equals(transicion.getNodo().getNombre())){
                    return true;
                }
            }
        }
        return false;
    }

}
