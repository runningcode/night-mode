package com.osacky.nightmode;

import android.app.IntentService;
import android.content.Intent;

import com.osacky.nightmode.annotations.EnabledPref;
import com.osacky.nightmode.annotations.RingerMode;
import com.osacky.nightmode.prefs.BooleanPreference;
import com.osacky.nightmode.prefs.IntPreference;

import javax.inject.Inject;

import hugo.weaving.DebugLog;

public class AlarmLaterService extends IntentService {

    @Inject @RingerMode IntPreference mRingerMode;
    @Inject @EnabledPref BooleanPreference mEnabledPref;

    public AlarmLaterService() {
        super(AlarmLaterService.class.getName());
    }

    @DebugLog
    @Override public void onCreate() {
        super.onCreate();
        Dagger_AlarmLaterComponent.builder()
                .powerApplicationComponent(PowerApplication.get(this).getComponent())
                .serviceModule(new ServiceModule())
                .build()
                .injectService(this);
    }

    @DebugLog
    @Override protected void onHandleIntent(Intent intent) {
        if (mEnabledPref.get() && Utils.isConnected(this)) {
            Utils.enableRinger(this, mRingerMode);
        }
    }
}
