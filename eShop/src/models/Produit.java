package models;

import java.sql.*;
import java.util.ArrayList;

public class Produit {

    private int codePro;
    private String nomPro;
    private float prix;
    private int qte;
    private String description = "RAS";
    private String codeFour;
    private int actif = 1;
    private int idCategorie;
    private String categorie;
    private static PreparedStatement savestmt, updatestmt, loadcodestmt;

    private Produit(int codePro, String nomPro, float prix, int qte, String codeFour, int idCategorie, String description, int actif) {
        this.codePro = codePro;
        this.nomPro = nomPro;
        this.prix = prix;
        this.qte = qte;
        this.codeFour = codeFour;
        this.idCategorie = idCategorie;
        this.description = description;
        this.actif = actif;
    }

    public Produit(String nomPro) {
        this.nomPro = nomPro;
    }

    public int getCodePro() {
        return codePro;
    }

    public String getNomPro() {
        return nomPro;
    }

    public void setNomPro(String nomPro) {
        this.nomPro = nomPro;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodeFour() {
        return codeFour;
    }

    public void setCodeFour(String codeFour) {
        this.codeFour = codeFour;
    }

    public int getActif() {
        return actif;
    }

    public void setActif(int actif) {
        this.actif = actif;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getCategorie() {
        return categorie;
    }

    public static void initializeDB() {
        try {
            Connection connection = BD.connex;
            savestmt = connection.prepareStatement("insert into Produit (nomPro, prix, qte, description, codeFour, actif, idCategorie) values (?, ?, ?, ?, ?, ?, ?)");
            updatestmt = connection.prepareStatement("update Produit set nomPro = ?, prix = ?, qte = ?, description = ?, actif = ?, idCategorie = ?, codeFour = ? where codePro = ?");
            loadcodestmt = connection.prepareStatement("select codePro from Produit where nomPro = ?");
        } catch (SQLException e) {
            System.err.println("Unable to fetch data from the DB !");
        }
    }

    public static ArrayList<Produit> getAll() {
        ArrayList<Produit> tmp = new ArrayList<>();
        try {
            Statement stmt = BD.getStatement();
            ResultSet set = stmt.executeQuery("select * from Produit");
            while (set.next()) {
                Produit g = new Produit(set.getInt(1), set.getString(2), set.getFloat(3), set.getInt(4), set.getString(6), set.getInt(8), set.getString(5), set.getInt(7));
                tmp.add(g);
            }
        } catch (SQLException ex) {
            System.err.println("Unable to fetch data from the DB !");
        }

        for (Produit t : tmp) {
            t.categorie = Categorie.findNomCat(t.idCategorie);
        }
        return tmp;
    }

    public static ArrayList<Produit> find(String pattern) {
        ArrayList<Produit> tmp = new ArrayList<>();
        try {
            Statement stmt = BD.getStatement();
            ResultSet set = stmt.executeQuery("select * from Produit where nomPro like '%" + pattern + "%'");
            while (set.next()) {
                Produit pr = new Produit(set.getInt(1), set.getString(2), set.getFloat(3), set.getInt(4), set.getString(6), set.getInt(8), set.getString(5), set.getInt(7));
                tmp.add(pr);
            }
        } catch (SQLException ex) {
            System.err.println("Unable to fetch data from the DB !");
        }

        for (Produit t : tmp) {
            t.categorie = Categorie.findNomCat(t.idCategorie);
        }
        return tmp;
    }

    public void save() {
        try {
            savestmt.setString(1, this.nomPro);
            savestmt.setFloat(2, this.prix);
            savestmt.setInt(3, this.qte);
            savestmt.setString(4, this.description);
            savestmt.setString(5, this.codeFour);
            savestmt.setInt(6, this.actif);
            savestmt.setInt(7, this.idCategorie);
            savestmt.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
    }

    public void update() {
        try {
            updatestmt.setString(1, this.nomPro);
            updatestmt.setFloat(2, this.prix);
            updatestmt.setInt(3, this.qte);
            updatestmt.setString(4, this.description);
            updatestmt.setInt(5, this.actif);
            updatestmt.setInt(6, this.idCategorie);
            updatestmt.setString(7, codeFour);
            updatestmt.setInt(8, this.codePro);
            updatestmt.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
    }

    public int fetchCodePro() {
        int codePro = 0;
        try {
            loadcodestmt.setString(1, this.nomPro);
            ResultSet set = loadcodestmt.executeQuery();
            set.next();
            codePro = set.getInt(1);
        } catch (Exception ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
        return codePro;
    }
}
