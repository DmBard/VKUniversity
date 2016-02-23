package com.dmbaryshev.vkschool.presenter;

import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.repository.AudioRepo;
import com.dmbaryshev.vkschool.model.view_model.AudioVM;
import com.dmbaryshev.vkschool.presenter.common.BasePresenter;
import com.dmbaryshev.vkschool.view.audio.fragment.IAudioView;

import rx.Observable;

public class AudioPresenter extends BasePresenter<IAudioView, AudioVM> {
    private static final int TRACKS_COUNT = 30;
    private              int mOffset      = 0;
    private AudioRepo mAudioRepo = new AudioRepo();

    @Override
    protected Observable<ResponseAnswer<AudioVM>> initObservable() {
        Observable<ResponseAnswer<AudioVM>> observable = mAudioRepo.getAudio(TRACKS_COUNT, mOffset);
        mOffset += TRACKS_COUNT;
        return observable;
    }

    public void loadMore() {
        load(true);
        mOffset += TRACKS_COUNT;
    }
}
