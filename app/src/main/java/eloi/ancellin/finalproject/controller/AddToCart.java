package eloi.ancellin.finalproject.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import eloi.ancellin.finalproject.R;
import eloi.ancellin.finalproject.modele.Item;

import static java.lang.StrictMath.abs;

public class AddToCart extends AppCompatActivity {


    ArrayList<Item> shopLst;
    ArrayList<Item> cart;
    private TextView name;
    private TextView qty;
    private TextView price;
    private EditText askedQty;
    private Button butSubmit;
    private int index;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);


        Bundle param= getIntent().getExtras();
        try {
            ArrayList paraShop = param.getParcelableArrayList("shopList");
            this.shopLst = Item.deserializeArrayIt(paraShop);
            ArrayList paraCart = param.getParcelableArrayList("cart");
            this.cart = Item.deserializeArrayIt(paraCart);
            this.index = param.getInt("index");

        } catch (NullPointerException e){
            System.out.println("une valeur est nulle");
        }


    name= (TextView)findViewById(R.id.activity_addToCart_name);
    price= (TextView)findViewById(R.id.activity_addToCart_price);
    qty= (TextView)findViewById(R.id.activity_addToCart_qty);
    askedQty = (EditText)findViewById(R.id.activity_addToCart_asked_qty);
    butSubmit = (Button)findViewById(R.id.activity_addToCart_submit);

    //while the quantity field is not filled we disable the button.
    butSubmit.setEnabled(false);


    name.setText("Name : " + this.shopLst.get(index).getName());
    price.setText("Price : " +Float.toString(this.shopLst.get(index).getPrice()));
    qty.setText("Quantity available : "+Integer.toString(this.shopLst.get(index).getQty()));
    System.out.println("qty first " + this.shopLst.get(index).getQty());

    askedQty.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                String strQty = askedQty.getText().toString();
                int intQty = Integer.parseInt(strQty);
                if(intQty<=shopLst.get(index).getQty() && intQty>=0){
                    butSubmit.setEnabled(true);
                }else{
                    butSubmit.setEnabled(false);
                }

            }catch (Exception e){
                butSubmit.setEnabled(false);
            }
        }


    });

    butSubmit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int intQty=Integer.parseInt( askedQty.getText().toString());
            if(cart.size()>0){
                for(int i=0; i<cart.size();i++){
                    if(cart.get(i).equals(shopLst.get(index))){
                        cart.get(i).setQty(cart.get(i).getQty() + intQty);
                    }else{
                        cart.add(new Item(shopLst.get(index)));
                        cart.get(0).setQty(intQty);
                    }
                }

            }else{
                cart.add(new Item(shopLst.get(index)));
                cart.get(cart.indexOf(shopLst.get(index))).setQty(intQty);
            }

            shopLst.get(index).setQty(shopLst.get(index).getQty()-intQty);


            Intent returnActivityShoppingIntent= new Intent(AddToCart.this, Shopping.class);
            returnActivityShoppingIntent.putExtra("shopLst", Item.serializeArrayIt(shopLst));
            returnActivityShoppingIntent.putExtra("cart", Item.serializeArrayIt(cart));
            setResult(Activity.RESULT_OK,returnActivityShoppingIntent);
            finish();
        }
    });


    }
}
