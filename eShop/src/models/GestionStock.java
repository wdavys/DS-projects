package models;

import java.sql.*;
import java.util.ArrayList;


public class GestionStock {

    private int idStock;
    private int qte;
    private String dateStock;
    private String operation;
    private int idGest;
    public int codePro;
    private static PreparedStatement savestmt;

    private GestionStock(int idStock, int qte, String dateStock, int operation, int idGest, int codePro) {
        this.idStock = idStock;
        this.qte = qte;
        this.dateStock = dateStock;
        this.operation = operation == 0 ? StringsFr.RETRAIT : StringsFr.AJOUT;
        this.idGest = idGest;
        this.codePro = codePro;
    }

    public GestionStock(int qte, String dateStock, int operation, int idGest, int codePro) {
        this.qte = qte;
        this.dateStock = dateStock;
        this.operation = operation == 0 ? StringsFr.RETRAIT : StringsFr.AJOUT;
        this.idGest = idGest;
        this.codePro = codePro;
    }

    public static void initializeDB() {
        try {
            Connection connection = BD.connex;
            savestmt = connection.prepareStatement("insert into Gestionstock (qte, dateStock, operation, idGest, codePro) values (?, ?, ?, ?, ?, ?)");
        } catch (SQLException e) {
            System.err.println("Unable to fetch data from the DB !");
        }
    }

    public static ArrayList<GestionStock> getAll() {
        ArrayList<GestionStock> tmp = new ArrayList<>();
        try {
            Statement stmt = BD.getStatement();
            ResultSet set = stmt.executeQuery("select * from Gestionstock");
            while (set.next()) {
                GestionStock stock = new GestionStock(set.getInt(1), set.getInt(2), set.getString(3), set.getInt(4), set.getInt(5), set.getInt(6));
                tmp.add(stock);
            }
        } catch (SQLException ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
        return tmp;
    }

    public static ArrayList<GestionStock> find(String pattern) {
        ArrayList<GestionStock> tmp = new ArrayList<>();
        try {
            Statement stmt = BD.getStatement();
            ResultSet set = stmt.executeQuery("select * from GestionStock where idGest like '%" + pattern + "%'");
            while (set.next()) {
                GestionStock stock = new GestionStock(set.getInt(1), set.getInt(2), set.getString(3), set.getInt(4), set.getInt(5), set.getInt(6));
                tmp.add(stock);
            }
        } catch (SQLException ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
        return tmp;
    }

    public void save() {
        try {
            int operation = this.operation.equals(StringsFr.RETRAIT) ? 0 : 1;
            savestmt.setInt(1, this.qte);
            savestmt.setString(2, this.dateStock);
            savestmt.setInt(3, operation);
            savestmt.setInt(4, this.idGest);
            savestmt.setInt(5, this.codePro);
            savestmt.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
    }
}
