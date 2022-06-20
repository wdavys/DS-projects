package models;

import java.sql.*;
import java.util.ArrayList;

public class Photo {

    private int idPhoto;
    private String lienPhoto;
    private int codePro;
    private static PreparedStatement savestmt, deletestmt;

    public void setCodePro(int codePro) {
        this.codePro = codePro;
    }

    private Photo(int idPhoto, String lienPhoto, int codePro) {
        this.idPhoto = idPhoto;
        this.lienPhoto = lienPhoto;
        this.codePro = codePro;
        initializeDB();
    }

    public Photo(String lienPhoto) {
        this.lienPhoto = lienPhoto;
        initializeDB();
    }

    public static void initializeDB() {
        try {
            Connection connection = BD.connex;
            savestmt = connection.prepareStatement("insert into Photo (lienPhoto, codePro) values (?, ?)");
            deletestmt = connection.prepareStatement("delete from Photo where idPhoto = ?");
        } catch (SQLException e) {
            System.err.println("Unable to fetch data from the DB !");
        }
    }

    public static ArrayList<Photo> getAll(int codePro) {
        ArrayList<Photo> tmp = new ArrayList<>();
        try {
            Statement stmt = BD.getStatement();
            ResultSet set = stmt.executeQuery("select * from Photo where codePro = " + codePro);
            while (set.next()) {
                Photo ph = new Photo(set.getInt(1), set.getString(2), set.getInt(3));
                tmp.add(ph);
            }
        } catch (SQLException ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
        return tmp;
    }

    public static ArrayList<String> getAllLien(int codePro) {
        ArrayList<String> tmp = new ArrayList<>();
        try {
            Statement stmt = BD.getStatement();
            ResultSet set = stmt.executeQuery("select lienPhoto from Photo where codePro = " + codePro);
            while (set.next()) {
                String ph = set.getString(1);
                tmp.add(ph);
            }
        } catch (SQLException ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
        return tmp;
    }

    public void save() {
        try {
            savestmt.setString(1, this.lienPhoto);
            savestmt.setInt(2, this.codePro);
            savestmt.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
    }

    public void delete() {
        try {
            deletestmt.setInt(1, this.idPhoto);
            deletestmt.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
    }
}