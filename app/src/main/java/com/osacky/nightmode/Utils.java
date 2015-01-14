package com.osacky.nightmode;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.StrictMode;
import android.widget.Toast;

import com.osacky.nightmode.prefs.IntPreference;

import static android.content.Context.AUDIO_SERVICE;
import static android.content.Intent.ACTION_BATTERY_CHANGED;
import static android.media.AudioManager.RINGER_MODE_SILENT;
import static android.os.BatteryManager.BATTERY_PLUGGED_AC;
import static android.os.BatteryManager.BATTERY_PLUGGED_USB;
import static android.os.BatteryManager.EXTRA_PLUGGED;

public class Utils {
    private Utils() {}

    public static void enableStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
    }

    public static boolean isConnected(Context context) {
        Intent intent = context.getApplicationContext().registerReceiver(null, new IntentFilter(ACTION_BATTERY_CHANGED));
        int plugged = intent.getIntExtra(EXTRA_PLUGGED, -1);
        return plugged == BATTERY_PLUGGED_AC || plugged == BATTERY_PLUGGED_USB;
    }

    public static void enableRinger(Context context, IntPreference ringerMode) {
        AudioManager audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        ringerMode.set(audioManager.getRingerMode());
        audioManager.setRingerMode(RINGER_MODE_SILENT);
//        Toast.makeText(context, R.string.enabled_mute, Toast.LENGTH_SHORT).show();
    }
}
