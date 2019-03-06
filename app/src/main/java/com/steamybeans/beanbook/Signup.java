package com.steamybeans.beanbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        ETfullName = (EditText) findViewById(R.id.ETfullName);
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

                if (validEmail(email)) {
                    String encodedEmail = encodeString(email);
                    database.child(encodedEmail).setValue(user);

                    startActivity(new Intent(Signup.this, MainActivity.class));
                } else {
                    Toast.makeText(Signup.this, "Invalid email address", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean validEmail(String email) {
        Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher matcher = pattern.matcher(email);
        boolean matchFound = matcher.matches();
        boolean result = false;
        if (matchFound) {
            result = true;
        }
        return result;
    }

    public static String encodeString(String string) {
        return string.replace(".", ",");
    }

}
