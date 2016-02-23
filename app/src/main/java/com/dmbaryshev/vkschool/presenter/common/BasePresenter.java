package com.dmbaryshev.vkschool.presenter.common;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.dto.common.VkError;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.view_model.IViewModel;
import com.dmbaryshev.vkschool.utils.DLog;
import com.dmbaryshev.vkschool.utils.NetworkHelper;
import com.dmbaryshev.vkschool.view.IView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<T extends IView<VM>, VM extends IViewModel> {
    private static final String TAG = DLog.makeLogTag(BasePresenter.class);
    protected T mView;
    protected Set<VM> mMissedAnswerSet = new LinkedHashSet<>();
    protected List<VM> mMissedAnswerList = new ArrayList<>();
    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    protected Observable<ResponseAnswer<VM>> mObservable;

    protected void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    protected void showData(ResponseAnswer<VM> data) {
        if (data != null) {
            DLog.i(TAG,
                   "showData:  size = " + data.getAnswer()
                                              .size() + "data.getAnswer() =" + data.getAnswer());
            mMissedAnswerSet.addAll(data.getAnswer());
            mMissedAnswerList.clear();
            mMissedAnswerList.addAll(mMissedAnswerSet);
            DLog.i(TAG,
                   "showData: missed size = " + mMissedAnswerSet.size());

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

        mView.showData(mMissedAnswerList);
    }

    public void onStop() {
        compositeSubscription.clear();
    }

    public void bindView(T view) {
        this.mView = view;
        if (mMissedAnswerList != null) {
            mView.showData(mMissedAnswerList);
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
