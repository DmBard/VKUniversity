package com.dmbaryshev.vkschool.presenter;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.dto.VkMessage;
import com.dmbaryshev.vkschool.model.dto.common.VkError;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.repository.MessageRepo;
import com.dmbaryshev.vkschool.view.messages.fragment.IMessagesView;

import java.util.List;

import rx.Observable;
import rx.Subscription;

public class MessagePresenter extends BasePresenter {
    private IMessagesView mView;

    public MessagePresenter(IMessagesView view) {mView = view;}

    public void showData(ResponseAnswer<VkMessage> data) {
        mView.stopLoad();
        if (data == null) {
            mView.showError(R.string.error_common);
            return;
        }

        VkError vkError = data.getVkError();
        List<VkMessage> answer = data.getAnswer();

        if (vkError != null) {
            mView.showError(vkError.getErrorMsg());
            return;
        }

        if (answer == null) {
            mView.showError(R.string.error_common);
            return;
        }

        mView.showMessages(answer);
    }

    @Override
    public void load() {
        MessageRepo messageRepo = new MessageRepo(mView.getIdUser());
        Observable<ResponseAnswer<VkMessage>> observable = messageRepo.getResponseAnswer();
        Subscription subscription = observable.subscribe(this::showData);
        addSubscription(subscription);
    }
}
