package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class BD {
    public static Connection connex = null;
    private static Statement statement = null;

    public static void ConnexionMySql() {
        if (connex == null) {
            try {
                String urlPilote = "com.mysql.jdbc.Driver";
                Class.forName(urlPilote);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Driver JDBC not found !");
            }

            try {
                String urlBD = "jdbc:mysql://localhost:3306/shopdb?verifyServerCertificate=false&useSSL=true";
                connex = DriverManager.getConnection(urlBD, "simeu", "password");
                statement = connex.createStatement();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Connection failed !");
            }
        }
    }

    public static Statement getStatement() {
      return statement;
    }
}
