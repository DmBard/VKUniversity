package com.dmbaryshev.vkschool.presenter.common;

import android.support.v4.util.SimpleArrayMap;

import com.dmbaryshev.vkschool.utils.DLog;

public class PresenterCache {
    private static PresenterCache instance = null;

    private SimpleArrayMap<String, BasePresenter> presenters;

    private PresenterCache() {}

    public static PresenterCache getInstance() {
        if (instance == null) {
            instance = new PresenterCache();
        }
        return instance;
    }

    @SuppressWarnings ("unchecked") // Handled internally
    public final <T extends BasePresenter> T getPresenter(String who, T newPresenter) {
        if (presenters == null) {
            presenters = new SimpleArrayMap<>();
        }
        T presenter = null;
        try {
            presenter = (T) presenters.get(who);
        } catch (ClassCastException e) {
            DLog.w("PresenterActivity", "Duplicate Presenter " +
                    "tag identified: " + who + ". This could " +
                    "cause issues with state.");
        }
        if (presenter == null) {
            presenter = newPresenter;
            presenters.put(who, presenter);
        }
        return presenter;
    }

    public final void removePresenter(String who) {
        if (presenters != null) {
            presenters.remove(who);
        }
    }
}
