package models;

import java.sql.*;
import java.util.ArrayList;

public class Gestionnaire {

	private int idGest;
	private String nomGest;
	private String typeGest;
	private String login;
	private String password;
	private int actif;
    private static PreparedStatement savestmt, updatestmt;

	private Gestionnaire(int idGest, String nomGest, int typeGest, String login, String password, int actif) {
	    this.idGest = idGest;
	    this.nomGest = nomGest;
	    switch (typeGest) {
            case 0 : this.typeGest = StringsFr.MAGASINIER;
            break;
            case 1 : this.typeGest = StringsFr.CAISSIER;
            break;
            case 2 : this.typeGest = StringsFr.ROOT;
            break;
        }
		this.login = login;
		this.password = password;
		this.actif = actif;
	}

    public Gestionnaire(String nomGest, int typeGest, String login, String password, int actif) {
        this.nomGest = nomGest;
        switch (typeGest) {
            case 0 : this.typeGest = StringsFr.MAGASINIER;
                break;
            case 1 : this.typeGest = StringsFr.CAISSIER;
                break;
            case 2 : this.typeGest = StringsFr.ROOT;
                break;
        }
        this.login = login;
        this.password = password;
        this.actif = actif;
    }

    public int getIdGest() {
        return this.idGest;
    }

	public String getNomGest() {
		return this.nomGest;
	}

	public void setNomGest(String nomGest) {
		this.nomGest = nomGest;
	}

	public String getTypeGest() {
		return this.typeGest;
	}

	public void setTypeGest(int typeGest) {
        switch (typeGest) {
            case 0 : this.typeGest = StringsFr.MAGASINIER;
                break;
            case 1 : this.typeGest = StringsFr.CAISSIER;
                break;
            case 2 : this.typeGest = StringsFr.ROOT;
                break;
        }
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getActif() {
		return this.actif;
	}

	public void setActif(int actif) {
		this.actif = actif;
	}

    public static void initializeDB() {
        try {
            Connection connection = BD.connex;
            savestmt = connection.prepareStatement("insert into Gestionnaire (nomGest, typeGest, login, pwd) values (?, ?, ?, ?)");
            updatestmt = connection.prepareStatement("update Gestionnaire set nomGest = ?, typeGest = ?, login = ?, pwd = ?, actif = ? where idGest = ?");
        } catch (SQLException e) {
            System.err.println("Unable to fetch data from the DB !");
        }
    }

	public static ArrayList<Gestionnaire> getAll() {
        ArrayList<Gestionnaire> tmp = new ArrayList<>();
        try {
            Statement stmt = BD.getStatement();
            ResultSet set = stmt.executeQuery("select * from Gestionnaire");
            while (set.next()) {
                Gestionnaire g = new Gestionnaire(set.getInt(1), set.getString(2), set.getInt(3), set.getString(4), set.getString(5), set.getInt(6));
                tmp.add(g);
            }
        } catch (SQLException ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
        return tmp;
    }

    public static ArrayList<Gestionnaire> find(String pattern) {
        ArrayList<Gestionnaire> tmp = new ArrayList<>();
        try {
            Statement stmt = BD.getStatement();
            ResultSet set = stmt.executeQuery("select * from Gestionnaire where nomGest like '%" + pattern + "%'");
            while (set.next()) {
                Gestionnaire g = new Gestionnaire(set.getInt(1), set.getString(2), set.getInt(3), set.getString(4), set.getString(5), set.getInt(6));
                tmp.add(g);
            }
        } catch (SQLException ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
        return tmp;
    }

    /*public static String findNomGest(String log) {
        String p = "";
        try {
            Statement stmt = BD.getStatement();
            ResultSet set = stmt.executeQuery("select nomGest from Gestionnaire where login = '" + log + "'");
            set.next();
            p = set.getString(1);
        } catch (SQLException ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
        return p;
    }

    public static int findTypeGest(String log) {
        int p = -1;
        try {
            Statement stmt = BD.getStatement();
            ResultSet set = stmt.executeQuery("select typeGest from Gestionnaire where login = '" + log + "'");
            set.next();
            p = set.getInt(1);
        } catch (SQLException ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
        return p;
    }*/

    public static Gestionnaire checkLogin(String log, String pass) {
	    Gestionnaire g = null;
	    try {
	        Statement stmt = BD.getStatement();
	        ResultSet set = stmt.executeQuery("select nomGest, typeGest, actif from Gestionnaire where login = '" + log + "' AND pwd = '" + pass + "'");
	        if (set.next()) {
	            g = new Gestionnaire(set.getString(1), set.getInt(2), log, pass, set.getInt(3));
            }
        } catch (SQLException ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
        return g;
    }

    public void save() {
        int typeGest = -1;
        try {
            switch (this.typeGest) {
                case StringsFr.MAGASINIER : typeGest = 0;
                    break;
                case StringsFr.CAISSIER : typeGest = 1;
                    break;
                case StringsFr.ROOT : typeGest = 2;
                    break;
            }
            savestmt.setString(1, this.nomGest);
            savestmt.setInt(2, typeGest);
            savestmt.setString(3, this.login);
            savestmt.setString(4, this.password);
            savestmt.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
    }

    public void update() {
        int typeGest = -1;
        try {
            switch (this.typeGest) {
                case StringsFr.MAGASINIER : typeGest = 0;
                    break;
                case StringsFr.CAISSIER : typeGest = 1;
                    break;
                case StringsFr.ROOT : typeGest = 2;
                    break;
            }
            updatestmt.setString(1, this.nomGest);
            updatestmt.setInt(2, typeGest);
            updatestmt.setString(3, this.login);
            updatestmt.setString(4, this.password);
            updatestmt.setInt(5, this.actif);
            updatestmt.setInt(6, this.idGest);
            updatestmt.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Unable to fetch data from the DB !");
        }
    }
}
