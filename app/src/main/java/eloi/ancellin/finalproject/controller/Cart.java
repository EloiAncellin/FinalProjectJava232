package eloi.ancellin.finalproject.controller;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import eloi.ancellin.finalproject.R;
import eloi.ancellin.finalproject.modele.Item;
import eloi.ancellin.finalproject.modele.User;
import eloi.ancellin.finalproject.modele.databaseAcess;

import static java.lang.Thread.sleep;

public class Cart extends AppCompatActivity {

    private ArrayList<Item> cart;
    private ArrayList<Item> shopList;
    private User usr;
    private Button butBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        // get the variables from the Intent
        Bundle param= getIntent().getExtras();
        this.usr= param.getParcelable("usr");
        //this.shopList
        ArrayList sArrShop= param.getParcelableArrayList("shopStr");
        this.shopList=Item.deserializeArrayIt(sArrShop);
        //this.cart
        ArrayList sArrCart= param.getParcelableArrayList("cartStr");
        this.cart=Item.deserializeArrayIt(sArrCart);
        showShopList();

        butBuy = findViewById(R.id.activity_cart_button_buy);
        butBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Your order has been placed", Snackbar.LENGTH_LONG).show();


                // then we need to save all the item and go back to the main page

                databaseAcess.saveUserShop(usr, shopList);
                Intent activityMainIntent=  new Intent(Cart.this, MainActivity.class);
                try {
                    TimeUnit.SECONDS.sleep(2);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println(e.getCause());
                }
                startActivity(activityMainIntent);
            }
        });


    }


    

    private void showShopList(){
        // Display the shopping list on the screen
        ListView listView=(ListView)findViewById(R.id.activity_cart_list);
        // Create an array list of string
        final ArrayList<String> arrayList=new ArrayList<>();
        for(int i=0; i<this.cart.size();i++){
            if(cart.get(i).getQty()>0) {
                arrayList.add(this.cart.get(i).getName() + "   $" + this.cart.get(i).getPrice());
            }else{
                arrayList.add(this.cart.get(i).getName() + "   $" + this.cart.get(i).getPrice()+ " no stock");
            }
        }

        //Create Adapter
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);

        //assign adapter to listview
        listView.setAdapter(null);
        listView.setAdapter(arrayAdapter);


    }



}
