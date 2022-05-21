package com.example.dailycame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class SmallPicture extends AppCompatActivity {
    private ImageView out;
    private ImageView picture;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smallpicture);
        init();
    }
    private void loadPicture(){
        Intent intent=getIntent();
        String path=intent.getStringExtra("path");
        System.out.println(path);
        Bitmap bitmap= BitmapFactory.decodeFile(getFilesDir().getAbsoluteFile()+"/"+path);
        picture.setImageBitmap(bitmap);

    }
    private void init(){
        out=findViewById(R.id.outButtom);
        picture=findViewById(R.id.image);
        addAC();
        loadPicture();
    }
    private void addAC(){
        out.setOnClickListener(view -> {
            finish();
        });
    }
}
