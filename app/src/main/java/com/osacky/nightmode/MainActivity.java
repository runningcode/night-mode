package com.osacky.nightmode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    private final Intent mShareIntent = new Intent(Intent.ACTION_SEND);
    {
        mShareIntent.setType("text/plain");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_power_mute));
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new WelcomeFragment())
                    .commit();
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                startActivity(Intent.createChooser(mShareIntent, getString(R.string.share_picker)));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
