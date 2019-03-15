package com.steamybeans.beanbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CommentItemAdapter extends RecyclerView.Adapter<CommentItemAdapter.ViewHolder> {

    private List<CommentListitem> listItems;
    private Context context;

    public CommentItemAdapter(List<CommentListitem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item_for_recyclerview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CommentListitem listItem = listItems.get(i);

        viewHolder.TVUser.setText(listItem.getUser());
        viewHolder.TVTime.setText(listItem.getDisplayTimeSinceComment());
        viewHolder.TVComment.setText(listItem.getComment());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
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
