package com.osacky.nightmode;

import dagger.Component;

@Component(modules = ServiceModule.class, dependencies = PowerApplicationComponent.class)
public interface AlarmLaterComponent {
    void injectService(AlarmLaterService service);
}
