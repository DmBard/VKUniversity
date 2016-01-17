package com.dmbaryshev.vkschool.ui.friends.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.network.model.VkUser;
import com.dmbaryshev.vkschool.ui.friends.adapter.FriendsAdapter;
import com.dmbaryshev.vkschool.ui.friends.loader.FriendsLoader;
import com.dmbaryshev.vkschool.utils.NetworkHelper;

import java.util.List;

public class FriendsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<VkUser>> {

    private static final int ID_IMAGE_LOADER = 0;

    private RecyclerView mRvFriends;
    private ProgressDialog mProgressDialog;

    public FriendsFragment() {
    }

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        load(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismissProgressDialog();
    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void initRecyclerView(View view) {
        mRvFriends = (RecyclerView) view.findViewById(R.id.rv_friends);
        mRvFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvFriends.setHasFixedSize(false);
        mRvFriends.setItemAnimator(new DefaultItemAnimator());
    }

    private void load(View view) {
        if (NetworkHelper.isOnline(getActivity())) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) { return; }
            mProgressDialog = ProgressDialog.show(getActivity(),
                                                  getActivity().getString(R.string.progress_dialog_title),
                                                  getActivity().getString(R.string.progress_dialog_message));
            initFriendsLoader();
        } else {
            showErrorSnackbar(view, R.string.error_network_unavailable);
        }
    }

    private void initFriendsLoader() {
        getLoaderManager().restartLoader(ID_IMAGE_LOADER, null, this);
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
    public Loader<List<VkUser>> onCreateLoader(int id, Bundle args) {
        return new FriendsLoader(getActivity().getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<List<VkUser>> loader, List<VkUser> data) {
        dismissProgressDialog();
        if (data == null) {
            showErrorSnackbar(getView(), R.string.error_common);
            return;
        }
        // TODO: 18.01.16 implement saving to database

        FriendsAdapter friendsAdapter = new FriendsAdapter(data);
        mRvFriends.setAdapter(friendsAdapter);

        getLoaderManager().destroyLoader(loader.getId());
    }

    @Override
    public void onLoaderReset(Loader<List<VkUser>> loader) {}
}
