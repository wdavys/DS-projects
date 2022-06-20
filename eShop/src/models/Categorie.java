package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Categorie {

    private int idCat;
    private String nomCat;
    private static PreparedStatement savestmt, updatestmt/*, getidcaprod*/;

    private Categorie(int id, String val) {
        this.idCat = id;
        this.nomCat = val;
    }

    public Categorie(String val) {
        this.nomCat = val;
    }

    public int getIdCat() {
        return this.idCat;
    }

    public String getNomCat() {
        return this.nomCat;
    }

    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }

    public static void initializeDB() {
        try {
            Connection connection = BD.connex;
            savestmt = connection.prepareStatement("insert into Categorie (nomCat) values (?)");
            updatestmt = connection.prepareStatement("update Categorie set nomCat = ? where idCat = ?");
            //getidcaprod = connection.prepareStatement("select * from Produit where idCat = ?");
        } catch (SQLException e) {
            System.err.println("Unable to fetch data from the DB !");
        }
    }

    public static ArrayList<Categorie> getAll() {
        ArrayList<Categorie> tmp = new ArrayList<>();
        try {
            Statement stmt = BD.getStatement();
            ResultSet set = stmt.executeQuery("select * from Categorie");
            while (set.next()) {
                Categorie c = new Categorie(set.getInt(1), set.getString(2));
                tmp.add(c);
            }
        } catch (SQLException ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
        return tmp;
    }

    public static ArrayList<String> getAllNomCat() {
        ArrayList<String> tmp = new ArrayList<>();
        try {
            Statement stmt = BD.getStatement();
            ResultSet set = stmt.executeQuery("select nomCat from Categorie");
            while (set.next()) {
                String c = set.getString(1);
                tmp.add(c);
            }
        } catch (SQLException ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
        return tmp;
    }
    
    public static ArrayList<Categorie> find(String pattern) {
        ArrayList<Categorie> tmp = new ArrayList<>();
        try {
            Statement stmt = BD.getStatement();
            ResultSet set = stmt.executeQuery("select * from Categorie where nomCat like '%" + pattern + "%'");
            while (set.next()) {
                Categorie c = new Categorie(set.getInt(1), set.getString(2));
                tmp.add(c);
            }
        } catch (SQLException ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
        return tmp;
    }

    public static int findIdCat(String pattern) {
        int tmp = 0;
        try {
            Statement stmt = BD.getStatement();
            ResultSet set = stmt.executeQuery("select idCat from Categorie where nomCat = '" + pattern + "'");
            while (set.next()) {
                tmp = set.getInt(1);
            }
        } catch (SQLException ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
        return tmp;
    }

    public static String findNomCat(int pattern) {
        String tmp = "";
        try {
            Statement stmt = BD.getStatement();
            ResultSet set = stmt.executeQuery("select nomCat from Categorie where idCat = " + pattern);
            while (set.next()) {
                tmp = set.getString(1);
            }
        } catch (SQLException ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
        return tmp;
    }

    public void save() {
        try {
            savestmt.setString(1, this.nomCat);
            savestmt.executeUpdate();
            ResultSet set = savestmt.executeQuery("select last_insert_id()");
            set.next();
            idCat = set.getInt(1);
        } catch (Exception ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
    }

    public void update() {
        try {
            updatestmt.setString(1, this.nomCat);
            updatestmt.setInt(2, this.idCat);
            updatestmt.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
    }

    /*public ArrayList<Produit> getAllProd() {
        ArrayList<Produit> listProd = new ArrayList<>();
        try {
            getidcaprod.setString(1, Integer.toString(idCat));
            ResultSet resultSet = getidcaprod.executeQuery();
            while (resultSet.next()) {
                Produit prod = new Produit(resultSet.getInt(2));
                prod.load();
                listProd.add(prod);
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return listProd;
    }*/
}
