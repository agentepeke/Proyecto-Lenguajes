package proyecto.automatas.mundo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * clase contenedora de todos los nodos del sistema
 * @author Peke
 */
public class Grafo {
    
    /**
     * Lista que contiene todos los nodos del sistema
     */
    private Collection<Nodo> nodos;
    
    /**
     * Tabla de transicios del automata
     */
    private String[][] tablaTransiciones;
    
    public Collection<Nodo> getNodos(){
        return nodos;
    }

    public String[][] getTablaTransiciones() {
        return tablaTransiciones;
    }

    public void setTablaTransiciones(String[][] tablaTransiciones) {
        this.tablaTransiciones = tablaTransiciones;
    }
    
    /**
     * Constructor de incio del Grafo
     */
    public Grafo(){
        nodos = new ArrayList<>();
        tablaTransiciones = null;
    }
    
    /**
     * Permite adicionar Nodos al Grafo
     * @param nodo  
     */
    public void addNodo(Nodo nodo){
        try{
            nodos.add(nodo);
        }catch(Exception ex){
            throw ex;
        }
    }
    
    public void addEstadoTabla(String estado, int fila, int columna){
        try{
            tablaTransiciones[fila][columna] = estado;
        }catch(Exception ex){
            throw ex;
        }
    }
    
    public boolean existNodo(Nodo nuevo){
        Iterator<Nodo> it = nodos.iterator();
        while(it.hasNext()){
            Nodo guardado = it.next();
            if(guardado.getNombre().equals(nuevo.getNombre()))
                return true;
        }
        return false;
    }
    
    public Nodo returnNodo(Nodo nuevo){
        Iterator<Nodo> it = nodos.iterator();
        while(it.hasNext()){
            Nodo guardado = it.next();
            if(guardado.getNombre().equals(nuevo.getNombre()))
                return guardado;
        }
        return null;
    }
}