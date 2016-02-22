package com.dmbaryshev.vkschool.view.messages.fragment;

import com.dmbaryshev.vkschool.model.view_model.MessageVM;
import com.dmbaryshev.vkschool.view.IView;

public interface IMessagesView extends IView<MessageVM> {

    int getIdUser();

    void addMessage(MessageVM messageVM);
}
