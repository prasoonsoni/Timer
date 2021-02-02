package com.prasoonsoni.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView timerClock;
    Boolean counterIsActive = false;
    Button controllerButton;
    CountDownTimer countDownTimer;

    public void resetTimer() {
        timerClock.setText("0:30");
        seekBar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("Go!");
        seekBar.setEnabled(true);
        counterIsActive = false;
    }

    public void updateTimer(int secondsLeft) {
        int minutes = (int) secondsLeft / 60;
        int seconds = (int) secondsLeft - minutes * 60;
        String secondString = Integer.toString(seconds);
        if (seconds <= 9) {
            secondString = "0" + secondString;
        }
        timerClock.setText(Integer.toString(minutes) + ":" + secondString);
    }

    public void controlTimer(View view) {
        if (counterIsActive == false) {
            counterIsActive = true;
            seekBar.setEnabled(false);
            controllerButton.setText("Stop");
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    timerClock.setText("0:00");
                    MediaPlayer mediaPlayer;
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.soundeffect);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        } else {
            resetTimer();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerClock = findViewById(R.id.timerClock);
        seekBar = findViewById(R.id.seekBar);
        controllerButton = findViewById(R.id.controllerButton);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}