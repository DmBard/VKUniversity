package com.dmbaryshev.vkschool.presenter;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.repository.AudioRepo;
import com.dmbaryshev.vkschool.model.view_model.AudioVM;
import com.dmbaryshev.vkschool.presenter.common.BasePresenter;
import com.dmbaryshev.vkschool.utils.NetworkHelper;
import com.dmbaryshev.vkschool.view.audio.fragment.IAudioRecommendationView;

import rx.Observable;
import rx.Subscription;

public class AudioRecommendationPresenter extends BasePresenter<IAudioRecommendationView, AudioVM> {
    private static final int TRACKS_COUNT = 30;
    private int mOffset = 0;
    private AudioRepo mAudioRepo = new AudioRepo();
    private String mTargetAudio;
    private AudioVM mAudioVM;

    @Override
    protected Observable<ResponseAnswer<AudioVM>> initObservable() {
        if (mAudioVM != null) {
            mTargetAudio = String.format("%d_%d", mAudioVM.ownerId, mAudioVM.id);
        }
        Observable<ResponseAnswer<AudioVM>> observable = mAudioRepo.getAudioRecommendation(
                mTargetAudio,
                TRACKS_COUNT,
                mOffset);
        mOffset += TRACKS_COUNT;
        return observable;
    }

    public void loadMore() {
        if (NetworkHelper.isOnline()) {
            mView.startLoad();
            Observable<ResponseAnswer<AudioVM>> observable = mAudioRepo.getAudioRecommendation(
                    mTargetAudio,
                    TRACKS_COUNT,
                    mOffset);
            mOffset += TRACKS_COUNT;
            Subscription subscription = observable.subscribe(answer -> super.showData(answer));
            addSubscription(subscription);
        } else { mView.showError(R.string.error_network_unavailable); }
    }

    public void setTargetAudio(AudioVM audioVM) {
        mAudioVM = audioVM;
    }
}
