package com.dmbaryshev.vkschool.presenter;

import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.repository.MessageRepo;
import com.dmbaryshev.vkschool.model.view_model.MessageVM;
import com.dmbaryshev.vkschool.model.view_model.UserVM;
import com.dmbaryshev.vkschool.presenter.common.BasePresenter;
import com.dmbaryshev.vkschool.view.messages.fragment.IMessageView;

import rx.Observable;
import rx.Subscription;

public class MessagePresenter extends BasePresenter<IMessageView, MessageVM> {
    private static final int MESSAGES_COUNT = 20;
    private UserVM mUserVM;
    private MessageRepo mMessageRepo = new MessageRepo();
    private int mOffset = 0;

    public void loadMore() {
        load(true);
        mOffset += MESSAGES_COUNT;
    }

    public void sendMessage(String messageText) {
        Observable<Void> observable = mMessageRepo.sendMessage(mUserVM.id, messageText);
        MessageVM messageVM = new MessageVM(0, messageText, MessageVM.OUT, null);
        Subscription subscription = observable.subscribe(v -> mView.addMessage(messageVM));
        addSubscription(subscription);
    }

    @Override
    protected Observable<ResponseAnswer<MessageVM>> initObservable() {
        Observable<ResponseAnswer<MessageVM>> observable = mMessageRepo.getMessages(mUserVM.id,
                                                                                    MESSAGES_COUNT,
                                                                                    mOffset);
        mOffset += MESSAGES_COUNT;

        return observable;
    }

    public UserVM getUserVM() {
        return mUserVM;
    }

    public void setUserVM(UserVM userVM) {
        mUserVM = userVM;
    }
}
