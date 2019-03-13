package com.steamybeans.beanbook;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewFriendsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Session session;
    private Authentication authentication;
    private TextView TVfriends;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friends);
        session = new Session(getApplicationContext());
        authentication = new Authentication();
        linearLayout = (LinearLayout) findViewById(R.id.Layviewfriends);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
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
            startActivity(new Intent(ViewFriendsActivity.this, AddFriendActivity.class));
        } else if (id == R.id.nav_view_friends) {
            startActivity(new Intent(ViewFriendsActivity.this, ViewFriendsActivity.class));
        } else if (id == R.id.nav_home) {
            startActivity(new Intent(ViewFriendsActivity.this, Home.class));
        } else if (id == R.id.nav_coffeeFinder) {
            startActivity(new Intent(ViewFriendsActivity.this, CoffeeFinder.class));
        } else if (id == R.id.nav_logout) {
            session.logout();
            startActivity(new Intent(ViewFriendsActivity.this, MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onResume() {
        super.onResume();

        String unencodedUser = session.getUsername();
        final String user = authentication.encodeString(unencodedUser);

        FirebaseDatabase.getInstance().getReference().child("Users").child(user).child("friends")
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int i = 1 ;
                        for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            TVfriends = new TextView(ViewFriendsActivity.this);
                            TVfriends.setText(NameFormatter.capitalize(snapshot.getValue() + " "));
                            TVfriends.setId(i);
                            TVfriends.setTextColor(Color.BLACK);
                            TVfriends.setTextSize(25);
                            TVfriends.setBackgroundColor(Color.WHITE);
                            TVfriends.setHeight(200);
                            TVfriends.setBackgroundResource(R.drawable.textbox_xml);
                            linearLayout.addView(TVfriends);
                            TVfriends.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    FirebaseDatabase.getInstance().getReference().child("friends").child(snapshot.getKey()).setValue(snapshot.child("fullName").getValue());


                                }
                            });
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) TVfriends.getLayoutParams();
                            params.setMargins(0, 20, 0, 0); //substitute parameters for left, top, right, bottom
                            TVfriends.setLayoutParams(params);
                            i++;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }

}