package com.steamybeans.beanbook;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReloadPostLikes {

    public void reloadLikes(String email, String time, TextView likesField) {
        final TextView likesText = likesField;
        FirebaseDatabase.getInstance().getReference().child("Users").child(email).child("posts").child(time).child("likes")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        final LikesStringCreator likesStringCreator = new LikesStringCreator();

                        int counter = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            counter++;
                        }
                        likesText.setText(likesStringCreator.likesCalculator(counter));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
