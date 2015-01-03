package com.osacky.nightmode;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.osacky.nightmode.annotations.EnabledPref;
import com.osacky.nightmode.prefs.BooleanPreference;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class WelcomeFragment extends Fragment {

    @InjectView(R.id.on_off_button) protected SwitchCompat mOnOffButton;
    @InjectView(R.id.red_warning) protected TextView mWarningText;
    @InjectView(R.id.text_switcher) protected TextSwitcher mTextSwitcher;

    @Inject @EnabledPref BooleanPreference mEnabledPref;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Dagger_FragmentComponent.builder()
                .powerApplicationComponent(PowerApplication.get(activity).getComponent())
                .fragmentModule(new FragmentModule())
                .build()
                .injectFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);
        mTextSwitcher.setFactory(mFactory);
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
            mTextSwitcher.setInAnimation(getActivity(), R.anim.in_anim_from_left);
            mTextSwitcher.setText(getActivity().getString(R.string.on));
            mOnOffButton.setChecked(true);
            mWarningText.setVisibility(GONE);
        } else {
            mTextSwitcher.setInAnimation(getActivity(), R.anim.in_anim_from_right);
            mTextSwitcher.setText(getActivity().getString(R.string.off));
            mOnOffButton.setChecked(false);
            mWarningText.setVisibility(VISIBLE);
        }
    }

    private final ViewSwitcher.ViewFactory mFactory = new ViewSwitcher.ViewFactory() {

        @Override
        public View makeView() {
            // Create a new TextView
            TextView t = new TextView(WelcomeFragment.this.getActivity());
            t.setTextAppearance(WelcomeFragment.this.getActivity(), R.style.TextAppearance_AppCompat_Title_Inverse);
            return t;
        }
    };
}
