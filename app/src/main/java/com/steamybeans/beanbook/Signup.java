package com.steamybeans.beanbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    private EditText ETfullName;
    private EditText ETemail;
    private EditText ETpassword;
    private Button BTNsignUp;
    private User user;
    private Authentication authentication;
    private FirebaseConnection firebaseConnection;


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
        authentication = new Authentication();
        firebaseConnection = new FirebaseConnection();

        BTNsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName = ETfullName.getText().toString();
                String email = ETemail.getText().toString();
                String password = ETpassword.getText().toString();

                user.setFullName(fullName);
                user.setPassword(password);

                //checks for any empty fields
                if ((ETfullName.getText().toString().equals("")) || (ETemail.getText().toString().equals("")) || (ETpassword.getText().toString().equals(""))) {
                    Toast.makeText(Signup.this, "Please fill in all fields.", Toast.LENGTH_LONG).show();
                }

                //checks if it is a valid email
                else if (authentication.validEmail(email)) {

                    //encodes the email to a valid format for firebase
                    String encodedEmail = authentication.encodeString(email);

                    //checks if email exists and adds it if it doesn't
                    firebaseConnection.addToDb(encodedEmail, user, Signup.this);

                    //redirects to log in page
                    startActivity(new Intent(Signup.this, MainActivity.class));

                } else {
                    Toast.makeText(Signup.this, "Invalid email address.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
