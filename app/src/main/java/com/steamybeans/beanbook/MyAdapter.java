package com.steamybeans.beanbook;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
    final ListItem listItem = listItems.get(i);

        final TextView likesField = viewHolder.TVLikes;

        viewHolder.TVUser.setText(listItem.getUser());
        viewHolder.TVTime.setText(listItem.getDisplayTimeSincePost());
        viewHolder.TVPost.setText(listItem.getPost());
        viewHolder.TVLikes.setText(listItem.getLikes());

        viewHolder.BTNLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get instance of database
                final DatabaseReference database;
                database = FirebaseDatabase.getInstance().getReference().child("Users").child(listItem.getEmail()).child("posts").child(listItem.getUid()).child("likes");

                // get current user session
                Session session;
                Authentication authentication;
                session = new Session(context.getApplicationContext());
                authentication = new Authentication();
                String unencodedUser = session.getUsername();
                final String user = authentication.encodeString(unencodedUser);

                // adds like to database and reloads likes field
                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        database.child(user).setValue("1");
                        ReloadPostLikes reloadPostLikes = new ReloadPostLikes();
                        reloadPostLikes.reloadLikes(listItem.getEmail(), listItem.getUid(), likesField);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        viewHolder.BTNComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ViewAndCommentOnPost.class);
                i.putExtra("email", listItem.getEmail());
                i.putExtra("uid", listItem.getUid());
                i.putExtra("displayTime", listItem.getDisplayTimeSincePost());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {

        int arr = 0;

        try{
            if (listItems.size() == 0){
                arr = 0;
            } else {
                arr = listItems.size();
            }
        } catch (Exception e){

        }
        return arr;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TVUser;
        public TextView TVTime;
        public TextView TVPost;
        public TextView TVLikes;
        public Button BTNLike;
        public Button BTNComments;


        public ViewHolder(View itemView) {
            super(itemView);

            TVUser = (TextView) itemView.findViewById(R.id.TVUser);
            TVTime = (TextView) itemView.findViewById(R.id.TVTime);
            TVPost = (TextView) itemView.findViewById(R.id.TVPost);
            TVLikes = (TextView) itemView.findViewById(R.id.TVLikes);
            BTNLike = (Button) itemView.findViewById(R.id.BTNLike);
            BTNComments = (Button) itemView.findViewById(R.id.BTNComments);
        }
    }
}
