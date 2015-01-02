package com.osacky.nightmode;

import com.osacky.nightmode.annotations.EnabledPref;
import com.osacky.nightmode.annotations.RingerMode;
import com.osacky.nightmode.annotations.TimePref;
import com.osacky.nightmode.prefs.BooleanPreference;
import com.osacky.nightmode.prefs.IntPreference;

import javax.inject.Singleton;

import dagger.Component;

@Component(
        modules = PowerApplicationModule.class
)
@Singleton
interface PowerApplicationComponent {
    void injectApplication(PowerApplication application);

    // expose these to child-components
    @TimePref IntPreference provideTimePref();
    @RingerMode IntPreference provideRingerMode();
    @EnabledPref BooleanPreference provideEnabledPref();
}
