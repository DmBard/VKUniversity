package com.dmbaryshev.vkschool.model.repository.mapper;

import com.dmbaryshev.vkschool.model.dto.VkAudio;
import com.dmbaryshev.vkschool.model.view_model.AudioVM;

public class AudioMapper extends BaseMapper<VkAudio, AudioVM> {
    @Override
    protected AudioVM createViewModel(VkAudio vkAudio) {
        return new AudioVM(vkAudio.id, vkAudio.ownerId, vkAudio.artist, vkAudio.title, vkAudio.duration);
    }
}
