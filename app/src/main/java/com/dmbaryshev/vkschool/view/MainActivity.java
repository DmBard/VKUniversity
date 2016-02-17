package com.dmbaryshev.vkschool.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.dto.VkAudio;
import com.dmbaryshev.vkschool.model.dto.VkUser;
import com.dmbaryshev.vkschool.utils.DLog;
import com.dmbaryshev.vkschool.utils.DateTimeHelper;
import com.dmbaryshev.vkschool.utils.PreferencesHelper;
import com.dmbaryshev.vkschool.view.audio.fragment.AudioFragment;
import com.dmbaryshev.vkschool.view.friends.fragment.FriendsFragment;
import com.dmbaryshev.vkschool.view.messages.fragment.MessagesFragment;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

// TODO: 17.02.2016 create presenter
public class MainActivity extends AppCompatActivity
        implements FriendsFragment.IFriendsFragmentListener,
                   NavigationView.OnNavigationItemSelectedListener,
                   AudioFragment.IAudioFragmentListener {
    private static final String TAG = DLog.makeLogTag(MainActivity.class);

    private FragmentManager mManager;
    private VkUser          mVkUser;
    private DrawerLayout    mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                                                                 mDrawer,
                                                                 toolbar,
                                                                 R.string.navigation_drawer_open,
                                                                 R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mManager = getFragmentManager();

        if (savedInstanceState == null) {
            openOrLogin();
        }
    }

    private void openOrLogin() {
        if (!VKSdk.isLoggedIn()) {
            VKSdk.login(MainActivity.this,
                        "friends",
                        "messages",
                        "photos",
                        "audio",
                        "wall",
                        "groups",
                        "status",
                        "market");
        } else {
            openFriendsFragment();
        }
    }

    private void openFriendsFragment() {
        //        if (getCurrentFragmentTag() != null && getCurrentFragmentTag().equals(FriendsFragment.TAG)) {
        //            return;
        //        }
        replaceFragment(FriendsFragment.newInstance(), FriendsFragment.TAG, true);
    }

    private void replaceFragment(Fragment fragment, String tag, boolean backstack) {
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, tag);
        if (backstack) { fragmentTransaction.addToBackStack(null); }
        fragmentTransaction.commit();
        setToolbarTitle(tag);
    }

    private void setToolbarTitle(String tag) {
        if (tag == null) {
            getSupportActionBar().setTitle("Friends");
            getSupportActionBar().setSubtitle("");
            return;
        }
        if (tag.equals(MessagesFragment.TAG)) {
            getSupportActionBar().setTitle(String.format("%s %s",
                                                         mVkUser.firstName,
                                                         mVkUser.lastName));
            getSupportActionBar().setSubtitle("Last seen at " + DateTimeHelper.convertTimestampToString(
                    mVkUser.lastSeen.time));
        } else if (tag.equals(FriendsFragment.TAG)) {
            getSupportActionBar().setTitle("Friends");
            getSupportActionBar().setSubtitle("");
        }
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

    @Override
    public void openMessageFragment(VkUser vkUser) {
        mVkUser = vkUser;
        replaceFragment(MessagesFragment.newInstance(vkUser.id), MessagesFragment.TAG, true);
    }

    @NonNull
    private VKCallback<VKAccessToken> getVkCallback(final PreferencesHelper preferencesHelper) {
        return new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                final String accessToken = res.accessToken;
                DLog.i(TAG, "onResult: accessToken = " + accessToken);
                preferencesHelper.setToken(accessToken);
                openFriendsFragment();
            }

            @Override
            public void onError(VKError error) {
                DLog.i(TAG, "onResult: error = " + error.errorMessage);
            }
        };
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            int count = getFragmentManager().getBackStackEntryCount();
            if (count == 0) {
                super.onBackPressed();
            } else {
                String tag = getCurrentFragmentTag();
                getFragmentManager().popBackStack();
                setToolbarTitle(tag);
            }
        }
    }

    public String getCurrentFragmentTag() {
        if (mManager == null) {return null;}
        FragmentManager.BackStackEntry backStackEntry = mManager.getBackStackEntryAt(mManager.getBackStackEntryCount() - 1);
        if (backStackEntry == null) { return null; }
        return backStackEntry.getName();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_friends:
                openFriendsFragment();
                break;
            case R.id.nav_audio:
                openAudioFragment();
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openAudioFragment() {
        replaceFragment(AudioFragment.newInstance(), AudioFragment.TAG, true);
    }

    @Override
    public void openRecommendationsAudioFragment(VkAudio vkAudio) {

    }
}
