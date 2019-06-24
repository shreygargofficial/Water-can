package com.qapaper.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    DatabaseReference myRef;
    EditText userC;
    EditText passC;
    String user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void loginValidate(View v)
    {

        userC=findViewById(R.id.user);
        passC=findViewById(R.id.pass);
        user=userC.getText().toString().trim();
        pass=passC.getText().toString().trim();
        if(user.equals("")||pass.equals(""))
            Toast.makeText(getApplicationContext(), "Please Fill All Field!", Toast.LENGTH_SHORT).show();
        else {

            myRef = FirebaseDatabase.getInstance().getReference().child("Member");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int error = 0;
                    if (!dataSnapshot.child(user).exists()) {
                        error = 1;
                    } else {


                        String userF = dataSnapshot.child(user).child("username").getValue().toString();
                        String passF = dataSnapshot.child(user).child("pass").getValue().toString();
                        if (user.equals(userF) && pass.equals(passF)) {
                            Toast.makeText(getApplicationContext(), "Successful!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            error = 1;

                        }
                    }
                    if (error == 1)
                        Toast.makeText(getApplicationContext(), "Wrong Credentials!", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    public void signUp(View v){
        Intent i=new Intent(getApplicationContext(),signup.class);
        startActivity(i);
    }
}