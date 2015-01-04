package com.elenoon.sound;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity implements OnMenuItemClickListener {
    final Context context = this;
    private ImageButton btnAttach;
    private ImageButton btnBack;
    private ImageButton btnHome;
    private Button button;
    private MediaRecorder recorder;
    File audioFile = null;
    private ImageButton btnRecord ;

    //    Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        addListenerOnButton();

        btnAttach = (ImageButton) findViewById(R.id.btn_click);

        findViewById(R.id.btn_click).setOnClickListener(new OnClickListener() {


            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.setOnMenuItemClickListener(MainActivity.this);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
            }
        });
        btnBack = (ImageButton) findViewById(R.id.imageBack);

        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
                System.exit(0);
            }
        });
        btnHome = (ImageButton) findViewById(R.id.imageHome);
        btnHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                System.exit(0);
            }
        });
    }

    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_comedy:
                Toast.makeText(this, "Comedy Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_movies:
                Toast.makeText(this, "Movies Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_music:
                Intent intent = new Intent(context, CustomDialogActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }

    }

    public void addListenerOnButton() {

        button = (Button) findViewById(R.id.btnelenoon);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent browserIntent =
                        new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.elenoon.ir"));
                startActivity(browserIntent);

            }

        });
        //Capture button release in Android of stackoverflow
        btnRecord = (ImageButton) findViewById(R.id.btnRecord);
        btnRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) 
                {
                    
                    Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
                    File sampleDir = Environment.getExternalStorageDirectory();
                    try {
                        audioFile = File.createTempFile("sound", ".mp3", sampleDir);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    recorder = new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    recorder.setAudioEncodingBitRate(16);
                    recorder.setAudioSamplingRate(44100);
                    recorder.setOutputFile(audioFile.getAbsolutePath());
                    try {
                        recorder.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    recorder.start();

                    
                } 
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Toast.makeText(getApplicationContext(), "Audio recorded successfully",
                            Toast.LENGTH_LONG).show();
                    recorder.stop();
                    recorder.release();
                    recorder = null;
                    ContentValues values = new ContentValues(4);
                    long current = System.currentTimeMillis();
                    values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile.getName());
                    values.put(MediaStore.Audio.Media.DATE_ADDED, (int)(current/1000));
                    values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp3");
                    values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());
                    ContentResolver contentResolver = getContentResolver();
                    Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    Uri newUri = contentResolver.insert(base, values);

                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
                    /*Toast.makeText(this, "Added File" + newUri, Toast.LENGTH_LONG).show();*/
                }

                return true;
                
            }
           
        });
    }
    
}
