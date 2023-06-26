/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 */
public class Conexion {

    String driver = "org.postgresql.Driver";
    String connectString = "jdbc:postgresql://silly.db.elephantsql.com:5432/vbnnqndy";
    String user = "vbnnqndy";
    String password = "password";
    Connection con;

    // construccion de la conexion
    public Conexion() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(connectString, user, password);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    // Hacer insert o update o delete
    public boolean ejecutarSql(String sql) {
        boolean guardo = false;
        try {

            Statement stmt = con.createStatement();
            guardo = stmt.execute(sql);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return guardo;
    }

    // Consultas
    public ResultSet ejecutarQuery(String sql) {
        ResultSet rs = null;
        try {

            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

        } catch (Exception e) {
            // JOptionPane.showMessageDialog(null,e.getMessage());
            System.out.print(e.getMessage());
        }

        return rs;

    }

    public void cerrarConexion() {
        try {
            con.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

}
