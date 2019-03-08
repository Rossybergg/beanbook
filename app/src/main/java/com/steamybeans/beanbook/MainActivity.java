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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

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
    private Authentication authentication;
    private VideoView VIDloginBG;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new Session(getApplicationContext());
        System.out.println(session.getUsername());
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
        ETemail = (EditText) findViewById(R.id.ETemail);
        ETpassword = (EditText) findViewById(R.id.ETpassword);
        BTNlogIn = (Button) findViewById(R.id.BTNlogIn);
        BTNsignUp = (Button) findViewById(R.id.BTNsignUp);

        BTNlogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ETemail.getText().toString();

                //create new authentication object
                authentication = new Authentication();

                //create a db ref object
                final DatabaseReference database;

                //connect to the child users
                database = FirebaseDatabase.getInstance().getReference().child("Users");

                //encodes the email to a valid format for firebase
                final String encodedEmail = authentication.encodeString(email);

                final String password = ETpassword.getText().toString();
                ETemail.onEditorAction(EditorInfo.IME_ACTION_DONE);
                ETpassword.onEditorAction(EditorInfo.IME_ACTION_DONE);

                //checks if an email has been entered
                if (ETemail.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "No email entered", Toast.LENGTH_LONG).show();
                } else {

                    //checks if the email exists
                    database.child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.exists()) {

                                //check that the password is correct
                                database.child(encodedEmail).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        //gets actual password from db
                                        String actualPassword = dataSnapshot.child("password").getValue().toString();
                                        //checks if password is correct
                                        if (authentication.correctPassword(actualPassword, password)) {
                                            //set the session
                                            session.setUsername(ETemail.getText().toString());
                                            //if it is correct got to new page
                                            startActivity(new Intent(MainActivity.this, PostListActivity.class));
                                        } else {
                                            //if password is incorrect Toast it
                                            Toast.makeText(MainActivity.this, "Password incorrect", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });

                            } else {
                                //if email does not exist print toast
                                Toast.makeText(MainActivity.this, "No user associated with this email address", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
        });

        BTNsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Signup.class));
            }
        });
    }
}