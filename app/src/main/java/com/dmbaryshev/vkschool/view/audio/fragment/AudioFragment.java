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
import com.dmbaryshev.vkschool.presenter.AudioPresenter;
import com.dmbaryshev.vkschool.utils.DLog;
import com.dmbaryshev.vkschool.view.audio.adapter.AudioAdapter;
import com.dmbaryshev.vkschool.view.common.BaseFragment;
import com.dmbaryshev.vkschool.view.common.IHolderClick;

import java.util.ArrayList;
import java.util.List;

public class AudioFragment extends BaseFragment<AudioPresenter> implements IAudioView,
                                                                           IHolderClick {
    public static final String TAG = DLog.makeLogTag(AudioFragment.class);
    private ProgressDialog mProgressDialog;
    private IAudioFragmentListener mListener;
    private AudioAdapter mAudioAdapter;
    private List<AudioVM> mVkAudios;
    private boolean mLoading = false;

    public AudioFragment() {
    }

    public static AudioFragment newInstance() {
        return new AudioFragment();
    }

    @Override
    public void onItemClick(int adapterPosition) {
        mListener.openRecommendationsAudioFragment(mAudioAdapter.getItem(adapterPosition));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (IAudioFragmentListener) activity;
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
        mAudioAdapter = new AudioAdapter(mVkAudios, this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        mPresenter.bindView(this);
        mPresenter.load();
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
    protected AudioPresenter getPresenter() {
        return new AudioPresenter();
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

    public interface IAudioFragmentListener {
        void openRecommendationsAudioFragment(AudioVM audioVM);
    }
}