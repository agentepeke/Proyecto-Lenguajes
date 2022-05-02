
package proyecto.automatas.vista;

import proyecto.automatas.clases.CargarDatos;
import proyecto.automatas.clases.Validacion;
import proyecto.automatas.mundo.Grafo;
import proyecto.automatas.mundo.Nodo;
import proyecto.automatas.mundo.Transicion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Peke
 */
public class VentanaInicio extends JFrame implements ActionListener {

    private JPanel panelSuperior;
    public static PanelDibujo panelMedio;
    public JTextField txtExpresion;
    public JButton btnValidar;
    private JPanel panelInferior;
    private Grafo grafo;
    private JPanel panelCargaDatos;
    private String alfabeto;
    private JButton btnGrabarNodo;
    private JComboBox listaEstadosInicio;
    private JComboBox listaEstadosFin;
    private JComboBox listaTransiciones;
    private JTextField txtNodo;
    private JButton btnGuardar;
    private JCheckBox chIsFinal;

    public JTextField getTxtExpresion() {
        return txtExpresion;
    }

    public void setTxtExpresion(JTextField txtExpresion) {
        this.txtExpresion = txtExpresion;
    }

    public String getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(String alfabeto) {
        this.alfabeto = alfabeto;
    }

    public VentanaInicio(String alfabeto) {
        this.alfabeto = alfabeto;
        this.grafo = new Grafo();
        initComponent();
    }

    private void initComponent() {
        construirVentana();
        construirPaneles();
        this.add(panelSuperior);
        this.add(panelMedio);
        this.add(panelInferior);
    }

