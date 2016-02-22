package com.dmbaryshev.vkschool.presenter;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.repository.AudioRepo;
import com.dmbaryshev.vkschool.model.view_model.AudioVM;
import com.dmbaryshev.vkschool.presenter.common.BasePresenter;
import com.dmbaryshev.vkschool.utils.NetworkHelper;
import com.dmbaryshev.vkschool.view.audio.fragment.IAudioView;

import rx.Observable;
import rx.Subscription;

public class AudioPresenter extends BasePresenter<IAudioView, AudioVM> {
    private static final int TRACKS_COUNT = 30;
    private              int mOffset      = 0;

    @Override
    protected Observable<ResponseAnswer<AudioVM>> initObservable() {
        AudioRepo audioRepo = new AudioRepo();
        Observable<ResponseAnswer<AudioVM>> observable = audioRepo.getAudio(TRACKS_COUNT, mOffset);
        mOffset = mOffset + TRACKS_COUNT;
        return observable;
    }

    public void loadMore() {
        if (NetworkHelper.isOnline()) {
            mView.startLoad();
            Observable<ResponseAnswer<AudioVM>> observable = initObservable();
            Subscription subscription = observable.subscribe(answer->super.showData(answer));
            addSubscription(subscription);
        } else { mView.showError(R.string.error_network_unavailable); }
    }
}
