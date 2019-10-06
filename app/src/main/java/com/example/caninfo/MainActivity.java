package com.example.caninfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import java.util.Collection;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String inf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // The callback can be enabled or disabled here or in handleOnBackPressed()

        settings_load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_exit) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }

        if (id == R.id.action_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void settings_load() {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("rpm",false) == false) {
            findViewById(R.id.Airbag).setVisibility(View.GONE);
            findViewById(R.id.Airbag_text).setVisibility(View.GONE);
        } else {
            findViewById(R.id.Airbag).setVisibility(View.VISIBLE);
            findViewById(R.id.Airbag_text).setVisibility(View.VISIBLE);
        }

        if (prefs.getBoolean("clap", false) == false) {
            findViewById(R.id.Handbrake).setVisibility(View.GONE);
        } else {
            findViewById(R.id.Handbrake).setVisibility(View.VISIBLE);
        }

    }

    public void check_update(View view) {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        Log.i("info", prefs.getString("canbus","/dev/ttyMT5"));

        UsbManager m = (UsbManager)getApplicationContext().getSystemService(USB_SERVICE);
        HashMap<String, UsbDevice> devices = m.getDeviceList();
        Collection<UsbDevice> ite = devices.values();
        UsbDevice[] usbs = ite.toArray(new UsbDevice[]{});
        for (UsbDevice usb : usbs){
            Log.i("info", usb.getDeviceName()); //Log all device names
        }

    }
}
