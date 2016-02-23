package com.dmbaryshev.vkschool.presenter;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.repository.MessageRepo;
import com.dmbaryshev.vkschool.model.view_model.MessageVM;
import com.dmbaryshev.vkschool.presenter.common.BasePresenter;
import com.dmbaryshev.vkschool.utils.NetworkHelper;
import com.dmbaryshev.vkschool.view.messages.fragment.IMessageView;

import rx.Observable;
import rx.Subscription;

public class MessagePresenter extends BasePresenter<IMessageView, MessageVM> {
    private static final int MESSAGES_COUNT = 20;
    private MessageRepo mMessageRepo = new MessageRepo();
    private int mOffset = 0;

    public void loadMore() {
        if (NetworkHelper.isOnline()) {
            mView.startLoad();
            Observable<ResponseAnswer<MessageVM>> observable = mMessageRepo.getMessages(mView.getIdUser(),
                                                                                        MESSAGES_COUNT,
                                                                                        mOffset);
            mOffset += MESSAGES_COUNT;
            Subscription subscription = observable.subscribe(answer -> super.showData(answer));
            addSubscription(subscription);
        } else { mView.showError(R.string.error_network_unavailable); }
    }

    public void sendMessage(String messageText) {
        Observable<Void> observable = mMessageRepo.sendMessage(mView.getIdUser(), messageText);
        MessageVM messageVM = new MessageVM(0, messageText, MessageVM.OUT, null);
        Subscription subscription = observable.subscribe(v -> mView.addMessage(messageVM));
        addSubscription(subscription);
    }

    @Override
    protected Observable<ResponseAnswer<MessageVM>> initObservable() {
        final Observable<ResponseAnswer<MessageVM>> observable = mMessageRepo.getMessages(mView.getIdUser(),
                                                                                          MESSAGES_COUNT,
                                                                                          mOffset);
        mOffset += MESSAGES_COUNT;

        return observable;
    }
}
