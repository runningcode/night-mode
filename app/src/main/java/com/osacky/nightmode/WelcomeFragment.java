package com.osacky.nightmode;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.osacky.nightmode.annotations.EnabledPref;
import com.osacky.nightmode.prefs.BooleanPreference;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class WelcomeFragment extends Fragment {

    @InjectView(R.id.on_off_button) protected SwitchCompat mOnOffButton;
    @InjectView(R.id.button_text) protected TextView mTextView;

    @Inject @EnabledPref BooleanPreference mEnabledPref;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dagger_FragmentComponent.builder()
                .powerApplicationComponent(PowerApplication.get(getActivity()).getComponent())
                .fragmentModule(new FragmentModule())
                .build()
                .injectFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);
        setUpSwitch();
        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.on_off_background)
    void onBackgroundClick() {
        mEnabledPref.set(!mOnOffButton.isChecked());
        setUpSwitch();
    }

    @OnCheckedChanged(R.id.on_off_button)
    void onButtonClicked(boolean enabled) {
        mEnabledPref.set(enabled);
        setUpSwitch();
    }

    private void setUpSwitch() {
        if (mEnabledPref.get()) {
            mTextView.setText(R.string.on);
            mOnOffButton.setChecked(true);
        } else {
            mTextView.setText(R.string.off);
            mOnOffButton.setChecked(false);
        }
    }
}
