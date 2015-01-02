package com.osacky.nightmode;

import dagger.Component;

@Component(dependencies = PowerApplicationComponent.class, modules = ReceiverModule.class)
public interface ReceiverComponent {
    void injectReceiver(PowerBroadcastReceiver receiver);
}
