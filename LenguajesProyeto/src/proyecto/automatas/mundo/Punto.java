package proyecto.automatas.mundo;

/**
 *
 * @author Peke
 */
public class Punto {
    
    /**
     * Coordenada x en el plano
     */
    private int x;
    
    /**
     * Coordenada y en el plano
     */
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Constructor de la clase Punto
     */
    public Punto(){
        
    }
    
    /**
     * Constructor de la clase Punto
     * @param x Coordenada x en el plano
     * @param y Coordenada y en el plano
     */
    public Punto(int x, int y){
        this.x = x;
        this.y = y;
    }
    
}
