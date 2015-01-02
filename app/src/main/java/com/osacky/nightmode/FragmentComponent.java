package com.osacky.nightmode;

import dagger.Component;

@Component(modules = FragmentModule.class, dependencies = PowerApplicationComponent.class)
public interface FragmentComponent {
    void injectFragment(WelcomeFragment welcomeFragment);
}
