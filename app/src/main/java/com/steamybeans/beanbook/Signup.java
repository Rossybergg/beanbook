package com.steamybeans.beanbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    private EditText ETfullName;
    private EditText ETemail;
    private EditText ETpassword;
    private Button BTNsignUp;
    private DatabaseReference database;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
    }

    private void init() {
        ETfullName = (EditText) findViewById(R.id.ETemail);
        ETemail = (EditText) findViewById(R.id.ETemail);
        ETpassword = (EditText) findViewById(R.id.ETpassword);
        BTNsignUp = (Button) findViewById(R.id.BTNsignUp);
        user = new User();
        database = FirebaseDatabase.getInstance().getReference().child("Users");

        BTNsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = ETfullName.getText().toString();
                String email = ETemail.getText().toString();
                String password = ETpassword.getText().toString();

                user.setFullName(fullName);
                user.setPassword(password);

                database.child(email).setValue(user);

                startActivity(new Intent(Signup.this, MainActivity.class));
            }
        });
    }
}
