package com.dmbaryshev.vkschool.view.common;

import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.presenter.BasePresenter;

public abstract class BaseFragment extends Fragment {

    protected BasePresenter mPresenter = getPresenter();

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
    public void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    protected abstract BasePresenter getPresenter();
}
