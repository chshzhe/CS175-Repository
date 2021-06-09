package com.example.project;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.model.Message;
import com.facebook.drawee.view.SimpleDraweeView;


import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.VideoViewHolder> {
    private List<Message> data;

    public void setData(List<Message> messageList) {
        data = messageList;
        Log.d("TEST","setData"+data.toString());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new VideoViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private SimpleDraweeView coverSD;
        private TextView fromTV;
        private TextView toTV;
        private TextView contentTV;
        private String url;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            fromTV = itemView.findViewById(R.id.tv_from);
            //toTV = itemView.findViewById(R.id.tv_to);
            //contentTV = itemView.findViewById(R.id.tv_content);
            coverSD = itemView.findViewById(R.id.sd_cover);
            itemView.setOnClickListener(this);
        }

        public void bind(Message message) {
            coverSD.setImageURI(message.getImageUrl());
            fromTV.setText("FROM: " + message.getFrom());
            //contentTV.setText(message.getContent());
            //toTV.setText("TO: " + message.getTo());
            url=message.getVideoUrl();
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), MediaPlayerActivity.class);
            intent.putExtra("text", url);
            v.getContext().startActivity(intent);
        }
    }

}
