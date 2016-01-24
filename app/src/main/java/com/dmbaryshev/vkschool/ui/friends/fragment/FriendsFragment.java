package com.dmbaryshev.vkschool.ui.friends.fragment;

import android.app.Activity;
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
import com.dmbaryshev.vkschool.network.ApiHelper;
import com.dmbaryshev.vkschool.network.ApiService;
import com.dmbaryshev.vkschool.network.model.VkUser;
import com.dmbaryshev.vkschool.network.model.common.CommonResponse;
import com.dmbaryshev.vkschool.ui.common.IHolderClick;
import com.dmbaryshev.vkschool.ui.common.loader.VkLoader;
import com.dmbaryshev.vkschool.ui.friends.adapter.FriendsAdapter;
import com.dmbaryshev.vkschool.utils.NetworkHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;

public class FriendsFragment extends Fragment
        implements IHolderClick, LoaderManager.LoaderCallbacks<List<VkUser>> {

    private static final int ID_FRIENDS_LOADER = 10;

    private ProgressDialog           mProgressDialog;
    private IFriendsFragmentListener mListener;
    private FriendsAdapter           mFriendsAdapter;
    private List<VkUser>             mVkUsers;

    public FriendsFragment() {
    }

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    private void initRecyclerView(View view) {
        RecyclerView rvFriends = (RecyclerView) view.findViewById(R.id.rv_friends);
        rvFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFriends.setHasFixedSize(false);
        rvFriends.setItemAnimator(new DefaultItemAnimator());
        rvFriends.setAdapter(mFriendsAdapter);
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
        getLoaderManager().initLoader(ID_FRIENDS_LOADER, null, this);
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
        ApiService apiService = ApiHelper.createService(getActivity());
        Call<CommonResponse<VkUser>> call = apiService.getFriendList("hints", "photo_100");
        return new VkLoader<>(getActivity().getApplicationContext(), call);
    }

    @Override
    public void onLoadFinished(Loader<List<VkUser>> loader, List<VkUser> data) {
        dismissProgressDialog();
        if (data == null) {
            showErrorSnackbar(getView(), R.string.error_common);
            return;
        }

        mVkUsers.clear();
        mVkUsers.addAll(data);
        mFriendsAdapter.notifyDataSetChanged();
        // TODO: 18.01.16 implement saving to database

    }

    @Override
    public void onLoaderReset(Loader<List<VkUser>> loader) {}

    @Override
    public void onItemClick(int adapterPosition) {
        mListener.openMessageFragment(mFriendsAdapter.getUserId(adapterPosition));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (IFriendsFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement FragmentButtonListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVkUsers = new ArrayList<>();
        mFriendsAdapter = new FriendsAdapter(mVkUsers, this);
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface IFriendsFragmentListener {
        void openMessageFragment(int id);
    }
}
