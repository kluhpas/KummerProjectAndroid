package com.kluhpas.kummerproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
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

    int [] data;
    boolean flag;

    MediaPlayer sound_0;
    MediaPlayer sound_1;
    MediaPlayer sound_2;
    MediaPlayer sound_3;

    View viewBackground;

    ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        super.onCreate(savedInstanceState);

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

        data = getIntent().getIntArrayExtra("dati");

        sound_0 = MediaPlayer.create(ShowSignal.this, R.raw.single_short_beep);
        sound_1 = MediaPlayer.create(ShowSignal.this, R.raw.single_long_beep);
        sound_2 = MediaPlayer.create(ShowSignal.this, R.raw.single_long_boop);
        sound_3 = MediaPlayer.create(ShowSignal.this, R.raw.double_short_beep);

        viewBackground = mContentView;
        imageview = findViewById(R.id.imageView);

    }

    public void signalRandom() {
        int tmp;
        Random rnd = new Random();
        do {
            tmp = rnd.nextInt(3);
        } while (data[tmp] == 0);
        switch (tmp) {
            case 0:
                drawColor();
                break;
            case 1:
                playSound();
                break;
            case 2:
                drawPicture();
                break;
        }

        if (flag) {
            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewBackground.setBackgroundColor(Color.WHITE);
                    imageview.setImageResource(android.R.color.transparent);
                }
            }, data[5]);

            tmp = rnd.nextInt((data[4] - data[3] + 1)) + data[3];
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    signalRandom();
                }
            }, tmp);
        }
    }

    public void drawColor() {
        int tmp;
        Random rnd = new Random();
        tmp = rnd.nextInt(data[0]);
        switch (tmp) {
            case 0:
                viewBackground.setBackgroundColor(Color.RED);
                break;
            case 1:
                viewBackground.setBackgroundColor(Color.BLUE);
                break;
            case 2:
                viewBackground.setBackgroundColor(Color.YELLOW);
                break;
            case 3:
                viewBackground.setBackgroundColor(Color.rgb(255, 105, 180));
                break;
            case 4:
                viewBackground.setBackgroundColor(Color.rgb(195,195,195));
                break;
        }
    }

    public void drawPicture() {
        int tmp;
        Random rnd = new Random();
        tmp = rnd.nextInt(data[2]);
        switch (tmp) {
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
        }
    }

    public void playSound() {
        int tmp;
        Random rnd = new Random();
        tmp = rnd.nextInt(data[1]);
        switch (tmp) {
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
        }
    }

    /** Called when the user taps the btnStart */
    public void exit(View view) {
        flag = false;
        Intent intent = new Intent(ShowSignal.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sound_0.isPlaying()) sound_0.stop();
        if (sound_1.isPlaying()) sound_1.stop();
        if (sound_2.isPlaying()) sound_2.stop();
        if (sound_3.isPlaying()) sound_3.stop();
        flag = false;
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

        new CountDownTimer(6000, 1000) {
            TextView txtView = findViewById(R.id.fullscreen_content);

            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished != 6000)
                    txtView.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                txtView.setText("");
                signalRandom();
            }
        }.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

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
