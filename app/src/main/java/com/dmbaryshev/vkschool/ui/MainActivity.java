package com.dmbaryshev.vkschool.ui;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.ui.friends.fragment.FriendsFragment;
import com.dmbaryshev.vkschool.utils.DLog;
import com.dmbaryshev.vkschool.utils.PreferencesHelper;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = DLog.makeLogTag(MainActivity.class);

    private FragmentManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mManager = getFragmentManager();

        openOrLogin();
    }

    private void openOrLogin() {
        if (!VKSdk.isLoggedIn()) {
            VKSdk.login(MainActivity.this,
                        "friends",
                        "photos",
                        "audio",
                        "wall",
                        "groups",
                        "status",
                        "market");
        } else {
            replaceFragment(FriendsFragment.newInstance());
        }
    }

    private void replaceFragment(FriendsFragment fragment) {
        mManager.beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final PreferencesHelper preferencesHelper = PreferencesHelper.getInstance(MainActivity.this);
        if (!isVkActivityResult(requestCode, resultCode, data, preferencesHelper)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean isVkActivityResult(int requestCode,
                                       int resultCode,
                                       Intent data,
                                       final PreferencesHelper preferencesHelper) {
        return VKSdk.onActivityResult(requestCode,
                                       resultCode,
                                       data,
                                       getVkCallback(preferencesHelper));
    }

    @NonNull
    private VKCallback<VKAccessToken> getVkCallback(final PreferencesHelper preferencesHelper) {
        return new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                final String accessToken = res.accessToken;
                DLog.i(TAG, "onResult: accessToken = " + accessToken);
                preferencesHelper.setToken(accessToken);
                replaceFragment(FriendsFragment.newInstance());
            }

            @Override
            public void onError(VKError error) {
                DLog.i(TAG, "onResult: error = " + error.errorMessage);
            }
        };
    }
}