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

    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    int [] signalVal = {0, 0, 0}, timerVal = {0, 0, 0};

    boolean[] colorVal = {false, false, false, false, false};
    boolean[] soundVal = {false, false, false, false};
    boolean[] pictureVal = {false, false, false, false, false};
    final boolean[] signalActive = {false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    boolean flag_btn_enable_switch_timer_settings = false, flag = true;

    int sets_left = 1, sets_total = 0;

    long time_left_work;

    MediaPlayer sound_0;
    MediaPlayer sound_1;
    MediaPlayer sound_2;
    MediaPlayer sound_3;

    TextView txtView_timer, txtView_sets;

    ImageView imageview;

    CountDownTimer timer_work, timer_rest, timer_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_show_signal);

        setTitle(R.string.start);

        mVisible = true;
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
        txtView_sets = findViewById(R.id.txtView_sets);

        System.arraycopy(colorVal, 0, signalActive, 0, colorVal.length);
        System.arraycopy(soundVal, 0, signalActive, 5, soundVal.length);
        System.arraycopy(pictureVal, 0, signalActive, 9, pictureVal.length);

        timer_start = new CountDownTimer(6000, 500) {
            final TextView txtView = findViewById(R.id.fullscreen_content);

            public void onTick(long millisUntilFinished) {
                if ((millisUntilFinished / 1000) >= 1)
                    txtView.setText(String.valueOf(millisUntilFinished / 1000));
                else
                    txtView.setText(R.string.start);
            }

            public void onFinish() {
                txtView.setText("");
                txtView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                if (!flag_btn_enable_switch_timer_settings) {
                    signalRandom();
                }
                else if (timerVal[2] == 0) {
                    timerVal[0] *= 1000;
                    timerVal[1] *= 1000;
                    timerVal[2] = -1;
                    txtView_sets.setText(String.valueOf(sets_left));
                    timerWork();
                    signalRandom();
                }
                else {
                    timerVal[0] *= 1000;
                    timerVal[1] *= 1000;
                    sets_total = timerVal[2];
                    String tmp = sets_left + "/" + sets_total;
                    txtView_sets.setText(tmp);
                    timerWork();
                    signalRandom();
                }
            }
        }.start();
    }

    /**
     * Obiettivo: Scelta del segnale da visualizzare.
     */
    public void signalRandom() {
        int tmp;
        Random rnd = new Random();

        if (flag) {
            imageview.setImageResource(android.R.color.transparent);
            imageview.setBackgroundColor(Color.TRANSPARENT);
            imageview.setVisibility(View.INVISIBLE);

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
        }

        cleanDisplay();
    }

    /**
     * Obiettivo: Cancellare segnale dopo il tempo deciso dall'utente.
     */
    public void cleanDisplay() {
        Handler handlerClean = new Handler();

        handlerClean.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageview.setImageResource(android.R.color.white);
                imageview.setBackgroundColor(Color.WHITE);
                imageview.setVisibility(View.INVISIBLE);
                startDelay();
            }
        }, signalVal[2]);
    }

    /**
     * Obiettivo: Partenza ritardata del ciclo change/show/clean con il tempo deciso dall'utente.
     */
    public void startDelay() {
        int tmp;
        Random rnd = new Random();

        if (time_left_work == 0)
            time_left_work = 2147483647;

        Handler handler = new Handler();
        if (flag && !flag_btn_enable_switch_timer_settings)
            tmp = rnd.nextInt((signalVal[1] - signalVal[0] + 1)) + signalVal[0];
        else if (flag && (int)time_left_work > (signalVal[1] + signalVal[2]))
            tmp = rnd.nextInt((signalVal[1] - signalVal[0] + 1)) + signalVal[0];
        else if (flag && (int)time_left_work > signalVal[0])
            tmp = signalVal[0] - signalVal[2];
        else
            tmp = timerVal[1] + (int)time_left_work;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                signalRandom();
            }
        }, tmp);
    }

    /**
     * Obiettivo: Visualizzare segnale (colore) a schermo.
     * @param tmp numero segnale scelto
     */
    public void drawColor(int tmp) {

        imageview.setVisibility(View.VISIBLE);

        switch (tmp) {
            case 0:
                imageview.setBackgroundColor(Color.RED);
                break;
            case 1:
                imageview.setBackgroundColor(Color.BLUE);
                break;
            case 2:
                imageview.setBackgroundColor(Color.GREEN);
                break;
            case 3:
                imageview.setBackgroundColor(Color.YELLOW);
                break;
            case 4:
                imageview.setBackgroundColor(Color.GRAY);
                break;
            default: break;
        }
    }

    /**
     * Obiettivo: Visualizzare segnale (immagine) a schermo.
     * @param tmp numero segnale scelto
     */
    public void drawPicture(int tmp) {

        imageview.setVisibility(View.VISIBLE);

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

    /**
     * Obiettivo: Visualizzare segnale (suono) a schermo.
     * @param tmp numero segnale scelto
     */
    public void playSound(int tmp) {

        switch (tmp-5) {
            case 0:
                if (sound_0 != null)
                    sound_0.start();
                break;
            case 1:
                if (sound_1 != null)
                    sound_1.start();
                break;
            case 2:
                if (sound_2 != null)
                    sound_2.start();
                break;
            case 3:
                if (sound_3 != null)
                    sound_3.start();
                break;
            default: break;
        }
    }

    /**
     * Obiettivo: Timer del tempo di work, al termine fa iniziare il tempo di rest o termina l'activity
     */
    public void timerWork() {

        timer_work = new CountDownTimer(timerVal[0], 500) {

            public void onTick(long millisUntilFinished) {
                txtView_timer.setText(String.valueOf(millisUntilFinished / 1000));
                time_left_work = millisUntilFinished;
            }

            public void onFinish() {
                timerVal[2]--;
                sets_left++;
                txtView_timer.setText("");
                txtView_sets.setText("");

                imageview.setImageResource(android.R.color.white);
                imageview.setBackgroundColor(Color.WHITE);
                imageview.setVisibility(View.INVISIBLE);

                if (sound_0 != null) sound_0.stop();
                if (sound_1 != null) sound_1.stop();
                if (sound_2 != null) sound_2.stop();
                if (sound_3 != null) sound_3.stop();

                if (timerVal[2] == 0)
                    exit();
                else if (timerVal[1] == 0) {
                    timerWork();
                }
                else {
                    flag = false;
                    timerRest();
                }
            }
        }.start();
    }

    /**
     * Obiettivo: Timer del tempo di rest, al termine fa iniziare il tempo di work
     */
    public void timerRest() {

        mContentView.setBackgroundColor(Color.WHITE);

        timer_rest = new CountDownTimer(timerVal[1], 500) {
            final TextView txtView = findViewById(R.id.fullscreen_content);
            public void onTick(long millisUntilFinished) {
                if ((millisUntilFinished / 1000) >= 1)
                    txtView.setText(String.valueOf(millisUntilFinished / 1000));
                else
                    txtView.setText(R.string.start);
            }

            public void onFinish() {
                imageview.setImageResource(android.R.color.transparent);
                imageview.setBackgroundColor(Color.TRANSPARENT);
                flag = true;
                txtView.setText("");
                txtView.setBackgroundColor(Color.TRANSPARENT);

                imageview.setVisibility(View.VISIBLE)
                ;
                if (sets_total == 0){
                    txtView_sets.setText(String.valueOf(sets_left));
                }
                else {
                    String tmp = sets_left + "/" + sets_total;
                    txtView_sets.setText(tmp);
                }

                timerWork();
            }
        }.start();
    }

    /**
     * Obiettivo: Terminare l'activity inviando alla MainActivity lo stato di ritorno
     */
    public void exit() {
        flag = false;

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

        if (timer_work != null)
            timer_work.cancel();
        if (timer_rest != null)
            timer_rest.cancel();
        if (timer_start != null)
            timer_start.cancel();

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exit();
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
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        mHideHandler.removeCallbacks(mHidePart2Runnable);

        delayedHide(3000);
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
