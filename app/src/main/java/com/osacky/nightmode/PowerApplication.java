package com.osacky.nightmode;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import java.util.Locale;

import timber.log.Timber;

public class PowerApplication extends Application {

    private PowerApplicationComponent mComponent;

    @Override public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Utils.enableStrictMode();
        } else {
        }
        Crashlytics.start(this);
        Crashlytics.setString("locale", Locale.getDefault().toString());

        mComponent = Dagger_PowerApplicationComponent.builder()
                .powerApplicationModule(new PowerApplicationModule(this))
                .build();
        mComponent.injectApplication(this);
    }

    public static PowerApplication get(Context context) {
        return (PowerApplication) context.getApplicationContext();
    }

    PowerApplicationComponent getComponent() {
        return mComponent;
    }
}
