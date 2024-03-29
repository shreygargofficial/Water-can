package com.qapaper.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemSelectedListener;
public class Location extends AppCompatActivity {
    Spinner loc_spinner;
    List<String> loc_list;
    List<Double> loc_price;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    RelativeLayout rl;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        pref=getApplicationContext().getSharedPreferences("session_user",0);
        if(!pref.contains("username"))
        {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
            finish();
        }
        TextView textView = findViewById(R.id.heading_loc);
        //font added
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Pacifico-Regular.ttf");
//        textView.setTypeface(typeface);

        pref = getApplicationContext().getSharedPreferences("session_user", 0);
        editor = pref.edit();
        loc_spinner = findViewById(R.id.loc_spinner);
        rl=findViewById(R.id.loader);
        rl.setVisibility(View.VISIBLE);
        loc_list = new ArrayList<String>();
        loc_price = new ArrayList<Double>();
        //Adding Default value for location name and price
        loc_list.add("Select One!!");
        loc_price.add(0.00);
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference().child("location");
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    LocationDetails locationDetails = dataSnapshot.child(String.valueOf(i)).getValue(LocationDetails.class);

                    String name = locationDetails.getName().toUpperCase();
                    Double price = locationDetails.getPrice();
                    loc_list.add(name);
                    loc_price.add(price);

                }
                rl.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Adapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, loc_list);

        loc_spinner.setAdapter((SpinnerAdapter) adapter);



    }

    public void loc_submit(View view) {
        String location = loc_spinner.getItemAtPosition(loc_spinner.getSelectedItemPosition()).toString();
        if (loc_spinner.getSelectedItemPosition() != 0) {
            Double price = loc_price.get(loc_spinner.getSelectedItemPosition());

            //Log.d("shs",location+""+String.valueOf(price));
            editor.putString("location", location);
            editor.putString("price", String.valueOf(price));
            editor.apply();

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }


    }

    @Override
    public void onBackPressed() {
        i++;
        if(i==2)
        super.onBackPressed();
        else
        Toast.makeText(this,"Press Again to Exit",Toast.LENGTH_SHORT).show();


    }

    public void home(View view) {

    }
    public void myorders(View view) {

        Intent i =new Intent(getApplicationContext(),order_container.class);
        startActivity(i);
    }
    public void myinfo(View view) {
        Intent i=new Intent(this,User.class);
        startActivity(i);

    }
}
