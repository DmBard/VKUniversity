package com.dmbaryshev.vkschool.view.audio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.view_model.AudioVM;
import com.dmbaryshev.vkschool.utils.DLog;
import com.dmbaryshev.vkschool.utils.DateTimeHelper;
import com.dmbaryshev.vkschool.view.common.IHolderClick;

import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {
    private static final String TAG = DLog.makeLogTag(AudioAdapter.class);
    private List<AudioVM> items;
    private Context      context;
    private IHolderClick mListener;

    public AudioAdapter(List<AudioVM> items, IHolderClick listener) {
        if (items != null) {
            this.items = items;
            mListener = listener;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_audio, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (items == null || items.size() <= 0) {return;}

        final AudioVM item = items.get(position);
        holder.tvDuration.setText(DateTimeHelper.convertDurationToString(item.duration));
        holder.tvTrack.setText(item.title);
        holder.tvArtist.setText(item.artist);
    }

    @Override
    public int getItemCount() {
        if (items != null && !items.isEmpty()) {
            return items.size();
        } else { return 0; }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView     tvDuration;
        protected TextView     tvArtist;
        protected TextView     tvTrack;
        protected IHolderClick listener;

        public ViewHolder(View view, final IHolderClick listener) {
            super(view);
            tvDuration = (TextView) view.findViewById(R.id.tv_duration);
            tvTrack = (TextView) view.findViewById(R.id.tv_track);
            tvArtist = (TextView) view.findViewById(R.id.tv_artist);
            this.listener = listener;
            view.setOnClickListener(v->listener.onItemClick(getAdapterPosition()));
        }
    }
}
