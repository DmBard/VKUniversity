package com.dmbaryshev.vkschool.view.audio.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.view_model.AudioVM;
import com.dmbaryshev.vkschool.presenter.AudioRecommendationPresenter;
import com.dmbaryshev.vkschool.utils.DLog;
import com.dmbaryshev.vkschool.view.audio.adapter.AudioRecommendationAdapter;
import com.dmbaryshev.vkschool.view.common.BaseFragment;
import com.dmbaryshev.vkschool.view.common.ICommonFragmentCallback;

import java.util.ArrayList;
import java.util.List;

public class AudioRecommendationFragment extends BaseFragment<AudioRecommendationPresenter> implements
                                                                                            IAudioRecommendationView,
                                                                                            AudioRecommendationAdapter.IAudioRecomAdapterClickListener {
    public static final String TAG = DLog.makeLogTag(AudioRecommendationFragment.class);
    public static final String KEY_AUDIO = "com.dmbaryshev.vkschool.view.audio.fragment.KEY_AUDIO";
    private ProgressDialog mProgressDialog;
    private IAudioRecommendationFragmentListener mListener;
    private AudioRecommendationAdapter mAudioAdapter;
    private List<AudioVM> mVkAudios;
    private boolean mLoading = false;

    public AudioRecommendationFragment() {
    }

    public static AudioRecommendationFragment newInstance(AudioVM audioVM) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_AUDIO, audioVM);
        AudioRecommendationFragment fragment = new AudioRecommendationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemClick(int adapterPosition) {
        mAudioAdapter.showAdditionBar(adapterPosition);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (IAudioRecommendationFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement FragmentButtonListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_audio, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVkAudios = new ArrayList<>();
        mAudioAdapter = new AudioRecommendationAdapter(mVkAudios, this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        mListener.showTitle(getString(R.string.fragment_title_recommendation));
        mPresenter.bindView(this);
        Bundle bundle = getArguments();
        AudioVM audioVM = null;
        if (bundle != null) {
            audioVM = bundle.getParcelable(KEY_AUDIO);
        }
        mPresenter.setTargetAudio(audioVM);
        mPresenter.load();
        showCount(mPresenter.getCount());
    }

    private void initRecyclerView(View view) {
        RecyclerView rvTracks = (RecyclerView) view.findViewById(R.id.rv_audio);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvTracks.setLayoutManager(layoutManager);
        rvTracks.setHasFixedSize(false);
        rvTracks.setItemAnimator(new DefaultItemAnimator());
        rvTracks.setAdapter(mAudioAdapter);
        rvTracks.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (!mLoading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            mLoading = true;
                            mPresenter.loadMore();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismissProgressDialog();
    }

    @Override
    protected AudioRecommendationPresenter getPresenter() {
        return new AudioRecommendationPresenter();
    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showError(int errorTextRes) {
        showErrorSnackbar(getView(), errorTextRes);
    }

    @Override
    public void showError(String errorText) {
        showErrorSnackbar(getView(), errorText);
    }

    @Override
    public void stopLoad() {
        dismissProgressDialog();
        mLoading = false;
    }

    @Override
    public void startLoad() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) { return; }
        mProgressDialog = ProgressDialog.show(getActivity(),
                                              getActivity().getString(R.string.progress_dialog_title),
                                              getActivity().getString(R.string.progress_dialog_message));
    }

    @Override
    public void showData(List<AudioVM> data) {
        mVkAudios.clear();
        mVkAudios.addAll(data);
        mAudioAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCount(int count) {
        if (count == 0) {
            mListener.showSubtitle("");
            return;
        }
        mListener.showSubtitle("" + count + " " + getResources().getQuantityString(R.plurals.track_count,
                                                                                   count));
    }

    @Override
    public void onPlayClick(int position) {
        mPresenter.play(position);
    }

    @Override
    public void onAddClick(int position) {
        mPresenter.addAudio(mAudioAdapter.getItem(position));
        mListener.onAudioAdded();
    }

    public interface IAudioRecommendationFragmentListener extends ICommonFragmentCallback {
        void onAudioAdded();
    }
}
