package com.dmbaryshev.vkschool.presenter.common;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.dto.common.VkError;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.view_model.IViewModel;
import com.dmbaryshev.vkschool.utils.NetworkHelper;
import com.dmbaryshev.vkschool.view.IView;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<T extends IView<VM>, VM extends IViewModel> {
    protected T mView;
    protected List<VM>            mMissedAnswer         = new LinkedList<>();
    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    protected Observable<ResponseAnswer<VM>> mObservable;

    protected void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    protected void showData(ResponseAnswer<VM> data) {
        if (data != null) {
            mMissedAnswer.addAll(data.getAnswer());
        }

        if (mView == null) { return; }

        mView.stopLoad();
        if (data == null) {
            mView.showError(R.string.error_common);
            return;
        }

        VkError vkError = data.getVkError();
        List<VM> answer = data.getAnswer();

        if (vkError != null) {
            mView.showError(vkError.errorMsg);
            return;
        }

        if (answer == null) {
            mView.showError(R.string.error_common);
            return;
        }

        mView.showData(mMissedAnswer);
    }

    public void onStop() {
        compositeSubscription.clear();
    }

    public void bindView(T view) {
        this.mView = view;
        if (mMissedAnswer != null) {
            mView.showData(mMissedAnswer);
        }
    }

    public void load() {
        if (NetworkHelper.isOnline()) {
            mView.startLoad();
            if (mObservable == null) {
                mObservable = initObservable();
            }
            Subscription subscription = mObservable.subscribe(this::showData);
            addSubscription(subscription);
        } else { mView.showError(R.string.error_network_unavailable); }
    }

    protected abstract Observable<ResponseAnswer<VM>> initObservable();

    public void unbindView() { this.mView = null; }
}
