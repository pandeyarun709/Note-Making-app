package com.example.arunpandey.notemakingapp;

import android.content.Intent;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {

    Prefrences preferences;
    SQLoperation sqLoperation;
    EditText name;
    EditText password;

    Button login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        login = findViewById(R.id.button);

        preferences = Prefrences.getInstance(getApplicationContext());
        sqLoperation = SQLoperation.getInstances(getApplicationContext());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                preferences.createUser(name.getText().toString(),password.getText().toString());

                if(!sqLoperation.checkTable(name.getText().toString()))
                {
                   sqLoperation.createTable(name.getText().toString());
                }
                Intent intent = new Intent(MainActivity.this,NotesActivity.class);
                startActivity(intent);
                finish();

            }
        });



    }

}
