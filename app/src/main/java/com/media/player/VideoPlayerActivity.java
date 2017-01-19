package com.media.player;


import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity {
    private VideoView myVideoView;
    private int position = 0;

    private ProgressDialog progressDialog;
    private MediaController mediaControls;
    boolean fromPicker;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        if (MediaPickerActivity.fileUri != null) {
            fromPicker = true;
        }

        if (mediaControls == null) {
            mediaControls = new MediaController(VideoPlayerActivity.this);
        }

        //initialize the VideoView
        myVideoView = (VideoView) findViewById(R.id.video_view);
        // create a progress bar while the video file is loading
        progressDialog = new ProgressDialog(VideoPlayerActivity.this);

        // set a title for the progress bar

        progressDialog.setTitle("Loading video");

        progressDialog.setMessage("Loading...");

        progressDialog.setCancelable(false);

        progressDialog.show();

        try {

            //set the media controller in the VideoView
            myVideoView.setMediaController(mediaControls);

            //set the uri of the video to be played
            if (fromPicker == false)
                myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));

            else
                myVideoView.setVideoURI(MediaPickerActivity.fileUri);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        myVideoView.requestFocus();
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // close the progress bar and play the video
                progressDialog.dismiss();
                //if we have a position on savedInstanceState, the video playback should start from here
                myVideoView.seekTo(position);
                if (position == 0) {
                    myVideoView.start();
                } else {
                    //if we come from a resumed activity, video playback will be paused
                    myVideoView.start();

                }

            }

        });


    }


    @Override

    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        //we use onSaveInstanceState in order to store the video playback position for orientation change

        savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());

       // myVideoView.pause();

    }


    @Override

    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        //we use onRestoreInstanceState in order to play the video playback from the stored position

        position = savedInstanceState.getInt("Position");

        myVideoView.seekTo(position);

    }

}
