package eloi.ancellin.finalproject.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.util.ArrayList;

import eloi.ancellin.finalproject.R;
import eloi.ancellin.finalproject.modele.Item;
import eloi.ancellin.finalproject.modele.User;
import eloi.ancellin.finalproject.modele.databaseAcess;

public class Login extends AppCompatActivity {

    private TextView mailInput;
    private TextView pwdInput;
    private Button subButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mailInput = (TextView)findViewById(R.id.activity_login_mail_input);
        pwdInput = (TextView)findViewById(R.id.activity_login_pwd_input);
        subButton= (Button) findViewById(R.id._activity_login_button_submit);
        subButton.setEnabled(false);


        mailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String smail = mailInput.getText().toString();
                String spwd = pwdInput.getText().toString();
                boolean boolMail = Security.isMail(smail);


                if ( boolMail && spwd.length() > 2) {
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

            }
        });

        pwdInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String smail = mailInput.getText().toString();
                String spwd = pwdInput.getText().toString();
                boolean boolMail = Security.isMail(smail);
                if ( boolMail && spwd.length() > 2) {
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

            }
        });

        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseAcess db = new databaseAcess();
                User usr= db.getUser(mailInput.getText().toString(), pwdInput.getText().toString());
                ArrayList<Item> shopLst = db.getUsrShopLst(usr);

                Intent shoppingActivityIntent = new Intent(Login.this, Shopping.class);
                shoppingActivityIntent.putExtra("user", usr);
                shoppingActivityIntent.putStringArrayListExtra("shoppingLst", Item.serializeArrayIt(shopLst));
                startActivity(shoppingActivityIntent);


                }
        });
    }
}
