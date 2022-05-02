package proyecto.automatas.mundo;

import proyecto.automatas.mundo.enums.Direccion;

/**
 *
 * @author Peke
 */
public class Transicion {

    /**
     * El nodo al cual hace referencia
     */
    private Nodo nodo;

    /**
     * El valor con el cual hace la transicion
     */
    private String valor;

    /**
     * Punto Inicial de la transicion
     */
    private Punto inicioTransicion;
    
    /**
     * Punto Final de la transicion
     */
    private Punto FinTransicion;

    /**
     * Direccion hacia donde apunta la transicion
     */
    private Direccion direccion;
    
    /**
     * Punto de la curva 
     */
    private Punto curva;
    
    public Punto getInicioTransicion() {
        return inicioTransicion;
    }

    public void setInicioTransicion(Punto inicioTransicion) {
        this.inicioTransicion = inicioTransicion;
    }

    public Punto getFinTransicion() {
        return FinTransicion;
    }

    public void setFinTransicion(Punto FinTransicion) {
        this.FinTransicion = FinTransicion;
    }
      
    public Nodo getNodo() {
        return nodo;
    }

    public void setNodo(Nodo nodo) {
        this.nodo = nodo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Punto getCurva() {
        return curva;
    }

    public void setCurva(Punto curva) {
        this.curva = curva;
    }
    
    /**
     * Constructor de la clase Transicion
     *
     * @param nodo El nodo hacia el cual apunta la transicion
     * @param valor El valor que lleva la transicion
     */
    public Transicion(Nodo nodo, String valor) {
        this.nodo = nodo;
        this.valor = valor;

    }
    
    public int distanciaX(){
        return this.getFinTransicion().getX() - this.getInicioTransicion().getX();
    }
    
    public int distanciaY(){
        return this.getFinTransicion().getX() - this.getInicioTransicion().getX();
    }
}
