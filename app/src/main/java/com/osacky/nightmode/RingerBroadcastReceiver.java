package com.osacky.nightmode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import timber.log.Timber;

public class RingerBroadcastReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        Timber.i("received intent %s", intent);
    }
}
