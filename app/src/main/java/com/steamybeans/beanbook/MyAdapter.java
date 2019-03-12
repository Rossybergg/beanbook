package com.steamybeans.beanbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    ListItem listItem = listItems.get(i);

        viewHolder.TVUser.setText(listItem.getUser());
        viewHolder.TVTime.setText(listItem.getTime());
        viewHolder.TVPost.setText(listItem.getPost());
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

        public ViewHolder(View itemView) {
            super(itemView);

            TVUser = (TextView) itemView.findViewById(R.id.TVUser);
            TVTime = (TextView) itemView.findViewById(R.id.TVTime);
            TVPost = (TextView) itemView.findViewById(R.id.TVPost);
        }
    }
}
