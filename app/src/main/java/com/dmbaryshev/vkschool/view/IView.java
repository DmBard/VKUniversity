package com.dmbaryshev.vkschool.view;

import com.dmbaryshev.vkschool.model.view_model.IViewModel;

import java.util.List;

public interface IView<T extends IViewModel> {
    void showError(int errorTextRes);

    void showError(String errorText);

    void stopLoad();

    void startLoad();

    void showData(List<T> data);

    void showCount(int count);
}

