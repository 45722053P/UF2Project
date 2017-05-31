package com.proyecto.proyectouf2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class photoActivity extends AppCompatActivity {

    String pathPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private File creararchivo() throws IOException {

        String fecha_foto = new SimpleDateFormat("mmhh_ddmmyyyy").format(new Date());
        String imagennombre = "picture_" + fecha_foto;
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(
                imagennombre,   // Prefijo de la imagen
                ".jpg",         // Sufijo de la imagen.
                storageDir      //Lugar donde va a ser guardada.
        );


        pathPhoto = "file:" + imagen.getAbsolutePath();
        return imagen;
    }


    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePhoto() {

        Intent takeFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takeFoto.resolveActivity(getPackageManager())!= null){
            File fotofile = null;
            try {

                fotofile = creararchivo();

            } catch (IOException ex) {

            }

            if (fotofile != null) {

                takeFoto.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(fotofile));
                startActivityForResult(takeFoto, REQUEST_TAKE_PHOTO);

            }
        }
    }
}


