package com.media.player;

import  android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MediaPickerActivity extends AppCompatActivity implements OnClickListener {

    Button btnAudio, btnVideo, btnImage;
    public static Uri fileUri;
    int mediaType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        btnAudio = (Button) findViewById(R.id.btn_audio);
        btnVideo = (Button) findViewById(R.id.btn_video);
        btnImage = (Button) findViewById(R.id.btn_image);


        btnAudio.setOnClickListener(this);
        btnVideo.setOnClickListener(this);
        btnImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);

        if (view.getId() == btnAudio.getId()) {
            intent.setType("audio/*");
            mediaType = 1;

        } else if (view.getId() == btnImage.getId()) {
            intent.setType("image/*");
            mediaType = 2;

        } else {
            intent.setType("video/*");
            mediaType = 3;

        }

        startActivityForResult(Intent.createChooser(intent, "Choose file"), 1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Intent i = null;
        if (resultCode == RESULT_CANCELED) {
            // action cancelled
        }
        if (resultCode == RESULT_OK) {
            fileUri = data.getData();

            if (mediaType == 1) {
                i = new Intent(getApplicationContext(), SimpleAudioPlayerActivity.class);
                startActivity(i);

            } else if (mediaType == 2) {
                i = new Intent(getApplicationContext(), CapturePhotoActivity.class);

            } else
                i = new Intent(getApplicationContext(), VideoPlayerActivity.class);

                startActivity(i);


        }
    }


}
