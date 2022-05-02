package proyecto.automatas.vista;

import proyecto.automatas.mundo.Grafo;
import proyecto.automatas.mundo.Nodo;
import proyecto.automatas.mundo.Punto;
import proyecto.automatas.mundo.Transicion;
import proyecto.automatas.mundo.enums.Direccion;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.JPanel;

/**
 *
 * @author Peke
 */
public class PanelDibujo extends JPanel {

    private static final int TAMANOCIRCULO = 6;
    private static final int TAMANOCIRCULOINTERNO = 5;
    private Grafo grafo;
    private Graphics2D g2;
    private Graphics g;
    private ArrayList<Punto> puntosCaracter;
    
    public Grafo getGrafo() {
        return grafo;
    }

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
    } 

    public ArrayList<Punto> getPuntosCaracter() {
        return puntosCaracter;
    }

    public void setPuntosCaracter(ArrayList<Punto> puntosCaracter) {
        this.puntosCaracter = puntosCaracter;
    }
   
    public PanelDibujo() {
        puntosCaracter = new ArrayList();
        grafo = null;
    }
    
    public PanelDibujo(Grafo grafo){
        this.grafo = grafo;
        puntosCaracter = new ArrayList();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;
        this.g2 = (Graphics2D)g;
        if(grafo != null){
            puntosCaracter.clear();
            pintarEstados();
            pintarTransiciones();
        }
    }

    public void pintarEstados() {
        int xIni = 10;
        int yIni = this.getHeight() / 2;
        int porcentaje = (this.getWidth() * (TAMANOCIRCULO + 5)) / 100;
        Iterator<Nodo> tr = grafo.getNodos().iterator();
        while (tr.hasNext()) {
            Nodo nodo = tr.next();
            nodo.setCentro(new Punto(xIni, yIni));
            xIni += porcentaje;
            pintarEstado(g, nodo);
        }
    }

    public void pintarEstado(Graphics g, Nodo nodo) {
        int porcentaje = (this.getWidth() * TAMANOCIRCULO) / 100;
        g.drawOval(nodo.getCentro().getX(), nodo.getCentro().getY(), porcentaje, porcentaje);
        nodo.setWidth(porcentaje);
        nodo.setHeight(porcentaje);
        if (nodo.isEstadoFin()) {
            int porcentaje2 = (this.getWidth() * TAMANOCIRCULOINTERNO) / 100;
            int diferencia = (porcentaje - porcentaje2) / 2;
            g.drawOval(nodo.getCentro().getX() + diferencia,
                    nodo.getCentro().getY() + diferencia, porcentaje2, porcentaje2);
        }
        int porcentajeFUente = (this.getWidth() * 1) / 100;
        Font f = new Font("Monospaced", Font.ITALIC, porcentajeFUente);
        g.setFont(f);
        g.drawString(nodo.getNombre(), nodo.getCentro().getX() + porcentaje / 2,
                nodo.getCentro().getY() + porcentaje / 2);
    }

    public void pintarTransiciones() {
        Collection<Transicion> transiciones = new ArrayList<>();
        for (Nodo nodo : grafo.getNodos()) {
            transiciones = nodo.getTransiciones();
            for (Transicion transicion : transiciones) {
                Nodo nodoDestino = transicion.getNodo();
                g2.setColor(Color.black);
                pintarTransicion(nodo, nodoDestino, g2, transicion);
                g2.setColor(Color.red);
                pintarDireccion(g2, transicion);
                g2.setColor(Color.blue);
                pintarCaracter(g2, transicion);
            }
        }
    }

    public void pintarTransicion(Nodo nodoSalida, Nodo nodoDestino, Graphics2D g2, Transicion transicion) {
        // create new QuadCurve2D.Float     
        QuadCurve2D q = new QuadCurve2D.Float();
        //int difX = transicion.distanciaX() / 2;
        int iniX = 0;
        int finX = 0;
        int iniY = 0;
        int finY = 0;
        int curvaY = 0;
        int curvaX = 0;

        transicion.setDireccion(identificarDireccion(nodoSalida, nodoDestino));
        switch (transicion.getDireccion()) {
            case arriba:
                break;
            case abajo:
                break;
            case derecha:
                iniX = nodoSalida.getCentro().getX() + (((nodoSalida.getCentro().getX() + nodoSalida.getWidth()) - nodoSalida.getCentro().getX()) / 2);
                finX = nodoDestino.getCentro().getX() + (((nodoDestino.getCentro().getX() + nodoDestino.getWidth()) - nodoDestino.getCentro().getX()) / 2);
                iniY = nodoSalida.getCentro().getY();
                finY = nodoDestino.getCentro().getY();
                curvaX = iniX + ((finX - iniX) / 2);
                //curvaY = nodoSalida.getCentro().getY() - 75;
                curvaY = nodoSalida.getCentro().getY() - (nodoSalida.getCentro().getY() * 20) / 100;
                break;
            case izquierda:
                iniX = nodoSalida.getCentro().getX() + (((nodoSalida.getCentro().getX() + nodoSalida.getWidth()) - nodoSalida.getCentro().getX()) / 2);
                finX = nodoDestino.getCentro().getX() + (((nodoDestino.getCentro().getX() + nodoDestino.getWidth()) - nodoDestino.getCentro().getX()) / 2);
                iniY = nodoSalida.getCentro().getY() + nodoSalida.getHeight();
                finY = nodoDestino.getCentro().getY() + nodoDestino.getHeight();
                curvaX = iniX + ((finX - iniX) / 2);
                //curvaY = nodoSalida.getCentro().getY() + nodoSalida.getHeight() + 75;
                curvaY = nodoSalida.getCentro().getY() + nodoSalida.getHeight() + (nodoSalida.getCentro().getY() * 20) / 100;
                break;
            case siMismoX:
                iniX = nodoSalida.getCentro().getX() + nodoSalida.getWidth();
                finX = nodoSalida.getCentro().getX();
                iniY = nodoSalida.getCentro().getY() + (nodoSalida.getHeight() / 2);
                finY = nodoSalida.getCentro().getY() + (nodoSalida.getHeight() / 2);
                curvaX = iniX + ((finX - iniX) / 2);
                //curvaY = nodoSalida.getCentro().getY() + nodoSalida.getHeight() - 200;
                curvaY = nodoSalida.getCentro().getY() + nodoSalida.getHeight() - (nodoSalida.getCentro().getY() * 60) / 100;
                break;
            case siMismoY:
                break;
        }
        transicion.setInicioTransicion(new Punto(iniX, iniY));
        transicion.setFinTransicion(new Punto(finX, finY));
        transicion.setCurva(new Punto(curvaX, curvaY));

        q.setCurve(transicion.getInicioTransicion().getX(),
                transicion.getInicioTransicion().getY(),
                transicion.getCurva().getX(),
                transicion.getCurva().getY(),
                transicion.getFinTransicion().getX(),
                transicion.getFinTransicion().getY());
        g2.draw(q);
    }

    private Direccion identificarDireccion(Nodo nodoSalida, Nodo nodoDestino) {
        Direccion dirRetorno;
        if (nodoSalida.getCentro().getY() == nodoDestino.getCentro().getY()) {
            // Se nueve en el eje X
            if (nodoDestino.getCentro().getX() > nodoSalida.getCentro().getX()) {
                dirRetorno = Direccion.derecha;
            } else if (nodoDestino.getCentro().getX() < nodoSalida.getCentro().getX()) {
                dirRetorno = Direccion.izquierda;
            } else {
                dirRetorno = Direccion.siMismoX;
            }
        } else {
            //Se mueve en el eje Y
            if (nodoDestino.getCentro().getY() < nodoSalida.getCentro().getY()) {
                dirRetorno = Direccion.abajo;
            } else if (nodoDestino.getCentro().getY() > nodoSalida.getCentro().getY()) {
                dirRetorno = Direccion.arriba;
            } else {
                dirRetorno = Direccion.siMismoY;
            }
        }
        return dirRetorno;
    }

    private void pintarDireccion(Graphics2D g, Transicion transicion) {
        switch (transicion.getDireccion()) {
            case derecha:
                g.drawLine(transicion.getFinTransicion().getX(), transicion.getFinTransicion().getY(),
                        //transicion.getFinTransicion().getX() - 30, transicion.getFinTransicion().getY());
                        transicion.getFinTransicion().getX() - ((transicion.distanciaX() * 7) / 100), transicion.getFinTransicion().getY());
                g.drawLine(transicion.getFinTransicion().getX(), transicion.getFinTransicion().getY(),
                        transicion.getFinTransicion().getX() - ((transicion.distanciaX() * 3) / 100), transicion.getFinTransicion().getY() - ((transicion.distanciaX() * 5) / 100));
                break;
            case izquierda:
                g.drawLine(transicion.getFinTransicion().getX(), transicion.getFinTransicion().getY(),
                        //transicion.getFinTransicion().getX() + 30, transicion.getFinTransicion().getY());
                        transicion.getFinTransicion().getX() + ((transicion.distanciaX() * 7) / 100) * -1, transicion.getFinTransicion().getY());
                g.drawLine(transicion.getFinTransicion().getX(), transicion.getFinTransicion().getY(),
                        //transicion.getFinTransicion().getX() + 10, transicion.getFinTransicion().getY() + 15);
                        transicion.getFinTransicion().getX() + ((transicion.distanciaX() * 3) / 100) * -1, transicion.getFinTransicion().getY() + ((transicion.distanciaX() * 5) / 100) * -1);
                break;
            case siMismoX:
                g.drawLine(transicion.getFinTransicion().getX(), transicion.getFinTransicion().getY(),
                        transicion.getFinTransicion().getX() - 5, transicion.getFinTransicion().getY() - 30);
                g.drawLine(transicion.getFinTransicion().getX(), transicion.getFinTransicion().getY(),
                        transicion.getFinTransicion().getX() + 20, transicion.getFinTransicion().getY() - 30);
                break;
        }

    }

    private void pintarCaracter(Graphics2D g, Transicion transicion) {
        int pointX = 0;
        int pointY = 0;
        switch (transicion.getDireccion()) {
            case derecha:
                pointX = transicion.getCurva().getX();
                pointY = transicion.getCurva().getY() + (((transicion.getFinTransicion().getY() - transicion.getCurva().getY()) * 30) / 100);
                break;
            case izquierda:
                pointX = transicion.getCurva().getX();
                pointY = transicion.getCurva().getY() - (((transicion.getCurva().getY() - transicion.getFinTransicion().getY()) * 15) / 100);
                break;
            case siMismoX:
                pointX = transicion.getCurva().getX();
                pointY = transicion.getCurva().getY() + (((transicion.getFinTransicion().getY() - transicion.getCurva().getY()) * 40) / 100);
            case arriba:
                break;
            case abajo:
                break;
            case siMismoY:
                break;
        }
        while(existPunto(new Punto(pointX, pointY))){
            pointX = pointX + 10;
        }
        g.drawString(transicion.getValor(), pointX, pointY);
        puntosCaracter.add(new Punto(pointX, pointY));
    }
    
    private boolean existPunto(Punto punto){
        for(Punto point : puntosCaracter){
            if(point.getX() == punto.getX() && point.getY() == point.getY())
                return true;
        }
        return false;
    }

    public void rellenarNodo(Graphics g, Nodo nodo, Color color){
        g.setColor(color);
        g.fillOval(nodo.getCentro().getX(), nodo.getCentro().getY(), nodo.getWidth(), nodo.getHeight());
    }
}
