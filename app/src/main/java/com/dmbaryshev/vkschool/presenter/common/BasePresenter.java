package com.dmbaryshev.vkschool.presenter.common;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.dto.common.VkError;
import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.view_model.IViewModel;
import com.dmbaryshev.vkschool.utils.DLog;
import com.dmbaryshev.vkschool.utils.NetworkHelper;
import com.dmbaryshev.vkschool.view.IView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<T extends IView<VM>, VM extends IViewModel> {
    private static final String TAG = DLog.makeLogTag(BasePresenter.class);
    protected T mView;
    protected Set<VM> mMissedAnswerSet = new LinkedHashSet<>();
    protected List<VM> mMissedAnswerList;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    protected Observable<ResponseAnswer<VM>> mObservable;
    private   int                            mCount;

    public int getCount() {
        return mCount;
    }

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
                   "showData: missed list size = " + mMissedAnswerSet.size());

        }

        if (mView == null) { return; }

        mView.stopLoad();
        if (data == null) {
            mView.showError(R.string.error_common);
            return;
        }
        mCount = data.getCount();
        VkError vkError = data.getVkError();
        List<VM> answer = data.getAnswer();

        mView.showCount(mCount);

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
        load(false);
    }

    protected void load(boolean force) {
        if (NetworkHelper.isOnline()) {
            if (mMissedAnswerList == null || force) {
                mView.startLoad();
                if(mMissedAnswerList == null) mMissedAnswerList = new ArrayList<>();
                if (mObservable == null)  mObservable = initObservable();
                if (force) {
                    mObservable = null;
                    mObservable = initObservable();
                }
                Subscription subscription = mObservable.subscribe(this::showData);
                addSubscription(subscription);
            }
        } else { mView.showError(R.string.error_network_unavailable); }
    }

    protected abstract Observable<ResponseAnswer<VM>> initObservable();

    public void unbindView() { this.mView = null; }
}
