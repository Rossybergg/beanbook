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
        setContentView(R.layout.activity_view_and_comment_on_post);

        TVUser = (TextView) findViewById(R.id.TVUser);
        TVTime = (TextView) findViewById(R.id.TVTime);
        TVPost = (TextView) findViewById(R.id.TVPost);
        TVLikes = (TextView) findViewById(R.id.TVLikes);
        BTNLike = (Button) findViewById(R.id.BTNLike);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String time = intent.getStringExtra("time");

        FirebaseDatabase.getInstance().getReference().child("Users").child(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        TVUser.setText(dataSnapshot.child("fullName").getValue().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }
}
