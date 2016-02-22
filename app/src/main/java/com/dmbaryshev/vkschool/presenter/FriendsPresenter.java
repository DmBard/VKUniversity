package com.dmbaryshev.vkschool.presenter;

import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.repository.UserRepo;
import com.dmbaryshev.vkschool.model.view_model.UserVM;
import com.dmbaryshev.vkschool.presenter.common.BasePresenter;
import com.dmbaryshev.vkschool.view.friends.fragment.IFriendsView;

import rx.Observable;

public class FriendsPresenter extends BasePresenter<IFriendsView, UserVM> {

    @Override
    protected Observable<ResponseAnswer<UserVM>> initObservable() {
        return new UserRepo().getFriends();
    }
}
