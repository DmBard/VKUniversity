package com.dmbaryshev.vkschool.view.audio.adapter;

import android.content.Context;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.view_model.AudioVM;
import com.dmbaryshev.vkschool.utils.DLog;
import com.dmbaryshev.vkschool.utils.DateTimeHelper;
import com.dmbaryshev.vkschool.view.common.IHolderClick;

import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {
    private static final String TAG = DLog.makeLogTag(AudioAdapter.class);
    private List<AudioVM>              items;
    private IAudioAdapterClickListener mListener;
    private SimpleArrayMap<Integer, Boolean> mIsOpenedMap = new SimpleArrayMap<>();

    public AudioAdapter(List<AudioVM> items, IAudioAdapterClickListener listener) {
        if (items != null) {
            this.items = items;
            mListener = listener;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
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
        Boolean isOpen = mIsOpenedMap.get(position);
        holder.ivPlay.setVisibility(isOpen == null || !isOpen ? View.GONE : View.VISIBLE);
        holder.ivRecommendation.setVisibility(isOpen == null || !isOpen ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        if (items != null && !items.isEmpty()) {
            return items.size();
        } else { return 0; }
    }

    public AudioVM getItem(int adapterPosition) {
        return items.get(adapterPosition);
    }

    public void showAdditionBar(int adapterPosition) {
        Boolean isOpen = mIsOpenedMap.get(adapterPosition);
        if (isOpen == null) {
            mIsOpenedMap.put(adapterPosition, true);
        } else {
            mIsOpenedMap.put(adapterPosition, !isOpen);
        }
        notifyItemChanged(adapterPosition);
    }

    public interface IAudioAdapterClickListener extends IHolderClick {
        void onPlayClick(int position);

        void onRecommendationClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView     tvDuration;
        protected TextView     tvArtist;
        protected TextView     tvTrack;
        protected ImageView    ivPlay;
        protected ImageView    ivRecommendation;
        protected IHolderClick listener;

        public ViewHolder(View view, final IAudioAdapterClickListener listener) {
            super(view);
            tvDuration = (TextView) view.findViewById(R.id.tv_duration);
            tvTrack = (TextView) view.findViewById(R.id.tv_track);
            tvArtist = (TextView) view.findViewById(R.id.tv_artist);
            ivPlay = (ImageView) view.findViewById(R.id.iv_play);
            ivRecommendation = (ImageView) view.findViewById(R.id.iv_recommendation);

            this.listener = listener;
            view.setOnClickListener(v->listener.onItemClick(getAdapterPosition()));
            ivPlay.setOnClickListener(v->listener.onPlayClick(getAdapterPosition()));
            ivRecommendation.setOnClickListener(v->listener.onRecommendationClick(getAdapterPosition()));
        }
    }
}

