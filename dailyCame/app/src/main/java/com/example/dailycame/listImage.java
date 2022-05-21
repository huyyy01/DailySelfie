package com.example.dailycame;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class listImage extends ArrayAdapter<Image> {
    private MainActivity context;
    public List<Image> imageList;
    public listImage(@NonNull MainActivity context, int resource,List<Image> imageList) {
        super(context, resource,imageList);
        this.context=context;
        this.imageList=imageList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View row=inflater.inflate(R.layout.listview,null);
        Image image= imageList.get(position);
        ((ImageView)row.findViewById(R.id.ImageList)).setImageBitmap(image.image);
        ((TextView)row.findViewById(R.id.textList)).setText(image.name);
        return row;
    }
}






















