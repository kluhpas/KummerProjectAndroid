package com.kluhpas.kummerproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {

    private boolean[] colorVal = {false, false, false, false, false};
    private boolean[] soundVal = {false, false, false, false};
    private boolean[] pictureVal = {false, false, false, false, false};

    private CheckBox color_0, color_1, color_2, color_3, color_4;
    private CheckBox sound_0, sound_1, sound_2, sound_3;
    private CheckBox picture_0, picture_1, picture_2, picture_3, picture_4;
    private EditText delayMin, delayMax, showTime, workTime, restTime, sets;
    private Switch switch_timer_settings;
    private FloatingActionButton startFab;
    private NavigationView navigationView;

    private int count_input_selected = 0, tmp_delayMin = 0, tmp_delayMax = 0, tmp_showTime = 0, tmp_workTime = 0, tmp_restTime = 0, tmp_sets = 0;
    private boolean flag_btn_enable_checkboxes = false, flag_btn_enable_delayMin = false, flag_btn_enable_delayMax = false, flag_btn_enable_showTime = false,
            flag_btn_enable_workTime = false, flag_btn_enable_restTime = false, flag_btn_enable_sets = false, flag_btn_enable_switch_timer_settings = false;

    private final String filename_config = "user_config.txt";

    private DrawerLayout mDrawerLayout;

    private userConfig default_config;

    private SharedPreferences sharedPref;

    private List<userConfig> listConfig = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        color_0 = findViewById(R.id.checkBox_color_0);
        color_1 = findViewById(R.id.checkBox_color_1);
        color_2 = findViewById(R.id.checkBox_color_2);
        color_3 = findViewById(R.id.checkBox_color_3);
        color_4 = findViewById(R.id.checkBox_color_4);
        sound_0 = findViewById(R.id.checkBox_sound_0);
        sound_1 = findViewById(R.id.checkBox_sound_1);
        sound_2 = findViewById(R.id.checkBox_sound_2);
        sound_3 = findViewById(R.id.checkBox_sound_3);
        picture_0 = findViewById(R.id.checkBox_picture_0);
        picture_1 = findViewById(R.id.checkBox_picture_1);
        picture_2 = findViewById(R.id.checkBox_picture_2);
        picture_3 = findViewById(R.id.checkBox_picture_3);
        picture_4 = findViewById(R.id.checkBox_picture_4);
        delayMin = findViewById(R.id.nptNumberDelayMin);
        delayMax = findViewById(R.id.nptNumberDelayMax);
        showTime = findViewById(R.id.nptNumberShowTime);
        workTime = findViewById(R.id.nptNumberWorkTime);
        restTime = findViewById(R.id.nptNumberRestTime);
        sets = findViewById(R.id.nptNumberSets);
        switch_timer_settings = findViewById(R.id.switch_timer_settings);
        navigationView = findViewById(R.id.nav_view);

        BottomAppBar bar = findViewById(R.id.bottomAppBar);
        setSupportActionBar(bar);
        if  (getSupportActionBar() != null) {
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }

        startFab = findViewById(R.id.fab);


        mDrawerLayout = findViewById(R.id.drawer_layout);

        default_config = new userConfig("Default Config", false, false, false, false, false, false, false, false, false, false, false, false, false, false, Integer.parseInt(getString(R.string.default_value_delay_min)), Integer.parseInt(getString(R.string.default_value_delay_max)), Integer.parseInt(getString(R.string.default_value_time_show)), Integer.parseInt(getString(R.string.default_value_work_time)), Integer.parseInt(getString(R.string.default_value_rest_time)), Integer.parseInt(getString(R.string.default_value_sets)), false);

        readFileWriteList();

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                        // set item as selected to persist highlight
                        menuItem.setChecked(true);

                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        switch (menuItem.getItemId()) {
                            case R.id.info:
                                Intent intent = new Intent(MainActivity.this, Info.class);
                                finish();
                                startActivity(intent);
                                break;

                            case R.id.default_config:
                                update_main_activity_data(default_config);
                                break;

                            case Menu.FIRST:
                                for (int i = 0; i < listConfig.size(); i++) {
                                    if (menuItem.getTitle().toString().compareToIgnoreCase(listConfig.get(i).getName_Config()) == 0)
                                        update_main_activity_data(listConfig.get(i));
                                }
                                break;
                        }
                        return true;
                    }
                });

        updateItemConfig();

        sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.com_kluhpas_KummerProject_LAST_PREFERENCE), Context.MODE_PRIVATE);
        if (!getIntent().getBooleanExtra("firstAccess", true)) {
            color_0.setChecked(sharedPref.getBoolean("color_0", false));
            color_1.setChecked(sharedPref.getBoolean("color_1", false));
            color_2.setChecked(sharedPref.getBoolean("color_2", false));
            color_3.setChecked(sharedPref.getBoolean("color_3", false));
            color_4.setChecked(sharedPref.getBoolean("color_4", false));
            sound_0.setChecked(sharedPref.getBoolean("sound_0", false));
            sound_1.setChecked(sharedPref.getBoolean("sound_1", false));
            sound_2.setChecked(sharedPref.getBoolean("sound_2", false));
            sound_3.setChecked(sharedPref.getBoolean("sound_3", false));
            picture_0.setChecked(sharedPref.getBoolean("picture_0", false));
            picture_1.setChecked(sharedPref.getBoolean("picture_1", false));
            picture_2.setChecked(sharedPref.getBoolean("picture_2", false));
            picture_3.setChecked(sharedPref.getBoolean("picture_3", false));
            picture_4.setChecked(sharedPref.getBoolean("picture_4", false));
            delayMin.setText(String.valueOf(sharedPref.getInt("delayMin", Integer.parseInt(getString(R.string.default_value_delay_min)))));
            delayMax.setText(String.valueOf(sharedPref.getInt("delayMax", Integer.parseInt(getString(R.string.default_value_delay_max)))));
            showTime.setText(String.valueOf(sharedPref.getInt("showTime", Integer.parseInt(getString(R.string.default_value_time_show)))));
            workTime.setText(String.valueOf(sharedPref.getInt("workTime", Integer.parseInt(getString(R.string.default_value_work_time)))));
            restTime.setText(String.valueOf(sharedPref.getInt("restTime", Integer.parseInt(getString(R.string.default_value_rest_time)))));
            sets.setText(String.valueOf(sharedPref.getInt("sets", Integer.parseInt(getString(R.string.default_value_sets)))));
            switch_timer_settings.setChecked(sharedPref.getBoolean("switch_timer_settings", false));
            click_switch_timer_settings(this.switch_timer_settings);

            createArray();
        }
        else {
            switch_timer_settings.setChecked(false);
            click_switch_timer_settings(this.switch_timer_settings);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.commit();
        }

        update_flag();

        if (count_input_selected > 0) {
            TextView countText = findViewById(R.id.txtView_input_count);
            countText.setText(String.valueOf(count_input_selected));
        }

        enableButton();

        delayMin.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                update_tmp_var();

                if (tmp_delayMin <= tmp_delayMax && tmp_delayMin > tmp_showTime) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("delayMin", Integer.parseInt(delayMin.getText().toString()));
                    editor.apply();

                    delayMin.setError(null);
                    flag_btn_enable_delayMin = true;

                    delayMax.setError(null);
                    flag_btn_enable_delayMax = true;

                    showTime.setError(null);
                    flag_btn_enable_showTime = true;

                    enableButton();
                }
                else if (tmp_delayMin > tmp_delayMax){
                    CharSequence text = getResources().getString(R.string.txtView_delay_min) + getResources().getString(R.string.error_lower) + getResources().getString(R.string.txtView_delay_max);
                    delayMin.setError(text);

                    flag_btn_enable_delayMin = false;

                    enableButton();
                }
                else {
                    CharSequence text = getResources().getString(R.string.txtView_delay_min) + getResources().getString(R.string.error_lower) + getResources().getString(R.string.txtView_time_show);
                    delayMin.setError(text);

                    flag_btn_enable_delayMin = false;

                    enableButton();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        delayMax.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                update_tmp_var();

                if (tmp_delayMax >= tmp_delayMin) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("delayMax", Integer.parseInt(delayMax.getText().toString()));
                    editor.apply();

                    delayMax.setError(null);
                    flag_btn_enable_delayMax = true;

                    delayMin.setError(null);
                    flag_btn_enable_delayMin = true;

                    enableButton();
                }
                else {
                    CharSequence text = getResources().getString(R.string.txtView_delay_max) + getResources().getString(R.string.error_greater) + getResources().getString(R.string.txtView_delay_min);
                    delayMax.setError(text);

                    flag_btn_enable_delayMax = false;

                    enableButton();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        showTime.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                update_tmp_var();

                if (tmp_showTime <= tmp_delayMin && tmp_showTime > 0) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("showTime", Integer.parseInt(showTime.getText().toString()));
                    editor.apply();

                    showTime.setError(null);
                    flag_btn_enable_showTime = true;

                    delayMin.setError(null);
                    flag_btn_enable_delayMin = true;

                    enableButton();
                }
                else if (tmp_showTime > tmp_delayMin){
                    CharSequence text = getResources().getString(R.string.txtView_time_show) + getResources().getString(R.string.error_lower) + getResources().getString(R.string.txtView_delay_min);
                    showTime.setError(text);

                    flag_btn_enable_showTime = false;

                    enableButton();
                }
                else {
                    CharSequence text = getResources().getString(R.string.txtView_time_show) + getResources().getString(R.string.error_zero_showTime);
                    showTime.setError(text);

                    flag_btn_enable_showTime = false;

                    enableButton();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        workTime.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (workTime.getText().toString().isEmpty()) {
                    CharSequence text = getResources().getString(R.string.txtView_work_time) + getResources().getString(R.string.error_empty);
                    workTime.setError(text);

                    flag_btn_enable_workTime = false;

                    enableButton();
                }
                else if (Integer.parseInt(workTime.getText().toString()) == 0) {
                    CharSequence text = getResources().getString(R.string.txtView_work_time) + getResources().getString(R.string.error_empty);
                    workTime.setError(text);

                    flag_btn_enable_workTime = false;

                    enableButton();
                }
                else {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("workTime", Integer.parseInt(workTime.getText().toString()));
                    editor.apply();

                    workTime.setError(null);
                    flag_btn_enable_workTime = true;

                    enableButton();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        restTime.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (restTime.getText().toString().isEmpty()) {
                    CharSequence text = getResources().getString(R.string.txtView_rest_time) + getResources().getString(R.string.error_empty);
                    restTime.setError(text);

                    flag_btn_enable_restTime = false;

                    enableButton();
                }
                else if (Integer.parseInt(restTime.getText().toString()) == 0) {
                    CharSequence text = getResources().getString(R.string.txtView_rest_time) + getResources().getString(R.string.error_empty);
                    restTime.setError(text);

                    flag_btn_enable_restTime = false;

                    enableButton();
                }
                else {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("restTime", Integer.parseInt(restTime.getText().toString()));
                    editor.apply();

                    restTime.setError(null);
                    flag_btn_enable_restTime = true;

                    enableButton();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        sets.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (sets.getText().toString().isEmpty()) {
                    CharSequence text = getResources().getString(R.string.txtView_sets) + getResources().getString(R.string.error_empty);
                    sets.setError(text);

                    flag_btn_enable_sets = false;

                    enableButton();
                }
                else {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("sets", Integer.parseInt(sets.getText().toString()));
                    editor.apply();

                    sets.setError(null);
                    flag_btn_enable_sets = true;

                    enableButton();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_bar_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.btn_add:
                saveConfig();
                return true;
            case R.id.btn_del:
                delConfig();
                return true;
            case R.id.btn_update:
                updateConfig();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* Called when the user taps a checkBox */
    public void updateCounter(View view) {

        TextView countText = findViewById(R.id.txtView_input_count);
        if (!countText.getText().toString().isEmpty())
            count_input_selected = Integer.parseInt(countText.getText().toString());
        else
            count_input_selected = 0;

        SharedPreferences.Editor editor = sharedPref.edit();

        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox_color_0: {
                if (checked) {
                    count_input_selected++;
                    editor.putBoolean("color_0", true);
                } else {
                    count_input_selected--;
                    editor.putBoolean("color_0", false);
                }
                break;
            }
            case R.id.checkBox_color_1: {
                if (checked) {
                    count_input_selected++;
                    editor.putBoolean("color_1", true);
                } else {
                    count_input_selected--;
                    editor.putBoolean("color_1", false);
                }
                break;
            }
            case R.id.checkBox_color_2: {
                if (checked) {
                    count_input_selected++;
                    editor.putBoolean("color_2", true);
                } else {
                    count_input_selected--;
                    editor.putBoolean("color_2", false);
                }
                break;
            }
            case R.id.checkBox_color_3: {
                if (checked) {
                    count_input_selected++;
                    editor.putBoolean("color_3", true);
                } else {
                    count_input_selected--;
                    editor.putBoolean("color_3", false);
                }
                break;
            }
            case R.id.checkBox_color_4: {
                if (checked) {
                    count_input_selected++;
                    editor.putBoolean("color_4", true);
                } else {
                    count_input_selected--;
                    editor.putBoolean("color_4", false);
                }
                break;
            }
            case R.id.checkBox_sound_0: {
                if (checked) {
                    count_input_selected++;
                    editor.putBoolean("sound_0", true);
                } else {
                    count_input_selected--;
                    editor.putBoolean("sound_0", false);
                }
                break;
            }
            case R.id.checkBox_sound_1: {
                if (checked) {
                    count_input_selected++;
                    editor.putBoolean("sound_1", true);
                } else {
                    count_input_selected--;
                    editor.putBoolean("sound_1", false);
                }
                break;
            }
            case R.id.checkBox_sound_2: {
                if (checked) {
                    count_input_selected++;
                    editor.putBoolean("sound_2", true);
                } else {
                    count_input_selected--;
                    editor.putBoolean("sound_2", false);
                }
                break;
            }
            case R.id.checkBox_sound_3: {
                if (checked) {
                    count_input_selected++;
                    editor.putBoolean("sound_3", true);
                } else {
                    count_input_selected--;
                    editor.putBoolean("sound_3", false);
                }
                break;
            }
            case R.id.checkBox_picture_0: {
                if (checked) {
                    count_input_selected++;
                    editor.putBoolean("picture_0", true);
                } else {
                    count_input_selected--;
                    editor.putBoolean("picture_0", false);
                }
                break;
            }
            case R.id.checkBox_picture_1: {
                if (checked) {
                    count_input_selected++;
                    editor.putBoolean("picture_1", true);
                } else {
                    count_input_selected--;
                    editor.putBoolean("picture_1", false);
                }
                break;
            }
            case R.id.checkBox_picture_2: {
                if (checked) {
                    count_input_selected++;
                    editor.putBoolean("picture_2", true);
                } else {
                    count_input_selected--;
                    editor.putBoolean("picture_2", false);
                }
                break;
            }
            case R.id.checkBox_picture_3: {
                if (checked) {
                    count_input_selected++;
                    editor.putBoolean("picture_3", true);
                } else {
                    count_input_selected--;
                    editor.putBoolean("picture_3", false);
                }
                break;
            }
            case R.id.checkBox_picture_4: {
                if (checked) {
                    count_input_selected++;
                    editor.putBoolean("picture_4", true);
                } else {
                    count_input_selected--;
                    editor.putBoolean("picture_4", false);
                }
                break;
            }
            default: break;
        }

        editor.apply();

        if (count_input_selected > 0)
            flag_btn_enable_checkboxes = true;
        else
            flag_btn_enable_checkboxes = false;


        enableButton();

        countText.setText(String.valueOf(count_input_selected));
    }

    /* Called when the user taps the btnStart */
    public void start(View view) {

        createArray();

        update_tmp_var ();

        if (tmp_showTime > 1)
            tmp_showTime--;

        int[] signalVal = {
                tmp_delayMin,
                tmp_delayMax,
                tmp_showTime
        };


        int[] timerVal = {
                tmp_workTime,
                tmp_restTime,
                tmp_sets
        };

        Intent intent = new Intent(MainActivity.this, ShowSignal.class);
        intent.putExtra("timer_isEnable", flag_btn_enable_switch_timer_settings);
        intent.putExtra("signalVal", signalVal);
        intent.putExtra("timerVal", timerVal);
        intent.putExtra("colorVal", colorVal);
        intent.putExtra("soundVal", soundVal);
        intent.putExtra("pictureVal", pictureVal);
        finish();
        startActivity(intent);
    }

    public void click_switch_timer_settings (View view) {
        if (switch_timer_settings.isChecked()) {
            flag_btn_enable_switch_timer_settings = true;

            findViewById(R.id.txtView_title_timer_settings).setEnabled(true);
            findViewById(R.id.txtInputLayout_workTime).setEnabled(true);
            findViewById(R.id.txtInputLayout_restTime).setEnabled(true);
            findViewById(R.id.txtInputLayout_sets).setEnabled(true);
            workTime.setEnabled(true);
            restTime.setEnabled(true);
            sets.setEnabled(true);

            workTime.setText(workTime.getText().toString());
            restTime.setText(restTime.getText().toString());
            sets.setText(sets.getText().toString());

        }
        else {
            flag_btn_enable_switch_timer_settings = false;

            findViewById(R.id.txtView_title_timer_settings).setEnabled(false);
            findViewById(R.id.txtInputLayout_workTime).setEnabled(false);
            findViewById(R.id.txtInputLayout_restTime).setEnabled(false);
            findViewById(R.id.txtInputLayout_sets).setEnabled(false);
            workTime.setEnabled(false);
            restTime.setEnabled(false);
            sets.setEnabled(false);

            workTime.setError(null);
            restTime.setError(null);
            sets.setError(null);
        }

        sharedPref.edit().putBoolean("switch_timer_settings", flag_btn_enable_switch_timer_settings).apply();

        enableButton();
    }

    private void saveConfig () {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.title_dialog_save_config);
        builder.setMessage(R.string.message_dialog_save_config);

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = ((LayoutInflater) inflater).inflate(R.layout.custom_add_dialog, null);
        builder.setView(dialogView);

        final EditText edt = dialogView.findViewById(R.id.editText_input_name_config);

        builder.setPositiveButton(R.string.positiveButton_add_dialog, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                update_tmp_var();

                int i = 0;
                boolean flag = false;
                if (!edt.getText().toString().isEmpty()) {
                    if (listConfig.size() > 0) {
                        do {
                            if (listConfig.get(i).getName_Config().compareToIgnoreCase(edt.getText().toString()) != 0) {
                                i++;
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.error_multiple_name, Toast.LENGTH_LONG).show();
                                flag = true;
                            }

                        } while (i < listConfig.size() && !flag);
                    }

                    if (!flag) {
                        userConfig tmp = new userConfig(edt.getText().toString(), color_0.isChecked(), color_1.isChecked(), color_2.isChecked(), color_3.isChecked(), color_4.isChecked(), sound_0.isChecked(), sound_1.isChecked(), sound_2.isChecked(), sound_3.isChecked(), picture_0.isChecked(), picture_1.isChecked(), picture_2.isChecked(), picture_3.isChecked(), picture_4.isChecked(), tmp_delayMin, tmp_delayMax, tmp_showTime, tmp_workTime, tmp_restTime, tmp_sets, flag_btn_enable_switch_timer_settings);
                        listConfig.add(tmp);

                        readListWriteFile();

                        updateItemConfig();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.error_string_empty, Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton(R.string.negativeButton_dialog, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateConfig () {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.title_dialog_save_config);
        builder.setMessage(R.string.message_dialog_delete_update_config);

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = ((LayoutInflater) inflater).inflate(R.layout.custom_update_dialog, null);
        builder.setView(dialogView);

        final Spinner spnr = dialogView.findViewById(R.id.spnr_update);

        List<String> tmp = new ArrayList<>();
        for (int i = 0; i < listConfig.size(); i++) {
            tmp.add(listConfig.get(i).getName_Config());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, tmp);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr.setAdapter(dataAdapter);

        builder.setPositiveButton(R.string.positiveButton_add_dialog, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                update_tmp_var();
                userConfig tmp = new userConfig(spnr.getSelectedItem().toString(), color_0.isChecked(), color_1.isChecked(), color_2.isChecked(), color_3.isChecked(), color_4.isChecked(), sound_0.isChecked(), sound_1.isChecked(), sound_2.isChecked(), sound_3.isChecked(), picture_0.isChecked(), picture_1.isChecked(), picture_2.isChecked(), picture_3.isChecked(), picture_4.isChecked(), tmp_delayMin, tmp_delayMax, tmp_showTime, tmp_workTime, tmp_restTime, tmp_sets, flag_btn_enable_switch_timer_settings);
                for (int i = 0; i < listConfig.size(); i++) {
                    if (listConfig.get(i).getName_Config().compareToIgnoreCase(spnr.getSelectedItem().toString()) == 0) {
                        listConfig.set(i, tmp);
                    }
                }

                readListWriteFile();
            }
        });
        builder.setNegativeButton(R.string.negativeButton_dialog, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void delConfig () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_dialog_delete_config);
        builder.setMessage(R.string.message_dialog_delete_update_config);

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_del_dialog, null);
        builder.setView(dialogView);

        final Spinner spnr = dialogView.findViewById(R.id.spnr_del);

        List<String> tmp = new ArrayList<>();
        for (int i = 0; i < listConfig.size(); i++) {
            tmp.add(listConfig.get(i).getName_Config());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, tmp);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr.setAdapter(dataAdapter);

        builder.setPositiveButton(R.string.positiveButton_del_dialog, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                update_tmp_var();
                for (int i = 0; i < listConfig.size(); i++) {
                    if (listConfig.get(i).getName_Config().compareToIgnoreCase(spnr.getSelectedItem().toString()) == 0) {
                        listConfig.remove(i);
                    }
                }
                readListWriteFile();

                updateItemConfig();
            }
        });
        builder.setNegativeButton(R.string.negativeButton_dialog, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createArray () {

        count_input_selected = 0;

        if (color_0.isChecked()) { colorVal[0] = true; count_input_selected++; } else colorVal[0] = false;
        if (color_1.isChecked()) { colorVal[1] = true; count_input_selected++; } else colorVal[1] = false;
        if (color_2.isChecked()) { colorVal[2] = true; count_input_selected++; } else colorVal[2] = false;
        if (color_3.isChecked()) { colorVal[3] = true; count_input_selected++; } else colorVal[3] = false;
        if (color_4.isChecked()) { colorVal[4] = true; count_input_selected++; } else colorVal[4] = false;
        if (sound_0.isChecked()) { soundVal[0] = true; count_input_selected++; } else soundVal[0] = false;
        if (sound_1.isChecked()) { soundVal[1] = true; count_input_selected++; } else soundVal[1] = false;
        if (sound_2.isChecked()) { soundVal[2] = true; count_input_selected++; } else soundVal[2] = false;
        if (sound_3.isChecked()) { soundVal[3] = true; count_input_selected++; } else soundVal[3] = false;
        if (picture_0.isChecked()) { pictureVal[0] = true; count_input_selected++; } else pictureVal[0] = false;
        if (picture_1.isChecked()) { pictureVal[1] = true; count_input_selected++; } else pictureVal[1] = false;
        if (picture_2.isChecked()) { pictureVal[2] = true; count_input_selected++; } else pictureVal[2] = false;
        if (picture_3.isChecked()) { pictureVal[3] = true; count_input_selected++; } else pictureVal[3] = false;
        if (picture_4.isChecked()) { pictureVal[4] = true; count_input_selected++; } else pictureVal[4] = false;
    }

    private void update_flag () {
        if (count_input_selected > 0) {
            flag_btn_enable_checkboxes = true;
        }
        else {
            flag_btn_enable_checkboxes = false;
        }

        if (Integer.parseInt(delayMin.getText().toString()) <= Integer.parseInt(delayMax.getText().toString()))
            flag_btn_enable_delayMin = true;
        else
            flag_btn_enable_delayMin = false;

        if (Integer.parseInt(delayMax.getText().toString()) >= Integer.parseInt(delayMin.getText().toString()))
            flag_btn_enable_delayMax = true;
        else
            flag_btn_enable_delayMax = false;

        if (Integer.parseInt(showTime.getText().toString()) <= Integer.parseInt(delayMin.getText().toString()))
            flag_btn_enable_showTime = true;
        else
            flag_btn_enable_showTime = false;

        if (workTime.getText().toString().isEmpty())
            flag_btn_enable_workTime = false;
        else
            flag_btn_enable_workTime = true;

        if (restTime.getText().toString().isEmpty())
            flag_btn_enable_restTime = false;
        else
            flag_btn_enable_restTime = true;

        if (sets.getText().toString().isEmpty())
            flag_btn_enable_sets = false;
        else
            flag_btn_enable_sets = true;

        flag_btn_enable_switch_timer_settings = switch_timer_settings.isChecked();
    }

    private void enableButton () {
        if (flag_btn_enable_checkboxes && flag_btn_enable_delayMin && flag_btn_enable_delayMax && flag_btn_enable_showTime
                && flag_btn_enable_workTime && flag_btn_enable_restTime && flag_btn_enable_sets && flag_btn_enable_switch_timer_settings)
            startFab.show();
        else if (flag_btn_enable_checkboxes && flag_btn_enable_delayMin && flag_btn_enable_delayMax && flag_btn_enable_showTime
                && !flag_btn_enable_switch_timer_settings)
            startFab.show();
        else
            startFab.hide();
    }

    private void update_tmp_var () {

        if (delayMin.getText().toString().isEmpty())
            tmp_delayMin = 0;
        else
            tmp_delayMin = Integer.parseInt(delayMin.getText().toString());

        if (delayMax.getText().toString().isEmpty())
            tmp_delayMax = 0;
        else
            tmp_delayMax = Integer.parseInt(delayMax.getText().toString());

        if (showTime.getText().toString().isEmpty())
            tmp_showTime = 0;
        else
            tmp_showTime = Integer.parseInt(showTime.getText().toString());

        if (workTime.getText().toString().isEmpty())
            tmp_workTime = 0;
        else
            tmp_workTime = Integer.parseInt(workTime.getText().toString());

        if (restTime.getText().toString().isEmpty())
            tmp_restTime = 0;
        else
            tmp_restTime = Integer.parseInt(restTime.getText().toString());

        if (sets.getText().toString().isEmpty())
            tmp_sets = 0;
        else
            tmp_sets = Integer.parseInt(sets.getText().toString());
    }

    private void update_main_activity_data (userConfig tmp) {
        color_0.setChecked(tmp.isColor_0());
        color_1.setChecked(tmp.isColor_1());
        color_2.setChecked(tmp.isColor_2());
        color_3.setChecked(tmp.isColor_3());
        color_4.setChecked(tmp.isColor_4());
        sound_0.setChecked(tmp.isSound_0());
        sound_1.setChecked(tmp.isSound_1());
        sound_2.setChecked(tmp.isSound_2());
        sound_3.setChecked(tmp.isSound_3());
        picture_0.setChecked(tmp.isPicture_0());
        picture_1.setChecked(tmp.isPicture_1());
        picture_2.setChecked(tmp.isPicture_2());
        picture_3.setChecked(tmp.isPicture_3());
        picture_4.setChecked(tmp.isPicture_4());
        delayMin.setText(String.valueOf(tmp.getDelayMin()) );
        delayMax.setText(String.valueOf(tmp.getDelayMax()));
        showTime.setText(String.valueOf(tmp.getShowTime()));
        workTime.setText(String.valueOf(tmp.getWorkTime()));
        restTime.setText(String.valueOf(tmp.getRestTime()));
        sets.setText(String.valueOf(tmp.getSets()));
        switch_timer_settings.setChecked(tmp.isSwitch_timer_settings());

        if (tmp.isSwitch_timer_settings()) {
            findViewById(R.id.txtView_title_timer_settings).setEnabled(true);
            findViewById(R.id.txtInputLayout_workTime).setEnabled(true);
            findViewById(R.id.txtInputLayout_restTime).setEnabled(true);
            findViewById(R.id.txtInputLayout_sets).setEnabled(true);
            workTime.setEnabled(true);
            restTime.setEnabled(true);
            sets.setEnabled(true);
        }
        else {
            findViewById(R.id.txtView_title_timer_settings).setEnabled(false);
            findViewById(R.id.txtInputLayout_workTime).setEnabled(false);
            findViewById(R.id.txtInputLayout_restTime).setEnabled(false);
            findViewById(R.id.txtInputLayout_sets).setEnabled(false);
            workTime.setEnabled(false);
            restTime.setEnabled(false);
            sets.setEnabled(false);

            workTime.setError(null);
            restTime.setError(null);
            sets.setError(null);
        }

        createArray();
        update_flag();
        enableButton();
        TextView countText = findViewById(R.id.txtView_input_count);
        countText.setText(String.valueOf(count_input_selected));

        update_tmp_var();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("color_0", color_0.isChecked());
        editor.putBoolean("color_1", color_1.isChecked());
        editor.putBoolean("color_2", color_2.isChecked());
        editor.putBoolean("color_3", color_3.isChecked());
        editor.putBoolean("color_4", color_4.isChecked());
        editor.putBoolean("sound_0", sound_0.isChecked());
        editor.putBoolean("sound_1", sound_1.isChecked());
        editor.putBoolean("sound_2", sound_2.isChecked());
        editor.putBoolean("sound_3", sound_3.isChecked());
        editor.putBoolean("picture_0", picture_0.isChecked());
        editor.putBoolean("picture_1", picture_1.isChecked());
        editor.putBoolean("picture_2", picture_2.isChecked());
        editor.putBoolean("picture_3", picture_3.isChecked());
        editor.putBoolean("picture_4", picture_4.isChecked());
        editor.putInt("delayMin", tmp_delayMin);
        editor.putInt("delayMax", tmp_delayMax);
        editor.putInt("showTime", tmp_showTime);
        editor.putInt("workTime", tmp_workTime);
        editor.putInt("restTime", tmp_restTime);
        editor.putInt("sets", tmp_sets);
        editor.putBoolean("switch_timer_settings", switch_timer_settings.isChecked());

        editor.apply();
    }

    private void readFileWriteList () {
        try {
            File file = new File(getApplicationContext().getFilesDir(), filename_config);
            if (!file.exists()) {
                file.createNewFile();
            }

            InputStream inputStream = getApplicationContext().openFileInput(filename_config);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();

                while (line != null) {
                    String[] tmp = line.split(";");
                    if (tmp[15].isEmpty())
                        tmp[15] = "0";
                    userConfig tmp_struct = new userConfig(tmp[0], Boolean.parseBoolean(tmp[1]), Boolean.parseBoolean(tmp[2]), Boolean.parseBoolean(tmp[3]), Boolean.parseBoolean(tmp[4]), Boolean.parseBoolean(tmp[5]),
                            Boolean.parseBoolean(tmp[6]), Boolean.parseBoolean(tmp[7]), Boolean.parseBoolean(tmp[8]), Boolean.parseBoolean(tmp[9]), Boolean.parseBoolean(tmp[10]),
                            Boolean.parseBoolean(tmp[11]), Boolean.parseBoolean(tmp[12]), Boolean.parseBoolean(tmp[13]), Boolean.parseBoolean(tmp[14]), Integer.parseInt(tmp[15]),
                            Integer.parseInt(tmp[16]), Integer.parseInt(tmp[17]), Integer.parseInt(tmp[18]), Integer.parseInt(tmp[19]), Integer.parseInt(tmp[20]), Boolean.parseBoolean(tmp[21]));
                    listConfig.add(tmp_struct);

                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }

        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    private void readListWriteFile () {

        Collections.sort(listConfig, new Comparator<userConfig>() {
            @Override
            public int compare(userConfig o1, userConfig o2) {
                return o1.getName_Config().compareTo(o2.getName_Config());
            }
        });

        try {
            File file = new File(getApplicationContext().getFilesDir(), filename_config);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream outputStream;
            outputStream = openFileOutput(filename_config, Context.MODE_PRIVATE);
            for (int i = 0; i < listConfig.size(); i++) {
                outputStream.write(listConfig.get(i).getAllParamConcat().getBytes());
                outputStream.write("\n".getBytes());
            }
            outputStream.close();
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not write file: " + e.toString());
        }
    }

    private void updateItemConfig () {
        Menu menu = navigationView.getMenu();

        menu.removeGroup(R.id.user_config);

        menu.add(R.id.user_config, R.id.default_config, 0, default_config.getName_Config()).setIcon(R.drawable.ic_directions_run_white_24dp);

        for (int i = 0; i < listConfig.size(); i++) {
            menu.add(R.id.user_config, Menu.FIRST, 0, listConfig.get(i).getName_Config()).setIcon(R.drawable.ic_directions_run_white_24dp);
        }
    }
}

