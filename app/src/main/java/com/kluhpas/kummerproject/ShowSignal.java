package com.kluhpas.kummerproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ShowSignal extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    int [] signalVal = {0, 0, 0}, timerVal = {0, 0, 0};
    boolean[] colorVal = {false, false, false, false, false};
    boolean[] soundVal = {false, false, false, false};
    boolean[] pictureVal = {false, false, false, false, false};
    boolean[] signalActive = {false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    boolean flag_btn_enable_switch_timer_settings = false, flag = false, flag_isDestroy = false;

    MediaPlayer sound_0;
    MediaPlayer sound_1;
    MediaPlayer sound_2;
    MediaPlayer sound_3;

    TextView txtView_timer;

    ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_show_signal);

        setTitle("Start");

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        flag_btn_enable_switch_timer_settings = getIntent().getBooleanExtra("timer_isEnable", false);
        signalVal = getIntent().getIntArrayExtra("signalVal");
        timerVal = getIntent().getIntArrayExtra("timerVal");
        colorVal = getIntent().getBooleanArrayExtra("colorVal");
        soundVal = getIntent().getBooleanArrayExtra("soundVal");
        pictureVal = getIntent().getBooleanArrayExtra("pictureVal");

        sound_0 = MediaPlayer.create(ShowSignal.this, R.raw.single_short_beep);
        sound_1 = MediaPlayer.create(ShowSignal.this, R.raw.single_long_beep);
        sound_2 = MediaPlayer.create(ShowSignal.this, R.raw.double_short_beep);
        sound_3 = MediaPlayer.create(ShowSignal.this, R.raw.single_long_boop);

        imageview = findViewById(R.id.imageView);
        txtView_timer = findViewById(R.id.txtView_timer);

        for (int i = 0; i < colorVal.length; i++) {
            if (colorVal[i])
                signalActive[i] = true;
            else
                signalActive[i] = false;
        }

        for (int i = 0; i < soundVal.length; i++) {
            if (soundVal[i])
                signalActive[i+5] = true;
            else
                signalActive[i+5] = false;
        }

        for (int i = 0; i < pictureVal.length; i++) {
            if (pictureVal[i])
                signalActive[i+9] = true;
            else
                signalActive[i+9] = false;
        }
    }

    public void signalRandom() {
        if (!flag_isDestroy) {
            if (flag) {
                int tmp;
                Random rnd = new Random();
                do {
                    tmp = rnd.nextInt(signalActive.length);
                } while (!signalActive[tmp]);

                switch (tmp) {
                    case 0:
                        drawColor(tmp);
                        break;
                    case 1:
                        drawColor(tmp);
                        break;
                    case 2:
                        drawColor(tmp);
                        break;
                    case 3:
                        drawColor(tmp);
                        break;
                    case 4:
                        drawColor(tmp);
                        break;
                    case 5:
                        playSound(tmp);
                        break;
                    case 6:
                        playSound(tmp);
                        break;
                    case 7:
                        playSound(tmp);
                        break;
                    case 8:
                        playSound(tmp);
                        break;
                    case 9:
                        drawPicture(tmp);
                        break;
                    case 10:
                        drawPicture(tmp);
                        break;
                    case 11:
                        drawPicture(tmp);
                        break;
                    case 12:
                        drawPicture(tmp);
                        break;
                    case 13:
                        drawPicture(tmp);
                        break;
                    default:
                        break;
                }

                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageview.setImageResource(android.R.color.transparent);
                        imageview.setBackgroundColor(Color.WHITE);
                        txtView_timer.setBackgroundColor(Color.WHITE);
                    }
                }, signalVal[2]);

                tmp = rnd.nextInt((signalVal[1] - signalVal[0] + 1)) + signalVal[0];
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        signalRandom();
                    }
                }, tmp);
            }
        }
    }

    public void drawColor(int tmp) {
        switch (tmp) {
            case 0:
                imageview.setBackgroundColor(Color.RED);
                txtView_timer.setBackgroundColor(Color.RED);
                break;
            case 1:
                imageview.setBackgroundColor(Color.BLUE);
                txtView_timer.setBackgroundColor(Color.BLUE);
                break;
            case 2:
                imageview.setBackgroundColor(Color.GREEN);
                txtView_timer.setBackgroundColor(Color.GREEN);
                break;
            case 3:
                imageview.setBackgroundColor(Color.YELLOW);
                txtView_timer.setBackgroundColor(Color.YELLOW);
                break;
            case 4:
                imageview.setBackgroundColor(Color.GRAY);
                txtView_timer.setBackgroundColor(Color.GRAY);
                break;
            default: break;
        }
    }

    public void drawPicture(int tmp) {
        switch (tmp-9) {
            case 0:
                imageview.setImageResource(R.drawable.circle);
                break;
            case 1:
                imageview.setImageResource(R.drawable.square);
                break;
            case 2:
                imageview.setImageResource(R.drawable.triangle);
                break;
            case 3:
                imageview.setImageResource(R.drawable.star);
                break;
            case 4:
                imageview.setImageResource(R.drawable.snow);
                break;
            default: break;
        }
    }

    public void playSound(int tmp) {
        switch (tmp-5) {
            case 0:
                sound_0.start();
                break;
            case 1:
                sound_1.start();
                break;
            case 2:
                sound_2.start();
                break;
            case 3:
                sound_3.start();
                break;
            default: break;
        }
    }

    public void timerWork() {

        new CountDownTimer(timerVal[0], 500) {

            public void onTick(long millisUntilFinished) {
                txtView_timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                timerVal[2]--;
                if (timerVal[2] == 0)
                    exit(mControlsView);
                else if (timerVal[1] == 0) {
                    timerWork();
                }
                else {
                    if (!flag_isDestroy) {
                        flag = false;
                        timerRest();
                    }
                }
            }
        }.start();
    }

    public void timerRest() {

        mContentView.setBackgroundColor(getResources().getColor(android.R.color.white));

        new CountDownTimer(timerVal[1], 500) {
            TextView txtView = findViewById(R.id.fullscreen_content);
            public void onTick(long millisUntilFinished) {
                txtView_timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                if (!flag_isDestroy) {
                    imageview.setImageResource(android.R.color.transparent);
                    imageview.setBackgroundColor(Color.WHITE);
                    txtView_timer.setBackgroundColor(Color.WHITE);
                    flag = true;
                    signalRandom();
                    timerWork();
                    txtView.setText("");
                    txtView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
            }
        }.start();
    }

    /** Called when the user taps the btnStart */
    public void exit(View view) {
        flag = false;
        flag_isDestroy = true;

        Intent intent = new Intent(ShowSignal.this, MainActivity.class);
        intent.putExtra("firstAccess", false);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onPause() {

        sound_0.stop();
        sound_1.stop();
        sound_2.stop();
        sound_3.stop();

        flag = false;

        super.onPause();
    }

    @Override
    protected void onDestroy() {

        sound_0.release();
        sound_0 = null;
        sound_1.release();
        sound_1 = null;
        sound_2.release();
        sound_2 = null;
        sound_3.release();
        sound_3 = null;

        flag = false;

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exit(mControlsView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        flag = true;
        flag_isDestroy = false;

       new CountDownTimer(6000, 500) {
            TextView txtView = findViewById(R.id.fullscreen_content);

            public void onTick(long millisUntilFinished) {
                if ((millisUntilFinished / 1000) >= 1)
                    txtView.setText(String.valueOf(millisUntilFinished / 1000));
                else
                    txtView.setText("START");
            }

            public void onFinish() {
                txtView.setText("");
                txtView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                if (!flag_btn_enable_switch_timer_settings)
                    signalRandom();
                else if (timerVal[2] == 0) {
                    timerVal[0] *= 1000;
                    timerVal[1] *= 1000;
                    timerVal[2] = -1;
                    timerWork();
                    signalRandom();
                }
                else {
                    timerVal[0] *= 1000;
                    timerVal[1] *= 1000;
                    timerWork();
                    signalRandom();
                }
            }
        }.start();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
