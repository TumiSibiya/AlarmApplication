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

    public boolean darkThemeSwitchChecked;
    public boolean clockSecondsChecked;

    Button closeButton;

    Changes accessChanges = new Changes();

    private String checkingString = "HH:mm";
    private String checkingStringSeconds = "HH:mm:ss";

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.settinigs_activity_layout);

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


                if (checked) {
                    clockSecondsChecked = true;

                } else {
                    clockSecondsChecked = false;

                }
                Toast.makeText(SettingActivity.this, "Seconds : " + clockSecondsChecked +
                        ",change " + accessChanges.gettPatten(), Toast.LENGTH_SHORT).show();

            }

        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "on closing tpatten is "+new Changes().gettPatten());

                Intent intent = new Intent(SettingActivity.this, ClockActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

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