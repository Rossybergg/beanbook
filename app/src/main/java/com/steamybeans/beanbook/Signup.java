package com.steamybeans.beanbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Signup extends AppCompatActivity {

    private EditText ETuserName;
    private EditText ETemail;
    private EditText ETpassword;
    private Button BTNsignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
    }

    private void init() {
        ETuserName = (EditText) findViewById(R.id.ETuserName);
        ETemail = (EditText) findViewById(R.id.ETemail);
        ETpassword = (EditText) findViewById(R.id.ETpassword);
        BTNsignUp = (Button) findViewById(R.id.BTNsignUp);

        BTNsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ETuserName.getText().toString();
                String email = ETemail.getText().toString();
                String password = ETpassword.getText().toString();
                startActivity(new Intent(Signup.this, MainActivity.class));
            }
        });
    }
}
