package com.osacky.nightmode;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

import com.osacky.nightmode.annotations.EnabledPref;
import com.osacky.nightmode.annotations.RingerMode;
import com.osacky.nightmode.prefs.BooleanPreference;
import com.osacky.nightmode.prefs.IntPreference;

import org.joda.time.LocalTime;

import javax.inject.Inject;

import timber.log.Timber;

import static android.app.AlarmManager.RTC_WAKEUP;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.AUDIO_SERVICE;
import static android.content.Intent.ACTION_POWER_CONNECTED;
import static android.content.Intent.ACTION_POWER_DISCONNECTED;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;

public class PowerBroadcastReceiver extends BroadcastReceiver {

    private static final LocalTime tenPM = new LocalTime(22, 0);
    private static final LocalTime sixAM = new LocalTime(6, 0);

    @Inject @RingerMode protected IntPreference ringerModePref;
    @Inject @EnabledPref protected BooleanPreference enabledPref;

    @Override public void onReceive(Context context, Intent intent) {
        Dagger_ReceiverComponent.builder()
                .powerApplicationComponent(PowerApplication.get(context).getComponent())
                .receiverModule(new ReceiverModule()).build()
                .injectReceiver(this);

        if (!enabledPref.get()) {
            return;
        }

        AudioManager audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        int ringerMode = audioManager.getRingerMode();
        if (ACTION_POWER_CONNECTED.equals(intent.getAction())) {
            Timber.d("power connected");
            LocalTime now = LocalTime.now();
            if (now.isAfter(tenPM) || now.isBefore(sixAM)) {
                Timber.i("turning on mute");
                Utils.enableRinger(context, ringerModePref);
            } else {
                Timber.d("setting mute alarm for later");
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                PendingIntent pendingIntent = PendingIntent.getService(context, 0, new Intent(context, AlarmLaterService.class), 0);
                long time = tenPM.toDateTimeToday().getMillis();
                if (SDK_INT >= KITKAT) {
                    alarmManager.setExact(RTC_WAKEUP, time, pendingIntent);
                } else {
                    alarmManager.set(RTC_WAKEUP, time, pendingIntent);
                }
            }
        } else if (ACTION_POWER_DISCONNECTED.equals(intent.getAction())) {
            Timber.d("power disconnected");
//            if (ringerMode == RINGER_MODE_SILENT) {
//                Timber.d("ringer mode is silent");
                audioManager.setRingerMode(ringerModePref.get());
                ringerModePref.delete();
            Toast.makeText(context, R.string.ringer_unmuted, Toast.LENGTH_SHORT).show();
//            } else {
                Timber.d("ringer mode is %s", ringerMode);
//            }

        }
    }
}
