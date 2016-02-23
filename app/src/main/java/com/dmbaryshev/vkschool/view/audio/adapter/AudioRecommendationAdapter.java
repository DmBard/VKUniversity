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

public class AudioRecommendationAdapter extends RecyclerView.Adapter<AudioRecommendationAdapter.ViewHolder> {
    private static final String TAG = DLog.makeLogTag(AudioAdapter.class);
    private List<AudioVM>                   items;
    private IAudioRecomAdapterClickListener mListener;
    private SimpleArrayMap<Integer, Boolean> mIsOpenedMap = new SimpleArrayMap<>();

    public AudioRecommendationAdapter(List<AudioVM> items, IAudioRecomAdapterClickListener listener) {
        if (items != null) {
            this.items = items;
            mListener = listener;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                                  .inflate(R.layout.item_audio_recommendation, parent, false);
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
        holder.ivAdd.setVisibility(isOpen == null || !isOpen ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        if (items != null && !items.isEmpty()) {
            return items.size();
        } else { return 0; }
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

    public AudioVM getItem(int position) {
        return items.get(position);
    }

    public interface IAudioRecomAdapterClickListener extends IHolderClick {
        void onPlayClick(int position);

        void onAddClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView ivPlay;
        protected ImageView ivAdd;
        protected TextView  tvDuration;
        protected TextView  tvArtist;
        protected TextView  tvTrack;

        protected IAudioRecomAdapterClickListener listener;

        public ViewHolder(View view, final IAudioRecomAdapterClickListener listener) {
            super(view);
            tvDuration = (TextView) view.findViewById(R.id.tv_duration);
            tvTrack = (TextView) view.findViewById(R.id.tv_track);
            tvArtist = (TextView) view.findViewById(R.id.tv_artist);
            ivPlay = (ImageView) view.findViewById(R.id.iv_play);
            ivAdd = (ImageView) view.findViewById(R.id.iv_add);
            this.listener = listener;
            view.setOnClickListener(v -> listener.onItemClick(getAdapterPosition()));
            ivPlay.setOnClickListener(v->listener.onPlayClick(getAdapterPosition()));
            ivAdd.setOnClickListener(v->listener.onAddClick(getAdapterPosition()));
        }
    }
}
