package com.steamybeans.beanbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewAndCommentOnPost extends AppCompatActivity

            implements NavigationView.OnNavigationItemSelectedListener {


    public TextView TVUser;
    public TextView TVTime;
    public TextView TVPost;
    public TextView TVLikes;
    public Button BTNLike;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_comment_on_post);

        TVUser = (TextView) findViewById(R.id.TVUser);
        TVTime = (TextView) findViewById(R.id.TVTime);
        TVPost = (TextView) findViewById(R.id.TVPost);
        TVLikes = (TextView) findViewById(R.id.TVLikes);
        BTNLike = (Button) findViewById(R.id.BTNLike);
        session = new Session(getApplicationContext());

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        final String time = intent.getStringExtra("time");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout4);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView TVemailaddress = header.findViewById(R.id.TVemailAddress);
        TextView TVfullName = header.findViewById(R.id.TVfullName);

        //Set Navbar Headers text
        TVemailaddress.setText(session.getUsername());
        TVfullName.setText(session.getFullName());
        navigationView.setNavigationItemSelectedListener(this);



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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_friend) {
            startActivity(new Intent(ViewAndCommentOnPost.this, AddFriendActivity.class));
        } else if (id == R.id.nav_view_friends) {
            startActivity(new Intent(ViewAndCommentOnPost.this, ViewFriendsActivity.class));
        } else if (id == R.id.nav_home) {
            startActivity(new Intent(ViewAndCommentOnPost.this, Home.class));
        }  else if (id == R.id.nav_coffeeFinder) {
            startActivity(new Intent(ViewAndCommentOnPost.this, CoffeeFinder.class));
        } else if (id == R.id.nav_logout) {
            session.logout();
            startActivity(new Intent(ViewAndCommentOnPost.this, MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout4);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
