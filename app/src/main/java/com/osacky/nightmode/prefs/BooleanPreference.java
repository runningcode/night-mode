package com.osacky.nightmode.prefs;

import android.content.SharedPreferences;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class BooleanPreference {
    private final SharedPreferences preferences;
    private final String key;
    private final boolean defaultValue;
    private BehaviorSubject<Boolean> subject;

    public BooleanPreference(SharedPreferences preferences, String key) {
        this(preferences, key, false);
    }

    public BooleanPreference(SharedPreferences preferences, String key, boolean defaultValue) {
        this.preferences = preferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public Observable<Boolean> observe() {
        if (subject == null)
            subject = BehaviorSubject.create(get());
        return subject;
    }

    public boolean get() {
        return preferences.getBoolean(key, defaultValue);
    }

    public boolean isSet() {
        return preferences.contains(key);
    }

    public void set(boolean value) {
        preferences.edit().putBoolean(key, value).apply();
        if (subject != null) {
            subject.onNext(value);
        }
    }

    public void delete() {
        preferences.edit().remove(key).apply();
    }
}
