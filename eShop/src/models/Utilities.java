package models;

import java.util.ArrayList;

public abstract class Utilities {

    public static int newCodeProduit() {
        int code;
        ArrayList<Integer> listCode = listCodeProduit();
        do {
            code = (int) Math.floor(Math.random() * 1000000);
        } while (testCode(listCode,code));

        return code;
    }

    public static String formatCode(int code) {
        int n = code / 1000;
        int m = code - n * 1000;

        return addZero(""+n)+"-"+addZero(""+m);
    }

    public static boolean testString(ArrayList<String> list, String value)
    {
        if (value == null)
            return false;
        for (String aList : list)
            if (value.toLowerCase().equals(aList.toLowerCase()))
                return true;

        return false;
    }

    public static double arrondir(double n) {
        return Math.floor(n*100) / 100.0;
    }

    private static String addZero(String a) {
        int n = a.length();
        StringBuilder aBuilder = new StringBuilder(a);
        for (int i = 1; i<=3-n; i++) {
            aBuilder.insert(0, "0");
        }
        a = aBuilder.toString();

        return a;
    }


    private static ArrayList<Integer> listCodeProduit() {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Produit> listProduit = Produit.getAll();
        for (Produit aListProduit : listProduit) list.add(aListProduit.getCodePro());

        return list;
    }

    private static boolean testCode(ArrayList<Integer> listCode, int code) {
        for (Integer aListCode : listCode)
            if (code == aListCode)
                return true;

        return false;
    }
}
