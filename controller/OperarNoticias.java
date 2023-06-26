
package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Conexion;
import model.Noticia;
import view.Principal;

public class OperarNoticias {

    // crear vista
    Principal principal;

    // crear metodo para setear la vista
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    // al llamar en metodo dentro de la lista hay que instanciar el controlador,
    // setear la vista y llamar al metodo del controlador

    // listar
    public ArrayList<Noticia> listarNoticias(JTable table) {
        // crear arraylist
        ArrayList<Noticia> noticias = consultarNoticias();

        // insertar en la tabla
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Noticia n : noticias) {
            System.out.println(n.getId() + " " + n.getFecha() + " " + n.getAutor() + " " + n.getTitular() + " "
                    + n.getContenido());

            model.addRow(new Object[] { n.getId(), n.getFecha(), n.getAutor(), n.getTitular(), n.getContenido() });

        }

        table.setModel(model);

        return noticias;
    }

    // insertar
    public void crearNoticia(String autor, String titular, String contenido) {
        Noticia n = new Noticia();
        // generar la fecha actual
        java.util.Date fecha = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        n.setFecha(sdf.format(fecha));

        n.setAutor(autor);
        n.setTitular(titular);
        n.setContenido(contenido);
        String sql = "insert into noticias (fecha, autor, titular, contenido) values ('" + n.getFecha() + "', '"
                + n.getAutor() + "', '" + n.getTitular() + "', '" + n.getContenido() + "')";
        Conexion c = new Conexion();
        c.ejecutarSql(sql);
        JOptionPane.showMessageDialog(null, "Noticia insertada correctamente");
        c.cerrarConexion();
    }

    // buscar por nombre
    public ArrayList<Noticia> buscarNoticia(String nombre, JTable table) {
        String sql = "select * from noticias where titular like '%" + nombre + "%'";
        Conexion c = new Conexion();
        ResultSet rs = c.ejecutarQuery(sql);
        ArrayList<Noticia> noticias = new ArrayList<>();

        try {
            while (rs.next()) {
                Noticia n = new Noticia();
                n.setId(rs.getInt("id"));
                n.setFecha(rs.getString("fecha"));
                n.setAutor(rs.getString("autor"));
                n.setTitular(rs.getString("titular"));
                n.setContenido(rs.getString("contenido"));

                noticias.add(n);
            }

        } catch (SQLException e) {
            System.err.println(e);
        }

        // agregar cada noticia a la tabla
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Noticia n : noticias) {
            System.out.println(n.getId() + " " + n.getFecha() + " " + n.getAutor() + " " + n.getTitular() + " "
                    + n.getContenido());

            model.addRow(new Object[] { n.getId(), n.getFecha(), n.getAutor(), n.getTitular(), n.getContenido() });

        }

        table.setModel(model);

        c.cerrarConexion();

        return noticias;
    }

    // eliminar
    public void eliminarNoticia(int id) {
        String sql = "delete from noticias where id = " + id;
        Conexion c = new Conexion();
        c.ejecutarSql(sql);
        c.cerrarConexion();
    }

    // actualizar
    public void actualizarNoticia(Noticia n) {
        String sql = "update noticias set fecha = '" + n.getFecha() + "', autor = '" + n.getAutor() + "', titular = '"
                + n.getTitular() + "', contenido = '" + n.getContenido() + "' where id = " + n.getId();
        Conexion c = new Conexion();
        c.ejecutarSql(sql);
        c.cerrarConexion();
    }

    // muestra una noticia en bucle cada 5 segundos utilizando un hilo, volver a
    // ejecutar la consulta cuando el contador llegue al final
    public void mostrarNoticia() {

        // crear hilo
        Thread hilo = new Thread() {
            @Override
            public void run() {

                // crear arraylist
                ArrayList<Noticia> noticias = consultarNoticias();
                int i = 0;
                while (true) {
                    try {
                        // mostrar noticia
                        principal.fecha.setText(noticias.get(i).getFecha());
                        principal.autor.setText(noticias.get(i).getAutor());
                        principal.titular.setText(noticias.get(i).getTitular());
                        principal.contenido.setText(noticias.get(i).getContenido());

                        // esperar 5 segundos
                        sleep(5000);

                        // incrementar contador
                        i++;

                        // si el contador es igual al tamaño del arraylist, volver a empezar
                        if (i == noticias.size()) {
                            i = 0;
                            noticias = consultarNoticias();
                        }
                    } catch (InterruptedException e) {
                        System.err.println(e);
                    }
                }
            }
        };

        // iniciar hilo
        hilo.start();

    }

    // consultar y retornar arraylist de noticias
    public ArrayList<Noticia> consultarNoticias() {
        String sql = "select * from noticias";
        Conexion c = new Conexion();
        ResultSet rs = c.ejecutarQuery(sql);
        ArrayList<Noticia> noticias = new ArrayList<>();

        try {
            while (rs.next()) {
                Noticia n = new Noticia();
                n.setId(rs.getInt("id"));
                n.setFecha(rs.getString("fecha"));
                n.setAutor(rs.getString("autor"));
                n.setTitular(rs.getString("titular"));
                n.setContenido(rs.getString("contenido"));

                noticias.add(n);
            }

        } catch (SQLException e) {
            System.err.println(e);
        }

        c.cerrarConexion();

        return noticias;

    }

}
