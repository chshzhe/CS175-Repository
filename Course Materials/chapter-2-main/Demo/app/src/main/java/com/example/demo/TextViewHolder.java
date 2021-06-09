package com.example.demo;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mTextView;

    public TextViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.text);
        itemView.setOnClickListener(this);
    }

    public void bind(String text) {
        mTextView.setText(text);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), MyActivity.class);
        intent.putExtra("extra", mTextView.getText().toString());
        v.getContext().startActivity(intent);
    }
}
