package com.dmbaryshev.vkschool.ui.messages.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.network.model.VkMessage;
import com.dmbaryshev.vkschool.utils.DLog;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    private static final String TAG = DLog.makeLogTag(MessagesAdapter.class);
    private List<VkMessage> items;
    private Context         context;

    public MessagesAdapter(List<VkMessage> items) {
        if (items != null) {
            this.items = new ArrayList<>(items);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (items == null || items.size() <= 0) {return;}

        final VkMessage item = items.get(position);
        holder.bind(context, item);
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
        private final ImageView    ivAtachmentPhoto;
        private final TextView     tvText;
        private final LinearLayout llItem;

        public ViewHolder(View view) {
            super(view);
            ivAtachmentPhoto = (ImageView) view.findViewById(R.id.iv_attachment_photo);
            tvText = (TextView) view.findViewById(R.id.tv_text);
            llItem = (LinearLayout) view.findViewById(R.id.ll_message_item);
        }

        public void bind(Context context, final VkMessage item) {
            tvText.setText(item.getBody());
            llItem.setBackgroundResource(item.getOut() == 0 ? R.drawable.drawable_message_in
                                                            : R.drawable.drawable_msg_out);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            llItem.setLayoutParams(params);


            if (item.getPhoto() == null) {
                ivAtachmentPhoto.setImageDrawable(null);
            } else {
                Glide.with(context).load(item.getPhoto()).into(ivAtachmentPhoto);
            }
        }
    }
}
