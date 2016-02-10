package com.dmbaryshev.vkschool.view.messages.fragment;

import com.dmbaryshev.vkschool.model.dto.VkMessage;
import com.dmbaryshev.vkschool.view.IView;

import java.util.List;

public interface IMessagesView extends IView {

    void showMessages(List<VkMessage> answer);

    int getIdUser();

    void addMessage(String messageText);
}
