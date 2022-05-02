
package proyecto.automatas.control;

import proyecto.automatas.vista.VentanaInicio;
import javax.swing.JOptionPane;


public class Automatas {

    public void init() {
        String alfabeto = JOptionPane.showInputDialog("Ingrese el alfabeto");
        VentanaInicio v = new VentanaInicio(alfabeto);
        v.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Automatas a = new Automatas();
            a.init();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
