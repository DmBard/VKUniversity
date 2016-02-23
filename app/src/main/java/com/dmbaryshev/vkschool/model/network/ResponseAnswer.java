package com.dmbaryshev.vkschool.model.network;

import com.dmbaryshev.vkschool.model.dto.common.VkError;

import java.util.List;

public class ResponseAnswer<T> {
    private int count;
    private List<T> answer;
    private VkError mVkError;

    public ResponseAnswer(int count, List<T> answer, VkError vkError) {
        this.count = count;
        this.answer = answer;
        mVkError = vkError;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getAnswer() {
        return answer;
    }

    public void setAnswer(List<T> answer) {
        this.answer = answer;
    }

    public VkError getVkError() {
        return mVkError;
    }

    public void setVkError(VkError vkError) {
        mVkError = vkError;
    }
}
