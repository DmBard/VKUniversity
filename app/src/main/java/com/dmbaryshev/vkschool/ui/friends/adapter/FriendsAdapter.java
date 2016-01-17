package com.dmbaryshev.vkschool.ui.friends.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.network.model.VkUser;
import com.dmbaryshev.vkschool.utils.DLog;

import java.util.ArrayList;
import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private static final String TAG = DLog.makeLogTag(FriendsAdapter.class);
    private List<VkUser> items;
    private Context      context;

    public FriendsAdapter(List<VkUser> items) {
        if (items != null) {
            this.items = new ArrayList<>(items);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (items == null || items.size() <= 0) {return;}

        final VkUser item = items.get(position);
        holder.tvName.setText(String.format("%s %s", item.getFirstName(), item.getLastName()));
        holder.tvStatus.setText(item.getOnline() == 1 ? context.getString(R.string.status_online) : "");
        Glide.with(context).load(item.getPhoto100()).into(holder.ivAvatar);
    }

    @Override
    public int getItemCount() {
        if (items != null && !items.isEmpty()) {
            return items.size();
        } else { return 0; }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView ivAvatar;
        protected TextView  tvName;
        protected TextView  tvStatus;

        public ViewHolder(View view) {
            super(view);
            ivAvatar = (ImageView) view.findViewById(R.id.iv_avatar);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvStatus = (TextView) view.findViewById(R.id.tv_status);
        }
    }
}
