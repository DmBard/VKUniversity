package com.dmbaryshev.vkschool.view.friends.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.view_model.UserVM;
import com.dmbaryshev.vkschool.utils.DLog;
import com.dmbaryshev.vkschool.view.common.IHolderClick;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private static final String TAG = DLog.makeLogTag(FriendsAdapter.class);
    private List<UserVM> items;
    private Context      context;
    private IHolderClick mListener;

    public FriendsAdapter(List<UserVM> items, IHolderClick listener) {
        if (items != null) {
            this.items = items;
            mListener = listener;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (items == null || items.size() <= 0) {return;}

        final UserVM item = items.get(position);
        holder.tvName.setText(String.format("%s %s", item.firstName, item.lastName));
        holder.tvStatus.setText(item.online == 1 ? context.getString(R.string.status_online) : "");
        Glide.with(context).load(item.photo100).into(holder.ivAvatar);
    }

    @Override
    public int getItemCount() {
        if (items != null && !items.isEmpty()) {
            return items.size();
        } else { return 0; }
    }

    public UserVM getUser(int adapterPosition) {
        return items.get(adapterPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView    ivAvatar;
        protected TextView     tvName;
        protected TextView     tvStatus;
        protected IHolderClick listener;

        public ViewHolder(View view, final IHolderClick listener) {
            super(view);
            ivAvatar = (ImageView) view.findViewById(R.id.iv_avatar);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvStatus = (TextView) view.findViewById(R.id.tv_status);
            this.listener = listener;
            view.setOnClickListener(v->listener.onItemClick(getAdapterPosition()));
        }
    }
}
