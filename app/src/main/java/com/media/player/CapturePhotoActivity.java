package com.media.player;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class CapturePhotoActivity extends AppCompatActivity {

    Button btnSelfie;
    ImageView ivViewer;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_photo);

        ivViewer = (ImageView) findViewById(R.id.imageView);
        btnSelfie = (Button) findViewById(R.id.btn_selfie);
        btnSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        if (MediaPickerActivity.fileUri != null) {

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), MediaPickerActivity.fileUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ivViewer.setImageBitmap(bitmap);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        ivViewer.setImageBitmap(bitmap);

    }

}
