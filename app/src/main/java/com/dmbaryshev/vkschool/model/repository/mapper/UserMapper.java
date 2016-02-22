package com.dmbaryshev.vkschool.model.repository.mapper;

import com.dmbaryshev.vkschool.model.dto.VkUser;
import com.dmbaryshev.vkschool.model.view_model.UserVM;

public class UserMapper extends BaseMapper<VkUser, UserVM> {

    @Override
    protected UserVM createViewModel(VkUser vkUser) {
        return new UserVM(vkUser.id,
                          vkUser.firstName,
                          vkUser.lastName,
                          vkUser.photo100,
                          vkUser.online,
                          vkUser.lastSeen);
    }
}