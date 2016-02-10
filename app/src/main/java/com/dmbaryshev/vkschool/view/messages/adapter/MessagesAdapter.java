package com.dmbaryshev.vkschool.view.messages.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.dto.VkMessage;
import com.dmbaryshev.vkschool.utils.DLog;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    private static final String TAG = DLog.makeLogTag(MessagesAdapter.class);
    private List<VkMessage> items;
    private Context         context;

    public MessagesAdapter(List<VkMessage> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final int messageResource =
                viewType == 0 ? R.layout.item_out_message : R.layout.item_in_message;
        View view = LayoutInflater.from(context).inflate(messageResource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (items == null || items.size() <= 0) {return;}
        holder.bind(context, items.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).out;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (items != null && !items.isEmpty()) {
            return items.size();
        } else { return 0; }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivAttachmentPhoto;
        private final TextView  tvText;

        public ViewHolder(View view) {
            super(view);
            ivAttachmentPhoto = (ImageView) view.findViewById(R.id.iv_attachment_photo);
            tvText = (TextView) view.findViewById(R.id.tv_text);
        }

        public void bind(Context context, final VkMessage item) {
            tvText.setText(item.body);

            if (item.getPhoto() == null) {
                ivAttachmentPhoto.setImageDrawable(null);
            } else {
                Glide.with(context).load(item.getPhoto()).into(ivAttachmentPhoto);
            }
        }
    }
}
