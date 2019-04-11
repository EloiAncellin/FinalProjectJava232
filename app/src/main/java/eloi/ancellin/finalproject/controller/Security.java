package eloi.ancellin.finalproject.controller;

import java.util.ArrayList;
import java.util.regex.Pattern;

import eloi.ancellin.*;
import eloi.ancellin.finalproject.modele.Item;


public class Security {

    public static String testString(String str) {

        if(str.matches("[a-zA-Z]+")) {
            return str;
        }
        else {
            return str=str.replaceAll("[^a-zA-Z]+","");
        }

    }

    // checks if the string is a number. If it's not, it returns -1
    public static double testNumber(String nbr){

        if(nbr.matches("^\\d+(\\.\\d+)?")) {
            double number = Double.parseDouble(nbr);
            return number;
        }
        else {
            return 0d;
        }
    }


    // Function to parse an integer to a double
    public static double intToDouble(String integer) {

        double dbl = testNumber(integer);
        dbl=(double)(int)dbl;
        return dbl;
    }

    public static int testInt(String integer) {
        double dbl = testNumber(integer);
        int integ = (int) dbl;
        return integ;
    }

    public static boolean stringToInt(String bool) {
        bool=testString(bool);
        boolean boole = Boolean.valueOf(bool);
        return boole;
    }


    public static boolean testUniqPrio(ArrayList<Item> shopLst,Item it) {
        int[] prioLst= new int[7];
        for(int i=0;i<shopLst.size();i++) {
            if(shopLst.get(i)!= null) {
                prioLst[i]=shopLst.get(i).getPrio();
            }else {
                break;
            }
        }
        for(int i=0;i<prioLst.length;i++) {
            if(prioLst[i]!=0 &&prioLst[i]==it.getPrio()) {

                return false;
            }
        }

        return true;
    }


    public static boolean testUniqName(ArrayList<Item> shopLst,Item it) {
        String[] nameLst= new String[7];
        int valMax=0;
        for(int i=0;i<shopLst.size();i++) {
            if(shopLst.get(i)!= null) {
                nameLst[i]=shopLst.get(i).getName();
                valMax=i+1;
            }else {
                break;
            }
        }

        for(int i=0;i<valMax ;i++) {
            if( nameLst[i].equals(it.getName())) {

                return false;
            }
        }


        return true;
    }

    public static boolean isString(String str){
        if(str.matches("[a-zA-Z]+"))
            return true;
        else
            return false;
    }

    public static boolean isFloat(String sFlt){
        try{
            float flt = Float.parseFloat(sFlt);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public static boolean isInt(String sInt){
        try{
            int i= Integer.parseInt(sInt);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public static boolean isMail(String mail){
        return Pattern.matches("[_a-zA-Z1-9]+(\\.[A-Za-z0-9]*)*@[A-Za-z0-9]+\\.[A-Za-z0-9]+(\\.[A-Za-z0-9]*)*", mail);
    }

}



