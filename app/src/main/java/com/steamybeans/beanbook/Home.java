package com.steamybeans.beanbook;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Home extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {


    private EditText ETaddPost;
    private Button BTNrefresh;
    private Session session;
    private Authentication authentication;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new Session(getApplicationContext());
        authentication = new Authentication();

        String unencodedUser = session.getUsername();
        final String user = authentication.encodeString(unencodedUser);
        System.out.println(user);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ETaddPost = (EditText) findViewById(R.id.ETaddPost);
        BTNrefresh = (Button) findViewById(R.id.BTNrefresh);


        FloatingActionButton BTNaddPost = (FloatingActionButton) findViewById(R.id.BTNaddPost);
        BTNaddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                final LocalDateTime now = LocalDateTime.now();
                final DatabaseReference database;
                database = FirebaseDatabase.getInstance().getReference().child("Users").child(user)
                        .child("posts").push();

                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (ETaddPost.getText().toString().trim().length() > 0) {
                            database.child("date").setValue(formatter.format(now));
                            database.child("content").setValue(ETaddPost.getText().toString());
                            ETaddPost.setText("");
                            recreate();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        BTNrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            startActivity(new Intent(Home.this, AddFriendActivity.class));
        } else if (id == R.id.nav_view_friends) {
            startActivity(new Intent(Home.this, ViewFriendsActivity.class));
        } else if (id == R.id.nav_home) {
            startActivity(new Intent(Home.this, Home.class));
        }  else if (id == R.id.nav_coffeeFinder) {
            startActivity(new Intent(Home.this, CoffeeFinder.class));
        } else if (id == R.id.nav_logout) {
            session.logout();
            startActivity(new Intent(Home.this, MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onResume() {
        super.onResume();

        String unencodedUser = session.getUsername();
        final String user = authentication.encodeString(unencodedUser);

        // find recycler view
        recyclerView = (RecyclerView)findViewById(R.id.RVfeed);

        // set linear layout manager
        layoutManager = new LinearLayoutManager(Home.this);
        recyclerView.setLayoutManager(layoutManager);

        //Creates the array that stores the posts
        listItems = new ArrayList<>();


        // getting all friends
        FirebaseDatabase.getInstance().getReference().child("Users").child(user).child("friends")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String friend = snapshot.getKey();
                            final String name = snapshot.getValue().toString();
                            final String email = snapshot.getKey();

                            //getting all posts of particular friend
            FirebaseDatabase.getInstance().getReference().child("Users").child(friend).child("posts")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                int counter = 0;
                                for (DataSnapshot snapshot2 : snapshot.child("likes").getChildren()) {
                                    counter++;
                                }

                                ListItem listItem = new ListItem(
                                        name,
                                        snapshot.child("content").getValue().toString(),
                                        snapshot.child("date").getValue().toString(),
                                        likesCalculator(counter),
                                        email,
                                        snapshot.getKey()
                                );
                                listItems.add(listItem);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        FirebaseDatabase.getInstance().getReference().child("Users").child(user).child("posts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            int counter = 0;
                            for (DataSnapshot snapshot2 : snapshot.child("likes").getChildren()) {
                                counter++;
                            }

                            ListItem listItem = new ListItem(
                                    session.getFullName(),
                                    snapshot.child("content").getValue().toString(),
                                    snapshot.child("date").getValue().toString(),
                                    likesCalculator(counter),
                                    user,
                                    snapshot.getKey()
                            );

                            listItems.add(listItem);

                            adapter = new MyAdapter(listItems, Home.this);
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public String likesCalculator(int numLikes) {
        if (numLikes == 0) {
            return "";
        } else if (numLikes == 1) {
            return "1 like";
        } else {
            String numValue = String.valueOf(numLikes);
            return numValue + " likes";
        }
    }
}