    private void panelSuperior() {
        panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());
        panelSuperior.setPreferredSize(new Dimension(this.getWidth(), (this.getHeight() * 5) / 100));
    }

    private void panelMedio() {
        panelMedio = new PanelDibujo();
        panelMedio.setLayout(null);
        panelMedio.setPreferredSize(new Dimension(this.getWidth(), (this.getHeight() * 80) / 100));
        panelMedio.setBackground(Color.WHITE);
    }

    private void panelInferior() {
        panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(1, 2));
        panelInferior.setPreferredSize(new Dimension(this.getWidth(), (this.getHeight() * 15) / 100));
        panelInferior.add(panelDatos());
        panelInferior.add(panelCargaDatos);
    }

    private void construirVentana() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setSize(tk.getScreenSize());
        this.setTitle("Automatas Version FMLC");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
    }

    private void construirPaneles() {
        panelSuperior();
        panelMedio();
        panelCargaDatos();
        panelInferior();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnValidar) {
            if (!this.txtExpresion.getText().equals("")) {
                btnValidar.setEnabled(false);
                String cadena = this.txtExpresion.getText();
                Validacion validacion = new Validacion(grafo, cadena, this.panelMedio);
                char[] solucion = new char[cadena.length()];
                for (int i = 0; i < solucion.length; i++) {
                    solucion[i] = ' ';
                }
                int pos = 0;
                Iterator<Nodo> i = grafo.getNodos().iterator();
                boolean exito = false;
                try {
                    exito = validacion.backtracking(solucion, pos, i.next());
                } catch (InterruptedException ex) {
                    Logger.getLogger(VentanaInicio.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.btnValidar.setEnabled(true);
                if (exito) {
                    JOptionPane.showMessageDialog(null, "La expresion es valida");
                } else {
                    JOptionPane.showMessageDialog(null, "La expresion no es valida");
                }
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese la expresion a validar");
            }
        } else if (e.getSource() == btnGuardar) {
            agregarTransicion();
            panelMedio.setGrafo(grafo);
            panelMedio.update(panelMedio.getGraphics());
        } else if (e.getSource() == btnGrabarNodo) {
            agregarNodo();
            panelMedio.setGrafo(grafo);
            panelMedio.update(panelMedio.getGraphics());
            actualizarListas();
       }
    }

    private void agregarNodo() {
        String nomNodo = txtNodo.getText();
        Nodo nodo = new Nodo(nomNodo);
        nodo.setEstadoFin(chIsFinal.isSelected());
        if (!grafo.existNodo(nodo)) {
            if(!nomNodo.equals("")){
                grafo.addNodo(nodo);
            }else{
               JOptionPane.showMessageDialog(null, "Ingrese un nombre para el estado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "El estado ingresado ya existe");
        }
    }

    private void agregarTransicion() {
        if (!grafo.getNodos().isEmpty()) {
            Nodo nodoInicio = grafo.returnNodo(new Nodo(listaEstadosInicio.getSelectedItem().toString()));
            Nodo nodoFin = grafo.returnNodo(new Nodo(listaEstadosFin.getSelectedItem().toString()));
            Transicion t = new Transicion(nodoFin, listaTransiciones.getSelectedItem().toString());
            if(!nodoInicio.existTransition(t)){
                nodoInicio.addTransicion(t);
            }else{
                JOptionPane.showMessageDialog(null, "La transicion ingresada ya existe");
            }
        }else{
            JOptionPane.showMessageDialog(null, "No ahi estados grabados actualmente");
        }
    }

    private void actualizarListas() {
        listaEstadosFin.removeAllItems();
        listaEstadosInicio.removeAllItems();
        for (Nodo nodo : grafo.getNodos()) {
            listaEstadosInicio.addItem(nodo.getNombre());
            listaEstadosFin.addItem(nodo.getNombre());
        }
    }

    private JPanel panelDatos() {
        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new GridLayout(3, 1));
        JLabel lblTitulo = new JLabel("Validar expresion Regular");
        lblTitulo.setSize(new Dimension(150, (panelInferior.getHeight() * 25) / 100));
        panelDatos.add(lblTitulo);
        txtExpresion = new JTextField();
        txtExpresion.setPreferredSize(new Dimension(150, (panelInferior.getHeight() * 25) / 100));
        panelDatos.add(txtExpresion);
        btnValidar = new JButton("Validar");
        btnValidar.setPreferredSize(new Dimension(50,  100 ));
        btnValidar.addActionListener(this);
        panelDatos.add(btnValidar);
        return panelDatos;
    }

    private void panelCargaDatos() {
        panelCargaDatos = new JPanel();
        panelCargaDatos.setLayout(new GridLayout(4, 3));
        listaEstadosInicio = new JComboBox();
        listaEstadosFin = new JComboBox();
        listaTransiciones = new JComboBox();
        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(this);
        chIsFinal = new JCheckBox("Es Final");
        chIsFinal.setSelected(false);
        btnGrabarNodo = new JButton("Agregar");
        btnGrabarNodo.addActionListener(this);
        txtNodo = new JTextField();
        panelCargaDatos.add(new JLabel(""));
        panelCargaDatos.add(new JLabel("Grabar Estado"));
        panelCargaDatos.add(new JLabel(""));
        panelCargaDatos.add(txtNodo);
        panelCargaDatos.add(chIsFinal);
        panelCargaDatos.add(btnGrabarNodo);
        panelCargaDatos.add(new JLabel(""));
        panelCargaDatos.add(new JLabel("Crear Transicion"));
        panelCargaDatos.add(new JLabel(""));
        panelCargaDatos.add(listaEstadosInicio);
        panelCargaDatos.add(listaEstadosFin);
        JPanel panelGrabar = new JPanel();
        panelGrabar.setLayout(new GridLayout(1, 2));
        for (int i = 0; i < alfabeto.length(); i++) {
            listaTransiciones.addItem(alfabeto.charAt(i));
        }
        panelGrabar.add(listaTransiciones);
        panelGrabar.add(btnGuardar);
        panelCargaDatos.add(panelGrabar);
    }

    public File abrirVentana() {
        File archivo = null;
        JFileChooser fileChooser = new JFileChooser();
        int respuesta = fileChooser.showOpenDialog(this);
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            archivo = fileChooser.getSelectedFile();
        }
        return archivo;
    }

    public void armarGrafo(Grafo g) {
        String[][] matriz = g.getTablaTransiciones();
        String[] array = matriz[0];
        Nodo nuevo = null, EstadoSalida = null;

        // Se crean todos los nodos principales de los estados
        for (int i = 1; i < matriz.length; i++) {
            nuevo = new Nodo(matriz[i][0], matriz[i][0].contains("*"));
            nuevo.setNombre(nuevo.getNombre().replace("*", ""));
            nuevo.setNombre(nuevo.getNombre().replace(">", ""));
            // Se toma como un nuevo estado
            if (!g.existNodo(nuevo)) {
                g.addNodo(nuevo);
            }
        }
        // Se crean las transiciones
        for (int i = 1; i < matriz.length; i++) {
            String nomNodo = matriz[i][0].replace("*", "");
            nomNodo = nomNodo.replace(">", "");
            EstadoSalida = g.returnNodo(new Nodo(nomNodo));
            for (int x = 1; x < matriz[0].length; x++) {
                nuevo = g.returnNodo(new Nodo(matriz[i][x]));
                if (g.existNodo(nuevo)) {
                    Transicion t = new Transicion(nuevo, array[x]);
                    if (EstadoSalida != null) {
                        EstadoSalida.addTransicion(t);
                    }
                }
            }
        }
    }
}
