package com.steamybeans.beanbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewAndCommentOnPost extends AppCompatActivity {

    public TextView TVUser;
    public TextView TVTime;
    public TextView TVPost;
    public TextView TVLikes;
    public Button BTNLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_and_comment_on_post);

        TVUser = (TextView) findViewById(R.id.TVUser);
        TVTime = (TextView) findViewById(R.id.TVTime);
        TVPost = (TextView) findViewById(R.id.TVPost);
        TVLikes = (TextView) findViewById(R.id.TVLikes);
        BTNLike = (Button) findViewById(R.id.BTNLike);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        final String time = intent.getStringExtra("time");


        FirebaseDatabase.getInstance().getReference().child("Users").child(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                        final Home home = new Home();

                        int counter = 0;
                        for (DataSnapshot snapshot2 : dataSnapshot.child("posts").child(time).child("likes").getChildren()) {
                            counter++;
                        }

                        final int counter2 = counter;

                        TVUser.setText(dataSnapshot.child("fullName").getValue().toString());
                        TVTime.setText(time);
                        TVPost.setText(dataSnapshot.child("posts").child(time).child("content").getValue().toString());

                        TVLikes.setText(home.likesCalculator(counter));

                        BTNLike.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final DatabaseReference database;
                                database = FirebaseDatabase.getInstance().getReference().child("Users").child(email).child("posts").child(time).child("likes");

                                // getting the user session
                                Session session;
                                Authentication authentication;
                                session = new Session(getApplicationContext());
                                authentication = new Authentication();
                                String unencodedUser = session.getUsername();
                                final String user = authentication.encodeString(unencodedUser);


                                // adds like to database
                                database.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        database.child(user).setValue("1");

                                        // this is a really stupid way of doing this but we cant think of another way
                                        final int counter3 = counter2 + 1;
                                        TVLikes.setText(home.likesCalculator(counter3));

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        });



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }
}
