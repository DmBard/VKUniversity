package com.dmbaryshev.vkschool.presenter;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.dto.VkMessage;
import com.dmbaryshev.vkschool.model.dto.common.VkError;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.repository.MessageRepo;
import com.dmbaryshev.vkschool.utils.NetworkHelper;
import com.dmbaryshev.vkschool.view.messages.fragment.IMessagesView;

import java.util.List;

import rx.Observable;
import rx.Subscription;

public class MessagePresenter extends BasePresenter {
    private IMessagesView mView;
    private MessageRepo   mMessageRepo;

    public MessagePresenter(IMessagesView view) {
        mView = view;
        mMessageRepo = new MessageRepo();
    }

    private void showData(ResponseAnswer<VkMessage> data) {
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

    private void addMessage(String messageText) {
        mView.addMessage(messageText);
    }

    @Override
    public void load() {
        if (NetworkHelper.isOnline()) {
            mView.startLoad();
            Observable<ResponseAnswer<VkMessage>> observable = mMessageRepo.getMessages(mView.getIdUser(),
                                                                                        20);
            Subscription subscription = observable.subscribe(this::showData);
            addSubscription(subscription);
        } else { mView.showError(R.string.error_network_unavailable); }
    }

    public void sendMessage(String messageText) {
        Observable<Void> observable = mMessageRepo.sendMessage(mView.getIdUser(), messageText);
        Subscription subscription = observable.subscribe(v->addMessage(messageText));
        addSubscription(subscription);
    }
}
