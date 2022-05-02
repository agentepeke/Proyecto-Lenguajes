
package proyecto.automatas.clases;
import proyecto.automatas.mundo.Grafo;
import proyecto.automatas.mundo.Nodo;
import proyecto.automatas.mundo.Transicion;
import proyecto.automatas.vista.PanelDibujo;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
//import sun.swing.BakedArrayList;

/**
 *
 * @author Peke
 */
public class Validacion {

    private Grafo grafo;
    private char[] expresion;
    private PanelDibujo p;
    private ArrayList frontera;

    public Validacion(Grafo grafo, String expresion, PanelDibujo p) {
        this.grafo = grafo;
        this.expresion = new char[expresion.length()];
        this.p = p;
        for (int i = 0; i < expresion.length(); i++) {
            this.expresion[i] = expresion.charAt(i);
        }
        frontera = new ArrayList();
    }

    public boolean backtracking(char[] solucion, int pos, Nodo nodo) throws InterruptedException {
        boolean exito = false;
        Iterator<Transicion> i = nodo.getTransiciones().iterator();
        do {
            if (i.hasNext()) {
                Transicion t = i.next();
                solucion[pos] = t.getValor().toCharArray()[0];
                if (valido(solucion, pos)) {
                    //frontera.add(nodo);
                    pintarNodo(t.getNodo());
                    if (pos != expresion.length - 1) {
                        exito = backtracking(solucion, pos + 1, t.getNodo());
                    } else {
                        p.rellenarNodo(p.getGraphics(), t.getNodo(), Color.RED);
                        p.pintarEstado(p.getGraphics(), t.getNodo());
                        if (t.getNodo().isEstadoFin()) {
                            exito = true;
                        }/*else{
                         if(frontera.size() > 0 && !frontera.get(frontera.size() - 1).equals(nodo)){
                         exito = backtracking(solucion, pos, (Nodo)frontera.remove(frontera.size() - 1));
                         }
                         }*/

                    }
                }
            }
        } while (!exito && i.hasNext());
        return exito;
    }

    private boolean valido(char[] solucion, int k) {
        for (int i = 0; i <= k; i++) {
            if (solucion[i] != expresion[i]) {
                return false;
            }
        }
        return true;
    }

    private void pintarNodo(Nodo nodo) {
        for (int i = 0; i < 2; i++) {
            try {
                // SI el numero es par se pinta el nodo de rojo
                if (i % 2 == 0) {
                    p.rellenarNodo(p.getGraphics(), nodo, Color.RED);
                } else {
                    p.rellenarNodo(p.getGraphics(), nodo, Color.WHITE);
                    p.pintarEstado(p.getGraphics(), nodo);
                }
                Thread.sleep(500);
            } catch (InterruptedException ex) {

            }
        }
    }
}
