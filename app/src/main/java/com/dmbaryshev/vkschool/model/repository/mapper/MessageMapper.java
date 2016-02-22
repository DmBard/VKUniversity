package com.dmbaryshev.vkschool.model.repository.mapper;

import com.dmbaryshev.vkschool.model.dto.VkMessage;
import com.dmbaryshev.vkschool.model.view_model.MessageVM;

public class MessageMapper extends BaseMapper<VkMessage, MessageVM> {
    @Override
    protected MessageVM createViewModel(VkMessage vkMessage) {
        return new MessageVM(vkMessage.id, vkMessage.body, vkMessage.out, vkMessage.attachments);
    }
}
