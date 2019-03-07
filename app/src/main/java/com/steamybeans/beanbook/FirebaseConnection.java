package com.steamybeans.beanbook;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.support.v4.content.ContextCompat.startActivity;

public class FirebaseConnection {

    public FirebaseConnection() {
    }

    public void validPassword(String encodedEmail, final String password, final TextView textView) {

        DatabaseReference database;
        database = FirebaseDatabase.getInstance().getReference().child("Users").child(encodedEmail);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String actualPassword = dataSnapshot.child("password").getValue().toString();

                if (actualPassword.equals(password)) {
                    textView.setText("Success");
//                    Toast.makeText(context,"Invalid password",Toast.LENGTH_LONG);
                } else {
//                    Toast.makeText(context,"Password correct",Toast.LENGTH_SHORT);
                    textView.setText("Failed");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    public void addToDb(final String encodedEmail, final User user, final Context context) {
        final DatabaseReference database;
        database = FirebaseDatabase.getInstance().getReference().child("Users");

            database.child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Toast.makeText(context, "Email already registered", Toast.LENGTH_LONG).show();
                    } else {
                        database.child(encodedEmail).setValue(user);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        }
}
