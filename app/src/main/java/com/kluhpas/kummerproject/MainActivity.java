package com.kluhpas.kummerproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.triggertrap.seekarc.SeekArc;
import com.triggertrap.seekarc.SeekArc.OnSeekArcChangeListener;

public class MainActivity extends AppCompatActivity {

    SeekArc color;
    TextView colorProgress;
    SeekArc sound;
    TextView soundProgress;
    SeekArc picture;
    TextView pictureProgress;
    SeekArc delayMin;
    EditText delayMinProgress;
    EditText delayMax;
    EditText showTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        color = (SeekArc) findViewById(R.id.skArcColor);
        colorProgress = (TextView) findViewById(R.id.txtViewColorValue);
        sound = (SeekArc) findViewById(R.id.skArcSound);
        soundProgress = (TextView) findViewById(R.id.txtViewSoundValue);
        picture = (SeekArc) findViewById(R.id.skArcPicture);
        pictureProgress = (TextView) findViewById(R.id.txtViewPictureValue);
        delayMin = (SeekArc) findViewById(R.id.skArcPicture);
        delayMinProgress = (EditText) findViewById(R.id.nptNumberDelayMin);
        delayMax = (EditText) findViewById(R.id.nptNumberDelayMax);
        showTime = (EditText) findViewById(R.id.nptNumberShowTime);

        color.setOnSeekArcChangeListener(new OnSeekArcChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {
            }

            @Override
            public void onProgressChanged(SeekArc seekArc, int progress,
                                          boolean fromUser) {
                colorProgress.setText(String.valueOf(progress));
            }
        });

        sound.setOnSeekArcChangeListener(new OnSeekArcChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {
            }

            @Override
            public void onProgressChanged(SeekArc seekArc, int progress,
                                          boolean fromUser) {
                soundProgress.setText(String.valueOf(progress));
            }
        });

        picture.setOnSeekArcChangeListener(new OnSeekArcChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {
            }

            @Override
            public void onProgressChanged(SeekArc seekArc, int progress,
                                          boolean fromUser) {
                pictureProgress.setText(String.valueOf(progress));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.info_item, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btnInfo) {
            Intent intent = new Intent(MainActivity.this, Info.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the user taps the btnStart */
    public void start(View view) {

        if (color.getProgress() == 0 && sound.getProgress() == 0 && picture.getProgress() == 0) {
            // 1. Instantiate an AlertDialog.Builder with its constructor
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(R.string.dialog_message_signal).setTitle(R.string.dialog_title);

            builder.setNeutralButton(R.string.dialog_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (TextUtils.isEmpty(delayMinProgress.getText().toString()) || TextUtils.isEmpty(delayMax.getText().toString()) || TextUtils.isEmpty(showTime.getText().toString())){
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setMessage(R.string.dialog_message_number).setTitle(R.string.dialog_title);

            builder.setNeutralButton(R.string.dialog_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (Integer.parseInt(delayMinProgress.getText().toString()) > Integer.parseInt(delayMax.getText().toString())){
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setMessage(R.string.dialog_message_number_min).setTitle(R.string.dialog_title);

            builder.setNeutralButton(R.string.dialog_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            int[] data = {color.getProgress(),
                    sound.getProgress(),
                    picture.getProgress(),
                    Integer.parseInt(delayMinProgress.getText().toString()),
                    Integer.parseInt(delayMax.getText().toString()),
                    Integer.parseInt(showTime.getText().toString()),
            };

            Intent intent = new Intent(MainActivity.this, ShowSignal.class);
            intent.putExtra("dati", data);
            finish();
            startActivity(intent);
        }
    }
}
