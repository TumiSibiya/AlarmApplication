package com.myapplications.alarm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    private static final String TAG = SettingActivity.class.getName();
    Switch darkThemeSwitch;
    Switch clockSecondsSwitch;
    boolean darkThemeSwitchChecked;
    boolean clockSecondsChecked;
    Button closeButton;
    Bundle bundle;
    String includeSecondsRequest = "Nothing";

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.settinigs_activity_layout);

        bundle = getIntent().getExtras();
        Log.d(TAG, ".......onCreate();.........include seconds request is " + includeSecondsRequest);

        setTitle("Settings");

        darkThemeSwitch = findViewById(R.id.darkTheme_switch_id);
        clockSecondsSwitch = findViewById(R.id.clockSeconds_switch_id);

        closeButton = findViewById(R.id.button_save_changes_id);

        darkThemeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean checked) {

                darkThemeSwitchChecked = checked;

                Toast.makeText(SettingActivity.this, "" + darkThemeSwitchChecked, Toast.LENGTH_SHORT).show();
            }
        });

        clockSecondsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton button, boolean checked) {
                Changes access = new Changes();

                if (checked) {
                    clockSecondsChecked = true;
                    access.settPatten("HH:mm:ss");

                    Toast.makeText(SettingActivity.this, "Seconds : " + clockSecondsChecked +
                            ",change " + access.gettPatten(), Toast.LENGTH_SHORT).show();


                } else {
                    clockSecondsChecked = false;

                    Toast.makeText(SettingActivity.this, "Seconds : " + clockSecondsChecked +
                            ",change " + access.gettPatten(), Toast.LENGTH_SHORT).show();
                }

            }

        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (includeSecondsRequest != null && includeSecondsRequest.equals("INCLUDE_SECONDS")) {

                    Intent goBack = new Intent(SettingActivity.this, ClockActivity.class);
                    goBack.putExtra(includeSecondsRequest, 0);
                    startActivity(goBack);
                }
                Log.d(TAG, ".........cloeOnClickListener().......include seconds request is " + includeSecondsRequest);
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean("darkThemeChecked", darkThemeSwitchChecked);
        editor.putBoolean("clockSecondsChecked", clockSecondsChecked);

        editor.apply();
    }

    @Override
    public void onStart() {
        super.onStart();

        bundle = getIntent().getExtras();


        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);

        darkThemeSwitch.setChecked(sharedPreferences.getBoolean("darkThemeChecked", darkThemeSwitchChecked));
        clockSecondsSwitch.setChecked(sharedPreferences.getBoolean("clockSecondsChecked", clockSecondsChecked));



/*        Changes access = new Changes();

        if(clockSecondsSwitch != null && clockSecondsSwitch.isChecked()){
            access.settPatten("HH:mm:ss");
        }else{
            access.settPatten("HH:mm");
        }
*/

    }
}