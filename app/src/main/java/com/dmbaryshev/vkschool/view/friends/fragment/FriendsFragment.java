package com.dmbaryshev.vkschool.view.friends.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.view_model.UserVM;
import com.dmbaryshev.vkschool.presenter.FriendsPresenter;
import com.dmbaryshev.vkschool.utils.DLog;
import com.dmbaryshev.vkschool.view.common.BaseFragment;
import com.dmbaryshev.vkschool.view.common.ICommonFragmentCallback;
import com.dmbaryshev.vkschool.view.common.IHolderClick;
import com.dmbaryshev.vkschool.view.friends.adapter.FriendsAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends BaseFragment<FriendsPresenter> implements IFriendsView, IHolderClick {
    public static final String TAG = DLog.makeLogTag(FriendsFragment.class);
    private ProgressDialog mProgressDialog;
    private IFriendsFragmentListener mListener;
    private FriendsAdapter           mFriendsAdapter;
    private List<UserVM>             mVkUsers;

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

    @Override
    protected FriendsPresenter getPresenter() {
        return new FriendsPresenter();
    }

    @Override
    public void onItemClick(int adapterPosition) {
        mListener.openMessageFragment(mFriendsAdapter.getUser(adapterPosition));
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
        mListener.showTitle(getString(R.string.fragment_title_friends));
        mListener.showSubtitle("");
        mPresenter.bindView(this);
        mPresenter.load();
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

    @Override
    public void showError(String errorText) {
        showErrorSnackbar(getView(), errorText);
    }

    @Override
    public void stopLoad() {
        dismissProgressDialog();
    }

    @Override
    public void showError(int errorTextRes) {
        showErrorSnackbar(getView(), errorTextRes);
    }

    @Override
    public void startLoad() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) { return; }
        mProgressDialog = ProgressDialog.show(getActivity(),
                                              getActivity().getString(R.string.progress_dialog_title),
                                              getActivity().getString(R.string.progress_dialog_message));    }

    @Override
    public void showData(List<UserVM> data) {
        mVkUsers.clear();
        mVkUsers.addAll(data);
        mFriendsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCount(int count) {

    }

    public interface IFriendsFragmentListener extends ICommonFragmentCallback {
        void openMessageFragment(UserVM userVM);
    }
}
