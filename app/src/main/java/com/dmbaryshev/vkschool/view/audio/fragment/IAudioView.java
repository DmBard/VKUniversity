package com.dmbaryshev.vkschool.view.audio.fragment;

import com.dmbaryshev.vkschool.model.dto.VkAudio;
import com.dmbaryshev.vkschool.view.IView;

import java.util.List;

public interface IAudioView extends IView {

    void showAudio(List<VkAudio> answer);
}
