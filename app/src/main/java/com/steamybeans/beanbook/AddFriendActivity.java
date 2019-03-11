package com.steamybeans.beanbook;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddFriendActivity extends AppCompatActivity {

    private Session session;
    private Authentication authentication;
    private TextView TVfriends;
    private LinearLayout linearLayout;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        session = new Session(getApplicationContext());
        authentication = new Authentication();
        linearLayout = (LinearLayout) findViewById(R.id.Layfriends);
    }

    protected void onResume() {
        super.onResume();

        String unencodedUser = session.getUsername();
        final String user = authentication.encodeString(unencodedUser);
        System.out.println(user);
        database = FirebaseDatabase.getInstance().getReference().child("Users").child(user);
        FirebaseDatabase.getInstance().getReference().child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int i = 1 ;
                        for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {


                            TVfriends = new TextView(AddFriendActivity.this);
                            TVfriends.setText(snapshot.getKey());
                            TVfriends.setId(i);
                            TVfriends.setTextColor(Color.WHITE);
                            TVfriends.setTextSize(25);
                            TVfriends.setBackgroundColor(Color.BLACK);
                            TVfriends.setHeight(200);
                            linearLayout.addView(TVfriends);
                            TVfriends.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    database.child("friends").child(snapshot.getKey()).setValue(snapshot.child("fullName").getValue());


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
