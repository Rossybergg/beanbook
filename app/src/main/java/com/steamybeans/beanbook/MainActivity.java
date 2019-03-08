package com.steamybeans.beanbook;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private EditText ETemail;
    private EditText ETpassword;
    private Button BTNlogIn;
    private Button BTNsignUp;
    private TextView TVmessage;
    private FirebaseConnection firebaseConnection;
    private Authentication authentication;
    private VideoView VIDloginBG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        TVmessage = (TextView) findViewById(R.id.TVmessage);

        BTNlogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ETemail.getText().toString();

                //create new Firebase object
                firebaseConnection = new FirebaseConnection();

                //create new authentication object
                authentication = new Authentication();

                //encodes the email to a valid format for firebase
                String encodedEmail = authentication.encodeString(email);

                //connect to db
                firebaseConnection.connectToDB();

                final String password = ETpassword.getText().toString();
                ETemail.onEditorAction(EditorInfo.IME_ACTION_DONE);
                ETpassword.onEditorAction(EditorInfo.IME_ACTION_DONE);


                //checks if an email has been entered
                if (ETemail.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "No email entered", Toast.LENGTH_LONG).show();
                } else {


                    //checks if email exists
                    if (firebaseConnection.emailExists(encodedEmail)) {

                        //gets the actual password
                        firebaseConnection.password(encodedEmail);

                        //sets the actual password
                        String actualPassword = firebaseConnection.getActualPassword();

                        //checks if password is correct if email exists
                        if (authentication.correctPassword(actualPassword, password)) {

                            //reset actual password
                            firebaseConnection.resetActualPassword();

                            //redirects to homepage
//                            startActivity(new Intent(MainActivity.this, PostListActivity.class));

                            Toast.makeText(MainActivity.this, "Password correct", Toast.LENGTH_LONG).show();

                        } else {

                            //gives toast is password is incorrect
                            Toast.makeText(MainActivity.this, "Password incorrect", Toast.LENGTH_LONG).show();
                        }

                        //gives toast is email doesn't exist
                    } else {
                        Toast.makeText(MainActivity.this, "Email doesn't exist", Toast.LENGTH_LONG).show();
                        firebaseConnection.getResult();
                    }
                }

            }
        });

        BTNsignUp.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Signup.class));
            }
        }));
    }
}


