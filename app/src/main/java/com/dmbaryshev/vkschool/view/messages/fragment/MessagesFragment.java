package com.dmbaryshev.vkschool.view.messages.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.dto.VkMessage;
import com.dmbaryshev.vkschool.presenter.BasePresenter;
import com.dmbaryshev.vkschool.presenter.MessagePresenter;
import com.dmbaryshev.vkschool.utils.DLog;
import com.dmbaryshev.vkschool.utils.NetworkHelper;
import com.dmbaryshev.vkschool.view.common.BaseFragment;
import com.dmbaryshev.vkschool.view.messages.adapter.MessagesAdapter;

import java.util.LinkedList;
import java.util.List;

public class MessagesFragment extends BaseFragment implements IMessagesView {
    private static final String TAG = DLog.makeLogTag(MessagesFragment.class);

    private static final String KEY_ID_USER = "ID_USER";

    private EditText        mEtText;
    private Button          mBtSend;
    private ProgressBar     mProgressBar;
    private MessagesAdapter mMessagesAdapter;
    private int             mIdUser;
    private List<VkMessage> mVkMessages;

    public MessagesFragment() {
    }

    public static MessagesFragment newInstance(int idUser) {
        Bundle args = new Bundle();
        args.putInt(KEY_ID_USER, idUser);
        final MessagesFragment messagesFragment = new MessagesFragment();
        messagesFragment.setArguments(args);
        return messagesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIdUser = getArguments().getInt(KEY_ID_USER);

        mVkMessages = new LinkedList<>();
        mMessagesAdapter = new MessagesAdapter(mVkMessages);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        mEtText = (EditText) view.findViewById(R.id.et_message);
        mBtSend = (Button) view.findViewById(R.id.bt_send);
        mBtSend.setOnClickListener(v->((MessagePresenter) mPresenter).sendMessage(mEtText.getText()
                                                                                         .toString()));
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading);
        mPresenter.load();
    }

    private void initRecyclerView(View view) {
        RecyclerView rvMessages = (RecyclerView) view.findViewById(R.id.rv_messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        rvMessages.setLayoutManager(layoutManager);
        rvMessages.setHasFixedSize(true);
        rvMessages.setItemAnimator(new DefaultItemAnimator());
        rvMessages.setAdapter(mMessagesAdapter);
    }

    @Override
    protected BasePresenter getPresenter() {
        return new MessagePresenter(this);
    }

    @Override
    public void showMessages(List<VkMessage> answer) {
        DLog.i(TAG, "showMessages: answer" + answer);
        mVkMessages.clear();
        mVkMessages.addAll(answer);
        mMessagesAdapter.notifyDataSetChanged();
    }

    @Override
    public int getIdUser() {
        return mIdUser;
    }

    @Override
    public void addMessage(String messageText) {
        VkMessage vkMessage = new VkMessage();
        vkMessage.setBody(messageText);
        vkMessage.setOut(1);
        mVkMessages.add(0, vkMessage);
        mMessagesAdapter.notifyDataSetChanged();
        mEtText.setText("");
    }

    @Override
    public void showError(String errorText) {
        showErrorSnackbar(getView(), errorText);
    }

    @Override
    public void stopLoad() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(int errorTextRes) {
        showErrorSnackbar(getView(), errorTextRes);
    }

    @Override
    public void startLoad() {
        mProgressBar.setVisibility(View.VISIBLE);
    }
}
