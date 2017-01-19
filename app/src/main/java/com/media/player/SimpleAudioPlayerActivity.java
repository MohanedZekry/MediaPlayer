package com.media.player;


import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class SimpleAudioPlayerActivity extends Activity {

    private Button btnForward, btnPause, btnPlay, btnBackward;
    private MediaPlayer mediaPlayer;
    private long currentTime = 0;
    private long totalTime = 0;
    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView tvCurrentDuration, tvTotalDuration, tvSongName;
    boolean fromPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_audio_player);

        if (MediaPickerActivity.fileUri != null) {
            fromPicker = true;
        }

        btnForward = (Button) findViewById(R.id.button);
        btnPause = (Button) findViewById(R.id.button2);
        btnPlay = (Button) findViewById(R.id.button3);
        btnBackward = (Button) findViewById(R.id.button4);

        tvCurrentDuration = (TextView) findViewById(R.id.textView2);
        tvTotalDuration = (TextView) findViewById(R.id.textView3);
        tvSongName = (TextView) findViewById(R.id.textview);
        tvSongName.setText("Song.mp3");

        seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setClickable(false);
        btnPause.setEnabled(false);

        Log.d("mp",fromPicker+"");
        if (fromPicker == false)
            mediaPlayer = MediaPlayer.create(this, R.raw.song);
        else {
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(this, MediaPickerActivity.fileUri);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Playing sound", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();

                totalTime = mediaPlayer.getDuration();
                currentTime = mediaPlayer.getCurrentPosition();

                seekbar.setMax((int) totalTime);

                tvTotalDuration.setText(milliSecondsToTimer(totalTime));

                tvCurrentDuration.setText(milliSecondsToTimer(currentTime));

                seekbar.setProgress((int) currentTime);
                btnPause.setEnabled(true);
                btnPlay.setEnabled(false);
                //to send a runnable after 1 second
                myHandler.postDelayed(UpdateSongTime, 1000);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Pausing sound", Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
                btnPause.setEnabled(false);
                btnPlay.setEnabled(true);
            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) currentTime;

                if ((temp + forwardTime) <= totalTime) {
                    currentTime = currentTime + forwardTime;
                    mediaPlayer.seekTo((int) currentTime);
                    //Toast.makeText(getApplicationContext(), "You have Jumped forward 5 seconds", Toast.LENGTH_SHORT).show();
                } else {
                    mediaPlayer.seekTo((int)totalTime);
                    //Toast.makeText(getApplicationContext(), "Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) currentTime;

                if ((temp - backwardTime) > 0) {
                    currentTime = currentTime - backwardTime;
                    mediaPlayer.seekTo((int) currentTime);
                    //Toast.makeText(getApplicationContext(), "You have Jumped backward 5 seconds", Toast.LENGTH_SHORT).show();
                } else {
                    mediaPlayer.seekTo(0);
                    //Toast.makeText(getApplicationContext(), "Cannot jump backward 5 seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            currentTime = mediaPlayer.getCurrentPosition();
            tvCurrentDuration.setText(milliSecondsToTimer(currentTime));

            seekbar.setProgress((int) currentTime);

            //to send another runnable after 1 second
            myHandler.postDelayed(this, 1000);
        }
    };


    /**
     * Function to convert milliseconds time to
     * Timer Format
     * Hours:Minutes:Seconds
     */
    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying())
            mediaPlayer.stop();

    }
}
