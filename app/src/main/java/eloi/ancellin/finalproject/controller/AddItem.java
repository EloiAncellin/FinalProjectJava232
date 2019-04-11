package eloi.ancellin.finalproject.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import eloi.ancellin.finalproject.R;
import eloi.ancellin.finalproject.modele.Item;
import eloi.ancellin.finalproject.modele.User;
import eloi.ancellin.finalproject.modele.databaseAcess;

public class AddItem extends AppCompatActivity {


    private EditText name;
    private EditText price;
    private EditText qty;
    private Button subButton;
    private User usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        //get the user from the shopping Activity
        Bundle param = getIntent().getExtras();
        usr = param.getParcelable("usr");


        name = findViewById(R.id.activity_add_item_name);
        price = findViewById(R.id.activity_add_item_price);
        qty = findViewById(R.id.activity_add_item_qty);
        subButton = findViewById(R.id.activity_add_item_submit);

        subButton.setEnabled(false);


        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // have to add the security
                String sName = name.getText().toString();
                String sPrice = price.getText().toString();
                String sQty = qty.getText().toString();
                boolean boolName = Security.isString(sName);
                boolean boolPrice = Security.isFloat(sPrice);
                boolean boolQty = Security.isInt(sQty);

                if(boolName && boolPrice && boolQty && sName.length()>2 && sPrice.length()>0 && sQty.length()>0){
                    subButton.setEnabled(true);
                }else{
                    subButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String sName = name.getText().toString();
                boolean boolName = Security.isString(sName);
                if(databaseAcess.testUniqueItem(sName) == false){
                    subButton.setEnabled(false);
                }
            }
        });

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // have to add the security
                String sName = name.getText().toString();
                String sPrice = price.getText().toString();
                String sQty = qty.getText().toString();
                boolean boolName = Security.isString(sName);
                boolean boolPrice = Security.isFloat(sPrice);
                boolean boolQty = Security.isInt(sQty);

                if(boolName && boolPrice && boolQty && sName.length()>2 && sPrice.length()>0 && sQty.length()>0){
                    subButton.setEnabled(true);
                }else{
                    subButton.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                String sName = name.getText().toString();
                boolean boolName = Security.isString(sName);
                if(databaseAcess.testUniqueItem(sName) == false){
                    subButton.setEnabled(false);
                }

            }
        });

        qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // have to add the security
                String sName = name.getText().toString();
                String sPrice = price.getText().toString();
                String sQty = qty.getText().toString();
                boolean boolName = Security.isString(sName);
                boolean boolPrice = Security.isFloat(sPrice);
                boolean boolQty = Security.isInt(sQty);

                if(boolName && boolPrice && boolQty && sName.length()>2 && sPrice.length()>0 && sQty.length()>0){
                    subButton.setEnabled(true);
                }else{
                    subButton.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                String sName = name.getText().toString();
                boolean boolName = Security.isString(sName);
                if(databaseAcess.testUniqueItem(sName) == false){
                    subButton.setEnabled(false);
                }
            }
        });


        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // have to add the security
                String sName = name.getText().toString();
                float fPrice = Float.parseFloat(price.getText().toString());
                int iQty = Integer.parseInt(qty.getText().toString());
                Item it = new Item(sName, iQty, 1, fPrice);

                // Saving the user in the database
                databaseAcess.addUsrItem(usr, it);

                // Returning the new item
                Intent resultShopActivityIntent = new Intent(AddItem.this, Shopping.class);
                String strIt = it.serialize();
                System.out.println(strIt);
                resultShopActivityIntent.putExtra("itemStr", strIt);
                setResult(1, resultShopActivityIntent);
                finish();

            }
        });
    }
}
