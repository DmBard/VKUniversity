package com.dmbaryshev.vkschool.ui.messages.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.dmbaryshev.vkschool.network.ApiHelper;
import com.dmbaryshev.vkschool.network.ApiService;
import com.dmbaryshev.vkschool.network.model.VkMessage;
import com.dmbaryshev.vkschool.network.model.common.CommonResponse;
import com.dmbaryshev.vkschool.ui.common.loader.VkLoader;
import com.dmbaryshev.vkschool.ui.messages.adapter.MessagesAdapter;
import com.dmbaryshev.vkschool.utils.DLog;
import com.dmbaryshev.vkschool.utils.NetworkHelper;
import com.squareup.okhttp.ResponseBody;

import java.util.LinkedList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MessagesFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<VkMessage>> {
    private static final String TAG = DLog.makeLogTag(MessagesFragment.class);

    private static final int    ID_IMAGE_LOADER = 0;
    private static final String KEY_ID_USER     = "ID_USER";

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
        mBtSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String messageText = mEtText.getText().toString();
                Call<ResponseBody> call = ApiHelper.createService(getActivity())
                                                   .sendMessage(mIdUser, messageText);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                        DLog.i(TAG, "onResponse: response = " + response.body().toString());
                        // TODO: 20.01.16 change logic
                        VkMessage vkMessage = new VkMessage();
                        vkMessage.setBody(messageText);
                        vkMessage.setOut(1);
                        mVkMessages.add(0, vkMessage);
                        mMessagesAdapter.notifyItemInserted(0);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });

        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading);
        load(view);
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

    private void load(View view) {
        if (NetworkHelper.isOnline(getActivity())) {
            if (mProgressBar.getVisibility() == View.VISIBLE) { return; }
            mProgressBar.setVisibility(View.VISIBLE);
            initLoader();
        } else {
            showErrorSnackbar(view, R.string.error_network_unavailable);
        }
    }

    private void initLoader() {
        getLoaderManager().initLoader(ID_IMAGE_LOADER, null, this);
    }

    public void showErrorSnackbar(final View view, int errorText) {
        Snackbar.make(view, errorText, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snackbar_button_text_retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        load(view);
                    }
                })
                .show();
    }

    @Override
    public Loader<List<VkMessage>> onCreateLoader(int id, Bundle args) {
        ApiService apiService = ApiHelper.createService(getActivity());
        Call<CommonResponse<VkMessage>> call = apiService.getMessageHistory(20, mIdUser);
        return new VkLoader<>(getActivity().getApplicationContext(), call);
    }

    @Override
    public void onLoadFinished(Loader<List<VkMessage>> loader, List<VkMessage> data) {
        mProgressBar.setVisibility(View.GONE);

        if (data == null) {
            showErrorSnackbar(getView(), R.string.error_common);
            return;
        }
        // TODO: 18.01.16 implement saving to database
        mVkMessages.clear();
        mVkMessages.addAll(data);
        mMessagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<VkMessage>> loader) {}
}
