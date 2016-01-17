package com.dmbaryshev.vkschool;

import android.app.Application;
import android.content.Intent;

import com.dmbaryshev.vkschool.ui.MainActivity;
import com.dmbaryshev.vkschool.utils.DLog;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class App extends Application {
    private static final String TAG = DLog.makeLogTag(App.class);

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                DLog.i(TAG, "onVKAccessTokenChanged: ");
                startActivity(new Intent(App.this, MainActivity.class));
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
    }
}
