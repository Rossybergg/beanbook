package com.steamybeans.beanbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CommentItemAdapter extends RecyclerView.Adapter<CommentItemAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    public CommentItemAdapter(List<ListItem>, listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int i) {
        final ListItem listItem = listItems.get(i);

        viewHolder.TVUser.setText(listItem.getUser());
        viewHolder.TVTime.setText(listItem.getTime());
        viewHolder.TVComment.setText(listItem.getComment());
    }


        public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TVUser;
        public TextView TVTime;
        public TextView TVComment;


        public ViewHolder(View itemView) {
            super(itemView);

            TVUser = (TextView) itemView.findViewById(R.id.TVUser);
            TVTime = (TextView) itemView.findViewById(R.id.TVTime);
            TVComment = (TextView) itemView.findViewById(R.id.TVComment);
        }
    }



}
