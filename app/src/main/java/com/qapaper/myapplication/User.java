package com.qapaper.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class User extends AppCompatActivity {
SharedPreferences sharedPreferencesobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        sharedPreferencesobj=getApplicationContext().getSharedPreferences("session_user",0);

        TextView nametv=findViewById(R.id.name);
        TextView emailtv=findViewById(R.id.email);
        TextView phonetv=findViewById(R.id.Phone);
        nametv.setText(sharedPreferencesobj.getString("username",""));
        phonetv.setText(sharedPreferencesobj.getString("phone",""));
        emailtv.setText(sharedPreferencesobj.getString("email",""));


    }
}