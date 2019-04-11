package eloi.ancellin.finalproject.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import eloi.ancellin.finalproject.R;
import eloi.ancellin.finalproject.modele.Item;
import eloi.ancellin.finalproject.modele.User;

public class Shopping extends AppCompatActivity {

    private ArrayList<Item> shopList;
    private ArrayList<Item> cart;
    private User usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialisation of the cart
        this.cart = new ArrayList<Item>();

        // get the parameters from the intent
        Bundle param = getIntent().getExtras();
        this.usr = param.getParcelable("user");

        //this.shopList
        ArrayList arr = param.getParcelableArrayList("shoppingLst");
        this.shopList = Item.deserializeArrayIt(arr);


        showShopList();

        System.out.println("Username" + usr.getUserName());
        //Add item button
        FloatingActionButton addIt = findViewById(R.id.activity_shopping_add_item);
        addIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityAddItemtIntent = new Intent(Shopping.this, AddItem.class);
                activityAddItemtIntent.putExtra("usr", usr);
                startActivityForResult(activityAddItemtIntent, 1);
            }
        });

        //Cart button
        FloatingActionButton cartBut = findViewById(R.id.activity_shopping_cart);
        cartBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityCartIntent = new Intent(Shopping.this, Cart.class);
                ArrayList<String> shopStr = Item.serializeArrayIt(shopList);
                ArrayList<String> shopCart = Item.serializeArrayIt(cart);
                activityCartIntent.putStringArrayListExtra("shopStr", shopStr);
                activityCartIntent.putStringArrayListExtra("cartStr", shopCart);
                activityCartIntent.putExtra("usr", usr);
                startActivity(activityCartIntent);
            }
        });

        // Sort shopping list button
        FloatingActionButton sort = findViewById(R.id.activity_shopping_sort_list);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent activitySortIntent = new Intent(Shopping.this, SortItem.class);
                ArrayList<String> shopStr = Item.serializeArrayIt(shopList);
                activitySortIntent.putStringArrayListExtra("shopStr", shopStr);
                startActivityForResult(activitySortIntent, 2);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) { // Activity addItemToCart result
                ArrayList arrShop = data.getStringArrayListExtra("shopLst");
                this.shopList = Item.deserializeArrayIt(arrShop);
                ArrayList arrCart = data.getStringArrayListExtra("cart");
                this.cart = Item.deserializeArrayIt(arrCart);
                showShopList();

            }
        } else if (requestCode == 1) { // Activity addItem result
            String strIt = data.getStringExtra("itemStr");
            Item it = Item.deserialize(strIt);
            this.shopList.add(it);
            showShopList();
            System.out.println("Bonjour ");
        } else if (requestCode == 2) {// Activity sort result

            ArrayList arrShop = data.getStringArrayListExtra("shopLst");
            this.shopList = Item.deserializeArrayIt(arrShop);
            showShopList();

        }

        if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result

        }
    }//onActivityResult


    private void showShopList() {
        // Display the shopping list on the screen
        ListView listView = (ListView) findViewById(R.id.activity_shopping_list);
        // Create an array list of string
        final ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < this.shopList.size(); i++) {
            if (shopList.get(i).getQty() > 0) {
                arrayList.add(this.shopList.get(i).getName() + "   $" + this.shopList.get(i).getPrice() + "       " + this.shopList.get(i).getQty() + "  available");
            }

            //Create Adapter
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

            //assign adapter to listview
            listView.setAdapter(null);
            listView.setAdapter(arrayAdapter);

            //add listener when we click on an item, we launch the addItemToCart activity to know the quatity...etc
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(Shopping.this, "clicked item:" + i + " " + arrayList.get(i).toString(), Toast.LENGTH_SHORT).show();


                    Intent activityAddToCartIntent = new Intent(Shopping.this, AddToCart.class);
                    activityAddToCartIntent.putExtra("index", i);
                    activityAddToCartIntent.putExtra("shopList", Item.serializeArrayIt(shopList));
                    activityAddToCartIntent.putExtra("cart", Item.serializeArrayIt(cart));
                    startActivityForResult(activityAddToCartIntent, 0);

                }
            });
        }
    }
}



