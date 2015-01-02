package com.osacky.nightmode;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.osacky.nightmode.annotations.EnabledPref;
import com.osacky.nightmode.annotations.RingerMode;
import com.osacky.nightmode.annotations.TimePref;
import com.osacky.nightmode.prefs.BooleanPreference;
import com.osacky.nightmode.prefs.IntPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PowerApplicationModule {
    private final Application application;

    PowerApplicationModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton Application application() {
        return application;
    }

    @Provides @Singleton SharedPreferences provideSharedPref(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides @Singleton @TimePref IntPreference provideTimePref(SharedPreferences preferences) {
        return new IntPreference(preferences, "time_pref");
    }

    @Provides @Singleton @RingerMode IntPreference provideRingerModePref(SharedPreferences preferences) {
        return new IntPreference(preferences, "ringer_pref", -1);
    }

    @Provides @Singleton @EnabledPref BooleanPreference provideEnabledPref(SharedPreferences preferences) {
        return new BooleanPreference(preferences, "enabled_pref", true);
    }
}
