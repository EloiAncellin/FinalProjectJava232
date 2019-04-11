package eloi.ancellin.finalproject.modele;

import android.os.StrictMode;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

import eloi.ancellin.finalproject.controller.Security;
import eloi.ancellin.finalproject.modele.Item;
import eloi.ancellin.finalproject.modele.User;


public class databaseAcess {

    Connection connection;

    // Test resources for the time that we don't have the database connexion
    private ArrayList<Item> shoppingTest = new ArrayList<Item>();
    private String [][] shoppingTestImple = {
            {"Eloi","12","4","7"},
            {"Pierre","7","2","2"},
            {"Simon","4","1","3"},
            {"Jean","3","8","4"},
            {"Paul","1","12","5"},
            {"Franck","2","9","6"},
            {"Olivier","22","14","1"}
    };





    public static Connection connectToDB() {


        //String jdbcUrl = String.format("jdbc:mysql://10.0.2.2:8889");
        String jdbcUrl= "jdbc:mysql://104.155.36.57:3306";


                     /*   "google/%s?cloudSqlInstance=%s"
                        + "&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false",
                "finalproject232",
                "fp-cs232-eloiancellin:us-east1:final-project-232");
                //"jdbc:mysql://localhost:8889/finalProject232";   */



        String username = "root";
        String password = "root";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection=null;

        try {
            Class.forName ("com.mysql.jdbc.Driver").newInstance ();
            connection = DriverManager.getConnection(jdbcUrl, username, password);

        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getSuppressed());
        }
        if(connection!= null){
            System.out.println("Connexion réussie");
        }
        return connection;

    }


    public static User getUser(String mail, String pwd){

        Connection conn = null;
        User usr = null;
        try{

            conn = connectToDB();

            Statement state = conn.createStatement();
            state.executeQuery("USE finalProject232;");
            PreparedStatement prep = conn.prepareStatement("SELECT * FROM user WHERE usr_mail = ? AND usr_pwd = ?");
            prep.setString(1, mail);
            prep.setString(2, pwd);
            prep.executeQuery();
            ResultSet res = prep.executeQuery();
            ResultSetMetaData meta = res.getMetaData();

            String name="";
            while(res.next()) {
                name = res.getString("usr_name");
                name = Security.testString(name);
            }
            usr = new User(name, mail, pwd);
            state.close();
            conn.close();
        }catch (Exception e){
            System.out.println("Database failure");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println("getUser");
        }

        return usr;
        //return databaseAcess.fakeUser();
    }

    public static ArrayList<Item> getUsrShopLst(User usr){
        // goes in the db to get a shopping list related to the user.
        // Initializes the shopping list with the array that has been gotten

        Connection conn = null;
        ArrayList<Item> shopLst = new ArrayList<Item>();
        try{

            conn = connectToDB();
            Statement state = conn.createStatement();

            state.executeQuery("USE finalProject232;");

            String query = "SELECT it_name, it_price, qty FROM (user INNER JOIN shopLst  INNER JOIN item";
            query += " ON item.it_id = shopLst.it_id AND shopLst.usr_id = user.usr_id)";
            query+= " WHERE usr_mail = ?;";

            PreparedStatement prep = conn.prepareStatement(query);

            prep.setString(1,  usr.getMail());
            prep.executeQuery();
            ResultSet res = prep.executeQuery();
            ResultSetMetaData meta = res.getMetaData();
            while(res.next()) {
                String itName = res.getString("it_name");
                float itPrice = Float.parseFloat(res.getString("it_price"));
                int itQty = Integer.parseInt(res.getString("qty"));
                Item it = new Item(itName, itQty, 0, itPrice);
                shopLst.add(new Item(it));
                System.out.println(it.serialize());
            }

           // String name = res.getString("usr_name");
            //usr = new User(name, mail, pwd);

            state.close();
            conn.close();
        }catch (Exception e){
            System.out.println("Database failure");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println("getUserShopLst");
        }
        ArrayList<String> shopStr = Item.serializeArrayIt(shopLst);
        for(int i=0; i< shopStr.size(); i++){
            System.out.println(shopStr.get(i));
        }
        System.out.println(shopLst.size());
        return  shopLst;

        // For now, we will just initialize it with the values harcoded.
        //this.initShopLst();
        // return this.shoppingTest;

    }

    public static User newUser( User usr){
        // goes in the db to get a shopping list related to the user.
        // Initializes the shopping list with the array that has been gotten

        Connection conn = null;
        ArrayList<Item> shopLst = new ArrayList<Item>();
        try{

            conn = connectToDB();
            Statement state = conn.createStatement();
            state.executeQuery("USE finalProject232;");


            // Inserting the user in the database
            String query = "INSERT INTO user (usr_mail, usr_name, usr_pwd) VALUES\n" + "( ?, ?, ?);";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setString(1,  usr.getMail());
            prep.setString(2,  usr.getUserName());
            prep.setString(3,  usr.getPwd());
            prep.executeUpdate();
            //System.out.println("Request 1 executed");

            // get the user id related to the email
            query = "SELECT usr_id FROM user WHERE usr_mail =? ";
            prep = conn.prepareStatement(query);
            prep.setString(1, usr.getMail());
            ResultSet res = prep.executeQuery();
            //System.out.println("Request 2 executed");
            String usr_idStr ="";
            while (res.next()){
                    usr_idStr = res.getString("usr_id");
            }
            int usr_id = Integer.parseInt(usr_idStr);
            // Aller chercher la liste des item_id et des qty def
            // faire un for loop pour les insertions dans la table shopLst

            query = "SELECT it_id, def_qty FROM item WHERE def_qty!=0;";
            prep = conn.prepareStatement(query);
            res = prep.executeQuery();
            //System.out.println("Request 3 executed");
            ArrayList<String> items = new ArrayList<String>();
            ResultSetMetaData meta = res.getMetaData();
            while (res.next()){
                    items.add(res.getString("it_id") +"," + res.getString("def_qty"));
            }

            query = "INSERT INTO shopLst (usr_id, it_id, qty) VALUES ";
            for(int i=0; i<items.size(); i++){
                String[] var = items.get(i).split(",");
                query += "(" + usr_id  + ","+ Integer.parseInt(var[0])+ "," + Integer.parseInt(var[1]) + ")";
                if(i!=items.size()-1)
                    query+=",";
                else
                    query+=";";
            }
            Statement stat = conn.createStatement();
            stat.executeUpdate(query);
            //System.out.println("Request 4 executed");


            state.close();
            conn.close();
        }catch (Exception e){
            System.out.println("Database failure");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println("newUser");
        }

        return usr;
    }

    public static boolean saveUserShop(User usr, ArrayList<Item> shopLst){
        // saves the user in the database.
        // saves the shoppingList related to the user


        Connection conn = null;
        try{

            conn = connectToDB();

            Statement state = conn.createStatement();
            state.executeQuery("USE finalProject232;");

            // We get the user, items and their names to be able to match that with the shopping list content
            String query = "SELECT it_name FROM shopLst NATURAL JOIN item  NATURAL JOIN user WHERE usr_mail = ?";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setString(1, usr.getMail());
            ResultSet res = prep.executeQuery();
            System.out.println("Query 1 ok");

            ResultSetMetaData meta = res.getMetaData();
            ArrayList<String> shpVar = new ArrayList<String>();
            while(res.next()){
                String var = res.getString("it_name");
                shpVar.add(var);
            }
            System.out.println("Test1");



            // Now we need to update the shopLst table to fit the right values of the qty
            query = "UPDATE shopLst NATURAL JOIN item NATURAL JOIN user SET qty = ? WHERE it_name = ? AND usr_mail =? ";
            prep = conn.prepareStatement(query);
            System.out.println("Test1");
            for(int i=0; i<shopLst.size(); i++){
                prep.setInt(1,shopLst.get(i).getQty());
                prep.setString(2,shpVar.get(i));
                prep.setString(3, usr.getMail());
                prep.executeUpdate();
                System.out.println("Test1 + " + i);
            }
            System.out.println("Query 2 ok");


            state.close();
            conn.close();
        }catch (Exception e){
            System.out.println("Database failure");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println("saveUserShop");
            return false;
        }

        return true;



    }

    public static boolean addUsrItem(User usr, Item it){
        // checks if the item already exist
        // if not, returns the shopLst with the added Item.

        Connection conn = null;
        try{

            conn = connectToDB();

            Statement state = conn.createStatement();
            state.executeQuery("USE finalProject232;");

            // First we get the user_id
            String query = "SELECT usr_id FROM user WHERE usr_mail = ?";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setString(1, usr.getMail());
            ResultSet res = prep.executeQuery();
            System.out.println("Query 1 ok");

            String usr_idStr = "";
            while(res.next()){
                usr_idStr = res.getString("usr_id");
            }
            int usr_id = Integer.parseInt(usr_idStr);

            //then we insert the item in the table item and get the generated value
            query = "INSERT INTO item (it_name, it_price, def_qty) VALUES (?, ?, 0)";
            prep = conn.prepareStatement(query);
            prep.setString(1, it.getName());
            prep.setFloat(2, it.getPrice());
            prep.executeUpdate();
            System.out.println("Query 2 ok");

            query = "SELECT it_id FROM item WHERE it_name = ?";
            prep = conn.prepareStatement(query);
            prep.setString(1, it.getName());
            res = prep.executeQuery();
            System.out.println("Query 3 ok");
            String it_idStr= "";
            while(res.next()){
                it_idStr = res.getString("it_id");
                System.out.println("Test 1");
            }
            System.out.println("Test 2");
            int it_id =Integer.parseInt(it_idStr);

            query = "INSERT INTO shopLst (usr_id, it_id, qty) VALUES ";
            query += "(?, ?, ?);";
            prep = conn.prepareStatement(query);
            prep.setInt(1, usr_id);
            prep.setInt(2, it_id);
            prep.setInt(3, it.getQty());
            prep.executeUpdate();
            System.out.println("Query 4 ok");

            System.out.println("Test requête");
            query = "SELECT * FROM shopLst WHERE usr_id = ? AND it_id ? AND qty = ?";
            prep = conn.prepareStatement(query);
            prep.setInt(1, usr_id);
            prep.setInt(2, it_id);
            prep.setInt(3, it.getQty());
            res =prep.executeQuery();
            while(res.next()){
                System.out.println("id_it" + res.getString("it_id"));
                System.out.println("id_it" + res.getString("usr_id"));
                System.out.println("id_it" + res.getString("qty_id"));
            }



            state.close();
            conn.close();
        }catch (Exception e){
            System.out.println("Database failure");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println("addUsrItem");
            return false;
        }
        return true;
    }


    private static User fakeUser() {
        User usr= new User("eloi", "eloi.ancellin@gmail.com", "testpwd");
        return usr;

    }

    public static boolean testUniqueUser(String usrMail){
        Connection conn = null;
        try{

            conn = connectToDB();

            Statement state = conn.createStatement();
            state.executeQuery("USE finalProject232;");

            // First we get the user_id
            String query = "SELECT usr_id FROM user WHERE usr_mail = ?";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setString(1, usrMail);
            ResultSet res = prep.executeQuery();
            System.out.println("Query 1 ok");
            int i=0;
            while(res.next()){
                i++;
            }

            state.close();
            conn.close();

            if(i!=0)
                return false;
            else
                return true;



        }catch (Exception e){
            System.out.println("Database failure");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println("testUniqUser");
            return false;
        }


    }

    public static  boolean testUniqueItem(String itName){
        Connection conn = null;
        try{

            conn = connectToDB();

            Statement state = conn.createStatement();
            state.executeQuery("USE finalProject232;");

            // First we get the user_id
            String query = "SELECT it_id FROM item WHERE it_name = ?";
            PreparedStatement prep = conn.prepareStatement(query);
            prep.setString(1, itName);
            ResultSet res = prep.executeQuery();
            System.out.println("Query 1 ok");
            int i=0;
            while(res.next()){
                i++;
            }

            state.close();
            conn.close();

            if(i!=0)
                return false;
            else
                return true;

        }catch (Exception e){
            System.out.println("Database failure");
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println("testUniqItem");
            return false;
        }
    }




    private void initShopLst() {

        for(int i=0;i<shoppingTestImple.length;i++) {
            Item it= new Item();
            it.setName(this.shoppingTestImple[i][0]);
            it.setPrice((float) Security.testNumber(this.shoppingTestImple[i][1]));
            it.setQty((int)Security.testNumber(this.shoppingTestImple[i][2]));
            it.setPrio((int)Security.testNumber(this.shoppingTestImple[i][3]));
            this.shoppingTest.add(it);
        }
    }


}
