package com.steamybeans.beanbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText ETuserName;
    private EditText ETpassword;
    private Button BTNlogIn;
    private Button BTNsignUp;
    private TextView TVmessage;

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        ETuserName = (EditText) findViewById(R.id.ETuserName);
        ETpassword = (EditText) findViewById(R.id.ETpassword);
        BTNlogIn = (Button) findViewById(R.id.BTNlogIn);
        BTNsignUp = (Button) findViewById(R.id.BTNsignUp);
        TVmessage = (TextView) findViewById(R.id.TVmessage);

        BTNlogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ETuserName.getText().toString();
                String password = ETpassword.getText().toString();
                if ((username.equals("Jedd")) && (password.equals("password"))) {
                    TVmessage.setText("Success");
                } else {
                    TVmessage.setText("Failed");
                }
                ETuserName.onEditorAction(EditorInfo.IME_ACTION_DONE);
                ETpassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });

        BTNsignUp.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Signup.class));
            }
        }));


//        BTNsignUp.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, Signup.class);
//                startActivity(intent);
//            }
//        });
    }
}
