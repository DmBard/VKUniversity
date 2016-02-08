package com.dmbaryshev.vkschool.view;

public interface IView {
    void showError(String errorText);

    void stopLoad();

    void showError(int errorTextRes);
}
