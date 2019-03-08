package com.steamybeans.beanbook;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
    }

    protected void onResume() {
        super.onResume();

        VideoView video = findViewById(R.id.VIDloginBG);

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        Uri videoPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.loginbg);
        video.setVideoURI(videoPath);
        video.start();
    }

    private void init() {
        ETfullName = (EditText) findViewById(R.id.ETfullName);
        ETemail = (EditText) findViewById(R.id.ETemail);
        ETpassword = (EditText) findViewById(R.id.ETpassword);
        BTNsignUp = (Button) findViewById(R.id.BTNsignUp);
        user = new User();
        authentication = new Authentication();

        BTNsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName = ETfullName.getText().toString();
                String email = ETemail.getText().toString();
                String password = ETpassword.getText().toString();
                final DatabaseReference database;
                database = FirebaseDatabase.getInstance().getReference().child("Users");

                user.setFullName(fullName);
                user.setPassword(password);

                //checks for any empty fields
                if ((fullName.equals("")) || (email.equals("")) || (password.equals(""))) {
                    Toast.makeText(Signup.this, "Please fill in all fields.", Toast.LENGTH_LONG).show();
                }

                //checks if it is a valid email
                else if (authentication.validEmail(email)) {

                    //encodes the email to a valid format for firebase
                    final String encodedEmail = authentication.encodeString(email);

                    //checks if email exists and adds it if it doesn't
                    database.child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(Signup.this, "Email already registered", Toast.LENGTH_LONG).show();
                            } else {
                                database.child(encodedEmail).setValue(user);
                                //redirects to log in page
                                startActivity(new Intent(Signup.this, MainActivity.class));

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                } else {
                    Toast.makeText(Signup.this, "Invalid email address.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
