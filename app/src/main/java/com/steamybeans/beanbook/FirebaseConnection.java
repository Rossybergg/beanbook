package com.steamybeans.beanbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseConnection {

    public FirebaseConnection() {
    }

    public String getPassword(String encodedEmail) {

        final String[] actualPassword = {};
        DatabaseReference database;
        database = FirebaseDatabase.getInstance().getReference().child("Users").child(encodedEmail);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                actualPassword[0] = dataSnapshot.child("password").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return actualPassword[0];
    }

    public boolean emailExists(final String encodedEmail) {
        final boolean[] result = {false};
        final DatabaseReference database;
        database = FirebaseDatabase.getInstance().getReference().child("Users");

        database.child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    result[0] = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });

        return result[0];
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

        public void deleteFromDb(final String email) {
            final DatabaseReference database;
            database = FirebaseDatabase.getInstance().getReference().child("Users");
            database.child(email).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().removeValue();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

}

