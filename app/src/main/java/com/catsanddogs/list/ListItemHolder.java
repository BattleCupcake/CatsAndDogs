package com.catsanddogs.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catsanddogs.R;

public class ListItemHolder extends RecyclerView.ViewHolder {
    public LinearLayout linearLayout;
    public ImageView imageView;
    public TextView textView;

    public ListItemHolder(View itemView, final OnItemClick onItemClick) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.ll_root);
        imageView = itemView.findViewById(R.id.item_image);
        textView = itemView.findViewById(R.id.item_text);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onClick((int) linearLayout.getTag());
            }
        });
    }
}
