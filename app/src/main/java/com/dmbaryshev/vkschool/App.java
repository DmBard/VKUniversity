package com.dmbaryshev.vkschool;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.dmbaryshev.vkschool.view.MainActivity;
import com.dmbaryshev.vkschool.utils.DLog;
import com.squareup.leakcanary.LeakCanary;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class App extends Application {
    private static final String TAG = DLog.makeLogTag(App.class);
    private static Context context;

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
        App.context = getApplicationContext();
        VKSdk.initialize(this);
        LeakCanary.install(this);
    }
    public static Context getAppContext() {
        return App.context;
    }
}
