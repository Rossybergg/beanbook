package com.steamybeans.beanbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostListActivity extends AppCompatActivity {

    DatabaseReference reff;
    TextView temp;
    LinearLayout linearLayout;
    private Button BTNlogOut;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        session = new Session(getApplicationContext());
        System.out.println(session.getUsername());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        linearLayout = (LinearLayout) findViewById(R.id.postLinearLayout);
        setSupportActionBar(toolbar);

        reff = FirebaseDatabase.getInstance().getReference().child("Users").child("u").child("posts");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                long numPostsLong = dataSnapshot.getChildrenCount();
                int numPosts = (int) numPostsLong;
                String[] postList = new String[numPosts];
                //Populating array of posts
                for (int i = 0; i < numPosts; i++) {
                    String childName = "post" + (i);
                    postList[i] = dataSnapshot.child(childName).getValue().toString();
                }
                //Creating text field for each post
                for (int i = 0; i < numPosts; i++) {
                    temp = new TextView(PostListActivity.this);
                    temp.setText(postList[i]);
                    linearLayout.addView(temp);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        init();

    }

    public void init() {
        BTNlogOut = (Button) findViewById(R.id.BTNlogOut);

        BTNlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logout();
                startActivity(new Intent(PostListActivity.this, MainActivity.class));
            }
        });
    }
}
