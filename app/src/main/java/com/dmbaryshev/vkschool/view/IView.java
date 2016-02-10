package com.dmbaryshev.vkschool.view;

public interface IView {
    void showError(int errorTextRes);

    void showError(String errorText);

    void stopLoad();

    void startLoad();
}

