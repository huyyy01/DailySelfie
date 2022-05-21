package com.example.dailycame;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Instrumentation;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private ImageView camera,outAlarm;
    ListView listView;
    static private long INTERVAL_TWO_MINUTES=10*1000l;
    static private long mumit=60*1000l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        createAlarm();
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            loadData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            loadData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void init(){
        camera=findViewById(R.id.camera);
        listView=findViewById(R.id.listView);
        outAlarm=findViewById(R.id.outAlarm);
        addAc();
        try {
            loadData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void addAc(){
        camera.setOnClickListener(view -> {
            dispatchTakePictureIntent();
        });
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            listImage listImage= (listImage) adapterView.getAdapter();
            Image image=listImage.imageList.get(i);
            Intent intent=new Intent(this,SmallPicture.class);
            intent.putExtra("path",image.name);
            startActivity(intent);
        });
        outAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unsetAlarm();
            }
        });
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            some.launch(takePictureIntent);
        } catch (ActivityNotFoundException e) {
            System.out.println("ko có camera");
        }
    }
    ActivityResultLauncher<Intent> some=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()== Activity.RESULT_OK){
                        Intent data=result.getData();
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        add(imageBitmap);
                    }
                }
            });
    private void add(Bitmap bitmap){
        String[] l=fileList();
        for (int i = 0; i < l.length; i++) {
            System.out.println(l[i]);
        }
        Date date=new Date();
        String s="H"+date.getTime()+".png";
        try {
            OutputStream ob=new FileOutputStream(new File(getFilesDir().getAbsoluteFile(),s));
            bitmap.compress(Bitmap.CompressFormat.PNG,100,ob);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            loadData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void loadData() throws FileNotFoundException {
        String[] listFile=fileList();
        List<Image> imageList=new ArrayList<>();
        Image image;
        OutputStream ob;
        for (int i = 0; i < listFile.length; i++) {
            System.out.println(getFilesDir().getAbsoluteFile()+"/"+listFile[i]);
            Bitmap bt= BitmapFactory.decodeFile(getFilesDir().getAbsoluteFile()+"/"+listFile[i]);
            image=new Image();
            image.image=bt;
            image.name=listFile[i];
            imageList.add(image);
        }
        listImage listImage=new listImage(this,R.layout.listview,imageList);
        listView.setAdapter(listImage);
    }


    private void createAlarm() {

        try {
            Intent intent = new Intent(this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY,17);
            calendar.set(Calendar.MINUTE, 25);
            TimeZone t=TimeZone.getDefault();


            System.out.println(calendar.getTime().toString());
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis()+10000,
                    pendingIntent);
        }
        catch (Exception exception) {
            Log.d("ALARM", exception.getMessage().toString());
        }
    }

    private void unsetAlarm() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }
}