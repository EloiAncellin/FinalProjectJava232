package eloi.ancellin.finalproject.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import eloi.ancellin.finalproject.R;
import eloi.ancellin.finalproject.modele.Item;
import eloi.ancellin.finalproject.modele.User;
import eloi.ancellin.finalproject.modele.databaseAcess;

public class Register extends AppCompatActivity {

    private TextView mailInput;
    private TextView pwdInput;
    private TextView nameInput;
    private Button subButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameInput= (TextView)findViewById(R.id.activity_register_name_input);
        mailInput = (TextView)findViewById(R.id.activity_register_mail_input);
        pwdInput = (TextView)findViewById(R.id.activity_register_pwd_input);
        subButton= (Button) findViewById(R.id.button_submit);
        subButton.setEnabled(false);


        mailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String sName = nameInput.getText().toString();
                String smail = mailInput.getText().toString();
                String spwd = pwdInput.getText().toString();
                boolean boolMail = Security.isMail(smail);
                boolean boolName = Security.isString(sName);

                if (sName.length() > 2 && boolMail && boolName && smail.length() > 2 && spwd.length() > 6) {
                    subButton.setEnabled(true);
                } else {
                    subButton.setEnabled(false);
                }

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String smail = mailInput.getText().toString();
                boolean boolMail = Security.isMail(smail);
                if(boolMail==true){
                    if(databaseAcess.testUniqueUser(smail)==false){
                        subButton.setEnabled(false);
                    }
                }


            }

        });

        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String sName = nameInput.getText().toString();
                String smail = mailInput.getText().toString();
                String spwd = pwdInput.getText().toString();
                boolean boolMail = Security.isMail(smail);
                boolean boolName = Security.isString(sName);

                if (sName.length() > 2 && boolMail && boolName && smail.length() > 2 && spwd.length() > 6) {
                    subButton.setEnabled(true);
                } else {
                    subButton.setEnabled(false);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String smail = mailInput.getText().toString();
                boolean boolMail = Security.isMail(smail);
                if(boolMail==true){
                    if(databaseAcess.testUniqueUser(smail)==false){
                        subButton.setEnabled(false);
                    }
                }
            }
        });

        pwdInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String sName = nameInput.getText().toString();
                String smail = mailInput.getText().toString();
                String spwd = pwdInput.getText().toString();
                boolean boolMail = Security.isMail(smail);
                boolean boolName = Security.isString(sName);

                if (sName.length() > 2 && boolMail && boolName && smail.length() > 2 && spwd.length() > 6) {
                    subButton.setEnabled(true);
                } else {
                    subButton.setEnabled(false);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String smail = mailInput.getText().toString();
                boolean boolMail = Security.isMail(smail);
                if(boolMail==true){
                    if(databaseAcess.testUniqueUser(smail)==false){
                        subButton.setEnabled(false);
                    }
                }
            }
        });

        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String mail = mailInput.getText().toString();
                String pwd = pwdInput.getText().toString();
                User usr = new User(name, mail, pwd);
                // we get the informations from the database
                databaseAcess db = new databaseAcess();
                usr = db.newUser(usr);
                ArrayList<Item> shopLst = db.getUsrShopLst(usr);
                shopLst.get(0).setQty(0);

                databaseAcess.saveUserShop(usr,shopLst);

                Intent shoppingActivityIntent = new Intent(Register.this, Shopping.class);
                shoppingActivityIntent.putExtra("user", usr);
                shoppingActivityIntent.putStringArrayListExtra("shoppingLst", Item.serializeArrayIt(shopLst));

                startActivity(shoppingActivityIntent);

            }
        });
    }
}
