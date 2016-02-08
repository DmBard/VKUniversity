package com.dmbaryshev.vkschool.view.friends.fragment;

import android.app.LoaderManager;
import android.content.Context;

import com.dmbaryshev.vkschool.model.dto.VkUser;
import com.dmbaryshev.vkschool.view.IView;

import java.util.List;

public interface IFriendsView extends IView {

    void showUsers(List<VkUser> answer);

}
