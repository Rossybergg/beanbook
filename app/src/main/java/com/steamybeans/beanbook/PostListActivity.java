package com.steamybeans.beanbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostListActivity extends AppCompatActivity {

    TextView post;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        post = (TextView) findViewById(R.id.postContent);


        reff = FirebaseDatabase.getInstance().getReference().child("Users").child("u").child("posts");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                long numPostsLong = dataSnapshot.getChildrenCount();
                int numPosts = (int) numPostsLong;
                String[] postList = new String[numPosts];
                for (int i = 0; i < numPosts; i++) {
                    String childName = "post" + (i);
                    postList[i] = dataSnapshot.child(childName).getValue().toString();
                }

                post.setText(postList[1]);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });




    }
}
