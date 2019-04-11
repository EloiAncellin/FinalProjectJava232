package eloi.ancellin.finalproject.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;

import eloi.ancellin.finalproject.R;
import eloi.ancellin.finalproject.modele.Item;

public class SortItem extends AppCompatActivity {


    private ArrayList<Item> shopList;
    private Button nameSort;
    private Button priceSort;
    private Button qtySort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_item);

        Bundle param= getIntent().getExtras();
        ArrayList arr= param.getParcelableArrayList("shopStr");
        this.shopList=Item.deserializeArrayIt(arr);


        //Link of the different buttons
        nameSort = findViewById(R.id.activity_sort_button_name);
        priceSort = findViewById(R.id.activity_sort_button_price);
        qtySort = findViewById(R.id.activity_sort_button_qty);

        nameSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sorting by name
                sortShopLst(1);
                result();
            }
        });

        priceSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sorting by price
                sortShopLst(2);
                result();

            }
        });

        qtySort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sorting by qty
                sortShopLst(3);
                result();
            }
        });

    }



    private void result(){
        Intent activityShopResult = new Intent(SortItem.this, Shopping.class);
        ArrayList<String> shopStrArr =  Item.serializeArrayIt(shopList);
        activityShopResult.putStringArrayListExtra("shopLst",shopStrArr);
        setResult(2,activityShopResult);
        finish();
    }

    private void sortShopLst(int sortCode) {
        int n = shopList.size();
        if (sortCode == 1) {
            System.out.println("Test sort Name");
            for (int i = 0; i < n - 1; i++)
                for (int j = 0; j < n - i - 1; j++)
                    if (shopList.get(j).getName().compareTo(shopList.get(j + 1).getName()) > 0) {
                        Collections.swap(shopList, j, j + 1);
                    }

        } else if (sortCode == 2) {
            for (int i = 0; i < n - 1; i++)
                for (int j = 0; j < n - i - 1; j++)
                    if (shopList.get(j).getPrice()>shopList.get(j + 1).getPrice()){
                        Collections.swap(shopList, j, j + 1);
                    }
        }else if(sortCode==3){
            for (int i = 0; i < n - 1; i++)
                for (int j = 0; j < n - i - 1; j++)
                    if (shopList.get(j).getQty()>shopList.get(j + 1).getQty()){
                        Collections.swap(shopList, j, j + 1);
                    }
        }
    }

}
