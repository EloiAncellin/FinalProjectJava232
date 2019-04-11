package eloi.ancellin.finalproject.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.sql.Connection;

import eloi.ancellin.finalproject.R;
import eloi.ancellin.finalproject.modele.databaseAcess;

public class MainActivity extends AppCompatActivity {

    private Button loginBut;
    private Button registerBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBut= (Button)findViewById(R.id.activity_main_button_login);
        registerBut= (Button)findViewById(R.id.activity_main_button_register);

        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivityIntent = new Intent(MainActivity.this, Login.class);
                startActivity(registerActivityIntent);
                databaseAcess db = new databaseAcess();
                Connection con = db.connectToDB();
            }
        });

        registerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent registerActivityIntent = new Intent(MainActivity.this, Register.class);
                 startActivity(registerActivityIntent);
            }
        });

    }
}
