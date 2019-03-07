package com.steamybeans.beanbook;

import android.content.Context;
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


    final Context context = this;



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
                final String password = ETpassword.getText().toString();
                ETemail.onEditorAction(EditorInfo.IME_ACTION_DONE);
                ETpassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
                authentication = new Authentication();
                firebaseConnection = new FirebaseConnection();

                if (ETemail.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "No email entered", Toast.LENGTH_LONG).show();
                } else {

                    //encodes the email to a valid format for firebase
                    String encodedEmail = authentication.encodeString(email);

                //connects to firebase db and checks if email is valid and password
                firebaseConnection.emailExists(encodedEmail, password, TVmessage);
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
