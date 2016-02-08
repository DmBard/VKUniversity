package com.dmbaryshev.vkschool.presenter;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.dto.VkUser;
import com.dmbaryshev.vkschool.model.dto.common.VkError;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.repository.UserRepo;
import com.dmbaryshev.vkschool.view.friends.fragment.IFriendsView;

import java.util.List;

import rx.Observable;
import rx.Subscription;

public class FriendsPresenter extends BasePresenter {
    private IFriendsView mView;

    public FriendsPresenter(IFriendsView view) {mView = view;}

    public void showData(ResponseAnswer<VkUser> data) {
        mView.stopLoad();
        if (data == null) {
            mView.showError(R.string.error_common);
            return;
        }

        VkError vkError = data.getVkError();
        List<VkUser> answer = data.getAnswer();

        if (vkError != null) {
            mView.showError(vkError.getErrorMsg());
            return;
        }

        if (answer == null) {
            mView.showError(R.string.error_common);
            return;
        }

        mView.showUsers(answer);
    }

    @Override
    public void load() {
        UserRepo userRepo = new UserRepo();
        Observable<ResponseAnswer<VkUser>> observable = userRepo.getResponseAnswer();
        Subscription subscription = observable.subscribe(this::showData);
        addSubscription(subscription);
    }
}
