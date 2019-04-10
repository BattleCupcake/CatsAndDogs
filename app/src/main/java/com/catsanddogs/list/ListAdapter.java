package com.catsanddogs.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.catsanddogs.R;
import com.catsanddogs.network.ItemData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListItemHolder> {

    private List<ItemData> listData;
    private OnListItemClick onListItemClick;

    public ListAdapter(OnListItemClick onListItemClick) {
        super();
        this.onListItemClick = onListItemClick;
    }

    public void updateList(List<ItemData> newListData) {
        if (newListData == null) {
            return;
        }
        listData = newListData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        ListItemHolder listItemHolder = new ListItemHolder(vItem, new OnItemClick() {
            @Override
            public void onClick(int id) {
                if (onListItemClick != null) {
                    onListItemClick.onClick(id, listData.get(id).getUrl(), listData.get(id).getTitle());
                }
            }
        });

        return listItemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        Picasso.get()
                .load(listData.get(position).getUrl())
                .into(holder.imageView);
        holder.textView.setText(String.valueOf(position + 1) + "\n" + listData.get(position).getTitle());
        holder.linearLayout.setTag(position);
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }
}

