package com.steamybeans.beanbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText ETemail;
    private EditText ETpassword;
    private Button BTNlogIn;
    private Button BTNsignUp;
    private TextView TVmessage;
    private DatabaseReference database;

    final Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        ETemail = (EditText) findViewById(R.id.ETemail);
        ETpassword = (EditText) findViewById(R.id.ETpassword);
        BTNlogIn = (Button) findViewById(R.id.BTNlogIn);
        BTNsignUp = (Button) findViewById(R.id.BTNsignUp);
        TVmessage = (TextView) findViewById(R.id.TVmessage);

        BTNlogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ETemail.getText().toString();
                final String password = ETpassword.getText().toString();
                ETemail.onEditorAction(EditorInfo.IME_ACTION_DONE);
                ETpassword.onEditorAction(EditorInfo.IME_ACTION_DONE);

                String encodedEmail = encodeString(email);

                database = FirebaseDatabase.getInstance().getReference().child("Users").child(encodedEmail);
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String actualPassword = dataSnapshot.child("password").getValue().toString();

                        if (actualPassword.equals(password)) { TVmessage.setText("Success"); }
                        else { TVmessage.setText("Failed"); }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }
        });

        BTNsignUp.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Signup.class));
            }
        }));
    }

    public static String encodeString(String string) {
        return string.replace(".", ",");
    }
}
