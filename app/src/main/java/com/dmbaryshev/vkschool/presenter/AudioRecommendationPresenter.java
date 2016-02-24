package com.dmbaryshev.vkschool.presenter;

import com.dmbaryshev.vkschool.App;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.repository.AudioRepo;
import com.dmbaryshev.vkschool.model.view_model.AudioVM;
import com.dmbaryshev.vkschool.presenter.common.BasePresenter;
import com.dmbaryshev.vkschool.service.AudioService;
import com.dmbaryshev.vkschool.view.audio.fragment.IAudioRecommendationView;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscription;

public class AudioRecommendationPresenter extends BasePresenter<IAudioRecommendationView, AudioVM> {
    private static final int TRACKS_COUNT = 30;
    private int mOffset = 0;
    private AudioRepo mAudioRepo = new AudioRepo();
    private String  mTargetAudio;
    private AudioVM mTargetAudioVM;

    @Override
    protected Observable<ResponseAnswer<AudioVM>> initObservable() {
        if (mTargetAudioVM != null) {
            mTargetAudio = String.format("%d_%d", mTargetAudioVM.ownerId, mTargetAudioVM.id);
        }
        Observable<ResponseAnswer<AudioVM>> observable = mAudioRepo.getAudioRecommendation(
                mTargetAudio,
                TRACKS_COUNT,
                mOffset);
        mOffset += TRACKS_COUNT;
        return observable;
    }

    public void loadMore() {
        load(true);
        mOffset += TRACKS_COUNT;
    }

    public void setTargetAudio(AudioVM audioVM) {
        mTargetAudioVM = audioVM;
    }

    public void addAudio(AudioVM audioVM) {
        Subscription subscription = mAudioRepo.addAudio(audioVM.id, audioVM.ownerId).subscribe();
        addSubscription(subscription);
    }

    public void play(int position) {
        AudioService.execute(App.getAppContext(),
                             AudioService.ACTION_PLAY,
                             new ArrayList<>(mMissedAnswerList),
                             position);
    }
}
