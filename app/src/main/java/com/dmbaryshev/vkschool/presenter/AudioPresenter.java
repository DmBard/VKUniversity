package com.dmbaryshev.vkschool.presenter;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.dto.VkAudio;
import com.dmbaryshev.vkschool.model.dto.common.VkError;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.repository.AudioRepo;
import com.dmbaryshev.vkschool.utils.NetworkHelper;
import com.dmbaryshev.vkschool.view.audio.fragment.IAudioView;

import java.util.List;

import rx.Observable;
import rx.Subscription;

public class AudioPresenter extends BasePresenter {
    private IAudioView mView;

    public AudioPresenter(IAudioView view) {mView = view;}

    private void showData(ResponseAnswer<VkAudio> data) {
        mView.stopLoad();
        if (data == null) {
            mView.showError(R.string.error_common);
            return;
        }

        VkError vkError = data.getVkError();
        List<VkAudio> answer = data.getAnswer();

        if (vkError != null) {
            mView.showError(vkError.errorMsg);
            return;
        }

        if (answer == null) {
            mView.showError(R.string.error_common);
            return;
        }

        mView.showAudio(answer);
    }

    @Override
    public void load() {
        if (NetworkHelper.isOnline()) {
            mView.startLoad();
            AudioRepo audioRepo = new AudioRepo();
            Observable<ResponseAnswer<VkAudio>> observable = audioRepo.getAudio();
            Subscription subscription = observable.subscribe(this::showData);
            addSubscription(subscription);
        } else { mView.showError(R.string.error_network_unavailable); }
    }
}
