package com.steamybeans.beanbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ViewAndCommentOnPost extends AppCompatActivity

            implements NavigationView.OnNavigationItemSelectedListener {


    public TextView TVUser;
    public TextView TVTime;
    public TextView TVPost;
    public TextView TVLikes;
    public Button BTNLike;
    private Button BTNadd;
    private Session session;
    private String email;
    private String uid;
    private String displayTime;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CommentListitem> listItems;
    private LinearLayoutManager layoutManager;
    private TextView PTaddComment;
    private Authentication authentication;
    private LikesStringCreator likesCalculator;


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
        likesCalculator = new LikesStringCreator();

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        uid = intent.getStringExtra("uid");
        displayTime = intent.getStringExtra("displayTime");


        // set up app + nav bar
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

        // set nav bar header text
        TVemailaddress.setText(session.getUsername());
        TVfullName.setText(session.getFullName());
        navigationView.setNavigationItemSelectedListener(this);

        // load post
        FirebaseDatabase.getInstance().getReference().child("Users").child(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                        // load number of likes
                        final LikesStringCreator likesStringCreator = new LikesStringCreator();
                        int counter = 0;
                        for (DataSnapshot snapshot2 : dataSnapshot.child("posts").child(uid).child("likes").getChildren()) {
                            counter++;
                        }

                        final int counter2 = counter;

                        TVUser.setText(NameFormatter.capitalize(dataSnapshot.child("fullName").getValue().toString()));
                        TVTime.setText(displayTime);
                        TVPost.setText(dataSnapshot.child("posts").child(uid).child("content").getValue().toString());

                        TVLikes.setText(likesCalculator.likesCalculator(counter));

                        // set up liking button
                        BTNLike.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final DatabaseReference database;
                                database = FirebaseDatabase.getInstance().getReference().child("Users").child(email).child("posts").child(uid).child("likes");

                                // getting the user session
                                Session session;
                                Authentication authentication;
                                session = new Session(getApplicationContext());
                                authentication = new Authentication();
                                String unencodedUser = session.getUsername();
                                final String user = authentication.encodeString(unencodedUser);


                                // add like to database and reload likes field
                                database.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        database.child(user).setValue("1");
                                        ReloadPostLikes reloadPostLikes = new ReloadPostLikes();
                                        reloadPostLikes.reloadLikes(email, uid, TVLikes);

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

    public void onResume() {
        super.onResume();

        PTaddComment = (TextView) findViewById(R.id.PTaddComment);
        BTNadd = (Button) findViewById(R.id.BTNadd);

        // find recycler view
        recyclerView = (RecyclerView)findViewById(R.id.RVFeed);

        // set linear layout manager
        layoutManager = new LinearLayoutManager(ViewAndCommentOnPost.this);
        System.out.println(recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        //Creates the array that stores the comments
        listItems = new ArrayList<>();

        // getting all comments
        FirebaseDatabase.getInstance().getReference().child("Users").child(email).child("posts").child(uid)
                .child("comments").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            //getting the time of a particular comment
                            final String commentTime = snapshot.child("date").getValue().toString();

                            //getting the comment itself
                            final String comment = snapshot.child("content").getValue().toString();

                            //getting the email associated with the comment
                            final String commentersEmail = snapshot.child("email").getValue().toString();


                            //getting the name of the commenter from the email
                            FirebaseDatabase.getInstance().getReference().child("Users").child(commentersEmail)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            final String name = NameFormatter.capitalize(dataSnapshot.child("fullName").getValue().toString());

                                            CommentListitem listItem = new CommentListitem(
                                                    name,
                                                    commentTime,
                                                    comment
                                            );
                                            listItems.add(listItem);
                                            System.out.println(listItems);
                                            adapter = new CommentItemAdapter(listItems, ViewAndCommentOnPost.this);
                                            recyclerView.setAdapter(adapter);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });



                        }
                    }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        BTNadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String postComment = PTaddComment.getText().toString();
                authentication = new Authentication();

                final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                final LocalDateTime now = LocalDateTime.now();
                final DatabaseReference database;
                database = FirebaseDatabase.getInstance().getReference().child("Users").child(email).child("posts")
                        .child(uid).child("comments").push();

                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (PTaddComment.getText().toString().trim().length() > 0) {
                            database.child("date").setValue(formatter.format(now));
                            database.child("content").setValue(postComment);
                            database.child("email").setValue(authentication.encodeString(session.getUsername()));
                            PTaddComment.setText("");
                            recreate();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }



}
