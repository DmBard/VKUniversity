package com.dmbaryshev.vkschool.view.common;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.presenter.common.BasePresenter;
import com.dmbaryshev.vkschool.presenter.common.PresenterCache;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

    protected P       mPresenter;
    private   boolean isDestroyedBySystem;
    private PresenterCache mPresenterCache = PresenterCache.getInstance();

    protected void showErrorSnackbar(final View view, int errorText) {
        Snackbar.make(view, errorText, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snackbar_button_text_retry, v->mPresenter.load())
                .show();
    }

    protected void showErrorSnackbar(final View view, String errorText) {
        Snackbar.make(view, errorText, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snackbar_button_text_retry, v->mPresenter.load())
                .show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = mPresenterCache.getPresenter(getTag(), getPresenter());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        isDestroyedBySystem = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isDestroyedBySystem = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unbindView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!isDestroyedBySystem) {
            mPresenterCache.removePresenter(getTag());
        }
    }

    protected abstract P getPresenter();
}
