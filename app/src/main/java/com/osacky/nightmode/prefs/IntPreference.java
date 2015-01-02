package com.osacky.nightmode.prefs;

import android.content.SharedPreferences;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class IntPreference {

    private final SharedPreferences preferences;
    private final String key;
    private final int defaultValue;
    private BehaviorSubject<Integer> subject;

    public IntPreference(SharedPreferences preferences, String key) {
        this(preferences, key, 0);
    }

    public IntPreference(SharedPreferences preferences, String key, int defaultValue) {
        this.preferences = preferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }


    public Observable<Integer> observe() {
        if (subject == null)
            subject = BehaviorSubject.create(get());
        return subject;
    }

    public int get() {
        return preferences.getInt(key, defaultValue);
    }

    public boolean isSet() {
        return preferences.contains(key);
    }

    public void set(int value) {
        preferences.edit().putInt(key, value).apply();
        if (subject != null) {
            subject.onNext(value);
        }
    }

    public void delete() {
        preferences.edit().remove(key).apply();
    }
}
