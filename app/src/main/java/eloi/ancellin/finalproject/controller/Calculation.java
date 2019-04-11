package eloi.ancellin.finalproject.controller;

import eloi.ancellin.finalproject.modele.Item;

import java.util.ArrayList;

public class Calculation {


    /**
     * Calculated the price of an Item Array
     * @param it
     * @return
     */
    public static double calcPrice(ArrayList<Item> it) {
        double total=0;

        for(int i=0; i<it.size();i++) {
            total += it.get(i).getPrice() * it.get(i).getQty();
        }
        return total;
    }


}
