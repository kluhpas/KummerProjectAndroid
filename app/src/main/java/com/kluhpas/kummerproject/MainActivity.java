package com.kluhpas.kummerproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean[] colorVal = {false, false, false, false, false};
    boolean[] soundVal = {false, false, false, false};
    boolean[] pictureVal = {false, false, false, false, false};

    CheckBox color_0, color_1, color_2, color_3, color_4;
    CheckBox sound_0, sound_1, sound_2, sound_3;
    CheckBox picture_0, picture_1, picture_2, picture_3, picture_4;
    EditText delayMin, delayMax, showTime, workTime, restTime, sets;
    Switch switch_timer_settings;
    FloatingActionButton startFab;

    int count_input_selected = 0, tmp_delayMin = 0, tmp_delayMax = 0, tmp_showTime = 0, tmp_workTime = 0, tmp_restTime = 0, tmp_sets = 0;
    boolean flag_btn_enable_checkboxes = false, flag_btn_enable_delayMin = false, flag_btn_enable_delayMax = false, flag_btn_enable_showTime = false,
            flag_btn_enable_workTime = false, flag_btn_enable_restTime = false, flag_btn_enable_sets = false, flag_btn_enable_switch_timer_settings = false;

    SharedPreferences sharedPref;

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

        BottomAppBar bar = (BottomAppBar) findViewById(R.id.bottomAppBar);
        setSupportActionBar(bar);

        startFab = findViewById(R.id.fab);

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

            if (count_input_selected > 0) {
                flag_btn_enable_checkboxes = true;
                TextView countText = findViewById(R.id.txtView_input_count);
                countText.setText(String.valueOf(count_input_selected));
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
        }
        else {
            switch_timer_settings.setChecked(false);
            click_switch_timer_settings(this.switch_timer_settings);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.commit();
            flag_btn_enable_delayMin = true;
            flag_btn_enable_delayMax = true;
            flag_btn_enable_showTime = true;
            flag_btn_enable_workTime = true;
            flag_btn_enable_restTime = true;
            flag_btn_enable_sets = true;
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

                    //startButton.setEnabled(enableButton());
                    //startFab.setVisibility(enableButton());
                    enableButton();
                }
                else if (tmp_delayMin > tmp_delayMax){
                    CharSequence text = getResources().getString(R.string.txtView_delay_min) + getResources().getString(R.string.error_lower) + getResources().getString(R.string.txtView_delay_max);
                    delayMin.setError(text);

                    flag_btn_enable_delayMin = false;

                    //startButton.setEnabled(enableButton());
                    //startFab.setVisibility(enableButton());
                    enableButton();
                }
                else {
                    CharSequence text = getResources().getString(R.string.txtView_delay_min) + getResources().getString(R.string.error_lower) + getResources().getString(R.string.txtView_time_show);
                    delayMin.setError(text);

                    flag_btn_enable_delayMin = false;

                    //startButton.setEnabled(enableButton());
                    //startFab.setVisibility(enableButton());
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

                    //startButton.setEnabled(enableButton());
                    //startFab.setVisibility(enableButton());
                    enableButton();
                }
                else {
                    CharSequence text = getResources().getString(R.string.txtView_delay_max) + getResources().getString(R.string.error_greater) + getResources().getString(R.string.txtView_delay_min);
                    delayMax.setError(text);

                    flag_btn_enable_delayMax = false;

                    //startButton.setEnabled(enableButton());
                    //startFab.setVisibility(enableButton());
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

                    //startButton.setEnabled(enableButton());
                    //startFab.setVisibility(enableButton());
                    enableButton();
                }
                else if (tmp_showTime > tmp_delayMin){
                    CharSequence text = getResources().getString(R.string.txtView_time_show) + getResources().getString(R.string.error_lower) + getResources().getString(R.string.txtView_delay_min);
                    showTime.setError(text);

                    flag_btn_enable_showTime = false;

                    //startButton.setEnabled(enableButton());
                    //startFab.setVisibility(enableButton());
                    enableButton();
                }
                else {
                    CharSequence text = getResources().getString(R.string.txtView_time_show) + getResources().getString(R.string.error_zero_showTime);
                    showTime.setError(text);

                    flag_btn_enable_showTime = false;

                    //startButton.setEnabled(enableButton());
                    //startFab.setVisibility(enableButton());
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

                    //startButton.setEnabled(enableButton());
                    //startFab.setVisibility(enableButton());
                    enableButton();
                }
                else {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("workTime", Integer.parseInt(workTime.getText().toString()));
                    editor.apply();

                    workTime.setError(null);
                    flag_btn_enable_workTime = true;

                    //startButton.setEnabled(enableButton());
                    //startFab.setVisibility(enableButton());
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

                    //startButton.setEnabled(enableButton());
                    //startFab.setVisibility(enableButton());
                    enableButton();
                }
                else {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("restTime", Integer.parseInt(restTime.getText().toString()));
                    editor.apply();

                    restTime.setError(null);
                    flag_btn_enable_restTime = true;

                    //startButton.setEnabled(enableButton());
                    //startFab.setVisibility(enableButton());
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

                    //startButton.setEnabled(enableButton());
                    //startFab.setVisibility(enableButton());
                    enableButton();
                }
                else {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("sets", Integer.parseInt(sets.getText().toString()));
                    editor.apply();

                    sets.setError(null);
                    flag_btn_enable_sets = true;

                    //startButton.setEnabled(enableButton());
                    //startFab.setVisibility(enableButton());
                    enableButton();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

    }

    private MenuItem mSpinnerItem1 = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_bar_item, menu);
