package com.dmbaryshev.vkschool.model.repository.mapper;

import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.view_model.IViewModel;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMapper<V, VM extends IViewModel> {

    public ResponseAnswer<VM> execute(ResponseAnswer<V> vResponseAnswer) {
        int count = vResponseAnswer.getCount();
        List<V> vList = vResponseAnswer.getAnswer();
        List<VM> vmList = new ArrayList<>();
        if (vList != null && !vList.isEmpty()) {
            for (V v : vList) {
                VM viewModel = createViewModel(v);
                vmList.add(viewModel);
            }
        }

        return new ResponseAnswer<>(count, vmList, vResponseAnswer.getVkError());
    }

    protected abstract VM createViewModel(V v);
}
