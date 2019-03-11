package com.steamybeans.beanbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;


public class FirebaseConnection {

    private static boolean result;
    private static String actualPassword = "*";

    public FirebaseConnection() {
    }

    public static boolean getResult() {
        System.out.println(result);
        return result;
    }

    public boolean resultTrue() {
        System.out.println("changing result to true");
        result = true;
        return result;
    }

//    public void resetResult() {
//        result = false;
//        System.out.println(result);
//    }

    public String getActualPassword() {
        return actualPassword;
    }

    public void resetActualPassword() {
        actualPassword = "*";
    }

    public void password(String encodedEmail) {

        DatabaseReference database;
        database = FirebaseDatabase.getInstance().getReference().child("Users").child(encodedEmail);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                actualPassword = dataSnapshot.child("password").getValue().toString();
                System.out.println(actualPassword);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void connectToDB() {
        DatabaseReference database;
        database = FirebaseDatabase.getInstance().getReference().child("Users");
    }





    public boolean emailExists(final String encodedEmail, final FirebaseConnection fb) {
        result = false;
        final Caller caller = new Caller();
        final OnGetDataListener newDataListner = new CallbackImpl();
        DatabaseReference database;
        database = FirebaseDatabase.getInstance().getReference().child("Users");
        database.child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            boolean returnTrue() {
                System.out.println("returning true");
                return true;
            }
            boolean returnFalse() {
                return false;
            }
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    System.out.println("data has been retrieved");
                    caller.register(newDataListner, fb);
                    returnTrue();
                }
                else {
                    returnFalse();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

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