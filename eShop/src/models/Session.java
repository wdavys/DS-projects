package models;

public class Session {

    public static boolean actif = false;
    public static boolean isloggedIn = false;
    private static Gestionnaire user = null;
    public static String login;
    private static String pwd;
    public static String typeUser;

    public static void logIn(Gestionnaire g) {
        user = g;
        isloggedIn = true;
        login = g.getLogin();
        pwd = g.getPassword();
        actif = g.getActif() != 0;
        typeUser = g.getTypeGest();
    }

    public static void logOut() {
        isloggedIn = false;
    }
}
