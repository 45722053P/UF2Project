package com.proyecto.proyectouf2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class videoActivity extends AppCompatActivity {

    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            //create new Intent
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

           // fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);  // create a file to save the video
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name

            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high

            // start the Video Capture Intent
            startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
        }
}