
package noticias;

import view.Principal;

/**
 *
 * @author tres
 */
public class Noticias {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.setVisible(true);
    }

}

// pintar un panel en la ventana principal
// private void showPanel(JPanel p) {
// p.setSize(600,230);
// p.setLocation(0, 0);
// operaciones.removeAll(); // operaciones es el nombre de variable de la
// ventana principal
// operaciones.add(p, BorderLayout.CENTER);
// operaciones.revalidate();
// operaciones.repaint();
// }