/*
        mSpinnerItem1 = menu.findItem(R.id.spnr_profile);
        View view1 = mSpinnerItem1.getActionView();

        if (view1 instanceof Spinner) {
            final Spinner spinner = (Spinner) view1;
            List<String> list = new ArrayList<String>();
            list.add("list 1");
            list.add("list 2");
            list.add("list 3");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btn_info) {
            Intent intent = new Intent(MainActivity.this, Info.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Called when the user taps a checkBox */
    public void updateCounter(View view) {

        TextView countText = findViewById(R.id.txtView_input_count);
        count_input_selected = Integer.parseInt(countText.getText().toString());

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

        if (count_input_selected > 0) flag_btn_enable_checkboxes = true;
        else flag_btn_enable_checkboxes = false;


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
        //startButton.setEnabled(enableButton());
        ////startFab.setVisibility(enableButton());
        enableButton();
        enableButton();
    }

    public void createArray () {

        count_input_selected = 0;

        if (color_0.isChecked()) { colorVal[0] = true; count_input_selected++; }
        if (color_1.isChecked()) { colorVal[1] = true; count_input_selected++; }
        if (color_2.isChecked()) { colorVal[2] = true; count_input_selected++; }
        if (color_3.isChecked()) { colorVal[3] = true; count_input_selected++; }
        if (color_4.isChecked()) { colorVal[4] = true; count_input_selected++; }
        if (sound_0.isChecked()) { soundVal[0] = true; count_input_selected++; }
        if (sound_1.isChecked()) { soundVal[1] = true; count_input_selected++; }
        if (sound_2.isChecked()) { soundVal[2] = true; count_input_selected++; }
        if (sound_3.isChecked()) { soundVal[3] = true; count_input_selected++; }
        if (picture_0.isChecked()) { pictureVal[0] = true; count_input_selected++; }
        if (picture_1.isChecked()) { pictureVal[1] = true; count_input_selected++; }
        if (picture_2.isChecked()) { pictureVal[2] = true; count_input_selected++; }
        if (picture_3.isChecked()) { pictureVal[3] = true; count_input_selected++; }
        if (picture_4.isChecked()) { pictureVal[4] = true; count_input_selected++; }
    }

    public void enableButton () {
        if (flag_btn_enable_checkboxes && flag_btn_enable_delayMin && flag_btn_enable_delayMax && flag_btn_enable_showTime
                && flag_btn_enable_workTime && flag_btn_enable_restTime && flag_btn_enable_sets && flag_btn_enable_switch_timer_settings)
            startFab.show();
        else if (flag_btn_enable_checkboxes && flag_btn_enable_delayMin && flag_btn_enable_delayMax && flag_btn_enable_showTime
                && !flag_btn_enable_switch_timer_settings)
            startFab.show();
        else
            startFab.hide();
    }

    public void update_tmp_var () {

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
}